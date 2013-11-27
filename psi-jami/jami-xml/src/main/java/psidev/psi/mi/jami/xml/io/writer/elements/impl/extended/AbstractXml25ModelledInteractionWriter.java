package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultNamedExperiment;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.extension.XmlExperiment;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25ConfidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25ParameterWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Abstract class for XML 2.5 writers of modelled interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractXml25ModelledInteractionWriter<I extends ModelledInteraction, P extends ModelledParticipant> extends AbstractXml25InteractionWriter<I, P> {
    private PsiXml25ElementWriter<Confidence> confidenceWriter;
    private PsiXml25ParameterWriter parameterWriter;

    public AbstractXml25ModelledInteractionWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex, PsiXml25ParticipantWriter<P> participantWriter) {
        super(writer, objectIndex, participantWriter);
        this.confidenceWriter = new Xml25ConfidenceWriter(writer);
        this.parameterWriter = new Xml25ParameterWriter(writer, objectIndex);
    }

    protected AbstractXml25ModelledInteractionWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                                     PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25XrefWriter primaryRefWriter,
                                                     PsiXml25XrefWriter secondaryRefWriter, PsiXml25ExperimentWriter experimentWriter,
                                                     PsiXml25ParticipantWriter<P> participantWriter, PsiXml25ElementWriter<InferredInteraction> inferredInteractionWriter,
                                                     PsiXml25ElementWriter<CvTerm> interactionTypeWriter, PsiXml25ElementWriter<Confidence> confidenceWriter,
                                                     PsiXml25ParameterWriter parameterWriter, PsiXml25ElementWriter<Annotation> attributeWriter,
                                                     PsiXml25ElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, experimentWriter,
                participantWriter, inferredInteractionWriter, interactionTypeWriter, attributeWriter,
                checksumWriter);
        this.confidenceWriter = confidenceWriter != null ? confidenceWriter : new Xml25ConfidenceWriter(writer);
        this.parameterWriter = parameterWriter != null ? parameterWriter : new Xml25ParameterWriter(writer, objectIndex);
    }

    @Override
    protected void initialiseDefaultExperiment() {
        setDefaultExperiment(new XmlExperiment(new DefaultPublication("Mock publication and experiment for modelled interactions that are not interaction evidences.", (String) null, (Date) null)));
        this.parameterWriter.setDefaultExperiment(getDefaultExperiment());
    }

    @Override
    public void setDefaultExperiment(Experiment defaultExperiment) {
        super.setDefaultExperiment(defaultExperiment);
        this.parameterWriter.setDefaultExperiment(defaultExperiment);
    }

    @Override
    public Experiment extractDefaultExperimentFrom(I interaction) {
        Experiment exp = null;
        if (!interaction.getCooperativeEffects().isEmpty()){
            CooperativeEffect effect = interaction.getCooperativeEffects().iterator().next();
            if (!effect.getCooperativityEvidences().isEmpty()){
                CooperativityEvidence evidence = effect.getCooperativityEvidences().iterator().next();
                // set first experiment as default experiment
                if (evidence.getPublication() != null){
                    exp = new DefaultNamedExperiment(evidence.getPublication());
                    ((NamedExperiment)exp).setFullName(evidence.getPublication().getTitle());
                }
            }
        }
        return exp != null ? exp : getDefaultExperiment() ;
    }

    @Override
    public List<Experiment> extractDefaultExperimentsFrom(I interaction) {
        Experiment exp = extractDefaultExperimentFrom(interaction);
        return exp != null ? Collections.singletonList(exp) : Collections.singletonList(getDefaultExperiment());
    }

    @Override
    protected void writeAvailability(I object) {
        // nothing to do
    }

    @Override
    protected void writeOtherAttributes(I object) {
        // nothing to do
    }

    @Override
    protected void writeModelled(I object) {
        // nothing to do
    }

    protected void writeExperimentRef(I object) throws XMLStreamException {
        // write experimental evidences
        if (!object.getCooperativeEffects().isEmpty()){
            CooperativeEffect effect = object.getCooperativeEffects().iterator().next();
            if (!effect.getCooperativityEvidences().isEmpty()){
                getStreamWriter().writeStartElement("experimentList");
                for (CooperativityEvidence evidence : effect.getCooperativityEvidences()){
                    // set first experiment as default experiment
                    if (evidence.getPublication() != null){
                        NamedExperiment exp = new DefaultNamedExperiment(evidence.getPublication());
                        exp.setFullName(evidence.getPublication().getTitle());
                        getStreamWriter().writeStartElement("experimentRef");
                        getStreamWriter().writeCharacters(Integer.toString(getObjectIndex().extractIdForExperiment(exp)));
                        getStreamWriter().writeEndElement();
                    }
                }
                // write end experiment list
                getStreamWriter().writeEndElement();
            }
            else {
                super.writeExperimentRef();
            }
        }
        else {
            super.writeExperimentRef();
        }
    }

    protected void writeExperimentDescription(I object) throws XMLStreamException {
        // write experimental evidences
        if (!object.getCooperativeEffects().isEmpty()){
            CooperativeEffect effect = object.getCooperativeEffects().iterator().next();
            if (!effect.getCooperativityEvidences().isEmpty()){
                getStreamWriter().writeStartElement("experimentList");
                for (CooperativityEvidence evidence : effect.getCooperativityEvidences()){
                    // set first experiment as default experiment
                    if (evidence.getPublication() != null){
                        NamedExperiment exp = new DefaultNamedExperiment(evidence.getPublication());
                        exp.setFullName(evidence.getPublication().getTitle());
                        getExperimentWriter().write(exp);
                    }
                }
                // write end experiment list
                getStreamWriter().writeEndElement();
            }
            else {
                super.writeExperimentDescription();
            }
        }
        else {
            super.writeExperimentDescription();
        }
    }

    @Override
    protected void writeParameters(I object) throws XMLStreamException {
        // write parameters
        if (!object.getModelledParameters().isEmpty()){
            // write start parameter list
            getStreamWriter().writeStartElement("parameterList");
            for (Object ann : object.getModelledParameters()){
                this.parameterWriter.write((ModelledParameter)ann);
            }
            // write end parameterList
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeConfidences(I object) throws XMLStreamException {
        // write confidences
        if (!object.getModelledConfidences().isEmpty()){
            // write start confidence list
            getStreamWriter().writeStartElement("confidenceList");
            for (Object ann : object.getModelledConfidences()){
                this.confidenceWriter.write((ModelledConfidence)ann);
            }
            // write end confidenceList
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void writeAttributes(I object) throws XMLStreamException {
        // write attributes
        if (!object.getAnnotations().isEmpty()){
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            // write existing attributes
            for (Object ann : object.getAnnotations()){
                getAttributeWriter().write((Annotation) ann);
            }
            // write cooperative effect
            // can only write the FIRST cooperative effect
            if (!object.getCooperativeEffects().isEmpty()){
                writeCooperativeEffect(object);
            }
            // write end attributeList
            getStreamWriter().writeEndElement();
        }
        // write cooperative effects
        else if (!object.getCooperativeEffects().isEmpty()){
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            // write cooperative effects
            writeCooperativeEffect(object);
            // write end attributeList
            getStreamWriter().writeEndElement();
        }
    }

    protected void writeCooperativeEffect(I object) throws XMLStreamException {
        CooperativeEffect effect = object.getCooperativeEffects().iterator().next();
        // write mechanism first
        if (effect instanceof Preassembly){
            writeCooperativeEffectAttribute(CooperativeEffect.PREASSEMBLY, CooperativeEffect.PREASSEMBLY_ID, null);
        }
        else if (effect instanceof Allostery){
            writeCooperativeEffectAttribute(CooperativeEffect.ALLOSTERY, CooperativeEffect.ALLOSTERY_ID, null);
            Allostery allostery = (Allostery)effect;

            // write allosteric molecule
            writeCooperativeEffectAttribute(CooperativeEffect.ALLOSTERIC_MOLECULE, CooperativeEffect.ALLOSTERIC_MOLECULE_ID,
                    Integer.toString(getObjectIndex().extractIdForParticipant(allostery.getAllostericMolecule())));
            // write allosteric effector
            AllostericEffector effector = allostery.getAllostericEffector();
            switch (effector.getEffectorType()){
                case molecule:
                    MoleculeEffector moleculeEffector = (MoleculeEffector)effector;
                    writeCooperativeEffectAttribute(CooperativeEffect.ALLOSTERIC_EFFECTOR, CooperativeEffect.ALLOSTERIC_EFFECTOR_ID,
                            Integer.toString(getObjectIndex().extractIdForParticipant(moleculeEffector.getMolecule())));
                    break;
                case feature_modification:
                    FeatureModificationEffector featureEffector = (FeatureModificationEffector)effector;
                    writeCooperativeEffectAttribute(CooperativeEffect.ALLOSTERIC_PTM, CooperativeEffect.ALLOSTERIC_PTM_ID,
                            Integer.toString(getObjectIndex().extractIdForFeature(featureEffector.getFeatureModification())));
                    break;
                default:
                    break;
            }
            // write allostery type
            if (allostery.getAllosteryType() != null){
                writeCooperativeEffectAttribute(allostery.getAllosteryType().getShortName(), allostery.getAllosteryType().getMIIdentifier(), null);
            }
            // write allostery mechanism
            if (allostery.getAllostericMechanism() != null){
                writeCooperativeEffectAttribute(allostery.getAllostericMechanism().getShortName(), allostery.getAllostericMechanism().getMIIdentifier(), null);
            }
        }
        // write outcome
        writeCooperativeEffectAttribute(effect.getOutCome().getShortName(), effect.getOutCome().getMIIdentifier(), null);
        // write response
        if (effect.getResponse() != null){
            writeCooperativeEffectAttribute(effect.getResponse().getShortName(), effect.getResponse().getMIIdentifier(), null);
        }
        // write affected interactions
        if (!effect.getAffectedInteractions().isEmpty()){
            for (ModelledInteraction affected : effect.getAffectedInteractions()){
                getObjectIndex().registerSubComplex(affected);
                writeCooperativeEffectAttribute(CooperativeEffect.AFFECTED_INTERACTION, CooperativeEffect.AFFECTED_INTERACTION_ID, Integer.toString(getObjectIndex().extractIdForInteraction(affected)));
            }
        }
        // write cooperative value
        if (effect.getCooperativeEffectValue() != null){
            writeCooperativeEffectAttribute(CooperativeEffect.COOPERATIVE_EFFECT_VALUE, CooperativeEffect.COOPERATIVE_EFFECT_VALUE_ID, effect.getCooperativeEffectValue().toString());
        }
    }

    @Override
    protected void writeNegative(I object) {
        // nothing to do
    }

    protected void writeCooperativeEffectAttribute(String name, String nameAc, String value) throws XMLStreamException {
        // write start
        getStreamWriter().writeStartElement("attribute");
        // write topic
        getStreamWriter().writeAttribute("name", name);
        if (nameAc != null){
            getStreamWriter().writeAttribute("nameAc", nameAc);
        }
        // write description
        if (value != null){
            getStreamWriter().writeCharacters(value);
        }

        // write end attribute
        getStreamWriter().writeEndElement();
    }
}
