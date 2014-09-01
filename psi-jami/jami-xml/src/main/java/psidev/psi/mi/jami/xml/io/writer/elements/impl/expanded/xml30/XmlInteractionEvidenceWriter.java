package psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.ExpandedPsiXmlElementWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Expanded XML 3.0 writer for an interaction evidence (with full experimental details).
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public class XmlInteractionEvidenceWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml30.AbstractXmlInteractionEvidenceWriter<InteractionEvidence>
        implements ExpandedPsiXmlElementWriter<InteractionEvidence> {
    public XmlInteractionEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void initialiseParticipantWriter() {
        super.setParticipantWriter(new XmlParticipantEvidenceWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void writeAvailability(InteractionEvidence object) throws XMLStreamException {
        if (object.getAvailability() != null){
            writeAvailabilityDescription(object.getAvailability());
        }
    }

    @Override
    protected CvTerm writeExperiments(InteractionEvidence object) throws XMLStreamException {
        super.writeExperiments(object);
        return writeExperimentDescription();
    }
}
