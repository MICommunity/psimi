package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlOrganismWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * PSI-XML writer for organism
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlOrganismWriter extends AbstractXmlOrganismWriter {
    public XmlOrganismWriter(XMLStreamWriter writer) {
        super(writer);
    }

    @Override
    protected void writeOtherProperties(Organism object) {
        // nothing to do
    }

    @Override
    protected void writeStartOrganism() throws XMLStreamException {
        getStreamWriter().writeStartElement("organism");
    }
}
