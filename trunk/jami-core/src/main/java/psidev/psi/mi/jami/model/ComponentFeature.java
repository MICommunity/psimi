package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * 	Biological property of a component that may be involved with or interfere with the binding of a molecule in a complex.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public interface ComponentFeature extends Feature{

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

    /**
     * The other features that can bind to this feature.
     * The collection cannot be null. If the feature does not bind with any other features, the method should return an empty collection
     * @return the binding features
     */
    public Collection<? extends ComponentFeature> getBindingSites();

    /**
     * This method will add the feature as a binding site
     * @param feature
     * @return true if feature is added to the list of features
     */
    public boolean addBindingSite(ComponentFeature feature);

    /**
     * This method will remove the feature from the binding sites.
     * @param feature
     * @return true if feature is removed from the list of features
     */
    public boolean removeBindingSite(ComponentFeature feature);

    /**
     * This method will add all features as binding sites
     * @param features
     * @return true if features are added to the list of features
     */
    public boolean addAllBindingSites(Collection<? extends ComponentFeature> features);

    /**
     * This method will remove all the features from binding sites
     * @param features
     * @return true if features are removed from the list of features
     */
    public boolean removeAllBindingSites(Collection<? extends ComponentFeature> features);
}
