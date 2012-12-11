package psidev.psi.mi.jami.model;

import java.util.Set;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/12/12</pre>
 */

public interface ExperimentalParticipant extends Participant<Interaction,Interactor,ExperimentalFeature>{


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
     * @param expRole : experimental role
     * @throws IllegalArgumentException when expRole is null
     */
    public void setExperimentalRole(CvTerm expRole);

    /**
     * Set of identification methods for this participant.
     * Each identification method is a controlled vocabulary term.
     * The set cannot be null. If the participant does not have any identification methods, it should return an empty set.
     * Ex: western blot, immunostaining, ...
     * @return the participant identification methods
     */
    public Set<CvTerm> getIdentificationMethods();

    /**
     * The experimental preparations for this participant.
     * Each experimental preparation is a controlled vocabulary term.
     * The set cannot be null. If the participant does not have any experimental preparations, the method should return an empty set.
     * Ex: engineered, cDNA library, ...
     * @return the experimental preparations.
     */
    public Set<CvTerm> getExperimentalPreparations();

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
     * The set cannot be null. If the participant does not have any confidences, the method should return an empty set.
     * Ex: author based scores, statistical confidences, ...
     * @return the confidences
     */
    public Set<Confidence> getConfidences();
}
