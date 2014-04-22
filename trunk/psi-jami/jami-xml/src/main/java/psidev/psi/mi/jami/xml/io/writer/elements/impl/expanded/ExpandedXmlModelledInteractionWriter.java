package psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlModelledInteractionWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Set;

/**
 * Expanded XML 2.5 writer for a modelled interaction (ignore experimental details).
 * It will write cooperative effects as attributes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public class ExpandedXmlModelledInteractionWriter extends AbstractXmlModelledInteractionWriter<ModelledInteraction, ModelledParticipant> implements ExpandedPsiXmlElementWriter<ModelledInteraction> {

    public ExpandedXmlModelledInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex, new ExpandedXmlModelledParticipantWriter(writer, objectIndex));
    }

    public ExpandedXmlModelledInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                                PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter,
                                                PsiXmlExperimentWriter experimentWriter, PsiXmlParticipantWriter<ModelledParticipant> participantWriter,
                                                PsiXmlElementWriter<Set<Feature>> inferredInteractionWriter, PsiXmlElementWriter<CvTerm> interactionTypeWriter,
                                                PsiXmlElementWriter<Confidence> confidenceWriter, PsiXmlParameterWriter parameterWriter,
                                                PsiXmlElementWriter<Annotation> attributeWriter, PsiXmlElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, experimentWriter,
                participantWriter != null ? participantWriter : new ExpandedXmlModelledParticipantWriter(writer, objectIndex), inferredInteractionWriter, interactionTypeWriter, confidenceWriter, parameterWriter, attributeWriter, checksumWriter);
    }

    @Override
    protected void writeExperiments(ModelledInteraction object) throws XMLStreamException {
        super.writeExperiments(object);
        writeExperimentDescription();
    }
}
