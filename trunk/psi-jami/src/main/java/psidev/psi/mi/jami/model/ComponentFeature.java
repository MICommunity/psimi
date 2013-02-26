package psidev.psi.mi.jami.model;

/**
 * 	Biological property of a component that may be involved with or interfere with the binding of a molecule in a complex.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public interface ComponentFeature extends Feature<ComponentFeature>{

    /**
     * The participant to which the feature is attached.
     * It can be null if the feature is not attached to any participants.
     * @return the participant
     */
    public Component getComponent();

    /**
     * Sets the participant.
     * @param participant : participant
     */
    public void setComponent(Component participant);

    /**
     * Sets the participant and add this feature to its list of features
     * @param participant : participant
     */
    public void setComponentAndAddFeature(Component participant);
}
