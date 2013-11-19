package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultNamedExperiment;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ParameterWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25ConfidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25ParameterWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;

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

    public AbstractXml25ModelledInteractionWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex, PsiXml25ElementWriter<P> participantWriter) {
        super(writer, objectIndex, participantWriter);
        this.confidenceWriter = new Xml25ConfidenceWriter(writer);
        this.parameterWriter = new Xml25ParameterWriter(writer, objectIndex);
    }

    protected AbstractXml25ModelledInteractionWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex,
                                                     PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                                     PsiXml25ElementWriter<P> participantWriter, PsiXml25ElementWriter<CvTerm> interactionTypeWriter,
                                                     PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Alias> aliasWriter,
                                                     PsiXml25ElementWriter<InferredInteraction> inferredInteractionWriter, PsiXml25ElementWriter<Experiment> experimentWriter,
                                                     PsiXml25ElementWriter<Confidence> confidenceWriter, PsiXml25ParameterWriter parameterWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, participantWriter, interactionTypeWriter, attributeWriter, experimentWriter, aliasWriter, inferredInteractionWriter);
        this.confidenceWriter = confidenceWriter != null ? confidenceWriter : new Xml25ConfidenceWriter(writer);
        this.parameterWriter = parameterWriter != null ? parameterWriter : new Xml25ParameterWriter(writer, objectIndex);
    }

    @Override
    protected void initialiseDefaultExperiment() {
        super.initialiseDefaultExperiment();
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
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
                getStreamWriter().writeStartElement("experimentList");
                for (CooperativityEvidence evidence : effect.getCooperativityEvidences()){
                    // set first experiment as default experiment
                    if (evidence.getPublication() != null){
                        NamedExperiment exp = new DefaultNamedExperiment(evidence.getPublication());
                        exp.setFullName(evidence.getPublication().getTitle());
                        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
                        getStreamWriter().writeStartElement("experimentRef");
                        getStreamWriter().writeCharacters(Integer.toString(getObjectIndex().extractIdForExperiment(exp)));
                        getStreamWriter().writeEndElement();
                        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
                    }
                }
                // write end experiment list
                getStreamWriter().writeEndElement();
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
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
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
                getStreamWriter().writeStartElement("experimentList");
                for (CooperativityEvidence evidence : effect.getCooperativityEvidences()){
                    // set first experiment as default experiment
                    if (evidence.getPublication() != null){
                        NamedExperiment exp = new DefaultNamedExperiment(evidence.getPublication());
                        exp.setFullName(evidence.getPublication().getTitle());
                        getExperimentWriter().write(exp);
                        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
                    }
                }
                // write end experiment list
                getStreamWriter().writeEndElement();
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
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
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write start parameter list
            getStreamWriter().writeStartElement("parameterList");
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (Object ann : object.getModelledParameters()){
                this.parameterWriter.write((ModelledParameter)ann);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end parameterList
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    @Override
    protected void writeConfidences(I object) throws XMLStreamException {
        // write confidences
        if (!object.getModelledConfidences().isEmpty()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write start confidence list
            getStreamWriter().writeStartElement("confidenceList");
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (Object ann : object.getModelledConfidences()){
                this.confidenceWriter.write((ModelledConfidence)ann);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end confidenceList
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    @Override
    protected void writeAttributes(I object) throws XMLStreamException {
        // write attributes
        if (!object.getAnnotations().isEmpty()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write existing attributes
            for (Object ann : object.getAnnotations()){
                getAttributeWriter().write((Annotation) ann);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write cooperative effect
            // can only write the FIRST cooperative effect
            if (!object.getCooperativeEffects().isEmpty()){
                writeCooperativeEffect(object);
            }
            // write end attributeList
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
        // write cooperative effects
        else if (!object.getCooperativeEffects().isEmpty()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write cooperative effects
            writeCooperativeEffect(object);
            // write end attributeList
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
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
        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
    }
}
