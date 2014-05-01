package psidev.psi.mi.jami.xml.io.writer.compact;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlFeatureWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXmlBasicBinaryInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.CompactXmlModelledInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.CompactXmlModelledParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.CompactXmlParticipantWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Compact PSI-XML 2.5 writer for light binary interactions (no experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class LightCompactXmlBinaryWriter extends AbstractCompactXmlWriter<BinaryInteraction> {

    public LightCompactXmlBinaryWriter() {
        super(BinaryInteraction.class);
    }

    public LightCompactXmlBinaryWriter(File file) throws IOException, XMLStreamException {
        super(BinaryInteraction.class, file);
    }

    public LightCompactXmlBinaryWriter(OutputStream output) throws XMLStreamException {
        super(BinaryInteraction.class, output);
    }

    public LightCompactXmlBinaryWriter(Writer writer) throws XMLStreamException {
        super(BinaryInteraction.class, writer);
    }

    public LightCompactXmlBinaryWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(BinaryInteraction.class, streamWriter, cache);
    }

    @Override
    protected void registerAvailabilities(BinaryInteraction interaction) {
        // nothing to do
    }

    @Override
    protected void registerExperiment(BinaryInteraction interaction) {
        getExperiments().add(getInteractionWriter().extractDefaultExperimentFrom(interaction));
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
        return complexWriter;
    }

    @Override
    protected PsiXmlInteractionWriter<BinaryInteraction> instantiateInteractionWriter(PsiXmlElementWriter<Alias> aliasWriter,
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
        CompactXmlBasicBinaryInteractionWriter writer = new CompactXmlBasicBinaryInteractionWriter(getStreamWriter(), getElementCache());
        writer.setAttributeWriter(attributeWriter);
        writer.setXrefWriter(primaryRefWriter);
        writer.setChecksumWriter(checksumWriter);
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
        CompactXmlModelledParticipantWriter writer = new CompactXmlModelledParticipantWriter(getStreamWriter(), getElementCache());
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

        CompactXmlParticipantWriter writer = new CompactXmlParticipantWriter(getStreamWriter(), getElementCache());
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
}