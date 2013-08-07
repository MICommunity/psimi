package psidev.psi.mi.jami.enricher.impl.protein;


import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.AliasMerger;
import psidev.psi.mi.jami.enricher.util.XrefMerger;
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
public class MinimumProteinEnricher
        extends AbstractProteinEnricher
        implements ProteinEnricher {

    /**
     * The only constructor which forces the setting of the fetcher
     * If the cvTerm fetcher is null, an illegal state exception will be thrown at the next enrichment.
     * @param proteinFetcher    The protein fetcher to use.
     */
    public MinimumProteinEnricher(ProteinFetcher proteinFetcher) {
        super(proteinFetcher);
    }

    /**
     * Prepares a protein with a dead identifier to be remapped.
     * Fires a report if the remap was successful.
     * @param proteinToEnrich   The protein to be enriched
     * @return                  The status of the enrichment
     * @throws EnricherException    Thrown if the remapper encounters a problem
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
            if(getProteinEnricherListener() != null){
                getProteinEnricherListener().onProteinRemapped(proteinToEnrich , oldUniprot);
                getProteinEnricherListener().onUniprotKbUpdate(proteinToEnrich , oldUniprot);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Strategy for the protein enrichment.
     * This method can be overwritten to change how the protein is enriched.
     * @param proteinToEnrich   The protein to be enriched.
     */
    @Override
    protected void processProtein(Protein proteinToEnrich) {
        //ShortName - is never null

        //FullName
        if(proteinToEnrich.getFullName() == null
                && proteinFetched.getFullName() != null){
            proteinToEnrich.setFullName( proteinFetched.getFullName() );
            if(getProteinEnricherListener() != null)
                getProteinEnricherListener().onFullNameUpdate(proteinToEnrich, null);
        }


        //PRIMARY Uniprot AC
        if(proteinToEnrich.getUniprotkb() == null
                && proteinFetched.getUniprotkb() != null) {
            proteinToEnrich.setUniprotkb(proteinFetched.getUniprotkb());
            if(getProteinEnricherListener() != null)
                getProteinEnricherListener().onUniprotKbUpdate(proteinToEnrich, null);
        }

        //Sequence
        if(proteinToEnrich.getSequence() == null
                && proteinFetched.getSequence() != null){
            proteinToEnrich.setSequence(proteinFetched.getSequence());
            if(getProteinEnricherListener() != null)
                getProteinEnricherListener().onSequenceUpdate(proteinToEnrich, null);

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
                    if(getProteinEnricherListener() != null)
                        getProteinEnricherListener().onAddedChecksum(proteinToEnrich, fetchedCrc64Checksum);
                }
            }

            if(!hasRogidChecksum){
                if(fetchedRogidChecksum != null){
                    proteinToEnrich.getChecksums().add(fetchedRogidChecksum);
                    if(getProteinEnricherListener() != null)
                        getProteinEnricherListener().onAddedChecksum(proteinToEnrich, fetchedRogidChecksum);
                }
            }
        }


        // == Identifiers ==
        if(! proteinFetched.getIdentifiers().isEmpty()) {
            XrefMerger merger = new XrefMerger();
            merger.merge(proteinFetched.getIdentifiers() , proteinToEnrich.getIdentifiers(), true);

            for(Xref xref: merger.getToAdd()){
                proteinToEnrich.getIdentifiers().add(xref);
                if(getProteinEnricherListener() != null)
                    getProteinEnricherListener().onAddedIdentifier(proteinToEnrich, xref);
            }
        }



        // == Alias ==
        if(! proteinFetched.getAliases().isEmpty()) {
            AliasMerger merger = new AliasMerger();
            merger.merge(proteinFetched.getAliases() , proteinToEnrich.getAliases());

            for(Alias alias: merger.getToAdd()){
                proteinToEnrich.getAliases().add(alias);
                if(getProteinEnricherListener() != null)
                    getProteinEnricherListener().onAddedAlias(proteinToEnrich, alias);
            }
        }
    }
}
