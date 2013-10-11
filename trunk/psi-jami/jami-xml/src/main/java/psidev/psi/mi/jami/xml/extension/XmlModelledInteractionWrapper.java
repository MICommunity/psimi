package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;
import java.util.Date;

/**
 * Wrapper for complexes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlTransient
public class XmlModelledInteractionWrapper extends XmlComplex implements Complex{

    private ModelledInteraction modelledInteraction;

    public XmlModelledInteractionWrapper(ModelledInteraction modelled){
       if (modelled == null){
           throw new IllegalArgumentException("The complex wrapper needs a non null ModelledInteraction");
       }
        this.modelledInteraction = modelled;
    }

    public Date getUpdatedDate() {
        return this.modelledInteraction.getUpdatedDate();
    }

    public void setUpdatedDate(Date updated) {
        this.modelledInteraction.setUpdatedDate(updated);
    }

    public Date getCreatedDate() {
        return this.modelledInteraction.getCreatedDate();
    }

    public void setCreatedDate(Date created) {
        this.modelledInteraction.setCreatedDate(created);
    }

    public CvTerm getInteractionType() {
        return this.modelledInteraction.getInteractionType();
    }

    public void setInteractionType(CvTerm term) {
        this.modelledInteraction.setInteractionType(term);
    }

    public boolean addParticipant(ModelledParticipant part) {
        return this.modelledInteraction.addParticipant(part);
    }

    public boolean removeParticipant(ModelledParticipant part) {
        return this.modelledInteraction.removeParticipant(part);
    }

    public boolean addAllParticipants(Collection<? extends ModelledParticipant> participants) {
        return this.modelledInteraction.addAllParticipants(participants);
    }

    public boolean removeAllParticipants(Collection<? extends ModelledParticipant> participants) {
        return this.modelledInteraction.removeAllParticipants(participants);
    }

    public Collection<ModelledParticipant> getParticipants() {
        return this.modelledInteraction.getParticipants();
    }

    public Collection<InteractionEvidence> getInteractionEvidences() {
        return this.modelledInteraction.getInteractionEvidences();
    }

    public Source getSource() {
        return this.modelledInteraction.getSource();
    }

    public void setSource(Source source) {
        this.modelledInteraction.setSource(source);
    }

    public Collection<ModelledConfidence> getModelledConfidences() {
        return this.modelledInteraction.getModelledConfidences();
    }

    public Collection<ModelledParameter> getModelledParameters() {
        return this.modelledInteraction.getModelledParameters();
    }

    public Collection<CooperativeEffect> getCooperativeEffects() {
        return this.modelledInteraction.getCooperativeEffects();
    }

    @Override
    public Collection<Annotation> getAnnotations() {
        return this.modelledInteraction.getAnnotations();
    }

    @Override
    public Collection<Checksum> getChecksums() {
        return this.modelledInteraction.getChecksums();
    }

    @Override
    public Collection<Xref> getXrefs() {
        return this.modelledInteraction.getXrefs();
    }

    @Override
    public Collection<Xref> getIdentifiers() {
        return this.modelledInteraction.getIdentifiers();
    }

    @Override
    public String getShortName() {
        return this.modelledInteraction.getShortName() != null ? this.modelledInteraction.getShortName() : PsiXmlUtils.UNSPECIFIED;
    }

    @Override
    public void setShortName(String name) {
        this.modelledInteraction.setShortName(name);
    }

    @Override
    public String getRigid() {
        return this.modelledInteraction.getRigid();
    }

    @Override
    public void setRigid(String rigid) {
        this.modelledInteraction.setRigid(rigid);
    }
}
