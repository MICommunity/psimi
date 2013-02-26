package psidev.psi.mi.jami.model;

/**
 * Participant of a biological complex.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */
public interface Component extends Participant<Interactor, ComponentFeature>{

    /**
     * Sets the complex and add the new component to its list of components
     * @param interaction : complex
     */
    public void setComplexAndAddComponent(Complex interaction);

    /**
     * The interaction in which the participant is involved.
     * It can be null if the participant is not attached to any interactions. It can happen if the participant has been removed from an interaction and is now invalid.
     * @return the interaction
     */
    public Complex getComplex();

    /**
     * Sets the interaction.
     * @param interaction : complex
     */
    public void setComplex(Complex interaction);
}
