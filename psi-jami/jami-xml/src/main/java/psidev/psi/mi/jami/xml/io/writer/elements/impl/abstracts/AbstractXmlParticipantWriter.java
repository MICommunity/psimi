package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlVariableNameWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Iterator;

/**
 * Abstract Xml writer for participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public abstract class AbstractXmlParticipantWriter<P extends Participant, F extends Feature> implements PsiXmlParticipantWriter<P> {
    private XMLStreamWriter streamWriter;
    private PsiXmlObjectCache objectIndex;
    private PsiXmlElementWriter<Alias> aliasWriter;
    private PsiXmlXrefWriter xrefWriter;
    private PsiXmlVariableNameWriter<CvTerm> biologicalRoleWriter;
    private PsiXmlElementWriter<F> featureWriter;
    private PsiXmlElementWriter<Annotation> attributeWriter;
    private PsiXmlElementWriter<Interactor> interactorWriter;
    private boolean writeComplexAsInteractor=false;

    public AbstractXmlParticipantWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXmlParticipantWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the AbstractXmlParticipantWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
    }

    public PsiXmlElementWriter<Alias> getAliasWriter() {
        if (this.aliasWriter == null){
            this.aliasWriter =  new XmlAliasWriter(streamWriter);
        }
        return aliasWriter;
    }

    public void setAliasWriter(PsiXmlElementWriter<Alias> aliasWriter) {
        this.aliasWriter = aliasWriter;
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

    public PsiXmlVariableNameWriter<CvTerm> getBiologicalRoleWriter() {
        if (this.biologicalRoleWriter == null){
            this.biologicalRoleWriter = new XmlCvTermWriter(streamWriter);
        }
        return biologicalRoleWriter;
    }

    public void setBiologicalRoleWriter(PsiXmlVariableNameWriter<CvTerm> biologicalRoleWriter) {
        this.biologicalRoleWriter = biologicalRoleWriter;
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

    public PsiXmlElementWriter<Interactor> getInteractorWriter() {
        if (this.interactorWriter == null){
            this.interactorWriter = new XmlInteractorWriter(streamWriter, objectIndex);
        }
        return interactorWriter;
    }

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
            this.streamWriter.writeStartElement("participant");
            int id = this.objectIndex.extractIdForParticipant(object);
            // write id attribute
            this.streamWriter.writeAttribute("id", Integer.toString(id));
            // write names
            writeNames(object);
            // write Xref
            writeXref(object);
            // write interactor
            writeInteractor(object);
            // write participant identification methods
            writeParticipantIdentificationMethods(object);
            // write biological role
            writeBiologicalRole(object);
            // write experimental roles
            writeExperimentalRoles(object);
            // write experimental preparations
            writeExperimentalPreparations(object);
            // write experimental interactor
            writeExperimentalInteractor(object);
            // write features
            writeFeatures(object);
            // write host organism
            writeHostOrganisms(object);
            // write confidences
            writeConfidences(object);
            // write parameters
            writeParameters(object);
            // write stoichiometry
            writeStoichiometry(object);
            // write attributes
            writeAttributes(object);
            // write end participant
            this.streamWriter.writeEndElement();

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the participant : "+object.toString(), e);
        }
    }

    @Override
    public boolean writeComplexAsInteractor() {
        return this.writeComplexAsInteractor;
    }

    @Override
    public void setComplexAsInteractor(boolean complexAsInteractor) {
        this.writeComplexAsInteractor = complexAsInteractor;
    }
    protected abstract void writeStoichiometry(P object);

    protected abstract void writeOtherAttributes(P object, boolean writeAttributeList) throws XMLStreamException;

    protected void writeAttributes(P object) throws XMLStreamException {
        // write attributes
        Stoichiometry stc = object.getStoichiometry();
        if (!object.getAnnotations().isEmpty()){
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            for (Object ann : object.getAnnotations()){
                getAttributeWriter().write((Annotation)ann);
            }
            // write stoichiometry attribute if not null
            writeOtherAttributes(object, false);
            // write end attributeList
            getStreamWriter().writeEndElement();
        }
        // write stoichiometry attribute if not null
        else {
            writeOtherAttributes(object, true);
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

    protected void writeNames(P object) throws XMLStreamException {
        boolean hasAliases = !object.getAliases().isEmpty();
        if (hasAliases){
            this.streamWriter.writeStartElement("names");
            // write aliases
            for (Object alias : object.getAliases()){
                getAliasWriter().write((Alias)alias);
            }
            // write end names
            this.streamWriter.writeEndElement();
        }
    }

    protected void writeBiologicalRole(P object) throws XMLStreamException {
        getBiologicalRoleWriter().write(object.getBiologicalRole(),"biologicalRole");
    }

    protected void writeInteractor(P object) throws XMLStreamException {
        Interactor interactor = object.getInteractor();
        // write interaction ref
        if (!writeComplexAsInteractor && interactor instanceof Complex){
            Complex complex = (Complex)interactor;
            // write complex as an interactor if no participants as XML 25 interactions must have at least one participant
            if (complex.getParticipants().isEmpty()){
                writeMolecule(interactor);
            }
            else{
                this.streamWriter.writeStartElement("interactionRef");
                int id = this.objectIndex.extractIdForComplex(complex);
                this.streamWriter.writeCharacters(Integer.toString(id));
                this.streamWriter.writeEndElement();

                // register this complex in case it has not been written yet
                this.objectIndex.registerSubComplex((Complex)interactor);
            }
        }
        // write interactor ref or interactor
        else{
            writeMolecule(interactor);
        }
    }

    protected abstract void writeMolecule(Interactor interactor) throws XMLStreamException ;
    protected abstract void writeExperimentalPreparations(P object) throws XMLStreamException;
    protected abstract void writeExperimentalRoles(P object) throws XMLStreamException;
    protected abstract void writeParticipantIdentificationMethods(P object) throws XMLStreamException;
    protected abstract void writeExperimentalInteractor(P object) throws XMLStreamException;
    protected abstract void writeHostOrganisms(P object) throws XMLStreamException;
    protected abstract void writeConfidences(P object) throws XMLStreamException;
    protected abstract void writeParameters(P object) throws XMLStreamException;

    protected void writeXref(P object) throws XMLStreamException {
        if (!object.getXrefs().isEmpty()){
            writeXrefFromParticipantXrefs(object);
        }
    }

    protected void writeXrefFromParticipantXrefs(P object) throws XMLStreamException {
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
                getXrefWriter().write(ref,"primaryRef");
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
