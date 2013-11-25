package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Source;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25PublicationWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for extended XML source having a release description and a release date
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class Xml25SourceWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25SourceWriter {
    public Xml25SourceWriter(XMLStreamWriter writer) {
        super(writer);
    }

    public Xml25SourceWriter(XMLStreamWriter writer, PsiXml25ElementWriter<Alias> aliasWriter,
                             PsiXml25PublicationWriter publicationWriter, PsiXml25XrefWriter primaryRefWriter,
                             PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<Annotation> attributeWriter) {
        super(writer, aliasWriter, publicationWriter, primaryRefWriter, secondaryRefWriter, attributeWriter);
    }

    @Override
    protected void writeReleaseAttributes(Source object) throws XMLStreamException {
        ExtendedPsi25Source xmlSource = (ExtendedPsi25Source) object;
        if (xmlSource.getRelease() != null){
            getStreamWriter().writeAttribute("release", xmlSource.getRelease());
        }
        if (xmlSource.getReleaseDate() != null){
            getStreamWriter().writeAttribute("releaseDate", xmlSource.getReleaseDate().toXMLFormat());
        }
        else{
            super.writeReleaseAttributes(object);
        }
    }
}
