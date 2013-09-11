package psidev.psi.mi.jami.enricher.impl.protein;


import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;


/**
 * Provides maximum updating of the Protein.
 * Will update all aspects covered by the minimum updater as well as updating the xrefs.
 * As an updater, values from the provided protein to enrich may be overwritten.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 20/05/13
 */
public class MaximumProteinUpdater
        extends MinimumProteinUpdater
        implements ProteinEnricher {


    public MaximumProteinUpdater(ProteinFetcher proteinFetcher) {
        super(proteinFetcher);
    }

    @Override
    protected void processProtein(Protein proteinToEnrich){
        super.processProtein(proteinToEnrich);

        if(! proteinFetched.getXrefs().isEmpty()) {
            XrefMergeUtils merger = new XrefMergeUtils();               //TODO check that qualifiers should protect xrefs
            merger.merge(proteinFetched.getXrefs() , proteinToEnrich.getXrefs() , true);
            for(Xref xref: merger.getToRemove()){
                proteinToEnrich.getXrefs().remove(xref);
                if(getProteinEnricherListener() != null)
                    getProteinEnricherListener().onRemovedXref(proteinToEnrich , xref);
            }
            for(Xref xref: merger.getToAdd()){
                proteinToEnrich.getXrefs().add(xref);
                if(getProteinEnricherListener() != null)
                    getProteinEnricherListener().onAddedXref(proteinToEnrich, xref);
            }
        }
    }
}
