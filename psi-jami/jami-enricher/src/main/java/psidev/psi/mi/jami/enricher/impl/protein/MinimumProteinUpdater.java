package psidev.psi.mi.jami.enricher.impl.protein;


import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.ChecksumMerger;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Updates a protein to the minimum level. As an updater, data may be overwritten.
 * This covers short name, full name, primary AC, checksums, identifiers and aliases.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  13/06/13
 */
public class MinimumProteinUpdater
        extends AbstractProteinEnricher
        implements ProteinEnricher {

    protected boolean isRemapped = false;

    /**
     * The only constructor which forces the setting of the fetcher
     * If the cvTerm fetcher is null, an illegal state exception will be thrown at the next enrichment.
     * @param proteinFetcher    The protein fetcher to use.
     */
    public MinimumProteinUpdater(ProteinFetcher proteinFetcher) {
        super(proteinFetcher);
    }

    /**
     * Enrichment of a single Protein.
     * At the end of the enrichment, the listener will be fired
     * @param proteinToEnrich       A protein to enrich
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
    @Override
    public void enrichProtein(Protein proteinToEnrich) throws EnricherException {
        isRemapped = false;
        super.enrichProtein(proteinToEnrich);
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

    /**
     * Strategy for the protein enrichment.
     * This method can be overwritten to change how the protein is enriched.
     * @param proteinToEnrich   The protein to be enriched.
     */
    @Override
    protected void processProtein(Protein proteinToEnrich)  {
        //ShortName - is never null
        if (! proteinToEnrich.getShortName().equalsIgnoreCase(proteinFetched.getShortName() )) {
            String oldValue = proteinToEnrich.getShortName();
            proteinToEnrich.setShortName(proteinFetched.getShortName());
            if(getProteinEnricherListener() != null)
                getProteinEnricherListener().onShortNameUpdate(proteinToEnrich, oldValue);
        }

        //Full name
        if(proteinFetched.getFullName() != null
                && ! proteinFetched.getFullName().equalsIgnoreCase(proteinToEnrich.getFullName()) ) {
            String oldValue = proteinToEnrich.getFullName();
            proteinToEnrich.setFullName(proteinFetched.getFullName());
            if(getProteinEnricherListener() != null)
                getProteinEnricherListener().onFullNameUpdate(proteinToEnrich, oldValue);
        }

        //PRIMARY Uniprot AC
        if(proteinFetched.getUniprotkb() != null
                && ! proteinFetched.getUniprotkb().equalsIgnoreCase(proteinToEnrich.getUniprotkb()) ) {
            String oldValue = proteinToEnrich.getUniprotkb();
            proteinToEnrich.setUniprotkb(proteinFetched.getUniprotkb());
            if(getProteinEnricherListener() != null)
                getProteinEnricherListener().onUniprotKbUpdate(proteinToEnrich, oldValue);
        }

        //Sequence
        if(proteinFetched.getSequence() != null
                && ! isRemapped //Todo - check that if the protein was remapped, the sequence remains unchanged
                && ! proteinFetched.getSequence().equalsIgnoreCase(proteinToEnrich.getSequence()) ) {
            String oldValue = proteinToEnrich.getSequence();
            proteinToEnrich.setSequence(proteinFetched.getSequence());
            if(getProteinEnricherListener() != null)
                getProteinEnricherListener().onSequenceUpdate(proteinToEnrich, oldValue);
        }

        // CHECKSUMS
        ChecksumMerger checksumMerger = new ChecksumMerger();
        checksumMerger.merge(proteinToEnrich.getChecksums() , proteinFetched.getChecksums());
        for(Checksum toAdd : checksumMerger.getFetchedToAdd()){
            proteinToEnrich.getChecksums().add(toAdd);
            if(getProteinEnricherListener() != null)
                getProteinEnricherListener().onAddedChecksum(proteinToEnrich , toAdd);
        }
        for(Checksum toRemove : checksumMerger.getToRemove()){
            proteinToEnrich.getChecksums().remove(toRemove);
            if(getProteinEnricherListener() != null)
                getProteinEnricherListener().onRemovedChecksum(proteinToEnrich , toRemove);
        }



        // For Identifiers and Aliases:
        // 1 - Find the CvTerms which represent the different types in the fetched set.
        // 2 - Cycle through the toEnrich set, recording any entries which meet the criteria to be removed
        //      and any entries which do not need to be added.
        // 3 - Add/remove those entries on the appropriate lists.

        // == Identifiers ==
        if(! proteinFetched.getIdentifiers().isEmpty()) {
            XrefMergeUtils xrefMerger = new XrefMergeUtils();
            // TODO no identifiers can be removed while the qualifiers are offering protection
            xrefMerger.merge(proteinFetched.getIdentifiers() , proteinToEnrich.getIdentifiers() , true);

            for(Xref xref: xrefMerger.getToRemove()){
                proteinToEnrich.getIdentifiers().remove(xref);
                if(getProteinEnricherListener() != null)
                    getProteinEnricherListener().onRemovedIdentifier(proteinToEnrich , xref);
            }

            for(Xref xref: xrefMerger.getToAdd()){
                proteinToEnrich.getIdentifiers().add(xref);
                if(getProteinEnricherListener() != null)
                    getProteinEnricherListener().onAddedIdentifier(proteinToEnrich, xref);
            }
        }



        // == Alias ==
        if(! proteinFetched.getAliases().isEmpty()) {
            AliasMergeUtils aliasMerger = new AliasMergeUtils();
            aliasMerger.merge(proteinFetched.getAliases() , proteinToEnrich.getAliases());

            for(Alias alias: aliasMerger.getToRemove()){
                proteinToEnrich.getAliases().remove(alias);
                if(getProteinEnricherListener() != null)
                    getProteinEnricherListener().onRemovedAlias(proteinToEnrich , alias);
            }

            for(Alias alias: aliasMerger.getToAdd()){
                proteinToEnrich.getAliases().add(alias);
                if(getProteinEnricherListener() != null)
                    getProteinEnricherListener().onAddedAlias(proteinToEnrich, alias);
            }
        }
    }
}
