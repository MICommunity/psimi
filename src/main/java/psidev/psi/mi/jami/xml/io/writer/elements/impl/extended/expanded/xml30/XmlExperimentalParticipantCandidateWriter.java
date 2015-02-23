package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml30;

import psidev.psi.mi.jami.model.ExperimentalParticipantCandidate;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.CompactPsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml30.AbstractXmlParticipantCandidateWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlInteractorWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlFeatureEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Expanded XML 3.0 writer for a participant evidence with full experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class XmlExperimentalParticipantCandidateWriter extends AbstractXmlParticipantCandidateWriter<ExperimentalParticipantCandidate, FeatureEvidence>
        implements CompactPsiXmlElementWriter<ExperimentalParticipantCandidate> {
    public XmlExperimentalParticipantCandidateWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void initialiseInteractorWriter() {
        super.setInteractorWriter(new XmlInteractorWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseFeatureWriter() {
       super.setFeatureWriter(new XmlFeatureEvidenceWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void writeMolecule(Interactor interactor) throws XMLStreamException {
        super.writeMoleculeDescription(interactor);
    }
}
