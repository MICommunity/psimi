package psidev.psi.mi.jami.enricher.impl.protein;

import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.impl.organism.MaximumOrganismEnricher;
import psidev.psi.mi.jami.enricher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.enricher.util.CollectionManipulationUtils;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;

import java.util.Collection;

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

        //Xref
        Collection<Xref> subtractedXrefs = CollectionManipulationUtils.comparatorSubtract(
                proteinFetched.getXrefs(),
                proteinToEnrich.getXrefs(),
                new DefaultXrefComparator());
        for(Xref xref: subtractedXrefs){
            proteinToEnrich.getXrefs().add(xref);
            if(listener != null) listener.onAddedXref(proteinFetched, xref);
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
