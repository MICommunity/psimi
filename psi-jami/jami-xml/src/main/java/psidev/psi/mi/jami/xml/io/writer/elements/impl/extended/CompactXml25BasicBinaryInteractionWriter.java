package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.io.writer.elements.CompactPsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXml25ParticipantWriter;

import javax.xml.stream.XMLStreamException;

/**
 * Compact XML 2.5 writer for a basic binary interaction (ignore experimental details) which has a fullname
 * and aliases in addition to a shortname
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */

public class CompactXml25BasicBinaryInteractionWriter extends AbstractXml25InteractionWriter<BinaryInteraction,Participant> implements CompactPsiXml25ElementWriter<BinaryInteraction> {

    public CompactXml25BasicBinaryInteractionWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex, new CompactXml25ParticipantWriter(writer, objectIndex));
    }

    public CompactXml25BasicBinaryInteractionWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex,
                                                    PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                                    PsiXml25ParticipantWriter<Participant> participantWriter, PsiXml25ElementWriter<CvTerm> interactionTypeWriter,
                                                    PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Alias> aliasWriter,
                                                    PsiXml25ElementWriter<InferredInteraction> inferredInteractionWriter, PsiXml25ElementWriter<Experiment> experimentWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter,
                participantWriter != null ? participantWriter : new CompactXml25ParticipantWriter(writer, objectIndex), interactionTypeWriter, attributeWriter,
                experimentWriter, aliasWriter,inferredInteractionWriter);
    }

    @Override
    protected void writeAvailability(BinaryInteraction object) {
        // nothing to do
    }

    @Override
    protected void writeExperiments(BinaryInteraction object) throws XMLStreamException {
        writeExperimentRef();
    }

    @Override
    protected void writeOtherAttributes(BinaryInteraction object) {
        // nothing to do
    }

    @Override
    protected void writeModelled(BinaryInteraction object) {
        // do nothing
    }

    @Override
    protected void writeParameters(BinaryInteraction object) {
        // nothing to do
    }

    @Override
    protected void writeConfidences(BinaryInteraction object) {
        // nothing to do
    }

    @Override
    protected void writeNegative(BinaryInteraction object) {
        // nothing to do
    }
}
