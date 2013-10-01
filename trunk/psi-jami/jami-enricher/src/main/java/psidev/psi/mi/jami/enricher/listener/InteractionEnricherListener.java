package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.listener.InteractionChangeListener;
import psidev.psi.mi.jami.model.Interaction;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public interface InteractionEnricherListener extends InteractionChangeListener, EnricherListener<Interaction> {

}
