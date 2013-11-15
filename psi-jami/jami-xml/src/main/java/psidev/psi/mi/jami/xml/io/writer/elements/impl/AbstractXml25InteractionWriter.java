package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.analysis.graph.BindingSiteCliqueFinder;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectIndex;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Abstract writer of interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public abstract class AbstractXml25InteractionWriter<T extends Interaction, P extends Participant> implements PsiXml25ElementWriter<T> {

    private XMLStreamWriter2 streamWriter;
    private PsiXml25ObjectIndex objectIndex;
    private PsiXml25XrefWriter primaryRefWriter;
    private PsiXml25XrefWriter secondaryRefWriter;
    private PsiXml25ElementWriter<P> participantWriter;
    private PsiXml25ElementWriter<CvTerm> interactionTypeWriter;
    private PsiXml25ElementWriter<Annotation> attributeWriter;
    private PsiXml25ElementWriter<Set<Feature>> inferredInteractionWriter;

    public AbstractXml25InteractionWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex,
                                          PsiXml25ElementWriter<P> participantWriter){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXml25InteractionWriter");
        }
        this.streamWriter = writer;

        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the AbstractXml25InteractionWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
        if (participantWriter == null){
            throw new IllegalArgumentException("The PsiXml 2.5 participant writer is mandatory for the AbstractXml25InteractionWriter.");
        }
        this.participantWriter = participantWriter;
        this.primaryRefWriter = new Xml25PrimaryXrefWriter(writer);
        this.secondaryRefWriter = new Xml25SecondaryXrefWriter(writer);
        this.interactionTypeWriter = new Xml25InteractionTypeWriter(writer);
        this.attributeWriter = new Xml25AnnotationWriter(writer);
        this.inferredInteractionWriter = new Xml25InferredInteractionWriter(writer, objectIndex);
    }

    public AbstractXml25InteractionWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex,
                                             PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                             PsiXml25ElementWriter<P> participantWriter, PsiXml25ElementWriter<CvTerm> interactionTypeWriter,
                                             PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Set<Feature>> inferredInteractionWriter) {
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXml25InteractionWriter");
        }
        this.streamWriter = writer;

        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the AbstractXml25InteractionWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
        if (participantWriter == null){
            throw new IllegalArgumentException("The PsiXml 2.5 participant writer is mandatory for the AbstractXml25InteractionWriter.");
        }
        this.participantWriter = participantWriter;
        this.primaryRefWriter = primaryRefWriter != null ? primaryRefWriter : new Xml25PrimaryXrefWriter(writer);
        this.secondaryRefWriter = secondaryRefWriter != null ? secondaryRefWriter : new Xml25SecondaryXrefWriter(writer);
        this.interactionTypeWriter = interactionTypeWriter != null ? interactionTypeWriter : new Xml25InteractionTypeWriter(writer);
        this.attributeWriter = attributeWriter != null ? attributeWriter : new Xml25AnnotationWriter(writer);
        this.inferredInteractionWriter = inferredInteractionWriter != null ? inferredInteractionWriter : new Xml25InferredInteractionWriter(writer, objectIndex);
    }

    @Override
    public void write(T object) throws MIIOException {
        try {
            // write start
            this.streamWriter.writeStartElement("interaction");
            // write id attribute
            int id = this.objectIndex.extractIdFor(object);
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

    protected void writeAttributes(T object) throws XMLStreamException {
        // write attributes
        if (!object.getAnnotations().isEmpty()){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write start attribute list
            this.streamWriter.writeStartElement("attributeList");
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (Object ann : object.getAnnotations()){
                this.attributeWriter.write((Annotation)ann);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end attributeList
            this.streamWriter.writeEndElement();
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected void writeInteractionType(T object) throws XMLStreamException {
        if (object.getInteractionType() != null){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            this.interactionTypeWriter.write(object.getInteractionType());
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected void writeInferredInteractions(T object) throws XMLStreamException {
        Collection<Set<Feature>> inferredInteractions = collectInferredInteractionsFrom(object);
        if (inferredInteractions != null && !inferredInteractions.isEmpty()){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            this.streamWriter.writeStartElement("inferredInteractionList");
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (Set<Feature> inferred : inferredInteractions){
                this.inferredInteractionWriter.write(inferred);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            this.streamWriter.writeEndElement();
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected void writeParticipants(T object) throws XMLStreamException {
        if (object.getParticipants().isEmpty()){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            this.streamWriter.writeStartElement("participantList");
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (Object participant : object.getParticipants()){
                this.participantWriter.write((P)participant);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            this.streamWriter.writeEndElement();
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected void writeNames(T object) throws XMLStreamException {
        boolean hasShortLabel = object.getShortName() != null;
        if (hasShortLabel){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            this.streamWriter.writeStartElement("names");
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write shortname
            this.streamWriter.writeStartElement("shortLabel");
            this.streamWriter.writeCharacters(object.getShortName());
            this.streamWriter.writeEndElement();
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write end names
            this.streamWriter.writeEndElement();
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected abstract void writeAvailability(T object);
    protected abstract void writeExperiments(T object) throws XMLStreamException;
    protected abstract void writeOtherAttributes(T object);
    protected abstract void writeIntraMolecular(T object);
    protected abstract void writeModelled(T object);

    protected void writeXref(T object) throws XMLStreamException {
        if (!object.getIdentifiers().isEmpty()){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            writeXrefFromInteractionIdentifiers(object);
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
        else if (!object.getXrefs().isEmpty()){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            writeXrefFromInteractionXrefs(object);
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected void writeXrefFromInteractionXrefs(T object) throws XMLStreamException {
        Iterator<Xref> refIterator = object.getXrefs().iterator();
        // default qualifier is null as we are not processing identifiers
        this.primaryRefWriter.setDefaultRefType(null);
        this.primaryRefWriter.setDefaultRefTypeAc(null);
        this.secondaryRefWriter.setDefaultRefType(null);
        this.secondaryRefWriter.setDefaultRefTypeAc(null);
        // write start xref
        this.streamWriter.writeStartElement("xref");
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);

        int index = 0;
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
        }

        // write end xref
        this.streamWriter.writeEndElement();
    }

    protected void writeXrefFromInteractionIdentifiers(T object) throws XMLStreamException {
        // write start xref
        this.streamWriter.writeStartElement("xref");
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);

        // all these xrefs are identity
        this.primaryRefWriter.setDefaultRefType(Xref.IDENTITY);
        this.primaryRefWriter.setDefaultRefTypeAc(Xref.IDENTITY_MI);
        this.secondaryRefWriter.setDefaultRefType(Xref.IDENTITY);
        this.secondaryRefWriter.setDefaultRefTypeAc(Xref.IDENTITY_MI);

        // write secondaryRefs and primary ref if not done already)
        Iterator<Xref> refIterator = object.getIdentifiers().iterator();
        boolean hasWrittenPrimaryRef = false;
        while (refIterator.hasNext()){
            Xref ref = refIterator.next();
            if (!hasWrittenPrimaryRef){
                hasWrittenPrimaryRef = true;
                this.primaryRefWriter.write(ref);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            else{
                this.secondaryRefWriter.write(ref);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
        }

        // write other xrefs
        if (!object.getXrefs().isEmpty()){
            // default qualifier is null
            this.secondaryRefWriter.setDefaultRefType(null);
            this.secondaryRefWriter.setDefaultRefTypeAc(null);
            for (Object ref : object.getXrefs()){
                this.secondaryRefWriter.write((Xref)ref);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
        }

        // write end xref
        this.streamWriter.writeEndElement();
    }

    protected XMLStreamWriter2 getStreamWriter() {
        return streamWriter;
    }

    protected PsiXml25ObjectIndex getObjectIndex() {
        return objectIndex;
    }

    protected abstract void writeParameters(T object);

    protected abstract void writeConfidences(T object);

    protected abstract void writeNegative(T object);

    private Collection<Set<Feature>> collectInferredInteractionsFrom(T object){
        BindingSiteCliqueFinder<T,Feature> cliqueFinder = new BindingSiteCliqueFinder<T, Feature>(object);
        return cliqueFinder.getAllMaximalCliques();
    }
}
