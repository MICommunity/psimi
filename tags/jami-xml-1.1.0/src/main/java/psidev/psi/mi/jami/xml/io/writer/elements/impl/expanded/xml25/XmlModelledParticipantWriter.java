package psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.ExpandedPsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml25.AbstractXmlModelledParticipantWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Expanded XML 2.5 writer for a modelled participant. (ignore all experimental details)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class XmlModelledParticipantWriter extends AbstractXmlModelledParticipantWriter
        implements ExpandedPsiXmlElementWriter<ModelledParticipant> {
    public XmlModelledParticipantWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void writeMolecule(Interactor interactor) throws XMLStreamException {
        super.writeMoleculeDescription(interactor);
    }
}

