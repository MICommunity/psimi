package psidev.psi.mi.jami.xml.io.writer;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Xref;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;

/**
 * abstract Xml 25 Writer for Xref object
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public abstract class AbstractXml25XrefWriter implements PsiXml25XrefWriter{

    private XMLStreamWriter2 streamWriter;
    private PsiXml25ElementWriter<Annotation> annotationWriter;
    private String defaultRefTypeAc = null;
    private String defaultRefType=null;

    public AbstractXml25XrefWriter(XMLStreamWriter2 writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXml25XrefWriter");
        }
        this.streamWriter = writer;
        this.annotationWriter = new Xml25AnnotationWriter(writer);
    }

    public AbstractXml25XrefWriter(XMLStreamWriter2 writer, PsiXml25ElementWriter<Annotation> annotationWriter){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXml25XrefWriter");
        }
        this.streamWriter = writer;
        this.annotationWriter = annotationWriter!=null ? annotationWriter : new Xml25AnnotationWriter(writer);
    }

    @Override
    public void write(Xref object) throws MIIOException {
        if (object != null){
            try {
                // write start
                writeStartDbRef();
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
                    this.streamWriter.writeAttribute("refType", this.defaultRefType != null? this.defaultRefTypeAc : PsiXml25Utils.UNSPECIFIED );
                    if (this.defaultRefTypeAc != null){
                        this.streamWriter.writeAttribute("refTypeAc", this.defaultRefTypeAc);
                    }
                }

                // write secondary and attributes
                if (object instanceof ExtendedPsi25Xref){
                    ExtendedPsi25Xref xmlXref = (ExtendedPsi25Xref)object;
                    // write secondary
                    if (xmlXref.getSecondary() != null){
                        this.streamWriter.writeAttribute("secondary", xmlXref.getSecondary());
                    }
                    // write attributes
                    if (!xmlXref.getAnnotations().isEmpty()){
                        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                        this.streamWriter.writeStartElement("attributeList");
                        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                        for (Annotation annot : xmlXref.getAnnotations()){
                            // write annotations
                            this.annotationWriter.write(annot);
                            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                        }

                        // write end attributeList
                        this.streamWriter.writeEndElement();
                    }
                }
                // write end db ref
                this.streamWriter.writeEndElement();

            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to write the db reference : "+object.toString(), e);
            }
        }
    }

    public XMLStreamWriter2 getStreamWriter() {
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

    protected abstract void writeStartDbRef() throws XMLStreamException;
}
