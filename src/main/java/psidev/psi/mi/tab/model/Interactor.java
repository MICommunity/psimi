/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model;

import org.apache.commons.collections.CollectionUtils;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
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
import java.util.*;

/**
 * Simple description of an Interactor.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public class Interactor extends DefaultParticipantEvidence implements Serializable, FileSourceContext {

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
    private List<Feature> mitabFeatures;

    /**
     * Stoichiometry of the interactor.
     */
    private List<Integer> mitabStoichiometry;

	/**
	 * Interactor's biological role.
	 */
	private List<CrossReference> biologicalRoles;


	/**
	 * Interactor's experimental role.
	 */
	private List<CrossReference> experimentalRoles;


	/**
	 * Participant identification method.
	 */
	private List<CrossReference> participantIdentificationMethods;

    private FileSourceLocator locator;

	///////////////////////////
	// Constructor

	public Interactor() {
        super(new MitabInteractor(), null);
        processNewBiologicalRoleInBiologicalRoleList(getBiologicalRole());
        processNewExperimentalRoleInExperimentalRoleList(getExperimentalRole());

        mitabInteractor = (MitabInteractor) getInteractor();
	}

	public Interactor(List<CrossReference> identifiers) {
        this();
		if (identifiers == null) {
			throw new IllegalArgumentException("You must give a non null list of identifiers.");
		}
        mitabInteractor.setUniqueIdentifiers(identifiers);
    }

    public Interactor(MitabInteractor mitabInteractor, CvTerm partDetMethod) {
        super(mitabInteractor, partDetMethod);
        processNewExperimentalRoleInExperimentalRoleList(getExperimentalRole());
        processNewBiologicalRoleInBiologicalRoleList(getBiologicalRole());
        if (partDetMethod != null){
            processNewMethodInIdentificationMethodList(partDetMethod);
        }
        this.mitabInteractor = mitabInteractor;
    }

    @Override
    protected void initialiseFeatureEvidences(){
        super.initialiseFeatureEvidencesWith(Collections.EMPTY_LIST);
    }

    @Override
    protected void initialiseFeatureEvidencesWith(Collection<FeatureEvidence> features){
        if (features == null){
            super.initialiseFeatureEvidencesWith(Collections.EMPTY_LIST);
        }
        else {
            if (mitabFeatures == null){
               mitabFeatures = new ArrayList<Feature>();
            }
            else{
                mitabFeatures.clear();
            }
            for (FeatureEvidence f : features){
                 addFeatureEvidence(f);
            }
        }
    }

    public FileSourceLocator getSourceLocator() {
        return locator;
    }

    public void setLocator(FileSourceLocator locator) {
        this.locator = locator;
    }

    @Override
    protected void initialiseAnnotations() {
        initialiseAnnotationsWith(getInteractor().getAnnotations());
    }

    @Override
    protected void initialiseXrefs() {
        initialiseXrefsWith(getInteractor().getXrefs());
    }

    @Override
    protected void initialiseAliases() {
        initialiseAliasesWith(getInteractor().getAliases());
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
	public void setXrefs(List<CrossReference> xrefs) {
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
	public void setAnnotations(List<Annotation> annotations) {
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
	public List<Feature> getFeatures() {
        if (mitabFeatures == null){
            mitabFeatures = new ArrayList<Feature>();
        }
		return mitabFeatures;
	}

	/**
	 * Setter for property 'features'.
	 *
	 * @param features Value to set for property 'features'.
	 */
	public void setFeatures(List<Feature> features) {
        getFeatures().clear();
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
        if (mitabStoichiometry == null){
            mitabStoichiometry = new InteractorMitabStoichiometryList();
        }
		return this.mitabStoichiometry;
	}

	/**
	 * Setter for property 'stoichiometry'.
	 *
	 * @param stoichiometry Value to set for property 'stoichiometry'.
	 */
	public void setStoichiometry(List<Integer> stoichiometry) {
        getInteractorStoichiometry().clear();
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
        if (participantIdentificationMethods == null){
            participantIdentificationMethods = new ParticipantIdentificationMethodList();
        }
		return participantIdentificationMethods;
	}

	/**
	 * Setter for property 'participant'.
	 *
	 * @param participantIdentificationMethods
	 *         Value to set for property 'participant'.
	 */
	public void setParticipantIdentificationMethods(List<CrossReference> participantIdentificationMethods) {
        getParticipantIdentificationMethods().clear();
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
        if (biologicalRoles == null){
            biologicalRoles = new BiologicalRoleList();
        }
        return biologicalRoles;
    }

    /**
     * Setter for property 'biologicalRoles'.
     *
     * @param biologicalRoles Value to set for property 'biologicalRoles'.
     */
    public void setBiologicalRoles(List<CrossReference> biologicalRoles) {

        if (biologicalRoles != null && !biologicalRoles.isEmpty()) {
            ((BiologicalRoleList)getBiologicalRoles()).clearOnly() ;
            this.biologicalRoles.addAll(biologicalRoles);
        }
        else {
            getBiologicalRoles().clear();
        }
    }

    /**
     * Getter fot property 'experimentalRoles'.
     *
     * @return Value for property 'experimentalRoles'.
     */
    public List<CrossReference> getExperimentalRoles() {
        if (experimentalRoles == null){
           experimentalRoles = new ExperimentalRoleList();
        }
        return experimentalRoles;
    }

    /**
     * Setter for property 'experimentalRoles'.
     *
     * @param experimentalRoles Value to set for property 'experimentalRoles'.
     */
    public void setExperimentalRoles(List<CrossReference> experimentalRoles) {
        if (experimentalRoles != null && !experimentalRoles.isEmpty()) {
            ((ExperimentalRoleList)getExperimentalRoles()).clearOnly();
            this.experimentalRoles.addAll(experimentalRoles);
        }
        else {
            getExperimentalRoles().clear();
        }
    }

	public boolean isEmpty() {
        if (this.getIdentifiers() != null && !this.getIdentifiers().isEmpty()){
             return false;
        }
        else if (this.getAlternativeIdentifiers() != null && !this.getAlternativeIdentifiers().isEmpty()){
             return false;
        }
        else if (this.getInteractorAliases() != null && !this.getInteractorAliases().isEmpty()){
            return false;
        }
        else if (this.hasOrganism() ){
            if (this.getOrganism().getTaxId() != -3){
                return false;
            }
        }
        else if (this.getBiologicalRoles() != null && !this.getBiologicalRoles().isEmpty()){
            if (!Participant.UNSPECIFIED_ROLE.equals(getBiologicalRole().getShortName())){
                return false;
            }
        }
        else if (this.getExperimentalRoles() != null && !this.getExperimentalRoles().isEmpty()){
            if (!Participant.UNSPECIFIED_ROLE.equals(getExperimentalRole().getShortName())){
                return false;
            }
        }
        else if (this.getInteractorTypes() != null && !this.getInteractorTypes().isEmpty()){
            if (!psidev.psi.mi.jami.model.Interactor.UNKNOWN_INTERACTOR.equals(getInteractor().getType().getShortName())){
                return false;
            }
        }
        else if (this.getInteractorXrefs() != null && !this.getInteractorXrefs().isEmpty()){
            return false;
        }
        else if (this.getChecksums() != null && !this.getChecksums().isEmpty()){
            return false;
        }
        else if (this.getFeatures() != null && !this.getFeatures().isEmpty()){
            return false;
        }
        else if (this.getInteractorStoichiometry() != null && !this.getInteractorStoichiometry().isEmpty()){
            return false;
        }
        else if (this.getParticipantIdentificationMethods() != null && !this.getParticipantIdentificationMethods().isEmpty()){
            return false;
        }
        else {
            return true;
        }
        return true;
	}

    protected void resetIdentificationMethodNameFromMiReferences(){
        if (!getParticipantIdentificationMethods().isEmpty()){
            Xref ref = XrefUtils.collectFirstIdentifierWithDatabase(new ArrayList<Xref>(participantIdentificationMethods), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);

            if (ref != null){
                String name = ref.getQualifier() != null ? ref.getQualifier().getShortName() : "unknown";
                getIdentificationMethod().setShortName(name);
                getIdentificationMethod().setFullName(name);
            }
        }
    }

    protected void resetIdentificationMethodNameFromFirstReferences(){
        if (!getParticipantIdentificationMethods().isEmpty()){
            Iterator<CrossReference> methodsIterator = participantIdentificationMethods.iterator();
            String name = null;

            while (name == null && methodsIterator.hasNext()){
                CrossReference ref = methodsIterator.next();

                if (ref.getText() != null){
                    name = ref.getText();
                }
            }

            getIdentificationMethod().setShortName(name != null ? name : "unknown");
            getIdentificationMethod().setFullName(name != null ? name : "unknown");
        }
    }

    protected void resetBiologicalRoleNameFromMiReferences(){
        if (!getBiologicalRoles().isEmpty()){
            Xref ref = XrefUtils.collectFirstIdentifierWithDatabase(new ArrayList<Xref>(biologicalRoles), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);

            if (ref != null){
                String name = ref.getQualifier() != null ? ref.getQualifier().getShortName() : "unknown";
                getBiologicalRole().setShortName(name);
                getBiologicalRole().setFullName(name);
            }
        }
    }

    protected void resetBiologicalRoleNameFromFirstReferences(){
        if (!getBiologicalRoles().isEmpty()){
            Iterator<CrossReference> methodsIterator = biologicalRoles.iterator();
            String name = null;

            while (name == null && methodsIterator.hasNext()){
                CrossReference ref = methodsIterator.next();

                if (ref.getText() != null){
                    name = ref.getText();
                }
            }

            getBiologicalRole().setShortName(name != null ? name : "unknown");
            getBiologicalRole().setFullName(name != null ? name : "unknown");
        }
    }

    protected void resetExperimentalRoleNameFromMiReferences(){
        if (!getExperimentalRoles().isEmpty()){
            Xref ref = XrefUtils.collectFirstIdentifierWithDatabase(new ArrayList<Xref>(experimentalRoles), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);

            if (ref != null){
                String name = ref.getQualifier() != null ? ref.getQualifier().getShortName() : "unknown";
                getExperimentalRole().setShortName(name);
                getExperimentalRole().setFullName(name);
            }
        }
    }

    protected void resetExperimentalRoleNameFromFirstReferences(){
        if (!getExperimentalRoles().isEmpty()){
            Iterator<CrossReference> methodsIterator = experimentalRoles.iterator();
            String name = null;

            while (name == null && methodsIterator.hasNext()){
                CrossReference ref = methodsIterator.next();

                if (ref.getText() != null){
                    name = ref.getText();
                }
            }

            getExperimentalRole().setShortName(name != null ? name : "unknown");
            getExperimentalRole().setFullName(name != null ? name : "unknown");
        }
    }
	/////////////////////////////
	// Object's override


    @Override
    public Collection<? extends FeatureEvidence> getFeatureEvidences() {
        return this.mitabFeatures;
    }

    @Override
    public boolean addFeatureEvidence(FeatureEvidence feature) {
        if (feature instanceof Feature){
            Feature f = (Feature)feature;
            if (getFeatures().add((Feature) feature)){
                f.setParticipantEvidence(this);
                return true;
            }
        }
        else {
            Feature tabFeature = new FeatureImpl(feature.getType() != null ? feature.getType().getShortName() : null, Collections.EMPTY_LIST);
            FeatureCloner.copyAndOverrideFeatureEvidenceProperties(feature, tabFeature);
            tabFeature.setParticipantEvidence(getInstance());

            if (getFeatures().add(tabFeature)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeFeatureEvidence(FeatureEvidence feature) {
        if (feature instanceof Feature){
            Feature f = (Feature)feature;
            if (getFeatures().remove(feature)){
                f.setParticipantEvidence(null);
                return true;
            }
        }
        else {
            Feature tabFeature = new FeatureImpl(feature.getType() != null ? feature.getType().getShortName() : null, Collections.EMPTY_LIST);
            FeatureCloner.copyAndOverrideFeatureEvidenceProperties(feature, tabFeature);

            if (getFeatures().remove(tabFeature)){
                tabFeature.setParticipantEvidence(null);
                return true;
            }
        }
        return false;
    }

    @Override
    public void setStoichiometry(Integer stoichiometry) {

        if (stoichiometry != null){
            if (getStoichiometry() != null){
                getInteractorStoichiometry().remove(getStoichiometry());
            }
            super.setStoichiometry(stoichiometry);
            getInteractorStoichiometry().add(getStoichiometry());
        }
        else if (!getInteractorStoichiometry().isEmpty()) {
            this.mitabStoichiometry.clear();
            super.setStoichiometry(null);
        }

    }

    public void setStoichiometryOnly(Integer stoichiometry) {

        super.setStoichiometry(stoichiometry);

    }

    @Override
    public void setInteractionEvidence(InteractionEvidence interaction) {
        if (interaction == null){
            super.setInteractionEvidence(null);
        }
        else if (interaction instanceof BinaryInteraction){
            super.setInteractionEvidence(interaction);
        }
        else if (interaction.getParticipantEvidences().size() > 2){
            throw new IllegalArgumentException("A MitabInteractor need a BinaryInteraction with one or two participants and not " + interaction.getParticipantEvidences().size());
        }
        else {
            BinaryInteraction<Interactor> convertedInteraction = new BinaryInteractionImpl();

            InteractionCloner.copyAndOverrideInteractionEvidenceProperties(interaction, convertedInteraction);
            super.setInteractionEvidence(convertedInteraction);
        }
    }

    @Override
    public void setInteractionEvidenceAndAddParticipantEvidence(InteractionEvidence interaction) {
        if (interaction == null){
            super.setInteractionEvidence(null);
        }
        else if (interaction instanceof BinaryInteraction){
            interaction.addParticipantEvidence(this);
        }
        else if (interaction.getParticipantEvidences().size() > 2){
            throw new IllegalArgumentException("A MitabInteractor need a BinaryInteraction with one or two participants and not " + interaction.getParticipantEvidences().size());
        }
        else {
            BinaryInteraction<Interactor> convertedInteraction = new BinaryInteractionImpl();

            InteractionCloner.copyAndOverrideInteractionEvidenceProperties(interaction, convertedInteraction);
            convertedInteraction.addParticipantEvidence(this);
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

            initialiseAliases();
            initialiseAnnotations();
            initialiseXrefs();
        }
    }

    @Override
    public void setIdentificationMethod(CvTerm identificationMethod) {
        super.setIdentificationMethod(identificationMethod);
        processNewMethodInIdentificationMethodList(identificationMethod);
    }

    protected void setIdentificationMethodOnly(CvTerm identificationMethod) {
        super.setIdentificationMethod(identificationMethod);
    }

    @Override
    public void setBiologicalRole(CvTerm bioRole) {
        super.setBiologicalRole(bioRole);
        processNewBiologicalRoleInBiologicalRoleList(bioRole);
    }

    protected void setBiologicalRoleOnly(CvTerm bioRole) {
        super.setBiologicalRole(bioRole);
    }

    @Override
    public void setExperimentalRole(CvTerm expRole) {
        super.setExperimentalRole(expRole);
        processNewExperimentalRoleInExperimentalRoleList(expRole);
    }

    protected void setExperimentalRoleOnly(CvTerm expRole) {
        super.setExperimentalRole(expRole);
    }

    private void processNewMethodInIdentificationMethodList(CvTerm identification) {
        if (identification.getMIIdentifier() != null){
            ((ParticipantIdentificationMethodList)getParticipantIdentificationMethods()).addOnly(new CrossReferenceImpl(CvTerm.PSI_MI, identification.getMIIdentifier(), identification.getFullName() != null ? identification.getFullName(): identification.getShortName()));
        }
        else{
            if (!identification.getIdentifiers().isEmpty()){
                Xref ref = identification.getIdentifiers().iterator().next();
                ((ParticipantIdentificationMethodList)getParticipantIdentificationMethods()).addOnly(new CrossReferenceImpl(ref.getDatabase().getShortName(), ref.getId(), identification.getFullName() != null ? identification.getFullName(): identification.getShortName()));
            }
            else {
                ((ParticipantIdentificationMethodList)getParticipantIdentificationMethods()).addOnly(new CrossReferenceImpl("unknown", "-", identification.getFullName() != null ? identification.getFullName(): identification.getShortName()));
            }
        }
    }

    private void processNewBiologicalRoleInBiologicalRoleList(CvTerm role) {
        if (role.getMIIdentifier() != null){
            ((BiologicalRoleList)getBiologicalRoles()).addOnly(new CrossReferenceImpl(CvTerm.PSI_MI, role.getMIIdentifier(), role.getFullName() != null ? role.getFullName(): role.getShortName()));
        }
        else{
            if (!role.getIdentifiers().isEmpty()){
                Xref ref = role.getIdentifiers().iterator().next();
                ((BiologicalRoleList)getBiologicalRoles()).addOnly(new CrossReferenceImpl(ref.getDatabase().getShortName(), ref.getId(), role.getFullName() != null ? role.getFullName(): role.getShortName()));
            }
            else {
                ((BiologicalRoleList)getBiologicalRoles()).addOnly(new CrossReferenceImpl("unknown", "-", role.getFullName() != null ? role.getFullName(): role.getShortName()));
            }
        }
    }

    private void processNewExperimentalRoleInExperimentalRoleList(CvTerm role) {
        if (role.getMIIdentifier() != null){
            ((ExperimentalRoleList)getExperimentalRoles()).addOnly(new CrossReferenceImpl(CvTerm.PSI_MI, role.getMIIdentifier(), role.getFullName() != null ? role.getFullName() : role.getShortName()));
        }
        else{
            if (!role.getIdentifiers().isEmpty()){
                Xref ref = role.getIdentifiers().iterator().next();
                ((ExperimentalRoleList)getExperimentalRoles()).addOnly(new CrossReferenceImpl(ref.getDatabase().getShortName(), ref.getId(), role.getFullName() != null ? role.getFullName() : role.getShortName()));
            }
            else {
                ((ExperimentalRoleList)getExperimentalRoles()).addOnly(new CrossReferenceImpl("unknown", "-", role.getFullName() != null ? role.getFullName() : role.getShortName()));
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
		sb.append(", biologicalRoles=").append(getBiologicalRoles());
		sb.append(", experimentalRoles=").append(getExperimentalRoles());
		sb.append(", interactorTypes=").append(getInteractorTypes());
		sb.append(", xrefs=").append(getInteractorXrefs());
		sb.append(", annotations=").append(getInteractorAnnotations());
		sb.append(", checksums=").append(getChecksums());
		sb.append(", features=").append(getFeatures());
		sb.append(", stoichiometry=").append(getInteractorStoichiometry());
		sb.append(", participantIdentificationMethods=").append(getParticipantIdentificationMethods());
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
		if (biologicalRoles != null ? !CollectionUtils.isEqualCollection(biologicalRoles, that.getBiologicalRoles()) : (that.biologicalRoles != null && !that.biologicalRoles.isEmpty()))
			return false;
		if (experimentalRoles != null ? !CollectionUtils.isEqualCollection(experimentalRoles, that.getExperimentalRoles()) : (that.experimentalRoles != null && !that.experimentalRoles.isEmpty()))
			return false;
		if (getInteractorTypes() != null ? !CollectionUtils.isEqualCollection(getInteractorTypes(), that.getInteractorTypes()) : that.getInteractorTypes() != null)
			return false;
		if (getInteractorXrefs() != null ? !CollectionUtils.isEqualCollection(getInteractorXrefs(), that.getInteractorXrefs()) : that.getInteractorXrefs() != null) return false;
		if (getInteractorAnnotations() != null ? !CollectionUtils.isEqualCollection(getInteractorAnnotations(), that.getInteractorAnnotations()) : that.getInteractorAnnotations() != null)
			return false;
		if (getChecksums() != null ? !CollectionUtils.isEqualCollection(getChecksums(), that.getChecksums()) : that.getChecksums() != null)
			return false;
		if (mitabFeatures != null ? !CollectionUtils.isEqualCollection(mitabFeatures, that.getFeatures()) : (that.mitabFeatures != null && !that.mitabFeatures.isEmpty()))
			return false;
		if (mitabStoichiometry != null ? !CollectionUtils.isEqualCollection(mitabStoichiometry, that.getInteractorStoichiometry()) : (that.mitabStoichiometry != null && !that.mitabStoichiometry.isEmpty()))
			return false;
		if (participantIdentificationMethods != null ? !CollectionUtils.isEqualCollection(participantIdentificationMethods, that.getParticipantIdentificationMethods()) : (that.participantIdentificationMethods != null && !that.participantIdentificationMethods.isEmpty()))
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

    protected class InteractorMitabStoichiometryList extends AbstractListHavingPoperties<Integer> {
        public InteractorMitabStoichiometryList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Integer added) {

            if (getStoichiometry() == null){
                setStoichiometryOnly(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(Integer removed) {

            if (isEmpty()){
                setStoichiometryOnly(null);
            }
            else if (getStoichiometry() == removed){
                setStoichiometryOnly(getInteractorStoichiometry().iterator().next());
            }
        }

        @Override
        protected void clearProperties() {
            setStoichiometryOnly(null);
        }
    }

    protected class ParticipantIdentificationMethodList extends AbstractListHavingPoperties<CrossReference> {
        public ParticipantIdentificationMethodList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            // the method is not set yet
            if (getIdentificationMethod() == null){
                String name = added.getText() != null ? added.getText() : "unknown";
                setIdentificationMethodOnly(new DefaultCvTerm(name, name, added));
            }
            else {
                getIdentificationMethod().getXrefs().add(added);
                // reset shortname
                if (getIdentificationMethod().getMIIdentifier() != null && getIdentificationMethod().getMIIdentifier().equals(added.getId())){
                    String name = added.getText();

                    if (name != null){
                        getIdentificationMethod().setShortName(name);
                    }
                    else {
                        resetIdentificationMethodNameFromMiReferences();
                        if (getIdentificationMethod().getShortName().equals("unknown")){
                            resetIdentificationMethodNameFromFirstReferences();
                        }
                    }
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            if (getIdentificationMethod() != null){
                getIdentificationMethod().getXrefs().remove(removed);

                if (removed.getText() != null && getIdentificationMethod().getShortName().equals(removed.getText())){
                    if (getIdentificationMethod().getMIIdentifier() != null){
                        resetIdentificationMethodNameFromMiReferences();
                        if (getIdentificationMethod().getShortName().equals("unknown")){
                            resetIdentificationMethodNameFromFirstReferences();
                        }
                    }
                    else {
                        resetIdentificationMethodNameFromFirstReferences();
                    }
                }
            }

            if (isEmpty()){
                setIdentificationMethodOnly(null);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all interactor types and reset current type
            setIdentificationMethodOnly(null);
        }
    }

    protected class BiologicalRoleList extends AbstractListHavingPoperties<CrossReference> {
        public BiologicalRoleList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            if (getBiologicalRole() == null){
                String name = added.getText() != null ? added.getText() : "unknown";
                setBiologicalRoleOnly(new DefaultCvTerm(name, name, added));
            }
            // it was a UNSPECIFIED role, needs to clear it
            else if ((size() == 1 || size() == 2) && Participant.UNSPECIFIED_ROLE.equalsIgnoreCase(getBiologicalRole().getShortName().trim()) && (added.getText() == null || !Participant.UNSPECIFIED_ROLE_MI.equalsIgnoreCase(added.getId()))){
                // remove unspecified method
                CrossReference old = new CrossReferenceImpl(CvTerm.PSI_MI, Participant.UNSPECIFIED_ROLE_MI, Participant.UNSPECIFIED_ROLE);
                removeOnly(old);

                String name = added.getText() != null ? added.getText() : "unknown";

                setBiologicalRoleOnly(new DefaultCvTerm(name, name, added));
            }
            else {
                getBiologicalRole().getXrefs().add(added);
                // reset shortname
                if (getBiologicalRole().getMIIdentifier() != null && getBiologicalRole().getMIIdentifier().equals(added.getId())){
                    String name = added.getText();

                    if (name != null){
                        getBiologicalRole().setShortName(name);
                    }
                    else {
                        resetBiologicalRoleNameFromMiReferences();
                        if (getBiologicalRole().getShortName().equals("unknown")){
                            resetBiologicalRoleNameFromFirstReferences();
                        }
                    }
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            if (getBiologicalRole() != null){
                getBiologicalRole().getXrefs().remove(removed);

                if (removed.getText() != null && getBiologicalRole().getShortName().equals(removed.getText())){
                    if (getBiologicalRole().getMIIdentifier() != null){
                        resetBiologicalRoleNameFromMiReferences();
                        if (getBiologicalRole().getShortName().equals("unknown")){
                            resetBiologicalRoleNameFromFirstReferences();
                        }
                    }
                    else {
                        resetBiologicalRoleNameFromFirstReferences();
                    }
                }
            }

            if (isEmpty()){
                setBiologicalRoleOnly(CvTermFactory.createUnspecifiedRole());
                processNewBiologicalRoleInBiologicalRoleList(getBiologicalRole());
            }
        }

        @Override
        protected void clearProperties() {
            // clear all interactor types and reset current type
            setBiologicalRoleOnly(CvTermFactory.createUnspecifiedRole());
            processNewBiologicalRoleInBiologicalRoleList(getBiologicalRole());
        }
    }

    protected class ExperimentalRoleList extends AbstractListHavingPoperties<CrossReference> {
        public ExperimentalRoleList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            if (getExperimentalRole() == null){
                String name = added.getText() != null ? added.getText() : "unknown";
                setExperimentalRoleOnly(new DefaultCvTerm(name, name, added));
            }
            // it was a UNSPECIFIED role, needs to clear it
            else if ((size() == 1 || size() == 2) && Participant.UNSPECIFIED_ROLE.equalsIgnoreCase(getExperimentalRole().getShortName().trim()) && (added.getText() == null || !Participant.UNSPECIFIED_ROLE_MI.equalsIgnoreCase(added.getId()))){
                // remove unspecified method
                CrossReference old = new CrossReferenceImpl(CvTerm.PSI_MI, Participant.UNSPECIFIED_ROLE_MI, Participant.UNSPECIFIED_ROLE);
                removeOnly(old);

                String name = added.getText() != null ? added.getText() : "unknown";

                setExperimentalRoleOnly(new DefaultCvTerm(name, name, added));
            }
            else {
                getExperimentalRole().getXrefs().add(added);
                // reset shortname
                if (getExperimentalRole().getMIIdentifier() != null && getExperimentalRole().getMIIdentifier().equals(added.getId())){
                    String name = added.getText();

                    if (name != null){
                        getExperimentalRole().setShortName(name);
                    }
                    else {
                        resetExperimentalRoleNameFromMiReferences();
                        if (getExperimentalRole().getShortName().equals("unknown")){
                            resetExperimentalRoleNameFromFirstReferences();
                        }
                    }
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            if (getExperimentalRole() != null){
                getExperimentalRole().getXrefs().remove(removed);

                if (removed.getText() != null && getExperimentalRole().getShortName().equals(removed.getText())){
                    if (getExperimentalRole().getMIIdentifier() != null){
                        resetExperimentalRoleNameFromMiReferences();
                        if (getExperimentalRole().getShortName().equals("unknown")){
                            resetExperimentalRoleNameFromFirstReferences();
                        }
                    }
                    else {
                        resetExperimentalRoleNameFromFirstReferences();
                    }
                }
            }

            if (isEmpty()){
                setExperimentalRoleOnly(CvTermFactory.createUnspecifiedRole());
                processNewExperimentalRoleInExperimentalRoleList(getExperimentalRole());
            }
        }

        @Override
        protected void clearProperties() {
            setExperimentalRoleOnly(CvTermFactory.createUnspecifiedRole());
            processNewExperimentalRoleInExperimentalRoleList(getExperimentalRole());
        }
    }
}
