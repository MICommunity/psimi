package psidev.psi.mi.jami.enricher.impl.protein;

import psidev.psi.mi.jami.bridges.exception.BadResultException;
import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.BadEnrichedFormException;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.enricher.impl.organism.MinimumOrganismUpdater;
import psidev.psi.mi.jami.enricher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.comparator.alias.DefaultAliasComparator;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;
import uk.ac.ebi.intact.irefindex.seguid.RogidGenerator;
import uk.ac.ebi.intact.irefindex.seguid.SeguidException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public class MinimumProteinUpdater
        extends AbstractProteinEnricher
        implements ProteinEnricher {

    protected boolean isRemapped = false;

    @Override
    public boolean enrichProtein(Protein proteinToEnrich)
            throws MissingServiceException, BadResultException, SeguidException,
            BadToEnrichFormException, BadSearchTermException, BridgeFailedException,
            BadEnrichedFormException {

        isRemapped = false;
        return super.enrichProtein(proteinToEnrich);
    }

    @Override
    protected boolean remapDeadProtein(Protein proteinToEnrich) {
        proteinToEnrich.getXrefs().add(
                new DefaultXref(
                        CvTermUtils.createUniprotkbDatabase(),
                        proteinToEnrich.getUniprotkb()
                )); //TODO UNIPROT REMOVED QUALIFIER

        proteinToEnrich.getAnnotations().add(
                AnnotationUtils.createCaution("This sequence has been withdrawn from Uniprot."));

        proteinToEnrich.setUniprotkb(null);

        if(remapProtein(proteinToEnrich , "proteinToEnrich has a dead UniprotKB ID.")){
            isRemapped = true;
            return true;
        } else {
            return false;
        }
    }


    @Override
    protected void processProtein(Protein proteinToEnrich) throws SeguidException {
        //ShortName - is never null
        if (! proteinToEnrich.getShortName().equalsIgnoreCase(proteinFetched.getShortName() )) {
            if(listener != null) listener.onShortNameUpdate(proteinToEnrich, proteinToEnrich.getShortName());
            proteinToEnrich.setShortName(proteinFetched.getShortName());
        }

        //Full name
        if(proteinFetched.getFullName() != null
                && ! proteinFetched.getFullName().equalsIgnoreCase(proteinToEnrich.getFullName()) ) {
            if(listener != null) listener.onFullNameUpdate(proteinToEnrich, proteinToEnrich.getFullName());
            proteinToEnrich.setFullName(proteinFetched.getFullName());
        }

        //PRIMARY Uniprot AC
        if(proteinFetched.getUniprotkb() != null
                && ! proteinFetched.getUniprotkb().equalsIgnoreCase(proteinToEnrich.getUniprotkb()) ) {
            if(listener != null) listener.onUniprotKbUpdate(proteinToEnrich, proteinToEnrich.getUniprotkb());
            proteinToEnrich.setUniprotkb(proteinFetched.getUniprotkb());
        }

        //Sequence
        if(proteinFetched.getSequence() != null
                && ! isRemapped
                && ! proteinFetched.getSequence().equalsIgnoreCase(proteinToEnrich.getSequence()) ) {
            if(listener != null) listener.onSequenceUpdate(proteinToEnrich, proteinToEnrich.getSequence());
            proteinToEnrich.setSequence(proteinFetched.getSequence());
        }

        //Checksums
        Checksum currentCrc64Checksum = null;
        Checksum currentRogidChecksum = null;
        for(Checksum checksum : proteinToEnrich.getChecksums()){
            if(checksum.getMethod().getShortName().equalsIgnoreCase(Checksum.ROGID)
                    || checksum.getMethod().getMIIdentifier().equalsIgnoreCase(Checksum.ROGID_MI)){
                currentRogidChecksum = checksum;
            }

            if(checksum.getMethod().getShortName().equalsIgnoreCase("CRC64")){
                currentCrc64Checksum = checksum;
            }
            if(currentCrc64Checksum != null && currentRogidChecksum != null) break;
        }

        if(proteinToEnrich.getSequence() != null
                && proteinFetched.getSequence() != null
                && proteinToEnrich.getOrganism().getTaxId() != -3){

            // ROGID
            RogidGenerator rogidGenerator = new RogidGenerator();
            String rogidValue = rogidGenerator.calculateRogid(
                    proteinToEnrich.getSequence(),""+proteinToEnrich.getOrganism().getTaxId());

            // Remove old checksum if it does not match the new value.
            if(currentRogidChecksum != null && ! currentRogidChecksum.getValue().equals(rogidValue)){
                proteinToEnrich.getChecksums().remove(currentRogidChecksum);
                if(listener != null) listener.onRemovedChecksum(proteinToEnrich, currentRogidChecksum);
                currentRogidChecksum = null;
            }
            // Add the new checksum if there is no current checksum
            // (or if it has been removed because it was out of date)
            if(currentRogidChecksum == null){
                Checksum rogidChecksum = ChecksumUtils.createRogid(rogidValue);
                proteinToEnrich.getChecksums().add(rogidChecksum);
                if(listener != null) listener.onAddedChecksum(proteinToEnrich, rogidChecksum);
            }

            // CRC64
            String crc64Value = null;

            // Remove old checksum if it does not match the new value.
            if(currentCrc64Checksum != null && ! currentCrc64Checksum.getValue().equals(crc64Value)){
                proteinToEnrich.getChecksums().remove(currentCrc64Checksum);
                if(listener != null) listener.onRemovedChecksum(proteinToEnrich, currentCrc64Checksum);
                crc64Value = null;
            }
            if(currentCrc64Checksum == null){
                Checksum crc64Checksum = ChecksumUtils.createChecksum("CRC64", crc64Value);
                proteinToEnrich.getChecksums().add(crc64Checksum);
                if(listener != null) listener.onAddedChecksum(proteinToEnrich, crc64Checksum);
            }
        }




        // For Identifiers and Aliases:
        // 1 - Find the CvTerms which represent the different types in the fetched set.
        // 2 - Cycle through the toEnrich set, recording any entries which meet the criteria to be removed
        //      and any entries which do not need to be added.
        // 3 - Add/remove those entries on the appropriate lists.


        //Identifiers
        Set<Xref> fetchedIdentifiersToAdd = new TreeSet<Xref>(new DefaultXrefComparator());
        fetchedIdentifiersToAdd.addAll(proteinFetched.getIdentifiers());

        Set<CvTerm> identifierCvTerms = new TreeSet<CvTerm>(new DefaultCvTermComparator());
        for(Xref xref : fetchedIdentifiersToAdd){
            if(! identifierCvTerms.contains(xref.getDatabase())) identifierCvTerms.add(xref.getDatabase());
        }

        Collection<Xref> toRemoveIdentifiers = new ArrayList<Xref>();
        for(Xref xrefIdentifier : proteinToEnrich.getIdentifiers()){
            if(xrefIdentifier.getQualifier() == null
                    && ! fetchedIdentifiersToAdd.contains(xrefIdentifier)
                    && identifierCvTerms.contains(xrefIdentifier.getDatabase())){
                toRemoveIdentifiers.add(xrefIdentifier);
            }
            else if( fetchedIdentifiersToAdd.contains(xrefIdentifier)){
                fetchedIdentifiersToAdd.remove(xrefIdentifier);
            }
        }
        for(Xref xref: toRemoveIdentifiers){
            proteinToEnrich.getIdentifiers().remove(xref);
            if(listener != null) listener.onRemovedIdentifier(proteinToEnrich , xref);
        }

        for(Xref xref: fetchedIdentifiersToAdd){
            proteinToEnrich.getIdentifiers().add(xref);
            if(listener != null) listener.onAddedIdentifier(proteinToEnrich, xref);
        }


        //Aliases
        Set<Alias> fetchedAliasesToAdd = new TreeSet<Alias>(new DefaultAliasComparator());
        fetchedAliasesToAdd.addAll(proteinFetched.getAliases());

        Set<CvTerm> aliasCvTerms = new TreeSet<CvTerm>(new DefaultCvTermComparator());
        for(Alias alias : fetchedAliasesToAdd){
            if(! aliasCvTerms.contains(alias.getType())) aliasCvTerms.add(alias.getType());
        }

        Collection<Alias> toRemoveAliases = new ArrayList<Alias>();
        for(Alias alias : proteinToEnrich.getAliases()){
            if(! fetchedAliasesToAdd.contains(alias)
                    && aliasCvTerms.contains(alias.getType())){
                toRemoveAliases.add(alias);
            }
            else if( fetchedAliasesToAdd.contains(alias)){
                fetchedAliasesToAdd.remove(alias);
            }
        }
        for(Alias alias: toRemoveAliases){
            proteinToEnrich.getAliases().remove(alias);
            if(listener != null) listener.onRemovedAlias(proteinToEnrich , alias);
        }

        for(Alias alias: fetchedAliasesToAdd){
            proteinToEnrich.getAliases().add(alias);
            if(listener != null) listener.onAddedAlias(proteinToEnrich , alias);
        }
    }

    @Override
    public OrganismEnricher getOrganismEnricher() {
        if( organismEnricher == null ){
            organismEnricher = new MinimumOrganismUpdater();
            organismEnricher.setFetcher(new MockOrganismFetcher());
        }

        return organismEnricher;
    }
}
