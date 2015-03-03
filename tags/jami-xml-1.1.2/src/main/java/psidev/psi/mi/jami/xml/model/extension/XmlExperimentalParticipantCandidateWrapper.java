package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.listener.EntityInteractorChangeListener;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.XmlEntryContext;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Wrapper for Xml participants candidates
 *
 * Addeding new modelled feature to this participant will not add new feature evidences to the wrapped participant evidence as they are incompatibles.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */
@XmlTransient
public class XmlExperimentalParticipantCandidateWrapper implements ModelledParticipantCandidate, ExtendedPsiXmlEntity<ModelledFeature>,
        FileSourceContext {

    private ExperimentalParticipantCandidate participant;
    private Collection<ModelledFeature> modelledFeatures;
    private ModelledParticipantPool parent;

    public XmlExperimentalParticipantCandidateWrapper(ExperimentalParticipantCandidate part, ModelledParticipantPool wrapper){
        if (part == null){
            throw new IllegalArgumentException("A experimental participant candidate wrapper needs a non null participant");
        }
        this.participant = part;
        setParentPool(wrapper);
        // register feature as complex participant
        XmlEntryContext.getInstance().registerComplexParticipant(((ExtendedPsiXmlEntity)this.participant).getId(), this);
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
    public String toString() {
        return this.participant.toString();
    }

    @Override
    public ModelledParticipantPool getParentPool() {
        if (this.parent == null &&
                this.participant.getParentPool() != null &&
                this.participant.getParentPool() instanceof ExperimentalParticipantPool){
            this.parent = new XmlExperimentalParticipantPoolWrapper(this.participant.
                    getParentPool(), null);
        }
        return this.parent;
    }

    @Override
    public void setParentPool(ModelledParticipantPool pool) {
        this.parent = pool;
    }

    public ExperimentalParticipantCandidate getWrappedParticipant(){
        return this.participant;
    }

    protected void initialiseFeatures(){
        this.modelledFeatures = new ArrayList<ModelledFeature>();
        for (FeatureEvidence feature : this.participant.getFeatures()){
            this.modelledFeatures.add(new XmlFeatureEvidenceWrapper((ExtendedPsiXmlFeatureEvidence)feature, this));
        }
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        return ((FileSourceContext)participant).getSourceLocator();
    }

    @Override
    public void setSourceLocator(FileSourceLocator locator) {
        ((FileSourceContext)participant).setSourceLocator(locator);
    }

    @Override
    public int getId() {
        return ((ExtendedPsiXmlEntity)participant).getId();
    }

    @Override
    public void setId(int id) {
        ((ExtendedPsiXmlEntity)participant).setId(id);
        // register participant  as complex participant
        XmlEntryContext.getInstance().registerComplexParticipant(((ExtendedPsiXmlEntity)this.participant).getId(), this);
    }
}
