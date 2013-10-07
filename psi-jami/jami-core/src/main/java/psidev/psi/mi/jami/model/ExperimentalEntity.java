package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * Experimental entity having experimental features
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/10/13</pre>
 */

public interface ExperimentalEntity extends Entity<FeatureEvidence> {
    /**
     * The experimental role of the participant.
     * It is a controlled vocabulary term and cannot be null.
     * It the experimental role role of a participant is not known or not relevant, the method should return
     * unspecified role (MI:0499)
     * Ex: bait, prey, ...
     * @return the experimental role
     */
    public CvTerm getExperimentalRole();

    /**
     * Sets the experimental role.
     * If expRole is null, it should create a unspecified role (MI:0499)
     * @param expRole : experimental role
     */
    public void setExperimentalRole(CvTerm expRole);

    /**
     * The identification methods for this participant.
     * Each identification method is a controlled vocabulary term.
     * The collection cannot be null. If the participant does not have any identification methods, this method should return an empty collection
     * Ex: western blot, immunostaining, ...
     * @return the participant identification method
     */
    public <C extends CvTerm> Collection<C> getIdentificationMethods();

    /**
     * The experimental preparations for this participant.
     * Each experimental preparation is a controlled vocabulary term.
     * The Collection cannot be null. If the participant does not have any experimental preparations, the method should return an empty Collection.
     * Ex: engineered, cDNA library, ...
     * @return the experimental preparations.
     */
    public <C extends CvTerm> Collection<C> getExperimentalPreparations();

    /**
     * The organisms in which the participant has been expressed.
     * It can be null if not relevant or same as the original source organism of the interactor
     * Ex: human-hela cells, ...
     * @return the expressed in organism
     */
    public Organism getExpressedInOrganism();

    /**
     * Sets the expressed in organism
     * @param organism: expressed in organism
     */
    public void setExpressedInOrganism(Organism organism);

    /**
     * The confidences for this participant.
     * The Collection cannot be null. If the participant does not have any confidences, the method should return an empty Collection.
     * Ex: author based scores, statistical confidences, ...
     * @return the confidences
     */
    public <C extends Confidence> Collection<C> getConfidences();

    /**
     * Numerical parameters associated with this participant.
     * The Collection cannot be null. If the participant does not have any parameters, the method should return an empty Collection.
     * @return the parameters
     */
    public <P extends Parameter> Collection<P> getParameters();
}
