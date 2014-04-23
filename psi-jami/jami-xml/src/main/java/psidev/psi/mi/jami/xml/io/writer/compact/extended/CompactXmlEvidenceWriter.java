package psidev.psi.mi.jami.xml.io.writer.compact.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlConfidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlExperimentWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlExperimentalPreparationWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlExperimentalRoleWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlFeatureEvidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlHostOrganismWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlInferredInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlParameterWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlParticipantIdentificationMethodWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlPrimaryXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlSecondaryXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlSourceWriter;
import psidev.psi.mi.jami.xml.model.extension.ExperimentalInteractor;
import psidev.psi.mi.jami.xml.model.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.model.extension.XmlSource;
import psidev.psi.mi.jami.xml.io.writer.compact.AbstractCompactXmlWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXmlNamedModelledParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.*;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Compact PSI-XML 2.5 writer for extended interaction evidences (full experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class CompactXmlEvidenceWriter extends AbstractCompactXmlWriter<InteractionEvidence> {

    public CompactXmlEvidenceWriter() {
        super(InteractionEvidence.class);
    }

    public CompactXmlEvidenceWriter(File file) throws IOException, XMLStreamException {
        super(InteractionEvidence.class, file);
    }

    public CompactXmlEvidenceWriter(OutputStream output) throws XMLStreamException {
        super(InteractionEvidence.class, output);
    }

    public CompactXmlEvidenceWriter(Writer writer) throws XMLStreamException {
        super(InteractionEvidence.class, writer);
    }

    public CompactXmlEvidenceWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(InteractionEvidence.class, streamWriter, cache);
    }

    @Override
    protected void registerAvailabilities(InteractionEvidence interaction) {
        if (interaction.getAvailability() != null){
            getAvailabilities().add(interaction.getAvailability());
        }
    }

    @Override
    protected void registerExperiment(InteractionEvidence interaction) {
        getExperiments().addAll(((PsiXmlExtendedInteractionWriter) getInteractionWriter()).extractDefaultExperimentsFrom(interaction));
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
        PsiXmlElementWriter<Alias> aliasWriter = new XmlAliasWriter(getStreamWriter());
        PsiXmlElementWriter<Annotation> attributeWriter = new XmlAnnotationWriter(getStreamWriter());
        PsiXmlXrefWriter primaryRefWriter = new XmlPrimaryXrefWriter(getStreamWriter(), attributeWriter);
        PsiXmlXrefWriter secondaryRefWriter = new XmlSecondaryXrefWriter(getStreamWriter(), attributeWriter);
        PsiXmlPublicationWriter publicationWriter = new XmlPublicationWriter(getStreamWriter(), primaryRefWriter, secondaryRefWriter, attributeWriter);
        PsiXmlElementWriter<CvTerm> cellTypeWriter = new XmlCelltypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXmlElementWriter<CvTerm> tissueWriter = new XmlTissueWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXmlElementWriter<CvTerm> compartmentWriter = new XmlCompartmentWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXmlElementWriter<Organism> hostOrganismWriter = new XmlHostOrganismWriter(getStreamWriter(), getElementCache(), aliasWriter, cellTypeWriter,
                compartmentWriter, tissueWriter);
        PsiXmlElementWriter<Organism> nonExperimentalHostOrganismWriter = new psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlHostOrganismWriter(getStreamWriter(), aliasWriter, cellTypeWriter,
                compartmentWriter, tissueWriter);
        PsiXmlElementWriter<CvTerm> detectionMethodWriter = new XmlInteractionDetectionMethodWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<CvTerm> confidenceTypeWriter = new XmlConfidenceTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter, attributeWriter);
        PsiXmlElementWriter<Confidence> confidenceWriter = new XmlConfidenceWriter(getStreamWriter(), getElementCache(), confidenceTypeWriter);
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
        PsiXmlElementWriter<CvTerm> featureDetectionWriter = new XmlFeatureDetectionMethodWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<FeatureEvidence> featureWriter = new XmlFeatureEvidenceWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, featureTypeWriter, featureDetectionWriter, rangeWriter, attributeWriter);
        PsiXmlElementWriter<CvTerm> expRoleWriter = new XmlExperimentalRoleWriter(getStreamWriter(), getElementCache(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<CvTerm> expPreparationWriter = new XmlExperimentalPreparationWriter(getStreamWriter(), getElementCache(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<CvTerm> identificationMethodWriter = new XmlParticipantIdentificationMethodWriter(getStreamWriter(), getElementCache(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlParameterWriter parameterWriter = new XmlParameterWriter(getStreamWriter(), getElementCache());
        CompactPsiXmlElementWriter<ExperimentalInteractor> experimentalInteractorWriter = new CompactXmlExperimentalInteractorWriter(getStreamWriter(), getElementCache());
        PsiXmlParticipantWriter<ParticipantEvidence> participantWriter = new CompactXmlParticipantEvidenceWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter,
                identificationMethodWriter, bioRoleWriter, expRoleWriter,
                expPreparationWriter, experimentalInteractorWriter, featureWriter,
                hostOrganismWriter, confidenceWriter, parameterWriter, attributeWriter);
        PsiXmlElementWriter<CvTerm> interactionTypeWriter = new XmlInteractionTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<InferredInteraction> inferredInteractionWriter = new XmlInferredInteractionWriter(getStreamWriter(), getElementCache());
        PsiXmlExperimentWriter experimentWriter = new XmlExperimentWriter(getStreamWriter(), getElementCache(), aliasWriter, publicationWriter,
                primaryRefWriter, secondaryRefWriter, nonExperimentalHostOrganismWriter, detectionMethodWriter,
                identificationMethodWriter, featureDetectionWriter, confidenceWriter, attributeWriter);
        PsiXmlElementWriter<ModelledFeature> modelledFeatureWriter = new XmlModelledFeatureWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, featureTypeWriter, rangeWriter, attributeWriter);
        PsiXmlParticipantWriter<ModelledParticipant> modelledParticipantWriter = new CompactXmlNamedModelledParticipantWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter, bioRoleWriter, modelledFeatureWriter, attributeWriter);
        PsiXmlElementWriter<String> availabilityWriter = new XmlAvailabilityWriter(getStreamWriter(), getElementCache());
        // initialise source
        setSourceWriter(new XmlSourceWriter(getStreamWriter(), aliasWriter, publicationWriter,
                primaryRefWriter, secondaryRefWriter, attributeWriter));
        // initialise experiment
        setExperimentWriter(experimentWriter);
        // initialise interactor
        setInteractorWriter(interactorWriter);
        // initialise availability writer
        setAvailabilityWriter(availabilityWriter);
        // initialise interaction
        setInteractionWriter(new CompactXmlInteractionEvidenceWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter,
                availabilityWriter, experimentWriter, participantWriter, inferredInteractionWriter, interactionTypeWriter,
                confidenceWriter, parameterWriter, attributeWriter,
                checksumWriter));
        // initialise complex
        setComplexWriter(new CompactXmlModelledInteractionWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter,
                experimentWriter, modelledParticipantWriter, inferredInteractionWriter, interactionTypeWriter,
                confidenceWriter, parameterWriter, attributeWriter,
                checksumWriter));
        setAnnotationsWriter(attributeWriter);
    }

    @Override
    protected void initialiseDefaultSource() {
        setDefaultSource(new XmlSource("Unknown source"));
    }
}

