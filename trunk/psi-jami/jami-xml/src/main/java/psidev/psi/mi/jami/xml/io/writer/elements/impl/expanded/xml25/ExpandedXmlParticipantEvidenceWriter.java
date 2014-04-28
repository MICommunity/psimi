package psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.ExpandedPsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25FeatureEvidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25ParticipantEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Expanded XML 2.5 writer for a participant evidence with full experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class ExpandedXmlParticipantEvidenceWriter extends AbstractXml25ParticipantEvidenceWriter implements ExpandedPsiXmlElementWriter<ParticipantEvidence> {
    public ExpandedXmlParticipantEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void initialiseFeatureWriter() {
        super.setFeatureWriter(new Xml25FeatureEvidenceWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void writeMolecule(Interactor interactor) throws XMLStreamException {
        super.writeMoleculeDescription(interactor);
    }
}
