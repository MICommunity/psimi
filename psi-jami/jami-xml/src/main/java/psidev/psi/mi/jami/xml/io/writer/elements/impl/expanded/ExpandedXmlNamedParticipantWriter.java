package psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.ExpandedPsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlFeatureWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlNamedParticipantWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Expanded Xml 2.5 writer for a basic named participant with a shortname and a fullname.
 * It ignores experimental details
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class ExpandedXmlNamedParticipantWriter extends AbstractXmlNamedParticipantWriter<Participant, Feature> implements ExpandedPsiXmlElementWriter<Participant> {
    public ExpandedXmlNamedParticipantWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex, new XmlFeatureWriter(writer, objectIndex));
    }

    public ExpandedXmlNamedParticipantWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                             PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter,
                                             PsiXmlXrefWriter secondaryRefWriter, PsiXmlElementWriter<Interactor> interactorWriter,
                                             PsiXmlElementWriter<CvTerm> biologicalRoleWriter, PsiXmlElementWriter<Feature> featureWriter,
                                             PsiXmlElementWriter<Annotation> attributeWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter, biologicalRoleWriter,
                featureWriter != null ? featureWriter : new XmlFeatureWriter(writer, objectIndex), attributeWriter);
    }

    @Override
    protected void writeMolecule(Interactor interactor) throws XMLStreamException {
        super.writeMoleculeDescription(interactor);
    }

    @Override
    protected void writeExperimentalPreparations(Participant object) {
        // nothing to do
    }

    @Override
    protected void writeExperimentalRoles(Participant object) {
        // nothing to do
    }

    @Override
    protected void writeParticipantIdentificationMethods(Participant object) {
        // nothing to do
    }

    @Override
    protected void writeExperimentalInteractor(Participant object) {
        // nothing to do
    }

    @Override
    protected void writeHostOrganisms(Participant object) {
        // nothing to do
    }

    @Override
    protected void writeConfidences(Participant object) {
        // nothing to do
    }

    @Override
    protected void writeParameters(Participant object) {
        // nothing to do
    }
}
