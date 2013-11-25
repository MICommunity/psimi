package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25OrganismWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * PSI-XML 2.5 writer for host organism
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class Xml25HostOrganismWriter extends AbstractXml25OrganismWriter {
    public Xml25HostOrganismWriter(XMLStreamWriter writer) {
        super(writer);
    }

    public Xml25HostOrganismWriter(XMLStreamWriter writer, PsiXml25ElementWriter<Alias> aliasWriter,
                                   PsiXml25ElementWriter<CvTerm> cellTypeWriter, PsiXml25ElementWriter<CvTerm> compartmentWriter,
                                   PsiXml25ElementWriter<CvTerm> tissueWriter) {
        super(writer, aliasWriter, cellTypeWriter, compartmentWriter, tissueWriter);
    }

    @Override
    protected void writeOtherProperties(Organism object) throws XMLStreamException {
        // nothing to do
    }

    @Override
    protected void writeStartOrganism() throws XMLStreamException {
        getStreamWriter().writeStartElement("hostOrganism");
    }
}
