package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;
import java.util.Iterator;

/**
 * Xml 25 writer for participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public abstract class AbstractXml25ParticipantWriter<P extends Participant, F extends Feature> implements PsiXml25ElementWriter<P> {
    private XMLStreamWriter2 streamWriter;
    private PsiXml25ObjectCache objectIndex;
    private PsiXml25ElementWriter<Alias> aliasWriter;
    private PsiXml25XrefWriter primaryRefWriter;
    private PsiXml25XrefWriter secondaryRefWriter;
    private PsiXml25ElementWriter<CvTerm> biologicalRoleWriter;
    private PsiXml25ElementWriter<F> featureWriter;
    private PsiXml25ElementWriter<Annotation> attributeWriter;
    private PsiXml25ElementWriter<Interactor> interactorWriter;

    public AbstractXml25ParticipantWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex,
                                          PsiXml25ElementWriter<F> featureWriter){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXml25ParticipantWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the AbstractXml25ParticipantWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
        if (featureWriter == null){
            throw new IllegalArgumentException("The PsiXml feature writer is mandatory for the AbstractXml25ParticipantWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.featureWriter = featureWriter;
        this.aliasWriter = new Xml25AliasWriter(writer);
        this.primaryRefWriter = new Xml25PrimaryXrefWriter(writer);
        this.secondaryRefWriter = new Xml25SecondaryXrefWriter(writer);
        this.biologicalRoleWriter = new Xml25BiologicalRoleWriter(writer);
        this.attributeWriter = new Xml25AnnotationWriter(writer);
        this.interactorWriter = new Xml25InteractorWriter(writer, objectIndex);
    }

    public AbstractXml25ParticipantWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex,
                                             PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25XrefWriter primaryRefWriter,
                                             PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<CvTerm> biologicalRoleWriter,
                                             PsiXml25ElementWriter<F> featureWriter, PsiXml25ElementWriter<Annotation> attributeWriter,
                                             PsiXml25ElementWriter<Interactor> interactorWriter) {
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXml25ParticipantWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the AbstractXml25ParticipantWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
        if (featureWriter == null){
            throw new IllegalArgumentException("The PsiXml feature writer is mandatory for the AbstractXml25ParticipantWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.featureWriter = featureWriter;
        this.aliasWriter = aliasWriter != null ? aliasWriter : new Xml25AliasWriter(writer);
        this.primaryRefWriter = primaryRefWriter != null ? primaryRefWriter : new Xml25PrimaryXrefWriter(writer);
        this.secondaryRefWriter = secondaryRefWriter != null ? secondaryRefWriter : new Xml25SecondaryXrefWriter(writer);
        this.biologicalRoleWriter = biologicalRoleWriter != null ? biologicalRoleWriter : new Xml25BiologicalRoleWriter(writer);
        this.attributeWriter = attributeWriter != null ? attributeWriter : new Xml25AnnotationWriter(writer);
        this.interactorWriter = interactorWriter != null ? interactorWriter : new Xml25InteractorWriter(writer, objectIndex);
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

    protected void writeAttributes(P object) throws XMLStreamException {
        // write attributes
        Stoichiometry stc = object.getStoichiometry();
        if (!object.getAnnotations().isEmpty()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (Object ann : object.getAnnotations()){
                this.attributeWriter.write((Annotation)ann);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write stoichiometry attribute if not null
            if (stc != null){
                writeStoichiometryAttribute(stc);
            }
            // write end rattributeList
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
        // write stoichiometry attribute if not null
        else if (stc != null){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            writeStoichiometryAttribute(stc);
            // write end rattributeList
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
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
        getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    protected void writeFeatures(P object) throws XMLStreamException {
        if (!object.getFeatures().isEmpty()){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write start feature list
            this.streamWriter.writeStartElement("featureList");
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (Object feature : object.getFeatures()){
                this.featureWriter.write((F)feature);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end featureList
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected void writeNames(P object) throws XMLStreamException {
        boolean hasAliases = !object.getAliases().isEmpty();
        if (hasAliases){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            this.streamWriter.writeStartElement("names");
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write aliases
            for (Object alias : object.getAliases()){
                this.aliasWriter.write((Alias)alias);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end names
            this.streamWriter.writeEndElement();
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    protected void writeBiologicalRole(P object) throws XMLStreamException {
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        this.biologicalRoleWriter.write(object.getBiologicalRole());
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    protected void writeInteractor(P object) throws XMLStreamException {
        Interactor interactor = object.getInteractor();
        // write interaction ref
        if (interactor instanceof Complex){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            this.streamWriter.writeStartElement("interactionRef");
            int id = this.objectIndex.extractIdForInteractor(interactor);
            this.streamWriter.writeCharacters(Integer.toString(id));
            this.streamWriter.writeEndElement();
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);

            // register this complex in case it has not been written yet
            this.objectIndex.registerSubComplex((Complex)interactor);
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
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            writeXrefFromParticipantXrefs(object);
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
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

    protected void writeMoleculeRef(Interactor interactor) throws XMLStreamException {
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        this.streamWriter.writeStartElement("interactorRef");
        this.streamWriter.writeCharacters(Integer.toString(this.objectIndex.extractIdForInteractor(interactor)));
        this.streamWriter.writeEndElement();
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    protected void writeMoleculeDescription(Interactor interactor) throws XMLStreamException {
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        this.interactorWriter.write(interactor);
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    protected XMLStreamWriter2 getStreamWriter() {
        return streamWriter;
    }

    protected PsiXml25ObjectCache getObjectIndex() {
        return objectIndex;
    }

    protected PsiXml25ElementWriter<Alias> getAliasWriter() {
        return aliasWriter;
    }
}
