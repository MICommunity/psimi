package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.Interaction;

import java.util.EventListener;

/**
 * Listener for changes in interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public interface InteractionChangeListener extends EventListener {
    
    public void onUpdatedRigid(Interaction interaction, String oldRigid);
}
