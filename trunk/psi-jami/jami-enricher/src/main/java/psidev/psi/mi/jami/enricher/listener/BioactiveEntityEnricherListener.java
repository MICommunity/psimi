package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.listener.BioactiveEntityChangeListener;
import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public interface BioactiveEntityEnricherListener
        extends InteractorEnricherListener<BioactiveEntity>, BioactiveEntityChangeListener {

}
