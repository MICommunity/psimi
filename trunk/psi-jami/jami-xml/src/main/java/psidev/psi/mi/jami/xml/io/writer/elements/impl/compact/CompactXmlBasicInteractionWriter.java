package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlInteractionWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Set;

/**
 * Compact XML 2.5 writer for a basic interaction (ignore experimental details)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class CompactXmlBasicInteractionWriter extends AbstractXmlInteractionWriter<Interaction,Participant> implements CompactPsiXmlElementWriter<Interaction> {
    public CompactXmlBasicInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex, new CompactXmlParticipantWriter(writer, objectIndex));
    }

    public CompactXmlBasicInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                            PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter,
                                            PsiXmlExperimentWriter experimentWriter, PsiXmlParticipantWriter<Participant> participantWriter,
                                            PsiXmlElementWriter<Set<Feature>> inferredInteractionWriter, PsiXmlElementWriter<CvTerm> interactionTypeWriter,
                                            PsiXmlElementWriter<Annotation> attributeWriter, PsiXmlElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, experimentWriter,
                participantWriter != null ? participantWriter : new CompactXmlParticipantWriter(writer, objectIndex), inferredInteractionWriter, interactionTypeWriter,
                attributeWriter, checksumWriter);
    }

    @Override
    protected void writeAvailability(Interaction object) {
        // nothing to do
    }

    @Override
    protected void writeExperiments(Interaction object) throws XMLStreamException {
        writeExperimentRef();
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
