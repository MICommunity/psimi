package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.CompactPsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlModelledFeatureWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlParticipantWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Compact XML 2.5 writer for a modelled participant. (ignore all experimental details)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class CompactXmlModelledParticipantWriter extends AbstractXmlParticipantWriter<ModelledParticipant, ModelledFeature> implements CompactPsiXmlElementWriter<ModelledParticipant> {
    public CompactXmlModelledParticipantWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex, new XmlModelledFeatureWriter(writer, objectIndex));
    }

    public CompactXmlModelledParticipantWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                               PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter,
                                               PsiXmlXrefWriter secondaryRefWriter, PsiXmlElementWriter<Interactor> interactorWriter,
                                               PsiXmlElementWriter<CvTerm> biologicalRoleWriter, PsiXmlElementWriter<ModelledFeature> featureWriter,
                                               PsiXmlElementWriter<Annotation> attributeWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter,interactorWriter,
                biologicalRoleWriter, featureWriter != null ? featureWriter : new XmlModelledFeatureWriter(writer, objectIndex), attributeWriter);
    }

    @Override
    protected void writeMolecule(Interactor interactor) throws XMLStreamException {
        super.writeMoleculeRef(interactor);
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
    protected void writeParticipantIdentificationMethods(ModelledParticipant object) {
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
