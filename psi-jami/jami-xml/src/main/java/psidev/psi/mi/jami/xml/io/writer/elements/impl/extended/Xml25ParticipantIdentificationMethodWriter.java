package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25ExperimentalCvTermWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Xml 2.5 writer for extended participant identification method having experiment references
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class Xml25ParticipantIdentificationMethodWriter extends AbstractXml25ExperimentalCvTermWriter {
    public Xml25ParticipantIdentificationMethodWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    public Xml25ParticipantIdentificationMethodWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex, PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter);
    }

    @Override
    protected void writeStartCvTerm() throws XMLStreamException {
        getStreamWriter().writeStartElement("participantIdentificationMethod");
    }
}
