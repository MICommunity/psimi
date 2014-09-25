package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.listener.InteractorChangeListener;
import psidev.psi.mi.jami.model.Interactor;

/**
 * Interface for interactor enricher listener
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public interface InteractorEnricherListener<T extends Interactor> extends InteractorChangeListener<T>, EnricherListener<T> {
}
