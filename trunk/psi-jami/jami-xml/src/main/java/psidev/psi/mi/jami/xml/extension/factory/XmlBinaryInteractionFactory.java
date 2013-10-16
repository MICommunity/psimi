package psidev.psi.mi.jami.xml.extension.factory;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.factory.DefaultBinaryInteractionFactory;
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

public class XmlBinaryInteractionFactory extends DefaultBinaryInteractionFactory {

    @Override
    public BinaryInteractionEvidence createSelfBinaryInteractionEvidenceFrom(InteractionEvidence interaction) {
        XmlBinaryInteractionEvidence binary = instantiateNewBinaryInteractionEvidence();
        InteractionCloner.copyAndOverrideParticipantsEvidencesToBinary(interaction, binary, false, true);

        XmlInteractionEvidence xmlSource = (XmlInteractionEvidence)interaction;
        xmlSource.setJAXBXref(xmlSource.getJAXBXref());

        return binary;
    }

    @Override
    public BinaryInteraction createBasicBinaryInteractionFrom(Interaction interaction, Participant p1, Participant p2, CvTerm expansionMethod) {
        return super.createBasicBinaryInteractionFrom(interaction, p1, p2, expansionMethod);
    }

    @Override
    public BinaryInteractionEvidence createBinaryInteractionEvidenceFrom(InteractionEvidence interaction, ParticipantEvidence p1, ParticipantEvidence p2, CvTerm expansionMethod) {
        return super.createBinaryInteractionEvidenceFrom(interaction, p1, p2, expansionMethod);
    }

    @Override
    public BinaryInteraction createSelfBinaryInteractionFrom(Interaction interaction) {
        return super.createSelfBinaryInteractionFrom(interaction);
    }

    @Override
    public ModelledBinaryInteraction createModelledBinaryInteractionFrom(ModelledInteraction interaction, ModelledParticipant p1, ModelledParticipant p2, CvTerm expansionMethod) {
        return super.createModelledBinaryInteractionFrom(interaction, p1, p2, expansionMethod);
    }

    @Override
    public ModelledBinaryInteraction createSelfModelledBinaryInteractionFrom(ModelledInteraction interaction) {
        return super.createSelfModelledBinaryInteractionFrom(interaction);
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
}
