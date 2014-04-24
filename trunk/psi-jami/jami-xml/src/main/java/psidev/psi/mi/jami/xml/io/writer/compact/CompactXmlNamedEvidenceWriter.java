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
    protected PsiXmlInteractionWriter<ModelledInteraction> instantiateComplexWriter(PsiXmlElementWriter<Alias> aliasWriter,
                                                                                    PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                    PsiXmlXrefWriter primaryRefWriter,
                                                                                    PsiXmlXrefWriter secondaryRefWriter,
                                                                                    PsiXmlElementWriter<Confidence> confidenceWriter,
                                                                                    PsiXmlElementWriter<Checksum> checksumWriter,
                                                                                    PsiXmlParameterWriter parameterWriter,
                                                                                    PsiXmlCvTermWriter<CvTerm> interactionTypeWriter,
                                                                                    PsiXmlExperimentWriter experimentWriter,
                                                                                    PsiXmlParticipantWriter<ModelledParticipant> modelledParticipantWriter,
                                                                                    PsiXmlElementWriter inferredInteractionWriter,
                                                                                    PsiXmlInteractionWriter interactionWriter) {
        CompactXmlNamedModelledInteractionWriter complexWriter = new CompactXmlNamedModelledInteractionWriter(getStreamWriter(), getElementCache());
        complexWriter.setAttributeWriter(attributeWriter);
        complexWriter.setPrimaryRefWriter(primaryRefWriter);
        complexWriter.setSecondaryRefWriter(secondaryRefWriter);
        complexWriter.setConfidenceWriter(confidenceWriter);
        complexWriter.setChecksumWriter(checksumWriter);
        complexWriter.setParameterWriter(parameterWriter);
        complexWriter.setInteractionTypeWriter(interactionTypeWriter);
        complexWriter.setExperimentWriter(experimentWriter);
        complexWriter.setParticipantWriter(modelledParticipantWriter);
        complexWriter.setInferredInteractionWriter(inferredInteractionWriter);
        complexWriter.setAliasWriter(aliasWriter);
        return complexWriter;
    }

    @Override
    protected PsiXmlInteractionWriter<InteractionEvidence> instantiateInteractionWriter(PsiXmlElementWriter<Alias> aliasWriter,
                                                                                              PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                              PsiXmlXrefWriter primaryRefWriter,
                                                                                              PsiXmlXrefWriter secondaryRefWriter,
                                                                                              PsiXmlElementWriter<Confidence> confidenceWriter,
                                                                                              PsiXmlElementWriter<Checksum> checksumWriter,
                                                                                              PsiXmlParameterWriter parameterWriter,
                                                                                              PsiXmlParticipantWriter participantWriter,
                                                                                              PsiXmlCvTermWriter<CvTerm> interactionTypeWriter,
                                                                                              PsiXmlExperimentWriter experimentWriter,
                                                                                              PsiXmlElementWriter<String> availabilityWriter,
                                                                                              PsiXmlElementWriter inferredInteractionWriter) {
        CompactXmlNamedInteractionEvidenceWriter writer = new CompactXmlNamedInteractionEvidenceWriter(getStreamWriter(), getElementCache());
        writer.setAttributeWriter(attributeWriter);
        writer.setPrimaryRefWriter(primaryRefWriter);
        writer.setSecondaryRefWriter(secondaryRefWriter);
        writer.setConfidenceWriter(confidenceWriter);
        writer.setChecksumWriter(checksumWriter);
        writer.setParameterWriter(parameterWriter);
        writer.setInteractionTypeWriter(interactionTypeWriter);
        writer.setExperimentWriter(experimentWriter);
        writer.setParticipantWriter(participantWriter);
        writer.setInferredInteractionWriter(inferredInteractionWriter);
        writer.setAvailabilityWriter(availabilityWriter);
        writer.setAliasWriter(aliasWriter);
        return writer;
    }

    @Override
    protected PsiXmlParticipantWriter<ModelledParticipant> instantiateModelledParticipantWriter(PsiXmlElementWriter<Alias> aliasWriter,
                                                                                                PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                                PsiXmlXrefWriter primaryRefWriter,
                                                                                                PsiXmlXrefWriter secondaryRefWriter,
                                                                                                PsiXmlElementWriter<Interactor> interactorWriter,
                                                                                                PsiXmlCvTermWriter<CvTerm> bioRoleWriter,
                                                                                                PsiXmlElementWriter<ModelledFeature> modelledFeatureWriter,
                                                                                                PsiXmlParticipantWriter participantWriter) {
        CompactXmlNamedModelledParticipantWriter writer = new CompactXmlNamedModelledParticipantWriter(getStreamWriter(), getElementCache());
        writer.setPrimaryRefWriter(primaryRefWriter);
        writer.setSecondaryRefWriter(secondaryRefWriter);
        writer.setAliasWriter(aliasWriter);
        writer.setAttributeWriter(attributeWriter);
        writer.setInteractorWriter(interactorWriter);
        writer.setBiologicalRoleWriter(bioRoleWriter);
        writer.setFeatureWriter(modelledFeatureWriter);
        return writer;
    }

    @Override
    protected <P extends Participant> PsiXmlParticipantWriter<P> instantiateParticipantWriter(PsiXmlElementWriter<Alias> aliasWriter,
                                                                                                      PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                                      PsiXmlXrefWriter primaryRefWriter,
                                                                                                      PsiXmlXrefWriter secondaryRefWriter,
                                                                                                      PsiXmlElementWriter<Confidence> confidenceWriter,
                                                                                                      PsiXmlElementWriter<Interactor> interactorWriter,
                                                                                                      PsiXmlCvTermWriter<CvTerm> bioRoleWriter,
                                                                                                      PsiXmlElementWriter featureWriter,
                                                                                                      PsiXmlParameterWriter parameterWriter,
                                                                                                      PsiXmlElementWriter<Organism> organismWriter) {

        CompactXmlNamedParticipantEvidenceWriter writer = new CompactXmlNamedParticipantEvidenceWriter(getStreamWriter(), getElementCache());
        writer.setPrimaryRefWriter(primaryRefWriter);
        writer.setSecondaryRefWriter(secondaryRefWriter);
        writer.setAliasWriter(aliasWriter);
        writer.setAttributeWriter(attributeWriter);
        writer.setInteractorWriter(interactorWriter);
        writer.setBiologicalRoleWriter(bioRoleWriter);
        writer.setFeatureWriter(featureWriter);
        writer.setHostOrganismWriter(organismWriter);
        writer.setParameterWriter(parameterWriter);
        writer.setConfidenceWriter(confidenceWriter);
        writer.setCvWriter(bioRoleWriter);
        return (PsiXmlParticipantWriter<P>) writer;
    }

    @Override
    protected <F extends Feature> PsiXmlElementWriter<F> instantiateFeatureWriter(PsiXmlElementWriter<Alias> aliasWriter,
                                                                                  PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                  PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter,
                                                                                  PsiXmlCvTermWriter<CvTerm> featureTypeWriter,
                                                                                  PsiXmlElementWriter<Range> rangeWriter) {
        XmlFeatureEvidenceWriter writer = new XmlFeatureEvidenceWriter(getStreamWriter(), getElementCache());
        writer.setRangeWriter(rangeWriter);
        writer.setPrimaryRefWriter(primaryRefWriter);
        writer.setSecondaryRefWriter(secondaryRefWriter);
        writer.setFeatureTypeWriter(featureTypeWriter);
        writer.setAliasWriter(aliasWriter);
        writer.setAttributeWriter(attributeWriter);
        writer.setDetectionMethodWriter(featureTypeWriter);
        return (PsiXmlElementWriter<F>) writer;
    }

    @Override
    protected PsiXmlExperimentWriter instantiateExperimentWriter(PsiXmlElementWriter<Alias> aliasWriter, PsiXmlElementWriter<Annotation> attributeWriter,
                                                                 PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter,
                                                                 PsiXmlPublicationWriter publicationWriter,
                                                                 PsiXmlElementWriter<Organism> nonExperimentalHostOrganismWriter,
                                                                 PsiXmlCvTermWriter<CvTerm> detectionMethodWriter,
                                                                 PsiXmlElementWriter<Confidence> confidenceWriter) {
        XmlNamedExperimentWriter expWriter = new XmlNamedExperimentWriter(getStreamWriter(), getElementCache());
        expWriter.setPrimaryRefWriter(primaryRefWriter);
        expWriter.setSecondaryRefWriter(secondaryRefWriter);
        expWriter.setAttributeWriter(attributeWriter);
        expWriter.setPublicationWriter(publicationWriter);
        expWriter.setHostOrganismWriter(nonExperimentalHostOrganismWriter);
        expWriter.setDetectionMethodWriter(detectionMethodWriter);
        expWriter.setConfidenceWriter(confidenceWriter);
        expWriter.setAliasWriter(aliasWriter);
        return expWriter;
    }
}
