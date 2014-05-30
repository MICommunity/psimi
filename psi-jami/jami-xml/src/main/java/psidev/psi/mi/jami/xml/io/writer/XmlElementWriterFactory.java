package psidev.psi.mi.jami.xml.io.writer;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXmlType;
import psidev.psi.mi.jami.xml.PsiXmlVersion;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.*;

import javax.xml.stream.XMLStreamWriter;

/**
 * Factory to initialise PSI-XML element writers depending on the version and the interaction object category
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/04/14</pre>
 */

public class XmlElementWriterFactory {

    private static final XmlElementWriterFactory instance = new XmlElementWriterFactory();

    private XmlElementWriterFactory(){
        // nothing to do here
    }

    public static XmlElementWriterFactory getInstance() {
        return instance;
    }

    /*public static PsiXmlInteractionWriter[] createInteractionWritersFor(PsiXmlVersion version, PsiXmlType type, InteractionCategory interactionCategory,
                                                                        ComplexType complexType, boolean extended, boolean named){
         switch (version){
             case v3_0_0:
                 createInteractionWriter30For(type, interactionCategory, complexType, extended, named);
                 break;
             default:
                 createInteractionWriter25For(type, interactionCategory, complexType, extended, named);
                 break;
         }
    }*/

    public static void createInteractionWriter25For(PsiXmlType type, InteractionCategory interactionCategory, ComplexType complexType,
                                                    boolean extended, boolean named) {

    }

    public static void createInteractionWriter30For(PsiXmlType type, InteractionCategory interactionCategory, ComplexType complexType,
                                                    boolean extended, boolean named) {

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
                                                                         PsiXmlVariableNameWriter<CvTerm> cellTypeWriter) {
        if (extended){
            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlHostOrganismWriter hostWriter =
                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlHostOrganismWriter(streamWriter, objectIndex);
            hostWriter.setAliasWriter(aliasWriter);
            hostWriter.setCvWriter(cellTypeWriter);
            return hostWriter;
        }
        else{
            XmlHostOrganismWriter hostWriter = new XmlHostOrganismWriter(streamWriter);
            hostWriter.setAliasWriter(aliasWriter);
            hostWriter.setCvWriter(cellTypeWriter);
            return hostWriter;
        }
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

    public static PsiXmlElementWriter<Preassembly> createPreassemblyWriter(XMLStreamWriter streamWriter, boolean expanded,
                                                                             PsiXmlObjectCache objectIndex, PsiXmlVariableNameWriter<CvTerm> cvWriter,
                                                                             PsiXmlElementWriter<Annotation> attributeWriter,
                                                                             PsiXmlPublicationWriter publicationWriter) {
        if (expanded){
            psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlPreAssemblyWriter writer =
                    new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlPreAssemblyWriter(streamWriter, objectIndex);
            writer.setCvWriter(cvWriter);
            writer.setAttributeWriter(attributeWriter);
            writer.setCooperativityEvidenceWriter(createCooperativityEvidenceWriter(streamWriter, expanded, cvWriter, publicationWriter));
            return writer;
        }
        else{
            XmlPreAssemblyWriter writer = new XmlPreAssemblyWriter(streamWriter, objectIndex);
            writer.setCvWriter(cvWriter);
            writer.setAttributeWriter(attributeWriter);
            writer.setCooperativityEvidenceWriter(createCooperativityEvidenceWriter(streamWriter, expanded, cvWriter, publicationWriter));
            return writer;
        }
    }

    public static PsiXmlCausalRelationshipWriter createCausalRelationshipWriter(XMLStreamWriter streamWriter, boolean expanded,
                                                                                PsiXmlObjectCache objectIndex, PsiXmlVariableNameWriter<CvTerm> cvWriter) {
        if (expanded){
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

    public static PsiXmlElementWriter<Parameter>[] createParameterWriters(XMLStreamWriter streamWriter, boolean extended,
                                                                                 PsiXmlObjectCache objectIndex, PsiXmlVersion version,
                                                                                 PsiXmlPublicationWriter publicationWriter) {
        if (extended){
            switch (version){
                case v3_0_0:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlParameterWriter paramWriter =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30.XmlParameterWriter(streamWriter, objectIndex);
                    XmlModelledParameterWriter modelledParamWriter = new XmlModelledParameterWriter(streamWriter, objectIndex);
                    modelledParamWriter.setPublicationWriter(publicationWriter);
                    return new PsiXmlElementWriter[]{paramWriter, modelledParamWriter};
                default:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlParameterWriter paramWriter2 =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlParameterWriter(streamWriter, objectIndex);
                    return new PsiXmlElementWriter[]{paramWriter2, paramWriter2};
            }
        }
        else{
            switch (version){
                case v3_0_0:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlParameterWriter paramWriter =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlParameterWriter(streamWriter, objectIndex);
                    XmlModelledParameterWriter modelledParamWriter = new XmlModelledParameterWriter(streamWriter, objectIndex);
                    modelledParamWriter.setPublicationWriter(publicationWriter);
                    return new PsiXmlElementWriter[]{paramWriter, modelledParamWriter};
                default:
                    psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlParameterWriter paramWriter2 =
                            new psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlParameterWriter(streamWriter, objectIndex);
                    return new PsiXmlElementWriter[]{paramWriter2, paramWriter2};
            }
        }
    }

    /*public static void initialiseElementWriters(){
        // basic sub writers
        // aliases
        PsiXmlElementWriter<Alias> aliasWriter = instantiateAliasWriter();
        // attributes
        PsiXmlElementWriter<Annotation> attributeWriter = instantiateAnnotationWriter();
        // xref
        PsiXmlXrefWriter xrefWriter = instantiateXrefWriter(attributeWriter);
        // publication
        PsiXmlPublicationWriter publicationWriter = instantiatePublicationWriter(attributeWriter, xrefWriter);
        // open cv
        PsiXmlVariableNameWriter<CvTerm> openCvWriter = instantiateOpenCvWriter(aliasWriter, attributeWriter, xrefWriter);
        // cv
        PsiXmlVariableNameWriter<CvTerm> cvWriter = instantiateCvWriter(aliasWriter, xrefWriter);
        // experiment Host Organism
        PsiXmlElementWriter<Organism> hostOrganismWriter = instantiateHostOrganismWriter(aliasWriter, openCvWriter);
        // confidence
        PsiXmlElementWriter<Confidence> confidenceWriter = instantiateConfidenceWriter(openCvWriter);
        // organism writer
        PsiXmlElementWriter<Organism> organismWriter = instantiateOrganismWriter(aliasWriter, openCvWriter);
        // checksum writer
        PsiXmlElementWriter<Checksum> checksumWriter = instantiateChecksumWriter();
        // interactor writer
        PsiXmlElementWriter<Interactor> interactorWriter = instantiateInteractorWriter(aliasWriter, attributeWriter, xrefWriter,
                cvWriter, organismWriter, checksumWriter);
        // begin position writer
        PsiXmlElementWriter<Position> beginWriter = instantiateBeginPositionWriter(cvWriter);
        // end position writer
        PsiXmlElementWriter<Position> endWriter = instantiateEndPositionWriter(cvWriter);
        // range writer
        PsiXmlElementWriter<Range> rangeWriter = instantiateRangeWriter(beginWriter, endWriter);
        // feature writer
        PsiXmlElementWriter featureWriter = instantiateFeatureWriter(aliasWriter, attributeWriter, xrefWriter,
                cvWriter, rangeWriter);
        // parameter writer
        PsiXmlParameterWriter parameterWriter = instantiateParameterWriter();
        // participant writer
        PsiXmlParticipantWriter participantWriter = instantiateParticipantWriter(aliasWriter,
                attributeWriter, xrefWriter, confidenceWriter, interactorWriter, cvWriter,
                featureWriter, parameterWriter, hostOrganismWriter);
        // experiment Writer
        PsiXmlExperimentWriter experimentWriter = instantiateExperimentWriter(aliasWriter, attributeWriter, xrefWriter,
                publicationWriter, hostOrganismWriter, cvWriter, confidenceWriter);
        // modelled feature writer
        PsiXmlElementWriter<ModelledFeature> modelledFeatureWriter = instantiateModelledFeatureWriter(aliasWriter, attributeWriter, xrefWriter,
                cvWriter, rangeWriter, featureWriter);
        // modelled participant writer
        PsiXmlParticipantWriter<ModelledParticipant> modelledParticipantWriter = instantiateModelledParticipantWriter(aliasWriter, attributeWriter,
                xrefWriter, interactorWriter, cvWriter, modelledFeatureWriter,participantWriter);
        // availability writer
        PsiXmlElementWriter<String> availabilityWriter = instantiateAvailabilityWriter();
        // inferredInteraction writer
        PsiXmlElementWriter inferredInteractionWriter = instantiateInferredInteractionWriter();
        // initialise source
        setSourceWriter(instantiateSourceWriter(aliasWriter, attributeWriter, xrefWriter, publicationWriter));
        // initialise optional writers
        initialiseOptionalWriters(experimentWriter, availabilityWriter, interactorWriter);
        // initialise interaction
        setInteractionWriter(instantiateInteractionWriter(aliasWriter, attributeWriter, xrefWriter,
                confidenceWriter, checksumWriter, parameterWriter, participantWriter, cvWriter, experimentWriter, availabilityWriter,
                inferredInteractionWriter));
        // initialise complex
        setComplexWriter(instantiateComplexWriter(aliasWriter, attributeWriter, xrefWriter,
                confidenceWriter, checksumWriter, parameterWriter, cvWriter, experimentWriter, modelledParticipantWriter,
                inferredInteractionWriter, getInteractionWriter()));
        // initialise annotation writer
        setAnnotationsWriter(attributeWriter);
    } */
}
