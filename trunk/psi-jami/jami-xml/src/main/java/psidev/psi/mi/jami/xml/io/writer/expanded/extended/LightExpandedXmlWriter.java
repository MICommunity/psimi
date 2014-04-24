package psidev.psi.mi.jami.xml.io.writer.expanded.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlFeatureWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.ExpandedXmlModelledParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.ExpandedXmlParticipantWriter;
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
 * Expanded PSI-XML 2.5 writer for light interactions (no experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class LightExpandedXmlWriter extends AbstractExpandedXmlWriter<Interaction> {

    public LightExpandedXmlWriter() {
        super(Interaction.class);
    }

    public LightExpandedXmlWriter(File file) throws IOException, XMLStreamException {
        super(Interaction.class, file);
    }

    public LightExpandedXmlWriter(OutputStream output) throws XMLStreamException {
        super(Interaction.class, output);
    }

    public LightExpandedXmlWriter(Writer writer) throws XMLStreamException {
        super(Interaction.class, writer);
    }

    public LightExpandedXmlWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(Interaction.class, streamWriter, cache);
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
        ExpandedXmlModelledInteractionWriter complexWriter = new ExpandedXmlModelledInteractionWriter(getStreamWriter(), getElementCache());
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
    protected PsiXmlInteractionWriter<Interaction> instantiateInteractionWriter(PsiXmlElementWriter<Alias> aliasWriter,
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
        ExpandedXmlBasicInteractionWriter writer = new ExpandedXmlBasicInteractionWriter(getStreamWriter(), getElementCache());
        writer.setAttributeWriter(attributeWriter);
        writer.setXrefWriter(primaryRefWriter);
        writer.setChecksumWriter(checksumWriter);
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
                                                                                                PsiXmlElementWriter<Interactor> interactorWriter,
                                                                                                PsiXmlVariableNameWriter<CvTerm> bioRoleWriter,
                                                                                                PsiXmlElementWriter<ModelledFeature> modelledFeatureWriter,
                                                                                                PsiXmlParticipantWriter participantWriter) {
        ExpandedXmlModelledParticipantWriter writer = new ExpandedXmlModelledParticipantWriter(getStreamWriter(), getElementCache());
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

        ExpandedXmlParticipantWriter writer = new ExpandedXmlParticipantWriter(getStreamWriter(), getElementCache());
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
        XmlFeatureWriter writer = new XmlFeatureWriter(getStreamWriter(), getElementCache());
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
                                                                 PsiXmlElementWriter<Annotation> attributeWriter, PsiXmlXrefWriter primaryRefWriter,
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
