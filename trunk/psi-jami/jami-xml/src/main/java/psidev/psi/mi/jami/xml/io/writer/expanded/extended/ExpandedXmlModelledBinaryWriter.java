package psidev.psi.mi.jami.xml.io.writer.expanded.extended;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlModelledFeatureWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.ExpandedXmlModelledParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.*;
import psidev.psi.mi.jami.xml.io.writer.expanded.AbstractExpandedXmlWriter;
import psidev.psi.mi.jami.xml.model.extension.XmlSource;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Expanded PSI-XML 2.5 writer for modelled binary interactions (no experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class ExpandedXmlModelledBinaryWriter extends AbstractExpandedXmlWriter<ModelledBinaryInteraction> {

    public ExpandedXmlModelledBinaryWriter() {
        super(ModelledBinaryInteraction.class);
    }

    public ExpandedXmlModelledBinaryWriter(File file) throws IOException, XMLStreamException {
        super(ModelledBinaryInteraction.class, file);
    }

    public ExpandedXmlModelledBinaryWriter(OutputStream output) throws XMLStreamException {
        super(ModelledBinaryInteraction.class, output);
    }

    public ExpandedXmlModelledBinaryWriter(Writer writer) throws XMLStreamException {
        super(ModelledBinaryInteraction.class, writer);
    }

    public ExpandedXmlModelledBinaryWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(ModelledBinaryInteraction.class, streamWriter, cache);
    }

    @Override
    protected Source extractSourceFromInteraction() {
        return getCurrentInteraction().getSource() != null ? getCurrentInteraction().getSource() : super.extractSourceFromInteraction();
    }

    @Override
    protected PsiXmlInteractionWriter<ModelledInteraction> instantiateComplexWriter(PsiXmlElementWriter<Alias> aliasWriter,
                                                                                    PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                    PsiXmlXrefWriter primaryRefWriter,
                                                                                    PsiXmlXrefWriter secondaryRefWriter,
                                                                                    PsiXmlElementWriter<Confidence> confidenceWriter,
                                                                                    PsiXmlElementWriter<Checksum> checksumWriter,
                                                                                    PsiXmlParameterWriter parameterWriter,
                                                                                    PsiXmlElementWriter<CvTerm> interactionTypeWriter,
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
        complexWriter.setXmlInferredInteractionWriter(inferredInteractionWriter);
        complexWriter.setAliasWriter(aliasWriter);
        // use same default experiment
        complexWriter.setDefaultExperiment(interactionWriter.getDefaultExperiment());
        return complexWriter;
    }

    @Override
    protected PsiXmlInteractionWriter<ModelledBinaryInteraction> instantiateInteractionWriter(PsiXmlElementWriter<Alias> aliasWriter,
                                                                                              PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                              PsiXmlXrefWriter primaryRefWriter,
                                                                                              PsiXmlXrefWriter secondaryRefWriter,
                                                                                              PsiXmlElementWriter<Confidence> confidenceWriter,
                                                                                              PsiXmlElementWriter<Checksum> checksumWriter,
                                                                                              PsiXmlParameterWriter parameterWriter,
                                                                                              PsiXmlParticipantWriter participantWriter,
                                                                                              PsiXmlElementWriter<CvTerm> interactionTypeWriter,
                                                                                              PsiXmlExperimentWriter experimentWriter,
                                                                                              PsiXmlElementWriter<String> availabilityWriter,
                                                                                              PsiXmlElementWriter inferredInteractionWriter) {
        ExpandedXmlModelledBinaryInteractionWriter writer = new ExpandedXmlModelledBinaryInteractionWriter(getStreamWriter(), getElementCache());
        writer.setAttributeWriter(attributeWriter);
        writer.setPrimaryRefWriter(primaryRefWriter);
        writer.setSecondaryRefWriter(secondaryRefWriter);
        writer.setConfidenceWriter(confidenceWriter);
        writer.setChecksumWriter(checksumWriter);
        writer.setParameterWriter(parameterWriter);
        writer.setInteractionTypeWriter(interactionTypeWriter);
        writer.setExperimentWriter(experimentWriter);
        writer.setParticipantWriter(participantWriter);
        writer.setXmlInferredInteractionWriter(inferredInteractionWriter);
        writer.setAliasWriter(aliasWriter);
        return writer;
    }

    @Override
    protected PsiXmlParticipantWriter<ModelledParticipant> instantiateModelledParticipantWriter(PsiXmlElementWriter<Alias> aliasWriter,
                                                                                                PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                                PsiXmlXrefWriter primaryRefWriter,
                                                                                                PsiXmlXrefWriter secondaryRefWriter,
                                                                                                PsiXmlElementWriter<Interactor> interactorWriter,
                                                                                                PsiXmlElementWriter<CvTerm> bioRoleWriter,
                                                                                                PsiXmlElementWriter<ModelledFeature> modelledFeatureWriter,
                                                                                                PsiXmlParticipantWriter participantWriter) {
        return participantWriter;
    }

    @Override
    protected PsiXmlElementWriter<CvTerm> instantiateParticipantDetectionMethodWriter(PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter) {

        return null;
    }

    @Override
    protected PsiXmlElementWriter<CvTerm> instantiateFeatureDetectionMethodWriter(PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter) {

        return null;
    }

    @Override
    protected <P extends Participant> PsiXmlParticipantWriter<P> instantiateParticipantWriter(PsiXmlElementWriter<Alias> aliasWriter,
                                                                                              PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                              PsiXmlXrefWriter primaryRefWriter,
                                                                                              PsiXmlXrefWriter secondaryRefWriter,
                                                                                              PsiXmlElementWriter<Confidence> confidenceWriter,
                                                                                              PsiXmlElementWriter<Interactor> interactorWriter,
                                                                                              PsiXmlElementWriter<CvTerm> bioRoleWriter,
                                                                                              PsiXmlElementWriter featureWriter,
                                                                                              PsiXmlParameterWriter parameterWriter,
                                                                                              PsiXmlElementWriter<CvTerm> participantIdentificationMethodWriter,
                                                                                              PsiXmlElementWriter<Organism> organismWriter) {

        ExpandedXmlModelledParticipantWriter writer = new ExpandedXmlModelledParticipantWriter(getStreamWriter(), getElementCache());
        writer.setPrimaryRefWriter(primaryRefWriter);
        writer.setSecondaryRefWriter(secondaryRefWriter);
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
                                                                                  PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter,
                                                                                  PsiXmlElementWriter<CvTerm> featureTypeWriter,
                                                                                  PsiXmlElementWriter<Range> rangeWriter,
                                                                                  PsiXmlElementWriter<CvTerm> featureDetectionWriter) {
        XmlModelledFeatureWriter writer = new XmlModelledFeatureWriter(getStreamWriter(), getElementCache());
        writer.setRangeWriter(rangeWriter);
        writer.setPrimaryRefWriter(primaryRefWriter);
        writer.setSecondaryRefWriter(secondaryRefWriter);
        writer.setFeatureTypeWriter(featureTypeWriter);
        writer.setAliasWriter(aliasWriter);
        writer.setAttributeWriter(attributeWriter);
        return (PsiXmlElementWriter<F>) writer;
    }

    @Override
    protected PsiXmlSourceWriter instantiateSourceWriter(PsiXmlElementWriter<Alias> aliasWriter, PsiXmlElementWriter<Annotation> attributeWriter, PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter, PsiXmlPublicationWriter publicationWriter) {
        XmlSourceWriter sourceWriter = new XmlSourceWriter(getStreamWriter());
        sourceWriter.setPrimaryRefWriter(primaryRefWriter);
        sourceWriter.setSecondaryRefWriter(secondaryRefWriter);
        sourceWriter.setAttributeWriter(attributeWriter);
        sourceWriter.setAliasWriter(aliasWriter);
        sourceWriter.setPublicationWriter(publicationWriter);
        return sourceWriter;
    }

    @Override
    protected PsiXmlExperimentWriter instantiateExperimentWriter(PsiXmlElementWriter<Alias> aliasWriter, PsiXmlElementWriter<Annotation> attributeWriter, PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter, PsiXmlPublicationWriter publicationWriter, PsiXmlElementWriter<Organism> nonExperimentalHostOrganismWriter, PsiXmlElementWriter<CvTerm> detectionMethodWriter, PsiXmlElementWriter<Confidence> confidenceWriter, PsiXmlElementWriter<CvTerm> participantIdentificationMethodWriter, PsiXmlElementWriter<CvTerm> featureDetectionMethodWriter) {
        XmlExperimentWriter expWriter = new XmlExperimentWriter(getStreamWriter(), getElementCache());
        expWriter.setPrimaryRefWriter(primaryRefWriter);
        expWriter.setSecondaryRefWriter(secondaryRefWriter);
        expWriter.setAttributeWriter(attributeWriter);
        expWriter.setPublicationWriter(publicationWriter);
        expWriter.setHostOrganismWriter(nonExperimentalHostOrganismWriter);
        expWriter.setDetectionMethodWriter(detectionMethodWriter);
        expWriter.setConfidenceWriter(confidenceWriter);
        expWriter.setAliasWriter(aliasWriter);
        expWriter.setParticipantIdentificationMethodWriter(participantIdentificationMethodWriter);
        expWriter.setFeatureDetectionMethodWriter(featureDetectionMethodWriter);
        return expWriter;
    }

    @Override
    protected PsiXmlParameterWriter instantiateParameterWriter() {
        return new XmlParameterWriter(getStreamWriter(), getElementCache());
    }

    @Override
    protected PsiXmlElementWriter<Confidence> instantiateConfidenceWriter(PsiXmlElementWriter<CvTerm> confidenceTypeWriter) {
        XmlConfidenceWriter confWriter = new XmlConfidenceWriter(getStreamWriter(), getElementCache());
        confWriter.setTypeWriter(confidenceTypeWriter);
        return confWriter;
    }

    @Override
    protected PsiXmlXrefWriter instantiateSecondaryRefWriter(PsiXmlElementWriter<Annotation> attributeWriter) {
        XmlSecondaryXrefWriter writer = new XmlSecondaryXrefWriter(getStreamWriter());
        writer.setAnnotationWriter(attributeWriter);
        return writer;
    }

    @Override
    protected PsiXmlXrefWriter instantiatePrimaryRefWriter(PsiXmlElementWriter<Annotation> attributeWriter) {
        XmlPrimaryXrefWriter writer = new XmlPrimaryXrefWriter(getStreamWriter());
        writer.setAnnotationWriter(attributeWriter);
        return writer;
    }

    @Override
    protected PsiXmlElementWriter instantiateInferredInteractionWriter() {
        return new XmlInferredInteractionWriter(getStreamWriter(), getElementCache());
    }

    @Override
    protected PsiXmlElementWriter<ModelledFeature> instantiateModelledFeatureWriter(PsiXmlElementWriter<Alias> aliasWriter, PsiXmlElementWriter<Annotation> attributeWriter, PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter, PsiXmlElementWriter<CvTerm> featureTypeWriter, PsiXmlElementWriter<Range> rangeWriter, PsiXmlElementWriter featureWriter) {
        return featureWriter;
    }

    @Override
    protected void initialiseDefaultSource() {
        setDefaultSource(new XmlSource("Unknown source"));
    }

    @Override
    protected void writeInteraction() throws XMLStreamException {
        // write interaction
        super.writeInteraction();
        // remove experiments
        for (Object exp : ((PsiXmlExtendedInteractionWriter)getInteractionWriter()).extractDefaultExperimentsFrom(getCurrentInteraction())){
            getElementCache().removeObject(exp);
        }
    }

    @Override
    protected void writeComplex(ModelledInteraction modelled) {
        super.writeComplex(modelled);
        // remove experiments
        for (Object exp : ((PsiXmlExtendedInteractionWriter)getComplexWriter()).extractDefaultExperimentsFrom(modelled)){
            getElementCache().removeObject(exp);
        }
    }
}
