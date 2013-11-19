package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Source;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25PublicationWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;

import javax.xml.stream.XMLStreamException;

/**
 * XML 2.5 writer for extended XML source having a release description and a release date
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class Xml25SourceWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25SourceWriter {
    public Xml25SourceWriter(XMLStreamWriter2 writer) {
        super(writer);
    }

    public Xml25SourceWriter(XMLStreamWriter2 writer, PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25PublicationWriter publicationWriter, PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter) {
        super(writer, aliasWriter, publicationWriter, attributeWriter, primaryRefWriter, secondaryRefWriter);
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
