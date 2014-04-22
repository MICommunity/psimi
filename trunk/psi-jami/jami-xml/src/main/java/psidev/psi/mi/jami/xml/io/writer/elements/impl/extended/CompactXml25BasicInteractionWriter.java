package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.model.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXmlParticipantWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Compact XML 2.5 writer for a basic interaction (ignore experimental details) which has a fullname
 * and aliases in addition to a shortname
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */

public class CompactXml25BasicInteractionWriter extends AbstractXml25InteractionWriter<Interaction,Participant> implements CompactPsiXmlElementWriter<Interaction> {

    public CompactXml25BasicInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex, new CompactXmlParticipantWriter(writer, objectIndex));
    }

    public CompactXml25BasicInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                              PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter,
                                              PsiXmlXrefWriter secondaryRefWriter, PsiXmlExperimentWriter experimentWriter,
                                              PsiXmlParticipantWriter<Participant> participantWriter, PsiXmlElementWriter<InferredInteraction> inferredInteractionWriter1,
                                              PsiXmlElementWriter<CvTerm> interactionTypeWriter, PsiXmlElementWriter<Annotation> attributeWriter,
                                              PsiXmlElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, experimentWriter,
                participantWriter != null ? participantWriter : new CompactXmlParticipantWriter(writer, objectIndex),
                inferredInteractionWriter1, interactionTypeWriter, attributeWriter, checksumWriter);
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
    protected void writeModelled(Interaction object) {
        // do nothing
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
