package psidev.psi.mi.enricher.proteinenricher;

import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 14:27
 */
public abstract class AbstractProteinEnricher {

    protected Protein getEnrichedForm(Protein MasterProtein){
        return null;
    }
}
