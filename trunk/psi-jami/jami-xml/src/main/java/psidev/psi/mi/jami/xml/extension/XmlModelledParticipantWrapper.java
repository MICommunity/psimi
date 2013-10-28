package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.listener.ParticipantInteractorChangeListener;
import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Wrapper for Xml participants
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */
@XmlTransient
public class XmlModelledParticipantWrapper extends XmlModelledParticipant{

    private ParticipantEvidence participant;

    public XmlModelledParticipantWrapper(ParticipantEvidence part, XmlInteractionEvidenceWrapper wrapper){
        if (part == null){
            throw new IllegalArgumentException("A participant evidence wrapper needs a non null participant");
        }
        this.participant = part;
        setInteraction(wrapper);
    }

    @Override
    public Collection<Alias> getAliases() {
        return this.participant.getAliases();
    }

    @Override
    public Collection<Xref> getXrefs() {
        return this.participant.getXrefs();
    }

    @Override
    public Interactor getInteractor() {
        return this.participant.getInteractor();
    }

    @Override
    public CausalRelationship getCausalRelationship() {
        return this.participant.getCausalRelationship();
    }

    @Override
    public void setCausalRelationship(CausalRelationship relationship) {
        this.participant.setCausalRelationship(relationship);
    }

    @Override
    public Collection<Annotation> getAnnotations() {
        return this.participant.getAnnotations();
    }

    @Override
    public Stoichiometry getStoichiometry() {
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
    public void setChangeListener(ParticipantInteractorChangeListener listener) {
        this.participant.setChangeListener(listener);
    }

    @Override
    public ParticipantInteractorChangeListener getChangeListener() {
        return this.participant.getChangeListener();
    }

    @Override
    public CvTerm getBiologicalRole() {
        return this.participant.getBiologicalRole();
    }

    @Override
    public void setBiologicalRole(CvTerm bioRole) {
        this.participant.setBiologicalRole(bioRole);
    }

    @Override
    protected void initialiseFeatures() {
        ArrayList<ModelledFeature> modelledFeatures = new ArrayList<ModelledFeature>(this.participant.getFeatures().size());
        for (FeatureEvidence part : this.participant.getFeatures()){
            modelledFeatures.add(new XmlModelledFeatureWrapper(part, this));
        }
        super.initialiseFeaturesWith(modelledFeatures);
    }
}
