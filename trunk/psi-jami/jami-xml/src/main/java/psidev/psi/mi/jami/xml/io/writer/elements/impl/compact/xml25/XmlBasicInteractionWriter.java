package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.CompactPsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml25.AbstractXmlInteractionWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Compact XML 2.5 writer for a basic interaction (ignore experimental details)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class XmlBasicInteractionWriter extends AbstractXmlInteractionWriter<Interaction> implements CompactPsiXmlElementWriter<Interaction> {
    public XmlBasicInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void initialiseParticipantWriter() {
        super.setParticipantWriter(new XmlParticipantWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void writeExperiments(Interaction object) throws XMLStreamException {
        writeExperimentRef();
    }
}
