package psidev.psi.mi.jami.xml.io.writer.compact.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.model.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.model.extension.XmlSource;
import psidev.psi.mi.jami.xml.io.writer.compact.AbstractCompactXml25Writer;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXmlNamedModelledParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXmlNamedParticipantWriter;
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

    public LightCompactXml25Writer(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
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
        PsiXmlElementWriter<Confidence> confidenceWriter = new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25ConfidenceWriter(getStreamWriter(), getElementCache(), confidenceTypeWriter);
        PsiXmlElementWriter<CvTerm> interactorTypeWriter = new XmlInteractorTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<Organism> organismWriter = new XmlOrganismWriter(getStreamWriter(), aliasWriter, cellTypeWriter,
                compartmentWriter, tissueWriter);
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
        PsiXmlElementWriter<Feature> featureWriter = new XmlFeatureWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, featureTypeWriter, rangeWriter, attributeWriter);
        PsiXmlParticipantWriter<Participant> participantWriter = new CompactXmlNamedParticipantWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter,
                bioRoleWriter, featureWriter, attributeWriter);
        PsiXmlElementWriter<CvTerm> interactionTypeWriter = new XmlInteractionTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<InferredInteraction> inferredInteractionWriter = new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25InferredInteractionWriter(getStreamWriter(), getElementCache());

        PsiXmlElementWriter<CvTerm> identificationMethodWriter = new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25ParticipantIdentificationMethodWriter(getStreamWriter(), getElementCache(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<CvTerm> featureDetectionWriter = new XmlFeatureDetectionMethodWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlExperimentWriter experimentWriter = new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25ExperimentWriter(getStreamWriter(), getElementCache(), aliasWriter, publicationWriter,
                primaryRefWriter, secondaryRefWriter, nonExperimentalHostOrganismWriter, detectionMethodWriter,
                identificationMethodWriter, featureDetectionWriter, confidenceWriter, attributeWriter);
        PsiXmlParameterWriter parameterWriter = new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.Xml25ParameterWriter(getStreamWriter(), getElementCache());
        PsiXmlElementWriter<ModelledFeature> modelledFeatureWriter = new XmlModelledFeatureWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, featureTypeWriter, rangeWriter, attributeWriter);
        PsiXmlParticipantWriter<ModelledParticipant> modelledParticipantWriter = new CompactXmlNamedModelledParticipantWriter(getStreamWriter(), getElementCache(),
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
