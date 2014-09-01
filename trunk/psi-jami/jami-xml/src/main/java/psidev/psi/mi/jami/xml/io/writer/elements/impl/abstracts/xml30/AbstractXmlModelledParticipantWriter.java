package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml30;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlCvTermWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlDbXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlInteractorWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlModelledFeatureWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlStoichiometryWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract class for XML 3.0 writer of modelled participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public abstract class AbstractXmlModelledParticipantWriter
        extends psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlModelledParticipantWriter {

    private PsiXmlElementWriter<Stoichiometry> stoichiometryWriter;

    public AbstractXmlModelledParticipantWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
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

    @Override
    protected void initialiseXrefWriter() {
        super.setXrefWriter(new XmlDbXrefWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseFeatureWriter() {
        super.setFeatureWriter(new XmlModelledFeatureWriter(getStreamWriter(), getObjectIndex()));
    }

    protected void writeStoichiometry(ModelledParticipant object){
        if (object.getStoichiometry() != null){
            getStoichiometryWriter().write(object.getStoichiometry());
        }
    }

    protected void writeOtherAttributes(ModelledParticipant object, boolean writeAttributeList) throws XMLStreamException {
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

    @Override
    protected void writeExperimentalPreparations(ModelledParticipant object) {
        // nothing to do
    }

    @Override
    protected void writeExperimentalRoles(ModelledParticipant object) {
        // nothing to do
    }

    @Override
    protected void writeParticipantIdentificationMethods(ModelledParticipant object, CvTerm method) {
        // nothing to do
    }

    @Override
    protected void writeExperimentalInteractor(ModelledParticipant object) {
        // nothing to do
    }

    @Override
    protected void writeHostOrganisms(ModelledParticipant object) {
        // nothing to do
    }

    @Override
    protected void writeConfidences(ModelledParticipant object) {
        // nothing to do
    }

    @Override
    protected void writeParameters(ModelledParticipant object) {
        // nothing to do
    }
}
