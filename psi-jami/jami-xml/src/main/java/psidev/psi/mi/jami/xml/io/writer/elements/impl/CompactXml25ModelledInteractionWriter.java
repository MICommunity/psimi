package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectIndex;
import psidev.psi.mi.jami.xml.io.writer.elements.CompactPsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;

import javax.xml.stream.XMLStreamException;
import java.util.Set;

/**
 * Compact XML 2.5 writer for a modelled interaction (ignore experimental details).
 * It will write cooperative effects as attributes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */

public class CompactXml25ModelledInteractionWriter extends AbstractXml25InteractionWithoutExperimentWriter<ModelledInteraction,ModelledParticipant> implements CompactPsiXml25ElementWriter<ModelledInteraction> {
    private PsiXml25ElementWriter<Confidence> confidenceWriter;
    private PsiXml25ElementWriter<Parameter> parameterWriter;
    public CompactXml25ModelledInteractionWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex) {
        super(writer, objectIndex, new CompactXml25ModelledParticipantWriter(writer, objectIndex));
    }

    public CompactXml25ModelledInteractionWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex,
                                              PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                              CompactPsiXml25ElementWriter<ModelledParticipant> participantWriter, PsiXml25ElementWriter<CvTerm> interactionTypeWriter,
                                              PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Set<Feature>> inferredInteractionWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter,
                participantWriter != null ? participantWriter : new CompactXml25ModelledParticipantWriter(writer, objectIndex), interactionTypeWriter, attributeWriter, inferredInteractionWriter);
    }

    @Override
    protected void writeAvailability(ModelledInteraction object) {
        // nothing to do
    }

    @Override
    protected void writeExperiments(ModelledInteraction object) throws XMLStreamException {
        writeExperimentRef();
    }

    @Override
    protected void writeOtherAttributes(ModelledInteraction object) {
        // nothing to do
    }

    @Override
    protected void writeIntraMolecular(ModelledInteraction object) {
        // nothing to do
    }

    @Override
    protected void writeModelled(ModelledInteraction object) {
        // nothing to do
    }

    @Override
    protected void writeParameters(ModelledInteraction object) {
        // nothing to do
    }

    @Override
    protected void writeConfidences(ModelledInteraction object) {
        // nothing to do
    }

    @Override
    protected void writeNegative(ModelledInteraction object) {
        // nothing to do
    }
}
