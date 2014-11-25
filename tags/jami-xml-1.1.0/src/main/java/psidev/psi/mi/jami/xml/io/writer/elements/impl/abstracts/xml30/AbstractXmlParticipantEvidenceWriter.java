package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml30;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlParameterWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlFeatureEvidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlStoichiometryWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract class for XML 3.0 writer of participant evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public abstract class AbstractXmlParticipantEvidenceWriter
        extends psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlParticipantEvidenceWriter {

    private PsiXmlElementWriter<Stoichiometry> stoichiometryWriter;
    private PsiXmlElementWriter<ExperimentalParticipantCandidate> participantCandidateWriter;

    public AbstractXmlParticipantEvidenceWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void initialiseXrefWriter() {
        super.setXrefWriter(new XmlDbXrefWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseFeatureWriter() {
        super.setFeatureWriter(new XmlFeatureEvidenceWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseConfidenceWriter() {
        super.setConfidenceWriter(new XmlConfidenceWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseHostOrganismWriter() {
         super.setHostOrganismWriter(new XmlHostOrganismWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseParameterWriter() {
        super.setParameterWriter(new XmlParameterWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseCvWriter() {
        super.setExperimentalCvWriter(new XmlCvTermWriter(getStreamWriter()));
    }

    public PsiXmlElementWriter<Stoichiometry> getStoichiometryWriter() {
        if (this.stoichiometryWriter == null){
            this.stoichiometryWriter = new XmlStoichiometryWriter(getStreamWriter());
        }
        return stoichiometryWriter;
    }

    public void setStoichiometryWriter(PsiXmlElementWriter<Stoichiometry> stoichiometryWriter) {
        this.stoichiometryWriter = stoichiometryWriter;
    }

    protected void writeStoichiometry(ParticipantEvidence object){
        if (object.getStoichiometry() != null){
            getStoichiometryWriter().write(object.getStoichiometry());
        }
    }

    protected void writeOtherAttributes(ParticipantEvidence object, boolean writeAttributeList) throws XMLStreamException {
        // nothing to do here
    }

    @Override
    protected void initialiseBiologicalRoleWriter() {
        super.setBiologicalRoleWriter(new XmlCvTermWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseInteractorWriter() {
        super.setInteractorWriter(new XmlInteractorWriter(getStreamWriter(), getObjectIndex()));
    }

    public PsiXmlElementWriter<ExperimentalParticipantCandidate> getParticipantCandidateWriter() {
        if (this.participantCandidateWriter == null){
            initialiseParticipantCandidateWriter();
        }
        return participantCandidateWriter;
    }

    protected abstract void initialiseParticipantCandidateWriter();

    public void setParticipantCandidateWriter(PsiXmlElementWriter<ExperimentalParticipantCandidate> participantCandidateWriter) {
        this.participantCandidateWriter = participantCandidateWriter;
    }

    @Override
    protected void writeParticipantPool(ParticipantPool pool) throws XMLStreamException {
        getStreamWriter().writeStartElement("interactorCandidateList");
        // write participant candidate type
        getBiologicalRoleWriter().write(pool.getType(), "moleculeSetType");
        // write candidates
        for (Object candidate : pool){
             getParticipantCandidateWriter().write((ExperimentalParticipantCandidate)candidate);
        }
        // end list
        getStreamWriter().writeEndElement();
    }
}
