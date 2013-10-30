package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Xml wrapper for interaction evidences used as complex
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */

public class XmlInteractionEvidenceWrapper extends XmlComplex implements Complex {
    private InteractionEvidence interactionEvidence;

    public XmlInteractionEvidenceWrapper(InteractionEvidence interaction){
        if (interaction == null){
            throw new IllegalArgumentException("The complex wrapper needs a non null InteractionEvidence");
        }
        this.interactionEvidence = interaction;
    }

    public Date getUpdatedDate() {
        return this.interactionEvidence.getUpdatedDate();
    }

    public void setUpdatedDate(Date updated) {
        this.interactionEvidence.setUpdatedDate(updated);
    }

    public Date getCreatedDate() {
        return this.interactionEvidence.getCreatedDate();
    }

    public void setCreatedDate(Date created) {
        this.interactionEvidence.setCreatedDate(created);
    }

    public CvTerm getInteractionType() {
        return this.interactionEvidence.getInteractionType();
    }

    public void setInteractionType(CvTerm term) {
        this.interactionEvidence.setInteractionType(term);
    }

    public Source getSource() {
        if (this.interactionEvidence.getExperiment() != null){
            Experiment exp =this.interactionEvidence.getExperiment();
            if (exp.getPublication() != null){
                return exp.getPublication().getSource();
            }
        }
        return null;
    }

    public void setSource(Source source) {
        if (this.interactionEvidence.getExperiment() != null){
            Experiment exp =this.interactionEvidence.getExperiment();
            if (exp.getPublication() != null){
                exp.getPublication().setSource(source);
            }
            else{
                exp.setPublicationAndAddExperiment(new BibRef());
                exp.getPublication().setSource(source);
            }
        }
        else{
            this.interactionEvidence.setExperimentAndAddInteractionEvidence(new XmlExperiment(new BibRef()));
            this.interactionEvidence.getExperiment().getPublication().setSource(source);
        }
    }

    @Override
    public Collection<Annotation> getAnnotations() {
        return this.interactionEvidence.getAnnotations();
    }

    @Override
    public Collection<Checksum> getChecksums() {
        return this.interactionEvidence.getChecksums();
    }

    @Override
    public Collection<Xref> getXrefs() {
        return this.interactionEvidence.getXrefs();
    }

    @Override
    public Collection<Xref> getIdentifiers() {
        return this.interactionEvidence.getIdentifiers();
    }

    @Override
    public String getShortName() {
        return this.interactionEvidence.getShortName() != null ? this.interactionEvidence.getShortName() : PsiXmlUtils.UNSPECIFIED;
    }

    @Override
    public void setShortName(String name) {
        this.interactionEvidence.setShortName(name);
    }

    @Override
    public String getRigid() {
        return this.interactionEvidence.getRigid();
    }

    @Override
    public void setRigid(String rigid) {
        this.interactionEvidence.setRigid(rigid);
    }

    @Override
    protected void initialiseModelledParameters(){
        Collection<ModelledParameter> modelledParameters = new ArrayList<ModelledParameter>(this.interactionEvidence.getParameters().size());
        for (Parameter param : this.interactionEvidence.getParameters()){
            modelledParameters.add(new XmlModelledParameterWrapper(param));
        }
        super.initialiseModelledParametersWith(modelledParameters);
    }

    @Override
    protected void initialiseModelledConfidences(){
        Collection<ModelledConfidence> modelledConfidences = new ArrayList<ModelledConfidence>(this.interactionEvidence.getConfidences().size());
        for (Confidence conf : this.interactionEvidence.getConfidences()){
            modelledConfidences.add(new XmlModelledConfidenceWrapper(conf));
        }
        super.initialiseModelledConfidencesWith(modelledConfidences);
    }

    @Override
    protected void initialiseParticipants(){
        Collection<ModelledParticipant> modelledParticipants = new ArrayList<ModelledParticipant>(this.interactionEvidence.getParticipants().size());
        for (ParticipantEvidence part : this.interactionEvidence.getParticipants()){
            modelledParticipants.add(new XmlParticipantEvidenceWrapper(part, this));
        }
        super.initialiseParticipantsWith(modelledParticipants);
    }
}
