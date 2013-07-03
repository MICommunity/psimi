package psidev.psi.mi.jami.enricher.impl.protein;

import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.impl.organism.MaximumOrganismEnricher;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.enricher.util.XrefUpdateMerger;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 16:34
 */
public class MaximumProteinEnricher
        extends MinimumProteinEnricher
        implements ProteinEnricher {



    @Override
    protected void processProtein(Protein proteinToEnrich) {
        super.processProtein(proteinToEnrich);

        if(! proteinFetched.getXrefs().isEmpty()) {
            XrefUpdateMerger merger = new XrefUpdateMerger();
            merger.merge(proteinFetched.getXrefs() , proteinToEnrich.getXrefs());

            for(Xref xref: merger.getToAdd()){
                proteinToEnrich.getXrefs().add(xref);
                if(listener != null) listener.onAddedXref(proteinToEnrich, xref);
            }
        }
    }

    @Override
    public OrganismEnricher getOrganismEnricher() {
        if( organismEnricher == null ){
            organismEnricher = new MaximumOrganismEnricher();
            organismEnricher.setFetcher(new MockOrganismFetcher());
        }

        return organismEnricher;
    }


}
