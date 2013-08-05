package psidev.psi.mi.jami.enricher.impl.protein;

import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.util.XrefMerger;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public class MaximumProteinEnricher
        extends MinimumProteinEnricher
        implements ProteinEnricher {


    public MaximumProteinEnricher(ProteinFetcher proteinFetcher) {
        super(proteinFetcher);
    }

    @Override
    protected void processProtein(Protein proteinToEnrich) {
        super.processProtein(proteinToEnrich);

        if(! proteinFetched.getXrefs().isEmpty()) {
            XrefMerger merger = new XrefMerger();
            merger.merge(proteinFetched.getXrefs() , proteinToEnrich.getXrefs() , true);

            for(Xref xref: merger.getToAdd()){
                proteinToEnrich.getXrefs().add(xref);
                if(getProteinEnricherListener() != null)
                    getProteinEnricherListener().onAddedXref(proteinToEnrich, xref);
            }
        }
    }

    /*
    @Override
    public OrganismEnricher getOrganismEnricher() {
        if( organismEnricher == null ){
            organismEnricher = new MaximumOrganismEnricher();
        }

        return organismEnricher;
    }  */


}
