package psidev.psi.mi.jami.xml.io.writer.expanded;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.*;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Expanded PSI-XML 2.5 writer for interaction evidences (full experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class ExpandedXmlEvidenceWriter extends AbstractExpandedXmlWriter<InteractionEvidence> {

    public ExpandedXmlEvidenceWriter() {
        super(InteractionEvidence.class);
    }

    public ExpandedXmlEvidenceWriter(File file) throws IOException, XMLStreamException {
        super(InteractionEvidence.class, file);
    }

    public ExpandedXmlEvidenceWriter(OutputStream output) throws XMLStreamException {
        super(InteractionEvidence.class, output);
    }

    public ExpandedXmlEvidenceWriter(Writer writer) throws XMLStreamException {
        super(InteractionEvidence.class, writer);
    }

    public ExpandedXmlEvidenceWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(InteractionEvidence.class, streamWriter, cache);
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
        ExpandedXmlModelledInteractionWriter complexWriter = new ExpandedXmlModelledInteractionWriter(getStreamWriter(), getElementCache());
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
        ExpandedXmlInteractionEvidenceWriter writer = new ExpandedXmlInteractionEvidenceWriter(getStreamWriter(), getElementCache());
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
        ExpandedXmlModelledParticipantWriter writer = new ExpandedXmlModelledParticipantWriter(getStreamWriter(), getElementCache());
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

        ExpandedXmlParticipantEvidenceWriter writer = new ExpandedXmlParticipantEvidenceWriter(getStreamWriter(), getElementCache());
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
        return (PsiXmlElementWriter<F>) writer;
    }
}

