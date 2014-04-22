package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlModelledInteractionWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Set;

/**
 * Compact XML 2.5 writer for a modelled interaction (ignore experimental details).
 * It will write cooperative effects as attributes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */

public class CompactXmlModelledInteractionWriter extends AbstractXmlModelledInteractionWriter<ModelledInteraction, ModelledParticipant> implements CompactPsiXmlElementWriter<ModelledInteraction> {

    public CompactXmlModelledInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex, new CompactXmlModelledParticipantWriter(writer, objectIndex));
    }

    public CompactXmlModelledInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                               PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter,
                                               PsiXmlExperimentWriter experimentWriter, PsiXmlParticipantWriter<ModelledParticipant> participantWriter,
                                               PsiXmlElementWriter<Set<Feature>> inferredInteractionWriter, PsiXmlElementWriter<CvTerm> interactionTypeWriter,
                                               PsiXmlElementWriter<Confidence> confidenceWriter, PsiXmlParameterWriter parameterWriter,
                                               PsiXmlElementWriter<Annotation> attributeWriter, PsiXmlElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, experimentWriter,
                participantWriter != null ? participantWriter : new CompactXmlModelledParticipantWriter(writer, objectIndex),
                inferredInteractionWriter, interactionTypeWriter,
                confidenceWriter, parameterWriter,
                attributeWriter, checksumWriter);
    }

    @Override
    protected void writeExperiments(ModelledInteraction object) throws XMLStreamException {
        super.writeExperiments(object);
        writeExperimentRef();
    }
}
