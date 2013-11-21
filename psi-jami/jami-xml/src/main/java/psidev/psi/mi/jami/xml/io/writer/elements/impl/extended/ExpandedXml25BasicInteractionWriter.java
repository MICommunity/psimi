package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.io.writer.elements.CompactPsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.ExpandedXml25ParticipantWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Expanded XML 2.5 writer for a basic interaction (ignore experimental details).
 * Interactions are named interactions with a fullname and aliases in addition to a shortname
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */

public class ExpandedXml25BasicInteractionWriter extends AbstractXml25InteractionWriter<Interaction,Participant> implements CompactPsiXml25ElementWriter<Interaction> {

    public ExpandedXml25BasicInteractionWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex, new ExpandedXml25ParticipantWriter(writer, objectIndex));
    }

    public ExpandedXml25BasicInteractionWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                              PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                              PsiXml25ParticipantWriter<Participant> participantWriter, PsiXml25ElementWriter<CvTerm> interactionTypeWriter,
                                              PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Experiment> experimentWriter, PsiXml25ElementWriter<Alias> aliasWriter,
                                              PsiXml25ElementWriter<InferredInteraction> inferredInteractionWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter,
                participantWriter != null ? participantWriter : new ExpandedXml25ParticipantWriter(writer, objectIndex), interactionTypeWriter, attributeWriter, experimentWriter, aliasWriter,
                inferredInteractionWriter);
    }

    @Override
    protected void writeAvailability(Interaction object) {
        // nothing to do
    }

    @Override
    protected void writeExperiments(Interaction object) throws XMLStreamException {
        writeExperimentDescription();
    }

    @Override
    protected void writeOtherAttributes(Interaction object) {
        // nothing to do
    }

    @Override
    protected void writeModelled(Interaction object) {
        // nothing to do
    }

    @Override
    protected void writeParameters(Interaction object) {
        // nothing to do
    }

    @Override
    protected void writeConfidences(Interaction object) {
        // nothing to do
    }

    @Override
    protected void writeNegative(Interaction object) {
        // nothing to do
    }
}
