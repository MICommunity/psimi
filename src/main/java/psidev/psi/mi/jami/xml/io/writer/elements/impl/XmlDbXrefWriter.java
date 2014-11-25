package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Xml 25 Writer for Xref object
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public class XmlDbXrefWriter implements PsiXmlXrefWriter {

    private XMLStreamWriter streamWriter;
    private String defaultRefTypeAc = null;
    private String defaultRefType=null;

    public XmlDbXrefWriter(XMLStreamWriter writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the XmlDbXrefWriter");
        }
        this.streamWriter = writer;
    }

    @Override
    public void write(Xref object, String nodeName) throws MIIOException {
        if (object != null){
            try {
                // write start
                writeStartDbRef(nodeName);
                // write database
                CvTerm db = object.getDatabase();
                this.streamWriter.writeAttribute("db", db.getShortName());
                if (db.getMIIdentifier() != null){
                    this.streamWriter.writeAttribute("dbAc", db.getMIIdentifier());
                }
                // write id
                this.streamWriter.writeAttribute("id", object.getId());
                // write version
                if (object.getVersion() != null){
                    this.streamWriter.writeAttribute("version", object.getVersion());
                }
                // write qualifier
                if (object.getQualifier() != null){
                    CvTerm qualifier = object.getQualifier();
                    this.streamWriter.writeAttribute("refType", qualifier.getShortName());
                    if (qualifier.getMIIdentifier() != null){
                        this.streamWriter.writeAttribute("refTypeAc", qualifier.getMIIdentifier());
                    }
                }
                else if (this.defaultRefType != null || this.defaultRefTypeAc != null){
                    this.streamWriter.writeAttribute("refType", this.defaultRefType != null? this.defaultRefType : PsiXmlUtils.UNSPECIFIED );
                    if (this.defaultRefTypeAc != null){
                        this.streamWriter.writeAttribute("refTypeAc", this.defaultRefTypeAc);
                    }
                }

                // write other properties
                writeOtherProperties(object);

                // write end db ref
                this.streamWriter.writeEndElement();

            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to write the db reference : "+object.toString(), e);
            }
        }
    }

    protected void writeOtherProperties(Xref object) throws XMLStreamException{
        // nothing to write by default
    }

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }

    @Override
    public void setDefaultRefType(String defaultType) {
        this.defaultRefType = defaultType;
    }

    @Override
    public void setDefaultRefTypeAc(String defaultTypeAc) {
        this.defaultRefTypeAc = defaultTypeAc;
    }

    protected void writeStartDbRef(String nodeName) throws XMLStreamException{
        getStreamWriter().writeStartElement(nodeName);
    }}
