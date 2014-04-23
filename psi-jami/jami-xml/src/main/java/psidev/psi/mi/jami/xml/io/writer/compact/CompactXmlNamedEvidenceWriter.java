package psidev.psi.mi.jami.xml.io.writer.compact;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXmlNamedInteractionEvidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXmlNamedModelledInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXmlNamedModelledParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXmlNamedParticipantEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Set;

/**
 * Compact PSI-XML 2.5 writer for named interaction evidences (full experimental evidences)
 * Participants, features and experiments also have extended names to write
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class CompactXmlNamedEvidenceWriter extends AbstractCompactXmlWriter<InteractionEvidence> {

    public CompactXmlNamedEvidenceWriter() {
        super(InteractionEvidence.class);
    }

    public CompactXmlNamedEvidenceWriter(File file) throws IOException, XMLStreamException {
        super(InteractionEvidence.class, file);
    }

    public CompactXmlNamedEvidenceWriter(OutputStream output) throws XMLStreamException {
        super(InteractionEvidence.class, output);
    }

    public CompactXmlNamedEvidenceWriter(Writer writer) throws XMLStreamException {
        super(InteractionEvidence.class, writer);
    }

    public CompactXmlNamedEvidenceWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
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
        getExperiments().add(getInteractionWriter().extractDefaultExperimentFrom(interaction));
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
        PsiXmlXrefWriter primaryRefWriter = new XmlPrimaryXrefWriter(getStreamWriter());
        PsiXmlXrefWriter secondaryRefWriter = new XmlSecondaryXrefWriter(getStreamWriter());
        PsiXmlPublicationWriter publicationWriter = new XmlPublicationWriter(getStreamWriter(), primaryRefWriter, secondaryRefWriter, attributeWriter);
        PsiXmlElementWriter<CvTerm> cellTypeWriter = new XmlCelltypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXmlElementWriter<CvTerm> tissueWriter = new XmlTissueWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXmlElementWriter<CvTerm> compartmentWriter = new XmlCompartmentWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter,
                attributeWriter);
        PsiXmlElementWriter<Organism> hostOrganismWriter = new XmlHostOrganismWriter(getStreamWriter(), aliasWriter, cellTypeWriter,
                compartmentWriter, tissueWriter);
        PsiXmlElementWriter<CvTerm> detectionMethodWriter = new XmlInteractionDetectionMethodWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<CvTerm> confidenceTypeWriter = new XmlConfidenceTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter, attributeWriter);
        PsiXmlElementWriter<Confidence> confidenceWriter = new XmlConfidenceWriter(getStreamWriter(), confidenceTypeWriter);
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
                aliasWriter, primaryRefWriter, secondaryRefWriter, featureTypeWriter, featureDetectionWriter,rangeWriter, attributeWriter);
        PsiXmlElementWriter<CvTerm> expRoleWriter = new XmlExperimentalRoleWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<CvTerm> expPreparationWriter = new XmlExperimentalPreparationWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<CvTerm> identificationMethodWriter = new XmlParticipantIdentificationMethodWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlParameterWriter parameterWriter = new XmlParameterWriter(getStreamWriter(), getElementCache());
        PsiXmlParticipantWriter<ParticipantEvidence> participantWriter = new CompactXmlNamedParticipantEvidenceWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter, identificationMethodWriter,
                bioRoleWriter, expRoleWriter, expPreparationWriter, featureWriter, hostOrganismWriter,
                confidenceWriter, parameterWriter, attributeWriter);
        PsiXmlElementWriter<CvTerm> interactionTypeWriter = new XmlInteractionTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<Set<Feature>> inferredInteractionWriter = new XmlInferredInteractionWriter(getStreamWriter(), getElementCache());
        PsiXmlExperimentWriter experimentWriter = new XmlExperimentWriter(getStreamWriter(), getElementCache(), publicationWriter,
                primaryRefWriter, secondaryRefWriter, hostOrganismWriter, detectionMethodWriter,
                confidenceWriter, attributeWriter);
        PsiXmlElementWriter<ModelledFeature> modelledFeatureWriter = new XmlModelledFeatureWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, featureTypeWriter, rangeWriter,attributeWriter);
        PsiXmlParticipantWriter<ModelledParticipant> modelledParticipantWriter = new CompactXmlNamedModelledParticipantWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter,
                bioRoleWriter, modelledFeatureWriter, attributeWriter);
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
        setInteractionWriter(new CompactXmlNamedInteractionEvidenceWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, availabilityWriter,
                experimentWriter, participantWriter, inferredInteractionWriter, interactionTypeWriter,
                confidenceWriter, parameterWriter, attributeWriter,
                checksumWriter));
        // initialise complex
        setComplexWriter(new CompactXmlNamedModelledInteractionWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter,
                experimentWriter, modelledParticipantWriter, inferredInteractionWriter, interactionTypeWriter,
                confidenceWriter, parameterWriter, attributeWriter,
                checksumWriter));
        setAnnotationsWriter(attributeWriter);
    }
}
