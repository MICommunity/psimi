package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.CompactPsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlInteractionEvidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.CompactXmlParticipantEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Compact XML 2.5 writer for an interaction evidence (with full experimental details).
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public class CompactXmlInteractionEvidenceWriter extends AbstractXmlInteractionEvidenceWriter<InteractionEvidence, ParticipantEvidence>
                                                   implements CompactPsiXmlElementWriter<InteractionEvidence> {
    public CompactXmlInteractionEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void initialiseParticipantWriter() {
        super.setParticipantWriter(new CompactXmlParticipantEvidenceWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void writeAvailability(InteractionEvidence object) throws XMLStreamException {
        if (object.getAvailability() != null){
            writeAvailabilityRef(object.getAvailability());
        }
    }

    @Override
    protected void writeExperiments(InteractionEvidence object) throws XMLStreamException {
        super.writeExperiments(object);
        writeExperimentRef();
    }
}
