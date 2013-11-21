package psidev.psi.mi.jami.xml.io.writer.compact.extended;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.io.writer.compact.AbstractCompactXml25Writer;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXml25NamedModelledParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXml25NamedParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25ConfidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25ExperimentWriter;
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
 * Compact PSI-XML 2.5 writer for light binary interactions (no experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class LightCompactXml25BinaryWriter extends AbstractCompactXml25Writer<BinaryInteraction>{

    public LightCompactXml25BinaryWriter() {
        super(BinaryInteraction.class);
    }

    public LightCompactXml25BinaryWriter(File file) throws IOException, XMLStreamException {
        super(BinaryInteraction.class, file);
    }

    public LightCompactXml25BinaryWriter(OutputStream output) throws XMLStreamException {
        super(BinaryInteraction.class, output);
    }

    public LightCompactXml25BinaryWriter(Writer writer) throws XMLStreamException {
        super(BinaryInteraction.class, writer);
    }

    public LightCompactXml25BinaryWriter(XMLStreamWriter streamWriter) {
        super(BinaryInteraction.class, streamWriter);
    }

    @Override
    protected void registerAvailabilities(BinaryInteraction interaction) {
        // nothing to do
    }

    @Override
    protected void registerExperiment(BinaryInteraction interaction) {
        getExperiments().add(getInteractionWriter().extractDefaultExperimentFrom(interaction));
    }

    @Override
    protected Source extractSourceFromInteraction() {
        return null;
    }

    @Override
    protected void initialiseSubWriters() {
        // basic sub writers
        PsiXml25ElementWriter<Alias> aliasWriter = new Xml25AliasWriter(getStreamWriter());
        PsiXml25ElementWriter<Annotation> attributeWriter = new Xml25AnnotationWriter(getStreamWriter());
        PsiXml25XrefWriter<Xref> primaryRefWriter = new Xml25PrimaryXrefWriter(getStreamWriter());
        PsiXml25XrefWriter<Xref> secondaryRefWriter = new Xml25SecondaryXrefWriter(getStreamWriter());
        PsiXml25PublicationWriter publicationWriter = new Xml25PublicationWriter(getStreamWriter(), primaryRefWriter, secondaryRefWriter, attributeWriter);
        PsiXml25ElementWriter<CvTerm> cellTypeWriter = new Xml25CelltypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXml25ElementWriter<CvTerm> tissueWriter = new Xml25TissueWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXml25ElementWriter<CvTerm> compartmentWriter = new Xml25CompartmentWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXml25ElementWriter<Organism> hostOrganismWriter = new Xml25HostOrganismWriter(getStreamWriter(), getElementCache(), aliasWriter, tissueWriter,
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
        PsiXml25ElementWriter<Feature> featureWriter = new Xml25NamedFeatureWriter(getStreamWriter(), getElementCache(),
                primaryRefWriter, secondaryRefWriter, featureTypeWriter, attributeWriter,rangeWriter, aliasWriter);
        PsiXml25ParticipantWriter<Participant> participantWriter = new CompactXml25NamedParticipantWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, bioRoleWriter, featureWriter, attributeWriter,
                interactorWriter);
        PsiXml25ElementWriter<CvTerm> interactionTypeWriter = new Xml25InteractionTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<InferredInteraction> inferredInteractionWriter = new Xml25InferredInteractionWriter(getStreamWriter(), getElementCache());

        PsiXml25ElementWriter<CvTerm> identificationMethodWriter = new Xml25ParticipantIdentificationMethodWriter(getStreamWriter(), getElementCache(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<CvTerm> featureDetectionWriter = new Xml25FeatureDetectionMethodWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<Experiment> experimentWriter = new Xml25ExperimentWriter(getStreamWriter(), getElementCache(), aliasWriter, publicationWriter,
                primaryRefWriter, secondaryRefWriter, hostOrganismWriter, detectionMethodWriter,
                identificationMethodWriter, featureDetectionWriter, attributeWriter, confidenceWriter);
        PsiXml25ParameterWriter parameterWriter = new Xml25ParameterWriter(getStreamWriter(), getElementCache());
        PsiXml25ElementWriter<ModelledFeature> modelledFeatureWriter = new Xml25NamedModelledFeatureWriter(getStreamWriter(), getElementCache(),
                primaryRefWriter, secondaryRefWriter, featureTypeWriter, attributeWriter,rangeWriter, aliasWriter);
        PsiXml25ParticipantWriter<ModelledParticipant> modelledParticipantWriter = new CompactXml25NamedModelledParticipantWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, bioRoleWriter, modelledFeatureWriter, attributeWriter,
                interactorWriter);

        // initialise source
        setSourceWriter(new Xml25SourceWriter(getStreamWriter(), aliasWriter, publicationWriter,
                attributeWriter, primaryRefWriter, secondaryRefWriter));
        // initialise experiment
        setExperimentWriter(experimentWriter);
        // initialise interactor
        setInteractorWriter(interactorWriter);
        // initialise interaction
        setInteractionWriter(new CompactXml25BasicBinaryInteractionWriter(getStreamWriter(), getElementCache(),
                primaryRefWriter, secondaryRefWriter, participantWriter, interactionTypeWriter,
                attributeWriter, aliasWriter, inferredInteractionWriter, experimentWriter));
        // initialise complex
        setComplexWriter(new CompactXml25ModelledInteractionWriter(getStreamWriter(), getElementCache(),
                primaryRefWriter, secondaryRefWriter, modelledParticipantWriter, interactionTypeWriter,
                attributeWriter, aliasWriter, inferredInteractionWriter, experimentWriter, confidenceWriter, parameterWriter));
    }
}