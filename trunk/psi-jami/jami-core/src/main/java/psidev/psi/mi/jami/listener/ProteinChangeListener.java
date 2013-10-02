package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.Protein;

/**
 * This listener listen to Protein changes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public interface ProteinChangeListener extends InteractorChangeListener<Protein>{

    /**
     * Listen to the event where the sequence of a protein has been changed.
     * If oldSequence is null, it means that the sequence has been initialised.
     * If the sequence of the protein is null, it means that the sequence of the protein has been reset
     * @param protein
     * @param oldSequence
     */
    public void onSequenceUpdate(Protein protein, String oldSequence);
}
