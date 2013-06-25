package psidev.psi.mi.jami.enricher.impl.protein;


import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.impl.organism.MinimumOrganismEnricher;
import psidev.psi.mi.jami.enricher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;


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
    protected boolean remapDeadProtein(Protein proteinToEnrich) throws BridgeFailedException {
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
    protected void processProtein(Protein proteinToEnrich) {
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
        if(proteinFetched.getSequence() != null
                && proteinToEnrich.getSequence().equalsIgnoreCase(proteinFetched.getSequence())){

            boolean hasCrc64Checksum = false;
            boolean hasRogidChecksum = false;
            Checksum crc64Checksum = null;
            Checksum rogidChecksum = null;

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

            for(Checksum checksum : proteinFetched.getChecksums()){
                if(checksum.getMethod() != null){
                    if(checksum.getMethod().getShortName().equalsIgnoreCase(Checksum.ROGID)
                            || (checksum.getMethod().getMIIdentifier() != null
                            && checksum.getMethod().getMIIdentifier().equalsIgnoreCase(Checksum.ROGID_MI))){
                        rogidChecksum = checksum;
                    }
                    else if(checksum.getMethod().getShortName().equalsIgnoreCase("CRC64")){
                        crc64Checksum = checksum;
                    }
                }
                if(crc64Checksum != null && rogidChecksum != null) break;
            }

            if(!hasCrc64Checksum) {
                if(crc64Checksum != null) {
                    proteinToEnrich.getChecksums().add(crc64Checksum);
                    if(listener != null) listener.onAddedChecksum(proteinToEnrich, crc64Checksum);
                }
            }

            if(!hasRogidChecksum
                    && proteinFetched.getOrganism().getTaxId() == proteinToEnrich.getOrganism().getTaxId()
                    && proteinToEnrich.getOrganism().getTaxId() != -3){
                if(rogidChecksum != null){
                    proteinToEnrich.getChecksums().add(rogidChecksum);
                    if(listener != null) listener.onAddedChecksum(proteinToEnrich, rogidChecksum);
                }
            }
        }



        //TODO - remove comparator
        // IDENTIFIERS
        /*Collection<Xref> subtractedIdentifiers = CollectionManipulationUtils.comparatorSubtract(
                proteinFetched.getIdentifiers(),
                proteinToEnrich.getIdentifiers(),
                new DefaultXrefComparator());
        for(Xref xref: subtractedIdentifiers){

            proteinToEnrich.getIdentifiers().add(xref);
            if(listener != null) listener.onAddedIdentifier(proteinFetched, xref);
        } */

        //TODO some introduced aliases may enter a form of conflict - need to do a further comparison.
        // ALIASES
        /*Collection<Alias> subtractedAliases = CollectionManipulationUtils.comparatorSubtract(
                proteinFetched.getAliases(),
                proteinToEnrich.getAliases(),
                new DefaultAliasComparator());
        for(Alias alias: subtractedAliases){

            proteinToEnrich.getAliases().add(alias);
            if(listener != null) listener.onAddedAlias(proteinFetched, alias);
        } */

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
