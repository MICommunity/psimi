package psidev.psi.mi.jami.enricher.impl.protein;


import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Enriches a protein to the minimum level. As an enricher, no data will be overwritten in the protein being enriched.
 * This covers full name, primary AC, checksums, identifiers and aliases.
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

        //CHECKSUMS
        //TODO - behaviour may add two crc64s or ROGID
        ChecksumMerger checksumMerger = new ChecksumMerger();
        checksumMerger.merge(proteinToEnrich.getChecksums() , proteinFetched.getChecksums());
        for(Checksum toAdd : checksumMerger.getFetchedToAdd()){
            proteinToEnrich.getChecksums().add(toAdd);
            if(getProteinEnricherListener() != null)
                getProteinEnricherListener().onAddedChecksum(proteinToEnrich , toAdd);
        }


        // == Identifiers ==
        if(! proteinFetched.getIdentifiers().isEmpty()) {
            XrefMergeUtils merger = new XrefMergeUtils();
            merger.merge(proteinFetched.getIdentifiers() , proteinToEnrich.getIdentifiers(), true);

            for(Xref xref: merger.getToAdd()){
                proteinToEnrich.getIdentifiers().add(xref);
                if(getProteinEnricherListener() != null)
                    getProteinEnricherListener().onAddedIdentifier(proteinToEnrich, xref);
            }
        }



        // == Alias ==
        if(! proteinFetched.getAliases().isEmpty()) {
            AliasMergeUtils merger = new AliasMergeUtils();
            merger.merge(proteinFetched.getAliases() , proteinToEnrich.getAliases());

            for(Alias alias: merger.getToAdd()){
                proteinToEnrich.getAliases().add(alias);
                if(getProteinEnricherListener() != null)
                    getProteinEnricherListener().onAddedAlias(proteinToEnrich, alias);
            }
        }
    }
}
