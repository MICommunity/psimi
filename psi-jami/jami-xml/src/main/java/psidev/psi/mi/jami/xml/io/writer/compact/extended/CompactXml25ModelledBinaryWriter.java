package psidev.psi.mi.jami.xml.io.writer.compact.extended;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.model.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.model.extension.XmlSource;
import psidev.psi.mi.jami.xml.io.writer.compact.AbstractCompactXml25Writer;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXmlNamedModelledParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25ConfidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25ExperimentWriter;
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
 * Compact PSI-XML 2.5 writer for modelled binary interactions (no experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class CompactXml25ModelledBinaryWriter extends AbstractCompactXml25Writer<ModelledBinaryInteraction>{

    public CompactXml25ModelledBinaryWriter() {
        super(ModelledBinaryInteraction.class);
    }

    public CompactXml25ModelledBinaryWriter(File file) throws IOException, XMLStreamException {
        super(ModelledBinaryInteraction.class, file);
    }

    public CompactXml25ModelledBinaryWriter(OutputStream output) throws XMLStreamException {
        super(ModelledBinaryInteraction.class, output);
    }

    public CompactXml25ModelledBinaryWriter(Writer writer) throws XMLStreamException {
        super(ModelledBinaryInteraction.class, writer);
    }

    public CompactXml25ModelledBinaryWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(ModelledBinaryInteraction.class, streamWriter, cache);
    }

    @Override
    protected void registerAvailabilities(ModelledBinaryInteraction interaction) {
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
    protected void registerExperiment(ModelledBinaryInteraction interaction) {
        getExperiments().add(getInteractionWriter().extractDefaultExperimentFrom(interaction));
    }

    @Override
    protected Source extractSourceFromInteraction() {
        return getCurrentInteraction().getSource() != null ? getCurrentInteraction().getSource() : super.extractSourceFromInteraction();
    }

    @Override
    protected void initialiseSubWriters() {
        // basic sub writers
        PsiXmlElementWriter<Alias> aliasWriter = new XmlAliasWriter(getStreamWriter());
        PsiXmlElementWriter<Annotation> attributeWriter = new XmlAnnotationWriter(getStreamWriter());
        PsiXmlXrefWriter primaryRefWriter = new Xml25PrimaryXrefWriter(getStreamWriter());
        PsiXmlXrefWriter secondaryRefWriter = new Xml25SecondaryXrefWriter(getStreamWriter());
        PsiXmlPublicationWriter publicationWriter = new XmlPublicationWriter(getStreamWriter(), primaryRefWriter, secondaryRefWriter, attributeWriter);
        PsiXmlElementWriter<CvTerm> cellTypeWriter = new XmlCelltypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXmlElementWriter<CvTerm> tissueWriter = new XmlTissueWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXmlElementWriter<CvTerm> compartmentWriter = new XmlCompartmentWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXmlElementWriter<Organism> nonExperimentalHostOrganismWriter = new XmlHostOrganismWriter(getStreamWriter(), aliasWriter, cellTypeWriter,
                compartmentWriter, tissueWriter);
        PsiXmlElementWriter<CvTerm> detectionMethodWriter = new XmlInteractionDetectionMethodWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<CvTerm> confidenceTypeWriter = new XmlConfidenceTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter, attributeWriter);
        PsiXmlElementWriter<Confidence> confidenceWriter = new Xml25ConfidenceWriter(getStreamWriter(), getElementCache(), confidenceTypeWriter);
        PsiXmlElementWriter<CvTerm> interactorTypeWriter = new XmlInteractorTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<Organism> organismWriter = new XmlOrganismWriter(getStreamWriter(), aliasWriter, tissueWriter,
                compartmentWriter, cellTypeWriter);
        PsiXmlElementWriter<Checksum> checksumWriter = new XmlChecksumWriter(getStreamWriter());
        PsiXmlElementWriter<Interactor> interactorWriter = new XmlInteractorWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, interactorTypeWriter, organismWriter, attributeWriter,
                checksumWriter);
        PsiXmlElementWriter<CvTerm> bioRoleWriter = new XmlBiologicalRoleWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<CvTerm> featureTypeWriter = new XmlFeatureTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<CvTerm> startStatusWriter = new XmlStartStatusWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<CvTerm> endStatusWriter = new XmlEndStatusWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<Position> beginWriter = new XmlBeginPositionWriter(getStreamWriter(), startStatusWriter);
        PsiXmlElementWriter<Position> endWriter = new XmlEndPositionWriter(getStreamWriter(), endStatusWriter);
        PsiXmlElementWriter<Range> rangeWriter = new XmlRangeWriter(getStreamWriter(), beginWriter, endWriter);
        PsiXmlElementWriter<CvTerm> interactionTypeWriter = new XmlInteractionTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<InferredInteraction> inferredInteractionWriter = new Xml25InferredInteractionWriter(getStreamWriter(), getElementCache());
        PsiXmlElementWriter<CvTerm> identificationMethodWriter = new Xml25ParticipantIdentificationMethodWriter(getStreamWriter(), getElementCache(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<CvTerm> featureDetectionWriter = new XmlFeatureDetectionMethodWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlExperimentWriter experimentWriter = new Xml25ExperimentWriter(getStreamWriter(), getElementCache(), aliasWriter, publicationWriter,
                primaryRefWriter, secondaryRefWriter, nonExperimentalHostOrganismWriter, detectionMethodWriter,
                identificationMethodWriter, featureDetectionWriter, confidenceWriter, attributeWriter);
        PsiXmlParameterWriter parameterWriter = new Xml25ParameterWriter(getStreamWriter(), getElementCache());
        PsiXmlElementWriter<ModelledFeature> modelledFeatureWriter = new XmlModelledFeatureWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, featureTypeWriter, rangeWriter, attributeWriter);
        PsiXmlParticipantWriter<ModelledParticipant> modelledParticipantWriter = new CompactXmlNamedModelledParticipantWriter(getStreamWriter(), getElementCache(),
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
        setComplexWriter(new CompactXml25ModelledInteractionWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, experimentWriter,
                modelledParticipantWriter, inferredInteractionWriter, interactionTypeWriter,
                confidenceWriter, parameterWriter, attributeWriter,
                checksumWriter));
        // initialise interaction
        setInteractionWriter(new CompactXml25ModelledBinaryInteractionWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, experimentWriter, modelledParticipantWriter,
                inferredInteractionWriter, interactionTypeWriter,
                confidenceWriter, parameterWriter, attributeWriter,
                checksumWriter));
        // use same default experiment
        getComplexWriter().setDefaultExperiment(getInteractionWriter().getDefaultExperiment());
        setAnnotationsWriter(attributeWriter);
    }

    @Override
    protected void initialiseDefaultSource() {
        setDefaultSource(new XmlSource("Unknown source"));
    }
}
