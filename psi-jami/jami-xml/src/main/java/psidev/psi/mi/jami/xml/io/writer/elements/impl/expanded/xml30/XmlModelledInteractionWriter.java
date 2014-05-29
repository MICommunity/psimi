package psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30;

import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.ExpandedPsiXmlElementWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Expanded XML 3.0 writer for a modelled interaction (ignore experimental details).
 * It will write cooperative effects as attributes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public class XmlModelledInteractionWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml30.AbstractXmlModelledInteractionWriter<ModelledInteraction>
        implements ExpandedPsiXmlElementWriter<ModelledInteraction> {

    public XmlModelledInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void initialiseParticipantWriter() {
        super.setParticipantWriter( new XmlModelledParticipantWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void writeExperiments(ModelledInteraction object) throws XMLStreamException {
        super.writeExperiments(object);
        writeExperimentDescription();
    }
}
