package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;

import javax.xml.stream.XMLStreamException;

/**
 * PSI-XML 2.5 writer for organism
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class Xml25OrganismWriter extends AbstractXml25OrganismWriter {
    public Xml25OrganismWriter(XMLStreamWriter2 writer) {
        super(writer);
    }

    public Xml25OrganismWriter(XMLStreamWriter2 writer, PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25ElementWriter<CvTerm> tissueWriter, PsiXml25ElementWriter<CvTerm> compartmentWriter, PsiXml25ElementWriter<CvTerm> cellTypeWriter) {
        super(writer, aliasWriter, tissueWriter, compartmentWriter, cellTypeWriter);
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
