package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlPublicationWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Xml30 writer for publications (bibref objects)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public class XmlPublicationWriter extends AbstractXmlPublicationWriter {

    public XmlPublicationWriter(XMLStreamWriter writer){
        super(writer);
    }

    @Override
    protected void writeBibrefContent(Publication object) throws MIIOException {
        try {
            // write identifiers and xrefs
            if (!object.getIdentifiers().isEmpty()){
                writeXrefFromPublicationIdentifiers(object);
            }
            // write only xrefs
            else if (!object.getXrefs().isEmpty()){
                writeXrefFromPublicationXrefs(object);
            }

            // write attributes
            boolean hasTitle = object.getTitle() != null;
            boolean hasJournal = object.getJournal() != null;
            boolean hasPublicationDate = object.getPublicationDate() != null;
            boolean hasCurationDepth = !CurationDepth.undefined.equals(object.getCurationDepth());
            boolean hasAuthors = !object.getAuthors().isEmpty();
            boolean hasAttributes = !object.getAnnotations().isEmpty();
            // write attributes if no identifiers available
            if (hasTitle || hasJournal || hasPublicationDate || hasCurationDepth || hasAuthors || hasAttributes){
                // write start attribute list
                getStreamWriter().writeStartElement("attributeList");
                // write publication properties such as title, journal, etc..
                writePublicationPropertiesAsAttributes(object, hasTitle, hasJournal, hasPublicationDate, hasCurationDepth, hasAuthors);
                // write normal attributes
                if (hasAttributes){
                    for (Annotation ann : object.getAnnotations()){
                        getAttributeWriter().write(ann);
                    }
                }
                // write end attributeList
                getStreamWriter().writeEndElement();
            }

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the publication : "+object.toString(), e);
        }
    }
}
