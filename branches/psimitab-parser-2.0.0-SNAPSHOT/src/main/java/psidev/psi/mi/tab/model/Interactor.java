/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;

import org.apache.commons.collections.CollectionUtils;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.clone.FeatureCloner;
import psidev.psi.mi.jami.utils.clone.InteractionCloner;
import psidev.psi.mi.jami.utils.clone.InteractorCloner;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Simple description of an Interactor.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public class Interactor extends DefaultParticipantEvidence implements Serializable {

	/**
	 * Generated with IntelliJ plugin generateSerialVersionUID.
	 * To keep things consistent, please use the same thing.
	 */
	private static final long serialVersionUID = -4549381843138930910L;

	///////////////////////
	// Instance variables

    private MitabInteractor mitabInteractor;

    /**
     * Features of the interactor.
     */
    private List<Feature> mitabFeatures
            = new InteractorMitabFeatureList();

    /**
     * Stoichiometry of the interactor.
     */
    private List<Integer> mitabStoichiometry
            = new InteractorMitabStoichiometryList();

	/**
	 * Interactor's biological role.
	 */
	private List<CrossReference> biologicalRoles
			= new BiologicalRoleList();

	/**
	 * Interactor's experimental role.
	 */
	private List<CrossReference> experimentalRoles
			= new ExperimentalRoleList();

	/**
	 * Participant identification method.
	 */
	private List<CrossReference> participantIdentificationMethods
			= new ParticipantIdentificationMethodList();


	///////////////////////////
	// Constructor

	public Interactor() {
        super(new MitabInteractor(), null);
        processNewBiologicalRoleInBiologicalRoleList(biologicalRole);
        processNewExperimentalRoleInExperimentalRoleList(experimentalRole);

        mitabInteractor = (MitabInteractor) interactor;
	}

	public Interactor(List<CrossReference> identifiers) {
        this();
		if (identifiers == null) {
			throw new IllegalArgumentException("You must give a non null list of identifiers.");
		}
        mitabInteractor = (MitabInteractor) interactor;
        mitabInteractor.setUniqueIdentifiers(identifiers);
    }

    public Interactor(MitabInteractor mitabInteractor, CvTerm partDetMethod) {
        super(mitabInteractor, partDetMethod);
        processNewExperimentalRoleInExperimentalRoleList(experimentalRole);
        processNewBiologicalRoleInBiologicalRoleList(biologicalRole);
        if (partDetMethod != null){
            processNewMethodInIdentificationMethodList(partDetMethod);
        }
        this.mitabInteractor = mitabInteractor;
    }

    @Override
    protected void initializeFeatures(){
        this.features = new InteractorFeatureList();
    }

	///////////////////////////
	// Getters and Setters

	/**
	 * Getter for property 'identifiers'.
	 *
	 * @return Value for property 'identifiers'.
	 */
	public List<CrossReference> getIdentifiers() {
		return mitabInteractor.getUniqueIdentifiers();
	}

	/**
	 * Setter for property 'identifiers'.
	 *
	 * @param identifiers Value to set for property 'identifiers'.
	 */
	public void setIdentifiers(List<CrossReference> identifiers) {
        mitabInteractor.setUniqueIdentifiers(identifiers);
	}

	/**
	 * Getter for property 'alternativeIdentifiers'.
	 *
	 * @return Value for property 'alternativeIdentifiers'.
	 */
	public List<CrossReference> getAlternativeIdentifiers() {
		return mitabInteractor.getAlternativeIdentifiers();
	}

	/**
	 * Setter for property 'alternativeIdentifiers'.
	 *
	 * @param alternativeIdentifiers Value to set for property 'alternativeIdentifiers'.
	 */
	public void setAlternativeIdentifiers(List<CrossReference> alternativeIdentifiers) {
        mitabInteractor.setAlternativeIdentifiers(alternativeIdentifiers);
	}

	/**
	 * Getter for property 'aliases'.
	 *
	 * @return Value for property 'aliases'.
	 */
	public List<Alias> getInteractorAliases() {
		return mitabInteractor.getMitabAliases();
	}

	/**
	 * Setter for property 'aliases'.
	 *
	 * @param aliases Value to set for property 'aliases'.
	 */
	public void setInteractorAliases(List<Alias> aliases) {
        mitabInteractor.setMitabAliases(aliases);
	}

	/**
	 * Getter for property 'organism'.
	 *
	 * @return Value for property 'organisms'.
	 */
	public Organism getOrganism() {
		return mitabInteractor.getOrganism();
	}

	/**
	 * Setter for property 'organism'.
	 *
	 * @param organism Value to set for property 'organisms'.
	 */
	public void setOrganism(Organism organism) {
        mitabInteractor.setOrganism(organism);
	}

	/**
	 * Checks if the interactor has a organism associated.
	 *
	 * @return true if has a organism
	 */
	public boolean hasOrganism() {
		return mitabInteractor.hasOrganism();
	}

	/**
	 * Getter fot property 'interactorTypes'.
	 *
	 * @return Value for property 'interactorTypes'.
	 */
	public List<CrossReference> getInteractorTypes() {
		return mitabInteractor.getInteractorTypes();
	}

	/**
	 * Setter for property 'interactorTypes'.
	 *
	 * @param interactorTypes Value to set for property 'interactorTypes'.
	 */
	public void setInteractorTypes(List<CrossReference> interactorTypes) {
        mitabInteractor.setInteractorTypes(interactorTypes);
	}

	/**
	 * Getter fot property 'xrefs'.
	 *
	 * @return Value for property 'xrefs'.
	 */
	public List<CrossReference> getInteractorXrefs() {
		return mitabInteractor.getMitabXrefs();
	}

	/**
	 * Setter for property 'xrefs'.
	 *
	 * @param xrefs Value to set for property 'xrefs'.
	 */
	public void setInteractorXrefs(List<CrossReference> xrefs) {
        mitabInteractor.setMitabXrefs(xrefs);
	}

	/**
	 * Getter fot property 'annotations'.
	 *
	 * @return Value for property 'annotations'.
	 */
	public List<Annotation> getInteractorAnnotations() {
		return mitabInteractor.getMitabAnnotations();
	}

	/**
	 * Setter for property 'annotations'.
	 *
	 * @param annotations Value to set for property 'annotations'.
	 */
	public void setInteractorAnnotations(List<Annotation> annotations) {
        mitabInteractor.setMitabAnnotations(annotations);
	}

	/**
	 * Getter fot property 'checksum'.
	 *
	 * @return Value for property 'checksums'.
	 */
	public List<Checksum> getChecksums() {
		return mitabInteractor.getMitabChecksums();
	}

	/**
	 * Setter for property 'checksums'.
	 *
	 * @param checksums Value to set for property 'checksums'.
	 */
	public void setChecksums(List<Checksum> checksums) {
        mitabInteractor.setMitabChecksums(checksums);
	}

	/**
	 * Getter fot property 'features'.
	 *
	 * @return Value for property 'features'.
	 */
	public List<Feature> getInteractorFeatures() {
		return mitabFeatures;
	}

	/**
	 * Setter for property 'features'.
	 *
	 * @param features Value to set for property 'features'.
	 */
	public void setInteractorFeatures(List<Feature> features) {
        this.mitabFeatures.clear();
        if (features != null) {
            this.mitabFeatures.addAll(features);
        }
	}

	/**
	 * Getter fot property 'stoichiometry'.
	 *
	 * @return Value for property 'stoichiometry'.
	 */
	public List<Integer> getInteractorStoichiometry() {
		return this.mitabStoichiometry;
	}

	/**
	 * Setter for property 'stoichiometry'.
	 *
	 * @param stoichiometry Value to set for property 'stoichiometry'.
	 */
	public void setInteractorStoichiometry(List<Integer> stoichiometry) {
        this.mitabStoichiometry.clear();
        if (stoichiometry != null) {
            this.mitabStoichiometry.addAll(stoichiometry);
        }
	}

	/**
	 * Getter fot property 'participants'.
	 *
	 * @return Value for property 'participants'.
	 */
	public List<CrossReference> getParticipantIdentificationMethods() {
		return participantIdentificationMethods;
	}

	/**
	 * Setter for property 'participant'.
	 *
	 * @param participantIdentificationMethods
	 *         Value to set for property 'participant'.
	 */
	public void setParticipantIdentificationMethods(List<CrossReference> participantIdentificationMethods) {
        this.participantIdentificationMethods.clear();
        if (participantIdentificationMethods != null) {
            this.participantIdentificationMethods.addAll(participantIdentificationMethods);
        }
	}

    /**
     * Getter fot property 'biologicalRoles'.
     *
     * @return Value for property 'biologicalRoles'.
     */
    public List<CrossReference> getBiologicalRoles() {
        return biologicalRoles;
    }

    /**
     * Setter for property 'biologicalRoles'.
     *
     * @param biologicalRoles Value to set for property 'biologicalRoles'.
     */
    public void setBiologicalRoles(List<CrossReference> biologicalRoles) {

        if (biologicalRoles != null) {
            ((BiologicalRoleList)this.biologicalRoles).clearOnly() ;
            this.biologicalRoles.addAll(biologicalRoles);
        }
        else {
            this.biologicalRoles.clear();
        }
    }

    /**
     * Getter fot property 'experimentalRoles'.
     *
     * @return Value for property 'experimentalRoles'.
     */
    public List<CrossReference> getExperimentalRoles() {
        return experimentalRoles;
    }

    /**
     * Setter for property 'experimentalRoles'.
     *
     * @param experimentalRoles Value to set for property 'experimentalRoles'.
     */
    public void setExperimentalRoles(List<CrossReference> experimentalRoles) {
        if (experimentalRoles != null) {
            ((ExperimentalRoleList)this.experimentalRoles).clearOnly();
            this.experimentalRoles.addAll(experimentalRoles);
        }
        else {
            this.experimentalRoles.clear();
        }
    }

	public boolean isEmpty() {
		//We don not want to have a empty interactor, we prefer a null interactor
		return
			(this.getIdentifiers() == null || this.getIdentifiers().isEmpty()) &&
			(this.getAlternativeIdentifiers() == null || this.getAlternativeIdentifiers().isEmpty()) &&
			(this.getInteractorAliases() == null || this.getInteractorAliases().isEmpty()) &&
			(!this.hasOrganism() || (this.hasOrganism()
									&& this.getOrganism().getIdentifiers().isEmpty()
									&& this.getOrganism().getTaxid() == null || this.getOrganism().getTaxid().isEmpty()) ) &&
			(this.getBiologicalRoles() == null || this.getBiologicalRoles().isEmpty()) &&
			(this.getExperimentalRoles() == null || this.getExperimentalRoles().isEmpty()) &&
			(this.getInteractorTypes() == null || this.getInteractorTypes().isEmpty()) &&
			(this.getInteractorXrefs() == null || this.getInteractorXrefs().isEmpty()) &&
			(this.getChecksums() == null || this.getChecksums().isEmpty()) &&
			(this.getInteractorFeatures() == null || this.getInteractorFeatures().isEmpty()) &&
			(this.getInteractorStoichiometry() == null || this.getInteractorStoichiometry().isEmpty()) &&
			(this.getParticipantIdentificationMethods() == null || this.getParticipantIdentificationMethods().isEmpty());
	}

    protected void resetIdentificationMethodNameFromMiReferences(){
        if (!participantIdentificationMethods.isEmpty()){
            Xref ref = XrefUtils.collectFirstIdentifierWithDatabase(new ArrayList<Xref>(participantIdentificationMethods), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);

            if (ref != null){
                String name = ref.getQualifier() != null ? ref.getQualifier().getShortName() : "unknown";
                identificationMethod.setShortName(name);
                identificationMethod.setFullName(name);
            }
        }
    }

    protected void resetIdentificationMethodNameFromFirstReferences(){
        if (!participantIdentificationMethods.isEmpty()){
            Iterator<CrossReference> methodsIterator = participantIdentificationMethods.iterator();
            String name = null;

            while (name == null && methodsIterator.hasNext()){
                CrossReference ref = methodsIterator.next();

                if (ref.getText() != null){
                    name = ref.getText();
                }
            }

            identificationMethod.setShortName(name != null ? name : "unknown");
            identificationMethod.setFullName(name != null ? name : "unknown");
        }
    }

    protected void resetBiologicalRoleNameFromMiReferences(){
        if (!biologicalRoles.isEmpty()){
            Xref ref = XrefUtils.collectFirstIdentifierWithDatabase(new ArrayList<Xref>(biologicalRoles), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);

            if (ref != null){
                String name = ref.getQualifier() != null ? ref.getQualifier().getShortName() : "unknown";
                biologicalRole.setShortName(name);
                biologicalRole.setFullName(name);
            }
        }
    }

    protected void resetBiologicalRoleNameFromFirstReferences(){
        if (!biologicalRoles.isEmpty()){
            Iterator<CrossReference> methodsIterator = biologicalRoles.iterator();
            String name = null;

            while (name == null && methodsIterator.hasNext()){
                CrossReference ref = methodsIterator.next();

                if (ref.getText() != null){
                    name = ref.getText();
                }
            }

            biologicalRole.setShortName(name != null ? name : "unknown");
            biologicalRole.setFullName(name != null ? name : "unknown");
        }
    }

    protected void resetExperimentalRoleNameFromMiReferences(){
        if (!experimentalRoles.isEmpty()){
            Xref ref = XrefUtils.collectFirstIdentifierWithDatabase(new ArrayList<Xref>(experimentalRoles), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);

            if (ref != null){
                String name = ref.getQualifier() != null ? ref.getQualifier().getShortName() : "unknown";
                experimentalRole.setShortName(name);
                experimentalRole.setFullName(name);
            }
        }
    }

    protected void resetExperimentalRoleNameFromFirstReferences(){
        if (!experimentalRoles.isEmpty()){
            Iterator<CrossReference> methodsIterator = experimentalRoles.iterator();
            String name = null;

            while (name == null && methodsIterator.hasNext()){
                CrossReference ref = methodsIterator.next();

                if (ref.getText() != null){
                    name = ref.getText();
                }
            }

            experimentalRole.setShortName(name != null ? name : "unknown");
            experimentalRole.setFullName(name != null ? name : "unknown");
        }
    }
	/////////////////////////////
	// Object's override

    @Override
    public void setStoichiometry(Integer stoichiometry) {

        if (stoichiometry != null){
            if (this.stoichiometry != null){
                this.mitabStoichiometry.remove(this.stoichiometry);
            }
            this.stoichiometry = stoichiometry;
            this.mitabStoichiometry.add(this.stoichiometry);
        }
        else if (!this.mitabStoichiometry.isEmpty()) {
            this.mitabStoichiometry.clear();
            this.stoichiometry = null;
        }

    }

    @Override
    public void setInteraction(InteractionEvidence interaction) {
        if (interaction == null){
            super.setInteraction(null);
        }
        else if (interaction instanceof BinaryInteraction){
            super.setInteraction(interaction);
        }
        else if (interaction.getParticipants().size() > 2){
            throw new IllegalArgumentException("A MitabInteractor need a BinaryInteraction with one or two participants and not " + interaction.getParticipants().size());
        }
        else {
            BinaryInteraction<Interactor> convertedInteraction = new BinaryInteractionImpl();

            InteractionCloner.copyAndOverrideInteractionEvidenceProperties(interaction, convertedInteraction);
            super.setInteraction(convertedInteraction);
        }
    }

    @Override
    public void setInteractionEvidenceAndAddParticipantEvidence(InteractionEvidence interaction) {
        if (interaction == null){
            super.setInteraction(null);
        }
        else if (interaction instanceof BinaryInteraction){
            super.setInteraction(interaction);
            interaction.getParticipants().add(this);
        }
        else if (interaction.getParticipants().size() > 2){
            throw new IllegalArgumentException("A MitabInteractor need a BinaryInteraction with one or two participants and not " + interaction.getParticipants().size());
        }
        else {
            BinaryInteraction<Interactor> convertedInteraction = new BinaryInteractionImpl();

            InteractionCloner.copyAndOverrideInteractionEvidenceProperties(interaction, convertedInteraction);
            super.setInteraction(convertedInteraction);
            convertedInteraction.getParticipants().add(this);
        }
    }

    @Override
    public void setInteractor(psidev.psi.mi.jami.model.Interactor interactor) {
        if (interactor == null){
            super.setInteractor(null);
            this.mitabInteractor = null;
        }
        else if (interactor instanceof MitabInteractor){
            super.setInteractor(interactor);
            this.mitabInteractor = (MitabInteractor) interactor;
        }
        else {
            MitabInteractor convertedInteractor = null;
            // create a MitabInteractor for backward compatibility
            if (interactor instanceof BioactiveEntity){
                convertedInteractor = new MitabBioactiveEntity(interactor.getType());
            }
            else if (interactor instanceof Gene){
                convertedInteractor = new MitabGene();
            }
            else if (interactor instanceof Protein){
                convertedInteractor = new MitabProtein(interactor.getType());
            }
            else if (interactor instanceof NucleicAcid){
                convertedInteractor = new MitabNucleicAcid(interactor.getType());
            }
            else {
                convertedInteractor = new MitabInteractor(interactor.getType());
            }

            InteractorCloner.copyAndOverrideInteractorProperties(interactor, convertedInteractor);
            mitabInteractor = convertedInteractor;
            super.setInteractor(convertedInteractor);
        }
    }

    @Override
    public void setIdentificationMethod(CvTerm identificationMethod) {
        super.setIdentificationMethod(identificationMethod);
        processNewMethodInIdentificationMethodList(identificationMethod);
    }

    @Override
    public void setBiologicalRole(CvTerm bioRole) {
        super.setBiologicalRole(bioRole);
        processNewBiologicalRoleInBiologicalRoleList(bioRole);
    }

    @Override
    public void setExperimentalRole(CvTerm expRole) {
        super.setExperimentalRole(expRole);
        processNewExperimentalRoleInExperimentalRoleList(expRole);
    }

    private void processNewMethodInIdentificationMethodList(CvTerm identification) {
        if (identification.getMIIdentifier() != null){
            ((ParticipantIdentificationMethodList)participantIdentificationMethods).addOnly(new CrossReferenceImpl(CvTerm.PSI_MI, identification.getMIIdentifier(), identification.getFullName() != null ? identification.getFullName(): identification.getShortName()));
        }
        else{
            if (!identification.getIdentifiers().isEmpty()){
                Xref ref = identification.getIdentifiers().iterator().next();
                ((ParticipantIdentificationMethodList)participantIdentificationMethods).addOnly(new CrossReferenceImpl(ref.getDatabase().getShortName(), ref.getId(), identification.getFullName() != null ? identification.getFullName(): identification.getShortName()));
            }
            else {
                ((ParticipantIdentificationMethodList)participantIdentificationMethods).addOnly(new CrossReferenceImpl("unknown", "-", identification.getFullName() != null ? identification.getFullName(): identification.getShortName()));
            }
        }
    }

    private void processNewBiologicalRoleInBiologicalRoleList(CvTerm role) {
        if (role.getMIIdentifier() != null){
            ((BiologicalRoleList)biologicalRoles).addOnly(new CrossReferenceImpl(CvTerm.PSI_MI, role.getMIIdentifier(), role.getFullName() != null ? role.getFullName(): role.getShortName()));
        }
        else{
            if (!role.getIdentifiers().isEmpty()){
                Xref ref = role.getIdentifiers().iterator().next();
                ((BiologicalRoleList)biologicalRoles).addOnly(new CrossReferenceImpl(ref.getDatabase().getShortName(), ref.getId(), role.getFullName() != null ? role.getFullName(): role.getShortName()));
            }
            else {
                ((BiologicalRoleList)biologicalRoles).addOnly(new CrossReferenceImpl("unknown", "-", role.getFullName() != null ? role.getFullName(): role.getShortName()));
            }
        }
    }

    private void processNewExperimentalRoleInExperimentalRoleList(CvTerm role) {
        if (role.getMIIdentifier() != null){
            ((ExperimentalRoleList)experimentalRoles).addOnly(new CrossReferenceImpl(CvTerm.PSI_MI, role.getMIIdentifier(), role.getFullName() != null ? role.getFullName() : role.getShortName()));
        }
        else{
            if (!role.getIdentifiers().isEmpty()){
                Xref ref = role.getIdentifiers().iterator().next();
                ((ExperimentalRoleList)experimentalRoles).addOnly(new CrossReferenceImpl(ref.getDatabase().getShortName(), ref.getId(), role.getFullName() != null ? role.getFullName() : role.getShortName()));
            }
            else {
                ((ExperimentalRoleList)experimentalRoles).addOnly(new CrossReferenceImpl("unknown", "-", role.getFullName() != null ? role.getFullName() : role.getShortName()));
            }
        }
    }

    private Interactor getInstance(){
        return this;
    }

    //TODO Update the toString, equals and hash

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append("{identifiers=").append(getIdentifiers());
		sb.append(", alternativeIdentifiers=").append(getAlternativeIdentifiers());
		sb.append(", aliases=").append(getInteractorAliases());
		sb.append(", organism=").append(getOrganism() != null ? getOrganism() : "-");
		sb.append(", biologicalRoles=").append(biologicalRoles);
		sb.append(", experimentalRoles=").append(experimentalRoles);
		sb.append(", interactorTypes=").append(getInteractorTypes());
		sb.append(", xrefs=").append(getInteractorXrefs());
		sb.append(", annotations=").append(getInteractorAnnotations());
		sb.append(", checksums=").append(getChecksums());
		sb.append(", features=").append(mitabFeatures);
		sb.append(", stoichiometry=").append(mitabStoichiometry);
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

		if (getIdentifiers() != null ? !CollectionUtils.isEqualCollection(getIdentifiers(), that.getIdentifiers()) : that.getIdentifiers() != null)
			return false;
		if (getOrganism() != null ? !getOrganism().equals(that.getOrganism()) : that.getOrganism() != null) return false;
		if (getAlternativeIdentifiers() != null ? !CollectionUtils.isEqualCollection(getAlternativeIdentifiers(), that.getAlternativeIdentifiers()) : that.getAlternativeIdentifiers() != null)
			return false;
		if (getInteractorAliases() != null ? !CollectionUtils.isEqualCollection(getInteractorAliases(), that.getInteractorAliases()) : that.getInteractorAliases() != null)
			return false;
		if (biologicalRoles != null ? !CollectionUtils.isEqualCollection(biologicalRoles, that.biologicalRoles) : that.biologicalRoles != null)
			return false;
		if (experimentalRoles != null ? !CollectionUtils.isEqualCollection(experimentalRoles, that.experimentalRoles) : that.experimentalRoles != null)
			return false;
		if (getInteractorTypes() != null ? !CollectionUtils.isEqualCollection(getInteractorTypes(), that.getInteractorTypes()) : that.getInteractorTypes() != null)
			return false;
		if (getInteractorXrefs() != null ? !CollectionUtils.isEqualCollection(getInteractorXrefs(), that.getInteractorXrefs()) : that.getInteractorXrefs() != null) return false;
		if (getInteractorAnnotations() != null ? !CollectionUtils.isEqualCollection(getInteractorAnnotations(), that.getInteractorAnnotations()) : that.getInteractorAnnotations() != null)
			return false;
		if (getChecksums() != null ? !CollectionUtils.isEqualCollection(getChecksums(), that.getChecksums()) : that.getChecksums() != null)
			return false;
		if (mitabFeatures != null ? !CollectionUtils.isEqualCollection(mitabFeatures, that.mitabFeatures) : that.mitabFeatures != null)
			return false;
		if (mitabStoichiometry != null ? !CollectionUtils.isEqualCollection(mitabStoichiometry, that.mitabStoichiometry) : that.mitabStoichiometry != null)
			return false;
		if (participantIdentificationMethods != null ? !CollectionUtils.isEqualCollection(participantIdentificationMethods, that.participantIdentificationMethods) : that.participantIdentificationMethods != null)
			return false;

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int result;
		result = (getIdentifiers() != null ? getIdentifiers().hashCode() : 0);
		result = 31 * result + (getOrganism() != null ? getOrganism().hashCode() : 0);
		result = 31 * result + (getAlternativeIdentifiers() != null ? getAlternativeIdentifiers().hashCode() : 0);
		result = 31 * result + (getInteractorAliases() != null ? getInteractorAliases().hashCode() : 0);
		result = 31 * result + (biologicalRoles != null ? biologicalRoles.hashCode() : 0);
		result = 31 * result + (experimentalRoles != null ? experimentalRoles.hashCode() : 0);
		result = 31 * result + (getInteractorTypes() != null ? getInteractorTypes().hashCode() : 0);
		result = 31 * result + (getInteractorXrefs() != null ? getInteractorXrefs().hashCode() : 0);
		result = 31 * result + (getInteractorAnnotations() != null ? getInteractorAnnotations().hashCode() : 0);
		result = 31 * result + (getChecksums() != null ? getChecksums().hashCode() : 0);
		result = 31 * result + (mitabFeatures != null ? mitabFeatures.hashCode() : 0);
		result = 31 * result + (mitabStoichiometry != null ? mitabStoichiometry.hashCode() : 0);
		result = 31 * result + (participantIdentificationMethods != null ? participantIdentificationMethods.hashCode() : 0);


		return result;
	}

    protected class InteractorFeatureList extends AbstractListHavingPoperties<FeatureEvidence> {
        public InteractorFeatureList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(FeatureEvidence added) {
            if (added instanceof Feature){
                Feature f = (Feature)added;
                f.setParticipant(getInstance());
                ((InteractorMitabFeatureList)mitabFeatures).addOnly((Feature) added);
            }
            else {
                Feature tabFeature = new FeatureImpl(added.getType() != null ? added.getType().getShortName() : null, Collections.EMPTY_LIST);
                FeatureCloner.copyAndOverrideFeatureProperties(added, tabFeature);
                tabFeature.setParticipant(getInstance());

                ((InteractorMitabFeatureList) mitabFeatures).addOnly(tabFeature);
            }
        }

        @Override
        protected void processRemovedObjectEvent(FeatureEvidence removed) {
            if (removed instanceof Checksum){
                ((InteractorMitabFeatureList)mitabFeatures).removeOnly(removed);
            }
            else {
                Feature tabFeature = new FeatureImpl(removed.getType() != null ? removed.getType().getShortName() : null, Collections.EMPTY_LIST);
                FeatureCloner.copyAndOverrideFeatureProperties(removed, tabFeature);

                ((InteractorMitabFeatureList)mitabFeatures).removeOnly(tabFeature);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all mitab features
            ((InteractorMitabFeatureList)mitabFeatures).clearOnly();
        }
    }

    protected class InteractorMitabFeatureList extends AbstractListHavingPoperties<Feature> {
        public InteractorMitabFeatureList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Feature added) {

            added.setParticipant(getInstance());
            // we added a feature, needs to add it in features
            ((InteractorFeatureList)features).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Feature removed) {
            removed.setParticipant(null);
            // we removed a feature, needs to remove it in features
            ((InteractorFeatureList)features).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {

            for (psidev.psi.mi.jami.model.Feature f : features){
                f.setParticipant(null);
            }
            // clear all features
            ((InteractorFeatureList)features).clearOnly();
        }
    }

    protected class InteractorMitabStoichiometryList extends AbstractListHavingPoperties<Integer> {
        public InteractorMitabStoichiometryList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Integer added) {

            if (stoichiometry == null){
                stoichiometry = added;
            }
        }

        @Override
        protected void processRemovedObjectEvent(Integer removed) {

            if (isEmpty()){
                stoichiometry = null;
            }
            else if (stoichiometry == removed){
                stoichiometry = mitabStoichiometry.iterator().next();
            }
        }

        @Override
        protected void clearProperties() {
            stoichiometry = null;
        }
    }

    protected class ParticipantIdentificationMethodList extends AbstractListHavingPoperties<CrossReference> {
        public ParticipantIdentificationMethodList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            // the method is not set yet
            if (identificationMethod == null){
                String name = added.getText() != null ? added.getText() : "unknown";
                identificationMethod = new DefaultCvTerm(name, name, added);
            }
            else {
                identificationMethod.getXrefs().add(added);
                // reset shortname
                if (identificationMethod.getMIIdentifier() != null && identificationMethod.getMIIdentifier().equals(added.getId())){
                    String name = added.getText();

                    if (name != null){
                        identificationMethod.setShortName(name);
                    }
                    else {
                        resetIdentificationMethodNameFromMiReferences();
                        if (identificationMethod.getShortName().equals("unknown")){
                            resetIdentificationMethodNameFromFirstReferences();
                        }
                    }
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            if (identificationMethod != null){
                identificationMethod.getXrefs().remove(removed);

                if (removed.getText() != null && identificationMethod.getShortName().equals(removed.getText())){
                    if (identificationMethod.getMIIdentifier() != null){
                        resetIdentificationMethodNameFromMiReferences();
                        if (identificationMethod.getShortName().equals("unknown")){
                            resetIdentificationMethodNameFromFirstReferences();
                        }
                    }
                    else {
                        resetIdentificationMethodNameFromFirstReferences();
                    }
                }
            }

            if (isEmpty()){
                identificationMethod = null;
            }
        }

        @Override
        protected void clearProperties() {
            // clear all interactor types and reset current type
            identificationMethod = null;
        }
    }

    protected class BiologicalRoleList extends AbstractListHavingPoperties<CrossReference> {
        public BiologicalRoleList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            if (biologicalRole == null){
                String name = added.getText() != null ? added.getText() : "unknown";
                biologicalRole = new DefaultCvTerm(name, name, added);
            }
            else {
                biologicalRole.getXrefs().add(added);
                // reset shortname
                if (biologicalRole.getMIIdentifier() != null && biologicalRole.getMIIdentifier().equals(added.getId())){
                    String name = added.getText();

                    if (name != null){
                        biologicalRole.setShortName(name);
                    }
                    else {
                        resetBiologicalRoleNameFromMiReferences();
                        if (biologicalRole.getShortName().equals("unknown")){
                            resetBiologicalRoleNameFromFirstReferences();
                        }
                    }
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            if (biologicalRole != null){
                biologicalRole.getXrefs().remove(removed);

                if (removed.getText() != null && biologicalRole.getShortName().equals(removed.getText())){
                    if (biologicalRole.getMIIdentifier() != null){
                        resetBiologicalRoleNameFromMiReferences();
                        if (biologicalRole.getShortName().equals("unknown")){
                            resetBiologicalRoleNameFromFirstReferences();
                        }
                    }
                    else {
                        resetBiologicalRoleNameFromFirstReferences();
                    }
                }
            }

            if (isEmpty()){
                biologicalRole = CvTermFactory.createUnspecifiedRole();
                processNewBiologicalRoleInBiologicalRoleList(biologicalRole);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all interactor types and reset current type
            biologicalRole = CvTermFactory.createUnspecifiedRole();
            processNewBiologicalRoleInBiologicalRoleList(biologicalRole);
        }
    }

    protected class ExperimentalRoleList extends AbstractListHavingPoperties<CrossReference> {
        public ExperimentalRoleList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            if (experimentalRole == null){
                String name = added.getText() != null ? added.getText() : "unknown";
                experimentalRole = new DefaultCvTerm(name, name, added);
            }
            else {
                experimentalRole.getXrefs().add(added);
                // reset shortname
                if (experimentalRole.getMIIdentifier() != null && experimentalRole.getMIIdentifier().equals(added.getId())){
                    String name = added.getText();

                    if (name != null){
                        experimentalRole.setShortName(name);
                    }
                    else {
                        resetExperimentalRoleNameFromMiReferences();
                        if (experimentalRole.getShortName().equals("unknown")){
                            resetExperimentalRoleNameFromFirstReferences();
                        }
                    }
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            if (experimentalRole != null){
                experimentalRole.getXrefs().remove(removed);

                if (removed.getText() != null && experimentalRole.getShortName().equals(removed.getText())){
                    if (experimentalRole.getMIIdentifier() != null){
                        resetExperimentalRoleNameFromMiReferences();
                        if (experimentalRole.getShortName().equals("unknown")){
                            resetExperimentalRoleNameFromFirstReferences();
                        }
                    }
                    else {
                        resetExperimentalRoleNameFromFirstReferences();
                    }
                }
            }

            if (isEmpty()){
                experimentalRole = CvTermFactory.createUnspecifiedRole();
                processNewExperimentalRoleInExperimentalRoleList(experimentalRole);
            }
        }

        @Override
        protected void clearProperties() {
            experimentalRole = CvTermFactory.createUnspecifiedRole();
            processNewExperimentalRoleInExperimentalRoleList(experimentalRole);
        }
    }
}
