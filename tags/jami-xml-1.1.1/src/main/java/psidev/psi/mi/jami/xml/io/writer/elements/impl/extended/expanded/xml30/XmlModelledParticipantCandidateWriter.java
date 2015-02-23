package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml30;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipantCandidate;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.CompactPsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml30.AbstractXmlParticipantCandidateWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlInteractorWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlModelledFeatureWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Expanded XML 3.0 writer for a participant evidence with full experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class XmlModelledParticipantCandidateWriter extends AbstractXmlParticipantCandidateWriter<ModelledParticipantCandidate, ModelledFeature>
        implements CompactPsiXmlElementWriter<ModelledParticipantCandidate> {
    public XmlModelledParticipantCandidateWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void initialiseInteractorWriter() {
        super.setInteractorWriter(new XmlInteractorWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseFeatureWriter() {
       super.setFeatureWriter(new XmlModelledFeatureWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void writeMolecule(Interactor interactor) throws XMLStreamException {
        super.writeMoleculeDescription(interactor);
    }
}
