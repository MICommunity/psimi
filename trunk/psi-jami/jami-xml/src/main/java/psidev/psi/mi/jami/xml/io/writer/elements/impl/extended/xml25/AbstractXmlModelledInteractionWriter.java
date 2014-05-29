package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlExtendedInteractionWriter;
import psidev.psi.mi.jami.xml.model.extension.ExtendedPsiXmlInteraction;
import psidev.psi.mi.jami.xml.model.extension.XmlExperiment;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.*;

/**
 * Abstract class for XML 2.5 writers of modelled interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractXmlModelledInteractionWriter<I extends ModelledInteraction>
        extends psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.AbstractXmlModelledInteractionWriter<I>
        implements PsiXmlExtendedInteractionWriter<I> {

    public AbstractXmlModelledInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);

    }

    @Override
    protected void initialiseExperimentWriter(){
        super.setExperimentWriter(new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlExperimentWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseDefaultExperiment() {
        super.initialiseDefaultExperiment();
        getParameterWriter().setDefaultExperiment(getDefaultExperiment());
    }

    @Override
    public void setDefaultExperiment(Experiment defaultExperiment) {
        super.setDefaultExperiment(defaultExperiment);
        getParameterWriter().setDefaultExperiment(defaultExperiment);
    }

    @Override
    public List<Experiment> extractDefaultExperimentsFrom(I interaction) {
        if (!interaction.getCooperativeEffects().isEmpty()){
            List<Experiment> expList = new ArrayList<Experiment>(interaction.getCooperativeEffects().size());
            CooperativeEffect effect = interaction.getCooperativeEffects().iterator().next();
            if (!effect.getCooperativityEvidences().isEmpty()){
                CooperativityEvidence evidence = effect.getCooperativityEvidences().iterator().next();
                // set first experiment as default experiment
                if (evidence.getPublication() != null){
                    Experiment exp = new XmlExperiment(evidence.getPublication());
                    ((XmlExperiment)exp).setFullName(evidence.getPublication().getTitle());
                    expList.add(exp);
                }
            }
            return expList;
        }
        else{
            return Collections.singletonList(getDefaultExperiment());
        }
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
                    exp = new XmlExperiment(evidence.getPublication());
                    ((XmlExperiment)exp).setFullName(evidence.getPublication().getTitle());
                }
            }
        }
        return exp != null ? exp : getDefaultExperiment() ;
    }

    @Override
    protected void writeInteractionType(I object) throws XMLStreamException {
        if (object instanceof ExtendedPsiXmlInteraction){
            ExtendedPsiXmlInteraction xmlInteraction = (ExtendedPsiXmlInteraction)object;
            if (!xmlInteraction.getInteractionTypes().isEmpty()){
                for (Object type : xmlInteraction.getInteractionTypes()){
                    getInteractionTypeWriter().write((CvTerm)type,"interactionType");
                }
            }
        }
        else{
            super.writeInteractionType(object);
        }
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
                        NamedExperiment exp = new XmlExperiment(evidence.getPublication());
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
                        NamedExperiment exp = new XmlExperiment(evidence.getPublication());
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

    protected void writeInferredInteractions(I object) throws XMLStreamException {
        Collection<Set<Feature>> inferredInteractions = collectInferredInteractionsFrom(object);
        if (inferredInteractions != null && !inferredInteractions.isEmpty()){
            getStreamWriter().writeStartElement("inferredInteractionList");
            for (Set<Feature> inferred : inferredInteractions){
                getInferredInteractionWriter().write(inferred);
            }
            getStreamWriter().writeEndElement();
        }
    }

    protected void writeCooperativeEffect(I object, boolean startAttributeList) throws XMLStreamException {
        if (startAttributeList){
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
        }

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

        if (startAttributeList){
            // write end attributeList
            getStreamWriter().writeEndElement();
        }
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

    @Override
    protected void writeStartInteraction() throws XMLStreamException {
        getStreamWriter().writeStartElement("interaction");
    }
}
