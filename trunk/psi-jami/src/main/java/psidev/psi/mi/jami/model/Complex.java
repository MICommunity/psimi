package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * An interactor composed of interacting molecules that can be copurified.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Complex extends Interactor {

    public static final String COMPLEX="complex";
    public static final String COMPLEX_MI="MI:0314";

    /**
     * The interaction evidences used to determine the complex.
     * The collection cannot be null. If the complex does not have any interaction evidences, the method should return an empty collection
     * @return the collection of experiments
     */
    public Collection<InteractionEvidence> getInteractionEvidences();

    /**
     * The components for this complex.
     * The collection cannot be null. If the complex does not have any components, the method should return an empty collection.
     * @return the components of a complex
     */
    public Collection<? extends Component> getComponents();

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
     * This method will add the participant and set the interaction of the new participant to this current interaction
     * @param part
     * @return true if participant is added to the list of participants
     */
    public boolean  addComponent(Component part);

    /**
     * This method will remove the participant and set the interaction of the removed participant to null.
     * @param part
     * @return true if participant is removed from the list of participants
     */
    public boolean removeComponent(Component part);

    /**
     * This method will add all participants and set the interaction of the new participants to this current interaction
     * @param part
     * @return true if participants are added to the list of participants
     */
    public boolean  addAllComponents(Collection<? extends Component> part);

    /**
     * This method will remove all the participant and set the interaction of the removed participants to null.
     * @param part
     * @return true if participants are removed from the list of participants
     */
    public boolean removeAllComponents(Collection<? extends Component> part);

    /**
     * The confidences in this interaction.
     * The Collection cannot be null. If the interaction does not have any confidences, the method should return an empty Collection.
     * Ex: author based scores, statistical confidences, ...
     * @return the confidences
     */
    public Collection<ModelledConfidence> getConfidences();

    /**
     * Collection of numerical parameters for this interaction.
     * The set cannot be null. If the interaction does not have any parameters, the method should return an empty Collection.
     * Ex: IC50, ...
     * @return the parameters
     */
    public Collection<ModelledParameter> getParameters();

    /**
     * The collection of publications/reviews associated with this complex.
     * The collection cannot be null. If the complex does not refer to any publications, this method shoudl return an empty collection.
     * @return
     */
    public Collection<Xref> getPublications();
}
