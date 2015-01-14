package psidev.psi.mi.jami.xml.io.writer.elements;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXmlType;
import psidev.psi.mi.jami.xml.PsiXmlVersion;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml30.XmlModelledParticipantCandidateWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.*;
import psidev.psi.mi.jami.xml.model.extension.ExperimentalInteractor;
import psidev.psi.mi.jami.xml.model.extension.InferredInteraction;

import javax.xml.stream.XMLStreamWriter;
import java.util.Set;

/**
 * Factory to initialise PSI-XML element writers depending on the version and the interaction object category
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/04/14</pre>
 */

public class PsiXmlElementWriterFactory {

    private static final PsiXmlElementWriterFactory instance = new PsiXmlElementWriterFactory();

    private PsiXmlElementWriterFactory(){
        // nothing to do here
    }

    public static PsiXmlElementWriterFactory getInstance() {
        return instance;
    }

    public static PsiXmlInteractionWriter[] createInteractionWritersFor(XMLStreamWriter streamWriter, PsiXmlObjectCache objectIndex, PsiXmlVersion version,
                                                                        PsiXmlType xmlType, InteractionCategory interactionCategory,
                                                                        ComplexType complexType, boolean extended, boolean named,
                                                                        PsiXmlElementWriter<Alias> aliasWriter,
                                                                        PsiXmlElementWriter<Annotation> attributeWriter,
                                                                        PsiXmlXrefWriter primaryRefWriter,
                                                                        PsiXmlElementWriter[] confidenceWriters,
                                                                        PsiXmlElementWriter<Checksum> checksumWriter,
                                                                        PsiXmlVariableNameWriter<CvTerm> interactionTypeWriter,
                                                                        PsiXmlVariableNameWriter<CvTerm> openCvWriter,
                                                                        PsiXmlExperimentWriter experimentWriter,
                                                                        PsiXmlElementWriter<String> availabilityWriter,
                                                                        PsiXmlElementWriter<Interactor> interactorWriter,
                                                                        PsiXmlPublicationWriter publicationWriter){

        PsiXmlParameterWriter[] parameterWriters = createParameterWriters(streamWriter, extended, objectIndex, version, publicationWriter);
        PsiXmlParticipantWriter[] participantWriters = createParticipantWriter(streamWriter, extended, objectIndex, version, xmlType, interactionCategory,
                aliasWriter, attributeWriter, primaryRefWriter, confidenceWriters[0], interactorWriter, interactionTypeWriter, openCvWriter, parameterWriters[0]);
        PsiXmlElementWriter inferredInteractionWriter = createInferredInteractionWriter(streamWriter, objectIndex);

        if (extended){
            return createExtendedPsiXmlInteractionWriters(streamWriter, objectIndex, version, xmlType, interactionCategory, complexType,
                    aliasWriter, attributeWriter, primaryRefWriter, confidenceWriters, checksumWriter, interactionTypeWriter,
                    experimentWriter, availabilityWriter, parameterWriters, participantWriters, inferredInteractionWriter, publicationWriter,
                    openCvWriter);
        }
        else if (named){
            return createNamedPsiXmlInteractionWriters(streamWriter, objectIndex, version, xmlType, interactionCategory, complexType,
                    aliasWriter, attributeWriter, primaryRefWriter, confidenceWriters, checksumWriter, interactionTypeWriter,
                    experimentWriter, availabilityWriter, parameterWriters, participantWriters, inferredInteractionWriter, publicationWriter,
                    openCvWriter);
        }
        else{
            return createDefaultPsiXmlInteractionWriters(streamWriter, objectIndex, version, xmlType, interactionCategory, complexType,
                    aliasWriter, attributeWriter, primaryRefWriter, confidenceWriters, checksumWriter, interactionTypeWriter,
                    experimentWriter, availabilityWriter, parameterWriters, participantWriters, inferredInteractionWriter, publicationWriter,
                    openCvWriter);
        }
    }

    public static PsiXmlInteractionWriter[] createDefaultPsiXmlInteractionWriters(XMLStreamWriter streamWriter, PsiXmlObjectCache objectIndex,
                                                                                   PsiXmlVersion version, PsiXmlType xmlType,
                                                                                   InteractionCategory interactionCategory, ComplexType complexType,
                                                                                   PsiXmlElementWriter<Alias> aliasWriter,
                                                                                   PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                   PsiXmlXrefWriter primaryRefWriter,
                                                                                   PsiXmlElementWriter[] confidenceWriters,
                                                                                   PsiXmlElementWriter<Checksum> checksumWriter,
                                                                                   PsiXmlVariableNameWriter<CvTerm> interactionTypeWriter,
                                                                                   PsiXmlExperimentWriter experimentWriter,
                                                                                   PsiXmlElementWriter<String> availabilityWriter,
                                                                                   PsiXmlParameterWriter[] parameterWriters,
                                                                                   PsiXmlParticipantWriter[] participantWriters,
                                                                                   PsiXmlElementWriter inferredInteractionWriter,
                                                                                   PsiXmlPublicationWriter publicationWriter,
                                                                                   PsiXmlVariableNameWriter<CvTerm> openCvWriter) {
        switch (version){
            case v3_0_0:
                switch (xmlType){
                    case compact:
                        PsiXmlElementWriter<Set<Feature>> bindingFeaturesWriter = createBindingFeaturesWriter(streamWriter,  objectIndex);
                        PsiXmlCausalRelationshipWriter relationshipWriter = createCausalRelationshipWriter(streamWriter,false,  objectIndex,
                                openCvWriter);
                        PsiXmlElementWriter<Preassembly> preassemblyWriter = createPreassemblyWriter(streamWriter, false, objectIndex, interactionTypeWriter,
                                attributeWriter, publicationWriter);
                        PsiXmlElementWriter<Allostery> allosteryWriter = createAllosteryWriter(streamWriter, false, objectIndex, interactionTypeWriter,
                                attributeWriter, publicationWriter);
                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlModelledInteractionWriter modelledWriter2 =
                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlModelledInteractionWriter(streamWriter, objectIndex);

                        modelledWriter2.setAliasWriter(aliasWriter);
                        modelledWriter2.setAttributeWriter(attributeWriter);
                        modelledWriter2.setXrefWriter(primaryRefWriter);
                        modelledWriter2.setInteractionTypeWriter(interactionTypeWriter);
                        modelledWriter2.setConfidenceWriter(confidenceWriters[1]);
                        modelledWriter2.setInferredInteractionWriter(inferredInteractionWriter);
                        modelledWriter2.setParticipantWriter(participantWriters[1]);
                        modelledWriter2.setChecksumWriter(checksumWriter);
                        modelledWriter2.setExperimentWriter(experimentWriter);
                        modelledWriter2.setParameterWriter(parameterWriters[1]);
                        modelledWriter2.setBindingFeaturesWriter(bindingFeaturesWriter);
                        modelledWriter2.setCausalRelationshipWriter(relationshipWriter);
                        modelledWriter2.setAllosteryWriter(allosteryWriter);
                        modelledWriter2.setPreAssemblyWriter(preassemblyWriter);

                        switch (complexType){
                            case binary:
                                switch (interactionCategory){
                                    case modelled:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlModelledBinaryInteractionWriter modelledWriter4 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlModelledBinaryInteractionWriter(streamWriter, objectIndex);
                                        modelledWriter4.setAliasWriter(aliasWriter);
                                        modelledWriter4.setAttributeWriter(attributeWriter);
                                        modelledWriter4.setXrefWriter(primaryRefWriter);
                                        modelledWriter4.setInteractionTypeWriter(interactionTypeWriter);
                                        modelledWriter4.setConfidenceWriter(confidenceWriters[1]);
                                        modelledWriter4.setInferredInteractionWriter(inferredInteractionWriter);
                                        modelledWriter4.setParticipantWriter(participantWriters[1]);
                                        modelledWriter4.setChecksumWriter(checksumWriter);
                                        modelledWriter4.setExperimentWriter(experimentWriter);
                                        modelledWriter4.setParameterWriter(parameterWriters[1]);
                                        modelledWriter4.setBindingFeaturesWriter(bindingFeaturesWriter);
                                        modelledWriter4.setAllosteryWriter(allosteryWriter);
                                        modelledWriter4.setPreAssemblyWriter(preassemblyWriter);
                                        modelledWriter4.setCausalRelationshipWriter(relationshipWriter);

                                        return new PsiXmlInteractionWriter[]{modelledWriter4, modelledWriter2};
                                    case complex:
                                        throw new IllegalArgumentException("Cannot create a XML complex writer for binary interactions");
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlBinaryInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlBinaryInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);
                                        writer2.setVariableParameterValueSetWriter(createVariableParameterValueSetWriter(streamWriter, objectIndex));
                                        writer2.setCausalRelationshipWriter(relationshipWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter2};
                                }
                            default:
                                switch (interactionCategory){
                                    case modelled:
                                        return new PsiXmlInteractionWriter[]{modelledWriter2, modelledWriter2};
                                    case complex:
                                        return new PsiXmlInteractionWriter[]{modelledWriter2, modelledWriter2};
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);
                                        writer2.setVariableParameterValueSetWriter(createVariableParameterValueSetWriter(streamWriter, objectIndex));
                                        writer2.setCausalRelationshipWriter(relationshipWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter2};
                                }
                        }

                    default:
                        bindingFeaturesWriter = createBindingFeaturesWriter(streamWriter,  objectIndex);
                        relationshipWriter = createCausalRelationshipWriter(streamWriter,false,  objectIndex,
                                openCvWriter);
                        preassemblyWriter = createPreassemblyWriter(streamWriter, false, objectIndex, interactionTypeWriter,
                                attributeWriter, publicationWriter);
                        allosteryWriter = createAllosteryWriter(streamWriter, false, objectIndex, interactionTypeWriter,
                                attributeWriter, publicationWriter);
                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlModelledInteractionWriter modelledWriter3 =
                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlModelledInteractionWriter(streamWriter, objectIndex);
                        modelledWriter3.setAliasWriter(aliasWriter);

                        modelledWriter3.setAttributeWriter(attributeWriter);
                        modelledWriter3.setXrefWriter(primaryRefWriter);
                        modelledWriter3.setInteractionTypeWriter(interactionTypeWriter);
                        modelledWriter3.setConfidenceWriter(confidenceWriters[1]);
                        modelledWriter3.setInferredInteractionWriter(inferredInteractionWriter);
                        modelledWriter3.setParticipantWriter(participantWriters[1]);
                        modelledWriter3.setChecksumWriter(checksumWriter);
                        modelledWriter3.setExperimentWriter(experimentWriter);
                        modelledWriter3.setParameterWriter(parameterWriters[1]);
                        modelledWriter3.setBindingFeaturesWriter(bindingFeaturesWriter);
                        modelledWriter3.setCausalRelationshipWriter(relationshipWriter);
                        modelledWriter3.setAllosteryWriter(allosteryWriter);
                        modelledWriter3.setPreAssemblyWriter(preassemblyWriter);

                        switch (complexType){
                            case binary:
                                switch (interactionCategory){
                                    case modelled:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlModelledBinaryInteractionWriter modelledWriter4 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlModelledBinaryInteractionWriter(streamWriter, objectIndex);
                                        modelledWriter4.setAliasWriter(aliasWriter);
                                        modelledWriter4.setAttributeWriter(attributeWriter);
                                        modelledWriter4.setXrefWriter(primaryRefWriter);
                                        modelledWriter4.setInteractionTypeWriter(interactionTypeWriter);
                                        modelledWriter4.setConfidenceWriter(confidenceWriters[1]);
                                        modelledWriter4.setInferredInteractionWriter(inferredInteractionWriter);
                                        modelledWriter4.setParticipantWriter(participantWriters[1]);
                                        modelledWriter4.setChecksumWriter(checksumWriter);
                                        modelledWriter4.setExperimentWriter(experimentWriter);
                                        modelledWriter4.setParameterWriter(parameterWriters[1]);
                                        modelledWriter4.setBindingFeaturesWriter(bindingFeaturesWriter);
                                        modelledWriter4.setCausalRelationshipWriter(relationshipWriter);
                                        modelledWriter4.setAllosteryWriter(allosteryWriter);
                                        modelledWriter4.setPreAssemblyWriter(preassemblyWriter);

                                        return new PsiXmlInteractionWriter[]{modelledWriter4, modelledWriter3};
                                    case complex:
                                        throw new IllegalArgumentException("Cannot create a XML complex writer for binary interactions");
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlBinaryInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlBinaryInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);
                                        writer2.setVariableParameterValueSetWriter(createVariableParameterValueSetWriter(streamWriter, objectIndex));
                                        writer2.setCausalRelationshipWriter(relationshipWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter3};
                                }
                            default:
                                switch (interactionCategory){
                                    case modelled:
                                        return new PsiXmlInteractionWriter[]{modelledWriter3, modelledWriter3};
                                    case complex:
                                        return new PsiXmlInteractionWriter[]{modelledWriter3, modelledWriter3};
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);
                                        writer2.setVariableParameterValueSetWriter(createVariableParameterValueSetWriter(streamWriter, objectIndex));
                                        writer2.setCausalRelationshipWriter(relationshipWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter3};
                                }
                        }
                }

            default:

                switch (xmlType){
                    case compact:
                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlModelledInteractionWriter modelledWriter2 =
                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlModelledInteractionWriter(streamWriter, objectIndex);
                        modelledWriter2.setAliasWriter(aliasWriter);
                        modelledWriter2.setAttributeWriter(attributeWriter);
                        modelledWriter2.setXrefWriter(primaryRefWriter);
                        modelledWriter2.setInteractionTypeWriter(interactionTypeWriter);
                        modelledWriter2.setConfidenceWriter(confidenceWriters[1]);
                        modelledWriter2.setInferredInteractionWriter(inferredInteractionWriter);
                        modelledWriter2.setParticipantWriter(participantWriters[1]);
                        modelledWriter2.setChecksumWriter(checksumWriter);
                        modelledWriter2.setExperimentWriter(experimentWriter);
                        modelledWriter2.setParameterWriter(parameterWriters[1]);

                        switch (complexType){
                            case binary:
                                switch (interactionCategory){
                                    case modelled:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlModelledBinaryInteractionWriter modelledWriter4 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlModelledBinaryInteractionWriter(streamWriter, objectIndex);
                                        modelledWriter4.setAliasWriter(aliasWriter);
                                        modelledWriter4.setAttributeWriter(attributeWriter);
                                        modelledWriter4.setXrefWriter(primaryRefWriter);
                                        modelledWriter4.setInteractionTypeWriter(interactionTypeWriter);
                                        modelledWriter4.setConfidenceWriter(confidenceWriters[1]);
                                        modelledWriter4.setInferredInteractionWriter(inferredInteractionWriter);
                                        modelledWriter4.setParticipantWriter(participantWriters[1]);
                                        modelledWriter4.setChecksumWriter(checksumWriter);
                                        modelledWriter4.setExperimentWriter(experimentWriter);
                                        modelledWriter4.setParameterWriter(parameterWriters[1]);

                                        return new PsiXmlInteractionWriter[]{modelledWriter4, modelledWriter2};
                                    case complex:
                                        throw new IllegalArgumentException("Cannot create a XML complex writer for binary interactions");
                                    case basic:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlBasicBinaryInteractionWriter writer3 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlBasicBinaryInteractionWriter(streamWriter, objectIndex);
                                        writer3.setAttributeWriter(attributeWriter);
                                        writer3.setXrefWriter(primaryRefWriter);
                                        writer3.setInteractionTypeWriter(interactionTypeWriter);
                                        writer3.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer3.setParticipantWriter(participantWriters[0]);
                                        writer3.setChecksumWriter(checksumWriter);
                                        writer3.setExperimentWriter(experimentWriter);

                                        return new PsiXmlInteractionWriter[]{writer3, modelledWriter2};
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlBinaryInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlBinaryInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter2};
                                }
                            default:
                                switch (interactionCategory){
                                    case modelled:
                                        return new PsiXmlInteractionWriter[]{modelledWriter2, modelledWriter2};
                                    case complex:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlComplexWriter complexWriter =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlComplexWriter(streamWriter, objectIndex);
                                        complexWriter.setAliasWriter(aliasWriter);
                                        complexWriter.setAttributeWriter(attributeWriter);
                                        complexWriter.setXrefWriter(primaryRefWriter);
                                        complexWriter.setInteractionTypeWriter(interactionTypeWriter);
                                        complexWriter.setConfidenceWriter(confidenceWriters[1]);
                                        complexWriter.setInferredInteractionWriter(inferredInteractionWriter);
                                        complexWriter.setParticipantWriter(participantWriters[1]);
                                        complexWriter.setChecksumWriter(checksumWriter);
                                        complexWriter.setExperimentWriter(experimentWriter);
                                        complexWriter.setParameterWriter(parameterWriters[1]);
                                        return new PsiXmlInteractionWriter[]{complexWriter, modelledWriter2};
                                    case basic:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlBasicInteractionWriter writer3 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlBasicInteractionWriter(streamWriter, objectIndex);
                                        writer3.setAttributeWriter(attributeWriter);
                                        writer3.setXrefWriter(primaryRefWriter);
                                        writer3.setInteractionTypeWriter(interactionTypeWriter);
                                        writer3.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer3.setParticipantWriter(participantWriters[0]);
                                        writer3.setChecksumWriter(checksumWriter);
                                        writer3.setExperimentWriter(experimentWriter);

                                        return new PsiXmlInteractionWriter[]{writer3, modelledWriter2};
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter2};
                                }
                        }

                    default:
                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlModelledInteractionWriter modelledWriter3 =
                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlModelledInteractionWriter(streamWriter, objectIndex);
                        modelledWriter3.setAliasWriter(aliasWriter);
                        modelledWriter3.setAttributeWriter(attributeWriter);
                        modelledWriter3.setXrefWriter(primaryRefWriter);
                        modelledWriter3.setInteractionTypeWriter(interactionTypeWriter);
                        modelledWriter3.setConfidenceWriter(confidenceWriters[1]);
                        modelledWriter3.setInferredInteractionWriter(inferredInteractionWriter);
                        modelledWriter3.setParticipantWriter(participantWriters[1]);
                        modelledWriter3.setChecksumWriter(checksumWriter);
                        modelledWriter3.setExperimentWriter(experimentWriter);
                        modelledWriter3.setParameterWriter(parameterWriters[1]);

                        switch (complexType){
                            case binary:
                                switch (interactionCategory){
                                    case modelled:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlModelledBinaryInteractionWriter modelledWriter4 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlModelledBinaryInteractionWriter(streamWriter, objectIndex);
                                        modelledWriter4.setAliasWriter(aliasWriter);
                                        modelledWriter4.setAttributeWriter(attributeWriter);
                                        modelledWriter4.setXrefWriter(primaryRefWriter);
                                        modelledWriter4.setInteractionTypeWriter(interactionTypeWriter);
                                        modelledWriter4.setConfidenceWriter(confidenceWriters[1]);
                                        modelledWriter4.setInferredInteractionWriter(inferredInteractionWriter);
                                        modelledWriter4.setParticipantWriter(participantWriters[1]);
                                        modelledWriter4.setChecksumWriter(checksumWriter);
                                        modelledWriter4.setExperimentWriter(experimentWriter);
                                        modelledWriter4.setParameterWriter(parameterWriters[1]);

                                        return new PsiXmlInteractionWriter[]{modelledWriter4, modelledWriter3};
                                    case complex:
                                        throw new IllegalArgumentException("Cannot create a XML complex writer for binary interactions");
                                    case basic:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlBasicBinaryInteractionWriter writer3 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlBasicBinaryInteractionWriter(streamWriter, objectIndex);
                                        writer3.setAttributeWriter(attributeWriter);
                                        writer3.setXrefWriter(primaryRefWriter);
                                        writer3.setInteractionTypeWriter(interactionTypeWriter);
                                        writer3.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer3.setParticipantWriter(participantWriters[0]);
                                        writer3.setChecksumWriter(checksumWriter);
                                        writer3.setExperimentWriter(experimentWriter);

                                        return new PsiXmlInteractionWriter[]{writer3, modelledWriter3};
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlBinaryInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlBinaryInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter3};
                                }
                            default:
                                switch (interactionCategory){
                                    case modelled:
                                        return new PsiXmlInteractionWriter[]{modelledWriter3, modelledWriter3};
                                    case complex:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlComplexWriter complexWriter =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlComplexWriter(streamWriter, objectIndex);
                                        complexWriter.setAliasWriter(aliasWriter);
                                        complexWriter.setAttributeWriter(attributeWriter);
                                        complexWriter.setXrefWriter(primaryRefWriter);
                                        complexWriter.setInteractionTypeWriter(interactionTypeWriter);
                                        complexWriter.setConfidenceWriter(confidenceWriters[1]);
                                        complexWriter.setInferredInteractionWriter(inferredInteractionWriter);
                                        complexWriter.setParticipantWriter(participantWriters[1]);
                                        complexWriter.setChecksumWriter(checksumWriter);
                                        complexWriter.setExperimentWriter(experimentWriter);
                                        complexWriter.setParameterWriter(parameterWriters[1]);
                                        return new PsiXmlInteractionWriter[]{complexWriter, modelledWriter3};
                                    case basic:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlBasicInteractionWriter writer3 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlBasicInteractionWriter(streamWriter, objectIndex);
                                        writer3.setAttributeWriter(attributeWriter);
                                        writer3.setXrefWriter(primaryRefWriter);
                                        writer3.setInteractionTypeWriter(interactionTypeWriter);
                                        writer3.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer3.setParticipantWriter(participantWriters[0]);
                                        writer3.setChecksumWriter(checksumWriter);
                                        writer3.setExperimentWriter(experimentWriter);

                                        return new PsiXmlInteractionWriter[]{writer3, modelledWriter3};
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter3};
                                }
                        }
                }
        }
    }

    public static PsiXmlInteractionWriter[] createNamedPsiXmlInteractionWriters(XMLStreamWriter streamWriter, PsiXmlObjectCache objectIndex,
                                                                                 PsiXmlVersion version, PsiXmlType xmlType,
                                                                                 InteractionCategory interactionCategory, ComplexType complexType,
                                                                                 PsiXmlElementWriter<Alias> aliasWriter,
                                                                                 PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                 PsiXmlXrefWriter primaryRefWriter,
                                                                                 PsiXmlElementWriter[] confidenceWriters,
                                                                                 PsiXmlElementWriter<Checksum> checksumWriter,
                                                                                 PsiXmlVariableNameWriter<CvTerm> interactionTypeWriter,
                                                                                 PsiXmlExperimentWriter experimentWriter,
                                                                                 PsiXmlElementWriter<String> availabilityWriter,
                                                                                 PsiXmlParameterWriter[] parameterWriters,
                                                                                 PsiXmlParticipantWriter[] participantWriters,
                                                                                 PsiXmlElementWriter inferredInteractionWriter,
                                                                                 PsiXmlPublicationWriter publicationWriter,
                                                                                 PsiXmlVariableNameWriter<CvTerm> openCvWriter) {
        switch (version){
            case v3_0_0:
                switch (xmlType){
                    case compact:
                        PsiXmlElementWriter<Set<Feature>> bindingFeaturesWriter = createBindingFeaturesWriter(streamWriter,  objectIndex);
                        PsiXmlCausalRelationshipWriter relationshipWriter = createCausalRelationshipWriter(streamWriter,false,  objectIndex,
                                openCvWriter);
                        PsiXmlElementWriter<Preassembly> preassemblyWriter = createPreassemblyWriter(streamWriter, false, objectIndex, interactionTypeWriter,
                                attributeWriter, publicationWriter);
                        PsiXmlElementWriter<Allostery> allosteryWriter = createAllosteryWriter(streamWriter, false, objectIndex, interactionTypeWriter,
                                attributeWriter, publicationWriter);
                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlNamedModelledInteractionWriter modelledWriter2 =
                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlNamedModelledInteractionWriter(streamWriter, objectIndex);

                        modelledWriter2.setAliasWriter(aliasWriter);
                        modelledWriter2.setAttributeWriter(attributeWriter);
                        modelledWriter2.setXrefWriter(primaryRefWriter);
                        modelledWriter2.setInteractionTypeWriter(interactionTypeWriter);
                        modelledWriter2.setConfidenceWriter(confidenceWriters[1]);
                        modelledWriter2.setInferredInteractionWriter(inferredInteractionWriter);
                        modelledWriter2.setParticipantWriter(participantWriters[1]);
                        modelledWriter2.setChecksumWriter(checksumWriter);
                        modelledWriter2.setExperimentWriter(experimentWriter);
                        modelledWriter2.setParameterWriter(parameterWriters[1]);
                        modelledWriter2.setBindingFeaturesWriter(bindingFeaturesWriter);
                        modelledWriter2.setAllosteryWriter(allosteryWriter);
                        modelledWriter2.setPreAssemblyWriter(preassemblyWriter);
                        modelledWriter2.setCausalRelationshipWriter(relationshipWriter);

                        switch (complexType){
                            case binary:
                                switch (interactionCategory){
                                    case modelled:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlNamedModelledBinaryInteractionWriter modelledWriter4 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlNamedModelledBinaryInteractionWriter(streamWriter, objectIndex);
                                        modelledWriter4.setAliasWriter(aliasWriter);
                                        modelledWriter4.setAttributeWriter(attributeWriter);
                                        modelledWriter4.setXrefWriter(primaryRefWriter);
                                        modelledWriter4.setInteractionTypeWriter(interactionTypeWriter);
                                        modelledWriter4.setConfidenceWriter(confidenceWriters[1]);
                                        modelledWriter4.setInferredInteractionWriter(inferredInteractionWriter);
                                        modelledWriter4.setParticipantWriter(participantWriters[1]);
                                        modelledWriter4.setChecksumWriter(checksumWriter);
                                        modelledWriter4.setExperimentWriter(experimentWriter);
                                        modelledWriter4.setParameterWriter(parameterWriters[1]);
                                        modelledWriter4.setBindingFeaturesWriter(bindingFeaturesWriter);
                                        modelledWriter4.setAllosteryWriter(allosteryWriter);
                                        modelledWriter4.setPreAssemblyWriter(preassemblyWriter);
                                        modelledWriter4.setCausalRelationshipWriter(relationshipWriter);

                                        return new PsiXmlInteractionWriter[]{modelledWriter4, modelledWriter2};
                                    case complex:
                                        throw new IllegalArgumentException("Cannot create a XML complex writer for binary interactions");
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlNamedBinaryInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlNamedBinaryInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);
                                        writer2.setVariableParameterValueSetWriter(createVariableParameterValueSetWriter(streamWriter, objectIndex));
                                        writer2.setCausalRelationshipWriter(relationshipWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter2};
                                }
                            default:
                                switch (interactionCategory){
                                    case modelled:
                                        return new PsiXmlInteractionWriter[]{modelledWriter2, modelledWriter2};
                                    case complex:
                                        return new PsiXmlInteractionWriter[]{modelledWriter2, modelledWriter2};
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlNamedInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlNamedInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);
                                        writer2.setVariableParameterValueSetWriter(createVariableParameterValueSetWriter(streamWriter, objectIndex));
                                        writer2.setCausalRelationshipWriter(relationshipWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter2};
                                }
                        }

                    default:
                        bindingFeaturesWriter = createBindingFeaturesWriter(streamWriter,  objectIndex);
                        relationshipWriter = createCausalRelationshipWriter(streamWriter,false,  objectIndex,
                                openCvWriter);
                        preassemblyWriter = createPreassemblyWriter(streamWriter, false, objectIndex, interactionTypeWriter,
                                attributeWriter, publicationWriter);
                        allosteryWriter = createAllosteryWriter(streamWriter, false, objectIndex, interactionTypeWriter,
                                attributeWriter, publicationWriter);
                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlNamedModelledInteractionWriter modelledWriter3 =
                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlNamedModelledInteractionWriter(streamWriter, objectIndex);

                        modelledWriter3.setAliasWriter(aliasWriter);
                        modelledWriter3.setAttributeWriter(attributeWriter);
                        modelledWriter3.setXrefWriter(primaryRefWriter);
                        modelledWriter3.setInteractionTypeWriter(interactionTypeWriter);
                        modelledWriter3.setConfidenceWriter(confidenceWriters[1]);
                        modelledWriter3.setInferredInteractionWriter(inferredInteractionWriter);
                        modelledWriter3.setParticipantWriter(participantWriters[1]);
                        modelledWriter3.setChecksumWriter(checksumWriter);
                        modelledWriter3.setExperimentWriter(experimentWriter);
                        modelledWriter3.setParameterWriter(parameterWriters[1]);
                        modelledWriter3.setBindingFeaturesWriter(bindingFeaturesWriter);
                        modelledWriter3.setAllosteryWriter(allosteryWriter);
                        modelledWriter3.setPreAssemblyWriter(preassemblyWriter);
                        modelledWriter3.setCausalRelationshipWriter(relationshipWriter);

                        switch (complexType){
                            case binary:
                                switch (interactionCategory){
                                    case modelled:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlNamedModelledBinaryInteractionWriter modelledWriter4 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlNamedModelledBinaryInteractionWriter(streamWriter, objectIndex);
                                        modelledWriter4.setAliasWriter(aliasWriter);
                                        modelledWriter4.setAttributeWriter(attributeWriter);
                                        modelledWriter4.setXrefWriter(primaryRefWriter);
                                        modelledWriter4.setInteractionTypeWriter(interactionTypeWriter);
                                        modelledWriter4.setConfidenceWriter(confidenceWriters[1]);
                                        modelledWriter4.setInferredInteractionWriter(inferredInteractionWriter);
                                        modelledWriter4.setParticipantWriter(participantWriters[1]);
                                        modelledWriter4.setChecksumWriter(checksumWriter);
                                        modelledWriter4.setExperimentWriter(experimentWriter);
                                        modelledWriter4.setParameterWriter(parameterWriters[1]);
                                        modelledWriter4.setBindingFeaturesWriter(bindingFeaturesWriter);
                                        modelledWriter4.setAllosteryWriter(allosteryWriter);
                                        modelledWriter4.setPreAssemblyWriter(preassemblyWriter);
                                        modelledWriter4.setCausalRelationshipWriter(relationshipWriter);

                                        return new PsiXmlInteractionWriter[]{modelledWriter4, modelledWriter3};
                                    case complex:
                                        throw new IllegalArgumentException("Cannot create a XML complex writer for binary interactions");
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlNamedBinaryInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlNamedBinaryInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);
                                        writer2.setVariableParameterValueSetWriter(createVariableParameterValueSetWriter(streamWriter, objectIndex));
                                        writer2.setCausalRelationshipWriter(relationshipWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter3};
                                }
                            default:
                                switch (interactionCategory){
                                    case modelled:
                                        return new PsiXmlInteractionWriter[]{modelledWriter3, modelledWriter3};
                                    case complex:
                                        return new PsiXmlInteractionWriter[]{modelledWriter3, modelledWriter3};
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlNamedInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlNamedInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);
                                        writer2.setVariableParameterValueSetWriter(createVariableParameterValueSetWriter(streamWriter, objectIndex));
                                        writer2.setCausalRelationshipWriter(relationshipWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter3};
                                }
                        }
                }

            default:

                switch (xmlType){
                    case compact:
                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlNamedModelledInteractionWriter modelledWriter2 =
                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlNamedModelledInteractionWriter(streamWriter, objectIndex);
                        modelledWriter2.setAliasWriter(aliasWriter);
                        modelledWriter2.setAttributeWriter(attributeWriter);
                        modelledWriter2.setXrefWriter(primaryRefWriter);
                        modelledWriter2.setInteractionTypeWriter(interactionTypeWriter);
                        modelledWriter2.setConfidenceWriter(confidenceWriters[1]);
                        modelledWriter2.setInferredInteractionWriter(inferredInteractionWriter);
                        modelledWriter2.setParticipantWriter(participantWriters[1]);
                        modelledWriter2.setChecksumWriter(checksumWriter);
                        modelledWriter2.setExperimentWriter(experimentWriter);
                        modelledWriter2.setParameterWriter(parameterWriters[1]);

                        switch (complexType){
                            case binary:
                                switch (interactionCategory){
                                    case modelled:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlNamedModelledBinaryInteractionWriter modelledWriter4 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlNamedModelledBinaryInteractionWriter(streamWriter, objectIndex);
                                        modelledWriter4.setAliasWriter(aliasWriter);
                                        modelledWriter4.setAttributeWriter(attributeWriter);
                                        modelledWriter4.setXrefWriter(primaryRefWriter);
                                        modelledWriter4.setInteractionTypeWriter(interactionTypeWriter);
                                        modelledWriter4.setConfidenceWriter(confidenceWriters[1]);
                                        modelledWriter4.setInferredInteractionWriter(inferredInteractionWriter);
                                        modelledWriter4.setParticipantWriter(participantWriters[1]);
                                        modelledWriter4.setChecksumWriter(checksumWriter);
                                        modelledWriter4.setExperimentWriter(experimentWriter);
                                        modelledWriter4.setParameterWriter(parameterWriters[1]);

                                        return new PsiXmlInteractionWriter[]{modelledWriter4, modelledWriter2};
                                    case complex:
                                        throw new IllegalArgumentException("Cannot create a XML complex writer for binary interactions");
                                    case basic:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlNamedBinaryInteractionWriter writer3 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlNamedBinaryInteractionWriter(streamWriter, objectIndex);
                                        writer3.setAttributeWriter(attributeWriter);
                                        writer3.setXrefWriter(primaryRefWriter);
                                        writer3.setInteractionTypeWriter(interactionTypeWriter);
                                        writer3.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer3.setParticipantWriter(participantWriters[0]);
                                        writer3.setChecksumWriter(checksumWriter);
                                        writer3.setExperimentWriter(experimentWriter);

                                        return new PsiXmlInteractionWriter[]{writer3, modelledWriter2};
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlNamedBinaryInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlNamedBinaryInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter2};
                                }
                            default:
                                switch (interactionCategory){
                                    case modelled:
                                        return new PsiXmlInteractionWriter[]{modelledWriter2, modelledWriter2};
                                    case complex:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlComplexWriter complexWriter =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlComplexWriter(streamWriter, objectIndex);
                                        complexWriter.setAliasWriter(aliasWriter);
                                        complexWriter.setAttributeWriter(attributeWriter);
                                        complexWriter.setXrefWriter(primaryRefWriter);
                                        complexWriter.setInteractionTypeWriter(interactionTypeWriter);
                                        complexWriter.setConfidenceWriter(confidenceWriters[1]);
                                        complexWriter.setInferredInteractionWriter(inferredInteractionWriter);
                                        complexWriter.setParticipantWriter(participantWriters[1]);
                                        complexWriter.setChecksumWriter(checksumWriter);
                                        complexWriter.setExperimentWriter(experimentWriter);
                                        complexWriter.setParameterWriter(parameterWriters[1]);
                                        return new PsiXmlInteractionWriter[]{complexWriter, modelledWriter2};
                                    case basic:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlNamedInteractionWriter writer3 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlNamedInteractionWriter(streamWriter, objectIndex);
                                        writer3.setAttributeWriter(attributeWriter);
                                        writer3.setXrefWriter(primaryRefWriter);
                                        writer3.setInteractionTypeWriter(interactionTypeWriter);
                                        writer3.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer3.setParticipantWriter(participantWriters[0]);
                                        writer3.setChecksumWriter(checksumWriter);
                                        writer3.setExperimentWriter(experimentWriter);

                                        return new PsiXmlInteractionWriter[]{writer3, modelledWriter2};
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlNamedInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlNamedInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter2};
                                }
                        }

                    default:
                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlNamedModelledInteractionWriter modelledWriter3 =
                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlNamedModelledInteractionWriter(streamWriter, objectIndex);
                        modelledWriter3.setAliasWriter(aliasWriter);
                        modelledWriter3.setAttributeWriter(attributeWriter);
                        modelledWriter3.setXrefWriter(primaryRefWriter);
                        modelledWriter3.setInteractionTypeWriter(interactionTypeWriter);
                        modelledWriter3.setConfidenceWriter(confidenceWriters[1]);
                        modelledWriter3.setInferredInteractionWriter(inferredInteractionWriter);
                        modelledWriter3.setParticipantWriter(participantWriters[1]);
                        modelledWriter3.setChecksumWriter(checksumWriter);
                        modelledWriter3.setExperimentWriter(experimentWriter);
                        modelledWriter3.setParameterWriter(parameterWriters[1]);

                        switch (complexType){
                            case binary:
                                switch (interactionCategory){
                                    case modelled:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlNamedModelledBinaryInteractionWriter modelledWriter4 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlNamedModelledBinaryInteractionWriter(streamWriter, objectIndex);
                                        modelledWriter4.setAliasWriter(aliasWriter);
                                        modelledWriter4.setAttributeWriter(attributeWriter);
                                        modelledWriter4.setXrefWriter(primaryRefWriter);
                                        modelledWriter4.setInteractionTypeWriter(interactionTypeWriter);
                                        modelledWriter4.setConfidenceWriter(confidenceWriters[1]);
                                        modelledWriter4.setInferredInteractionWriter(inferredInteractionWriter);
                                        modelledWriter4.setParticipantWriter(participantWriters[1]);
                                        modelledWriter4.setChecksumWriter(checksumWriter);
                                        modelledWriter4.setExperimentWriter(experimentWriter);
                                        modelledWriter4.setParameterWriter(parameterWriters[1]);

                                        return new PsiXmlInteractionWriter[]{modelledWriter4, modelledWriter3};
                                    case complex:
                                        throw new IllegalArgumentException("Cannot create a XML complex writer for binary interactions");
                                    case basic:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlNamedBinaryInteractionWriter writer3 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlNamedBinaryInteractionWriter(streamWriter, objectIndex);
                                        writer3.setAttributeWriter(attributeWriter);
                                        writer3.setXrefWriter(primaryRefWriter);
                                        writer3.setInteractionTypeWriter(interactionTypeWriter);
                                        writer3.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer3.setParticipantWriter(participantWriters[0]);
                                        writer3.setChecksumWriter(checksumWriter);
                                        writer3.setExperimentWriter(experimentWriter);

                                        return new PsiXmlInteractionWriter[]{writer3, modelledWriter3};
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlNamedBinaryInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlNamedBinaryInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter3};
                                }
                            default:
                                switch (interactionCategory){
                                    case modelled:
                                        return new PsiXmlInteractionWriter[]{modelledWriter3, modelledWriter3};
                                    case complex:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlComplexWriter complexWriter =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlComplexWriter(streamWriter, objectIndex);
                                        complexWriter.setAliasWriter(aliasWriter);
                                        complexWriter.setAttributeWriter(attributeWriter);
                                        complexWriter.setXrefWriter(primaryRefWriter);
                                        complexWriter.setInteractionTypeWriter(interactionTypeWriter);
                                        complexWriter.setConfidenceWriter(confidenceWriters[1]);
                                        complexWriter.setInferredInteractionWriter(inferredInteractionWriter);
                                        complexWriter.setParticipantWriter(participantWriters[1]);
                                        complexWriter.setChecksumWriter(checksumWriter);
                                        complexWriter.setExperimentWriter(experimentWriter);
                                        complexWriter.setParameterWriter(parameterWriters[1]);
                                        return new PsiXmlInteractionWriter[]{complexWriter, modelledWriter3};
                                    case basic:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlNamedInteractionWriter writer3 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlNamedInteractionWriter(streamWriter, objectIndex);
                                        writer3.setAttributeWriter(attributeWriter);
                                        writer3.setXrefWriter(primaryRefWriter);
                                        writer3.setInteractionTypeWriter(interactionTypeWriter);
                                        writer3.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer3.setParticipantWriter(participantWriters[0]);
                                        writer3.setChecksumWriter(checksumWriter);
                                        writer3.setExperimentWriter(experimentWriter);

                                        return new PsiXmlInteractionWriter[]{writer3, modelledWriter3};
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlNamedInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlNamedInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter3};
                                }
                        }
                }
        }
    }

    public static PsiXmlInteractionWriter[] createExtendedPsiXmlInteractionWriters(XMLStreamWriter streamWriter, PsiXmlObjectCache objectIndex,
                                                                                    PsiXmlVersion version, PsiXmlType xmlType,
                                                                                    InteractionCategory interactionCategory, ComplexType complexType,
                                                                                    PsiXmlElementWriter<Alias> aliasWriter,
                                                                                    PsiXmlElementWriter<Annotation> attributeWriter,
                                                                                    PsiXmlXrefWriter primaryRefWriter,
                                                                                    PsiXmlElementWriter[] confidenceWriters,
                                                                                    PsiXmlElementWriter<Checksum> checksumWriter,
                                                                                    PsiXmlVariableNameWriter<CvTerm> interactionTypeWriter,
                                                                                    PsiXmlExperimentWriter experimentWriter,
                                                                                    PsiXmlElementWriter<String> availabilityWriter,
                                                                                    PsiXmlParameterWriter[] parameterWriters,
                                                                                    PsiXmlParticipantWriter[] participantWriters,
                                                                                    PsiXmlElementWriter inferredInteractionWriter,
                                                                                    PsiXmlPublicationWriter publicationWriter,
                                                                                    PsiXmlVariableNameWriter<CvTerm> openCvWriter) {
        PsiXmlElementWriter<InferredInteraction> extendedInferredInteractionWriter = createExtendedInferredInteractionWriter(streamWriter,
                objectIndex);

        switch (version){
            case v3_0_0:
                switch (xmlType){
                    case compact:
                        PsiXmlElementWriter<Set<Feature>> bindingFeaturesWriter = createBindingFeaturesWriter(streamWriter,  objectIndex);
                        PsiXmlCausalRelationshipWriter relationshipWriter = createCausalRelationshipWriter(streamWriter,false,  objectIndex,
                                openCvWriter);
                        PsiXmlElementWriter<Preassembly> preassemblyWriter = createPreassemblyWriter(streamWriter, false, objectIndex, interactionTypeWriter,
                                attributeWriter, publicationWriter);
                        PsiXmlElementWriter<Allostery> allosteryWriter = createAllosteryWriter(streamWriter, false, objectIndex, interactionTypeWriter,
                                attributeWriter, publicationWriter);
                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml30.XmlModelledInteractionWriter modelledWriter2 =
                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml30.XmlModelledInteractionWriter(streamWriter, objectIndex);

                        modelledWriter2.setAliasWriter(aliasWriter);
                        modelledWriter2.setAttributeWriter(attributeWriter);
                        modelledWriter2.setXrefWriter(primaryRefWriter);
                        modelledWriter2.setInteractionTypeWriter(interactionTypeWriter);
                        modelledWriter2.setConfidenceWriter(confidenceWriters[1]);
                        modelledWriter2.setInferredInteractionWriter(inferredInteractionWriter);
                        modelledWriter2.setParticipantWriter(participantWriters[1]);
                        modelledWriter2.setChecksumWriter(checksumWriter);
                        modelledWriter2.setExperimentWriter(experimentWriter);
                        modelledWriter2.setParameterWriter(parameterWriters[1]);
                        modelledWriter2.setBindingFeaturesWriter(bindingFeaturesWriter);
                        modelledWriter2.setAllosteryWriter(allosteryWriter);
                        modelledWriter2.setPreAssemblyWriter(preassemblyWriter);
                        modelledWriter2.setCausalRelationshipWriter(relationshipWriter);

                        switch (complexType){
                            case binary:
                                switch (interactionCategory){
                                    case modelled:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml30.XmlModelledBinaryInteractionWriter modelledWriter4 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml30.XmlModelledBinaryInteractionWriter(streamWriter, objectIndex);
                                        modelledWriter4.setAliasWriter(aliasWriter);
                                        modelledWriter4.setAttributeWriter(attributeWriter);
                                        modelledWriter4.setXrefWriter(primaryRefWriter);
                                        modelledWriter4.setInteractionTypeWriter(interactionTypeWriter);
                                        modelledWriter4.setConfidenceWriter(confidenceWriters[1]);
                                        modelledWriter4.setInferredInteractionWriter(inferredInteractionWriter);
                                        modelledWriter4.setParticipantWriter(participantWriters[1]);
                                        modelledWriter4.setChecksumWriter(checksumWriter);
                                        modelledWriter4.setExperimentWriter(experimentWriter);
                                        modelledWriter4.setParameterWriter(parameterWriters[1]);
                                        modelledWriter4.setBindingFeaturesWriter(bindingFeaturesWriter);
                                        modelledWriter4.setAllosteryWriter(allosteryWriter);
                                        modelledWriter4.setPreAssemblyWriter(preassemblyWriter);
                                        modelledWriter4.setCausalRelationshipWriter(relationshipWriter);

                                        return new PsiXmlInteractionWriter[]{modelledWriter4, modelledWriter2};
                                    case complex:
                                        throw new IllegalArgumentException("Cannot create a XML complex writer for binary interactions");
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml30.XmlBinaryInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml30.XmlBinaryInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);
                                        writer2.setVariableParameterValueSetWriter(createVariableParameterValueSetWriter(streamWriter, objectIndex));
                                        writer2.setCausalRelationshipWriter(relationshipWriter);
                                        writer2.setXmlInferredInteractionWriter(extendedInferredInteractionWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter2};
                                }
                            default:
                                switch (interactionCategory){
                                    case modelled:
                                        return new PsiXmlInteractionWriter[]{modelledWriter2, modelledWriter2};
                                    case complex:
                                        return new PsiXmlInteractionWriter[]{modelledWriter2, modelledWriter2};
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml30.XmlInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml30.XmlInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);
                                        writer2.setVariableParameterValueSetWriter(createVariableParameterValueSetWriter(streamWriter, objectIndex));
                                        writer2.setCausalRelationshipWriter(relationshipWriter);
                                        writer2.setXmlInferredInteractionWriter(extendedInferredInteractionWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter2};
                                }
                        }

                    default:
                        bindingFeaturesWriter = createBindingFeaturesWriter(streamWriter,  objectIndex);
                        relationshipWriter = createCausalRelationshipWriter(streamWriter,false,  objectIndex,
                                openCvWriter);
                        preassemblyWriter = createPreassemblyWriter(streamWriter, false, objectIndex, interactionTypeWriter,
                                attributeWriter, publicationWriter);
                        allosteryWriter = createAllosteryWriter(streamWriter, false, objectIndex, interactionTypeWriter,
                                attributeWriter, publicationWriter);
                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml30.XmlModelledInteractionWriter modelledWriter3 =
                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml30.XmlModelledInteractionWriter(streamWriter, objectIndex);

                        modelledWriter3.setAliasWriter(aliasWriter);
                        modelledWriter3.setAttributeWriter(attributeWriter);
                        modelledWriter3.setXrefWriter(primaryRefWriter);
                        modelledWriter3.setInteractionTypeWriter(interactionTypeWriter);
                        modelledWriter3.setConfidenceWriter(confidenceWriters[1]);
                        modelledWriter3.setInferredInteractionWriter(inferredInteractionWriter);
                        modelledWriter3.setParticipantWriter(participantWriters[1]);
                        modelledWriter3.setChecksumWriter(checksumWriter);
                        modelledWriter3.setExperimentWriter(experimentWriter);
                        modelledWriter3.setParameterWriter(parameterWriters[1]);
                        modelledWriter3.setBindingFeaturesWriter(bindingFeaturesWriter);
                        modelledWriter3.setAllosteryWriter(allosteryWriter);
                        modelledWriter3.setPreAssemblyWriter(preassemblyWriter);
                        modelledWriter3.setCausalRelationshipWriter(relationshipWriter);

                        switch (complexType){
                            case binary:
                                switch (interactionCategory){
                                    case modelled:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml30.XmlModelledBinaryInteractionWriter modelledWriter4 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml30.XmlModelledBinaryInteractionWriter(streamWriter, objectIndex);
                                        modelledWriter4.setAliasWriter(aliasWriter);
                                        modelledWriter4.setAttributeWriter(attributeWriter);
                                        modelledWriter4.setXrefWriter(primaryRefWriter);
                                        modelledWriter4.setInteractionTypeWriter(interactionTypeWriter);
                                        modelledWriter4.setConfidenceWriter(confidenceWriters[1]);
                                        modelledWriter4.setInferredInteractionWriter(inferredInteractionWriter);
                                        modelledWriter4.setParticipantWriter(participantWriters[1]);
                                        modelledWriter4.setChecksumWriter(checksumWriter);
                                        modelledWriter4.setExperimentWriter(experimentWriter);
                                        modelledWriter4.setParameterWriter(parameterWriters[1]);
                                        modelledWriter4.setBindingFeaturesWriter(bindingFeaturesWriter);
                                        modelledWriter4.setAllosteryWriter(allosteryWriter);
                                        modelledWriter4.setPreAssemblyWriter(preassemblyWriter);
                                        modelledWriter4.setCausalRelationshipWriter(relationshipWriter);

                                        return new PsiXmlInteractionWriter[]{modelledWriter4, modelledWriter3};
                                    case complex:
                                        throw new IllegalArgumentException("Cannot create a XML complex writer for binary interactions");
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml30.XmlBinaryInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml30.XmlBinaryInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);
                                        writer2.setVariableParameterValueSetWriter(createVariableParameterValueSetWriter(streamWriter, objectIndex));
                                        writer2.setCausalRelationshipWriter(relationshipWriter);
                                        writer2.setXmlInferredInteractionWriter(extendedInferredInteractionWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter3};
                                }
                            default:
                                switch (interactionCategory){
                                    case modelled:
                                        return new PsiXmlInteractionWriter[]{modelledWriter3, modelledWriter3};
                                    case complex:
                                        return new PsiXmlInteractionWriter[]{modelledWriter3, modelledWriter3};
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml30.XmlInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml30.XmlInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);
                                        writer2.setVariableParameterValueSetWriter(createVariableParameterValueSetWriter(streamWriter, objectIndex));
                                        writer2.setCausalRelationshipWriter(relationshipWriter);
                                        writer2.setXmlInferredInteractionWriter(extendedInferredInteractionWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter3};
                                }
                        }
                }

            default:

                switch (xmlType){
                    case compact:
                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlModelledInteractionWriter modelledWriter2 =
                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlModelledInteractionWriter(streamWriter, objectIndex);
                        modelledWriter2.setAliasWriter(aliasWriter);
                        modelledWriter2.setAttributeWriter(attributeWriter);
                        modelledWriter2.setXrefWriter(primaryRefWriter);
                        modelledWriter2.setInteractionTypeWriter(interactionTypeWriter);
                        modelledWriter2.setConfidenceWriter(confidenceWriters[1]);
                        modelledWriter2.setInferredInteractionWriter(inferredInteractionWriter);
                        modelledWriter2.setParticipantWriter(participantWriters[1]);
                        modelledWriter2.setChecksumWriter(checksumWriter);
                        modelledWriter2.setExperimentWriter(experimentWriter);
                        modelledWriter2.setParameterWriter(parameterWriters[1]);
                        modelledWriter2.setXmlInferredInteractionWriter(extendedInferredInteractionWriter);

                        switch (complexType){
                            case binary:
                                switch (interactionCategory){
                                    case modelled:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlModelledBinaryInteractionWriter modelledWriter4 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlModelledBinaryInteractionWriter(streamWriter, objectIndex);
                                        modelledWriter4.setAliasWriter(aliasWriter);
                                        modelledWriter4.setAttributeWriter(attributeWriter);
                                        modelledWriter4.setXrefWriter(primaryRefWriter);
                                        modelledWriter4.setInteractionTypeWriter(interactionTypeWriter);
                                        modelledWriter4.setConfidenceWriter(confidenceWriters[1]);
                                        modelledWriter4.setInferredInteractionWriter(inferredInteractionWriter);
                                        modelledWriter4.setParticipantWriter(participantWriters[1]);
                                        modelledWriter4.setChecksumWriter(checksumWriter);
                                        modelledWriter4.setExperimentWriter(experimentWriter);
                                        modelledWriter4.setParameterWriter(parameterWriters[1]);
                                        modelledWriter4.setXmlInferredInteractionWriter(extendedInferredInteractionWriter);

                                        return new PsiXmlInteractionWriter[]{modelledWriter4, modelledWriter2};
                                    case complex:
                                        throw new IllegalArgumentException("Cannot create a XML complex writer for binary interactions");
                                    case basic:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlBasicBinaryInteractionWriter writer3 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlBasicBinaryInteractionWriter(streamWriter, objectIndex);
                                        writer3.setAttributeWriter(attributeWriter);
                                        writer3.setXrefWriter(primaryRefWriter);
                                        writer3.setInteractionTypeWriter(interactionTypeWriter);
                                        writer3.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer3.setParticipantWriter(participantWriters[0]);
                                        writer3.setChecksumWriter(checksumWriter);
                                        writer3.setExperimentWriter(experimentWriter);
                                        writer3.setXmlInferredInteractionWriter(extendedInferredInteractionWriter);

                                        return new PsiXmlInteractionWriter[]{writer3, modelledWriter2};
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlBinaryInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlBinaryInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);
                                        writer2.setXmlInferredInteractionWriter(extendedInferredInteractionWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter2};
                                }
                            default:
                                switch (interactionCategory){
                                    case modelled:
                                        return new PsiXmlInteractionWriter[]{modelledWriter2, modelledWriter2};
                                    case complex:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlComplexWriter complexWriter =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlComplexWriter(streamWriter, objectIndex);
                                        complexWriter.setAliasWriter(aliasWriter);
                                        complexWriter.setAttributeWriter(attributeWriter);
                                        complexWriter.setXrefWriter(primaryRefWriter);
                                        complexWriter.setInteractionTypeWriter(interactionTypeWriter);
                                        complexWriter.setConfidenceWriter(confidenceWriters[1]);
                                        complexWriter.setInferredInteractionWriter(inferredInteractionWriter);
                                        complexWriter.setParticipantWriter(participantWriters[1]);
                                        complexWriter.setChecksumWriter(checksumWriter);
                                        complexWriter.setExperimentWriter(experimentWriter);
                                        complexWriter.setParameterWriter(parameterWriters[1]);
                                        return new PsiXmlInteractionWriter[]{complexWriter, modelledWriter2};
                                    case basic:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlBasicInteractionWriter writer3 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlBasicInteractionWriter(streamWriter, objectIndex);
                                        writer3.setAttributeWriter(attributeWriter);
                                        writer3.setXrefWriter(primaryRefWriter);
                                        writer3.setInteractionTypeWriter(interactionTypeWriter);
                                        writer3.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer3.setParticipantWriter(participantWriters[0]);
                                        writer3.setChecksumWriter(checksumWriter);
                                        writer3.setExperimentWriter(experimentWriter);
                                        writer3.setXmlInferredInteractionWriter(extendedInferredInteractionWriter);

                                        return new PsiXmlInteractionWriter[]{writer3, modelledWriter2};
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);
                                        writer2.setXmlInferredInteractionWriter(extendedInferredInteractionWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter2};
                                }
                        }

                    default:
                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlModelledInteractionWriter modelledWriter3 =
                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlModelledInteractionWriter(streamWriter, objectIndex);
                        modelledWriter3.setAliasWriter(aliasWriter);
                        modelledWriter3.setAttributeWriter(attributeWriter);
                        modelledWriter3.setXrefWriter(primaryRefWriter);
                        modelledWriter3.setInteractionTypeWriter(interactionTypeWriter);
                        modelledWriter3.setConfidenceWriter(confidenceWriters[1]);
                        modelledWriter3.setInferredInteractionWriter(inferredInteractionWriter);
                        modelledWriter3.setParticipantWriter(participantWriters[1]);
                        modelledWriter3.setChecksumWriter(checksumWriter);
                        modelledWriter3.setExperimentWriter(experimentWriter);
                        modelledWriter3.setParameterWriter(parameterWriters[1]);
                        modelledWriter3.setXmlInferredInteractionWriter(extendedInferredInteractionWriter);

                        switch (complexType){
                            case binary:
                                switch (interactionCategory){
                                    case modelled:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlModelledBinaryInteractionWriter modelledWriter4 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlModelledBinaryInteractionWriter(streamWriter, objectIndex);
                                        modelledWriter4.setAliasWriter(aliasWriter);
                                        modelledWriter4.setAttributeWriter(attributeWriter);
                                        modelledWriter4.setXrefWriter(primaryRefWriter);
                                        modelledWriter4.setInteractionTypeWriter(interactionTypeWriter);
                                        modelledWriter4.setConfidenceWriter(confidenceWriters[1]);
                                        modelledWriter4.setInferredInteractionWriter(inferredInteractionWriter);
                                        modelledWriter4.setParticipantWriter(participantWriters[1]);
                                        modelledWriter4.setChecksumWriter(checksumWriter);
                                        modelledWriter4.setExperimentWriter(experimentWriter);
                                        modelledWriter4.setParameterWriter(parameterWriters[1]);
                                        modelledWriter4.setXmlInferredInteractionWriter(extendedInferredInteractionWriter);

                                        return new PsiXmlInteractionWriter[]{modelledWriter4, modelledWriter3};
                                    case complex:
                                        throw new IllegalArgumentException("Cannot create a XML complex writer for binary interactions");
                                    case basic:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlBasicBinaryInteractionWriter writer3 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlBasicBinaryInteractionWriter(streamWriter, objectIndex);
                                        writer3.setAttributeWriter(attributeWriter);
                                        writer3.setXrefWriter(primaryRefWriter);
                                        writer3.setInteractionTypeWriter(interactionTypeWriter);
                                        writer3.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer3.setParticipantWriter(participantWriters[0]);
                                        writer3.setChecksumWriter(checksumWriter);
                                        writer3.setExperimentWriter(experimentWriter);
                                        writer3.setXmlInferredInteractionWriter(extendedInferredInteractionWriter);

                                        return new PsiXmlInteractionWriter[]{writer3, modelledWriter3};
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlBinaryInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlBinaryInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);
                                        writer2.setXmlInferredInteractionWriter(extendedInferredInteractionWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter3};
                                }
                            default:
                                switch (interactionCategory){
                                    case modelled:
                                        return new PsiXmlInteractionWriter[]{modelledWriter3, modelledWriter3};
                                    case complex:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlComplexWriter complexWriter =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlComplexWriter(streamWriter, objectIndex);
                                        complexWriter.setAliasWriter(aliasWriter);
                                        complexWriter.setAttributeWriter(attributeWriter);
                                        complexWriter.setXrefWriter(primaryRefWriter);
                                        complexWriter.setInteractionTypeWriter(interactionTypeWriter);
                                        complexWriter.setConfidenceWriter(confidenceWriters[1]);
                                        complexWriter.setInferredInteractionWriter(inferredInteractionWriter);
                                        complexWriter.setParticipantWriter(participantWriters[1]);
                                        complexWriter.setChecksumWriter(checksumWriter);
                                        complexWriter.setExperimentWriter(experimentWriter);
                                        complexWriter.setParameterWriter(parameterWriters[1]);
                                        return new PsiXmlInteractionWriter[]{complexWriter, modelledWriter3};
                                    case basic:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlBasicInteractionWriter writer3 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlBasicInteractionWriter(streamWriter, objectIndex);
                                        writer3.setAttributeWriter(attributeWriter);
                                        writer3.setXrefWriter(primaryRefWriter);
                                        writer3.setInteractionTypeWriter(interactionTypeWriter);
                                        writer3.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer3.setParticipantWriter(participantWriters[0]);
                                        writer3.setChecksumWriter(checksumWriter);
                                        writer3.setExperimentWriter(experimentWriter);
                                        writer3.setXmlInferredInteractionWriter(extendedInferredInteractionWriter);

                                        return new PsiXmlInteractionWriter[]{writer3, modelledWriter3};
                                    default:
                                        psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlInteractionEvidenceWriter writer2 =
                                                new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlInteractionEvidenceWriter(streamWriter, objectIndex);
                                        writer2.setAttributeWriter(attributeWriter);
                                        writer2.setXrefWriter(primaryRefWriter);
                                        writer2.setInteractionTypeWriter(interactionTypeWriter);
                                        writer2.setConfidenceWriter(confidenceWriters[0]);
                                        writer2.setInferredInteractionWriter(inferredInteractionWriter);
                                        writer2.setParticipantWriter(participantWriters[0]);
                                        writer2.setChecksumWriter(checksumWriter);
                                        writer2.setExperimentWriter(experimentWriter);
                                        writer2.setParameterWriter(parameterWriters[0]);
                                        writer2.setAvailabilityWriter(availabilityWriter);
                                        writer2.setXmlInferredInteractionWriter(extendedInferredInteractionWriter);

                                        return new PsiXmlInteractionWriter[]{writer2, modelledWriter3};
                                }
                        }
                }
        }
    }

    public static PsiXmlElementWriter<Alias> createAliasWriter(XMLStreamWriter streamWriter){
        return new XmlAliasWriter(streamWriter);
    }

    public static PsiXmlElementWriter<Annotation> createAnnotationWriter(XMLStreamWriter streamWriter){
        return new XmlAnnotationWriter(streamWriter);
    }

    public static PsiXmlXrefWriter createXrefWriter(XMLStreamWriter streamWriter, boolean extended,
                                                    PsiXmlElementWriter<Annotation> annotationWriter){
        PsiXmlXrefWriter writer;
        if (extended){
            writer = new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlDbXrefWriter(streamWriter);
            ((psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlDbXrefWriter)writer).setAnnotationWriter(annotationWriter);
        }
        else{
            writer = new XmlDbXrefWriter(streamWriter);
        }
        return writer;
    }

    public static PsiXmlPublicationWriter createPublicationWriter(XMLStreamWriter streamWriter, boolean extended,
                                                                  PsiXmlElementWriter<Annotation> attributeWriter, PsiXmlXrefWriter primaryRefWriter,
                                                                  PsiXmlVersion version) {
        PsiXmlPublicationWriter publicationWriter;
        if (extended){
            switch (version){
                case v3_0_0:
                    publicationWriter = new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlPublicationWriter(streamWriter);
                    ((psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlPublicationWriter)publicationWriter)
                            .setAttributeWriter(attributeWriter);
                    ((psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlPublicationWriter)publicationWriter)
                            .setXrefWriter(primaryRefWriter);
                    break;
                default:
                    publicationWriter = new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlPublicationWriter(streamWriter);
                    ((psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlPublicationWriter)publicationWriter)
                            .setAttributeWriter(attributeWriter);
                    ((psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlPublicationWriter)publicationWriter)
                            .setXrefWriter(primaryRefWriter);
                    break;
            }
        }
        else{
            switch (version){
                case v3_0_0:
                    publicationWriter = new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlPublicationWriter(streamWriter);
                    ((psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlPublicationWriter)publicationWriter)
                            .setAttributeWriter(attributeWriter);
                    ((psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlPublicationWriter)publicationWriter)
                            .setXrefWriter(primaryRefWriter);
                    break;
                default:
                    publicationWriter = new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlPublicationWriter(streamWriter);
                    ((psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlPublicationWriter)publicationWriter)
                            .setAttributeWriter(attributeWriter);
                    ((psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlPublicationWriter)publicationWriter)
                            .setXrefWriter(primaryRefWriter);
                    break;
            }
        }
        return publicationWriter;
    }

    public static PsiXmlVariableNameWriter<CvTerm> createOpenCvWriter(XMLStreamWriter streamWriter, boolean extended,
                                                                      PsiXmlElementWriter<Alias> aliasWriter, PsiXmlElementWriter<Annotation> attributeWriter,
                                                                      PsiXmlXrefWriter primaryRefWriter) {
        if (extended){
            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlOpenCvTermWriter cellTypeWriter =
                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlOpenCvTermWriter(streamWriter);
            cellTypeWriter.setAttributeWriter(attributeWriter);
            cellTypeWriter.setAliasWriter(aliasWriter);
            cellTypeWriter.setXrefWriter(primaryRefWriter);
            return cellTypeWriter;
        }
        else{
            XmlOpenCvTermWriter cellTypeWriter = new XmlOpenCvTermWriter(streamWriter);
            cellTypeWriter.setAttributeWriter(attributeWriter);
            cellTypeWriter.setAliasWriter(aliasWriter);
            cellTypeWriter.setXrefWriter(primaryRefWriter);
            return cellTypeWriter;
        }
    }

    public static PsiXmlVariableNameWriter<CvTerm> createExperimentalCvWriter(XMLStreamWriter streamWriter, boolean extended,
                                                                              PsiXmlObjectCache objectIndex,
                                                                              PsiXmlElementWriter<Alias> aliasWriter,
                                                                              PsiXmlXrefWriter primaryRefWriter) {
        if (extended){
            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlExperimentalCvTermWriter cellTypeWriter =
                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlExperimentalCvTermWriter(streamWriter, objectIndex);
            cellTypeWriter.setAliasWriter(aliasWriter);
            cellTypeWriter.setXrefWriter(primaryRefWriter);
            return cellTypeWriter;
        }
        else{
            XmlCvTermWriter cellTypeWriter = new XmlCvTermWriter(streamWriter);
            cellTypeWriter.setAliasWriter(aliasWriter);
            cellTypeWriter.setXrefWriter(primaryRefWriter);
            return cellTypeWriter;
        }
    }

    public static PsiXmlVariableNameWriter<CvTerm> createCvWriter(XMLStreamWriter streamWriter, boolean extended,
                                                                  PsiXmlElementWriter<Alias> aliasWriter, PsiXmlXrefWriter primaryRefWriter) {
        if (extended){
            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlCvTermWriter tissueWriter =
                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlCvTermWriter(streamWriter);
            tissueWriter.setAliasWriter(aliasWriter);
            tissueWriter.setXrefWriter(primaryRefWriter);
            return tissueWriter;
        }
        else{
            XmlCvTermWriter tissueWriter = new XmlCvTermWriter(streamWriter);
            tissueWriter.setAliasWriter(aliasWriter);
            tissueWriter.setXrefWriter(primaryRefWriter);
            return tissueWriter;
        }
    }

    public static PsiXmlElementWriter<Organism> createHostOrganismWriter(XMLStreamWriter streamWriter, boolean extended,
                                                                         PsiXmlObjectCache objectIndex, PsiXmlElementWriter<Alias> aliasWriter,
                                                                         PsiXmlElementWriter<Annotation> annotationWriter,
                                                                         PsiXmlXrefWriter xrefWriter,
                                                                         PsiXmlVariableNameWriter<CvTerm> openCvWriter) {
        if (extended){
            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlHostOrganismWriter hostWriter =
                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlHostOrganismWriter(streamWriter, objectIndex);
            hostWriter.setAliasWriter(aliasWriter);
            hostWriter.setCvWriter(openCvWriter);
            return hostWriter;
        }
        else{
            XmlHostOrganismWriter hostWriter = new XmlHostOrganismWriter(streamWriter);
            hostWriter.setAliasWriter(aliasWriter);
            hostWriter.setCvWriter(openCvWriter);
            return hostWriter;
        }
    }

    public static PsiXmlElementWriter<Organism> createOrganismWriter(XMLStreamWriter streamWriter, boolean extended,
                                                                     PsiXmlElementWriter<Alias> aliasWriter,
                                                                     PsiXmlElementWriter<Annotation> annotationWriter,
                                                                     PsiXmlXrefWriter xrefWriter,
                                                                     PsiXmlVariableNameWriter<CvTerm> openCvWriter) {
        if (extended){
            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlOrganismWriter hostWriter =
                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlOrganismWriter(streamWriter);
            hostWriter.setAliasWriter(aliasWriter);
            hostWriter.setCvWriter(openCvWriter);
            return hostWriter;
        }
        else{
            XmlOrganismWriter hostWriter = new XmlOrganismWriter(streamWriter);
            hostWriter.setAliasWriter(aliasWriter);
            hostWriter.setCvWriter(openCvWriter);
            return hostWriter;
        }
    }

    public static PsiXmlElementWriter<Checksum> createChecksumWriter(XMLStreamWriter streamWriter) {
        return  new XmlChecksumWriter(streamWriter);
    }

    public static PsiXmlElementWriter<Confidence>[] createConfidenceWriters(XMLStreamWriter streamWriter, boolean extended,
                                                                            PsiXmlObjectCache objectIndex, PsiXmlVersion version,
                                                                            PsiXmlVariableNameWriter<CvTerm> confidenceTypeWriter,
                                                                            PsiXmlPublicationWriter publicationWriter) {
        if (extended){
            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlConfidenceWriter confWriter =
                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlConfidenceWriter(streamWriter, objectIndex);
            confWriter.setTypeWriter(confidenceTypeWriter);
            switch (version){
                case v3_0_0:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlModelledConfidenceWriter modelledConfWriter =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlModelledConfidenceWriter(streamWriter);
                    modelledConfWriter.setTypeWriter(confidenceTypeWriter);
                    modelledConfWriter.setPublicationWriter(publicationWriter);
                    return new PsiXmlElementWriter[]{confWriter, modelledConfWriter};
                default:
                    return new PsiXmlElementWriter[]{confWriter, confWriter};
            }
        }
        else{
            XmlConfidenceWriter confWriter = new XmlConfidenceWriter(streamWriter);
            confWriter.setTypeWriter(confidenceTypeWriter);
            switch (version){
                case v3_0_0:
                    XmlModelledConfidenceWriter modelledConfWriter = new XmlModelledConfidenceWriter(streamWriter);
                    modelledConfWriter.setTypeWriter(confidenceTypeWriter);
                    modelledConfWriter.setPublicationWriter(publicationWriter);
                    return new PsiXmlElementWriter[]{confWriter, modelledConfWriter};
                default:
                    return new PsiXmlElementWriter[]{confWriter, confWriter};
            }
        }
    }

    public static PsiXmlElementWriter<ResultingSequence> createResultingSequenceWriter(XMLStreamWriter streamWriter, boolean extended,
                                                                                       PsiXmlXrefWriter refWriter) {
        if (extended){
            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlResultingSequenceWriter writer =
                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlResultingSequenceWriter(streamWriter);
            writer.setXrefWriter(refWriter);
            return writer;
        }
        else{
            XmlResultingSequenceWriter writer = new XmlResultingSequenceWriter(streamWriter);
            writer.setXrefWriter(refWriter);
            return writer;
        }
    }

    public static PsiXmlElementWriter<CooperativityEvidence> createCooperativityEvidenceWriter(XMLStreamWriter streamWriter, boolean expanded,
                                                                                               PsiXmlVariableNameWriter<CvTerm> cvWriter,
                                                                                               PsiXmlPublicationWriter publicationWriter) {

        if (expanded){
            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlCooperativityEvidenceWriter writer =
                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlCooperativityEvidenceWriter(streamWriter);
            writer.setCvWriter(cvWriter);
            writer.setPublicationWriter(publicationWriter);
            return writer;
        }
        else{
            XmlCooperativityEvidenceWriter writer = new XmlCooperativityEvidenceWriter(streamWriter);
            writer.setCvWriter(cvWriter);
            writer.setPublicationWriter(publicationWriter);
            return writer;
        }
    }

    public static PsiXmlElementWriter<Allostery> createAllosteryWriter(XMLStreamWriter streamWriter, boolean expanded,
                                                                       PsiXmlObjectCache objectIndex, PsiXmlVariableNameWriter<CvTerm> cvWriter,
                                                                       PsiXmlElementWriter<Annotation> attributeWriter,
                                                                       PsiXmlPublicationWriter publicationWriter) {
        if (expanded){
            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlAllosteryWriter writer =
                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlAllosteryWriter(streamWriter, objectIndex);
            writer.setCvWriter(cvWriter);
            writer.setAttributeWriter(attributeWriter);
            writer.setCooperativityEvidenceWriter(createCooperativityEvidenceWriter(streamWriter, expanded, cvWriter, publicationWriter));
            return writer;
        }
        else{
            XmlAllosteryWriter writer = new XmlAllosteryWriter(streamWriter, objectIndex);
            writer.setCvWriter(cvWriter);
            writer.setAttributeWriter(attributeWriter);
            writer.setCooperativityEvidenceWriter(createCooperativityEvidenceWriter(streamWriter, expanded, cvWriter, publicationWriter));
            return writer;
        }
    }

    public static PsiXmlElementWriter<Preassembly> createPreassemblyWriter(XMLStreamWriter streamWriter, boolean extended,
                                                                           PsiXmlObjectCache objectIndex, PsiXmlVariableNameWriter<CvTerm> cvWriter,
                                                                           PsiXmlElementWriter<Annotation> attributeWriter,
                                                                           PsiXmlPublicationWriter publicationWriter) {
        if (extended){
            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlPreAssemblyWriter writer =
                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlPreAssemblyWriter(streamWriter, objectIndex);
            writer.setCvWriter(cvWriter);
            writer.setAttributeWriter(attributeWriter);
            writer.setCooperativityEvidenceWriter(createCooperativityEvidenceWriter(streamWriter, extended, cvWriter, publicationWriter));
            return writer;
        }
        else{
            XmlPreAssemblyWriter writer = new XmlPreAssemblyWriter(streamWriter, objectIndex);
            writer.setCvWriter(cvWriter);
            writer.setAttributeWriter(attributeWriter);
            writer.setCooperativityEvidenceWriter(createCooperativityEvidenceWriter(streamWriter, extended, cvWriter, publicationWriter));
            return writer;
        }
    }

    public static PsiXmlCausalRelationshipWriter createCausalRelationshipWriter(XMLStreamWriter streamWriter, boolean extended,
                                                                                PsiXmlObjectCache objectIndex, PsiXmlVariableNameWriter<CvTerm> cvWriter) {
        if (extended){
            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlCausalRelationshipWriter writer =
                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlCausalRelationshipWriter(streamWriter, objectIndex);
            writer.setCausalStatementWriter(cvWriter);
            return writer;
        }
        else{
            XmlCausalRelationshipWriter writer = new XmlCausalRelationshipWriter(streamWriter, objectIndex);
            writer.setCausalStatementWriter(cvWriter);
            return writer;
        }
    }

    public static PsiXmlElementWriter<VariableParameterValueSet> createVariableParameterValueSetWriter(XMLStreamWriter streamWriter,
                                                                                                       PsiXmlObjectCache objectIndex) {
        return new XmlVariableParameterValueSetWriter(streamWriter, objectIndex);
    }

    public static PsiXmlElementWriter<VariableParameterValue> createVariableParameterValueWriter(XMLStreamWriter streamWriter,
                                                                                                 PsiXmlObjectCache objectIndex) {
        return new XmlVariableParameterValueWriter(streamWriter, objectIndex);
    }

    public static PsiXmlElementWriter<VariableParameter> createVariableParameterWriter(XMLStreamWriter streamWriter, boolean extended,
                                                                                       PsiXmlObjectCache objectIndex, PsiXmlVariableNameWriter<CvTerm> cvWriter) {
        if (extended){
            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlVariableParameterWriter writer =
                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlVariableParameterWriter(streamWriter, objectIndex);
            writer.setUnitWriter(cvWriter);
            writer.setVariableParameterValueWriter(createVariableParameterValueWriter(streamWriter, objectIndex));
            return writer;
        }
        else{
            XmlVariableParameterWriter writer = new XmlVariableParameterWriter(streamWriter, objectIndex);
            writer.setUnitWriter(cvWriter);
            writer.setVariableParameterValueWriter(createVariableParameterValueWriter(streamWriter, objectIndex));
            return writer;
        }
    }

    public static PsiXmlElementWriter<Position>[] createPositionsWriter(XMLStreamWriter streamWriter, boolean extended,
                                                                        PsiXmlVariableNameWriter<CvTerm> statusWriter) {
        if (extended){
            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlBeginPositionWriter startWriter =
                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlBeginPositionWriter(streamWriter);
            startWriter.setStatusWriter(statusWriter);
            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlEndPositionWriter endWriter =
                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlEndPositionWriter(streamWriter);
            endWriter.setStatusWriter(statusWriter);
            return new PsiXmlElementWriter[]{startWriter, endWriter};
        }
        else{
            XmlBeginPositionWriter startWriter = new XmlBeginPositionWriter(streamWriter);
            startWriter.setStatusWriter(statusWriter);
            XmlEndPositionWriter endWriter = new XmlEndPositionWriter(streamWriter);
            endWriter.setStatusWriter(statusWriter);
            return new PsiXmlElementWriter[]{startWriter, endWriter};
        }
    }

    public static PsiXmlElementWriter<Range> createRangeWriter(XMLStreamWriter streamWriter, boolean extended,
                                                               PsiXmlObjectCache objectIndex, PsiXmlVersion version,
                                                               PsiXmlXrefWriter refWriter, PsiXmlVariableNameWriter<CvTerm> statusWriter) {
        PsiXmlElementWriter<Position>[] positionsWriter = createPositionsWriter(streamWriter, extended, statusWriter);

        if (extended){
            switch (version){
                case v3_0_0:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlRangeWriter writer =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlRangeWriter(streamWriter, objectIndex);
                    writer.setResultingSequenceWriter(createResultingSequenceWriter(streamWriter, extended, refWriter));
                    writer.setStartPositionWriter(positionsWriter[0]);
                    writer.setEndPositionWriter(positionsWriter[1]);
                    return writer;
                default:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlRangeWriter writer2 =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlRangeWriter(streamWriter);
                    writer2.setStartPositionWriter(positionsWriter[0]);
                    writer2.setEndPositionWriter(positionsWriter[1]);
                    return writer2;
            }
        }
        else{
            switch (version){
                case v3_0_0:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlRangeWriter writer =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlRangeWriter(streamWriter, objectIndex);
                    writer.setResultingSequenceWriter(createResultingSequenceWriter(streamWriter, extended, refWriter));
                    writer.setStartPositionWriter(positionsWriter[0]);
                    writer.setEndPositionWriter(positionsWriter[1]);
                    return writer;
                default:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlRangeWriter writer2 =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlRangeWriter(streamWriter);
                    writer2.setStartPositionWriter(positionsWriter[0]);
                    writer2.setEndPositionWriter(positionsWriter[1]);
                    return writer2;
            }
        }
    }

    public static PsiXmlParameterWriter[] createParameterWriters(XMLStreamWriter streamWriter, boolean extended,
                                                                          PsiXmlObjectCache objectIndex, PsiXmlVersion version,
                                                                          PsiXmlPublicationWriter publicationWriter) {
        if (extended){
            switch (version){
                case v3_0_0:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlParameterWriter paramWriter =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlParameterWriter(streamWriter, objectIndex);
                    XmlModelledParameterWriter modelledParamWriter = new XmlModelledParameterWriter(streamWriter, objectIndex);
                    modelledParamWriter.setPublicationWriter(publicationWriter);
                    return new PsiXmlParameterWriter[]{paramWriter, modelledParamWriter};
                default:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlParameterWriter paramWriter2 =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlParameterWriter(streamWriter, objectIndex);
                    return new PsiXmlParameterWriter[]{paramWriter2, paramWriter2};
            }
        }
        else{
            switch (version){
                case v3_0_0:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlParameterWriter paramWriter =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlParameterWriter(streamWriter, objectIndex);
                    XmlModelledParameterWriter modelledParamWriter = new XmlModelledParameterWriter(streamWriter, objectIndex);
                    modelledParamWriter.setPublicationWriter(publicationWriter);
                    return new PsiXmlParameterWriter[]{paramWriter, modelledParamWriter};
                default:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlParameterWriter paramWriter2 =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlParameterWriter(streamWriter, objectIndex);
                    return new PsiXmlParameterWriter[]{paramWriter2, paramWriter2};
            }
        }
    }

    public static PsiXmlElementWriter<String> createAvailabilityWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache objectIndex) {
        return new XmlAvailabilityWriter(streamWriter, objectIndex);
    }

    public static PsiXmlElementWriter<Set<Feature>> createInferredInteractionWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache objectIndex) {
        return new XmlInferredInteractionWriter(streamWriter, objectIndex);
    }

    public static PsiXmlElementWriter<InferredInteraction> createExtendedInferredInteractionWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache objectIndex) {
        return new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlInferredInteractionWriter(streamWriter, objectIndex);
    }

    public static PsiXmlElementWriter<Set<Feature>> createBindingFeaturesWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache objectIndex) {
        return new XmlBindingFeaturesWriter(streamWriter, objectIndex);
    }

    public static PsiXmlElementWriter<ExperimentalInteractor> createExperimentalInteractorWriter(XMLStreamWriter streamWriter,
                                                                                                 PsiXmlObjectCache objectIndex,
                                                                                                 PsiXmlType xmlType) {
        switch (xmlType){
            case compact:
                return new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.XmlExperimentalInteractorWriter(streamWriter, objectIndex);
            default:
                return new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.XmlExperimentalInteractorWriter(streamWriter, objectIndex);
        }
    }

    public static PsiXmlSourceWriter createSourceWriter(XMLStreamWriter streamWriter, boolean extended,
                                                             PsiXmlVersion version,
                                                             PsiXmlElementWriter<Alias> aliasWriter, PsiXmlElementWriter<Annotation> attributeWriter,
                                                         PsiXmlXrefWriter primaryRefWriter,
                                                         PsiXmlPublicationWriter publicationWriter) {
        if (extended){
            switch (version){
                case v3_0_0:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlSourceWriter sourceWriter =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlSourceWriter(streamWriter);
                    sourceWriter.setXrefWriter(primaryRefWriter);
                    sourceWriter.setAttributeWriter(attributeWriter);
                    sourceWriter.setAliasWriter(aliasWriter);
                    sourceWriter.setPublicationWriter(publicationWriter);
                    return sourceWriter;
                default:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlSourceWriter sourceWriter2 =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlSourceWriter(streamWriter);
                    sourceWriter2.setXrefWriter(primaryRefWriter);
                    sourceWriter2.setAttributeWriter(attributeWriter);
                    sourceWriter2.setAliasWriter(aliasWriter);
                    sourceWriter2.setPublicationWriter(publicationWriter);
                    return sourceWriter2;
            }
        }
        else{
            switch (version){
                case v3_0_0:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlSourceWriter sourceWriter =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlSourceWriter(streamWriter);
                    sourceWriter.setXrefWriter(primaryRefWriter);
                    sourceWriter.setAttributeWriter(attributeWriter);
                    sourceWriter.setAliasWriter(aliasWriter);
                    sourceWriter.setPublicationWriter(publicationWriter);
                    return sourceWriter;
                default:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlSourceWriter sourceWriter2 =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlSourceWriter(streamWriter);
                    sourceWriter2.setXrefWriter(primaryRefWriter);
                    sourceWriter2.setAttributeWriter(attributeWriter);
                    sourceWriter2.setAliasWriter(aliasWriter);
                    sourceWriter2.setPublicationWriter(publicationWriter);
                    return sourceWriter2;
            }
        }

    }

    public static PsiXmlExperimentWriter createExperimentWriter(XMLStreamWriter streamWriter, boolean extended,
                                                                     PsiXmlObjectCache objectIndex, PsiXmlVersion version,
                                                                     boolean named, PsiXmlElementWriter<Alias> aliasWriter,
                                                                     PsiXmlElementWriter<Annotation> attributeWriter,
                                                                 PsiXmlXrefWriter primaryRefWriter,
                                                                 PsiXmlPublicationWriter publicationWriter,
                                                                 PsiXmlElementWriter<Organism> nonExperimentalHostOrganismWriter,
                                                                 PsiXmlVariableNameWriter<CvTerm> detectionMethodWriter,
                                                                 PsiXmlElementWriter<Confidence> confidenceWriter) {
        if (extended){
            switch (version){
                case v3_0_0:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlExperimentWriter expWriter =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlExperimentWriter(streamWriter, objectIndex);
                    expWriter.setXrefWriter(primaryRefWriter);
                    expWriter.setAttributeWriter(attributeWriter);
                    expWriter.setPublicationWriter(publicationWriter);
                    expWriter.setHostOrganismWriter(nonExperimentalHostOrganismWriter);
                    expWriter.setDetectionMethodWriter(detectionMethodWriter);
                    expWriter.setConfidenceWriter(confidenceWriter);
                    expWriter.setAliasWriter(aliasWriter);
                    return expWriter;
                default:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlExperimentWriter expWriter2 =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlExperimentWriter(streamWriter, objectIndex);
                    expWriter2.setXrefWriter(primaryRefWriter);
                    expWriter2.setAttributeWriter(attributeWriter);
                    expWriter2.setPublicationWriter(publicationWriter);
                    expWriter2.setHostOrganismWriter(nonExperimentalHostOrganismWriter);
                    expWriter2.setDetectionMethodWriter(detectionMethodWriter);
                    expWriter2.setConfidenceWriter(confidenceWriter);
                    expWriter2.setAliasWriter(aliasWriter);
                    return expWriter2;
            }
        }
        else if (named){
            switch (version){
                case v3_0_0:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlNamedExperimentWriter expWriter =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlNamedExperimentWriter(streamWriter, objectIndex);
                    expWriter.setXrefWriter(primaryRefWriter);
                    expWriter.setAttributeWriter(attributeWriter);
                    expWriter.setPublicationWriter(publicationWriter);
                    expWriter.setHostOrganismWriter(nonExperimentalHostOrganismWriter);
                    expWriter.setDetectionMethodWriter(detectionMethodWriter);
                    expWriter.setConfidenceWriter(confidenceWriter);
                    expWriter.setAliasWriter(aliasWriter);
                    return expWriter;
                default:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlNamedExperimentWriter expWriter2 =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlNamedExperimentWriter(streamWriter, objectIndex);
                    expWriter2.setXrefWriter(primaryRefWriter);
                    expWriter2.setAttributeWriter(attributeWriter);
                    expWriter2.setPublicationWriter(publicationWriter);
                    expWriter2.setHostOrganismWriter(nonExperimentalHostOrganismWriter);
                    expWriter2.setDetectionMethodWriter(detectionMethodWriter);
                    expWriter2.setConfidenceWriter(confidenceWriter);
                    expWriter2.setAliasWriter(aliasWriter);
                    return expWriter2;
            }
        }
        else{
            switch (version){
                case v3_0_0:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlExperimentWriter expWriter =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlExperimentWriter(streamWriter, objectIndex);
                    expWriter.setXrefWriter(primaryRefWriter);
                    expWriter.setAttributeWriter(attributeWriter);
                    expWriter.setPublicationWriter(publicationWriter);
                    expWriter.setHostOrganismWriter(nonExperimentalHostOrganismWriter);
                    expWriter.setDetectionMethodWriter(detectionMethodWriter);
                    expWriter.setConfidenceWriter(confidenceWriter);
                    return expWriter;
                default:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlExperimentWriter expWriter2 =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlExperimentWriter(streamWriter, objectIndex);
                    expWriter2.setXrefWriter(primaryRefWriter);
                    expWriter2.setAttributeWriter(attributeWriter);
                    expWriter2.setPublicationWriter(publicationWriter);
                    expWriter2.setHostOrganismWriter(nonExperimentalHostOrganismWriter);
                    expWriter2.setDetectionMethodWriter(detectionMethodWriter);
                    expWriter2.setConfidenceWriter(confidenceWriter);
                    return expWriter2;
            }
        }
    }

    public static PsiXmlElementWriter<Interactor> createInteractorWriter(XMLStreamWriter streamWriter, boolean extended,
                                                                         PsiXmlObjectCache objectIndex, PsiXmlElementWriter<Alias> aliasWriter,
                                                                         PsiXmlElementWriter<Annotation> attributeWriter,
                                                                         PsiXmlXrefWriter primaryRefWriter,
                                                                         PsiXmlVariableNameWriter<CvTerm> interactorTypeWriter,
                                                                         PsiXmlElementWriter<Organism> organismWriter,
                                                                         PsiXmlElementWriter<Checksum> checksumWriter) {
        if (extended){
            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlInteractorWriter interactorWriter =
                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlInteractorWriter(streamWriter, objectIndex);
            interactorWriter.setAliasWriter(aliasWriter);
            interactorWriter.setAttributeWriter(attributeWriter);
            interactorWriter.setXrefWriter(primaryRefWriter);
            interactorWriter.setInteractorTypeWriter(interactorTypeWriter);
            interactorWriter.setOrganismWriter(organismWriter);
            interactorWriter.setChecksumWriter(checksumWriter);
            return interactorWriter;
        }
        else{
            XmlInteractorWriter interactorWriter = new XmlInteractorWriter(streamWriter, objectIndex);
            interactorWriter.setAliasWriter(aliasWriter);
            interactorWriter.setAttributeWriter(attributeWriter);
            interactorWriter.setXrefWriter(primaryRefWriter);
            interactorWriter.setInteractorTypeWriter(interactorTypeWriter);
            interactorWriter.setOrganismWriter(organismWriter);
            interactorWriter.setChecksumWriter(checksumWriter);
            return interactorWriter;
        }
    }

    public static <F extends Feature> PsiXmlElementWriter<F>[] createFeatureWriter(XMLStreamWriter streamWriter, boolean extended,
                                                                          PsiXmlObjectCache objectIndex, PsiXmlVersion version,
                                                                          InteractionCategory category, PsiXmlElementWriter<Alias> aliasWriter,
                                                                          PsiXmlElementWriter<Annotation> attributeWriter,
                                                                          PsiXmlXrefWriter primaryRefWriter,
                                                                          PsiXmlVariableNameWriter<CvTerm> featureTypeWriter,
                                                                          PsiXmlParameterWriter parameterWriter){
        PsiXmlElementWriter<Range> rangeWriter = createRangeWriter(streamWriter, extended, objectIndex, version, primaryRefWriter,
                featureTypeWriter);

        if (extended){
            switch (version){
                case v3_0_0:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlModelledFeatureWriter modelledWriter =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlModelledFeatureWriter(streamWriter, objectIndex);
                    modelledWriter.setAliasWriter(aliasWriter);
                    modelledWriter.setAttributeWriter(attributeWriter);
                    modelledWriter.setFeatureTypeWriter(featureTypeWriter);
                    modelledWriter.setRangeWriter(rangeWriter);
                    modelledWriter.setXrefWriter(primaryRefWriter);

                    switch (category){
                        case modelled:
                            return new PsiXmlElementWriter[]{modelledWriter, modelledWriter};
                        default:
                            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlFeatureEvidenceWriter writer =
                                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlFeatureEvidenceWriter(streamWriter, objectIndex);
                            writer.setAliasWriter(aliasWriter);
                            writer.setAttributeWriter(attributeWriter);
                            writer.setFeatureTypeWriter(featureTypeWriter);
                            writer.setRangeWriter(rangeWriter);
                            writer.setXrefWriter(primaryRefWriter);
                            writer.setParameterWriter(parameterWriter);

                            return new PsiXmlElementWriter[]{writer, modelledWriter};
                    }

                default:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlModelledFeatureWriter modelledWriter2 =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlModelledFeatureWriter(streamWriter, objectIndex);
                    modelledWriter2.setAliasWriter(aliasWriter);
                    modelledWriter2.setAttributeWriter(attributeWriter);
                    modelledWriter2.setFeatureTypeWriter(featureTypeWriter);
                    modelledWriter2.setRangeWriter(rangeWriter);
                    modelledWriter2.setXrefWriter(primaryRefWriter);

                    switch (category){
                        case modelled:
                            return new PsiXmlElementWriter[]{modelledWriter2, modelledWriter2};
                        case basic:
                            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlFeatureWriter writer3 =
                                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlFeatureWriter(streamWriter, objectIndex);
                            writer3.setAliasWriter(aliasWriter);
                            writer3.setAttributeWriter(attributeWriter);
                            writer3.setFeatureTypeWriter(featureTypeWriter);
                            writer3.setRangeWriter(rangeWriter);
                            writer3.setXrefWriter(primaryRefWriter);

                            return new PsiXmlElementWriter[]{writer3, modelledWriter2};
                        default:
                            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlFeatureEvidenceWriter writer2 =
                                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlFeatureEvidenceWriter(streamWriter, objectIndex);
                            writer2.setAliasWriter(aliasWriter);
                            writer2.setAttributeWriter(attributeWriter);
                            writer2.setFeatureTypeWriter(featureTypeWriter);
                            writer2.setRangeWriter(rangeWriter);
                            writer2.setXrefWriter(primaryRefWriter);

                            return new PsiXmlElementWriter[]{writer2, modelledWriter2};
                    }
            }
        }
        else{
            switch (version){
                case v3_0_0:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlModelledFeatureWriter modelledWriter =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlModelledFeatureWriter(streamWriter, objectIndex);
                    modelledWriter.setAliasWriter(aliasWriter);
                    modelledWriter.setAttributeWriter(attributeWriter);
                    modelledWriter.setFeatureTypeWriter(featureTypeWriter);
                    modelledWriter.setRangeWriter(rangeWriter);
                    modelledWriter.setXrefWriter(primaryRefWriter);

                    switch (category){
                        case modelled:
                            return new PsiXmlElementWriter[]{modelledWriter, modelledWriter};
                        default:
                            psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlFeatureEvidenceWriter writer =
                                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlFeatureEvidenceWriter(streamWriter, objectIndex);
                            writer.setAliasWriter(aliasWriter);
                            writer.setAttributeWriter(attributeWriter);
                            writer.setFeatureTypeWriter(featureTypeWriter);
                            writer.setRangeWriter(rangeWriter);
                            writer.setXrefWriter(primaryRefWriter);
                            writer.setParameterWriter(parameterWriter);

                            return new PsiXmlElementWriter[]{writer, modelledWriter};
                    }

                default:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlModelledFeatureWriter modelledWriter2 =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlModelledFeatureWriter(streamWriter, objectIndex);
                    modelledWriter2.setAliasWriter(aliasWriter);
                    modelledWriter2.setAttributeWriter(attributeWriter);
                    modelledWriter2.setFeatureTypeWriter(featureTypeWriter);
                    modelledWriter2.setRangeWriter(rangeWriter);
                    modelledWriter2.setXrefWriter(primaryRefWriter);

                    switch (category){
                        case modelled:
                            return new PsiXmlElementWriter[]{modelledWriter2, modelledWriter2};
                        case basic:
                            psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlFeatureWriter writer3 =
                                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlFeatureWriter(streamWriter, objectIndex);
                            writer3.setAliasWriter(aliasWriter);
                            writer3.setAttributeWriter(attributeWriter);
                            writer3.setFeatureTypeWriter(featureTypeWriter);
                            writer3.setRangeWriter(rangeWriter);
                            writer3.setXrefWriter(primaryRefWriter);

                            return new PsiXmlElementWriter[]{writer3, modelledWriter2};
                        default:
                            psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlFeatureEvidenceWriter writer2 =
                                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlFeatureEvidenceWriter(streamWriter, objectIndex);
                            writer2.setAliasWriter(aliasWriter);
                            writer2.setAttributeWriter(attributeWriter);
                            writer2.setFeatureTypeWriter(featureTypeWriter);
                            writer2.setRangeWriter(rangeWriter);
                            writer2.setXrefWriter(primaryRefWriter);

                            return new PsiXmlElementWriter[]{writer2, modelledWriter2};
                    }
            }
        }
    }

    public static <P extends Participant> PsiXmlParticipantWriter<P>[] createParticipantWriter(XMLStreamWriter streamWriter, boolean extended,
                                                                     PsiXmlObjectCache objectIndex, PsiXmlVersion version,
                                                                     PsiXmlType xmlType,
                                                                     InteractionCategory category, PsiXmlElementWriter<Alias> aliasWriter,
                                                                     PsiXmlElementWriter<Annotation> attributeWriter,
                                                                     PsiXmlXrefWriter primaryRefWriter,
                                                                     PsiXmlElementWriter<Confidence> confidenceWriter,
                                                                     PsiXmlElementWriter<Interactor> interactorWriter,
                                                                     PsiXmlVariableNameWriter<CvTerm> cvWriter,
                                                                     PsiXmlVariableNameWriter<CvTerm> openCvWriter,
                                                                     PsiXmlParameterWriter parameterWriter){

        PsiXmlElementWriter[] featureWriters = createFeatureWriter(streamWriter, extended, objectIndex, version, category, aliasWriter,
                attributeWriter, primaryRefWriter, cvWriter, parameterWriter);
        PsiXmlVariableNameWriter<CvTerm> experimentalCvWriter = createExperimentalCvWriter(streamWriter, extended, objectIndex, aliasWriter,
                primaryRefWriter);
        PsiXmlElementWriter[] candidateWriters = createParticipantCandidateWriter(streamWriter, extended, objectIndex, version, xmlType,
                category, interactorWriter, (PsiXmlElementWriter<ModelledFeature>) featureWriters[1],
                (PsiXmlElementWriter<FeatureEvidence>) featureWriters[0]);


        if (extended){
            PsiXmlElementWriter<ExperimentalInteractor> experimentalInteractorWriter = createExperimentalInteractorWriter(streamWriter, objectIndex,
                    xmlType);

            switch (version) {
                case v3_0_0:
                    switch (xmlType) {
                        case compact:
                            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml30.XmlModelledParticipantWriter modelledWriter2 =
                                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml30.XmlModelledParticipantWriter(streamWriter, objectIndex);
                            modelledWriter2.setAliasWriter(aliasWriter);
                            modelledWriter2.setAttributeWriter(attributeWriter);
                            modelledWriter2.setXrefWriter(primaryRefWriter);
                            modelledWriter2.setFeatureWriter((PsiXmlElementWriter<ModelledFeature>) featureWriters[1]);
                            modelledWriter2.setInteractorWriter(interactorWriter);
                            modelledWriter2.setBiologicalRoleWriter(cvWriter);
                            modelledWriter2.setParticipantCandidateWriter(candidateWriters[1]);

                            switch (category) {
                                case modelled:
                                    return new PsiXmlParticipantWriter[]{modelledWriter2, modelledWriter2};
                                default:
                                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml30.XmlParticipantEvidenceWriter writer2 =
                                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml30.XmlParticipantEvidenceWriter(streamWriter, objectIndex);
                                    writer2.setAliasWriter(aliasWriter);
                                    writer2.setAttributeWriter(attributeWriter);
                                    writer2.setXrefWriter(primaryRefWriter);
                                    writer2.setFeatureWriter((PsiXmlElementWriter<FeatureEvidence>) featureWriters[0]);
                                    writer2.setInteractorWriter(interactorWriter);
                                    writer2.setBiologicalRoleWriter(cvWriter);
                                    writer2.setExperimentalCvWriter(experimentalCvWriter);
                                    writer2.setParameterWriter(parameterWriter);
                                    writer2.setConfidenceWriter(confidenceWriter);
                                    writer2.setHostOrganismWriter(createHostOrganismWriter(streamWriter, extended, objectIndex, aliasWriter,
                                            attributeWriter, primaryRefWriter, openCvWriter));
                                    writer2.setExperimentalInteractorWriter((CompactPsiXmlElementWriter<ExperimentalInteractor>)
                                            experimentalInteractorWriter);
                                    writer2.setParticipantCandidateWriter(candidateWriters[0]);

                                    return new PsiXmlParticipantWriter[]{writer2, modelledWriter2};
                            }
                        default:
                            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml30.XmlModelledParticipantWriter modelledWriter3 =
                                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml30.XmlModelledParticipantWriter(streamWriter, objectIndex);
                            modelledWriter3.setAliasWriter(aliasWriter);
                            modelledWriter3.setAttributeWriter(attributeWriter);
                            modelledWriter3.setXrefWriter(primaryRefWriter);
                            modelledWriter3.setFeatureWriter((PsiXmlElementWriter<ModelledFeature>) featureWriters[1]);
                            modelledWriter3.setInteractorWriter(interactorWriter);
                            modelledWriter3.setBiologicalRoleWriter(cvWriter);
                            modelledWriter3.setParticipantCandidateWriter(candidateWriters[1]);

                            switch (category) {
                                case modelled:
                                    return new PsiXmlParticipantWriter[]{modelledWriter3, modelledWriter3};
                                default:
                                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml30.XmlParticipantEvidenceWriter writer2 =
                                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml30.XmlParticipantEvidenceWriter(streamWriter, objectIndex);
                                    writer2.setAliasWriter(aliasWriter);
                                    writer2.setAttributeWriter(attributeWriter);
                                    writer2.setXrefWriter(primaryRefWriter);
                                    writer2.setFeatureWriter((PsiXmlElementWriter<FeatureEvidence>) featureWriters[0]);
                                    writer2.setInteractorWriter(interactorWriter);
                                    writer2.setBiologicalRoleWriter(cvWriter);
                                    writer2.setExperimentalCvWriter(experimentalCvWriter);
                                    writer2.setParameterWriter(parameterWriter);
                                    writer2.setConfidenceWriter(confidenceWriter);
                                    writer2.setHostOrganismWriter(createHostOrganismWriter(streamWriter, extended, objectIndex, aliasWriter,
                                            attributeWriter, primaryRefWriter, openCvWriter));
                                    writer2.setExperimentalInteractorWriter((ExpandedPsiXmlElementWriter<ExperimentalInteractor>)
                                            experimentalInteractorWriter);
                                    writer2.setParticipantCandidateWriter(candidateWriters[0]);

                                    return new PsiXmlParticipantWriter[]{writer2, modelledWriter3};
                            }
                    }

                default:

                    switch (xmlType) {
                        case compact:
                            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlModelledParticipantWriter modelledWriter2 =
                                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlModelledParticipantWriter(streamWriter, objectIndex);
                            modelledWriter2.setAliasWriter(aliasWriter);
                            modelledWriter2.setAttributeWriter(attributeWriter);
                            modelledWriter2.setXrefWriter(primaryRefWriter);
                            modelledWriter2.setFeatureWriter((PsiXmlElementWriter<ModelledFeature>) featureWriters[1]);
                            modelledWriter2.setInteractorWriter(interactorWriter);
                            modelledWriter2.setBiologicalRoleWriter(cvWriter);

                            switch (category) {
                                case modelled:
                                    return new PsiXmlParticipantWriter[]{modelledWriter2, modelledWriter2};
                                case basic:
                                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlParticipantWriter writer3 =
                                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlParticipantWriter(streamWriter, objectIndex);
                                    writer3.setAliasWriter(aliasWriter);
                                    writer3.setAttributeWriter(attributeWriter);
                                    writer3.setXrefWriter(primaryRefWriter);
                                    writer3.setFeatureWriter((PsiXmlElementWriter<Feature>) featureWriters[0]);
                                    writer3.setInteractorWriter(interactorWriter);
                                    writer3.setBiologicalRoleWriter(cvWriter);

                                    return new PsiXmlParticipantWriter[]{writer3, modelledWriter2};
                                default:
                                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlParticipantEvidenceWriter writer2 =
                                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml25.XmlParticipantEvidenceWriter(streamWriter, objectIndex);
                                    writer2.setAliasWriter(aliasWriter);
                                    writer2.setAttributeWriter(attributeWriter);
                                    writer2.setXrefWriter(primaryRefWriter);
                                    writer2.setFeatureWriter((PsiXmlElementWriter<FeatureEvidence>) featureWriters[0]);
                                    writer2.setInteractorWriter(interactorWriter);
                                    writer2.setBiologicalRoleWriter(cvWriter);
                                    writer2.setExperimentalCvWriter(experimentalCvWriter);
                                    writer2.setParameterWriter(parameterWriter);
                                    writer2.setConfidenceWriter(confidenceWriter);
                                    writer2.setHostOrganismWriter(createHostOrganismWriter(streamWriter, extended, objectIndex, aliasWriter,
                                            attributeWriter, primaryRefWriter, openCvWriter));

                                    return new PsiXmlParticipantWriter[]{writer2, modelledWriter2};
                            }
                        default:
                            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlModelledParticipantWriter modelledWriter3 =
                                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlModelledParticipantWriter(streamWriter, objectIndex);
                            modelledWriter3.setAliasWriter(aliasWriter);
                            modelledWriter3.setAttributeWriter(attributeWriter);
                            modelledWriter3.setXrefWriter(primaryRefWriter);
                            modelledWriter3.setFeatureWriter((PsiXmlElementWriter<ModelledFeature>) featureWriters[1]);
                            modelledWriter3.setInteractorWriter(interactorWriter);
                            modelledWriter3.setBiologicalRoleWriter(cvWriter);

                            switch (category) {
                                case modelled:
                                    return new PsiXmlParticipantWriter[]{modelledWriter3, modelledWriter3};
                                case basic:
                                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlParticipantWriter writer3 =
                                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlParticipantWriter(streamWriter, objectIndex);
                                    writer3.setAliasWriter(aliasWriter);
                                    writer3.setAttributeWriter(attributeWriter);
                                    writer3.setXrefWriter(primaryRefWriter);
                                    writer3.setFeatureWriter((PsiXmlElementWriter<Feature>) featureWriters[0]);
                                    writer3.setInteractorWriter(interactorWriter);
                                    writer3.setBiologicalRoleWriter(cvWriter);

                                    return new PsiXmlParticipantWriter[]{writer3, modelledWriter3};
                                default:
                                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlParticipantEvidenceWriter writer2 =
                                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25.XmlParticipantEvidenceWriter(streamWriter, objectIndex);
                                    writer2.setAliasWriter(aliasWriter);
                                    writer2.setAttributeWriter(attributeWriter);
                                    writer2.setXrefWriter(primaryRefWriter);
                                    writer2.setFeatureWriter((PsiXmlElementWriter<FeatureEvidence>) featureWriters[0]);
                                    writer2.setInteractorWriter(interactorWriter);
                                    writer2.setBiologicalRoleWriter(cvWriter);
                                    writer2.setExperimentalCvWriter(experimentalCvWriter);
                                    writer2.setParameterWriter(parameterWriter);
                                    writer2.setConfidenceWriter(confidenceWriter);
                                    writer2.setHostOrganismWriter(createHostOrganismWriter(streamWriter, extended, objectIndex, aliasWriter,
                                            attributeWriter, primaryRefWriter, openCvWriter));

                                    return new PsiXmlParticipantWriter[]{writer2, modelledWriter3};
                            }
                    }
            }
        }
        else{
            switch (version){
                case v3_0_0:
                    switch (xmlType){
                        case compact:
                            psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlModelledParticipantWriter modelledWriter2 =
                                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlModelledParticipantWriter(streamWriter, objectIndex);
                            modelledWriter2.setAliasWriter(aliasWriter);
                            modelledWriter2.setAttributeWriter(attributeWriter);
                            modelledWriter2.setXrefWriter(primaryRefWriter);
                            modelledWriter2.setFeatureWriter((PsiXmlElementWriter<ModelledFeature>) featureWriters[1]);
                            modelledWriter2.setInteractorWriter(interactorWriter);
                            modelledWriter2.setBiologicalRoleWriter(cvWriter);
                            modelledWriter2.setParticipantCandidateWriter(candidateWriters[1]);

                            switch (category){
                                case modelled:
                                    return new PsiXmlParticipantWriter[]{modelledWriter2, modelledWriter2};
                                default:
                                    psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlParticipantEvidenceWriter writer2 =
                                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlParticipantEvidenceWriter(streamWriter, objectIndex);
                                    writer2.setAliasWriter(aliasWriter);
                                    writer2.setAttributeWriter(attributeWriter);
                                    writer2.setXrefWriter(primaryRefWriter);
                                    writer2.setFeatureWriter((PsiXmlElementWriter<FeatureEvidence>) featureWriters[0]);
                                    writer2.setInteractorWriter(interactorWriter);
                                    writer2.setBiologicalRoleWriter(cvWriter);
                                    writer2.setExperimentalCvWriter(experimentalCvWriter);
                                    writer2.setParameterWriter(parameterWriter);
                                    writer2.setConfidenceWriter(confidenceWriter);
                                    writer2.setHostOrganismWriter(createHostOrganismWriter(streamWriter, extended, objectIndex, aliasWriter,
                                            attributeWriter, primaryRefWriter, openCvWriter));
                                    writer2.setParticipantCandidateWriter(candidateWriters[0]);

                                    return new PsiXmlParticipantWriter[]{writer2, modelledWriter2};
                            }
                        default:
                            psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlModelledParticipantWriter modelledWriter3 =
                                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlModelledParticipantWriter(streamWriter, objectIndex);
                            modelledWriter3.setAliasWriter(aliasWriter);
                            modelledWriter3.setAttributeWriter(attributeWriter);
                            modelledWriter3.setXrefWriter(primaryRefWriter);
                            modelledWriter3.setFeatureWriter((PsiXmlElementWriter<ModelledFeature>) featureWriters[1]);
                            modelledWriter3.setInteractorWriter(interactorWriter);
                            modelledWriter3.setBiologicalRoleWriter(cvWriter);
                            modelledWriter3.setParticipantCandidateWriter(candidateWriters[1]);

                            switch (category){
                                case modelled:
                                    return new PsiXmlParticipantWriter[]{modelledWriter3, modelledWriter3};
                                default:
                                    psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlParticipantEvidenceWriter writer2 =
                                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlParticipantEvidenceWriter(streamWriter, objectIndex);
                                    writer2.setAliasWriter(aliasWriter);
                                    writer2.setAttributeWriter(attributeWriter);
                                    writer2.setXrefWriter(primaryRefWriter);
                                    writer2.setFeatureWriter((PsiXmlElementWriter<FeatureEvidence>) featureWriters[0]);
                                    writer2.setInteractorWriter(interactorWriter);
                                    writer2.setBiologicalRoleWriter(cvWriter);
                                    writer2.setExperimentalCvWriter(experimentalCvWriter);
                                    writer2.setParameterWriter(parameterWriter);
                                    writer2.setConfidenceWriter(confidenceWriter);
                                    writer2.setHostOrganismWriter(createHostOrganismWriter(streamWriter, extended, objectIndex, aliasWriter,
                                            attributeWriter, primaryRefWriter, openCvWriter));
                                    writer2.setParticipantCandidateWriter(candidateWriters[0]);

                                    return new PsiXmlParticipantWriter[]{writer2, modelledWriter3};
                            }
                    }

                default:

                    switch (xmlType){
                        case compact:
                            psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlModelledParticipantWriter modelledWriter2 =
                                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlModelledParticipantWriter(streamWriter, objectIndex);
                            modelledWriter2.setAliasWriter(aliasWriter);
                            modelledWriter2.setAttributeWriter(attributeWriter);
                            modelledWriter2.setXrefWriter(primaryRefWriter);
                            modelledWriter2.setFeatureWriter((PsiXmlElementWriter<ModelledFeature>)featureWriters[1]);
                            modelledWriter2.setInteractorWriter(interactorWriter);
                            modelledWriter2.setBiologicalRoleWriter(cvWriter);

                            switch (category){
                                case modelled:
                                    return new PsiXmlParticipantWriter[]{modelledWriter2, modelledWriter2};
                                case basic:
                                    psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlParticipantWriter writer3 =
                                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlParticipantWriter(streamWriter, objectIndex);
                                    writer3.setAliasWriter(aliasWriter);
                                    writer3.setAttributeWriter(attributeWriter);
                                    writer3.setXrefWriter(primaryRefWriter);
                                    writer3.setFeatureWriter((PsiXmlElementWriter<Feature>)featureWriters[0]);
                                    writer3.setInteractorWriter(interactorWriter);
                                    writer3.setBiologicalRoleWriter(cvWriter);

                                    return new PsiXmlParticipantWriter[]{writer3, modelledWriter2};
                                default:
                                    psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlParticipantEvidenceWriter writer2 =
                                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml25.XmlParticipantEvidenceWriter(streamWriter, objectIndex);
                                    writer2.setAliasWriter(aliasWriter);
                                    writer2.setAttributeWriter(attributeWriter);
                                    writer2.setXrefWriter(primaryRefWriter);
                                    writer2.setFeatureWriter((PsiXmlElementWriter<FeatureEvidence>) featureWriters[0]);
                                    writer2.setInteractorWriter(interactorWriter);
                                    writer2.setBiologicalRoleWriter(cvWriter);
                                    writer2.setExperimentalCvWriter(experimentalCvWriter);
                                    writer2.setParameterWriter(parameterWriter);
                                    writer2.setConfidenceWriter(confidenceWriter);
                                    writer2.setHostOrganismWriter(createHostOrganismWriter(streamWriter, extended, objectIndex, aliasWriter,
                                            attributeWriter, primaryRefWriter, openCvWriter));

                                    return new PsiXmlParticipantWriter[]{writer2, modelledWriter2};
                            }
                        default:
                            psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlModelledParticipantWriter modelledWriter3 =
                                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlModelledParticipantWriter(streamWriter, objectIndex);
                            modelledWriter3.setAliasWriter(aliasWriter);
                            modelledWriter3.setAttributeWriter(attributeWriter);
                            modelledWriter3.setXrefWriter(primaryRefWriter);
                            modelledWriter3.setFeatureWriter((PsiXmlElementWriter<ModelledFeature>)featureWriters[1]);
                            modelledWriter3.setInteractorWriter(interactorWriter);
                            modelledWriter3.setBiologicalRoleWriter(cvWriter);

                            switch (category){
                                case modelled:
                                    return new PsiXmlParticipantWriter[]{modelledWriter3, modelledWriter3};
                                case basic:
                                    psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlParticipantWriter writer3 =
                                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlParticipantWriter(streamWriter, objectIndex);
                                    writer3.setAliasWriter(aliasWriter);
                                    writer3.setAttributeWriter(attributeWriter);
                                    writer3.setXrefWriter(primaryRefWriter);
                                    writer3.setFeatureWriter((PsiXmlElementWriter<Feature>)featureWriters[0]);
                                    writer3.setInteractorWriter(interactorWriter);
                                    writer3.setBiologicalRoleWriter(cvWriter);

                                    return new PsiXmlParticipantWriter[]{writer3, modelledWriter3};
                                default:
                                    psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlParticipantEvidenceWriter writer2 =
                                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlParticipantEvidenceWriter(streamWriter, objectIndex);
                                    writer2.setAliasWriter(aliasWriter);
                                    writer2.setAttributeWriter(attributeWriter);
                                    writer2.setXrefWriter(primaryRefWriter);
                                    writer2.setFeatureWriter((PsiXmlElementWriter<FeatureEvidence>) featureWriters[0]);
                                    writer2.setInteractorWriter(interactorWriter);
                                    writer2.setBiologicalRoleWriter(cvWriter);
                                    writer2.setExperimentalCvWriter(experimentalCvWriter);
                                    writer2.setParameterWriter(parameterWriter);
                                    writer2.setConfidenceWriter(confidenceWriter);
                                    writer2.setHostOrganismWriter(createHostOrganismWriter(streamWriter, extended, objectIndex, aliasWriter,
                                            attributeWriter, primaryRefWriter, openCvWriter));

                                    return new PsiXmlParticipantWriter[]{writer2, modelledWriter3};
                            }
                    }
            }
        }
    }

    public static <P extends ParticipantCandidate> PsiXmlElementWriter<P>[] createParticipantCandidateWriter(XMLStreamWriter streamWriter, boolean extended,
                                                                                               PsiXmlObjectCache objectIndex, PsiXmlVersion version,
                                                                                               PsiXmlType xmlType,
                                                                                               InteractionCategory category, PsiXmlElementWriter<Interactor> interactorWriter,
                                                                                               PsiXmlElementWriter<ModelledFeature> modelledFeatureWriter,
                                                                                               PsiXmlElementWriter<FeatureEvidence> featureEvidenceWriter){

        if (extended){
            switch (version) {
                case v3_0_0:
                    switch (xmlType) {
                        case compact:
                            XmlModelledParticipantCandidateWriter modelledWriter2 =
                                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml30.XmlModelledParticipantCandidateWriter(streamWriter, objectIndex);
                            modelledWriter2.setFeatureWriter(modelledFeatureWriter);
                            modelledWriter2.setInteractorWriter(interactorWriter);

                            switch (category) {
                                case modelled:
                                    return new PsiXmlElementWriter[]{modelledWriter2, modelledWriter2};
                                default:
                                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml30.XmlExperimentalParticipantCandidateWriter writer2 =
                                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.compact.xml30.XmlExperimentalParticipantCandidateWriter(streamWriter, objectIndex);
                                    writer2.setFeatureWriter(featureEvidenceWriter);
                                    writer2.setInteractorWriter(interactorWriter);

                                    return new PsiXmlElementWriter[]{writer2, modelledWriter2};
                            }
                        default:
                            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml30.XmlModelledParticipantCandidateWriter modelledWriter3 =
                                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml30.XmlModelledParticipantCandidateWriter(streamWriter, objectIndex);
                            modelledWriter3.setFeatureWriter(modelledFeatureWriter);
                            modelledWriter3.setInteractorWriter(interactorWriter);

                            switch (category) {
                                case modelled:
                                    return new PsiXmlElementWriter[]{modelledWriter3, modelledWriter3};
                                default:
                                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml30.XmlExperimentalParticipantCandidateWriter writer2 =
                                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml30.XmlExperimentalParticipantCandidateWriter(streamWriter, objectIndex);

                                    writer2.setFeatureWriter(featureEvidenceWriter);
                                    writer2.setInteractorWriter(interactorWriter);

                                    return new PsiXmlElementWriter[]{writer2, modelledWriter3};
                            }
                    }

                default:
                    return null;
            }
        }
        else{
            switch (version){
                case v3_0_0:
                    switch (xmlType){
                        case compact:
                            psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlModelledParticipantCandidateWriter modelledWriter2 =
                                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlModelledParticipantCandidateWriter(streamWriter, objectIndex);
                            modelledWriter2.setFeatureWriter(modelledFeatureWriter);
                            modelledWriter2.setInteractorWriter(interactorWriter);

                            switch (category){
                                case modelled:
                                    return new PsiXmlElementWriter[]{modelledWriter2, modelledWriter2};
                                default:
                                    psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlExperimentalParticipantCandidateWriter writer2 =
                                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.compact.xml30.XmlExperimentalParticipantCandidateWriter(streamWriter, objectIndex);
                                    writer2.setFeatureWriter(featureEvidenceWriter);
                                    writer2.setInteractorWriter(interactorWriter);

                                    return new PsiXmlElementWriter[]{writer2, modelledWriter2};
                            }
                        default:
                            psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlModelledParticipantCandidateWriter modelledWriter3 =
                                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlModelledParticipantCandidateWriter(streamWriter, objectIndex);

                            modelledWriter3.setFeatureWriter(modelledFeatureWriter);
                            modelledWriter3.setInteractorWriter(interactorWriter);

                            switch (category){
                                case modelled:
                                    return new PsiXmlElementWriter[]{modelledWriter3, modelledWriter3};
                                default:
                                    psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlExperimentalParticipantCandidateWriter writer2 =
                                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml30.XmlExperimentalParticipantCandidateWriter(streamWriter, objectIndex);
                                    writer2.setFeatureWriter(featureEvidenceWriter);
                                    writer2.setInteractorWriter(interactorWriter);

                                    return new PsiXmlElementWriter[]{writer2, modelledWriter3};
                            }
                    }

                default:

                    return null;
            }
        }
    }
}
