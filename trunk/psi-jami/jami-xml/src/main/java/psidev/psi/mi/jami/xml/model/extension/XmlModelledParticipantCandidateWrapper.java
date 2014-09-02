package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.listener.EntityInteractorChangeListener;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Wrapper for XmlParticipant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/10/13</pre>
 */

public class XmlModelledParticipantCandidateWrapper implements ModelledParticipant {

    private ModelledParticipantCandidate participant;
    private ModelledInteraction parent;
    private SynchronizedFeatureList modelledFeatures;

    private Collection<Alias> aliases;
    private Collection<Xref> xrefs;
    private Collection<Annotation> annotations;

    private CvTerm bioRole;

    public XmlModelledParticipantCandidateWrapper(ModelledParticipantCandidate part, ModelledInteraction wrapper){
        if (part == null){
            throw new IllegalArgumentException("A participant candidate wrapper needs a non null participant candidate");
        }
        this.participant = part;
        this.parent = wrapper;
    }

    @Override
    public Collection<Alias> getAliases() {
        if (this.participant.getParentPool() != null){
            return this.participant.getParentPool().getAliases();
        }
        else if (this.aliases == null){
            this.aliases = new ArrayList<Alias>();
        }
        return this.aliases;
    }

    @Override
    public Collection<Xref> getXrefs() {
        if (this.participant.getParentPool() != null){
            return this.participant.getParentPool().getXrefs();
        }
        else if (this.xrefs == null){
            this.xrefs = new ArrayList<Xref>();
        }
        return this.xrefs;
    }

    @Override
    public Interactor getInteractor() {
        return this.participant.getInteractor();
    }

    @Override
    public void setInteractor(Interactor interactor) {
        this.participant.setInteractor(interactor);
    }

    @Override
    public Collection<CausalRelationship> getCausalRelationships() {
        return this.participant.getCausalRelationships();
    }

    @Override
    public Collection<Annotation> getAnnotations() {
        if (this.participant.getParentPool() != null){
            return this.participant.getParentPool().getAnnotations();
        }
        else if (this.annotations == null){
            this.annotations = new ArrayList<Annotation>();
        }
        return this.annotations;
    }

    @Override
    public Stoichiometry getStoichiometry() {
        if (this.participant.getStoichiometry() == null
                && this.participant.getParentPool() != null){
            return this.participant.getParentPool().getStoichiometry();
        }
        return this.participant.getStoichiometry();
    }

    @Override
    public void setStoichiometry(Integer stoichiometry) {
        this.participant.setStoichiometry(stoichiometry);
    }

    @Override
    public void setStoichiometry(Stoichiometry stoichiometry) {
        this.participant.setStoichiometry(stoichiometry);
    }

    @Override
    public Collection<ModelledFeature> getFeatures() {
        if (this.modelledFeatures == null){
            initialiseFeatures();
        }
        return this.modelledFeatures;
    }

    @Override
    public void setChangeListener(EntityInteractorChangeListener listener) {
        this.participant.setChangeListener(listener);
    }

    @Override
    public boolean addFeature(ModelledFeature feature) {
        if (feature == null){
            return false;
        }
        if (this.modelledFeatures == null){
            initialiseFeatures();
        }
        if (this.modelledFeatures.add(feature)){
            feature.setParticipant(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFeature(ModelledFeature feature) {
        if (feature == null){
            return false;
        }
        if (this.modelledFeatures == null){
            initialiseFeatures();
        }
        if (this.modelledFeatures.remove(feature)){
            feature.setParticipant(null);
            return true;
        }
        return false;
    }

    @Override
    public boolean addAllFeatures(Collection<? extends ModelledFeature> features) {
        if (features == null){
            return false;
        }

        boolean added = false;
        for (ModelledFeature feature : features){
            if (addFeature(feature)){
                added = true;
            }
        }
        return added;
    }

    @Override
    public boolean removeAllFeatures(Collection<? extends ModelledFeature> features) {
        if (features == null){
            return false;
        }

        boolean added = false;
        for (ModelledFeature feature : features){
            if (removeFeature(feature)){
                added = true;
            }
        }
        return added;
    }

    @Override
    public EntityInteractorChangeListener getChangeListener() {
        return this.participant.getChangeListener();
    }

    @Override
    public CvTerm getBiologicalRole() {
        if (this.bioRole == null && this.participant.getParentPool() == null){
            this.bioRole = new XmlCvTerm(Participant.UNSPECIFIED_ROLE,
                    new XmlXref(CvTermUtils.createPsiMiDatabase(),Interactor.UNKNOWN_INTERACTOR_MI, CvTermUtils.createIdentityQualifier()));
        }
        else if (this.bioRole == null){
            this.bioRole = this.participant.getParentPool().getBiologicalRole();
        }
        return this.bioRole;
    }

    @Override
    public void setBiologicalRole(CvTerm bioRole) {
        this.bioRole = bioRole;
    }

    protected void initialiseFeatures(){
        this.modelledFeatures = new SynchronizedFeatureList();
        if (!this.participant.getFeatures().isEmpty()){
            for (Feature feature : this.participant.getFeatures()){
                this.modelledFeatures.addOnly(new XmlFeatureWrapper(feature, this));
            }
        }
        // add parent pool features
        if (participant.getParentPool() != null){
            for (Object feature : this.participant.getParentPool().getFeatures()){
                this.modelledFeatures.addOnly(new XmlFeatureWrapper((Feature)feature, this));
            }
        }
    }

    @Override
    public String toString() {
        return this.participant.toString();
    }

    public ModelledParticipantCandidate getWrappedParticipant(){
        return this.participant;
    }

    @Override
    public void setInteractionAndAddParticipant(ModelledInteraction interaction) {
        if (this.parent != null){
            this.parent.removeParticipant(this);
        }

        if (interaction != null){
            interaction.addParticipant(this);
        }
    }

    @Override
    public ModelledInteraction getInteraction() {
        if (this.parent == null &&
                this.participant.getParentPool() != null &&
                this.participant.getParentPool().getInteraction() instanceof ExtendedPsiXmlModelledInteraction){
            this.parent = new XmlModelledInteractionComplexWrapper((ExtendedPsiXmlModelledInteraction)this.participant.
                    getParentPool().getInteraction());
        }
        return this.parent;
    }

    @Override
    public void setInteraction(ModelledInteraction interaction) {
        this.parent = interaction;
    }

    ////////////////////////////////////// classes
    private class SynchronizedFeatureList extends AbstractListHavingProperties<ModelledFeature> {

        private SynchronizedFeatureList() {
        }

        @Override
        protected void processAddedObjectEvent(ModelledFeature added) {
            participant.getFeatures().add(added);
        }

        @Override
        protected void processRemovedObjectEvent(ModelledFeature removed) {
            participant.getFeatures().remove(removed);
        }

        @Override
        protected void clearProperties() {
            participant.getFeatures().clear();
        }
    }
}