package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25;

import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlDbXrefWriter;
import psidev.psi.mi.jami.xml.model.extension.ExtendedPsiXmlSource;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for expanded XML source having a release description and a release date
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class XmlSourceWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlSourceWriter {
    public XmlSourceWriter(XMLStreamWriter writer) {
        super(writer);
    }

    protected void initialiseXrefWriter() {
        super.setXrefWriter(new XmlDbXrefWriter(getStreamWriter()));
    }

    protected void initialisePublicationWriter() {
        super.setPublicationWriter(new XmlPublicationWriter(getStreamWriter()));
    }

    @Override
    protected void writeReleaseAttributes(Source object) throws XMLStreamException {
        if (object instanceof ExtendedPsiXmlSource){
            ExtendedPsiXmlSource xmlSource = (ExtendedPsiXmlSource) object;
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
        else{
            super.writeReleaseAttributes(object);
        }
    }
}
