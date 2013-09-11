package psidev.psi.mi.jami.enricher.listener;


import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.listener.ProteinChangeListener;
import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  10/06/13
 */
public interface ProteinEnricherListener
        extends ProteinChangeListener , EnricherListener<Protein> {

}
