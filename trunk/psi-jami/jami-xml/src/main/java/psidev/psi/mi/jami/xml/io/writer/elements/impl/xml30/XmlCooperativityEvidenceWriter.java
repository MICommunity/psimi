package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.CooperativityEvidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlVariableNameWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlCvTermWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Xml 30 writer for cooperativity evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlCooperativityEvidenceWriter implements PsiXmlElementWriter<CooperativityEvidence> {
    private XMLStreamWriter streamWriter;
    private PsiXmlElementWriter<Publication> publicationWriter;
    private PsiXmlVariableNameWriter<CvTerm> cvWriter;

    public XmlCooperativityEvidenceWriter(XMLStreamWriter writer){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the XmlCooperativityEvidenceWriter");
        }
        this.streamWriter = writer;
    }

    public PsiXmlElementWriter<Publication> getPublicationWriter() {
        if (this.publicationWriter == null){
            initialisePublicationWriter();
        }
        return publicationWriter;
    }

    protected void initialisePublicationWriter() {
        this.publicationWriter = new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlPublicationWriter(streamWriter);
    }

    public void setPublicationWriter(PsiXmlElementWriter<Publication> publicationWriter) {
        this.publicationWriter = publicationWriter;
    }

    public PsiXmlVariableNameWriter<CvTerm> getCvWriter() {
        if (this.cvWriter == null){
            initialiseCvWriter();
        }
        return cvWriter;
    }

    protected void initialiseCvWriter() {
        this.cvWriter = new XmlCvTermWriter(streamWriter);
    }

    public void setCvWriter(PsiXmlVariableNameWriter<CvTerm> cvWriter) {
        this.cvWriter = cvWriter;
    }

    @Override
    public void write(CooperativityEvidence object) throws MIIOException {
        try {
            // write start
            this.streamWriter.writeStartElement("cooperativityEvidenceDescription");
            // write publication
            writePublication(object);
            // write evidences
            writeEvidenceMethods(object);
            // write end cooperativity evidence
            this.streamWriter.writeEndElement();

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the participant : "+object.toString(), e);
        }
    }

    private void writeEvidenceMethods(CooperativityEvidence object) throws XMLStreamException {
        if (!object.getEvidenceMethods().isEmpty()){
            // write start
            this.streamWriter.writeStartElement("evidenceMethodList");
            for (CvTerm term : object.getEvidenceMethods()){
                getCvWriter().write(term, "evidenceMethod");
            }
            // write end list
            this.streamWriter.writeEndElement();
        }
    }

    protected void writePublication(CooperativityEvidence object) throws XMLStreamException {
        if (object.getPublication() != null){
            getPublicationWriter().write(object.getPublication());
        }
    }

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }
}
