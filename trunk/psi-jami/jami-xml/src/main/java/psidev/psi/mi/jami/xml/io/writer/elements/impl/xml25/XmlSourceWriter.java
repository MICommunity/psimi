package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlSourceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlAliasWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlAnnotationWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlDbXrefWriter;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * Writer of a source in an 2.5 entry.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public class XmlSourceWriter implements PsiXmlSourceWriter {

    private XMLStreamWriter streamWriter;
    private XMLGregorianCalendar calendar;
    private static final Logger logger = Logger.getLogger("XmlSourceWriter");
    private PsiXmlElementWriter<Alias> aliasWriter;
    private PsiXmlXrefWriter xrefWriter;
    private PsiXmlElementWriter<Publication> publicationWriter;
    private PsiXmlElementWriter<Annotation> attributeWriter;

    public XmlSourceWriter(XMLStreamWriter writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the XML25SourceWriter");
        }
        this.streamWriter = writer;
        this.calendar = null;
    }

    public PsiXmlElementWriter<Alias> getAliasWriter() {
        if (this.aliasWriter == null){
            this.aliasWriter = new XmlAliasWriter(streamWriter);
        }
        return aliasWriter;
    }

    public void setAliasWriter(PsiXmlElementWriter<Alias> aliasWriter) {
        this.aliasWriter = aliasWriter;
    }

    public PsiXmlXrefWriter getXrefWriter() {
        if (this.xrefWriter == null){
            initialiseXrefWriter();
        }
        return xrefWriter;
    }

    protected void initialiseXrefWriter() {
        this.xrefWriter = new XmlDbXrefWriter(streamWriter);
    }

    public void setXrefWriter(PsiXmlXrefWriter xrefWriter) {
        this.xrefWriter = xrefWriter;
    }

    public PsiXmlElementWriter<Publication> getPublicationWriter() {
        if (this.publicationWriter == null){
            initialisePublicationWriter();
        }
        return publicationWriter;
    }

    protected void initialisePublicationWriter() {
        this.publicationWriter = new XmlPublicationWriter(streamWriter);
    }

    public void setPublicationWriter(PsiXmlElementWriter<Publication> publicationWriter) {
        this.publicationWriter = publicationWriter;
    }

    public PsiXmlElementWriter<Annotation> getAttributeWriter() {
        if (this.attributeWriter == null){
            this.attributeWriter = new XmlAnnotationWriter(streamWriter);

        }
        return attributeWriter;
    }

    public void setAttributeWriter(PsiXmlElementWriter<Annotation> attributeWriter) {
        this.attributeWriter = attributeWriter;
    }

    @Override
    public void write(Source object) throws MIIOException{
        try {
            // write start
            this.streamWriter.writeStartElement(PsiXmlUtils.SOURCE_TAG);
            // write release/release date
            writeReleaseAttributes(object);
            // write names
            boolean hasShortName = object.getShortName() != null;
            boolean hasFullName = object.getFullName() != null;
            boolean hasAliases = !object.getSynonyms().isEmpty();
            if (hasShortName || hasFullName || hasAliases){
                this.streamWriter.writeStartElement("names");
                // write shortname
                if (hasShortName){
                    this.streamWriter.writeStartElement("shortLabel");
                    this.streamWriter.writeCharacters(object.getShortName());
                    this.streamWriter.writeEndElement();
                }
                // write fullname
                if (hasFullName){
                    this.streamWriter.writeStartElement("fullName");
                    this.streamWriter.writeCharacters(object.getFullName());
                    this.streamWriter.writeEndElement();
                }
                // write aliases
                for (Alias alias : object.getSynonyms()){
                    getAliasWriter().write(alias);
                }
                // write end names
                this.streamWriter.writeEndElement();
            }
            // write bibRef
            if (object.getPublication() != null){
                getPublicationWriter().write(object.getPublication());
            }
            // write Xref
            if (!object.getIdentifiers().isEmpty()){
                writeXrefFromSourceIdentifiers(object);
            }
            else if (!object.getXrefs().isEmpty()){
                writeXrefFromSourceXrefs(object);
            }
            // write attributes
            if (!object.getAnnotations().isEmpty()){
                // write start attribute list
                this.streamWriter.writeStartElement("attributeList");
                for (Annotation ann : object.getAnnotations()){
                    getAttributeWriter().write(ann);
                }
                // write end attributeList
                this.streamWriter.writeEndElement();
            }
            // write end source
            this.streamWriter.writeEndElement();
        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the source of the entry: "+object.toString(), e);
        }
    }

    protected void writeReleaseAttributes(Source object) throws XMLStreamException {
        if (this.calendar != null) {
            this.streamWriter.writeAttribute("releaseDate", this.calendar.toXMLFormat());
        }
    }

    protected void writeXrefFromSourceXrefs(Source object) throws XMLStreamException {
        Iterator<Xref> refIterator = object.getXrefs().iterator();
        // default qualifier is null as we are not processing identifiers
        getXrefWriter().setDefaultRefType(null);
        getXrefWriter().setDefaultRefTypeAc(null);
        // write start xref
        this.streamWriter.writeStartElement("xref");

        int index = 0;
        while (refIterator.hasNext()){
            Xref ref = refIterator.next();
            // write primaryRef
            if (index == 0){
                getXrefWriter().write(ref,"primaryRef");
                index++;
            }
            // write secondaryref
            else{
                getXrefWriter().write(ref,"secondaryRef");
                index++;
            }
        }

        // write end xref
        this.streamWriter.writeEndElement();
    }

    protected void writeXrefFromSourceIdentifiers(Source object) throws XMLStreamException {
        // write start xref
        this.streamWriter.writeStartElement("xref");

        // default qualifier is identity
        getXrefWriter().setDefaultRefType(Xref.IDENTITY);
        getXrefWriter().setDefaultRefTypeAc(Xref.IDENTITY_MI);
        String mi = object.getMIIdentifier();
        String par = object.getPARIdentifier();
        Xref miXref = null;
        Xref parXref = null;
        if (miXref != null || parXref != null){
            for (Xref ref : object.getIdentifiers()){
                // ignore MI that is already written
                if (mi != null && mi.equals(ref.getId())
                        && XrefUtils.isXrefFromDatabase(ref, CvTerm.PSI_MI_MI, CvTerm.PSI_MI)){
                    mi = null;
                    miXref = ref;
                }
                // ignore PAR that is already written
                else if (par != null && par.equals(ref.getId())
                        && XrefUtils.isXrefFromDatabase(ref, null, CvTerm.PSI_PAR)){
                    par = null;
                    parXref = ref;
                }
            }
        }

        boolean hasWrittenPrimaryRef = false;
        // write primaryRef
        if (miXref != null){
            getXrefWriter().write(miXref,"primaryRef");
            hasWrittenPrimaryRef = true;
            if (parXref != null){
                getXrefWriter().write(parXref,"secondaryRef");
            }
        }
        else if (parXref != null){
            getXrefWriter().write(parXref,"primaryRef");
            hasWrittenPrimaryRef = true;
        }

        // write secondaryRefs (and primary ref if not done already)
        Iterator<Xref> refIterator = object.getIdentifiers().iterator();
        while (refIterator.hasNext()){
            Xref ref = refIterator.next();
            // ignore MI that is already written
            // ignore PAR that is already written
            if (ref != miXref && ref != parXref){
                if (!hasWrittenPrimaryRef){
                    hasWrittenPrimaryRef = true;
                    getXrefWriter().write(ref,"primaryRef");
                }
                else{
                    getXrefWriter().write(ref, "secondaryRef");
                }
            }
        }

        // write other xrefs
        if (!object.getXrefs().isEmpty()){
            // default qualifier is null
            getXrefWriter().setDefaultRefType(null);
            getXrefWriter().setDefaultRefTypeAc(null);
            for (Xref ref : object.getXrefs()){
                getXrefWriter().write(ref,"secondaryRef");
            }
        }

        // write end xref
        this.streamWriter.writeEndElement();
    }

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }

    @Override
    public XMLGregorianCalendar getDefaultReleaseDate() {
        return this.calendar;
    }

    @Override
    public void setDefaultReleaseDate(XMLGregorianCalendar date) {
        this.calendar = date;
    }
}
