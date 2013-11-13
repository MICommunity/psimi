package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectIndex;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Participant;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25ParticipantEvidence;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
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

public abstract class AbstractXml25ParticipantWriter<P extends Participant> implements PsiXml25ElementWriter<P> {
    private XMLStreamWriter2 streamWriter;
    private PsiXml25ObjectIndex objectIndex;
    private PsiXml25ElementWriter<Alias> aliasWriter;
    private PsiXml25XrefWriter primaryRefWriter;
    private PsiXml25XrefWriter secondaryRefWriter;
    private PsiXml25ElementWriter<CvTerm> biologicalRoleWriter;

    public AbstractXml25ParticipantWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXml25ParticipantWriter");
        }
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the AbstractXml25ParticipantWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
        this.streamWriter = writer;
    }

    @Override
    public void write(P object) throws MIIOException {
        try {
            // write start
            this.streamWriter.writeStartElement("participant");
            int id = this.objectIndex.extractIdFor(object);
            // write id attribute
            this.streamWriter.writeAttribute("id", Integer.toString(id));
            boolean hasAliases = !object.getAliases().isEmpty();

            // write extended participant
            if (object instanceof ExtendedPsi25Participant){
                ExtendedPsi25Participant xmlParticipant = (ExtendedPsi25Participant) object;
                writeExtendedParticipant(object, hasAliases, xmlParticipant);
            }
            // write normal interaction
            else{
                // write names
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
                // write features
                // write host organism
                // write confidences
                // write parameters
                // write attributes
            }

            // write end participant
            this.streamWriter.writeEndElement();

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the participant : "+object.toString(), e);
        }
    }

    protected abstract void writeExperimentalPreparations(P object);

    protected abstract void writeExperimentalRoles(P object);
    protected abstract void writeExtendedExperimentalRoles(ExtendedPsi25ParticipantEvidence object);

    protected void writeBiologicalRole(P object) throws XMLStreamException {
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        this.biologicalRoleWriter.write(object.getBiologicalRole());
        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
    }

    protected abstract void writeParticipantIdentificationMethods(P object) throws XMLStreamException;
    protected abstract void writeExtendedParticipantIdentificationMethods(ExtendedPsi25ParticipantEvidence object) throws XMLStreamException;

    protected void writeInteractor(P object) throws XMLStreamException {
        Interactor interactor = object.getInteractor();
        // write interaction ref
        if (interactor instanceof Complex){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            this.streamWriter.writeStartElement("interactionRef");
            int id = this.objectIndex.extractIdFor(interactor);
            this.streamWriter.writeCharacters(Integer.toString(id));
            this.streamWriter.writeEndElement();
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
        // write interactor ref or interactor
        else{
            writeMolecule(interactor);
        }
    }

    protected abstract void writeMolecule(Interactor interactor) throws XMLStreamException ;

    protected void writeExtendedParticipant(P object, boolean hasAliases, ExtendedPsi25Participant xmlParticipant) throws XMLStreamException {
        // write names
        boolean hasFullLabel = xmlParticipant.getFullName() != null;
        boolean hasShortLabel = xmlParticipant.getShortName() != null;
        if (hasShortLabel || hasFullLabel || hasAliases){
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            this.streamWriter.writeStartElement("names");
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write shortname
            if (hasShortLabel){
                this.streamWriter.writeStartElement("shortLabel");
                this.streamWriter.writeCharacters(xmlParticipant.getShortName());
                this.streamWriter.writeEndElement();
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write fullname
            if (hasFullLabel){
                this.streamWriter.writeStartElement("fullName");
                this.streamWriter.writeCharacters(xmlParticipant.getFullName());
                this.streamWriter.writeEndElement();
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write aliases
            for (Object alias : object.getAliases()){
                this.aliasWriter.write((Alias)alias);
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end names
            this.streamWriter.writeEndElement();
            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);

            // write Xref
            writeXref(object);
            // write interactor
            writeInteractor(object);

            // check if another expanded
            if (xmlParticipant instanceof ExtendedPsi25ParticipantEvidence){
                ExtendedPsi25ParticipantEvidence xmlParticipantEvidence = (ExtendedPsi25ParticipantEvidence) xmlParticipant;
                // write participant identification methods
                writeExtendedParticipantIdentificationMethods(xmlParticipantEvidence);
                // write biological role
                writeBiologicalRole(object);
                // write experimental roles
                writeExtendedExperimentalRoles(xmlParticipantEvidence);
                // write experimental preparations
                writeExperimentalPreparations(object);
                // write experimental interactor
                writeExperimentalInteractor(xmlParticipantEvidence);
            }
            else{
                // write participant identification methods
                writeParticipantIdentificationMethods(object);
                // write biological role
                writeBiologicalRole(object);
                // write experimental roles
                writeExperimentalRoles(object);
                // write experimental preparations
                writeExperimentalPreparations(object);
            }
        }
    }

    protected abstract void writeExperimentalInteractor(ExtendedPsi25ParticipantEvidence xmlParticipantEvidence);

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

    protected XMLStreamWriter2 getStreamWriter() {
        return streamWriter;
    }
}
