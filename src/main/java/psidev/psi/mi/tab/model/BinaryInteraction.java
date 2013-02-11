package psidev.psi.mi.tab.model;

import psidev.psi.mi.jami.model.ExperimentalInteraction;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * TODO comment that class header
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since specify the maven artifact version
 */
public interface BinaryInteraction<T extends Interactor> extends ExperimentalInteraction, Flippable, Serializable {

    /**
     * Returns interactor A.
     *
     * @return interactor A.
     */
    T getInteractorA();

    /**
     * Sets interactor A.
     *
     * @param interactorA interactor A.
     */
    void setInteractorA(T interactorA);

    /**
     * Returns interactor B.
     *
     * @return interactor B.
     */
    T getInteractorB();

    /**
     * Sets interactor B.
     *
     * @param interactorB interactor B.
     */
    void setInteractorB(T interactorB);

    /**
     * Returns detection method for that interaction.
     *
     * @return detection method for that interaction.
     */
    List<CrossReference> getDetectionMethods();

    /**
     * Sets detection method for that interaction.
     *
     * @param detectionMethods detection method for that interaction.
     */
    void setDetectionMethods(List<CrossReference> detectionMethods);

    /**
     * Returns types of the interaction
     *
     * @return types of the interaction
     */
    List<CrossReference> getInteractionTypes();

    /**
     * Sets types of the interaction
     *
     * @param interactionTypes types of the interaction
     */
    void setInteractionTypes(List<CrossReference> interactionTypes);

    /**
     * Returns associated publications of that interaction.
     *
     * @return associated publications of that interaction.
     */
    List<CrossReference> getPublications();

    /**
     * Sets associated publications of that interaction.
     *
     * @param publications associated publications of that interaction.
     */
    void setPublications(List<CrossReference> publications);

    /**
     * Returns associated confidence value for that interaction.
     *
     * @return associated confidence value for that interaction.
     */
    List<Confidence> getConfidenceValues();

    /**
     * Sets associated confidence value for that interaction.
     *
     * @param confidenceValues associated confidence value for that interaction.
     */
    void setConfidenceValues(List<Confidence> confidenceValues);

    /**
     * Getter for property 'authors'.
     *
     * @return Value for property 'authors'.
     */
    List<Author> getAuthors();

    /**
     * Setter for property 'authors'.
     *
     * @param authors Value to set for property 'authors'.
     */
    void setAuthors(List<Author> authors);

    /**
     * Getter for property 'sourceDatabases'.
     *
     * @return Value for property 'sourceDatabases'.
     */
    List<CrossReference> getSourceDatabases();

    /**
     * Setter for property 'sourceDatabases'.
     *
     * @param sourceDatabases Value to set for property 'sourceDatabases'.
     */
    void setSourceDatabases(List<CrossReference> sourceDatabases);

    /**
     * Getter for property 'interactionAcs'.
     *
     * @return Value for property 'interactionAcs'.
     */
    List<CrossReference> getInteractionAcs();

    /**
     * Setter for property 'interactionAcs'.
     *
     * @param interactionAcs Value to set for property 'interactionAcs'.
     */
    void setInteractionAcs(List<CrossReference> interactionAcs);

    //MITAB 2.6

    /**
     * Returns the type of complex expansion for the interaction.
     *
     * @return type of complex expansion.
     */
    List<CrossReference> getComplexExpansion();

    /**
     * Sets the type of complex expansion for the interaction.
     *
     * @param complexExpansion for the interaction.
     */
    void setComplexExpansion(List<CrossReference> complexExpansion);

    /**
     * Returns the cross references for the interaction.
     *
     * @return cross references for the interaction.
     */
    List<CrossReference> getMitabXrefs();

    /**
     * Sets the cross references for the interaction.
     *
     * @param xrefs for the interaction.
     */
    void setMitabXrefs(List<CrossReference> xrefs);

    /**
     * Returns the annotations for the interaction.
     *
     * @return annotations for the interaction.
     */
    List<Annotation> getMitabAnnotations();

    /**
     * Sets the annotations for the interaction.
     *
     * @param interactionAnnotations for the interaction.
     */
    void setMitabAnnotations(List<Annotation> interactionAnnotations);

    /**
     * Returns the host organism for the interaction.
     *
     * @return host organism for the interaction.
     */
    Organism getHostOrganism();

    /**
     * Sets the host organism for the interaction.
     *
     * @param hostOrganism for the interaction.
     */
    void setHostOrganism(Organism hostOrganism);

    /**
     * Checks if the interaction has a host organism associated.
     *
     * @return the boolean with the result.
     */
    boolean hasHostOrganism();

    /**
     * Returns the parameters for the interaction.
     *
     * @return parameters for the interaction.
     */
    List<Parameter> getMitabParameters();

    /**
     * Sets the parameters for the interaction.
     *
     * @param parameters for the interaction.
     */
    void setMitabParameters(List<Parameter> parameters);

    /**
     * Returns the date when the curation started.
     *
     * @return date when the curation started.
     */
    List<Date> getCreationDate();

    /**
     * Sets the date when the curation started.
     *
     * @param creationDate date when the curation started.
     */
    void setCreationDate(List<Date> creationDate);

    /**
     * Returns the date when the interaction was updated for the last time.
     *
     * @return date when the interaction was updated for the last time.
     */
    List<Date> getUpdateDate();

    /**
     * Sets the date when the interaction was updated for the last time.
     *
     * @param updateDate when the interaction was updated for the last time.
     */
    void setUpdateDate(List<Date> updateDate);

    /**
     * Returns the checksums for the interaction.
     *
     * @return checksums for the interaction.
     */
    List<Checksum> getMitabChecksums();

    /**
     * Sets the checksum for the interaction.
     *
     * @param interactionChecksums checksum for the interaction.
     */
    void setMitabChecksums(List<Checksum> interactionChecksums);

    /**
     * Returns if the interaction is negative (true) or
     * positive (false)
     *
     * @return if the interaction is negative or not.
     */
    boolean isNegativeInteraction();

    /**
     * Sets if the interaction is negative (true) or
     * postive (false)
     *
     * @param negativeInteraction true if it is negative or false if it is true.
     */
    void setNegativeInteraction(Boolean negativeInteraction);

    //MITAB 2.7


}
