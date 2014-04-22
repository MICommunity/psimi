package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.model.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXmlModelledParticipantWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Compact XML 2.5 writer for an extended modelled interaction (ignore experimental details).
 * It will write cooperative effects as attributes.
 * It will write intra-molecular property, names, interaction types and experiments
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */

public class CompactXml25ModelledInteractionWriter extends AbstractXml25ModelledInteractionWriter<ModelledInteraction, ModelledParticipant> implements CompactPsiXmlElementWriter<ModelledInteraction> {

    public CompactXml25ModelledInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex, new CompactXmlModelledParticipantWriter(writer, objectIndex));
    }

    public CompactXml25ModelledInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                                 PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter,
                                                 PsiXmlXrefWriter secondaryRefWriter, PsiXmlExperimentWriter experimentWriter,
                                                 PsiXmlParticipantWriter<ModelledParticipant> participantWriter, PsiXmlElementWriter<InferredInteraction> inferredInteractionWriter,
                                                 PsiXmlElementWriter<CvTerm> interactionTypeWriter, PsiXmlElementWriter<Confidence> confidenceWriter,
                                                 PsiXmlParameterWriter parameterWriter, PsiXmlElementWriter<Annotation> attributeWriter,
                                                 PsiXmlElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, experimentWriter,
                participantWriter != null ? participantWriter : new CompactXmlModelledParticipantWriter(writer, objectIndex), inferredInteractionWriter, interactionTypeWriter, confidenceWriter, parameterWriter, attributeWriter, checksumWriter);
    }

    @Override
    protected void writeExperiments(ModelledInteraction object) throws XMLStreamException {
        writeExperimentRef(object);
    }
}
