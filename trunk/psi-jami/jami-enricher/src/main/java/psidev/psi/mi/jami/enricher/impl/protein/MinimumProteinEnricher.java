package psidev.psi.mi.jami.enricher.impl.protein;


import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.impl.organism.MinimumOrganismEnricher;
import psidev.psi.mi.jami.enricher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.enricher.util.CollectionManipulationUtils;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.comparator.alias.DefaultAliasComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;
import uk.ac.ebi.intact.irefindex.seguid.RogidGenerator;
import uk.ac.ebi.intact.irefindex.seguid.SeguidException;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 14:27
 */
public class MinimumProteinEnricher
        extends AbstractProteinEnricher
        implements ProteinEnricher {


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

        return remapProtein(proteinToEnrich , "proteinToEnrich has a dead UniprotKB ID.");
    }

    @Override
    protected void processProtein(Protein proteinToEnrich) throws SeguidException {
        //InteractorType
        if(!proteinToEnrich.getInteractorType().getMIIdentifier().equalsIgnoreCase(Protein.PROTEIN_MI)){
            if(proteinToEnrich.getInteractorType().getMIIdentifier().equalsIgnoreCase(Interactor.UNKNOWN_INTERACTOR_MI)){
                if(listener != null) listener.onAddedInteractorType(proteinToEnrich);
                proteinToEnrich.setInteractorType(CvTermUtils.createProteinInteractorType());
            }
        }

        //ShortName - is never null

        //FullName
        if(proteinToEnrich.getFullName() == null
                && proteinFetched.getFullName() != null){
            if(listener != null) listener.onFullNameUpdate(proteinFetched, null);
            proteinToEnrich.setFullName( proteinFetched.getFullName() );
        }

        //TODO
        //PRIMARY Uniprot AC
        if(proteinToEnrich.getUniprotkb() == null
                && proteinFetched.getUniprotkb() != null) {
            if(listener != null) listener.onUniprotKbUpdate(proteinFetched, null);
            proteinToEnrich.setUniprotkb(proteinFetched.getUniprotkb());
        }

        //Sequence
        if(proteinToEnrich.getSequence() == null
                && proteinFetched.getSequence() != null){
            if(listener != null) listener.onSequenceUpdate(proteinFetched, null);
            proteinToEnrich.setSequence(proteinFetched.getSequence());
        }

        //Checksums
        boolean hasCrc64Checksum = false;
        boolean hasRogidChecksum = false;
        for(Checksum checksum : proteinToEnrich.getChecksums()){
            if(checksum.getMethod().getShortName().equalsIgnoreCase(Checksum.ROGID)
                    || checksum.getMethod().getMIIdentifier().equalsIgnoreCase(Checksum.ROGID_MI)){
                hasRogidChecksum = true;
            }

            if(checksum.getMethod().getShortName().equalsIgnoreCase("CRC64")){
                hasCrc64Checksum = true;
            }
            if(hasCrc64Checksum && hasRogidChecksum) break;
        }

        // Can only add a CRC64 if there is a sequence which matches the protein fetched and an organism
        if(proteinToEnrich.getSequence() != null
                && proteinToEnrich.getSequence().equals(proteinFetched.getSequence())
                && proteinToEnrich.getOrganism().getTaxId() != -3){

            if(!hasRogidChecksum){
                RogidGenerator rogidGenerator = new RogidGenerator();
                String rogidValue = rogidGenerator.calculateRogid(
                        proteinToEnrich.getSequence(),""+proteinToEnrich.getOrganism().getTaxId());

                Checksum rogidChecksum = ChecksumUtils.createRogid(rogidValue);
                proteinToEnrich.getChecksums().add(rogidChecksum);
                if(listener != null) listener.onAddedChecksum(proteinToEnrich, rogidChecksum);
            }

            if(!hasCrc64Checksum) {
                String crc64Value = null;
                Checksum crc64Checksum = ChecksumUtils.createChecksum("CRC64", crc64Value);
                proteinToEnrich.getChecksums().add(crc64Checksum);
                if(listener != null) listener.onAddedChecksum(proteinToEnrich, crc64Checksum);
            }
        }



        //TODO - is this correct? Is there a scenario where 2 primary ACs are created?
        // IDENTIFIERS
        Collection<Xref> subtractedIdentifiers = CollectionManipulationUtils.comparatorSubtract(
                proteinFetched.getIdentifiers(),
                proteinToEnrich.getIdentifiers(),
                new DefaultXrefComparator());
        for(Xref xref: subtractedIdentifiers){
            if(listener != null) listener.onAddedIdentifier(proteinFetched, xref);
            proteinToEnrich.getIdentifiers().add(xref);
        }

        //TODO some introduced aliases may enter a form of conflict - need to do a further comparison.
        // ALIASES
        Collection<Alias> subtractedAliases = CollectionManipulationUtils.comparatorSubtract(
                proteinFetched.getAliases(),
                proteinToEnrich.getAliases(),
                new DefaultAliasComparator());
        for(Alias alias: subtractedAliases){
            if(listener != null) listener.onAddedAlias(proteinFetched, alias);
            proteinToEnrich.getAliases().add(alias);
        }




    }



    @Override
    public OrganismEnricher getOrganismEnricher() {
        if( organismEnricher == null ){
            organismEnricher = new MinimumOrganismEnricher();
            organismEnricher.setFetcher(new MockOrganismFetcher());
        }

        return organismEnricher;
    }
}
