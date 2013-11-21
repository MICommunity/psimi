package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25ModelledInteractionWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Set;

/**
 * Compact XML 2.5 writer for a modelled binary interaction (ignore experimental details).
 * It will write cooperative effects as attributes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */

public class CompactXml25ModelledBinaryInteractionWriter extends AbstractXml25ModelledInteractionWriter<ModelledBinaryInteraction, ModelledParticipant> implements CompactPsiXml25ElementWriter<ModelledBinaryInteraction> {

    public CompactXml25ModelledBinaryInteractionWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex, new CompactXml25ModelledParticipantWriter(writer, objectIndex));
    }

    public CompactXml25ModelledBinaryInteractionWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                                       PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                                       PsiXml25ParticipantWriter<ModelledParticipant> participantWriter, PsiXml25ElementWriter<CvTerm> interactionTypeWriter,
                                                       PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Set<Feature>> inferredInteractionWriter,
                                                       PsiXml25ElementWriter<Experiment> experimentWriter,
                                                       PsiXml25ElementWriter<Confidence> confidenceWriter, PsiXml25ParameterWriter parameterWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter,
                participantWriter != null ? participantWriter : new CompactXml25ModelledParticipantWriter(writer, objectIndex), interactionTypeWriter, attributeWriter,
                inferredInteractionWriter, experimentWriter, confidenceWriter, parameterWriter);
    }

    @Override
    protected void writeExperiments(ModelledBinaryInteraction object) throws XMLStreamException {
        super.writeExperiments(object);
        writeExperimentRef();
    }
}
