package psidev.psi.mi.jami.xml.io.writer.expanded;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.ExpandedXml25InteractionEvidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.ExpandedXml25ModelledInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.ExpandedXml25ModelledParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.ExpandedXml25ParticipantEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Set;

/**
 * Expanded PSI-XML 2.5 writer for interaction evidences (full experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class ExpandedXml25EvidenceWriter extends AbstractExpandedXml25Writer<InteractionEvidence>{

    public ExpandedXml25EvidenceWriter() {
        super(InteractionEvidence.class);
    }

    public ExpandedXml25EvidenceWriter(File file) throws IOException, XMLStreamException {
        super(InteractionEvidence.class, file);
    }

    public ExpandedXml25EvidenceWriter(OutputStream output) throws XMLStreamException {
        super(InteractionEvidence.class, output);
    }

    public ExpandedXml25EvidenceWriter(Writer writer) throws XMLStreamException {
        super(InteractionEvidence.class, writer);
    }

    public ExpandedXml25EvidenceWriter(XMLStreamWriter streamWriter) {
        super(InteractionEvidence.class, streamWriter);
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
        PsiXml25XrefWriter primaryRefWriter = new Xml25PrimaryXrefWriter(getStreamWriter());
        PsiXml25XrefWriter secondaryRefWriter = new Xml25SecondaryXrefWriter(getStreamWriter());
        PsiXml25PublicationWriter publicationWriter = new Xml25PublicationWriter(getStreamWriter(), primaryRefWriter, secondaryRefWriter, attributeWriter);
        PsiXml25ElementWriter<CvTerm> cellTypeWriter = new Xml25CelltypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXml25ElementWriter<CvTerm> tissueWriter = new Xml25TissueWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXml25ElementWriter<CvTerm> compartmentWriter = new Xml25CompartmentWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXml25ElementWriter<Organism> hostOrganismWriter = new Xml25HostOrganismWriter(getStreamWriter(), aliasWriter, tissueWriter,
                compartmentWriter, cellTypeWriter);
        PsiXml25ElementWriter<CvTerm> detectionMethodWriter = new Xml25InteractionDetectionMethodWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<CvTerm> confidenceTypeWriter = new Xml25ConfidenceTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter, attributeWriter);
        PsiXml25ElementWriter<Confidence> confidenceWriter = new Xml25ConfidenceWriter(getStreamWriter(), confidenceTypeWriter);
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
                primaryRefWriter, secondaryRefWriter, featureTypeWriter, featureDetectionWriter,rangeWriter, attributeWriter);
        PsiXml25ElementWriter<CvTerm> expRoleWriter = new Xml25ExperimentalRoleWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<CvTerm> expPreparationWriter = new Xml25ExperimentalPreparationWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<CvTerm> identificationMethodWriter = new Xml25ParticipantIdentificationMethodWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ParameterWriter parameterWriter = new Xml25ParameterWriter(getStreamWriter(), getElementCache());
        PsiXml25ParticipantWriter<ParticipantEvidence> participantWriter = new ExpandedXml25ParticipantEvidenceWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter,
                identificationMethodWriter, bioRoleWriter, expRoleWriter, expPreparationWriter,
                featureWriter, hostOrganismWriter, confidenceWriter, parameterWriter, attributeWriter);
        PsiXml25ElementWriter<CvTerm> interactionTypeWriter = new Xml25InteractionTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<Set<Feature>> inferredInteractionWriter = new Xml25InferredInteractionWriter(getStreamWriter(), getElementCache());
        PsiXml25ExperimentWriter experimentWriter = new Xml25ExperimentWriter(getStreamWriter(), getElementCache(), publicationWriter,
                primaryRefWriter, secondaryRefWriter, hostOrganismWriter, detectionMethodWriter,
                confidenceWriter, attributeWriter);
        PsiXml25ElementWriter<ModelledFeature> modelledFeatureWriter = new Xml25ModelledFeatureWriter(getStreamWriter(), getElementCache(),
                primaryRefWriter, secondaryRefWriter, featureTypeWriter,rangeWriter, attributeWriter);
        PsiXml25ParticipantWriter<ModelledParticipant> modelledParticipantWriter = new ExpandedXml25ModelledParticipantWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter,
                bioRoleWriter, modelledFeatureWriter, attributeWriter);
        PsiXml25ElementWriter<String> availabilityWriter = new Xml25AvailabilityWriter(getStreamWriter(), getElementCache());
        // initialise source
        setSourceWriter(new Xml25SourceWriter(getStreamWriter(), aliasWriter, publicationWriter,
                primaryRefWriter, secondaryRefWriter, attributeWriter));
        // initialise interaction
        setInteractionWriter(new ExpandedXml25InteractionEvidenceWriter(getStreamWriter(), getElementCache(),
                primaryRefWriter, secondaryRefWriter, availabilityWriter, experimentWriter,
                participantWriter, inferredInteractionWriter, interactionTypeWriter,
                confidenceWriter, parameterWriter, attributeWriter,
                checksumWriter));
        // initialise complex
        setComplexWriter(new ExpandedXml25ModelledInteractionWriter(getStreamWriter(), getElementCache(),
                primaryRefWriter, secondaryRefWriter, experimentWriter,
                modelledParticipantWriter, inferredInteractionWriter, interactionTypeWriter,
                confidenceWriter, parameterWriter, attributeWriter,
                checksumWriter));
    }
}

