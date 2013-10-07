package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Interactor;

import java.util.EventListener;

/**
 * Listeners for changes in participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */

public interface ParticipantInteractorChangeListener extends EventListener{

   public void onInteractorUpdate(Entity entity, Interactor oldInteractor);
}
