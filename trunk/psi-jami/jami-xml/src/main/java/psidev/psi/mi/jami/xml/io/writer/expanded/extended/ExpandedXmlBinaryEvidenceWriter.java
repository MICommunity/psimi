package psidev.psi.mi.jami.xml.io.writer.expanded.extended;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.ExpandedXmlModelledParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.*;
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
import psidev.psi.mi.jami.xml.io.writer.expanded.AbstractExpandedXmlWriter;
import psidev.psi.mi.jami.xml.model.extension.XmlSource;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Expanded PSI-XML 2.5 writer for extended binary interaction evidences (full experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class ExpandedXmlBinaryEvidenceWriter extends AbstractExpandedXmlWriter<BinaryInteractionEvidence> {

    public ExpandedXmlBinaryEvidenceWriter() {
        super(BinaryInteractionEvidence.class);
    }

    public ExpandedXmlBinaryEvidenceWriter(File file) throws IOException, XMLStreamException {
        super(BinaryInteractionEvidence.class, file);
    }

    public ExpandedXmlBinaryEvidenceWriter(OutputStream output) throws XMLStreamException {
        super(BinaryInteractionEvidence.class, output);
    }

    public ExpandedXmlBinaryEvidenceWriter(Writer writer) throws XMLStreamException {
        super(BinaryInteractionEvidence.class, writer);
    }

    public ExpandedXmlBinaryEvidenceWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache elementCache) {
        super(BinaryInteractionEvidence.class, streamWriter, elementCache);
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
        return complexWriter;
    }

    @Override
    protected PsiXmlInteractionWriter<BinaryInteractionEvidence> instantiateInteractionWriter(PsiXmlElementWriter<Alias> aliasWriter,
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
        ExpandedXmlBinaryInteractionEvidenceWriter writer = new ExpandedXmlBinaryInteractionEvidenceWriter(getStreamWriter(), getElementCache());
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
                                                                                                PsiXmlElementWriter<CvTerm> bioRoleWriter,
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
    protected PsiXmlElementWriter<CvTerm> instantiateParticipantDetectionMethodWriter(PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter) {
        XmlParticipantIdentificationMethodWriter identificationMethodWriter = new XmlParticipantIdentificationMethodWriter(getStreamWriter(), getElementCache());
        identificationMethodWriter.setAliasWriter(aliasWriter);
        identificationMethodWriter.setSecondaryRefWriter(secondaryRefWriter);
        identificationMethodWriter.setPrimaryRefWriter(primaryRefWriter);

        return identificationMethodWriter;
    }

    @Override
    protected PsiXmlElementWriter<CvTerm> instantiateFeatureDetectionMethodWriter(PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter) {
        XmlFeatureDetectionMethodWriter featureDetectionWriter = new XmlFeatureDetectionMethodWriter(getStreamWriter());
        featureDetectionWriter.setAliasWriter(aliasWriter);
        featureDetectionWriter.setSecondaryRefWriter(secondaryRefWriter);
        featureDetectionWriter.setPrimaryRefWriter(primaryRefWriter);

        return featureDetectionWriter;
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
        XmlExperimentalRoleWriter expRoleWriter = new XmlExperimentalRoleWriter(getStreamWriter(), getElementCache());
        expRoleWriter.setAliasWriter(aliasWriter);
        expRoleWriter.setPrimaryRefWriter(primaryRefWriter);
        expRoleWriter.setSecondaryRefWriter(secondaryRefWriter);

        XmlExperimentalPreparationWriter expPreparationWriter = new XmlExperimentalPreparationWriter(getStreamWriter(), getElementCache());
        expPreparationWriter.setAliasWriter(aliasWriter);
        expPreparationWriter.setPrimaryRefWriter(primaryRefWriter);
        expPreparationWriter.setSecondaryRefWriter(secondaryRefWriter);

        XmlHostOrganismWriter hostOrganismWriter = new XmlHostOrganismWriter(getStreamWriter(), getElementCache());
        hostOrganismWriter.setAliasWriter(aliasWriter);
        hostOrganismWriter.setCellTypeWriter(hostOrganismWriter.getCellTypeWriter());
        hostOrganismWriter.setCompartmentWriter(hostOrganismWriter.getCompartmentWriter());
        hostOrganismWriter.setTissueWriter(hostOrganismWriter.getTissueWriter());

        ExpandedXmlParticipantEvidenceWriter writer = new ExpandedXmlParticipantEvidenceWriter(getStreamWriter(), getElementCache());
        writer.setPrimaryRefWriter(primaryRefWriter);
        writer.setSecondaryRefWriter(secondaryRefWriter);
        writer.setAliasWriter(aliasWriter);
        writer.setAttributeWriter(attributeWriter);
        writer.setInteractorWriter(interactorWriter);
        writer.setBiologicalRoleWriter(bioRoleWriter);
        writer.setFeatureWriter(featureWriter);
        writer.setIdentificationMethodWriter(participantIdentificationMethodWriter);
        writer.setHostOrganismWriter(organismWriter);
        writer.setParameterWriter(parameterWriter);
        writer.setConfidenceWriter(confidenceWriter);
        writer.setExperimentalPreparationWriter(expPreparationWriter);
        writer.setExperimentalRoleWriter(expRoleWriter);
        writer.setExperimentalInteractorWriter(new ExpandedXmlExperimentalInteractorWriter(getStreamWriter(), getElementCache()));
        return (PsiXmlParticipantWriter<P>) writer;
    }

    @Override
    protected <F extends Feature> PsiXmlElementWriter<F> instantiateFeatureWriter(PsiXmlElementWriter<Alias> aliasWriter,
                                                                                  PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                  PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter,
                                                                                  PsiXmlElementWriter<CvTerm> featureTypeWriter,
                                                                                  PsiXmlElementWriter<Range> rangeWriter,
                                                                                  PsiXmlElementWriter<CvTerm> featureDetectionWriter) {
        XmlFeatureEvidenceWriter writer = new XmlFeatureEvidenceWriter(getStreamWriter(), getElementCache());
        writer.setRangeWriter(rangeWriter);
        writer.setPrimaryRefWriter(primaryRefWriter);
        writer.setSecondaryRefWriter(secondaryRefWriter);
        writer.setFeatureTypeWriter(featureTypeWriter);
        writer.setAliasWriter(aliasWriter);
        writer.setAttributeWriter(attributeWriter);
        writer.setDetectionMethodWriter(featureDetectionWriter);
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
