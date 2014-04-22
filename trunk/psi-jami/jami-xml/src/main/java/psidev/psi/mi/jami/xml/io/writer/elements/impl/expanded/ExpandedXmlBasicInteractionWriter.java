package psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlInteractionWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Set;

/**
 * Expanded XML 2.5 writer for a basic interaction (ignore experimental details)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */

public class ExpandedXmlBasicInteractionWriter extends AbstractXmlInteractionWriter<Interaction,Participant> implements ExpandedPsiXmlElementWriter<Interaction> {
    public ExpandedXmlBasicInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex, new ExpandedXmlParticipantWriter(writer, objectIndex));
    }

    public ExpandedXmlBasicInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                             PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter,
                                             PsiXmlExperimentWriter experimentWriter, PsiXmlParticipantWriter<Participant> participantWriter,
                                             PsiXmlElementWriter<Set<Feature>> inferredInteractionWriter, PsiXmlElementWriter<CvTerm> interactionTypeWriter,
                                             PsiXmlElementWriter<Annotation> attributeWriter, PsiXmlElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, experimentWriter,
                participantWriter != null ? participantWriter : new ExpandedXmlParticipantWriter(writer, objectIndex), inferredInteractionWriter, interactionTypeWriter, attributeWriter, checksumWriter);
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
    protected void writeIntraMolecular(Interaction object) {
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
