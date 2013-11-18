package psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectIndex;
import psidev.psi.mi.jami.xml.io.writer.elements.ExpandedPsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25FeatureWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25ParticipantWriter;

import javax.xml.stream.XMLStreamException;

/**
 * Expanded XML 2.5 writer for a basic participant (ignore experimental details)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class ExpandedXml25ParticipantWriter extends AbstractXml25ParticipantWriter<Participant, Feature> implements ExpandedPsiXml25ElementWriter<Participant> {
    public ExpandedXml25ParticipantWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex) {
        super(writer, objectIndex, new Xml25FeatureWriter(writer, objectIndex));
    }

    public ExpandedXml25ParticipantWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex, PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<CvTerm> biologicalRoleWriter, PsiXml25ElementWriter<Feature> featureWriter, PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Interactor> interactorWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, biologicalRoleWriter, featureWriter, attributeWriter, interactorWriter);
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

