package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.listener.InteractorPoolChangeListener;
import psidev.psi.mi.jami.model.InteractorPool;

/**
 * Interface for interactor pool enricher listener
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public interface InteractorPoolEnricherListener extends InteractorPoolChangeListener, EnricherListener<InteractorPool> {
}
