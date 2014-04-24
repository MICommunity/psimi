package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlExperimentWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlPublicationWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Date;
import java.util.Iterator;

/**
 * PSI-XML 2.5 experiment writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlExperimentWriter implements PsiXmlExperimentWriter {
    private XMLStreamWriter streamWriter;
    private PsiXmlObjectCache objectIndex;
    private PsiXmlPublicationWriter publicationWriter;
    private PsiXmlXrefWriter primaryRefWriter;
    private PsiXmlXrefWriter secondaryRefWriter;
    private PsiXmlElementWriter<Organism> hostOrganismWriter;
    private PsiXmlElementWriter<CvTerm> detectionMethodWriter;
    private PsiXmlElementWriter<Annotation> attributeWriter;
    private PsiXmlElementWriter<Confidence> confidenceWriter;
    private Publication defaultPublication;

    public XmlExperimentWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
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
            this.publicationWriter = new XmlPublicationWriter(streamWriter);

        }
        return publicationWriter;
    }

    public void setPublicationWriter(PsiXmlPublicationWriter publicationWriter) {
        this.publicationWriter = publicationWriter;
    }

    public PsiXmlXrefWriter getPrimaryRefWriter() {
        if (this.primaryRefWriter == null){
            this.primaryRefWriter = new XmlPrimaryXrefWriter(streamWriter);
        }
        return primaryRefWriter;
    }

    public void setPrimaryRefWriter(PsiXmlXrefWriter primaryRefWriter) {
        this.primaryRefWriter = primaryRefWriter;
    }

    public PsiXmlXrefWriter getSecondaryRefWriter() {
        if (this.secondaryRefWriter == null){
            this.secondaryRefWriter = new XmlSecondaryXrefWriter(streamWriter);
        }
        return secondaryRefWriter;
    }

    public void setSecondaryRefWriter(PsiXmlXrefWriter secondaryRefWriter) {
        this.secondaryRefWriter = secondaryRefWriter;
    }

    public PsiXmlElementWriter<Organism> getHostOrganismWriter() {
        if (this.hostOrganismWriter == null){
            this.hostOrganismWriter = new XmlHostOrganismWriter(streamWriter);
        }
        return hostOrganismWriter;
    }

    public void setHostOrganismWriter(PsiXmlElementWriter<Organism> hostOrganismWriter) {
        this.hostOrganismWriter = hostOrganismWriter;
    }

    public PsiXmlElementWriter<CvTerm> getDetectionMethodWriter() {
        if (this.detectionMethodWriter == null){
            this.detectionMethodWriter = new XmlInteractionDetectionMethodWriter(streamWriter);
        }
        return detectionMethodWriter;
    }

    public void setDetectionMethodWriter(PsiXmlElementWriter<CvTerm> detectionMethodWriter) {
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
            this.confidenceWriter = new XmlConfidenceWriter(streamWriter);
        }
        return confidenceWriter;
    }

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
            // write attribute list
            writeAttributes(object);

            // write end experiment
            this.streamWriter.writeEndElement();

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the experiment : "+object.toString(), e);
        }
    }

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
            if (object.getPublication() != null){
                Publication pub = object.getPublication();
                // write all attributes from publication if identifiers are not empty.
                // if the list of identifiers of a publication is not empty, annotations of a publication are not exported
                // in bibref elements
                if (!pub.getIdentifiers().isEmpty()){
                    getPublicationWriter().writeAllPublicationAttributes(pub, object.getAnnotations());
                }
            }

            // write end attributeList
            this.streamWriter.writeEndElement();
        }
        // write annotations from publication
        else if (object.getPublication() != null){
            Publication pub = object.getPublication();
            // write all attributes from publication if identifiers are not empty.
            // if the list of identifiers of a publication is not empty, annotations of a publication are not exported
            // in bibref elements
            if (!pub.getIdentifiers().isEmpty()){
                getPublicationWriter().writeAllPublicationAttributes(pub);
            }
        }
    }

    protected void writeInteractiondetectionMethod(Experiment object) throws XMLStreamException {
        CvTerm detectionMethod = object.getInteractionDetectionMethod();
        // write cv
        getDetectionMethodWriter().write(detectionMethod);
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
        if (!object.getXrefs().isEmpty() || imexId != null){
            // write start xref
            this.streamWriter.writeStartElement("xref");
            if (!object.getXrefs().isEmpty()){
                writeXrefFromExperimentXrefs(object, imexId);
            }
            else{
                writeImexId("primaryRef", imexId);
            }
            // write end xref
            this.streamWriter.writeEndElement();
        }
    }

    protected void writeXrefFromExperimentXrefs(Experiment object, String imexId) throws XMLStreamException {
        Iterator<Xref> refIterator = object.getXrefs().iterator();
        // default qualifier is null as we are not processing identifiers
        getPrimaryRefWriter().setDefaultRefType(null);
        getPrimaryRefWriter().setDefaultRefTypeAc(null);
        getSecondaryRefWriter().setDefaultRefType(null);
        getSecondaryRefWriter().setDefaultRefTypeAc(null);

        int index = 0;
        boolean foundImexId = false;
        while (refIterator.hasNext()){
            Xref ref = refIterator.next();
            // write primaryRef
            if (index == 0){
                getPrimaryRefWriter().write(ref);
                index++;
            }
            // write secondaryref
            else{
                getSecondaryRefWriter().write(ref);
                index++;
            }

            // found IMEx id
            if (imexId != null && imexId.equals(ref.getId())
                    && XrefUtils.isXrefFromDatabase(ref, Xref.IMEX_MI, Xref.IMEX)
                    && XrefUtils.doesXrefHaveQualifier(ref, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)){
                foundImexId=true;
            }
        }

        // write imex id
        if (!foundImexId && imexId != null){
            writeImexId("secondaryRef", imexId);
        }
    }

    protected void writeImexId(String nodeName, String imexId) throws XMLStreamException {
        // write start
        this.streamWriter.writeStartElement(nodeName);
        // write database
        this.streamWriter.writeAttribute("db", Xref.IMEX);
        this.streamWriter.writeAttribute("dbAc", Xref.IMEX_MI);
        // write id
        this.streamWriter.writeAttribute("id", imexId);
        // write qualifier
        this.streamWriter.writeAttribute("refType", Xref.IMEX_PRIMARY);
        this.streamWriter.writeAttribute("refTypeAc", Xref.IMEX_PRIMARY_MI);
        // write end db ref
        this.streamWriter.writeEndElement();
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
