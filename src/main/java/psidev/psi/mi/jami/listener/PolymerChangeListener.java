package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.Polymer;

/**
 * Listener for changes in polymer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/01/14</pre>
 */

public interface PolymerChangeListener<P extends Polymer> extends InteractorChangeListener<P> {
    /**
     * Listen to the event where the sequence of a protein has been changed.
     * If oldSequence is null, it means that the sequence has been initialised.
     * If the sequence of the protein is null, it means that the sequence of the protein has been reset
     * @param protein : updated polymer
     * @param oldSequence : old sequence
     */
    public void onSequenceUpdate(P protein, String oldSequence);
}
