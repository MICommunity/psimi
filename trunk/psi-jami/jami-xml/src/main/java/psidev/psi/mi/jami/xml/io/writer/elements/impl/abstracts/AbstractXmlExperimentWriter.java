package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Date;
import java.util.Iterator;

/**
 * Abstract PSI-XML experiment writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public abstract class AbstractXmlExperimentWriter implements PsiXmlExperimentWriter {
    private XMLStreamWriter streamWriter;
    private PsiXmlObjectCache objectIndex;
    private PsiXmlPublicationWriter publicationWriter;
    private PsiXmlXrefWriter xrefWriter;
    private PsiXmlElementWriter<Organism> hostOrganismWriter;
    private PsiXmlVariableNameWriter<CvTerm> detectionMethodWriter;
    private PsiXmlElementWriter<Annotation> attributeWriter;
    private PsiXmlElementWriter<Confidence> confidenceWriter;
    private Publication defaultPublication;

    public AbstractXmlExperimentWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the XmlExperimentWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the XmlExperimentWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
    }

    public PsiXmlPublicationWriter getPublicationWriter() {
        if (this.publicationWriter == null){
            initialisePublicationWriter();

        }
        return publicationWriter;
    }

    protected abstract void initialisePublicationWriter();

    public void setPublicationWriter(PsiXmlPublicationWriter publicationWriter) {
        this.publicationWriter = publicationWriter;
    }

    public PsiXmlXrefWriter getXrefWriter() {
        if (this.xrefWriter == null){
            initialiseXrefWriter();
        }
        return xrefWriter;
    }

    protected abstract void initialiseXrefWriter();

    public void setXrefWriter(PsiXmlXrefWriter xrefWriter) {
        this.xrefWriter = xrefWriter;
    }

    public PsiXmlElementWriter<Organism> getHostOrganismWriter() {
        if (this.hostOrganismWriter == null){
            initialiseHostOrganismWriter();
        }
        return hostOrganismWriter;
    }

    protected abstract void initialiseHostOrganismWriter();

    public void setHostOrganismWriter(PsiXmlElementWriter<Organism> hostOrganismWriter) {
        this.hostOrganismWriter = hostOrganismWriter;
    }

    public PsiXmlVariableNameWriter<CvTerm> getDetectionMethodWriter() {
        if (this.detectionMethodWriter == null){
            initialiseDetectionMethodWriter();
        }
        return detectionMethodWriter;
    }

    protected abstract void initialiseDetectionMethodWriter();

    public void setDetectionMethodWriter(PsiXmlVariableNameWriter<CvTerm> detectionMethodWriter) {
        this.detectionMethodWriter = detectionMethodWriter;
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

    public PsiXmlElementWriter<Confidence> getConfidenceWriter() {
        if (this.confidenceWriter == null){
            initialiseConfidenceWriter();
        }
        return confidenceWriter;
    }

    protected abstract void initialiseConfidenceWriter();

    public void setConfidenceWriter(PsiXmlElementWriter<Confidence> confidenceWriter) {
        this.confidenceWriter = confidenceWriter;
    }

    @Override
    public void write(Experiment object) throws MIIOException {
        try {
            // write start
            this.streamWriter.writeStartElement("experimentDescription");
            int id = this.objectIndex.extractIdForExperiment(object);
            // write id attribute
            this.streamWriter.writeAttribute("id", Integer.toString(id));

            // write names
            writeNames(object);
            // write publication and xrefs
            writePublicationAndXrefs(object);
            // write host organism
            writeHostOrganism(object);
            // write interaction detection method
            writeInteractiondetectionMethod(object);
            // write other properties
            writeOtherProperties(object);
            // write confidences
            writeConfidences(object);
            // write variable parameters
            writeVariableParameters(object);
            // write attribute list
            writeAttributes(object);

            // write end experiment
            this.streamWriter.writeEndElement();

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the experiment : "+object.toString(), e);
        }
    }

    protected abstract void writeVariableParameters(Experiment object) throws XMLStreamException;

    protected void writeOtherProperties(Experiment object) throws XMLStreamException {
        // nothing to do here
    }

    protected void writeConfidences(Experiment object) throws XMLStreamException {
       if (!object.getConfidences().isEmpty()){
           // write start confidence list
           this.streamWriter.writeStartElement("confidenceList");
           for (Confidence conf : object.getConfidences()){
               getConfidenceWriter().write(conf);
           }
           // write end confidence
           this.streamWriter.writeEndElement();
       }
    }

    protected void writeAttributes(Experiment object) throws XMLStreamException {
        // write annotations from experiment first
        if (!object.getAnnotations().isEmpty()){
            // write start attribute list
            this.streamWriter.writeStartElement("attributeList");
            for (Annotation ann : object.getAnnotations()){
                getAttributeWriter().write(ann);
            }

            // write publication attributes if not done at the bibref level
            writeOtherAttributes(object, false);

            // write end attributeList
            this.streamWriter.writeEndElement();
        }
        // write annotations from publication
        else{
            writeOtherAttributes(object, true);
        }
    }

    protected abstract void writeOtherAttributes(Experiment object, boolean needToWriteAttributeList) throws XMLStreamException;

    protected void writeInteractiondetectionMethod(Experiment object) throws XMLStreamException {
        CvTerm detectionMethod = object.getInteractionDetectionMethod();
        // write cv
        getDetectionMethodWriter().write(detectionMethod, "interactionDetectionMethod");
    }

    protected void writeHostOrganism(Experiment object) throws XMLStreamException {
        Organism host = object.getHostOrganism();
        if (host != null){
            // write start host organism list
            this.streamWriter.writeStartElement("hostOrganismList");
            // write host
            getHostOrganismWriter().write(host);
            // write end host organism list
            this.streamWriter.writeEndElement();
        }
    }

    protected void writePublicationAndXrefs(Experiment object) throws XMLStreamException {
        String imexId=null;
        // write publication
        Publication publication = object.getPublication();
        if (publication != null){
            getPublicationWriter().write(publication);
            imexId = publication.getImexId();
        }
        else{
            getPublicationWriter().write(getDefaultPublication());
            imexId = getDefaultPublication().getImexId();
        }
        // write xrefs
        writeExperimentXrefs(object, imexId);
    }

    protected abstract void writeExperimentXrefs(Experiment object, String imexId) throws XMLStreamException;

    protected void writeXrefFromExperimentXrefs(Experiment object, String imexId) throws XMLStreamException {
        Iterator<Xref> refIterator = object.getXrefs().iterator();
        // default qualifier is null as we are not processing identifiers
        getXrefWriter().setDefaultRefType(null);
        getXrefWriter().setDefaultRefTypeAc(null);

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
    }

    protected void writeNames(Experiment object) throws XMLStreamException {
        boolean hasPublicationTitle = object.getPublication() != null && object.getPublication().getTitle() != null;

        if (hasPublicationTitle){
            this.streamWriter.writeStartElement("names");

            // write fullname
            this.streamWriter.writeStartElement("fullName");
            this.streamWriter.writeCharacters(object.getPublication().getTitle());
            this.streamWriter.writeEndElement();

            // write end names
            this.streamWriter.writeEndElement();
        }
    }

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }

    protected PsiXmlObjectCache getObjectIndex() {
        return objectIndex;
    }

    @Override
    public Publication getDefaultPublication() {
        if (this.defaultPublication == null){
            initialiseDefaultPublication();
        }
        return this.defaultPublication;
    }

    @Override
    public void setDefaultPublication(Publication pub) {
        if (pub == null){
            throw new IllegalArgumentException("The default publication cannot be null");
        }
        this.defaultPublication = pub;
    }

    protected void initialiseDefaultPublication(){
        this.defaultPublication = new DefaultPublication("Mock publication for experiments that do not have a publication reference",(String)null,(Date)null);
    }
}
