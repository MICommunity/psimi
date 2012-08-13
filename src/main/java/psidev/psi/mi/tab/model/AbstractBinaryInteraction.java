/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representation of a binary interaction as in the MITAB25 format.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public abstract class AbstractBinaryInteraction<T extends Interactor> implements BinaryInteraction<T> {

    private static final long serialVersionUID = 5851048278405255668L;

    ///////////////////////
    // Instance variable

    /**
     *  MITAB 2.5
     */

    /**
     * Interactor A.
     */
    private T interactorA;

    /**
     * Interactor B.
     */
    private T interactorB;

    /**
     * Detection method for that interaction.
     */
    private List<CrossReference> detectionMethods
            = new ArrayList<CrossReference>();

    /**
     * Types of the interaction.
     */
    private List<CrossReference> interactionTypes
            = new ArrayList<CrossReference>();

    /**
     * Associated publications of that interaction.
     */
    private List<CrossReference> publications
            = new ArrayList<CrossReference>();

    /**
     * Associated confidence value for that interaction.
     */
    private List<Confidence> confidenceValues
            = new ArrayList<Confidence>();

    /**
     * Source databases.
     */
    private List<CrossReference> sourceDatabases
            = new ArrayList<CrossReference>();

    /**
     * Identifiers of the interaction that provided the data.
     */
    private List<CrossReference> interactionAcs
            = new ArrayList<CrossReference>();

    /**
     * First author surname(s) of the publication(s).
     */
    private List<Author> authors = new ArrayList<Author>();


    /**
     *  MITAB 2.6
     */

    /**
     * Model used to convert n-ary interactions into binary.
     */
    private List<CrossReference> complexExpansion
            = new ArrayList<CrossReference>();

    /**
     * Cross references associated to the interaction.
     */
    private List<CrossReference> interactionXrefs
            = new ArrayList<CrossReference>();

    /**
     * Annotations for the interaction.
     */
    private List<Annotation> interactionAnnotations
            = new ArrayList<Annotation>();

    /**
     * Organism where the interaction happens.
     */
    private Organism hostOrganism;

    /**
     * Parameters for the interaction.
     */
    private List<Parameter> interactionParameters
            = new ArrayList<Parameter>();

    /**
     * Creation Date
     */
    private List<Date> creationDate
            = new ArrayList<Date>();

    /**
     * Update Date
     */
    private List<Date> updateDate
            = new ArrayList<Date>();

    /**
     * Checksum for interaction.
     */
    private List<Checksum> interactionChecksums
            = new ArrayList<Checksum>();

    /**
     * Boolean value ti distinguish positive interactions and
     * negative interactions.
     */
    private boolean negativeInteraction;

    /**
     * MITAB 2.7
     */

    ///////////////////////
    // Constructors
    public AbstractBinaryInteraction() {

    }

    public AbstractBinaryInteraction(T interactor) {
        setInteractorA(interactor);
    }

    public AbstractBinaryInteraction(T interactorA, T interactorB) {
        setInteractorA(interactorA);
        setInteractorB(interactorB);
    }


    public void flip() {
        T interactorA = getInteractorA();
        T interactorB = getInteractorB();

        setInteractorA(interactorB);
        setInteractorB(interactorA);
    }

    ///////////////////////////
    // Getters & Setters

    /**
     * {@inheritDoc}
     */
    public T getInteractorA() {
        return interactorA;
    }

    /**
     * {@inheritDoc}
     */
    public void setInteractorA(T interactorA) {
        this.interactorA = interactorA;
    }

    /**
     * {@inheritDoc}
     */
    public T getInteractorB() {
        return interactorB;
    }

    /**
     * {@inheritDoc}
     */
    public void setInteractorB(T interactorB) {
        this.interactorB = interactorB;
    }

    /**
     * {@inheritDoc}
     */
    public List<CrossReference> getDetectionMethods() {
        return detectionMethods;
    }

    /**
     * {@inheritDoc}
     */
    public void setDetectionMethods(List<CrossReference> detectionMethods) {
        this.detectionMethods = detectionMethods;
    }

    /**
     * {@inheritDoc}
     */
    public List<CrossReference> getInteractionTypes() {
        return interactionTypes;
    }

    /**
     * {@inheritDoc}
     */
    public void setInteractionTypes(List<CrossReference> interactionTypes) {
        this.interactionTypes = interactionTypes;
    }

    /**
     * {@inheritDoc}
     */
    public List<CrossReference> getPublications() {
        return publications;
    }

    /**
     * {@inheritDoc}
     */
    public void setPublications(List<CrossReference> publications) {
        this.publications = publications;
    }

    /**
     * {@inheritDoc}
     */
    public List<Confidence> getConfidenceValues() {
        return confidenceValues;
    }

    /**
     * {@inheritDoc}
     */
    public void setConfidenceValues(List<Confidence> confidenceValues) {
        this.confidenceValues = confidenceValues;
    }

    /**
     * {@inheritDoc}
     */
    public List<Author> getAuthors() {
        return authors;
    }

    /**
     * {@inheritDoc}
     */
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    /**
     * {@inheritDoc}
     */
    public List<CrossReference> getSourceDatabases() {
        return sourceDatabases;
    }

    /**
     * {@inheritDoc}
     */
    public void setSourceDatabases(List<CrossReference> sourceDatabases) {
        this.sourceDatabases = sourceDatabases;
    }

    /**
     * {@inheritDoc}
     */
    public List<CrossReference> getInteractionAcs() {
        return interactionAcs;
    }

    /**
     * {@inheritDoc}
     */
    public void setInteractionAcs(List<CrossReference> interactionAcs) {
        this.interactionAcs = interactionAcs;
    }

    /**
     * MITAB 2.6
     */

    /**
     * {@inheritDoc}
     */
    public List<CrossReference> getComplexExpansion() {
        return complexExpansion;
    }

    /**
     * {@inheritDoc}
     */
    public void setComplexExpansion(List<CrossReference> complexExpansion) {
        this.complexExpansion = complexExpansion;
    }

    /**
     * {@inheritDoc}
     */
    public List<CrossReference> getInteractionXrefs() {
        return interactionXrefs;
    }

    /**
     * {@inheritDoc}
     */
    public void setXrefs(List<CrossReference> interactionXrefs) {
        this.interactionXrefs = interactionXrefs;
    }

    /**
     * {@inheritDoc}
     */
    public List<Annotation> getInteractionAnnotations() {
        return interactionAnnotations;
    }

    /**
     * {@inheritDoc}
     */
    public void setAnnotations(List<Annotation> interactionAnnotations) {
        this.interactionAnnotations = interactionAnnotations;
    }

    /**
     * {@inheritDoc}
     */
    public Organism getHostOrganism() {
        return hostOrganism;
    }

    /**
     * {@inheritDoc}
     */
    public void setHostOrganism(Organism hostOrganism) {
        this.hostOrganism = hostOrganism;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasHostOrganism() {
        return hostOrganism != null;
    }

    /**
     * {@inheritDoc}
     */
    public List<Parameter> getInteractionParameters() {
        return interactionParameters;
    }

    /**
     * {@inheritDoc}
     */
    public void setParameters(List<Parameter> interactionParameters) {
        this.interactionParameters = interactionParameters;
    }

    /**
     * {@inheritDoc}
     */
    public List<Date> getCreationDate() {
        return creationDate;
    }

    /**
     * {@inheritDoc}
     */
    public void setCreationDate(List<Date> creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * {@inheritDoc}
     */
    public List<Date> getUpdateDate() {
        return updateDate;
    }

    /**
     * {@inheritDoc}
     */
    public void setUpdateDate(List<Date> updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * {@inheritDoc}
     */
    public List<Checksum> getInteractionChecksums() {
        return interactionChecksums;
    }

    /**
     * {@inheritDoc}
     */
    public void setChecksums(List<Checksum> interactionChecksums) {
        this.interactionChecksums = interactionChecksums;
    }


    /**
     * {@inheritDoc}
     */
    public boolean isNegativeInteraction() {
        return negativeInteraction;
    }

    /**
     * {@inheritDoc}
     */
    public void setNegativeInteraction(Boolean negativeInteraction) {
        this.negativeInteraction = negativeInteraction;
    }


    /////////////////////////
    // Object's override

    //We need update the toString, equals and hash ?

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{interactorA=").append(interactorA);
        sb.append(", interactorB=").append(interactorB);
        sb.append(", detectionMethods=").append(detectionMethods);
        sb.append(", interactionTypes=").append(interactionTypes);
        sb.append(", authors=").append(authors);
        sb.append(", publications=").append(publications);
        sb.append(", confidenceValues=").append(confidenceValues);
        sb.append(", sourceDatabases=").append(sourceDatabases);
        sb.append(", interactionAcs=").append(interactionAcs);
        sb.append(", complexExpansion=").append(complexExpansion);
        sb.append(", xrefs=").append(interactionXrefs);
        sb.append(", annotations=").append(interactionAnnotations);
        sb.append(", hostOrganism=").append(hostOrganism);
        sb.append(", parameters=").append(interactionParameters);
        sb.append(", creationDate=").append(creationDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", checksums=").append(interactionChecksums);
        sb.append(", negative=").append(negativeInteraction);
        sb.append('}');
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        //TODO When two interactions are equals?

        AbstractBinaryInteraction that = (AbstractBinaryInteraction) o;


        if (detectionMethods != null ? !detectionMethods.equals(that.detectionMethods) : that.detectionMethods != null) {
            return false;
        }
        if (interactionTypes != null ? !interactionTypes.equals(that.interactionTypes) : that.interactionTypes != null) {
            return false;
        }


        // TODO update to (A == A' && B == B') || (B == A' && A == B')

        boolean part1 = (interactorA.equals(that.interactorA) && interactorB.equals(that.interactorB));
        boolean part2 = (interactorB.equals(that.interactorA) && interactorA.equals(that.interactorB));
        if (!(part1 || part2)) {
            return false;
        }

        if (publications != null ? !publications.equals(that.publications) : that.publications != null) {
            return false;
        }

        if (confidenceValues != null ? !confidenceValues.equals(that.confidenceValues) : that.confidenceValues != null) {
            return false;
        }

        if (sourceDatabases != null ? !sourceDatabases.equals(that.sourceDatabases) : that.sourceDatabases != null) {
            return false;
        }

        if (interactionAcs != null ? !interactionAcs.equals(that.interactionAcs) : that.interactionAcs != null) {
            return false;
        }

        if (authors != null ? !authors.equals(that.authors) : that.authors != null) {
            return false;
        }

        if (complexExpansion != null ? !complexExpansion.equals(that.complexExpansion) : that.complexExpansion != null) {
            return false;
        }

        if (interactionXrefs != null ? !interactionXrefs.equals(that.interactionXrefs) : that.interactionXrefs != null) {
            return false;
        }

        if (interactionAnnotations != null ? !interactionAnnotations.equals(that.interactionAnnotations) : that.interactionAnnotations != null) {
            return false;
        }

        if (hostOrganism != null ? !hostOrganism.equals(that.hostOrganism) : that.hostOrganism != null) {
            return false;
        }

        if (interactionParameters != null ? !interactionParameters.equals(that.interactionParameters) : that.interactionParameters != null) {
            return false;
        }

        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) {
            return false;
        }

        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) {
            return false;
        }

        if (interactionChecksums != null ? !interactionChecksums.equals(that.interactionChecksums) : that.interactionChecksums != null) {
            return false;
        }

        return negativeInteraction == that.negativeInteraction;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result;

        // Note: we want to reflect that (A == A' && B == B') || (B == A' && A == B')

        result = interactorA.hashCode() * interactorB.hashCode();
        result = 31 * result + (detectionMethods != null ? detectionMethods.hashCode() : 0);
        result = 31 * result + (interactionTypes != null ? interactionTypes.hashCode() : 0);
        result = 31 * result + (publications != null ? publications.hashCode() : 0);
        return result;
    }


}