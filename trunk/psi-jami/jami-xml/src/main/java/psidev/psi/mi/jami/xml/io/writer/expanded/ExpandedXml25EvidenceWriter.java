package psidev.psi.mi.jami.xml.io.writer.expanded;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.ExpandedXmlInteractionEvidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.ExpandedXmlModelledInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.ExpandedXmlModelledParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.ExpandedXmlParticipantEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Set;

/**
 * Expanded PSI-XML 2.5 writer for interaction evidences (full experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class ExpandedXml25EvidenceWriter extends AbstractExpandedXml25Writer<InteractionEvidence>{

    public ExpandedXml25EvidenceWriter() {
        super(InteractionEvidence.class);
    }

    public ExpandedXml25EvidenceWriter(File file) throws IOException, XMLStreamException {
        super(InteractionEvidence.class, file);
    }

    public ExpandedXml25EvidenceWriter(OutputStream output) throws XMLStreamException {
        super(InteractionEvidence.class, output);
    }

    public ExpandedXml25EvidenceWriter(Writer writer) throws XMLStreamException {
        super(InteractionEvidence.class, writer);
    }

    public ExpandedXml25EvidenceWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
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
        PsiXmlParticipantWriter<ParticipantEvidence> participantWriter = new ExpandedXmlParticipantEvidenceWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter,
                identificationMethodWriter, bioRoleWriter, expRoleWriter, expPreparationWriter,
                featureWriter, hostOrganismWriter, confidenceWriter, parameterWriter, attributeWriter);
        PsiXmlElementWriter<CvTerm> interactionTypeWriter = new XmlInteractionTypeWriter(getStreamWriter(), aliasWriter, primaryRefWriter, secondaryRefWriter);
        PsiXmlElementWriter<Set<Feature>> inferredInteractionWriter = new XmlInferredInteractionWriter(getStreamWriter(), getElementCache());
        PsiXmlExperimentWriter experimentWriter = new XmlExperimentWriter(getStreamWriter(), getElementCache(), publicationWriter,
                primaryRefWriter, secondaryRefWriter, hostOrganismWriter, detectionMethodWriter,
                confidenceWriter, attributeWriter);
        PsiXmlElementWriter<ModelledFeature> modelledFeatureWriter = new XmlModelledFeatureWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, featureTypeWriter,rangeWriter, attributeWriter);
        PsiXmlParticipantWriter<ModelledParticipant> modelledParticipantWriter = new ExpandedXmlModelledParticipantWriter(getStreamWriter(), getElementCache(),
                aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter,
                bioRoleWriter, modelledFeatureWriter, attributeWriter);
        PsiXmlElementWriter<String> availabilityWriter = new XmlAvailabilityWriter(getStreamWriter(), getElementCache());
        // initialise source
        setSourceWriter(new XmlSourceWriter(getStreamWriter(), aliasWriter, publicationWriter,
                primaryRefWriter, secondaryRefWriter, attributeWriter));
        // initialise interaction
        setInteractionWriter(new ExpandedXmlInteractionEvidenceWriter(getStreamWriter(), getElementCache(),
                primaryRefWriter, secondaryRefWriter, availabilityWriter, experimentWriter,
                participantWriter, inferredInteractionWriter, interactionTypeWriter,
                confidenceWriter, parameterWriter, attributeWriter,
                checksumWriter));
        // initialise complex
        setComplexWriter(new ExpandedXmlModelledInteractionWriter(getStreamWriter(), getElementCache(),
                primaryRefWriter, secondaryRefWriter, experimentWriter,
                modelledParticipantWriter, inferredInteractionWriter, interactionTypeWriter,
                confidenceWriter, parameterWriter, attributeWriter,
                checksumWriter));
        setAnnotationsWriter(attributeWriter);
    }
}

