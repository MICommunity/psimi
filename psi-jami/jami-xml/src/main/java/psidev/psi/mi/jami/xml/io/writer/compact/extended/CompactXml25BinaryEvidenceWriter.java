package psidev.psi.mi.jami.xml.io.writer.compact.extended;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.extension.ExperimentalInteractor;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.io.writer.compact.AbstractCompactXml25Writer;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXml25NamedModelledParticipantWriter;
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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Compact PSI-XML 2.5 writer for extended binary interaction evidences (full experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class CompactXml25BinaryEvidenceWriter extends AbstractCompactXml25Writer<BinaryInteractionEvidence>{

    public CompactXml25BinaryEvidenceWriter() {
        super(BinaryInteractionEvidence.class);
    }

    public CompactXml25BinaryEvidenceWriter(File file) throws IOException, XMLStreamException {
        super(BinaryInteractionEvidence.class, file);
    }

    public CompactXml25BinaryEvidenceWriter(OutputStream output) throws XMLStreamException {
        super(BinaryInteractionEvidence.class, output);
    }

    public CompactXml25BinaryEvidenceWriter(Writer writer) throws XMLStreamException {
        super(BinaryInteractionEvidence.class, writer);
    }

    public CompactXml25BinaryEvidenceWriter(XMLStreamWriter streamWriter) {
        super(BinaryInteractionEvidence.class, streamWriter);
    }

    @Override
    protected void registerAvailabilities(BinaryInteractionEvidence interaction) {
        if (interaction.getAvailability() != null){
            getAvailabilities().add(interaction.getAvailability());
        }
    }

    @Override
    protected void registerExperiment(BinaryInteractionEvidence interaction) {
        getExperiments().addAll(((PsiXml25ExtendedInteractionWriter) getInteractionWriter()).extractDefaultExperimentsFrom(interaction));
    }

    @Override
    protected Source extractSourceFromInteraction() {
        Experiment exp = getCurrentInteraction().getExperiment();
        if (exp != null && exp.getPublication() != null && exp.getPublication().getSource() != null){
            return exp.getPublication().getSource();
        }
        return super.extractSourceFromInteraction();
    }

    @Override
    protected void initialiseSubWriters() {
        // basic sub writers
        PsiXml25ElementWriter<Alias> aliasWriter = new Xml25AliasWriter(getStreamWriter());
        PsiXml25ElementWriter<Annotation> attributeWriter = new Xml25AnnotationWriter(getStreamWriter());
        PsiXml25XrefWriter primaryRefWriter = new Xml25PrimaryXrefWriter(getStreamWriter(), attributeWriter);
        PsiXml25XrefWriter secondaryRefWriter = new Xml25SecondaryXrefWriter(getStreamWriter(), attributeWriter);
        PsiXml25PublicationWriter publicationWriter = new Xml25PublicationWriter(getStreamWriter(), primaryRefWriter, secondaryRefWriter, attributeWriter);
        PsiXml25ElementWriter<CvTerm> cellTypeWriter = new Xml25CelltypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXml25ElementWriter<CvTerm> tissueWriter = new Xml25TissueWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXml25ElementWriter<CvTerm> compartmentWriter = new Xml25CompartmentWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXml25ElementWriter<Organism> hostOrganismWriter = new Xml25HostOrganismWriter(getStreamWriter(), getElementCache(), aliasWriter, cellTypeWriter,
                compartmentWriter, tissueWriter);
        PsiXml25ElementWriter<Organism> nonExperimentalHostOrganismWriter = new psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25HostOrganismWriter(getStreamWriter(), aliasWriter, cellTypeWriter,
                compartmentWriter, tissueWriter);
        PsiXml25ElementWriter<CvTerm> detectionMethodWriter = new Xml25InteractionDetectionMethodWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<CvTerm> confidenceTypeWriter = new Xml25ConfidenceTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter, attributeWriter);
        PsiXml25ElementWriter<Confidence> confidenceWriter = new Xml25ConfidenceWriter(getStreamWriter(), getElementCache(), confidenceTypeWriter);
        PsiXml25ElementWriter<CvTerm> interactorTypeWriter = new Xml25InteractorTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<Organism> organismWriter = new Xml25OrganismWriter(getStreamWriter(), aliasWriter, cellTypeWriter,
                compartmentWriter, tissueWriter);
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
        PsiXml25ElementWriter<FeatureEvidence> featureWriter = new Xml25FeatureEvidenceWriter(getStreamWriter(), getElementCache(), aliasWriter,
                primaryRefWriter, secondaryRefWriter, featureTypeWriter, featureDetectionWriter, rangeWriter, attributeWriter);
        PsiXml25ElementWriter<CvTerm> expRoleWriter = new Xml25ExperimentalRoleWriter(getStreamWriter(), getElementCache(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<CvTerm> expPreparationWriter = new Xml25ExperimentalPreparationWriter(getStreamWriter(), getElementCache(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<CvTerm> identificationMethodWriter = new Xml25ParticipantIdentificationMethodWriter(getStreamWriter(), getElementCache(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ParameterWriter parameterWriter = new Xml25ParameterWriter(getStreamWriter(), getElementCache());
        CompactPsiXml25ElementWriter<ExperimentalInteractor> experimentalInteractorWriter = new CompactXml25ExperimentalInteractorWriter(getStreamWriter(), getElementCache());
        PsiXml25ParticipantWriter<ParticipantEvidence> participantWriter = new CompactXml25ParticipantEvidenceWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter,
                identificationMethodWriter, bioRoleWriter, expRoleWriter,
                expPreparationWriter, experimentalInteractorWriter, featureWriter,
                hostOrganismWriter, confidenceWriter, parameterWriter, attributeWriter);
        PsiXml25ElementWriter<CvTerm> interactionTypeWriter = new Xml25InteractionTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<InferredInteraction> inferredInteractionWriter = new Xml25InferredInteractionWriter(getStreamWriter(), getElementCache());
        PsiXml25ExperimentWriter experimentWriter = new Xml25ExperimentWriter(getStreamWriter(), getElementCache(), aliasWriter,
                publicationWriter, primaryRefWriter, secondaryRefWriter,
                nonExperimentalHostOrganismWriter, detectionMethodWriter,
                identificationMethodWriter, featureDetectionWriter, confidenceWriter,
                attributeWriter);
        PsiXml25ElementWriter<ModelledFeature> modelledFeatureWriter = new Xml25NamedModelledFeatureWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, featureTypeWriter, rangeWriter, attributeWriter);
        PsiXml25ParticipantWriter<ModelledParticipant> modelledParticipantWriter = new CompactXml25NamedModelledParticipantWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter, bioRoleWriter, modelledFeatureWriter, attributeWriter);
        PsiXml25ElementWriter<String> availabilityWriter = new Xml25AvailabilityWriter(getStreamWriter(), getElementCache());
        // initialise source
        setSourceWriter(new Xml25SourceWriter(getStreamWriter(), aliasWriter, publicationWriter,
                primaryRefWriter, secondaryRefWriter, attributeWriter));
        // initialise experiment
        setExperimentWriter(experimentWriter);
        // initialise interactor
        setInteractorWriter(interactorWriter);
        // initialise availability writer
        setAvailabilityWriter(availabilityWriter);
        // initialise interaction
        setInteractionWriter(new CompactXml25BinaryInteractionEvidenceWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, availabilityWriter, experimentWriter, participantWriter,
                inferredInteractionWriter, interactionTypeWriter,
                confidenceWriter, parameterWriter, attributeWriter,
                checksumWriter));
        // initialise complex
        setComplexWriter(new CompactXml25ModelledInteractionWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter,
                experimentWriter, modelledParticipantWriter, inferredInteractionWriter, interactionTypeWriter,
                confidenceWriter, parameterWriter,attributeWriter,
                checksumWriter));
    }
}
