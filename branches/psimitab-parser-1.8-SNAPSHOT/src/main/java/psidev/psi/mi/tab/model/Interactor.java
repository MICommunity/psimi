/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.ArrayList;

/**
 * Simple description of an Interactor.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public class Interactor implements Serializable {

    /**
     * Generated with IntelliJ plugin generateSerialVersionUID.
     * To keep things consistent, please use the same thing.
     */
    private static final long serialVersionUID = -4549381843138930910L;

    ///////////////////////
    // Instance variables

    /**
     * Primary identifiers of the interactor.
     */
    private Collection<CrossReference> identifiers
            = new ArrayList<CrossReference>();

    /**
     * Alternative identifiers of the interactor.
     */
    private Collection<CrossReference> alternativeIdentifiers
            = new ArrayList<CrossReference>();

    /**
     * Aliases of the interactor (ie. alternative names).
     */
    private Collection<Alias> aliases
            = new ArrayList<Alias>();

    /**
     * Organism the interactor belongs to.
     */
    private Organism organism;

    /**
     * Interactor's biological role.
     */
    private Collection<CrossReference> biologicalRoles
            = new ArrayList<CrossReference>();

    /**
     * Interactor's experimental role.
     */
    private Collection<CrossReference> experimentalRoles
            = new ArrayList<CrossReference>();

    /**
     * Type of the interactor.
     */
    private Collection<CrossReference> interactorTypes
            = new ArrayList<CrossReference>();

    /**
     * Cross references of the interactor.
     */
    private Collection<CrossReference> xrefs
            = new ArrayList<CrossReference>();

    /**
     * Annotations of the interactor.
     */
    private Collection<Annotation> annotations
            = new ArrayList<Annotation>();

    /**
     * Checksums of the interactor.
     */
    private Collection<Checksum> checksums
            = new ArrayList<Checksum>();

    /**
     * Features of the interactor.
     */
    private Collection<Feature> features
            = new ArrayList<Feature>();

    /**
     * Stoichiometry of the interactor.
     */
    private Collection<Integer> stoichiometry
            = new ArrayList<Integer>();

    /**
     * Participant identification method.
     */
    private Collection<ParticipantIdentificationMethod> participantIdentificationMethods
            = new ArrayList<ParticipantIdentificationMethod>();


    ///////////////////////////
    // Constructor

    public Interactor() {
    }

    public Interactor(Collection<CrossReference> identifiers) {
        if (identifiers == null) {
            throw new IllegalArgumentException("You must give a non null collection of identifiers.");
        }
        this.identifiers = identifiers;
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Getter for property 'identifiers'.
     *
     * @return Value for property 'identifiers'.
     */
    public Collection<CrossReference> getIdentifiers() {
        return identifiers;
    }

    /**
     * Setter for property 'identifiers'.
     *
     * @param identifiers Value to set for property 'identifiers'.
     */
    public void setIdentifiers(Collection<CrossReference> identifiers) {
        this.identifiers = identifiers;
    }

    /**
     * Getter for property 'alternativeIdentifiers'.
     *
     * @return Value for property 'alternativeIdentifiers'.
     */
    public Collection<CrossReference> getAlternativeIdentifiers() {
        return alternativeIdentifiers;
    }

    /**
     * Setter for property 'alternativeIdentifiers'.
     *
     * @param alternativeIdentifiers Value to set for property 'alternativeIdentifiers'.
     */
    public void setAlternativeIdentifiers(Collection<CrossReference> alternativeIdentifiers) {
        this.alternativeIdentifiers = alternativeIdentifiers;
    }

    /**
     * Getter for property 'aliases'.
     *
     * @return Value for property 'aliases'.
     */
    public Collection<Alias> getAliases() {
        return aliases;
    }

    /**
     * Setter for property 'aliases'.
     *
     * @param aliases Value to set for property 'aliases'.
     */
    public void setAliases(Collection<Alias> aliases) {
        this.aliases = aliases;
    }

    /**
     * Getter for property 'organism'.
     *
     * @return Value for property 'organisms'.
     */
    public Organism getOrganism() {
        return organism;
    }

    /**
     * Setter for property 'organism'.
     *
     * @param organism Value to set for property 'organisms'.
     */
    public void setOrganism(Organism organism) {
        this.organism = organism;
    }

    /**
     * Checks if the interactor has a organism associated.
     *
     * @return true if has a organism
     */
    public boolean hasOrganism() {
        return organism != null;
    }

    /**
     * Getter fot property 'biologicalRoles'.
     *
     * @return Value for property 'biologicalRoles'.
     */
    public Collection<CrossReference> getBiologicalRoles() {
        return biologicalRoles;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Setter for property 'biologicalRoles'.
     *
     * @param biologicalRoles Value to set for property 'biologicalRoles'.
     */
    public void setBiologicalRoles(Collection<CrossReference> biologicalRoles) {
        this.biologicalRoles = biologicalRoles;
    }

    /**
     * Getter fot property 'experimentalRoles'.
     *
     * @return Value for property 'experimentalRoles'.
     */
    public Collection<CrossReference> getExperimentalRoles() {
        return experimentalRoles;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Setter for property 'experimentalRoles'.
     *
     * @param experimentalRoles Value to set for property 'experimentalRoles'.
     */
    public void setExperimentalRoles(Collection<CrossReference> experimentalRoles) {
        this.experimentalRoles = experimentalRoles;
    }

    /**
     * Getter fot property 'interactorTypes'.
     *
     * @return Value for property 'interactorTypes'.
     */
    public Collection<CrossReference> getInteractorTypes() {
        return interactorTypes;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Setter for property 'interactorTypes'.
     *
     * @param interactorTypes Value to set for property 'interactorTypes'.
     */
    public void setInteractorTypes(Collection<CrossReference> interactorTypes) {
        this.interactorTypes = interactorTypes;
    }

    /**
     * Getter fot property 'xrefs'.
     *
     * @return Value for property 'xrefs'.
     */
    public Collection<CrossReference> getXrefs() {
        return xrefs;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Setter for property 'xrefs'.
     *
     * @param xrefs Value to set for property 'xrefs'.
     */
    public void setXrefs(Collection<CrossReference> xrefs) {
        this.xrefs = xrefs;
    }

    /**
     * Getter fot property 'annotations'.
     *
     * @return Value for property 'annotations'.
     */
    public Collection<Annotation> getAnnotations() {
        return annotations;
    }

    /**
     * Setter for property 'annotations'.
     *
     * @param annotations Value to set for property 'annotations'.
     */
    public void setAnnotations(Collection<Annotation> annotations) {
        this.annotations = annotations;
    }

    /**
     * Getter fot property 'checksum'.
     *
     * @return Value for property 'checksums'.
     */
    public Collection<Checksum> getChecksums() {
        return checksums;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Setter for property 'checksums'.
     *
     * @param checksums Value to set for property 'checksums'.
     */
    public void setChecksums(Collection<Checksum> checksums) {
        this.checksums = checksums;
    }

    /**
     * Getter fot property 'features'.
     *
     * @return Value for property 'features'.
     */
    public Collection<Feature> getFeatures() {
        return features;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Setter for property 'features'.
     *
     * @param features Value to set for property 'features'.
     */
    public void setFeatures(Collection<Feature> features) {
        this.features = features;
    }

    /**
     * Getter fot property 'stoichiometry'.
     *
     * @return Value for property 'stoichiometry'.
     */
    public Collection<Integer> getStoichiometry() {
        return stoichiometry;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Setter for property 'stoichiometry'.
     *
     * @param stoichiometry Value to set for property 'stoichiometry'.
     */
    public void setStoichiometry(Collection<Integer> stoichiometry) {
        this.stoichiometry = stoichiometry;
    }

    /**
     * Getter fot property 'participants'.
     *
     * @return Value for property 'participants'.
     */
    public Collection<ParticipantIdentificationMethod> getParticipantIdentificationMethods() {
        return participantIdentificationMethods;
    }

    /**
     * Setter for property 'participant'.
     *
     * @param participantIdentificationMethods
     *         Value to set for property 'participant'.
     */
    public void setParticipantIdentificationMethods(Collection<ParticipantIdentificationMethod> participantIdentificationMethods) {
        this.participantIdentificationMethods = participantIdentificationMethods;
    }

    /////////////////////////////
    // Object's override

    //TODO Update the toString, equals and hash
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{identifiers=").append(identifiers);
        sb.append(", alternativeIdentifiers=").append(alternativeIdentifiers);
        sb.append(", aliases=").append(aliases);
        sb.append(", organism=").append(organism);
        sb.append(", biologicalRoles=").append(biologicalRoles);
        sb.append(", experimentalRoles=").append(experimentalRoles);
        sb.append(", interactorTypes=").append(interactorTypes);
        sb.append(", xrefs=").append(xrefs);
        sb.append(", annotations=").append(annotations);
        sb.append(", checksums=").append(checksums);
        sb.append(", features=").append(features);
        sb.append(", stoichiometry=").append(stoichiometry);
        sb.append(", participantIdentificationMethods=").append(participantIdentificationMethods);
        sb.append('}');
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        //TODO When two interactions are equals?

        Interactor that = (Interactor) o;

        if (identifiers != null ? !identifiers.equals(that.identifiers) : that.identifiers != null) return false;
        if (organism != null ? !organism.equals(that.organism) : that.organism != null) return false;
//        if (alternativeIdentifiers != null ? !alternativeIdentifiers.equals(that.alternativeIdentifiers) : that.alternativeIdentifiers != null) return false;
//        if (aliases != null ? !aliases.equals(that.aliases) : that.aliases != null) return false;
//        if (biologicalRoles != null ? !biologicalRoles.equals(that.biologicalRoles) : that.biologicalRoles != null) return false;
//        if (experimentalRoles != null ? !experimentalRoles.equals(that.experimentalRoles) : that.experimentalRoles != null) return false;
//        if (interactorTypes != null ? !interactorTypes.equals(that.interactorTypes) : that.interactorTypes != null) return false;
//        if (xrefs != null ? !xrefs.equals(that.xrefs) : that.xrefs != null) return false;
//        if (annotations != null ? !annotations.equals(that.annotations) : that.annotations != null) return false;
//        if (checksums != null ? !checksums.equals(that.checksums) : that.checksums != null) return false;
//        if (features != null ? !features.equals(that.features) : that.features != null) return false;
//        if (stoichiometry != null ? !stoichiometry.equals(that.stoichiometry) : that.stoichiometry != null) return false;
//        if (participantIdentificationMethods != null ? !participantIdentificationMethods.equals(that.participantIdentificationMethods) : that.participantIdentificationMethods != null) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result;
        result = (identifiers != null ? identifiers.hashCode() : 0);
        result = 31 * result + (organism != null ? organism.hashCode() : 0);
        return result;
    }
}