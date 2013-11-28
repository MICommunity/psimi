package psidev.psi.mi.jami.xml.io.writer.compact;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXml25ModelledParticipantWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Set;

/**
 * Compact PSI-XML 2.5 writer for modelled interactions (no experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class CompactXml25ModelledWriter extends AbstractCompactXml25Writer<ModelledInteraction>{

    public CompactXml25ModelledWriter() {
        super(ModelledInteraction.class);
    }

    public CompactXml25ModelledWriter(File file) throws IOException, XMLStreamException {
        super(ModelledInteraction.class, file);
    }

    public CompactXml25ModelledWriter(OutputStream output) throws XMLStreamException {
        super(ModelledInteraction.class, output);
    }

    public CompactXml25ModelledWriter(Writer writer) throws XMLStreamException {
        super(ModelledInteraction.class, writer);
    }

    public CompactXml25ModelledWriter(XMLStreamWriter streamWriter, PsiXml25ObjectCache cache) {
        super(ModelledInteraction.class, streamWriter, cache);
    }

    @Override
    protected void registerAvailabilities(ModelledInteraction interaction) {
        // nothing to do
    }

    @Override
    protected void registerInteractionProperties() {
        super.registerInteractionProperties();
        for (CooperativeEffect effect : getCurrentInteraction().getCooperativeEffects()){
            for (ModelledInteraction interaction : effect.getAffectedInteractions()){
                registerAllInteractorsAndExperimentsFrom(interaction);
            }
        }
    }

    @Override
    protected void registerExperiment(ModelledInteraction interaction) {
        getExperiments().add(getInteractionWriter().extractDefaultExperimentFrom(interaction));
    }

    @Override
    protected Source extractSourceFromInteraction() {
        return getCurrentInteraction().getSource() != null ? getCurrentInteraction().getSource() : super.extractSourceFromInteraction();
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
        PsiXml25ElementWriter<Organism> hostOrganismWriter = new Xml25HostOrganismWriter(getStreamWriter(), aliasWriter, cellTypeWriter,
                compartmentWriter, tissueWriter);
        PsiXml25ElementWriter<CvTerm> detectionMethodWriter = new Xml25InteractionDetectionMethodWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<CvTerm> confidenceTypeWriter = new Xml25ConfidenceTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter, attributeWriter);
        PsiXml25ElementWriter<Confidence> confidenceWriter = new Xml25ConfidenceWriter(getStreamWriter(), confidenceTypeWriter);
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
        PsiXml25ElementWriter<CvTerm> interactionTypeWriter = new Xml25InteractionTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXml25ElementWriter<Set<Feature>> inferredInteractionWriter = new Xml25InferredInteractionWriter(getStreamWriter(), getElementCache());
        PsiXml25ExperimentWriter experimentWriter = new Xml25ExperimentWriter(getStreamWriter(), getElementCache(), publicationWriter,
                primaryRefWriter, secondaryRefWriter, hostOrganismWriter, detectionMethodWriter,
                confidenceWriter, attributeWriter);
        PsiXml25ParameterWriter parameterWriter = new Xml25ParameterWriter(getStreamWriter(), getElementCache());
        PsiXml25ElementWriter<ModelledFeature> modelledFeatureWriter = new Xml25ModelledFeatureWriter(getStreamWriter(), getElementCache(),
                primaryRefWriter, secondaryRefWriter, featureTypeWriter, rangeWriter, attributeWriter);
        PsiXml25ParticipantWriter<ModelledParticipant> modelledParticipantWriter = new CompactXml25ModelledParticipantWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter,
                bioRoleWriter, modelledFeatureWriter, attributeWriter);

        // initialise source
        setSourceWriter(new Xml25SourceWriter(getStreamWriter(), aliasWriter, publicationWriter,
                primaryRefWriter, secondaryRefWriter, attributeWriter));
        // initialise experiment
        setExperimentWriter(experimentWriter);
        // initialise interactor
        setInteractorWriter(interactorWriter);
        // initialise complex
        setComplexWriter(new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXml25ModelledInteractionWriter(getStreamWriter(), getElementCache(),
                primaryRefWriter, secondaryRefWriter, experimentWriter,
                modelledParticipantWriter, inferredInteractionWriter, interactionTypeWriter,
                confidenceWriter, parameterWriter, attributeWriter,
                checksumWriter));
        // initialise interaction
        setInteractionWriter(getComplexWriter());
        setAnnotationsWriter(attributeWriter);
    }
}
