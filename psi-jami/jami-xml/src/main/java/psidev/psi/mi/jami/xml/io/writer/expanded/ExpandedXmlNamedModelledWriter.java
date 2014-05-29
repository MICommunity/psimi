package psidev.psi.mi.jami.xml.io.writer.expanded;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlNamedModelledInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlNamedModelledParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlModelledFeatureWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlNamedExperimentWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Expanded PSI-XML 2.5 writer for named modelled interactions (no experimental evidences).
 * Participants, features, experiments also have expanded names
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class ExpandedXmlNamedModelledWriter extends AbstractExpandedXmlWriter<ModelledInteraction> {

    public ExpandedXmlNamedModelledWriter() {
        super(ModelledInteraction.class);
    }

    public ExpandedXmlNamedModelledWriter(File file) throws IOException, XMLStreamException {
        super(ModelledInteraction.class, file);
    }

    public ExpandedXmlNamedModelledWriter(OutputStream output) throws XMLStreamException {
        super(ModelledInteraction.class, output);
    }

    public ExpandedXmlNamedModelledWriter(Writer writer) throws XMLStreamException {
        super(ModelledInteraction.class, writer);
    }

    public ExpandedXmlNamedModelledWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(ModelledInteraction.class, streamWriter, cache);
    }


    @Override
    protected Source extractSourceFromInteraction() {
        return getCurrentInteraction().getSource() != null ? getCurrentInteraction().getSource() : super.extractSourceFromInteraction();
    }

    @Override
    protected PsiXmlInteractionWriter<ModelledInteraction> instantiateComplexWriter(PsiXmlElementWriter<Alias> aliasWriter,
                                                                                    PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                    PsiXmlXrefWriter primaryRefWriter,
                                                                                    PsiXmlElementWriter<Confidence> confidenceWriter,
                                                                                    PsiXmlElementWriter<Checksum> checksumWriter,
                                                                                    PsiXmlParameterWriter parameterWriter,
                                                                                    PsiXmlVariableNameWriter<CvTerm> interactionTypeWriter,
                                                                                    PsiXmlExperimentWriter experimentWriter,
                                                                                    PsiXmlParticipantWriter<ModelledParticipant> modelledParticipantWriter,
                                                                                    PsiXmlElementWriter inferredInteractionWriter,
                                                                                    PsiXmlInteractionWriter interactionWriter) {
        return interactionWriter;
    }

    @Override
    protected PsiXmlInteractionWriter<ModelledInteraction> instantiateInteractionWriter(PsiXmlElementWriter<Alias> aliasWriter,
                                                                                        PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                        PsiXmlXrefWriter primaryRefWriter,
                                                                                        PsiXmlElementWriter<Confidence> confidenceWriter,
                                                                                        PsiXmlElementWriter<Checksum> checksumWriter,
                                                                                        PsiXmlParameterWriter parameterWriter,
                                                                                        PsiXmlParticipantWriter participantWriter,
                                                                                        PsiXmlVariableNameWriter<CvTerm> interactionTypeWriter,
                                                                                        PsiXmlExperimentWriter experimentWriter,
                                                                                        PsiXmlElementWriter<String> availabilityWriter,
                                                                                        PsiXmlElementWriter inferredInteractionWriter) {
        XmlNamedModelledInteractionWriter writer = new XmlNamedModelledInteractionWriter(getStreamWriter(), getElementCache());
        writer.setAttributeWriter(attributeWriter);
        writer.setXrefWriter(primaryRefWriter);
        writer.setConfidenceWriter(confidenceWriter);
        writer.setChecksumWriter(checksumWriter);
        writer.setParameterWriter(parameterWriter);
        writer.setInteractionTypeWriter(interactionTypeWriter);
        writer.setExperimentWriter(experimentWriter);
        writer.setParticipantWriter(participantWriter);
        writer.setInferredInteractionWriter(inferredInteractionWriter);
        writer.setAliasWriter(aliasWriter);
        return writer;
    }

    @Override
    protected PsiXmlParticipantWriter<ModelledParticipant> instantiateModelledParticipantWriter(PsiXmlElementWriter<Alias> aliasWriter,
                                                                                                PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                                PsiXmlXrefWriter primaryRefWriter,
                                                                                                PsiXmlElementWriter<Interactor> interactorWriter,
                                                                                                PsiXmlVariableNameWriter<CvTerm> bioRoleWriter,
                                                                                                PsiXmlElementWriter<ModelledFeature> modelledFeatureWriter,
                                                                                                PsiXmlParticipantWriter participantWriter) {
        return participantWriter;
    }

    @Override
    protected <P extends Participant> PsiXmlParticipantWriter<P> instantiateParticipantWriter(PsiXmlElementWriter<Alias> aliasWriter,
                                                                                              PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                              PsiXmlXrefWriter primaryRefWriter,
                                                                                              PsiXmlElementWriter<Confidence> confidenceWriter,
                                                                                              PsiXmlElementWriter<Interactor> interactorWriter,
                                                                                              PsiXmlVariableNameWriter<CvTerm> bioRoleWriter,
                                                                                              PsiXmlElementWriter featureWriter,
                                                                                              PsiXmlParameterWriter parameterWriter,
                                                                                              PsiXmlElementWriter<Organism> organismWriter) {

        XmlNamedModelledParticipantWriter writer = new XmlNamedModelledParticipantWriter(getStreamWriter(), getElementCache());
        writer.setXrefWriter(primaryRefWriter);
        writer.setAliasWriter(aliasWriter);
        writer.setAttributeWriter(attributeWriter);
        writer.setInteractorWriter(interactorWriter);
        writer.setBiologicalRoleWriter(bioRoleWriter);
        writer.setFeatureWriter(featureWriter);
        return (PsiXmlParticipantWriter<P>) writer;
    }

    @Override
    protected <F extends Feature> PsiXmlElementWriter<F> instantiateFeatureWriter(PsiXmlElementWriter<Alias> aliasWriter,
                                                                                  PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                  PsiXmlXrefWriter primaryRefWriter,
                                                                                  PsiXmlVariableNameWriter<CvTerm> featureTypeWriter,
                                                                                  PsiXmlElementWriter<Range> rangeWriter) {
        XmlModelledFeatureWriter writer = new XmlModelledFeatureWriter(getStreamWriter(), getElementCache());
        writer.setRangeWriter(rangeWriter);
        writer.setXrefWriter(primaryRefWriter);
        writer.setFeatureTypeWriter(featureTypeWriter);
        writer.setAliasWriter(aliasWriter);
        writer.setAttributeWriter(attributeWriter);
        return (PsiXmlElementWriter<F>) writer;
    }

    @Override
    protected PsiXmlElementWriter<ModelledFeature> instantiateModelledFeatureWriter(PsiXmlElementWriter<Alias> aliasWriter,
                                                                                    PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                    PsiXmlXrefWriter primaryRefWriter,
                                                                                    PsiXmlVariableNameWriter<CvTerm> featureTypeWriter,
                                                                                    PsiXmlElementWriter<Range> rangeWriter,
                                                                                    PsiXmlElementWriter featureWriter) {
        return featureWriter;
    }

    @Override
    protected PsiXmlExperimentWriter instantiateExperimentWriter(PsiXmlElementWriter<Alias> aliasWriter, PsiXmlElementWriter<Annotation> attributeWriter,
                                                                 PsiXmlXrefWriter primaryRefWriter,
                                                                 PsiXmlPublicationWriter publicationWriter,
                                                                 PsiXmlElementWriter<Organism> nonExperimentalHostOrganismWriter,
                                                                 PsiXmlVariableNameWriter<CvTerm> detectionMethodWriter,
                                                                 PsiXmlElementWriter<Confidence> confidenceWriter) {
        XmlNamedExperimentWriter expWriter = new XmlNamedExperimentWriter(getStreamWriter(), getElementCache());
        expWriter.setXrefWriter(primaryRefWriter);
        expWriter.setAttributeWriter(attributeWriter);
        expWriter.setPublicationWriter(publicationWriter);
        expWriter.setHostOrganismWriter(nonExperimentalHostOrganismWriter);
        expWriter.setDetectionMethodWriter(detectionMethodWriter);
        expWriter.setConfidenceWriter(confidenceWriter);
        expWriter.setAliasWriter(aliasWriter);
        return expWriter;
    }
}
