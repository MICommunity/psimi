package psidev.psi.mi.jami.enricher.impl.protein;


import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.util.XrefUpdateMerger;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;


/**
 * Created with IntelliJ IDEA.
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
            XrefUpdateMerger merger = new XrefUpdateMerger();
            merger.merge(proteinFetched.getXrefs() , proteinToEnrich.getXrefs() , false);
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

    /*
    @Override
    public OrganismEnricher getOrganismEnricher() {
        if( organismEnricher == null ){
            organismEnricher = new MaximumOrganismUpdater();
        }

        return organismEnricher;
    }*/
}
