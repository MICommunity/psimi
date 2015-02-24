package psidev.psi.mi.jami.enricher.listener;

import psidev.psi.mi.jami.listener.PolymerChangeListener;
import psidev.psi.mi.jami.model.Polymer;

/**
 * Interface for polymer enricher listener
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public interface PolymerEnricherListener<T extends Polymer> extends PolymerChangeListener<T>, EnricherListener<T> {
}
