package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.analysis.graph.BindingSiteCliqueFinder;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultExperiment;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Abstract writer of interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public abstract class AbstractXmlInteractionWriter<T extends Interaction, P extends Participant> implements PsiXmlInteractionWriter<T> {

    private XMLStreamWriter streamWriter;
    private PsiXmlObjectCache objectIndex;
    private PsiXmlXrefWriter xrefWriter;
    private PsiXmlParticipantWriter<P> participantWriter;
    private PsiXmlVariableNameWriter<CvTerm> interactionTypeWriter;
    private PsiXmlElementWriter<Annotation> attributeWriter;
    private PsiXmlElementWriter<Set<Feature>> inferredInteractionWriter;
    private Experiment defaultExperiment;
    private PsiXmlExperimentWriter experimentWriter;
    private PsiXmlElementWriter<Checksum> checksumWriter;

    public AbstractXmlInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXmlInteractionWriter");
        }
        this.streamWriter = writer;

        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the AbstractXmlInteractionWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
    }

    public PsiXmlParticipantWriter<P> getParticipantWriter() {
        if (this.participantWriter == null){
            initialiseParticipantWriter();
        }
        return participantWriter;
    }

    protected abstract void initialiseParticipantWriter();

    public void setParticipantWriter(PsiXmlParticipantWriter<P> participantWriter) {
        this.participantWriter = participantWriter;
    }

    public PsiXmlXrefWriter getXrefWriter() {
        if (this.xrefWriter == null){
            this.xrefWriter = new XmlDbXrefWriter(streamWriter);
        }
        return xrefWriter;
    }

    public void setXrefWriter(PsiXmlXrefWriter xrefWriter) {
        this.xrefWriter = xrefWriter;
    }

    public void setInteractionTypeWriter(PsiXmlVariableNameWriter<CvTerm> interactionTypeWriter) {
        this.interactionTypeWriter = interactionTypeWriter;
    }

    public void setAttributeWriter(PsiXmlElementWriter<Annotation> attributeWriter) {
        this.attributeWriter = attributeWriter;
    }

    public PsiXmlElementWriter<Set<Feature>> getInferredInteractionWriter() {
        if (this.inferredInteractionWriter == null){
            this.inferredInteractionWriter = new XmlInferredInteractionWriter(streamWriter, objectIndex);
        }
        return inferredInteractionWriter;
    }

    public void setInferredInteractionWriter(PsiXmlElementWriter<Set<Feature>> inferredInteractionWriter) {
        this.inferredInteractionWriter = inferredInteractionWriter;
    }

    public void setExperimentWriter(PsiXmlExperimentWriter experimentWriter) {
        this.experimentWriter = experimentWriter;
    }

    public void setChecksumWriter(PsiXmlElementWriter<Checksum> checksumWriter) {
        this.checksumWriter = checksumWriter;
    }

    public PsiXmlElementWriter<Experiment> getExperimentWriter() {
        if (this.experimentWriter == null){
            this.experimentWriter = new Xml25ExperimentWriter(streamWriter, objectIndex);
        }
        return experimentWriter;
    }

    public PsiXmlElementWriter<Checksum> getChecksumWriter() {
        if (this.checksumWriter == null){
            this.checksumWriter = new XmlChecksumWriter(streamWriter);
        }
        return checksumWriter;
    }

    public PsiXmlVariableNameWriter<CvTerm> getInteractionTypeWriter() {
        if (this.interactionTypeWriter == null){
            this.interactionTypeWriter = new XmlCvTermWriter(streamWriter);
        }
        return interactionTypeWriter;
    }

    public PsiXmlElementWriter<Annotation> getAttributeWriter() {
        if (this.attributeWriter == null){
            this.attributeWriter = new XmlAnnotationWriter(streamWriter);
        }
        return attributeWriter;
    }

    @Override
    public void write(T object) throws MIIOException {
        try {
            // write start
            this.streamWriter.writeStartElement("interaction");
            // write id attribute
            int id = this.objectIndex.extractIdForInteraction(object);
            this.streamWriter.writeAttribute("id", Integer.toString(id));
            // write other attributes (such as imex id)
            writeOtherAttributes(object);

            // write names
            writeNames(object);
            // write Xref
            writeXref(object);
            // write availability
            writeAvailability(object);
            // write experiments
            writeExperiments(object);
            // write participants
            writeParticipants(object);
            // write inferred interactions
            writeInferredInteractions(object);
            // write interaction type
            writeInteractionType(object);
            // write modelled
            writeModelled(object);
            // write intramolecular
            writeIntraMolecular(object);
            // write negative
            writeNegative(object);
            // write confidences
            writeConfidences(object);
            // write parameters
            writeParameters(object);
            // write attributes
            writeAttributes(object);
            // write end interaction
            this.streamWriter.writeEndElement();

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the interaction : "+object.toString(), e);
        }
    }

    public Experiment getDefaultExperiment() {
        if (this.defaultExperiment == null){
            initialiseDefaultExperiment();
        }
        return defaultExperiment;
    }

    public void setDefaultExperiment(Experiment defaultExperiment) {
        this.defaultExperiment = defaultExperiment;
    }

    @Override
    public Experiment extractDefaultExperimentFrom(T interaction) {
        return getDefaultExperiment();
    }

    @Override
    public boolean writeComplexAsInteractor() {
        return this.participantWriter.writeComplexAsInteractor();
    }

    @Override
    public void setComplexAsInteractor(boolean complexAsInteractor) {
        getParticipantWriter().setComplexAsInteractor(complexAsInteractor);
    }

    protected void writeAttributes(T object) throws XMLStreamException {
        // write attributes
        if (!object.getAnnotations().isEmpty()){
            // write start attribute list
            this.streamWriter.writeStartElement("attributeList");
            for (Object ann : object.getAnnotations()){
                getAttributeWriter().write((Annotation)ann);
            }
            for (Object c : object.getChecksums()){
                getChecksumWriter().write((Checksum)c);
            }
            // write end attributeList
            this.streamWriter.writeEndElement();
        }
        // write checksum
        else if (!object.getChecksums().isEmpty()){
            // write start attribute list
            this.streamWriter.writeStartElement("attributeList");
            for (Object c : object.getChecksums()){
                getChecksumWriter().write((Checksum)c);
            }
            // write end attributeList
            this.streamWriter.writeEndElement();
        }
    }

    protected void writeInteractionType(T object) throws XMLStreamException {
        if (object.getInteractionType() != null){
            getInteractionTypeWriter().write(object.getInteractionType(),"interactionType");
        }
    }

    protected void writeInferredInteractions(T object) throws XMLStreamException {
        Collection<Set<Feature>> inferredInteractions = collectInferredInteractionsFrom(object);
        if (inferredInteractions != null && !inferredInteractions.isEmpty()){
            this.streamWriter.writeStartElement("inferredInteractionList");
            for (Set<Feature> inferred : inferredInteractions){
                getInferredInteractionWriter().write(inferred);
            }
            this.streamWriter.writeEndElement();
        }
    }

    protected void writeParticipants(T object) throws XMLStreamException {
        if (!object.getParticipants().isEmpty()){
            this.streamWriter.writeStartElement("participantList");
            for (Object participant : object.getParticipants()){
                getParticipantWriter().write((P)participant);
            }
            this.streamWriter.writeEndElement();
        }
    }

    protected void writeNames(T object) throws XMLStreamException {
        boolean hasShortLabel = object.getShortName() != null;
        if (hasShortLabel){
            this.streamWriter.writeStartElement("names");
            // write shortname
            this.streamWriter.writeStartElement("shortLabel");
            this.streamWriter.writeCharacters(object.getShortName());
            this.streamWriter.writeEndElement();
            // write end names
            this.streamWriter.writeEndElement();
        }
    }

    protected abstract void writeAvailability(T object) throws XMLStreamException;
    protected abstract void writeExperiments(T object) throws XMLStreamException;
    protected abstract void writeOtherAttributes(T object) throws XMLStreamException;
    protected abstract void writeIntraMolecular(T object) throws XMLStreamException;
    protected abstract void writeModelled(T object) throws XMLStreamException;

    protected void writeXref(T object) throws XMLStreamException {
        if (!object.getIdentifiers().isEmpty()){
            writeXrefFromInteractionIdentifiers(object);
        }
        else if (!object.getXrefs().isEmpty()){
            writeXrefFromInteractionXrefs(object);
        }
    }

    protected void writeXrefFromInteractionXrefs(T object) throws XMLStreamException {
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
                getXrefWriter().write(ref, "primaryRef");
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

    protected void writeXrefFromInteractionIdentifiers(T object) throws XMLStreamException {
        // write start xref
        this.streamWriter.writeStartElement("xref");

        // all these xrefs are identity
        getXrefWriter().setDefaultRefType(Xref.IDENTITY);
        getXrefWriter().setDefaultRefTypeAc(Xref.IDENTITY_MI);

        // write secondaryRefs and primary ref if not done already)
        Iterator<Xref> refIterator = object.getIdentifiers().iterator();
        boolean hasWrittenPrimaryRef = false;
        while (refIterator.hasNext()){
            Xref ref = refIterator.next();
            if (!hasWrittenPrimaryRef){
                hasWrittenPrimaryRef = true;
                getXrefWriter().write(ref, "primaryRef");
            }
            else{
                getXrefWriter().write(ref,"secondaryRef");
            }
        }

        // write other xrefs
        if (!object.getXrefs().isEmpty()){
            // default qualifier is null
            getXrefWriter().setDefaultRefType(null);
            getXrefWriter().setDefaultRefTypeAc(null);
            for (Object ref : object.getXrefs()){
                getXrefWriter().write((Xref)ref,"secondaryRef");
            }
        }

        // write end xref
        this.streamWriter.writeEndElement();
    }

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }

    protected PsiXmlObjectCache getObjectIndex() {
        return objectIndex;
    }

    protected abstract void writeParameters(T object) throws XMLStreamException;

    protected abstract void writeConfidences(T object) throws XMLStreamException;

    protected abstract void writeNegative(T object) throws XMLStreamException;

    protected void writeExperimentRef() throws XMLStreamException {
        getStreamWriter().writeStartElement("experimentList");
        getStreamWriter().writeStartElement("experimentRef");
        getStreamWriter().writeCharacters(Integer.toString(getObjectIndex().extractIdForExperiment(getDefaultExperiment())));
        getStreamWriter().writeEndElement();
        getStreamWriter().writeEndElement();
    }

    protected void writeExperimentDescription() throws XMLStreamException {
        getStreamWriter().writeStartElement("experimentList");
        getExperimentWriter().write(getDefaultExperiment());
        getStreamWriter().writeEndElement();
    }

    protected void initialiseDefaultExperiment(){
        this.defaultExperiment = new DefaultExperiment(new DefaultPublication("Mock publication for interactions that do not have experimental details.",(String)null,(Date)null));
    }

    protected void writeAttribute(String name, String nameAc) throws XMLStreamException {
        // write start
        this.streamWriter.writeStartElement("attribute");
        // write topic
        this.streamWriter.writeAttribute("name", name);
        if (nameAc!= null){
            this.streamWriter.writeAttribute("nameAc", nameAc);
        }
        // write end attribute
        this.streamWriter.writeEndElement();
    }

    private Collection<Set<Feature>> collectInferredInteractionsFrom(T object){
        BindingSiteCliqueFinder<T,Feature> cliqueFinder = new BindingSiteCliqueFinder<T, Feature>(object);
        return cliqueFinder.getAllMaximalCliques();
    }
}
