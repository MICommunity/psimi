package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml30;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlVariableNameWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlAnnotationWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlCvTermWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlCooperativityEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract Xml 30 writer for cooperative effect
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public abstract class AbstractXmlCooperativeEffectWriter<C extends CooperativeEffect> implements PsiXmlElementWriter<C> {
    private XMLStreamWriter streamWriter;
    private PsiXmlElementWriter<CooperativityEvidence> cooperativityEvidenceWriter;
    private PsiXmlVariableNameWriter<CvTerm> cvWriter;
    private PsiXmlObjectCache objectIndex;
    private PsiXmlElementWriter<Annotation> attributeWriter;

    public AbstractXmlCooperativeEffectWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXmlCooperativeEffectWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the AbstractXmlCooperativeEffectWriter. It is necessary for generating an id to an interaction");
        }
        this.objectIndex = objectIndex;
    }

    public PsiXmlElementWriter<CooperativityEvidence> getCooperativityEvidenceWriter() {
        if (this.cooperativityEvidenceWriter == null){
            initialiseCooperativityEvidenceWriter();
        }
        return cooperativityEvidenceWriter;
    }

    protected void initialiseCooperativityEvidenceWriter() {
        this.cooperativityEvidenceWriter = new XmlCooperativityEvidenceWriter(streamWriter);
    }

    public void setCooperativityEvidenceWriter(PsiXmlElementWriter<CooperativityEvidence> cooperativityEvidenceWriter) {
        this.cooperativityEvidenceWriter = cooperativityEvidenceWriter;
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
    public void write(C object) throws MIIOException {
        try {
            // write start
            writeStartCooperativeEffect();
            // write cooperativity evidence list
            writeCooperativityEvidenceList(object);
            // write affected interaction list
            writeAffectedInteractionList(object);
            // write effect outcome
            writeOutcome(object);
            // write effect response
            writeResponse(object);
            // write attributes
            writeAttributes(object);
            // write other properties
            writeOtherProperties(object);
            // write end cooperative effect
            this.streamWriter.writeEndElement();

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the participant : "+object.toString(), e);
        }
    }

    protected void writeOutcome(C object) {
        if (object.getOutCome() != null){
            getCvWriter().write(object.getOutCome(), "cooperativeEffectOutcome");
        }
    }

    protected void writeResponse(C object) {
        if (object.getResponse() != null){
            getCvWriter().write(object.getResponse(), "cooperativeEffectResponse");
        }
    }

    protected void writeAttributes(C object) throws XMLStreamException {
        // write attributes
        if (!object.getAnnotations().isEmpty()){
            // write start attribute list
            this.streamWriter.writeStartElement("attributeList");
            for (Object ann : object.getAnnotations()){
                getAttributeWriter().write((Annotation)ann);
            }
            // write end attributeList
            this.streamWriter.writeEndElement();
        }
    }

    protected abstract void writeOtherProperties(C object) throws XMLStreamException;

    protected void writeAffectedInteractionList(C object) throws XMLStreamException {
        if (!object.getAffectedInteractions().isEmpty()){
            // write start
            this.streamWriter.writeStartElement("affectedInteractionList");
            for (ModelledInteraction interaction : object.getAffectedInteractions()){
                // write start interaction
                this.streamWriter.writeStartElement("affectedInteractionRef");
                getStreamWriter().writeCharacters(Integer.toString(getObjectIndex().extractIdForInteraction(interaction)));
                // write end interaction
                this.streamWriter.writeEndElement();
            }
            // write end list
            this.streamWriter.writeEndElement();
        }
    }

    protected abstract void writeStartCooperativeEffect()throws XMLStreamException ;

    protected void writeCooperativityEvidenceList(C object) throws XMLStreamException {
        if (!object.getCooperativityEvidences().isEmpty()){
            // write start
            this.streamWriter.writeStartElement("cooperativityEvidenceList");
            for (CooperativityEvidence evidence : object.getCooperativityEvidences()){
                getCooperativityEvidenceWriter().write(evidence);
            }
            // write end list
            this.streamWriter.writeEndElement();
        }
    }

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }

    protected PsiXmlObjectCache getObjectIndex() {
        return objectIndex;
    }

}
