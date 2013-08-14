package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * Experiment in which some interactions has been determined.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Experiment {

    public static String UNSPECIFIED_METHOD = "unspecified method";
    public static String UNSPECIFIED_METHOD_MI = "MI:0686";

    /**
     * The publication where the experiment has been described.
     * It can be null if the experiment is not attached to any publications.
     * This can happen when an experiment has been removed from a publication and is not valid anymore.
     * @return the publication
     */
    public Publication getPublication();

    /**
     * Set the publication where the experiment has been described.
     * @param publication : the publication
     */
    public void setPublication(Publication publication);

    /**
     * Set the publication where the experiment has been described and add the experiment to the list of experiments for this publication
     * If publication is null, it will remove this experiment from the previous publication attached to this experiment
     * @param publication : the publication
     */
    public void setPublicationAndAddExperiment(Publication publication);

    /**
     * Collection of cross references for an experiment which can give more information about the experiment.
     * It cannot be null and if the experiment does not have any xrefs, the method should return an empty Collection.
     * Ex: PRIDE experiment/project xrefs
     * @return the xrefs
     */
    public <X extends Xref> Collection<X> getXrefs();

    /**
     * Collection of annotations for an experiment.
     * It cannot be null. If the experiment does not have any annotations, the method should return an empty Collection.
     * Ex: data-processing, comments, cautions, confidence-mapping annotations
     * @return the annotations
     */
    public <A extends Annotation> Collection<A> getAnnotations();

    /**
     * Collection of confidences for a specific experiment. It can happen that an authors give different confidences to the same experiment depending on the environment.
     * It cannot be null. If the experiment does not have any confidences, the method should return an empty Collection.
     * @return the confidences for this experiment
     */
    public <C extends Confidence> Collection<C> getConfidences();

    /**
     * The experimental method to determine the interaction. It is a controlled vocabulary term and cannot not be null.
     * Ex: pull down, coip, ...
     * @return the interaction detection method
     */
    public CvTerm getInteractionDetectionMethod();

    /**
     * Set the interaction detection method for this experiment
     * If term is null, this method will set the interaction detection method to 'unspecified method' (MI:0686).
     * @param term : the detection method
     */
    public void setInteractionDetectionMethod(CvTerm term);

    /**
     * The host organism where the interaction took place in this experiment.
     * It can be null.
     * Ex: in vitro, human-hela cells
     * @return the host organism
     */
    public Organism getHostOrganism();

    /**
     * Sets the host organism of an experiment
     * @param organism : host organism
     */
    public void setHostOrganism(Organism organism);

    /**
     * The interactions determined in this experiment.
     * The collection cannot be null. If the experiment did not show any interactions, the method should return an empty collection
     * @return the interactions
     */
    public <I extends InteractionEvidence> Collection<I> getInteractionEvidences();

    /**
     * This method will add the interaction evidence and set the experiment of the new interaction evidence to this current experiment
     * @param evidence
     * @return true if interaction evidence is added to the list of interactions
     */
    public boolean  addInteractionEvidence(InteractionEvidence evidence);

    /**
     * This method will remove the interaction evidence and set the experiment of the new interaction evidence to null
     * @param evidence
     * @return true if interaction evidence is removed from the list of interactions
     */
    public boolean removeInteractionEvidence(InteractionEvidence evidence);

    /**
     * This method will add all the interaction evidences and set the experiment of the new interaction evidences to this current experiment
     * @param evidences
     * @return true if interaction evidences are added to the list of interaction evidences
     */
    public boolean  addAllInteractionEvidences(Collection<? extends InteractionEvidence> evidences);

    /**
     * This method will remove the interaction evidences and set the experiment of the removed interaction evidences to null.
     * @param evidences
     * @return true if interaction evidences are removed from the list of interactions
     */
    public boolean removeAllInteractionEvidences(Collection<? extends InteractionEvidence> evidences);

    /**
     * The collection of variableParameters and their values used in this experiment.
     * The collection cannot be null. If the experiment does not have any variableParameters,
     * this method should return an empty collection.
     * @return the collection of variableParameters and their values used in this experiment
     */
    public <V extends VariableParameter> Collection<V> getVariableParameters();

    /**
     * This method will add the variableParameter and set the experiment of the new variableParameter to this current experiment
     * @param variableParameter
     * @return true if variableParameter is added to the list of variableParameters
     */
    public boolean  addVariableParameter(VariableParameter variableParameter);

    /**
     * This method will remove the variableParameter and set the experiment of the removed variableParameter to null.
     * @param variableParameter
     * @return true if variableParameter is removed from the list of variableParameters
     */
    public boolean removeVariableParameter(VariableParameter variableParameter);

    /**
     * This method will add all variableParameters and set the experiment of the new variableParameters to this current experiment
     * @param variableParameters
     * @return true if variableParameters are added to the list of variableParameters
     */
    public boolean  addAllVariableParameters(Collection<? extends VariableParameter> variableParameters);

    /**
     * This method will remove all the variableParameters and set the experiment of the removed variableParameters to null.
     * @param variableParameters
     * @return true if variableParameters are removed from the list of variableParameters
     */
    public boolean removeAllVariableParameters(Collection<? extends VariableParameter> variableParameters);
}
