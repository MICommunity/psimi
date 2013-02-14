package psidev.psi.mi.jami.model;

/**
 *  Participant of a modelled interaction.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public interface ModelledParticipant extends Participant<ModelledInteraction, Interactor, ModelledFeature>{

    /**
     * Sets the complex and add the new component to its list of components
     * @param interaction : modelled interaction
     */
    public void setModelledInteractionAndAddModelledParticipant(ModelledInteraction interaction);
}
