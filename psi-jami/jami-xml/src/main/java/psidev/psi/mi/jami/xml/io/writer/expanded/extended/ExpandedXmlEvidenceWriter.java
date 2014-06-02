package psidev.psi.mi.jami.xml.io.writer.expanded.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlModelledParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlExperimentalInteractorWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlInteractionEvidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlModelledInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlParticipantEvidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlFeatureEvidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlExperimentWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlParameterWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlSourceWriter;
import psidev.psi.mi.jami.xml.io.writer.expanded.AbstractExpandedXmlWriter;
import psidev.psi.mi.jami.xml.model.extension.XmlSource;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Expanded PSI-XML 2.5 writer for expanded interaction evidences (full experimental evidences)
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
                                                                                    PsiXmlElementWriter<Confidence> confidenceWriter,
                                                                                    PsiXmlElementWriter<Checksum> checksumWriter,
                                                                                    PsiXmlParameterWriter parameterWriter,
                                                                                    PsiXmlVariableNameWriter<CvTerm> interactionTypeWriter,
                                                                                    PsiXmlExperimentWriter experimentWriter,
                                                                                    PsiXmlParticipantWriter<ModelledParticipant> modelledParticipantWriter,
                                                                                    PsiXmlElementWriter inferredInteractionWriter,
                                                                                    PsiXmlInteractionWriter interactionWriter) {
        XmlModelledInteractionWriter complexWriter = new XmlModelledInteractionWriter(getStreamWriter(), getElementCache());
        complexWriter.setAttributeWriter(attributeWriter);
        complexWriter.setXrefWriter(primaryRefWriter);
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
    protected PsiXmlInteractionWriter<InteractionEvidence> instantiateInteractionWriter(PsiXmlElementWriter<Alias> aliasWriter,
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
        XmlInteractionEvidenceWriter writer = new XmlInteractionEvidenceWriter(getStreamWriter(), getElementCache());
        writer.setAttributeWriter(attributeWriter);
        writer.setXrefWriter(primaryRefWriter);
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
                                                                                                PsiXmlElementWriter<Interactor> interactorWriter,
                                                                                                PsiXmlVariableNameWriter<CvTerm> bioRoleWriter,
                                                                                                PsiXmlElementWriter<ModelledFeature> modelledFeatureWriter,
                                                                                                PsiXmlParticipantWriter participantWriter) {
        XmlModelledParticipantWriter writer = new XmlModelledParticipantWriter(getStreamWriter(), getElementCache());
        writer.setXrefWriter(primaryRefWriter);
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
                                                                                                      PsiXmlElementWriter<Confidence> confidenceWriter,
                                                                                                      PsiXmlElementWriter<Interactor> interactorWriter,
                                                                                                      PsiXmlVariableNameWriter<CvTerm> bioRoleWriter,
                                                                                                      PsiXmlElementWriter featureWriter,
                                                                                                      PsiXmlParameterWriter parameterWriter,
                                                                                                      PsiXmlElementWriter<Organism> organismWriter) {
        XmlExperimentalCvTermWriter expRoleWriter = new XmlExperimentalCvTermWriter(getStreamWriter(), getElementCache());
        expRoleWriter.setAliasWriter(aliasWriter);
        expRoleWriter.setXrefWriter(primaryRefWriter);

        XmlHostOrganismWriter hostOrganismWriter = new XmlHostOrganismWriter(getStreamWriter(), getElementCache());
        hostOrganismWriter.setAliasWriter(aliasWriter);
        hostOrganismWriter.setCvWriter(hostOrganismWriter.getCvWriter());

        XmlParticipantEvidenceWriter writer = new XmlParticipantEvidenceWriter(getStreamWriter(), getElementCache());
        writer.setXrefWriter(primaryRefWriter);
        writer.setAliasWriter(aliasWriter);
        writer.setAttributeWriter(attributeWriter);
        writer.setInteractorWriter(interactorWriter);
        writer.setBiologicalRoleWriter(bioRoleWriter);
        writer.setFeatureWriter(featureWriter);
        writer.setHostOrganismWriter(organismWriter);
        writer.setParameterWriter(parameterWriter);
        writer.setConfidenceWriter(confidenceWriter);
        writer.setExperimentalCvWriter(expRoleWriter);
        writer.setExperimentalInteractorWriter(new XmlExperimentalInteractorWriter(getStreamWriter(), getElementCache()));
        return (PsiXmlParticipantWriter<P>) writer;
    }

    @Override
    protected <F extends Feature> PsiXmlElementWriter<F> instantiateFeatureWriter(PsiXmlElementWriter<Alias> aliasWriter,
                                                                                  PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                  PsiXmlXrefWriter primaryRefWriter,
                                                                                  PsiXmlVariableNameWriter<CvTerm> featureTypeWriter,
                                                                                  PsiXmlElementWriter<Range> rangeWriter) {
        XmlFeatureEvidenceWriter writer = new XmlFeatureEvidenceWriter(getStreamWriter(), getElementCache());
        writer.setRangeWriter(rangeWriter);
        writer.setXrefWriter(primaryRefWriter);
        writer.setFeatureTypeWriter(featureTypeWriter);
        writer.setAliasWriter(aliasWriter);
        writer.setAttributeWriter(attributeWriter);
        return (PsiXmlElementWriter<F>) writer;
    }

    @Override
    protected PsiXmlSourceWriter instantiateSourceWriter(PsiXmlElementWriter<Alias> aliasWriter, PsiXmlElementWriter<Annotation> attributeWriter,
                                                         PsiXmlXrefWriter primaryRefWriter, PsiXmlPublicationWriter publicationWriter) {
        XmlSourceWriter sourceWriter = new XmlSourceWriter(getStreamWriter());
        sourceWriter.setXrefWriter(primaryRefWriter);
        sourceWriter.setAttributeWriter(attributeWriter);
        sourceWriter.setAliasWriter(aliasWriter);
        sourceWriter.setPublicationWriter(publicationWriter);
        return sourceWriter;
    }

    @Override
    protected PsiXmlExperimentWriter instantiateExperimentWriter(PsiXmlElementWriter<Alias> aliasWriter,
                                                                 PsiXmlElementWriter<Annotation> attributeWriter,
                                                                 PsiXmlXrefWriter primaryRefWriter,
                                                                 PsiXmlPublicationWriter publicationWriter,
                                                                 PsiXmlElementWriter<Organism> nonExperimentalHostOrganismWriter,
                                                                 PsiXmlVariableNameWriter<CvTerm> detectionMethodWriter,
                                                                 PsiXmlElementWriter<Confidence> confidenceWriter) {
        XmlExperimentWriter expWriter = new XmlExperimentWriter(getStreamWriter(), getElementCache());
        expWriter.setXrefWriter(primaryRefWriter);
        expWriter.setAttributeWriter(attributeWriter);
        expWriter.setPublicationWriter(publicationWriter);
        expWriter.setHostOrganismWriter(nonExperimentalHostOrganismWriter);
        expWriter.setDetectionMethodWriter(detectionMethodWriter);
        expWriter.setConfidenceWriter(confidenceWriter);
        expWriter.setAliasWriter(aliasWriter);
        return expWriter;
    }

    @Override
    protected PsiXmlParameterWriter instantiateParameterWriter() {
        return new XmlParameterWriter(getStreamWriter(), getElementCache());
    }

    @Override
    protected PsiXmlElementWriter<Confidence> instantiateConfidenceWriter(PsiXmlVariableNameWriter<CvTerm> confidenceTypeWriter) {
        XmlConfidenceWriter confWriter = new XmlConfidenceWriter(getStreamWriter(), getElementCache());
        confWriter.setTypeWriter(confidenceTypeWriter);
        return confWriter;
    }

    @Override
    protected PsiXmlXrefWriter instantiateXrefWriter(PsiXmlElementWriter<Annotation> attributeWriter) {
        XmlDbXrefWriter writer = new XmlDbXrefWriter(getStreamWriter());
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

