package psidev.psi.mi.jami.xml.io.writer.compact.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.extension.XmlSource;
import psidev.psi.mi.jami.xml.io.writer.compact.AbstractCompactXml25Writer;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXml25NamedModelledParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXml25NamedParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.CompactXml25BasicInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.CompactXml25ModelledInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25PrimaryXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25SecondaryXrefWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Compact PSI-XML 2.5 writer for light interactions (no experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class LightCompactXml25Writer extends AbstractCompactXml25Writer<Interaction>{

    public LightCompactXml25Writer() {
        super(Interaction.class);
    }

    public LightCompactXml25Writer(File file) throws IOException, XMLStreamException {
        super(Interaction.class, file);
    }

    public LightCompactXml25Writer(OutputStream output) throws XMLStreamException {
        super(Interaction.class, output);
    }

    public LightCompactXml25Writer(Writer writer) throws XMLStreamException {
        super(Interaction.class, writer);
    }

    public LightCompactXml25Writer(XMLStreamWriter streamWriter, PsiXml25ObjectCache cache) {
        super(Interaction.class, streamWriter, cache);
    }

    @Override
    protected void registerAvailabilities(Interaction interaction) {
        // nothing to do
    }

    @Override
    protected void registerExperiment(Interaction interaction) {
        getExperiments().add(getInteractionWriter().extractDefaultExperimentFrom(interaction));
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
        PsiXml25ElementWriter<Organism> nonExperimentalHostOrganismWriter = new psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25HostOrganismWriter(getStreamWriter(), aliasWriter, cellTypeWriter,
                compartmentWriter, tissueWriter);
        PsiXml25ElementWriter<CvTerm> detectionMethodWriter = new Xml25InteractionDetectionMethodWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<CvTerm> confidenceTypeWriter = new Xml25ConfidenceTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter, attributeWriter);
        PsiXml25ElementWriter<Confidence> confidenceWriter = new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25ConfidenceWriter(getStreamWriter(), getElementCache(), confidenceTypeWriter);
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
        PsiXml25ElementWriter<Feature> featureWriter = new Xml25NamedFeatureWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, featureTypeWriter, rangeWriter, attributeWriter);
        PsiXml25ParticipantWriter<Participant> participantWriter = new CompactXml25NamedParticipantWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter,
                bioRoleWriter, featureWriter, attributeWriter);
        PsiXml25ElementWriter<CvTerm> interactionTypeWriter = new Xml25InteractionTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<InferredInteraction> inferredInteractionWriter = new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25InferredInteractionWriter(getStreamWriter(), getElementCache());

        PsiXml25ElementWriter<CvTerm> identificationMethodWriter = new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25ParticipantIdentificationMethodWriter(getStreamWriter(), getElementCache(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<CvTerm> featureDetectionWriter = new Xml25FeatureDetectionMethodWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ExperimentWriter experimentWriter = new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25ExperimentWriter(getStreamWriter(), getElementCache(), aliasWriter, publicationWriter,
                primaryRefWriter, secondaryRefWriter, nonExperimentalHostOrganismWriter, detectionMethodWriter,
                identificationMethodWriter, featureDetectionWriter, confidenceWriter, attributeWriter);
        PsiXml25ParameterWriter parameterWriter = new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25ParameterWriter(getStreamWriter(), getElementCache());
        PsiXml25ElementWriter<ModelledFeature> modelledFeatureWriter = new Xml25NamedModelledFeatureWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, featureTypeWriter, rangeWriter, attributeWriter);
        PsiXml25ParticipantWriter<ModelledParticipant> modelledParticipantWriter = new CompactXml25NamedModelledParticipantWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter,
                bioRoleWriter, modelledFeatureWriter, attributeWriter);

        // initialise source
        setSourceWriter(new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25SourceWriter(getStreamWriter(), aliasWriter, publicationWriter,
                primaryRefWriter, secondaryRefWriter, attributeWriter));
        // initialise experiment
        setExperimentWriter(experimentWriter);
        // initialise interactor
        setInteractorWriter(interactorWriter);
        // initialise interaction
        setInteractionWriter(new CompactXml25BasicInteractionWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, experimentWriter,
                participantWriter, inferredInteractionWriter, interactionTypeWriter,
                attributeWriter, checksumWriter));
        // initialise complex
        setComplexWriter(new CompactXml25ModelledInteractionWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, experimentWriter,
                modelledParticipantWriter, inferredInteractionWriter, interactionTypeWriter,
                confidenceWriter, parameterWriter, attributeWriter,
                checksumWriter));
        setAnnotationsWriter(attributeWriter);
    }

    @Override
    protected void initialiseDefaultSource() {
        setDefaultSource(new XmlSource("Unknown source"));
    }
}