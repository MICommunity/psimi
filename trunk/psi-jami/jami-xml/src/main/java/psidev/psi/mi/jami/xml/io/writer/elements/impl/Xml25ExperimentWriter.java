package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25PublicationWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;
import java.util.Iterator;

/**
 * PSI-XML 2.5 experiment writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class Xml25ExperimentWriter implements PsiXml25ElementWriter<Experiment> {
    private XMLStreamWriter2 streamWriter;
    private PsiXml25ObjectCache objectIndex;
    private PsiXml25PublicationWriter publicationWriter;
    private PsiXml25XrefWriter primaryRefWriter;
    private PsiXml25XrefWriter secondaryRefWriter;
    private PsiXml25ElementWriter<Organism> hostOrganismWriter;
    private PsiXml25ElementWriter<CvTerm> detectionMethodWriter;
    private PsiXml25ElementWriter<Annotation> attributeWriter;
    private PsiXml25ElementWriter<Confidence> confidenceWriter;

    public Xml25ExperimentWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25ExperimentWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the Xml25ExperimentWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
        this.publicationWriter = new Xml25PublicationWriter(writer);
        this.primaryRefWriter = new Xml25PrimaryXrefWriter(writer);
        this.secondaryRefWriter = new Xml25SecondaryXrefWriter(writer);
        this.hostOrganismWriter = new Xml25HostOrganismWriter(writer);
        this.detectionMethodWriter = new Xml25InteractionDetectionMethodWriter(writer);
        this.attributeWriter = new Xml25AnnotationWriter(writer);
        this.confidenceWriter = new Xml25ConfidenceWriter(writer);
    }

    public Xml25ExperimentWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex,
                                 PsiXml25PublicationWriter publicationWriter,
                                 PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                 PsiXml25ElementWriter<Organism> hostOrganismWriter, PsiXml25ElementWriter<CvTerm> detectionMethodWriter,
                                 PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Confidence> confidenceWriter) {
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25ExperimentWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the Xml25ExperimentWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
        this.publicationWriter = publicationWriter != null ? publicationWriter : new Xml25PublicationWriter(writer);
        this.primaryRefWriter = primaryRefWriter != null ? primaryRefWriter : new Xml25PrimaryXrefWriter(writer);
        this.secondaryRefWriter = secondaryRefWriter != null ? secondaryRefWriter : new Xml25SecondaryXrefWriter(writer);
        this.hostOrganismWriter = hostOrganismWriter != null ? hostOrganismWriter : new Xml25HostOrganismWriter(writer);
        this.detectionMethodWriter = detectionMethodWriter != null ? detectionMethodWriter : new Xml25InteractionDetectionMethodWriter(writer);
        this.attributeWriter = attributeWriter != null ? attributeWriter : new Xml25AnnotationWriter(writer);
        this.confidenceWriter = confidenceWriter != null ? confidenceWriter : new Xml25ConfidenceWriter(writer);
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
           this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
           // write start confidence list
           this.streamWriter.writeStartElement("confidenceList");
           this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
           for (Confidence conf : object.getConfidences()){
               this.confidenceWriter.write(conf);
               this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
           }
           // write end confidence
           this.streamWriter.writeEndElement();
           this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
       }
    }

    protected void writeAttributes(Experiment object) throws XMLStreamException {
        // write annotations from experiment first
        if (!object.getAnnotations().isEmpty()){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write start attribute list
            this.streamWriter.writeStartElement("attributeList");
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (Annotation ann : object.getAnnotations()){
                this.attributeWriter.write(ann);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }

            // write publication attributes if not done at the bibref level
            if (object.getPublication() != null){
                Publication pub = object.getPublication();
                // write all attributes from publication if identifiers are not empty.
                // if the list of identifiers of a publication is not empty, annotations of a publication are not exported
                // in bibref elements
                if (!pub.getIdentifiers().isEmpty()){
                    this.publicationWriter.writeAllPublicationAttributes(pub, object.getAnnotations());
                }
            }

            // write end attributeList
            this.streamWriter.writeEndElement();
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
        // write annotations from publication
        else if (object.getPublication() != null){
            Publication pub = object.getPublication();
            // write all attributes from publication if identifiers are not empty.
            // if the list of identifiers of a publication is not empty, annotations of a publication are not exported
            // in bibref elements
            if (!pub.getIdentifiers().isEmpty()){
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                this.publicationWriter.writeAllPublicationAttributes(pub);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
        }
    }

    protected void writeInteractiondetectionMethod(Experiment object) throws XMLStreamException {
        CvTerm detectionMethod = object.getInteractionDetectionMethod();
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        // write cv
        this.detectionMethodWriter.write(detectionMethod);
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    protected void writeHostOrganism(Experiment object) throws XMLStreamException {
        Organism host = object.getHostOrganism();
        if (host != null){
            // write start host organism list
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            this.streamWriter.writeStartElement("hostOrganismList");
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write host
            this.hostOrganismWriter.write(host);
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write end host organism list
            this.streamWriter.writeEndElement();
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected void writePublicationAndXrefs(Experiment object) throws XMLStreamException {
        String imexId=null;
        // write publication
        Publication publication = object.getPublication();
        if (publication != null){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            this.publicationWriter.write(publication);
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            imexId = publication.getImexId();
        }
        // write xrefs
        if (!object.getXrefs().isEmpty() || imexId != null){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write start xref
            this.streamWriter.writeStartElement("xref");
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            if (!object.getXrefs().isEmpty()){
                writeXrefFromExperimentXrefs(object, imexId);
            }
            else{
                writeImexId("primaryRef", imexId);
            }
            // write end xref
            this.streamWriter.writeEndElement();
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected void writeXrefFromExperimentXrefs(Experiment object, String imexId) throws XMLStreamException {
        Iterator<Xref> refIterator = object.getXrefs().iterator();
        // default qualifier is null as we are not processing identifiers
        this.primaryRefWriter.setDefaultRefType(null);
        this.primaryRefWriter.setDefaultRefTypeAc(null);
        this.secondaryRefWriter.setDefaultRefType(null);
        this.secondaryRefWriter.setDefaultRefTypeAc(null);

        int index = 0;
        boolean foundImexId = false;
        while (refIterator.hasNext()){
            Xref ref = refIterator.next();
            // write primaryRef
            if (index == 0){
                this.primaryRefWriter.write(ref);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                index++;
            }
            // write secondaryref
            else{
                this.secondaryRefWriter.write(ref);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
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
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    protected void writeNames(Experiment object) throws XMLStreamException {
        boolean hasPublicationTitle = object.getPublication() != null && object.getPublication().getTitle() != null;

        if (hasPublicationTitle){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            this.streamWriter.writeStartElement("names");
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);

            // write fullname
            this.streamWriter.writeStartElement("fullName");
            this.streamWriter.writeCharacters(object.getPublication().getTitle());
            this.streamWriter.writeEndElement();
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);

            // write end names
            this.streamWriter.writeEndElement();
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected XMLStreamWriter2 getStreamWriter() {
        return streamWriter;
    }

    protected PsiXml25ElementWriter<Organism> getHostOrganismWriter() {
        return hostOrganismWriter;
    }
}
