package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Iterator;

/**
 * Xml 25 writer for participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public abstract class AbstractXmlParticipantWriter<P extends Participant, F extends Feature> implements PsiXmlParticipantWriter<P> {
    private XMLStreamWriter streamWriter;
    private PsiXmlObjectCache objectIndex;
    private PsiXmlElementWriter<Alias> aliasWriter;
    private PsiXmlXrefWriter primaryRefWriter;
    private PsiXmlXrefWriter secondaryRefWriter;
    private PsiXmlElementWriter biologicalRoleWriter;
    private PsiXmlElementWriter<F> featureWriter;
    private PsiXmlElementWriter<Annotation> attributeWriter;
    private PsiXmlElementWriter<Interactor> interactorWriter;
    private boolean writeComplexAsInteractor=false;

    public AbstractXmlParticipantWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                        PsiXmlElementWriter<F> featureWriter){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXmlParticipantWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the AbstractXmlParticipantWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
        if (featureWriter == null){
            throw new IllegalArgumentException("The PsiXml feature writer is mandatory for the AbstractXmlParticipantWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.featureWriter = featureWriter;
        this.aliasWriter = new XmlAliasWriter(writer);
        this.primaryRefWriter = new XmlPrimaryXrefWriter(writer);
        this.secondaryRefWriter = new XmlSecondaryXrefWriter(writer);
        this.biologicalRoleWriter = new XmlBiologicalRoleWriter(writer);
        this.attributeWriter = new XmlAnnotationWriter(writer);
        this.interactorWriter = new XmlInteractorWriter(writer, objectIndex);
    }

    public AbstractXmlParticipantWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                        PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter,
                                        PsiXmlXrefWriter secondaryRefWriter, PsiXmlElementWriter<Interactor> interactorWriter,
                                        PsiXmlElementWriter biologicalRoleWriter, PsiXmlElementWriter<F> featureWriter,
                                        PsiXmlElementWriter<Annotation> attributeWriter) {
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXmlParticipantWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the AbstractXmlParticipantWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
        if (featureWriter == null){
            throw new IllegalArgumentException("The PsiXml feature writer is mandatory for the AbstractXmlParticipantWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.featureWriter = featureWriter;
        this.aliasWriter = aliasWriter != null ? aliasWriter : new XmlAliasWriter(writer);
        this.primaryRefWriter = primaryRefWriter != null ? primaryRefWriter : new XmlPrimaryXrefWriter(writer);
        this.secondaryRefWriter = secondaryRefWriter != null ? secondaryRefWriter : new XmlSecondaryXrefWriter(writer);
        this.biologicalRoleWriter = biologicalRoleWriter != null ? biologicalRoleWriter : new XmlBiologicalRoleWriter(writer);
        this.attributeWriter = attributeWriter != null ? attributeWriter : new XmlAnnotationWriter(writer);
        this.interactorWriter = interactorWriter != null ? interactorWriter : new XmlInteractorWriter(writer, objectIndex);
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

    protected void writeAttributes(P object) throws XMLStreamException {
        // write attributes
        Stoichiometry stc = object.getStoichiometry();
        if (!object.getAnnotations().isEmpty()){
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            for (Object ann : object.getAnnotations()){
                this.attributeWriter.write((Annotation)ann);
            }
            // write stoichiometry attribute if not null
            if (stc != null){
                writeStoichiometryAttribute(stc);
            }
            // write end rattributeList
            getStreamWriter().writeEndElement();
        }
        // write stoichiometry attribute if not null
        else if (stc != null){
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            writeStoichiometryAttribute(stc);
            // write end rattributeList
            getStreamWriter().writeEndElement();
        }
    }

    protected void writeStoichiometryAttribute(Stoichiometry stc) throws XMLStreamException {
        // write stoichiometry

        // write start
        this.streamWriter.writeStartElement("attribute");
        // write topic
        this.streamWriter.writeAttribute("name", Annotation.COMMENT);
        this.streamWriter.writeAttribute("nameAc", Annotation.COMMENT_MI);
        // write description
        this.streamWriter.writeCharacters(PsiXml25Utils.STOICHIOMETRY_PREFIX);
        this.streamWriter.writeCharacters(Long.toString(stc.getMinValue()));
        // stoichiometry range
        if (stc.getMaxValue() != stc.getMinValue()){
            this.streamWriter.writeCharacters(" - ");
            this.streamWriter.writeCharacters(Long.toString(stc.getMaxValue()));
        }
        // write end attribute
        this.streamWriter.writeEndElement();
    }

    protected void writeFeatures(P object) throws XMLStreamException {
        if (!object.getFeatures().isEmpty()){
            // write start feature list
            this.streamWriter.writeStartElement("featureList");
            for (Object feature : object.getFeatures()){
                this.featureWriter.write((F)feature);
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
                this.aliasWriter.write((Alias)alias);
            }
            // write end names
            this.streamWriter.writeEndElement();
        }
    }

    protected void writeBiologicalRole(P object) throws XMLStreamException {
        this.biologicalRoleWriter.write(object.getBiologicalRole());
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
        this.primaryRefWriter.setDefaultRefType(null);
        this.primaryRefWriter.setDefaultRefTypeAc(null);
        this.secondaryRefWriter.setDefaultRefType(null);
        this.secondaryRefWriter.setDefaultRefTypeAc(null);
        // write start xref
        this.streamWriter.writeStartElement("xref");

        int index = 0;
        while (refIterator.hasNext()){
            Xref ref = refIterator.next();
            // write primaryRef
            if (index == 0){
                this.primaryRefWriter.write(ref);
                index++;
            }
            // write secondaryref
            else{
                this.secondaryRefWriter.write(ref);
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
        this.interactorWriter.write(interactor);
    }

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }

    protected PsiXmlObjectCache getObjectIndex() {
        return objectIndex;
    }

    protected PsiXmlElementWriter<Alias> getAliasWriter() {
        return aliasWriter;
    }
}
