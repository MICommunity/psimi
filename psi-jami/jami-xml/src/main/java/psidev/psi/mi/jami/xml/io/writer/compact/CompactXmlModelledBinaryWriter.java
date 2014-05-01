package psidev.psi.mi.jami.xml.io.writer.compact;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlModelledFeatureWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXmlModelledBinaryInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXmlModelledInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.CompactXmlModelledParticipantWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Compact PSI-XML 2.5 writer for modelled binary interactions (no experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class CompactXmlModelledBinaryWriter extends AbstractCompactXmlWriter<ModelledBinaryInteraction> {

    public CompactXmlModelledBinaryWriter() {
        super(ModelledBinaryInteraction.class);
    }

    public CompactXmlModelledBinaryWriter(File file) throws IOException, XMLStreamException {
        super(ModelledBinaryInteraction.class, file);
    }

    public CompactXmlModelledBinaryWriter(OutputStream output) throws XMLStreamException {
        super(ModelledBinaryInteraction.class, output);
    }

    public CompactXmlModelledBinaryWriter(Writer writer) throws XMLStreamException {
        super(ModelledBinaryInteraction.class, writer);
    }

    public CompactXmlModelledBinaryWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(ModelledBinaryInteraction.class, streamWriter, cache);
    }

    @Override
    protected void registerAvailabilities(ModelledBinaryInteraction interaction) {
        // nothing to do
    }

    @Override
    protected void registerExperiment(ModelledBinaryInteraction interaction) {
        getExperiments().add(getInteractionWriter().extractDefaultExperimentFrom(interaction));
    }

    @Override
    protected Source extractSourceFromInteraction() {
        return getCurrentInteraction().getSource() != null ? getCurrentInteraction().getSource() : super.extractSourceFromInteraction();
    }

    @Override
    protected void registerInteractionProperties() {
        super.registerInteractionProperties();
        for (CooperativeEffect effect : getCurrentInteraction().getCooperativeEffects()){
            for (ModelledInteraction interaction : effect.getAffectedInteractions()){
                registerAllInteractorsAndExperimentsFrom(interaction);
            }
        }
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
        CompactXmlModelledInteractionWriter complexWriter = new CompactXmlModelledInteractionWriter(getStreamWriter(), getElementCache());
        complexWriter.setAttributeWriter(attributeWriter);
        complexWriter.setXrefWriter(primaryRefWriter);
        complexWriter.setConfidenceWriter(confidenceWriter);
        complexWriter.setChecksumWriter(checksumWriter);
        complexWriter.setParameterWriter(parameterWriter);
        complexWriter.setInteractionTypeWriter(interactionTypeWriter);
        complexWriter.setExperimentWriter(experimentWriter);
        complexWriter.setParticipantWriter(modelledParticipantWriter);
        complexWriter.setInferredInteractionWriter(inferredInteractionWriter);
        // use same default experiment
        complexWriter.setDefaultExperiment(interactionWriter.getDefaultExperiment());
        return complexWriter;
    }

    @Override
    protected PsiXmlInteractionWriter<ModelledBinaryInteraction> instantiateInteractionWriter(PsiXmlElementWriter<Alias> aliasWriter,
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
        CompactXmlModelledBinaryInteractionWriter writer = new CompactXmlModelledBinaryInteractionWriter(getStreamWriter(), getElementCache());
        writer.setAttributeWriter(attributeWriter);
        writer.setXrefWriter(primaryRefWriter);
        writer.setConfidenceWriter(confidenceWriter);
        writer.setChecksumWriter(checksumWriter);
        writer.setParameterWriter(parameterWriter);
        writer.setInteractionTypeWriter(interactionTypeWriter);
        writer.setExperimentWriter(experimentWriter);
        writer.setParticipantWriter(participantWriter);
        writer.setInferredInteractionWriter(inferredInteractionWriter);
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

        CompactXmlModelledParticipantWriter writer = new CompactXmlModelledParticipantWriter(getStreamWriter(), getElementCache());
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
}
