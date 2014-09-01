package psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.ExpandedPsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml25.AbstractXmlInteractionWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Expanded XML 2.5 writer for a basic interaction (ignore experimental details)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */

public class XmlBasicInteractionWriter extends AbstractXmlInteractionWriter<Interaction> implements ExpandedPsiXmlElementWriter<Interaction> {
    public XmlBasicInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void initialiseParticipantWriter() {
        super.setParticipantWriter(new XmlParticipantWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected CvTerm writeExperiments(Interaction object) throws XMLStreamException {
        return writeExperimentDescription();
    }
}
