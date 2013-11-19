package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXml25ModelledParticipantWriter;

import javax.xml.stream.XMLStreamException;

/**
 * Compact XML 2.5 writer for an extended modelled interaction (ignore experimental details).
 * It will write cooperative effects as attributes.
 * It will write intra-molecular property, names, interaction types and experiments
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */

public class CompactXml25ModelledInteractionWriter extends AbstractXml25ModelledInteractionWriter<ModelledInteraction, ModelledParticipant> implements CompactPsiXml25ElementWriter<ModelledInteraction> {

    public CompactXml25ModelledInteractionWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex, new CompactXml25ModelledParticipantWriter(writer, objectIndex));
    }

    public CompactXml25ModelledInteractionWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex, PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter, PsiXml25ParticipantWriter<ModelledParticipant> participantWriter, PsiXml25ElementWriter<CvTerm> interactionTypeWriter, PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25ElementWriter<InferredInteraction> inferredInteractionWriter, PsiXml25ElementWriter<Experiment> experimentWriter,
                                                 PsiXml25ElementWriter<Confidence> confidenceWriter, PsiXml25ParameterWriter parameterWriter
                                                 ) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter,
                participantWriter != null ? participantWriter : new CompactXml25ModelledParticipantWriter(writer, objectIndex), interactionTypeWriter, attributeWriter, aliasWriter, inferredInteractionWriter, experimentWriter,
                confidenceWriter, parameterWriter);
    }

    @Override
    protected void writeExperiments(ModelledInteraction object) throws XMLStreamException {
        writeExperimentRef(object);
    }
}
