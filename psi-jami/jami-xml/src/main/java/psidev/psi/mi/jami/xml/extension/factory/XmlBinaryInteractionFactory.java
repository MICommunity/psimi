package psidev.psi.mi.jami.xml.extension.factory;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.factory.BinaryInteractionFactory;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.clone.InteractionCloner;
import psidev.psi.mi.jami.xml.extension.XmlBasicInteraction;
import psidev.psi.mi.jami.xml.extension.XmlInteractionEvidence;
import psidev.psi.mi.jami.xml.extension.XmlModelledInteraction;
import psidev.psi.mi.jami.xml.extension.binary.*;

/**
 * Xml extension of BinaryInteractionFactory
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public class XmlBinaryInteractionFactory implements BinaryInteractionFactory {

    @Override
    public BinaryInteractionEvidence createSelfBinaryInteractionEvidenceFrom(InteractionEvidence interaction) {
        XmlBinaryInteractionEvidence binary = instantiateNewBinaryInteractionEvidence();
        InteractionCloner.copyAndOverrideParticipantsEvidencesToBinary(interaction, binary, false, true);

        copyXmlInteractionEvidenceProperties(interaction, binary);
        return binary;
    }

    @Override
    public BinaryInteraction createBasicBinaryInteractionFrom(Interaction interaction, Participant p1, Participant p2, CvTerm expansionMethod) {
        XmlBinaryInteraction binary = instantiateNewBinaryInteraction();
        binary.setComplexExpansion(expansionMethod);
        copyBasicXmlInteractionProperties(interaction, binary);
        binary.setParticipantA(p1);
        binary.setParticipantB(p2);
        return binary;    }

    @Override
    public BinaryInteractionEvidence createBinaryInteractionEvidenceFrom(InteractionEvidence interaction, ParticipantEvidence p1, ParticipantEvidence p2, CvTerm expansionMethod) {
        XmlBinaryInteractionEvidence binary = instantiateNewBinaryInteractionEvidence();
        binary.setComplexExpansion(expansionMethod);
        copyXmlInteractionEvidenceProperties(interaction, binary);
        binary.setParticipantA(p1);
        binary.setParticipantB(p2);
        return binary;
    }

    @Override
    public BinaryInteraction createSelfBinaryInteractionFrom(Interaction interaction) {
        XmlBinaryInteraction binary = instantiateNewBinaryInteraction();
        InteractionCloner.copyAndOverrideBasicParticipantsToBinary(interaction, binary, false, true);

        copyBasicXmlInteractionProperties(interaction, binary);
        return binary;
    }

    @Override
    public ModelledBinaryInteraction createModelledBinaryInteractionFrom(ModelledInteraction interaction, ModelledParticipant p1, ModelledParticipant p2, CvTerm expansionMethod) {
        XmlModelledBinaryInteraction binary = instantiateNewModelledBinaryInteraction();
        binary.setComplexExpansion(expansionMethod);
        copyXmlModelledInteractionProperties(interaction, binary);
        binary.setParticipantA(p1);
        binary.setParticipantB(p2);
        return binary;
    }

    @Override
    public ModelledBinaryInteraction createSelfModelledBinaryInteractionFrom(ModelledInteraction interaction) {
        XmlModelledBinaryInteraction binary = instantiateNewModelledBinaryInteraction();
        InteractionCloner.copyAndOverrideModelledParticipantsToBinary(interaction, binary, false, true);

        copyXmlModelledInteractionProperties(interaction, binary);
        return binary;
    }

    @Override
    public BinaryInteraction createBinaryInteractionWrapperFrom(Interaction interaction) {
        return new XmlBinaryInteractionWrapper((XmlBasicInteraction)interaction);
    }

    @Override
    public BinaryInteractionEvidence createBinaryInteractionEvidenceWrapperFrom(InteractionEvidence interaction) {
        return new XmlBinaryInteractionEvidenceWrapper((XmlInteractionEvidence)interaction);
    }

    @Override
    public ModelledBinaryInteraction createModelledBinaryInteractionWrapperFrom(ModelledInteraction interaction) {
        return new XmlModelledBinaryInteractionWrapper((XmlModelledInteraction)interaction);
    }

    @Override
    public XmlBinaryInteraction instantiateNewBinaryInteraction() {
        return new XmlBinaryInteraction();
    }

    @Override
    public XmlBinaryInteractionEvidence instantiateNewBinaryInteractionEvidence() {
        return new XmlBinaryInteractionEvidence();
    }

    @Override
    public XmlModelledBinaryInteraction instantiateNewModelledBinaryInteraction() {
        return new XmlModelledBinaryInteraction();
    }

    private void copyXmlInteractionEvidenceProperties(InteractionEvidence interaction, XmlBinaryInteractionEvidence binary) {
        XmlInteractionEvidence xmlSource = (XmlInteractionEvidence)interaction;
        binary.setJAXBNames(xmlSource.getJAXBNames());
        binary.setJAXBXref(xmlSource.getJAXBXref());
        binary.setJAXBAvailability(xmlSource.getJAXBAvailability());
        binary.setJAXBAvailabilityRef(xmlSource.getJAXBAvailabilityRef());
        binary.setJAXBId(xmlSource.getJAXBId());
        binary.getConfidences().addAll(xmlSource.getConfidences());
        binary.getParameters().addAll(xmlSource.getParameters());
        binary.getExperiments().addAll(xmlSource.getExperiments());
        binary.setJAXBInferredInteractions(xmlSource.getJAXBInferredInteractions());
        binary.setJAXBInteractionTypes(xmlSource.getJAXBInteractionTypes());
        binary.setJAXBModelled(xmlSource.getJAXBModelled());
        binary.setJAXBIntraMolecular(xmlSource.getJAXBIntraMolecular());
        binary.setJAXBNegative(xmlSource.getJAXBNegative());
        binary.getAnnotations().addAll(xmlSource.getAnnotations());
        binary.setSourceLocator(xmlSource.getSourceLocator());
        binary.setEntry(xmlSource.getEntry());
        binary.getChecksums().addAll(xmlSource.getChecksums());
    }

    private void copyBasicXmlInteractionProperties(Interaction interaction, XmlBinaryInteraction binary) {
        XmlBasicInteraction xmlSource = (XmlBasicInteraction)interaction;
        binary.setJAXBNames(xmlSource.getJAXBNames());
        binary.setJAXBXref(xmlSource.getJAXBXref());
        binary.setJAXBId(xmlSource.getJAXBId());
        binary.setJAXBInferredInteractions(xmlSource.getJAXBInferredInteractions());
        binary.setJAXBInteractionTypes(xmlSource.getJAXBInteractionTypes());
        binary.setJAXBIntraMolecular(xmlSource.getJAXBIntraMolecular());
        binary.getAnnotations().addAll(xmlSource.getAnnotations());
        binary.setSourceLocator(xmlSource.getSourceLocator());
        binary.setEntry(xmlSource.getEntry());
        binary.getChecksums().addAll(xmlSource.getChecksums());
    }

    private void copyXmlModelledInteractionProperties(ModelledInteraction interaction, XmlModelledBinaryInteraction binary) {
        XmlModelledInteraction xmlSource = (XmlModelledInteraction)interaction;
        binary.setJAXBNames(xmlSource.getJAXBNames());
        binary.setJAXBXref(xmlSource.getJAXBXref());
        binary.setJAXBId(xmlSource.getJAXBId());
        binary.getModelledConfidences().addAll(xmlSource.getModelledConfidences());
        binary.getModelledParameters().addAll(xmlSource.getModelledParameters());
        binary.setJAXBInferredInteractions(xmlSource.getJAXBInferredInteractions());
        binary.setJAXBInteractionTypes(xmlSource.getJAXBInteractionTypes());
        binary.setJAXBIntraMolecular(xmlSource.getJAXBIntraMolecular());
        binary.getAnnotations().addAll(xmlSource.getAnnotations());
        binary.setSourceLocator(xmlSource.getSourceLocator());
        binary.setEntry(xmlSource.getEntry());
        binary.setSource(xmlSource.getSource());
        binary.getChecksums().addAll(xmlSource.getChecksums());
        binary.getCooperativeEffects().addAll(xmlSource.getCooperativeEffects());
        binary.getInteractionEvidences().addAll(xmlSource.getInteractionEvidences());
    }
}
