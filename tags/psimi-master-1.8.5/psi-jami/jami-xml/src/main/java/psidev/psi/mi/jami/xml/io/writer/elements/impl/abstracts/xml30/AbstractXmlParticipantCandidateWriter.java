package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml30;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.ParticipantCandidate;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract Xml writer for participant candidate
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public abstract class AbstractXmlParticipantCandidateWriter<P extends ParticipantCandidate, F extends Feature>
        implements PsiXmlElementWriter<P> {
    private XMLStreamWriter streamWriter;
    private PsiXmlObjectCache objectIndex;
    private PsiXmlElementWriter<F> featureWriter;
    private PsiXmlElementWriter<Interactor> interactorWriter;

    public AbstractXmlParticipantCandidateWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXmlParticipantCandidateWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the AbstractXmlParticipantCandidateWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
    }

    public PsiXmlElementWriter<Interactor> getInteractorWriter() {
        if (this.interactorWriter == null){
            initialiseInteractorWriter();
        }
        return interactorWriter;
    }

    protected abstract void initialiseInteractorWriter();

    public void setInteractorWriter(PsiXmlElementWriter<Interactor> interactorWriter) {
        this.interactorWriter = interactorWriter;
    }

    public PsiXmlElementWriter<F> getFeatureWriter() {
        if (featureWriter == null){
            initialiseFeatureWriter();
        }
        return featureWriter;
    }

    protected abstract void initialiseFeatureWriter();

    public void setFeatureWriter(PsiXmlElementWriter<F> featureWriter) {
        this.featureWriter = featureWriter;
    }

    @Override
    public void write(P object) throws MIIOException {
        try {
            // write start
            this.streamWriter.writeStartElement("interactorCandidate");
            int id = this.objectIndex.extractIdForParticipant(object);
            // write id attribute
            this.streamWriter.writeAttribute("id", Integer.toString(id));
            // write interactor
            writeInteractor(object);
            // write features
            writeFeatures(object);
            // write end participant
            this.streamWriter.writeEndElement();

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the participant candidate : "+object.toString(), e);
        }
    }

    protected void writeFeatures(P object) throws XMLStreamException {
        if (!object.getFeatures().isEmpty()){
            // write start feature list
            this.streamWriter.writeStartElement("featureList");
            for (Object feature : object.getFeatures()){
                getFeatureWriter().write((F) feature);
            }
            // write end featureList
            getStreamWriter().writeEndElement();
        }
    }

    protected void writeInteractor(P object) throws XMLStreamException {
        Interactor interactor = object.getInteractor();
        // write interactor ref or interactor
        writeMolecule(interactor);
    }

    protected abstract void writeMolecule(Interactor interactor) throws XMLStreamException ;

    protected void writeMoleculeRef(Interactor interactor) throws XMLStreamException {
        this.streamWriter.writeStartElement("interactorRef");
        this.streamWriter.writeCharacters(Integer.toString(this.objectIndex.extractIdForInteractor(interactor)));
        this.streamWriter.writeEndElement();
    }

    protected void writeMoleculeDescription(Interactor interactor) throws XMLStreamException {
        getInteractorWriter().write(interactor);
    }

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }

    protected PsiXmlObjectCache getObjectIndex() {
        return objectIndex;
    }
}
