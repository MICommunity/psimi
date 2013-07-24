package psidev.psi.mi.jami.enricher.impl.protein;


import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.impl.organism.OrganismUpdaterMaximum;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.enricher.util.XrefUpdateMerger;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;


/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 20/05/13
 */
public class ProteinUpdaterMaximum
        extends ProteinUpdaterMinimum
        implements ProteinEnricher {



    @Override
    protected void processProtein(Protein proteinToEnrich){
        super.processProtein(proteinToEnrich);

        if(! proteinFetched.getXrefs().isEmpty()) {
            XrefUpdateMerger merger = new XrefUpdateMerger();
            merger.merge(proteinFetched.getXrefs() , proteinToEnrich.getXrefs() , false);
            for(Xref xref: merger.getToRemove()){
                proteinToEnrich.getXrefs().remove(xref);
                if(listener != null) listener.onRemovedXref(proteinToEnrich , xref);
            }
            for(Xref xref: merger.getToAdd()){
                proteinToEnrich.getXrefs().add(xref);
                if(listener != null) listener.onAddedXref(proteinToEnrich, xref);
            }
        }
    }

    /*
    @Override
    public OrganismEnricher getOrganismEnricher() {
        if( organismEnricher == null ){
            organismEnricher = new OrganismUpdaterMaximum();
        }

        return organismEnricher;
    }*/
}
