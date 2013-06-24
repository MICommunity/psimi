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
            if(proteinToEnrich.getInteractorType().getMIIdentifier().equalsIgnoreCase(
                    Interactor.UNKNOWN_INTERACTOR_MI)){

                proteinToEnrich.setInteractorType(CvTermUtils.createProteinInteractorType());
                if(listener != null) listener.onAddedInteractorType(proteinToEnrich);
            }
        }

        //ShortName - is never null

        //FullName
        if(proteinToEnrich.getFullName() == null
                && proteinFetched.getFullName() != null){
            proteinToEnrich.setFullName( proteinFetched.getFullName() );
            if(listener != null) listener.onFullNameUpdate(proteinFetched, null);
        }

        //TODO
        //PRIMARY Uniprot AC
        if(proteinToEnrich.getUniprotkb() == null
                && proteinFetched.getUniprotkb() != null) {
            proteinToEnrich.setUniprotkb(proteinFetched.getUniprotkb());
            if(listener != null) listener.onUniprotKbUpdate(proteinFetched, null);
        }

        //Sequence
        if(proteinToEnrich.getSequence() == null
                && proteinFetched.getSequence() != null){
            proteinToEnrich.setSequence(proteinFetched.getSequence());
            if(listener != null) listener.onSequenceUpdate(proteinFetched, null);

        }

        //Checksums
        // Can only add a checksum if there is a sequence which matches the protein fetched and an organism
        if(proteinToEnrich.getSequence() != null
                && proteinToEnrich.getSequence().equals(proteinFetched.getSequence())
                && proteinToEnrich.getOrganism().getTaxId() != -3){

            boolean hasCrc64Checksum = false;
            boolean hasRogidChecksum = false;
            for(Checksum checksum : proteinToEnrich.getChecksums()){
                if(checksum.getMethod() != null){
                    if(checksum.getMethod().getShortName().equalsIgnoreCase(Checksum.ROGID)
                            || (checksum.getMethod().getMIIdentifier() != null
                            && checksum.getMethod().getMIIdentifier().equalsIgnoreCase(Checksum.ROGID_MI))){
                        hasRogidChecksum = true;
                    }
                    else if(checksum.getMethod().getShortName().equalsIgnoreCase("CRC64")){
                        hasCrc64Checksum = true;
                    }
                }
                if(hasCrc64Checksum && hasRogidChecksum) break;
            }

            if(!hasRogidChecksum){
                RogidGenerator rogidGenerator = new RogidGenerator();
                String rogidValue = rogidGenerator.calculateRogid(
                        proteinToEnrich.getSequence(),""+proteinToEnrich.getOrganism().getTaxId());

                Checksum rogidChecksum = ChecksumUtils.createRogid(rogidValue);
                proteinToEnrich.getChecksums().add(rogidChecksum);
                if(listener != null) listener.onAddedChecksum(proteinToEnrich, rogidChecksum);
            }

            if(!hasCrc64Checksum) {  //TODO implement the creation of a real CRC64 checksum
                String crc64Value = "MAKE A CRC64CHECKSUM";
                Checksum crc64Checksum = ChecksumUtils.createChecksum("CRC64", crc64Value);
                proteinToEnrich.getChecksums().add(crc64Checksum);
                if(listener != null) listener.onAddedChecksum(proteinToEnrich, crc64Checksum);
            }
        }



        //TODO - remove comparator
        // IDENTIFIERS
        Collection<Xref> subtractedIdentifiers = CollectionManipulationUtils.comparatorSubtract(
                proteinFetched.getIdentifiers(),
                proteinToEnrich.getIdentifiers(),
                new DefaultXrefComparator());
        for(Xref xref: subtractedIdentifiers){

            proteinToEnrich.getIdentifiers().add(xref);
            if(listener != null) listener.onAddedIdentifier(proteinFetched, xref);
        }

        //TODO some introduced aliases may enter a form of conflict - need to do a further comparison.
        // ALIASES
        Collection<Alias> subtractedAliases = CollectionManipulationUtils.comparatorSubtract(
                proteinFetched.getAliases(),
                proteinToEnrich.getAliases(),
                new DefaultAliasComparator());
        for(Alias alias: subtractedAliases){

            proteinToEnrich.getAliases().add(alias);
            if(listener != null) listener.onAddedAlias(proteinFetched, alias);
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
