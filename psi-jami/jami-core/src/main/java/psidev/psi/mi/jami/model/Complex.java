package psidev.psi.mi.jami.model;

/**
 * An interactor composed of interacting molecules that can be copurified.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Complex extends Interactor, ModelledInteraction, NamedInteraction<ModelledParticipant> {

    public static final String COMPLEX="complex";
    public static final String COMPLEX_MI="MI:0314";

    /**
     * The physical properties for this complex.
     * It is a shortcut which should point to the first complex-properties annotation in the collection of annotations.
     * Example: Molecular mass = 154 kDa
     * @return
     */
    public String getPhysicalProperties();

    /**
     * Sets the physical properties of this complex.
     * It will remove the old complex-properties annotation from the collection of annotations and replace it
     * with the new complex-properties annotation. If the new complex-properties is null, all the existing complex-properties annotations will be removed from the
     * collection of annotations
     * @param properties
     */
    public void setPhysicalProperties(String properties);

    /**
     * The recommended name of a complex.
     * It is a shortcut which should point to the first complex recommended name alias in the collection of aliases.
     * @return
     */
    public String getRecommendedName();

    /**
     * Sets the recommended name of this complex.
     * It will remove the old recommended name from the collection of aliases and replace it
     * with the new recommended name. If the new recommended name is null, all the existing recommended names will be removed from the
     * collection of aliases
     * @param name
     */
    public void setRecommendedName(String name);

    /**
     * The systematic name of a complex.
     * It is a shortcut which should point to the first complex systematic name alias in the collection of aliases.
     * @return
     */
    public String getSystematicName();

    /**
     * Sets the systematic name of this complex.
     * It will remove the old systematic name from the collection of aliases and replace it
     * with the new systematic name. If the new systematic name is null, all the existing systematic names will be removed from the
     * collection of aliases
     * @param name
     */
    public void setSystematicName(String name);
}
