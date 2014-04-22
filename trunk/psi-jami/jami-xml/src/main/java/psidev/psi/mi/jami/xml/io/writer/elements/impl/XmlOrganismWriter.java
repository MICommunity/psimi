package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlOrganismWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * PSI-XML 2.5 writer for organism
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlOrganismWriter extends AbstractXmlOrganismWriter {
    public XmlOrganismWriter(XMLStreamWriter writer) {
        super(writer);
    }

    public XmlOrganismWriter(XMLStreamWriter writer, PsiXmlElementWriter<Alias> aliasWriter,
                             PsiXmlElementWriter<CvTerm> cellTypeWriter, PsiXmlElementWriter<CvTerm> compartmentWriter,
                             PsiXmlElementWriter<CvTerm> tissueWriter) {
        super(writer, aliasWriter, cellTypeWriter, compartmentWriter, tissueWriter);
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
