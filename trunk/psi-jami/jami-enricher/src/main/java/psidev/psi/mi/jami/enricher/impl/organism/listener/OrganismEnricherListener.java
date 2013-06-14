package psidev.psi.mi.jami.enricher.impl.organism.listener;

import psidev.psi.mi.jami.listener.OrganismChangeListener;
import psidev.psi.mi.jami.model.Organism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 16:57
 */
public interface OrganismEnricherListener extends OrganismChangeListener{

    public void onOrganismEnriched(Organism organism , String status);
}
