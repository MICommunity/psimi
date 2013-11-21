package psidev.psi.mi.jami.xml.io.writer.expanded.extended;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.extension.ExperimentalInteractor;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.ExpandedXml25NamedModelledParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25ConfidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25ExperimentWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25ExperimentalPreparationWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25ExperimentalRoleWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25FeatureEvidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25HostOrganismWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25InferredInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25ParameterWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25ParticipantIdentificationMethodWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25PrimaryXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25SecondaryXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25SourceWriter;
import psidev.psi.mi.jami.xml.io.writer.expanded.AbstractExpandedXml25Writer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Expanded PSI-XML 2.5 writer for extended binary interaction evidences (full experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class ExpandedXml25BinaryEvidenceWriter extends AbstractExpandedXml25Writer<BinaryInteractionEvidence> {

    public ExpandedXml25BinaryEvidenceWriter() {
        super(BinaryInteractionEvidence.class);
    }

    public ExpandedXml25BinaryEvidenceWriter(File file) throws IOException, XMLStreamException {
        super(BinaryInteractionEvidence.class, file);
    }

    public ExpandedXml25BinaryEvidenceWriter(OutputStream output) throws XMLStreamException {
        super(BinaryInteractionEvidence.class, output);
    }

    public ExpandedXml25BinaryEvidenceWriter(Writer writer) throws XMLStreamException {
        super(BinaryInteractionEvidence.class, writer);
    }

    public ExpandedXml25BinaryEvidenceWriter(XMLStreamWriter streamWriter) {
        super(BinaryInteractionEvidence.class, streamWriter);
    }

    @Override
    protected Source extractSourceFromInteraction() {
        Experiment exp = getCurrentInteraction().getExperiment();
        if (exp != null && exp.getPublication() != null){
            return exp.getPublication().getSource();
        }
        return null;
    }

    @Override
    protected void initialiseSubWriters() {
        // basic sub writers
        PsiXml25ElementWriter<Alias> aliasWriter = new Xml25AliasWriter(getStreamWriter());
        PsiXml25ElementWriter<Annotation> attributeWriter = new Xml25AnnotationWriter(getStreamWriter());
        PsiXml25XrefWriter<Xref> primaryRefWriter = new Xml25PrimaryXrefWriter(getStreamWriter(), attributeWriter);
        PsiXml25XrefWriter<Xref> secondaryRefWriter = new Xml25SecondaryXrefWriter(getStreamWriter(), attributeWriter);
        PsiXml25PublicationWriter publicationWriter = new Xml25PublicationWriter(getStreamWriter(), primaryRefWriter, secondaryRefWriter, attributeWriter);
        PsiXml25ElementWriter<CvTerm> cellTypeWriter = new Xml25CelltypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXml25ElementWriter<CvTerm> tissueWriter = new Xml25TissueWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXml25ElementWriter<CvTerm> compartmentWriter = new Xml25CompartmentWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXml25ElementWriter<Organism> hostOrganismWriter = new Xml25HostOrganismWriter(getStreamWriter(), getElementCache(), aliasWriter, tissueWriter,
                compartmentWriter, cellTypeWriter);
        PsiXml25ElementWriter<Organism> nonExperimentalHostOrganismWriter = new psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25HostOrganismWriter(getStreamWriter(), aliasWriter, tissueWriter,
                compartmentWriter, cellTypeWriter);
        PsiXml25ElementWriter<CvTerm> detectionMethodWriter = new Xml25InteractionDetectionMethodWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<CvTerm> confidenceTypeWriter = new Xml25ConfidenceTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter, attributeWriter);
        PsiXml25ElementWriter<Confidence> confidenceWriter = new Xml25ConfidenceWriter(getStreamWriter(), getElementCache(), confidenceTypeWriter);
        PsiXml25ElementWriter<CvTerm> interactorTypeWriter = new Xml25InteractorTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<Organism> organismWriter = new Xml25OrganismWriter(getStreamWriter(), aliasWriter, tissueWriter,
                compartmentWriter, cellTypeWriter);
        PsiXml25ElementWriter<Checksum> checksumWriter = new Xml25ChecksumWriter(getStreamWriter());
        PsiXml25ElementWriter<Interactor> interactorWriter = new Xml25InteractorWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, interactorTypeWriter, organismWriter, attributeWriter,
                checksumWriter);
        PsiXml25ElementWriter<CvTerm> bioRoleWriter = new Xml25BiologicalRoleWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<CvTerm> featureTypeWriter = new Xml25FeatureTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<CvTerm> startStatusWriter = new Xml25StartStatusWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<CvTerm> endStatusWriter = new Xml25EndStatusWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<Position> beginWriter = new Xml25BeginPositionWriter(getStreamWriter(), startStatusWriter);
        PsiXml25ElementWriter<Position> endWriter = new Xml25EndPositionWriter(getStreamWriter(), endStatusWriter);
        PsiXml25ElementWriter<Range> rangeWriter = new Xml25RangeWriter(getStreamWriter(), beginWriter, endWriter);
        PsiXml25ElementWriter<CvTerm> featureDetectionWriter = new Xml25FeatureDetectionMethodWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<FeatureEvidence> featureWriter = new Xml25FeatureEvidenceWriter(getStreamWriter(), getElementCache(),
                primaryRefWriter, secondaryRefWriter, featureTypeWriter, attributeWriter,rangeWriter, featureDetectionWriter, aliasWriter);
        PsiXml25ElementWriter<CvTerm> expRoleWriter = new Xml25ExperimentalRoleWriter(getStreamWriter(), getElementCache(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<CvTerm> expPreparationWriter = new Xml25ExperimentalPreparationWriter(getStreamWriter(), getElementCache(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<CvTerm> identificationMethodWriter = new Xml25ParticipantIdentificationMethodWriter(getStreamWriter(), getElementCache(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ParameterWriter parameterWriter = new Xml25ParameterWriter(getStreamWriter(), getElementCache());
        ExpandedPsiXml25ElementWriter<ExperimentalInteractor> experimentalInteractorWriter = new ExpandedXml25ExperimentalInteractorWriter(getStreamWriter(), getElementCache());
        PsiXml25ParticipantWriter<ParticipantEvidence> participantWriter = new ExpandedXml25ParticipantEvidenceWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, bioRoleWriter, featureWriter, attributeWriter,
                interactorWriter, experimentalInteractorWriter, expPreparationWriter, identificationMethodWriter, confidenceWriter, expRoleWriter,
                hostOrganismWriter, parameterWriter);
        PsiXml25ElementWriter<CvTerm> interactionTypeWriter = new Xml25InteractionTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<InferredInteraction> inferredInteractionWriter = new Xml25InferredInteractionWriter(getStreamWriter(), getElementCache());
        PsiXml25ElementWriter<Experiment> experimentWriter = new Xml25ExperimentWriter(getStreamWriter(), getElementCache(), aliasWriter, publicationWriter,
                primaryRefWriter, secondaryRefWriter, nonExperimentalHostOrganismWriter, detectionMethodWriter,
                identificationMethodWriter, featureDetectionWriter, attributeWriter, confidenceWriter);
        PsiXml25ElementWriter<ModelledFeature> modelledFeatureWriter = new Xml25NamedModelledFeatureWriter(getStreamWriter(), getElementCache(),
                primaryRefWriter, secondaryRefWriter, featureTypeWriter, attributeWriter,rangeWriter, aliasWriter);
        PsiXml25ParticipantWriter<ModelledParticipant> modelledParticipantWriter = new ExpandedXml25NamedModelledParticipantWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, bioRoleWriter, modelledFeatureWriter, attributeWriter,
                interactorWriter);
        PsiXml25ElementWriter<String> availabilityWriter = new Xml25AvailabilityWriter(getStreamWriter(), getElementCache());
        // initialise source
        setSourceWriter(new Xml25SourceWriter(getStreamWriter(), aliasWriter, publicationWriter,
                attributeWriter, primaryRefWriter, secondaryRefWriter));
        // initialise interaction
        setInteractionWriter(new ExpandedXml25BinaryInteractionEvidenceWriter(getStreamWriter(), getElementCache(),
                primaryRefWriter, secondaryRefWriter, participantWriter, interactionTypeWriter,
                attributeWriter, experimentWriter, aliasWriter, inferredInteractionWriter, availabilityWriter, confidenceWriter, parameterWriter));
        // initialise complex
        setComplexWriter(new ExpandedXml25ModelledInteractionWriter(getStreamWriter(), getElementCache(),
                primaryRefWriter, secondaryRefWriter, modelledParticipantWriter, interactionTypeWriter,
                attributeWriter, aliasWriter, inferredInteractionWriter, experimentWriter, confidenceWriter, parameterWriter));
    }
}
