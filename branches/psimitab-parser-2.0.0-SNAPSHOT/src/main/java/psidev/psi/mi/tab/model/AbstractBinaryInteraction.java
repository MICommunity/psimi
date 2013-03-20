/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;
import psidev.psi.mi.jami.utils.ParameterUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.clone.ExperimentCloner;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Representation of a binary interaction as in the MITAB25 format.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public abstract class AbstractBinaryInteraction<T extends Interactor> extends DefaultInteractionEvidence implements BinaryInteraction<T>, FileSourceContext {

	private static final long serialVersionUID = 5851048278405255668L;

    /**
     * Sets up a logger for that class.
     */
    Log log = LogFactory.getLog(AbstractBinaryInteraction.class);

	///////////////////////
	// Instance variable

	/**
	 *  MITAB 2.5
	 */

	/**
	 * Interactor A.
	 */
	protected T interactorA;

	/**
	 * Interactor B.
	 */
	protected T interactorB;

    private MitabExperiment mitabExperiment;

    /**
     * Types of the interaction.
     */
    private List<CrossReference> interactionTypes;


    /**
     * Associated confidence value for that interaction.
     */
    private List<Confidence> confidenceValues;


    /**
     * Identifiers of the interaction that provided the data.
     */
    private List<CrossReference> interactionAcs;


	/**
	 *  MITAB 2.6
	 */

	/**
	 * Model used to convert n-ary interactions into binary.
	 */
	private List<CrossReference> complexExpansion;


	/**
	 * Cross references associated to the interaction.
	 */
	private List<CrossReference> interactionXrefs;


	/**
	 * Annotations for the interaction.
	 */
	private List<Annotation> interactionAnnotations;


    /**
     * Parameters for the interaction.
     */
    private List<Parameter> interactionParameters;


    /**
     * Checksum for interaction.
     */
    private List<Checksum> interactionChecksums;


    /**
     * Update Date
     */
    private List<Date> updateDate;


    /**
     * Creation Date
     */
    private List<Date> creationDate;

    private int lineNumber;

	/**
	 * MITAB 2.7
	 */

	///////////////////////
	// Constructors
	public AbstractBinaryInteraction() {
        super();
        setExperimentAndAddInteractionEvidence(new MitabExperiment());
	}

	public AbstractBinaryInteraction(T interactor) {
        super();
		setInteractorA(interactor);
        setExperimentAndAddInteractionEvidence(new MitabExperiment());

    }

	public AbstractBinaryInteraction(T interactorA, T interactorB) {
        super();
		setInteractorA(interactorA);
		setInteractorB(interactorB);
        setExperimentAndAddInteractionEvidence(new MitabExperiment());
    }

    @Override
    protected void initialiseExperimentalConfidences(){
        initialiseExperimentalConfidencesWith(new BinaryInteractionConfidenceList());
    }

    @Override
    protected void initialiseIdentifiers(){
        initialiseIdentifiersWith(new BinaryInteractionIdentifiersList());
    }

    @Override
    protected void initialiseXrefs() {
        initialiseXrefsWith(new BinaryInteractionXrefList());
    }

    @Override
    protected void initialiseAnnotations() {
        initialiseAnnotationsWith(new BinaryInteractionAnnotationList());
    }

    @Override
    protected void initialiseExperimentalParameters(){
        initialiseExperimentalParametersWith(new BinaryInteractionParametersList());
    }

    @Override
    protected void initialiseChecksum(){
        initialiseChecksumWith(new BinaryInteractionChecksumList());
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
        if (interactionTypes == null){
            interactionTypes = new InteractionTypesList();
        }
        return interactionTypes;
    }

    /**
     * {@inheritDoc}
     */
    public void setInteractionTypes(List<CrossReference> interactionTypes) {
        getInteractionTypes().clear();
        if (interactionTypes != null) {
            this.interactionTypes.addAll(interactionTypes);
        }
    }


    /**
     * {@inheritDoc}
     */
    public List<Confidence> getConfidenceValues() {
        if (confidenceValues == null){
            confidenceValues = new InteractionMitabConfidenceList();
        }
        return confidenceValues;
    }

    /**
     * {@inheritDoc}
     */
    public void setConfidenceValues(List<Confidence> confidenceValues) {
        getConfidenceValues().clear();
        if (confidenceValues != null) {
            this.confidenceValues.addAll(confidenceValues);
        }
    }

	/**
	 * {@inheritDoc}
	 */
	public List<CrossReference> getSourceDatabases() {
		return mitabExperiment.getMitabPublication().getSourceDatabases();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSourceDatabases(List<CrossReference> sourceDatabases) {
        mitabExperiment.getMitabPublication().setSourceDatabases(sourceDatabases);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<CrossReference> getInteractionAcs() {
        if (interactionAcs == null){
            interactionAcs = new InteractionMitabIdentifiersList();
        }
		return interactionAcs;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setInteractionAcs(List<CrossReference> interactionAcs) {
        getInteractionAcs().clear();
        if (interactionAcs != null) {
            this.interactionAcs.addAll(interactionAcs);
        }
	}

	/**
	 * MITAB 2.6
	 */

	/**
	 * {@inheritDoc}
	 */
	public List<CrossReference> getComplexExpansion() {
        if (complexExpansion == null){
            complexExpansion = new ComplexExpansionList();
        }
		return complexExpansion;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setComplexExpansion(List<CrossReference> complexExpansion) {
        getComplexExpansion().clear();
        if (complexExpansion != null && !complexExpansion.isEmpty()){
            this.complexExpansion.addAll(complexExpansion);
        }
	}

	/**
	 * {@inheritDoc}
	 */
	public List<CrossReference> getInteractionXrefs() {
        if (interactionXrefs == null){
            interactionXrefs = new InteractionMitabXrefsList();
        }
		return interactionXrefs;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setXrefs(List<CrossReference> xrefs) {
        getInteractionXrefs().clear();
        if (xrefs != null) {
            this.interactionXrefs.addAll(xrefs);
        }
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Annotation> getInteractionAnnotations() {
        if (interactionAnnotations == null){
            interactionAnnotations = new InteractionMitabAnnotationList();
        }
		return interactionAnnotations;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAnnotations(List<Annotation> interactionAnnotations) {
        getInteractionAnnotations().clear();
        if (interactionAnnotations != null) {
            this.interactionAnnotations.addAll(interactionAnnotations);
        }
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Parameter> getParameters() {
        if (interactionParameters == null){
            interactionParameters = new InteractionMitabParametersList();
        }
		return interactionParameters;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setParameters(List<Parameter> parameters) {
        getParameters().clear();
        if (parameters != null) {
            this.interactionParameters.addAll(parameters);
        }
	}

    /**
     * {@inheritDoc}
     */
    public List<Checksum> getInteractionChecksums() {
        if (interactionChecksums == null){
            interactionChecksums = new InteractionMitabChecksumList();
        }
        return interactionChecksums;
    }

    /**
     * {@inheritDoc}
     */
    public void setChecksums(List<Checksum> interactionChecksums) {
        getInteractionChecksums().clear();
        if (interactionChecksums != null) {
            this.interactionChecksums.addAll(interactionChecksums);
        }
    }

	/**
	 * {@inheritDoc}
	 */
	public List<Date> getUpdateDate() {
        if (updateDate == null){
            updateDate = new MitabUpdateDateList();
        }
		return updateDate;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setUpdateDate(List<Date> updateDate) {
        getUpdateDate().clear();
        if (updateDate != null) {
            this.updateDate.addAll(updateDate);
        }
	}

    /**
     * {@inheritDoc}
     */
    public List<Date> getCreationDate() {
        if (creationDate == null){
            creationDate = new MitabCreationDateList();
        }
        return creationDate;
    }

    /**
     * {@inheritDoc}
     */
    public void setCreationDate(List<Date> creationDate) {
        getCreationDate().clear();
        if (creationDate != null) {
            this.creationDate.addAll(creationDate);
        }
    }

    // experiment

    /**
     * {@inheritDoc}
     */
    public List<CrossReference> getDetectionMethods() {
        return mitabExperiment.getDetectionMethods();
    }

    /**
     * {@inheritDoc}
     */
    public void setDetectionMethods(List<CrossReference> detectionMethods) {
        mitabExperiment.setDetectionMethods(detectionMethods);
    }

    /**
     * {@inheritDoc}
     */
    public Organism getHostOrganism() {
        return mitabExperiment.getHostOrganism();
    }

    /**
     * {@inheritDoc}
     */
    public void setHostOrganism(Organism hostOrganism) {
        mitabExperiment.setHostOrganism(hostOrganism);
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasHostOrganism() {
        return mitabExperiment.hasHostOrganism();
    }

    // publication

    /**
     * {@inheritDoc}
     */
    public List<CrossReference> getPublications() {
        return mitabExperiment.getMitabPublication().getPublications();
    }

    /**
     * {@inheritDoc}
     */
    public void setPublications(List<CrossReference> publications) {
        mitabExperiment.getMitabPublication().setPublications(publications);
    }

    /**
     * {@inheritDoc}
     */
    public List<Author> getAuthors() {
        return mitabExperiment.getMitabPublication().getMitabAuthors();
    }

    /**
     * {@inheritDoc}
     */
    public void setAuthors(List<Author> authors) {
        mitabExperiment.getMitabPublication().setMitabAuthors(authors);
    }

	/**
	 * {@inheritDoc}
	 */
	public boolean isNegativeInteraction() {
		return isNegative();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setNegativeInteraction(Boolean negativeInteraction) {
        if (negativeInteraction == null){
           setNegative(false);
        }
        else {
            setNegative(negativeInteraction);
        }
	}

    protected void resetInteractionTypeNameFromMiReferences(){
        if (!getInteractionTypes().isEmpty()){
            Xref ref = XrefUtils.collectFirstIdentifierWithDatabase(new ArrayList<Xref>(interactionTypes), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);

            if (ref != null){
                String name = ref.getQualifier() != null ? ref.getQualifier().getShortName() : "unknown";
                getType().setShortName(name);
                getType().setFullName(name);
            }
        }
    }

    protected void resetInteractionTypeNameFromFirstReferences(){
        if (!getInteractionTypes().isEmpty()){
            Iterator<CrossReference> methodsIterator = interactionTypes.iterator();
            String name = null;

            while (name == null && methodsIterator.hasNext()){
                CrossReference ref = methodsIterator.next();

                if (ref.getText() != null){
                    name = ref.getText();
                }
            }

            getType().setShortName(name != null ? name : "unknown");
            getType().setFullName(name != null ? name : "unknown");
        }
    }

	/////////////////////////
	// Object's override

    @Override
    public void setExperiment(psidev.psi.mi.jami.model.Experiment experiment) {
        if (experiment == null){
            super.setExperiment(null);
            this.mitabExperiment = null;
        }
        else if (experiment instanceof MitabExperiment){
            super.setExperiment(experiment);
            this.mitabExperiment = (MitabExperiment) experiment;
        }
        else {
            MitabExperiment convertedExperiment = new MitabExperiment();

            ExperimentCloner.copyAndOverrideExperimentProperties(experiment, convertedExperiment);
            mitabExperiment = convertedExperiment;
            super.setExperiment(experiment);
        }
    }

    @Override
    public void setExperimentAndAddInteractionEvidence(Experiment experiment) {
        if (experiment == null){
            super.setExperiment(null);
            this.mitabExperiment = null;
        }
        else if (experiment instanceof MitabExperiment){
            super.setExperiment(experiment);
            this.mitabExperiment = (MitabExperiment) experiment;
            experiment.getInteractionEvidences().add(this);
        }
        else {
            MitabExperiment convertedExperiment = new MitabExperiment();

            ExperimentCloner.copyAndOverrideExperimentProperties(experiment, convertedExperiment);
            mitabExperiment = convertedExperiment;
            super.setExperiment(experiment);
            experiment.getInteractionEvidences().add(this);
        }
    }

    @Override
    public void setCreatedDate(Date created) {
        ((MitabCreationDateList)getCreationDate()).clearOnly();
        super.setCreatedDate(created);
        creationDate.add(created);
    }

    protected void setCreatedDateOnly(Date created) {
        super.setCreatedDate(created);
    }

    @Override
    public void setUpdatedDate(Date updated) {
        ((MitabUpdateDateList)getUpdateDate()).clearOnly();
        super.setUpdatedDate(updated);
        updateDate.add(updated);
    }

    public void setUpdatedDateOnly(Date updated) {
        super.setUpdatedDate(updated);
    }

    @Override
    public void setType(CvTerm type) {
        super.setType(type);
        processNewInteractionTypesList(type);
    }

    protected void setTypeOnly(CvTerm type) {
        super.setType(type);
    }

    private void processNewInteractionTypesList(CvTerm type) {
        ((InteractionTypesList)getInteractionTypes()).clearOnly();
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

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getColumnNumber() {
        return 0;
    }

    public int getId(){
        return 0;
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
		sb.append(", detectionMethods=").append(getDetectionMethods());
		sb.append(", interactionTypes=").append(interactionTypes);
		sb.append(", authors=").append(getAuthors());
		sb.append(", publications=").append(getPublications());
		sb.append(", confidenceValues=").append(confidenceValues);
		sb.append(", sourceDatabases=").append(getSourceDatabases());
		sb.append(", interactionAcs=").append(interactionAcs);
		sb.append(", complexExpansion=").append(complexExpansion);
		sb.append(", xrefs=").append(interactionXrefs);
		sb.append(", annotations=").append(interactionAnnotations);
		sb.append(", hostOrganism=").append(hasHostOrganism() ? getHostOrganism() : "-");
		sb.append(", parameters=").append(interactionParameters);
		sb.append(", creationDate=").append(creationDate);
		sb.append(", updateDate=").append(updateDate);
		sb.append(", checksums=").append(interactionChecksums);
		sb.append(", negative=").append(isNegative());
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


		if (getDetectionMethods() != null ? !getDetectionMethods().equals(that.getDetectionMethods()) : that.getDetectionMethods() != null) {
			return false;
		}
		if (interactionTypes != null ? !interactionTypes.equals(that.interactionTypes) : (that.interactionTypes != null && !that.interactionTypes.isEmpty())) {
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


		if (getPublications() != null ? !CollectionUtils.isEqualCollection(getPublications(), that.getPublications()) : that.getPublications() != null) {
			return false;
		}

		if (confidenceValues != null ? !CollectionUtils.isEqualCollection(confidenceValues, that.getConfidenceValues()) : (that.confidenceValues != null && !that.confidenceValues.isEmpty())) {
			return false;
		}

		if (getSourceDatabases() != null ? !CollectionUtils.isEqualCollection(getSourceDatabases(), that.getSourceDatabases()) : that.getSourceDatabases() != null) {
			return false;
		}

		if (interactionAcs != null ? !CollectionUtils.isEqualCollection(interactionAcs, that.getInteractionAcs()) : (that.interactionAcs != null && !that.interactionAcs.isEmpty())) {
			return false;
		}

		if (getAuthors() != null ? !CollectionUtils.isEqualCollection(getAuthors(), that.getAuthors()) : that.getAuthors() != null) {
			return false;
		}

		if (complexExpansion != null ? !CollectionUtils.isEqualCollection(complexExpansion, that.getComplexExpansion()) : (that.complexExpansion != null && !that.complexExpansion.isEmpty())) {
			return false;
		}

		if (interactionXrefs != null ? !CollectionUtils.isEqualCollection(interactionXrefs, that.getInteractionXrefs()) : (that.interactionXrefs != null && !that.interactionXrefs.isEmpty())) {
			return false;
		}

		if (interactionAnnotations != null ? !CollectionUtils.isEqualCollection(interactionAnnotations, that.getInteractionAnnotations()) : (that.interactionAnnotations != null && !that.interactionAnnotations.isEmpty())) {
			return false;
		}

		if (getHostOrganism() != null ? !getHostOrganism().equals(that.getHostOrganism()) : that.getHostOrganism() != null) {
			return false;
		}

		if (interactionParameters != null ? !CollectionUtils.isEqualCollection(interactionParameters, that.getParameters()) : (that.interactionParameters != null && !that.interactionParameters.isEmpty())) {
			return false;
		}

		if (creationDate != null ? !CollectionUtils.isEqualCollection(creationDate, that.getCreationDate()) : (that.creationDate != null && !that.creationDate.isEmpty())) {
			return false;
		}

		if (updateDate != null ? !CollectionUtils.isEqualCollection(updateDate, that.getUpdateDate()) : (that.updateDate != null && !that.updateDate.isEmpty())) {
			return false;
		}

		if (interactionChecksums != null ? !CollectionUtils.isEqualCollection(interactionChecksums, that.getInteractionChecksums()) : (that.interactionChecksums != null && !that.interactionChecksums.isEmpty())) {
			return false;
		}

		return isNegative() == that.isNegative();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int result;

		// Note: we want to reflect that (A == A' && B == B') || (B == A' && A == B')

		result = interactorA.hashCode() * interactorB.hashCode();
		result = 31 * result + (getDetectionMethods() != null ? getDetectionMethods().hashCode() : 0);
		result = 31 * result + (interactionTypes != null ? interactionTypes.hashCode() : 0);
		result = 31 * result + (getPublications() != null ? getPublications().hashCode() : 0);
		result = 31 * result + (confidenceValues != null ? confidenceValues.hashCode() : 0);
		result = 31 * result + (getSourceDatabases() != null ? getSourceDatabases().hashCode() : 0);
		result = 31 * result + (interactionAcs != null ? interactionAcs.hashCode() : 0);
		result = 31 * result + (getAuthors() != null ? getAuthors().hashCode() : 0);
		result = 31 * result + (getSourceDatabases() != null ? getSourceDatabases().hashCode() : 0);
		result = 31 * result + (complexExpansion != null ? complexExpansion.hashCode() : 0);
		result = 31 * result + (interactionXrefs != null ? interactionXrefs.hashCode() : 0);
		result = 31 * result + (interactionAnnotations != null ? interactionAnnotations.hashCode() : 0);
		result = 31 * result + (getHostOrganism() != null ? getHostOrganism().hashCode() : 0);
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

            if (getType() == null){
                String name = added.getText() != null ? added.getText() : "unknown";
                setTypeOnly(new DefaultCvTerm(name, name, added));
            }
            else {
                getType().getXrefs().add(added);
                // reset shortname
                if (getType().getMIIdentifier() != null && getType().getMIIdentifier().equals(added.getId())){
                    String name = added.getText();

                    if (name != null){
                        getType().setShortName(name);
                    }
                    else {
                        resetInteractionTypeNameFromMiReferences();
                        if (getType().getShortName().equals("unknown")){
                            resetInteractionTypeNameFromFirstReferences();
                        }
                    }
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            if (getType() != null){
                getType().getXrefs().remove(removed);

                if (removed.getText() != null && getType().getShortName().equals(removed.getText())){
                    if (getType().getMIIdentifier() != null){
                        resetInteractionTypeNameFromMiReferences();
                        if (getType().getShortName().equals("unknown")){
                            resetInteractionTypeNameFromFirstReferences();
                        }
                    }
                    else {
                        resetInteractionTypeNameFromFirstReferences();
                    }
                }
            }

            if (isEmpty()){
                setTypeOnly(null);
            }
        }

        @Override
        protected void clearProperties() {
            setTypeOnly(null);
        }
    }

    protected class BinaryInteractionConfidenceList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Confidence> {
        public BinaryInteractionConfidenceList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Confidence added) {
            if (added instanceof Confidence){
                ((InteractionMitabConfidenceList)getConfidenceValues()).addOnly((Confidence) added);
            }
            else {
                ((InteractionMitabConfidenceList)getConfidenceValues()).addOnly(new ConfidenceImpl(added.getType().getShortName(), added.getValue(), added.getUnit() != null ? added.getUnit().getShortName() : null));
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Confidence removed) {
            if (removed instanceof Confidence){
                ((InteractionMitabConfidenceList)getConfidenceValues()).removeOnly(removed);
            }
            else {
                ((InteractionMitabConfidenceList)getConfidenceValues()).removeOnly(new ConfidenceImpl(removed.getType().getShortName(), removed.getValue(), removed.getUnit() != null ? removed.getUnit().getShortName() : null));
            }
        }

        @Override
        protected void clearProperties() {
            // clear all confidences
            ((InteractionMitabConfidenceList)getConfidenceValues()).clearOnly();
        }
    }

    protected class InteractionMitabConfidenceList extends AbstractListHavingPoperties<Confidence> {
        public InteractionMitabConfidenceList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Confidence added) {

            // we added a confidence, needs to add it in confidences
            ((BinaryInteractionConfidenceList)getExperimentalConfidences()).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Confidence removed) {

            // we removed a Confidence, needs to remove it in confidences
            ((BinaryInteractionConfidenceList)getExperimentalConfidences()).removeOnly(removed);

        }

        @Override
        protected void clearProperties() {
            // clear all confidences
            ((BinaryInteractionConfidenceList)getExperimentalConfidences()).clearOnly();
        }
    }

    protected class BinaryInteractionIdentifiersList extends AbstractListHavingPoperties<Xref> {
        public BinaryInteractionIdentifiersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Xref added) {
            if (added instanceof CrossReference){
                ((InteractionMitabIdentifiersList)getInteractionAcs()).addOnly((CrossReference) added);
            }
            else {
                ((InteractionMitabIdentifiersList)getInteractionAcs()).addOnly(new CrossReferenceImpl(added.getDatabase().getShortName(), added.getId(), added.getQualifier()!= null ? added.getQualifier().getShortName() : null));
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {
            if (removed instanceof CrossReference){
                ((InteractionMitabIdentifiersList)getInteractionAcs()).removeOnly(removed);
            }
            else {
                ((InteractionMitabIdentifiersList)getInteractionAcs()).removeOnly(new CrossReferenceImpl(removed.getDatabase().getShortName(), removed.getId(), removed.getQualifier()!= null ? removed.getQualifier().getShortName() : null));
            }
        }

        @Override
        protected void clearProperties() {
            // clear all confidences
            ((InteractionMitabIdentifiersList)getInteractionAcs()).clearOnly();
        }
    }

    protected class InteractionMitabIdentifiersList extends AbstractListHavingPoperties<CrossReference> {
        public InteractionMitabIdentifiersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            // imex
            if (added.getDatabase().getShortName().toLowerCase().trim().equals(Xref.IMEX) && getImexId() == null){
                assignImexId(added.getId());
            }
            else {
                ((BinaryInteractionIdentifiersList)getIdentifiers()).addOnly(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            // imex
            if (removed.getDatabase().getShortName().toLowerCase().trim().equals(Xref.IMEX) && getImexId() != null && getImexId().equals(removed.getId())){
                getXrefs().remove(removed);
            }
            else {
                ((BinaryInteractionIdentifiersList)getIdentifiers()).removeOnly(removed);
            }
        }

        @Override
        protected void clearProperties() {

            // remove imex id from xrefs
            clearPropertiesLinkedToXrefs();

            // clear all confidences
            ((BinaryInteractionIdentifiersList)getIdentifiers()).clearOnly();
        }
    }

    private class BinaryInteractionXrefList extends AbstractListHavingPoperties<Xref> {
        public BinaryInteractionXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {
            processAddedXrefEvent(added);

            // the added identifier is imex and the current imex is not set
            if (getImexId() == null && XrefUtils.isXrefFromDatabase(added, Xref.IMEX_MI, Xref.IMEX)){
                // the added xref is imex-primary
                if (XrefUtils.doesXrefHaveQualifier(added, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)){
                    if (added instanceof CrossReference){
                        ((InteractionMitabIdentifiersList) getInteractionAcs()).addOnly((CrossReference)added);
                    }
                    else {
                        CrossReference imex = new CrossReferenceImpl(added.getDatabase().getShortName(), added.getId(), added.getQualifier().getShortName());
                        ((InteractionMitabIdentifiersList) getInteractionAcs()).addOnly(imex);
                    }
                }
            }
            else{
                if (added instanceof CrossReference){
                    ((InteractionMitabXrefsList)getInteractionXrefs()).addOnly((CrossReference) added);
                }
                else {
                    ((InteractionMitabXrefsList)getInteractionXrefs()).addOnly(new CrossReferenceImpl(added.getDatabase().getShortName(), added.getId(), added.getQualifier()!= null ? added.getQualifier().getShortName() : null));
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            // the removed identifier is pubmed
            if (getImexId() != null && getImexId().equals(removed.getId())){
                if (removed instanceof CrossReference){
                    ((InteractionMitabIdentifiersList) getInteractionAcs()).removeOnly(removed);
                }
                else {
                    CrossReference imex = new CrossReferenceImpl(removed.getDatabase().getShortName(), removed.getId(), removed.getQualifier().getShortName());
                    ((InteractionMitabIdentifiersList) getInteractionAcs()).removeOnly(imex);
                }
            }
            // the removed identifier is pubmed
            processRemovedXrefEvent(removed);
        }

        @Override
        protected void clearProperties() {
            ((InteractionMitabIdentifiersList) interactionAcs).retainAllOnly(getIdentifiers());
            ((InteractionMitabXrefsList)interactionXrefs).clearOnly();
            clearPropertiesLinkedToXrefs();
        }
    }

    private class InteractionMitabXrefsList extends AbstractListHavingPoperties<CrossReference> {
        public InteractionMitabXrefsList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            ((BinaryInteractionXrefList)getXrefs()).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            ((BinaryInteractionXrefList)getXrefs()).removeOnly(removed);

        }

        @Override
        protected void clearProperties() {
            // clear all xrefs excepted imex
            ((BinaryInteractionXrefList)getXrefs()).retainAllOnly(interactionAcs);
        }
    }

    protected class BinaryInteractionAnnotationList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Annotation> {
        public BinaryInteractionAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Annotation added) {
            String shortTopic = added.getTopic().getShortName();

            // we have a complex expansion added as a annotation
            if (psidev.psi.mi.jami.model.Annotation.COMPLEX_EXPANSION.equals(shortTopic)){
                CrossReference matchingXref = null;
                if (added.getValue() == null){
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.COMPLEX_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.COMPLEX_EXPANSION);
                }
                else if (psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION.equalsIgnoreCase(added.getValue().trim())){
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION);
                }
                else if (psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION.equalsIgnoreCase(added.getValue().trim())){
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION);
                }
                else if (psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION.equalsIgnoreCase(added.getValue().trim())){
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION);
                }
                else {
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.COMPLEX_EXPANSION_MI, added.getValue());
                }
                ((ComplexExpansionList)getComplexExpansion()).addOnly(matchingXref);
            }
            else if (psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION.equalsIgnoreCase(shortTopic)){
                CrossReference matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION);
                ((ComplexExpansionList)getComplexExpansion()).addOnly(matchingXref);
            }
            else if (psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION.equalsIgnoreCase(shortTopic)){
                CrossReference matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION);
                ((ComplexExpansionList)getComplexExpansion()).addOnly(matchingXref);
            }
            else if (psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION.equalsIgnoreCase(shortTopic)){
                CrossReference matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION);
                ((ComplexExpansionList)getComplexExpansion()).addOnly(matchingXref);
            }
            // we have a simple xref
            else {
                if (added instanceof Annotation){
                    ((InteractionMitabAnnotationList)getInteractionAnnotations()).addOnly((Annotation) added);
                }
                else {
                    ((InteractionMitabAnnotationList)getInteractionAnnotations()).addOnly(new AnnotationImpl(added.getTopic().getShortName(), added.getValue()));
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Annotation removed) {
            String shortTopic = removed.getTopic().getShortName();

            // we have a complex expansion added as a annotation
            if (psidev.psi.mi.jami.model.Annotation.COMPLEX_EXPANSION.equals(shortTopic)){
                CrossReference matchingXref = null;
                if (removed.getValue() == null){
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.COMPLEX_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.COMPLEX_EXPANSION);
                }
                else if (psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION.equalsIgnoreCase(removed.getValue().trim())){
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION);
                }
                else if (psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION.equalsIgnoreCase(removed.getValue().trim())){
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION);
                }
                else if (psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION.equalsIgnoreCase(removed.getValue().trim())){
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION);
                }
                else {
                    matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.COMPLEX_EXPANSION_MI, removed.getValue());
                }
                ((ComplexExpansionList)getComplexExpansion()).removeOnly(matchingXref);
            }
            else if (psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION.equalsIgnoreCase(shortTopic)){
                CrossReference matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.SPOKE_EXPANSION);
                ((ComplexExpansionList)getComplexExpansion()).removeOnly(matchingXref);
            }
            else if (psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION.equalsIgnoreCase(shortTopic)){
                CrossReference matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.MATRIX_EXPANSION);
                ((ComplexExpansionList)getComplexExpansion()).removeOnly(matchingXref);
            }
            else if (psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION.equalsIgnoreCase(shortTopic)){
                CrossReference matchingXref = new CrossReferenceImpl(CvTerm.PSI_MI, psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION_MI, psidev.psi.mi.jami.model.Annotation.BIPARTITE_EXPANSION);
                ((ComplexExpansionList)getComplexExpansion()).removeOnly(matchingXref);
            }
            // remove normal annotation
            else {
                if (removed instanceof Annotation){
                    ((InteractionMitabAnnotationList)getInteractionAnnotations()).removeOnly(removed);
                }
                else {
                    ((InteractionMitabAnnotationList)getInteractionAnnotations()).removeOnly(new AnnotationImpl(removed.getTopic().getShortName(), removed.getValue()));
                }
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractionMitabAnnotationList)getInteractionAnnotations()).clearOnly();
            ((ComplexExpansionList)getComplexExpansion()).clearOnly();
        }
    }

    protected class ComplexExpansionList extends AbstractListHavingPoperties<CrossReference> {
        public ComplexExpansionList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {
            ((BinaryInteractionAnnotationList)getAnnotations()).addOnly(
                    new DefaultAnnotation(CvTermFactory.createMICvTerm(Annotation.COMPLEX_EXPANSION, Annotation.COMPLEX_EXPANSION_MI), added.getText() != null ? added.getText() : added.getId()));
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {
            ((BinaryInteractionAnnotationList)getAnnotations()).removeOnly(
                    new DefaultAnnotation(CvTermFactory.createMICvTerm(Annotation.COMPLEX_EXPANSION, Annotation.COMPLEX_EXPANSION_MI), removed.getText() != null ? removed.getText() : removed.getId()));
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((BinaryInteractionAnnotationList)getAnnotations()).retainAllOnly(getAnnotations());
        }
    }

    protected class InteractionMitabAnnotationList extends AbstractListHavingPoperties<Annotation> {
        public InteractionMitabAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Annotation added) {

            // we added a annotation, needs to add it in annotations
            ((BinaryInteractionAnnotationList)getAnnotations()).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Annotation removed) {

            // we removed a annotation, needs to remove it in annotations
            ((BinaryInteractionAnnotationList)getAnnotations()).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((BinaryInteractionAnnotationList)getAnnotations()).retainAllOnly(getComplexExpansion());
        }
    }

    protected class BinaryInteractionParametersList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Parameter> {
        public BinaryInteractionParametersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Parameter added) {
            if (added instanceof Parameter){
                ((InteractionMitabParametersList) getParameters()).addOnly((Parameter) added);
            }
            else {
                try {
                    ((InteractionMitabParametersList) getParameters()).addOnly(new ParameterImpl(added.getType().getShortName(), ParameterUtils.getParameterValueAsString(added)));
                } catch (IllegalParameterException e) {
                    removeOnly(added);
                    log.error("Impossible to add this parameter because not well formatted", e);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Parameter removed) {
            if (removed instanceof Parameter){
                ((InteractionMitabParametersList) getParameters()).removeOnly(removed);
            }
            else {
                try {
                    ((InteractionMitabParametersList) getParameters()).removeOnly(new ParameterImpl(removed.getType().getShortName(), ParameterUtils.getParameterValueAsString(removed)));
                } catch (IllegalParameterException e) {
                    log.error("Impossible to remove entirely this parameter because not well formatted", e);
                }
            }
        }

        @Override
        protected void clearProperties() {
            ((InteractionMitabParametersList) getParameters()).clearOnly();
        }
    }

    protected class InteractionMitabParametersList extends AbstractListHavingPoperties<Parameter> {
        public InteractionMitabParametersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Parameter added) {

            ((BinaryInteractionParametersList)getExperimentalParameters()).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Parameter removed) {

            ((BinaryInteractionParametersList)getExperimentalParameters()).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            ((BinaryInteractionParametersList)getExperimentalParameters()).clearOnly();
        }
    }

    protected class BinaryInteractionChecksumList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Checksum> {
        public BinaryInteractionChecksumList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Checksum added) {
            Checksum modified = null;
            if (added instanceof Checksum){
                modified = (Checksum) added;
                ((InteractionMitabChecksumList)getInteractionChecksums()).addOnly(modified);
            }
            else {
                modified = new ChecksumImpl(added.getMethod().getShortName(), added.getValue());
                ((InteractionMitabChecksumList)getInteractionChecksums()).addOnly(modified);
            }

            processAddedChecksumEvent(modified);
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Checksum removed) {
            Checksum modified = null;
            if (removed instanceof Checksum){
                modified = (Checksum) removed;
                ((InteractionMitabChecksumList)getInteractionChecksums()).removeOnly(modified);
            }
            else {
                modified = new ChecksumImpl(removed.getMethod().getShortName(), removed.getValue());
                ((InteractionMitabChecksumList)getInteractionChecksums()).removeOnly(modified);
            }

            processRemovedChecksumEvent(modified);
        }

        @Override
        protected void clearProperties() {
            // clear all checksums
            ((InteractionMitabChecksumList)getInteractionChecksums()).clearOnly();
            clearPropertiesLinkedToChecksums();
        }
    }

    protected class InteractionMitabChecksumList extends AbstractListHavingPoperties<Checksum> {
        public InteractionMitabChecksumList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Checksum added) {

            // we added a checksum, needs to add it in checksums
            ((BinaryInteractionChecksumList)getChecksums()).addOnly(added);
            processRemovedChecksumEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Checksum removed) {

            // we removed a checksum, needs to remove it in checksums
            ((BinaryInteractionChecksumList)getChecksums()).removeOnly(removed);
            processRemovedChecksumEvent(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all checksums
            ((BinaryInteractionChecksumList)getChecksums()).clearOnly();
            clearPropertiesLinkedToChecksums();
        }
    }

    protected class MitabCreationDateList extends AbstractListHavingPoperties<Date> {
        public MitabCreationDateList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Date added) {

            if (getCreatedDate() == null){
                setCreatedDateOnly(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(Date removed) {

            if (getCreatedDate() != null && getCreatedDate() == removed && !isEmpty()){
                setCreatedDateOnly(creationDate.iterator().next());
            }

            if (isEmpty()){
                setCreatedDateOnly(null);
            }
        }

        @Override
        protected void clearProperties() {
            setCreatedDateOnly(null);
        }
    }

    protected class MitabUpdateDateList extends AbstractListHavingPoperties<Date> {
        public MitabUpdateDateList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Date added) {

            if (getUpdatedDate() == null){
                setUpdatedDateOnly(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(Date removed) {

            if (getUpdatedDate() != null && getUpdatedDate() == removed && !isEmpty()){
                setUpdatedDateOnly(updateDate.iterator().next());
            }

            if (isEmpty()){
                setUpdatedDateOnly(null);
            }
        }

        @Override
        protected void clearProperties() {
            setUpdatedDateOnly(null);
        }
    }
}
