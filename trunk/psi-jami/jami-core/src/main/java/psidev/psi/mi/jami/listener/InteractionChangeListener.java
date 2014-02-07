package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

import java.util.Date;

/**
 * Listener for changes in interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public interface InteractionChangeListener<I extends Interaction> extends AnnotationsChangeListener<I>, IdentifiersChangeListener<I>,
                       XrefsChangeListener<I>, ChecksumsChangeListener<I>{

    public void onShortNameUpdate(I interaction, String oldName);

    public void onUpdatedDateUpdate(I interaction, Date oldUpdate);

    public void onCreatedDateUpdate(I interaction, Date oldCreated);

    public void onInteractionTypeUpdate(I interaction, CvTerm oldType);

    public void onAddedParticipant(I interaction, Participant addedParticipant);

    public void onRemovedParticipant(I interaction, Participant removedParticipant);
}
