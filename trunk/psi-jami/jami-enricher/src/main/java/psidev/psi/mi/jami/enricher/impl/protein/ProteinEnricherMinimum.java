package psidev.psi.mi.jami.enricher.impl.protein;


import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.organism.OrganismEnricherMinimum;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.enricher.util.AliasUpdateMerger;
import psidev.psi.mi.jami.enricher.util.XrefUpdateMerger;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 14/05/13
 */
public class ProteinEnricherMinimum
        extends AbstractProteinEnricher
        implements ProteinEnricher {

    /**
     * Prepares a protein with a dead identifier to be remapped.
     * Fires a report if the remap was successful.
     * @param proteinToEnrich   The protein to be enriched
     * @return                  The status of the enrichment
     * @throws EnricherException
     */
    @Override
    protected boolean remapDeadProtein(Protein proteinToEnrich) throws EnricherException {
        String oldUniprot = proteinToEnrich.getUniprotkb();

        proteinToEnrich.getXrefs().add(
                new DefaultXref(
                        CvTermUtils.createUniprotkbDatabase(),
                        proteinToEnrich.getUniprotkb() ,
                        new DefaultCvTerm("uniprot-removed-ac")
                ));

        proteinToEnrich.getAnnotations().add(
                AnnotationUtils.createCaution("This sequence has been withdrawn from Uniprot."));

        proteinToEnrich.setUniprotkb(null);

        if(remapProtein(proteinToEnrich , "proteinToEnrich has a dead UniprotKB ID.")){
            listener.onProteinRemapped(proteinToEnrich , oldUniprot);
            listener.onUniprotKbUpdate(proteinToEnrich , oldUniprot);
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param proteinToEnrich
     */
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
            if(listener != null) listener.onFullNameUpdate(proteinToEnrich, null);
        }


        //PRIMARY Uniprot AC
        if(proteinToEnrich.getUniprotkb() == null
                && proteinFetched.getUniprotkb() != null) {
            proteinToEnrich.setUniprotkb(proteinFetched.getUniprotkb());
            if(listener != null) listener.onUniprotKbUpdate(proteinToEnrich, null);
        }

        //Sequence
        if(proteinToEnrich.getSequence() == null
                && proteinFetched.getSequence() != null){
            proteinToEnrich.setSequence(proteinFetched.getSequence());
            if(listener != null) listener.onSequenceUpdate(proteinToEnrich, null);

        }

        //Checksums
        // Can only add a checksum if there is a sequence which matches the protein fetched and an organism
        if(proteinFetched.getSequence() != null
                && proteinToEnrich.getSequence().equalsIgnoreCase(proteinFetched.getSequence())){

            boolean hasCrc64Checksum = false;
            boolean hasRogidChecksum = false;
            Checksum fetchedCrc64Checksum = null;
            Checksum fetchedRogidChecksum = null;

            // If the organisms do no match - all rogid searching can be curtailed.
            if(proteinFetched.getOrganism().getTaxId() != proteinToEnrich.getOrganism().getTaxId()
                    || proteinToEnrich.getOrganism().getTaxId() == -3 ){
                hasRogidChecksum = true;
            }

            // Find checksums already in protein
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
            // Find the fetched checksums
            for(Checksum checksum : proteinFetched.getChecksums()){
                if(checksum.getMethod() != null){
                    if(checksum.getMethod().getShortName().equalsIgnoreCase(Checksum.ROGID)
                            || (checksum.getMethod().getMIIdentifier() != null
                            && checksum.getMethod().getMIIdentifier().equalsIgnoreCase(Checksum.ROGID_MI))){
                        fetchedRogidChecksum = checksum;
                    }
                    else if(checksum.getMethod().getShortName().equalsIgnoreCase("CRC64")){
                        fetchedCrc64Checksum = checksum;
                    }
                }
                if( (fetchedCrc64Checksum != null || hasCrc64Checksum)
                        && (fetchedRogidChecksum != null || hasRogidChecksum) ) break;
            }

            if(!hasCrc64Checksum) {
                if(fetchedCrc64Checksum != null) {
                    proteinToEnrich.getChecksums().add(fetchedCrc64Checksum);
                    if(listener != null) listener.onAddedChecksum(proteinToEnrich, fetchedCrc64Checksum);
                }
            }

            if(!hasRogidChecksum){
                if(fetchedRogidChecksum != null){
                    proteinToEnrich.getChecksums().add(fetchedRogidChecksum);
                    if(listener != null) listener.onAddedChecksum(proteinToEnrich, fetchedRogidChecksum);
                }
            }
        }


        // == Identifiers ==
        if(! proteinFetched.getIdentifiers().isEmpty()) {
            XrefUpdateMerger merger = new XrefUpdateMerger();
            merger.merge(proteinFetched.getIdentifiers() , proteinToEnrich.getIdentifiers(), true);

            for(Xref xref: merger.getToAdd()){
                proteinToEnrich.getIdentifiers().add(xref);
                if(listener != null) listener.onAddedIdentifier(proteinToEnrich, xref);
            }
        }



        // == Alias ==
        if(! proteinFetched.getAliases().isEmpty()) {
            AliasUpdateMerger merger = new AliasUpdateMerger();
            merger.merge(proteinFetched.getAliases() , proteinToEnrich.getAliases());

            for(Alias alias: merger.getToAdd()){
                proteinToEnrich.getAliases().add(alias);
                if(listener != null) listener.onAddedAlias(proteinToEnrich, alias);
            }
        }

    }



    @Override
    public OrganismEnricher getOrganismEnricher() {
        if( organismEnricher == null ) organismEnricher = new OrganismEnricherMinimum();
        return organismEnricher;
    }
}
