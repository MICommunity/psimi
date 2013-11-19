package psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25ModelledInteractionWriter;

import javax.xml.stream.XMLStreamException;
import java.util.Set;

/**
 * Expanded XML 2.5 writer for a modelled interaction (ignore experimental details).
 * It will write cooperative effects as attributes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public class ExpandedXml25ModelledInteractionWriter extends AbstractXml25ModelledInteractionWriter<ModelledInteraction, ModelledParticipant> implements ExpandedPsiXml25ElementWriter<ModelledInteraction> {

    public ExpandedXml25ModelledInteractionWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex, new ExpandedXml25ModelledParticipantWriter(writer, objectIndex));
    }

    public ExpandedXml25ModelledInteractionWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex,
                                                 PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                                 PsiXml25ParticipantWriter<ModelledParticipant> participantWriter, PsiXml25ElementWriter<CvTerm> interactionTypeWriter,
                                                 PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Set<Feature>> inferredInteractionWriter,
                                                 PsiXml25ElementWriter<Experiment> experimentWriter,
                                                 PsiXml25ElementWriter<Confidence> confidenceWriter, PsiXml25ParameterWriter parameterWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter,
                participantWriter != null ? participantWriter : new ExpandedXml25ModelledParticipantWriter(writer, objectIndex), interactionTypeWriter, attributeWriter,
                inferredInteractionWriter, experimentWriter, confidenceWriter, parameterWriter);
    }

    @Override
    protected void writeExperiments(ModelledInteraction object) throws XMLStreamException {
        super.writeExperiments(object);
        writeExperimentDescription();
    }
}
