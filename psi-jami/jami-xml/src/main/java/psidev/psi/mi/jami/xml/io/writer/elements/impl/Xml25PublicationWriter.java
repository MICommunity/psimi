package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlPublicationWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Xml25 writer for publications (bibref objects)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public class Xml25PublicationWriter extends AbstractXmlPublicationWriter {

    public Xml25PublicationWriter(XMLStreamWriter writer){
        super(writer);
    }

    @Override
    protected void writeBibrefContent(Publication object) throws MIIOException {
        try {
            // write xref
            if (!object.getIdentifiers().isEmpty()){
                writeXrefFromPublicationIdentifiers(object);
            }
            else {
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
                // write xref if no identifiers and no attributes available
                else if (!object.getXrefs().isEmpty()){
                    writeXrefFromPublicationXrefs(object);
                }
            }

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the publication : "+object.toString(), e);
        }
    }
}
