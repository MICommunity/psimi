/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;


import org.apache.commons.collections.CollectionUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExperimentalParticipant;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultExperimentalInteraction;
import psidev.psi.mi.jami.model.impl.DefaultSource;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;

import java.util.*;

/**
 * Representation of a binary interaction as in the MITAB25 format.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public abstract class AbstractBinaryInteraction<T extends Interactor> extends DefaultExperimentalInteraction implements BinaryInteraction<T> {

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
     * Types of the interaction.
     */
    private List<CrossReference> interactionTypes
            = new InteractionTypesList();

    /**
     * Associated confidence value for that interaction.
     */
    private List<Confidence> confidenceValues
            = new InteractionMitabConfidenceList();

    /**
     * Source databases.
     */
    private List<CrossReference> sourceDatabases
            = new InteractionSourcesList();

    /**
     * Identifiers of the interaction that provided the data.
     */
    private List<CrossReference> interactionAcs
            = new InteractionMitabIdentifiersList();

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
     * Parameters for the interaction.
     */
    private List<Parameter> interactionParameters
            = new ArrayList<Parameter>();

    /**
     * Checksum for interaction.
     */
    private List<Checksum> interactionChecksums
            = new ArrayList<Checksum>();

    /**
     * Update Date
     */
    private List<Date> updateDate
            = new ArrayList<Date>();

    /**
     * Creation Date
     */
    private List<Date> creationDate
            = new ArrayList<Date>();


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

    @Override
    protected void initializeConfidences(){
        this.confidences = new BinaryInteractionConfidenceList();
    }

    @Override
    protected void initializeIdentifiers(){
        this.identifiers = new BinaryInteractionIdentifiersList();
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
    public List<CrossReference> getInteractionTypes() {
        return interactionTypes;
    }

    /**
     * {@inheritDoc}
     */
    public void setInteractionTypes(List<CrossReference> interactionTypes) {
        this.interactionTypes.clear();
        if (interactionTypes != null) {
            this.interactionTypes.addAll(interactionTypes);
        }
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
        this.confidenceValues.clear();
        if (confidenceValues != null) {
            this.confidenceValues.addAll(confidenceValues);
        }
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
	public List<CrossReference> getMitabXrefs() {
		return interactionXrefs;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMitabXrefs(List<CrossReference> xrefs) {
		this.interactionXrefs = xrefs;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Annotation> getMitabAnnotations() {
		return interactionAnnotations;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMitabAnnotations(List<Annotation> interactionAnnotations) {
		this.interactionAnnotations = interactionAnnotations;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Parameter> getMitabParameters() {
		return interactionParameters;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMitabParameters(List<Parameter> parameters) {
		this.interactionParameters = parameters;
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
	public List<Checksum> getChecksums() {
		return interactionChecksums;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMitabChecksums(List<Checksum> interactionChecksums) {
		this.interactionChecksums = interactionChecksums;
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

    // experiment

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

    // publication

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
	public boolean isNegativeInteraction() {
		return isNegative;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setNegativeInteraction(Boolean negativeInteraction) {
        if (negativeInteraction == null){
           this.isNegative = false;
        }
        else {
            this.isNegative = negativeInteraction;
        }
	}

    protected void resetInteractionTypeNameFromMiReferences(){
        if (!interactionTypes.isEmpty()){
            Xref ref = XrefUtils.collectFirstIdentifierWithDatabase(new ArrayList<Xref>(interactionTypes), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);

            if (ref != null){
                String name = ref.getQualifier() != null ? ref.getQualifier().getShortName() : "unknown";
                type.setShortName(name);
                type.setFullName(name);
            }
        }
    }

    protected void resetInteractionTypeNameFromFirstReferences(){
        if (!interactionTypes.isEmpty()){
            Iterator<CrossReference> methodsIterator = interactionTypes.iterator();
            String name = null;

            while (name == null && methodsIterator.hasNext()){
                CrossReference ref = methodsIterator.next();

                if (ref.getText() != null){
                    name = ref.getText();
                }
            }

            type.setShortName(name != null ? name : "unknown");
            type.setFullName(name != null ? name : "unknown");
        }
    }

    protected void resetSourceNameFromMiReferences(){
        if (!sourceDatabases.isEmpty()){
            Xref ref = XrefUtils.collectFirstIdentifierWithDatabase(new ArrayList<Xref>(sourceDatabases), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);

            if (ref != null){
                String name = ref.getQualifier() != null ? ref.getQualifier().getShortName() : "unknown";
                source.setShortName(name);
                source.setFullName(name);
            }
        }
    }

    protected void resetSourceNameFromFirstReferences(){
        if (!sourceDatabases.isEmpty()){
            Iterator<CrossReference> methodsIterator = sourceDatabases.iterator();
            String name = null;

            while (name == null && methodsIterator.hasNext()){
                CrossReference ref = methodsIterator.next();

                if (ref.getText() != null){
                    name = ref.getText();
                }
            }

            source.setShortName(name != null ? name : "unknown");
            source.setFullName(name != null ? name : "unknown");
        }
    }

	/////////////////////////
	// Object's override

    @Override
    public Collection<ExperimentalParticipant> getParticipants() {

        if (interactorA != null && interactorB != null){
            return Arrays.asList((ExperimentalParticipant) interactorA, (ExperimentalParticipant) interactorB);
        }
        else if (interactorA != null){
            return Arrays.asList((ExperimentalParticipant) interactorA);
        }
        else if (interactorB != null){
            return Arrays.asList((ExperimentalParticipant) interactorB);
        }
        else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public void setType(CvTerm type) {
        super.setType(type);
        processNewInteractionTypesList(type);
    }

    private void processNewInteractionTypesList(CvTerm type) {
        if (type.getMIIdentifier() != null){
            ((InteractionTypesList)interactionTypes).addOnly(new CrossReferenceImpl(CvTerm.PSI_MI, type.getMIIdentifier(), type.getFullName() != null ? type.getFullName() : type.getShortName()));
        }
        else{
            if (!type.getIdentifiers().isEmpty()){
                Xref ref = type.getIdentifiers().iterator().next();
                ((InteractionTypesList)interactionTypes).addOnly(new CrossReferenceImpl(ref.getDatabase().getShortName(), ref.getId(), type.getFullName() != null ? type.getFullName() : type.getShortName()));
            }
            else {
                ((InteractionTypesList)interactionTypes).addOnly(new CrossReferenceImpl("unknown", "-", type.getFullName() != null ? type.getFullName() : type.getShortName()));
            }
        }
    }

    @Override
    public void setSource(Source source) {
        super.setSource(source);
        processNewSourceDatabasesList(source);
    }

    private void processNewSourceDatabasesList(Source source) {
        if (source.getMIIdentifier() != null){
            ((InteractionSourcesList)sourceDatabases).addOnly(new CrossReferenceImpl(CvTerm.PSI_MI, source.getMIIdentifier(), source.getFullName() != null ? source.getFullName() : source.getShortName()));
        }
        else{
            if (!source.getIdentifiers().isEmpty()){
                Xref ref = source.getIdentifiers().iterator().next();
                ((InteractionSourcesList)sourceDatabases).addOnly(new CrossReferenceImpl(ref.getDatabase().getShortName(), ref.getId(), source.getFullName() != null ? source.getFullName() : source.getShortName()));
            }
            else {
                ((InteractionSourcesList)sourceDatabases).addOnly(new CrossReferenceImpl("unknown", "-", source.getFullName() != null ? source.getFullName() : source.getShortName()));
            }
        }
    }

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
		sb.append(", negative=").append(isNegative);
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
		//interactorA or interactorB can be null
		if (interactorA == null) {
			if (that.interactorA != null && that.interactorB != null) {
				return false;
			} else if (that.interactorA == null) {
				//We check interactor B, This shouldn't be null (because then both interactor should be null)
				if (!interactorB.equals(that.interactorB)) {
					return false;
				}

			} else if (that.interactorB == null) {
				if (!interactorB.equals(that.interactorA)) {
					return false;
				}
			}
		} else if (interactorB == null) {
			if (that.interactorB != null && that.interactorA != null) {
				return false;
			} else if (that.interactorB == null) {
				//We check interactor A, This shouldn't be null (because then both interactor should be null)
				if (!interactorA.equals(that.interactorA)) {
					return false;
				}
			} else if ((interactorB == null && that.interactorA == null)) {
				if (!interactorA.equals(that.interactorB)) {
					return false;
				}
			}
		} else if (interactorA != null && interactorB != null) {
			boolean part1 = (interactorA.equals(that.interactorA) && interactorB.equals(that.interactorB));
			boolean part2 = (interactorB.equals(that.interactorA) && interactorA.equals(that.interactorB));

			if (!(part1 || part2)) {
				return false;
			}
		}


		if (publications != null ? !CollectionUtils.isEqualCollection(publications, that.publications) : that.publications != null) {
			return false;
		}

		if (confidenceValues != null ? !CollectionUtils.isEqualCollection(confidenceValues, that.confidenceValues) : that.confidenceValues != null) {
			return false;
		}

		if (sourceDatabases != null ? !CollectionUtils.isEqualCollection(sourceDatabases, that.sourceDatabases) : that.sourceDatabases != null) {
			return false;
		}

		if (interactionAcs != null ? !CollectionUtils.isEqualCollection(interactionAcs, that.interactionAcs) : that.interactionAcs != null) {
			return false;
		}

		if (authors != null ? !CollectionUtils.isEqualCollection(authors, that.authors) : that.authors != null) {
			return false;
		}

		if (complexExpansion != null ? !CollectionUtils.isEqualCollection(complexExpansion, that.complexExpansion) : that.complexExpansion != null) {
			return false;
		}

		if (interactionXrefs != null ? !CollectionUtils.isEqualCollection(interactionXrefs, that.interactionXrefs) : that.interactionXrefs != null) {
			return false;
		}

		if (interactionAnnotations != null ? !CollectionUtils.isEqualCollection(interactionAnnotations, that.interactionAnnotations) : that.interactionAnnotations != null) {
			return false;
		}

		if (hostOrganism != null ? !hostOrganism.equals(that.hostOrganism) : that.hostOrganism != null) {
			return false;
		}

		if (interactionParameters != null ? !CollectionUtils.isEqualCollection(interactionParameters, that.interactionParameters) : that.interactionParameters != null) {
			return false;
		}

		if (creationDate != null ? !CollectionUtils.isEqualCollection(creationDate, that.creationDate) : that.creationDate != null) {
			return false;
		}

		if (updateDate != null ? !CollectionUtils.isEqualCollection(updateDate, that.updateDate) : that.updateDate != null) {
			return false;
		}

		if (interactionChecksums != null ? !CollectionUtils.isEqualCollection(interactionChecksums, that.interactionChecksums) : that.interactionChecksums != null) {
			return false;
		}

		return isNegative == that.isNegative;

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
		result = 31 * result + (confidenceValues != null ? confidenceValues.hashCode() : 0);
		result = 31 * result + (sourceDatabases != null ? sourceDatabases.hashCode() : 0);
		result = 31 * result + (interactionAcs != null ? interactionAcs.hashCode() : 0);
		result = 31 * result + (authors != null ? authors.hashCode() : 0);
		result = 31 * result + (sourceDatabases != null ? sourceDatabases.hashCode() : 0);
		result = 31 * result + (complexExpansion != null ? complexExpansion.hashCode() : 0);
		result = 31 * result + (interactionXrefs != null ? interactionXrefs.hashCode() : 0);
		result = 31 * result + (interactionAnnotations != null ? interactionAnnotations.hashCode() : 0);
		result = 31 * result + (hostOrganism != null ? hostOrganism.hashCode() : 0);
		result = 31 * result + (interactionParameters != null ? interactionParameters.hashCode() : 0);
		result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
		result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
		result = 31 * result + (interactionChecksums != null ? interactionChecksums.hashCode() : 0);


		return result;
	}

    protected class InteractionTypesList extends AbstractListHavingPoperties<CrossReference> {
        public InteractionTypesList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            if (type == null){
                String name = added.getText() != null ? added.getText() : "unknown";
                type = new DefaultCvTerm(name, name, added);
            }
            else {
                type.getXrefs().add(added);
                // reset shortname
                if (type.getMIIdentifier() != null && type.getMIIdentifier().equals(added.getId())){
                    String name = added.getText();

                    if (name != null){
                        type.setShortName(name);
                    }
                    else {
                        resetInteractionTypeNameFromMiReferences();
                        if (type.getShortName().equals("unknown")){
                            resetInteractionTypeNameFromFirstReferences();
                        }
                    }
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            if (type != null){
                type.getXrefs().remove(removed);

                if (removed.getText() != null && type.getShortName().equals(removed.getText())){
                    if (type.getMIIdentifier() != null){
                        resetInteractionTypeNameFromMiReferences();
                        if (type.getShortName().equals("unknown")){
                            resetInteractionTypeNameFromFirstReferences();
                        }
                    }
                    else {
                        resetInteractionTypeNameFromFirstReferences();
                    }
                }
            }

            if (isEmpty()){
                type = null;
            }
        }

        @Override
        protected void clearProperties() {
            type = null;
        }
    }

    protected class BinaryInteractionConfidenceList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Confidence> {
        public BinaryInteractionConfidenceList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Confidence added) {
            if (added instanceof Confidence){
                ((InteractionMitabConfidenceList)confidenceValues).addOnly((Confidence) added);
            }
            else {
                ((InteractionMitabConfidenceList)confidenceValues).addOnly(new ConfidenceImpl(added.getType().getShortName(), added.getValue(), added.getUnit() != null ? added.getUnit().getShortName() : null));
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Confidence removed) {
            if (removed instanceof Confidence){
                ((InteractionMitabConfidenceList)confidenceValues).removeOnly(removed);
            }
            else {
                ((InteractionMitabConfidenceList)confidenceValues).removeOnly(new ConfidenceImpl(removed.getType().getShortName(), removed.getValue(), removed.getUnit() != null ? removed.getUnit().getShortName() : null));
            }
        }

        @Override
        protected void clearProperties() {
            // clear all confidences
            ((InteractionMitabConfidenceList)confidenceValues).clearOnly();
        }
    }

    protected class InteractionMitabConfidenceList extends AbstractListHavingPoperties<Confidence> {
        public InteractionMitabConfidenceList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Confidence added) {

            // we added a confidence, needs to add it in confidences
            ((BinaryInteractionConfidenceList)confidences).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Confidence removed) {

            // we removed a Confidence, needs to remove it in confidences
            ((BinaryInteractionConfidenceList)confidences).removeOnly(removed);

        }

        @Override
        protected void clearProperties() {
            // clear all confidences
            ((BinaryInteractionConfidenceList)confidences).clearOnly();
        }
    }

    protected class InteractionSourcesList extends AbstractListHavingPoperties<CrossReference> {
        public InteractionSourcesList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            if (source == null){
                String name = added.getText() != null ? added.getText() : "unknown";
                source = new DefaultSource(name, name, added);
            }
            else {
                source.getXrefs().add(added);
                // reset shortname
                if (source.getMIIdentifier() != null && source.getMIIdentifier().equals(added.getId())){
                    String name = added.getText();

                    if (name != null){
                        source.setShortName(name);
                    }
                    else {
                        resetSourceNameFromMiReferences();
                        if (source.getShortName().equals("unknown")){
                            resetSourceNameFromFirstReferences();
                        }
                    }
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            if (source != null){
                source.getXrefs().remove(removed);

                if (removed.getText() != null && source.getShortName().equals(removed.getText())){
                    if (source.getMIIdentifier() != null){
                        resetSourceNameFromMiReferences();
                        if (source.getShortName().equals("unknown")){
                            resetSourceNameFromFirstReferences();
                        }
                    }
                    else {
                        resetSourceNameFromFirstReferences();
                    }
                }
            }

            if (isEmpty()){
                source = null;
            }
        }

        @Override
        protected void clearProperties() {
            source = null;
        }
    }

    protected class BinaryInteractionIdentifiersList extends AbstractListHavingPoperties<Xref> {
        public BinaryInteractionIdentifiersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Xref added) {
            if (added instanceof CrossReference){
                ((InteractionMitabIdentifiersList)interactionAcs).addOnly((CrossReference) added);
            }
            else {
                ((InteractionMitabIdentifiersList)interactionAcs).addOnly(new CrossReferenceImpl(added.getDatabase().getShortName(), added.getId(), added.getQualifier()!= null ? added.getQualifier().getShortName() : null));
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {
            if (removed instanceof CrossReference){
                ((InteractionMitabIdentifiersList)interactionAcs).removeOnly(removed);
            }
            else {
                ((InteractionMitabIdentifiersList)interactionAcs).removeOnly(new CrossReferenceImpl(removed.getDatabase().getShortName(), removed.getId(), removed.getQualifier()!= null ? removed.getQualifier().getShortName() : null));
            }
        }

        @Override
        protected void clearProperties() {
            // clear all confidences
            ((InteractionMitabIdentifiersList)interactionAcs).clearOnly();
        }
    }

    protected class InteractionMitabIdentifiersList extends AbstractListHavingPoperties<CrossReference> {
        public InteractionMitabIdentifiersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            // we added a confidence, needs to add it in confidences
            ((BinaryInteractionIdentifiersList)identifiers).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            // we removed a Confidence, needs to remove it in confidences
            ((BinaryInteractionIdentifiersList)identifiers).removeOnly(removed);

        }

        @Override
        protected void clearProperties() {
            // clear all confidences
            ((BinaryInteractionIdentifiersList)identifiers).clearOnly();
        }
    }
}
