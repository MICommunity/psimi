package psidev.psi.mi.jami.model;

/**
 * Participant of a biological complex.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */
public interface Component extends Participant<Complex, Interactor, ComponentFeature>{

    /**
     * Sets the complex and add the new component to its list of components
     * @param interaction : complex
     */
    public void setComplexAndAddComponent(Complex interaction);
}
