package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlModelledInteractionWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Set;

/**
 * Compact XML 2.5 writer for a modelled binary interaction (ignore experimental details).
 * It will write cooperative effects as attributes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */

public class CompactXmlModelledBinaryInteractionWriter extends AbstractXmlModelledInteractionWriter<ModelledBinaryInteraction, ModelledParticipant> implements CompactPsiXmlElementWriter<ModelledBinaryInteraction> {

    public CompactXmlModelledBinaryInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex, new CompactXmlModelledParticipantWriter(writer, objectIndex));
    }

    public CompactXmlModelledBinaryInteractionWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex,
                                                     PsiXmlXrefWriter primaryRefWriter, PsiXmlXrefWriter secondaryRefWriter,
                                                     PsiXmlExperimentWriter experimentWriter, PsiXmlParticipantWriter<ModelledParticipant> participantWriter,
                                                     PsiXmlElementWriter<Set<Feature>> inferredInteractionWriter, PsiXmlElementWriter<CvTerm> interactionTypeWriter,
                                                     PsiXmlElementWriter<Confidence> confidenceWriter, PsiXmlParameterWriter parameterWriter,
                                                     PsiXmlElementWriter<Annotation> attributeWriter, PsiXmlElementWriter<Checksum> checksumWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, experimentWriter,
                participantWriter != null ? participantWriter : new CompactXmlModelledParticipantWriter(writer, objectIndex), inferredInteractionWriter,
                interactionTypeWriter, confidenceWriter, parameterWriter, attributeWriter, checksumWriter);
    }

    @Override
    protected void writeExperiments(ModelledBinaryInteraction object) throws XMLStreamException {
        super.writeExperiments(object);
        writeExperimentRef();
    }

    @Override
    protected void writeAttributes(ModelledBinaryInteraction object) throws XMLStreamException {
        // write attributes
        if (!object.getAnnotations().isEmpty()){
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            for (Object ann : object.getAnnotations()){
                getAttributeWriter().write((Annotation)ann);
            }
            for (Object c : object.getChecksums()){
                getChecksumWriter().write((Checksum)c);
            }
            // can only write the FIRST cooperative effect
            if (!object.getCooperativeEffects().isEmpty()){
                writeCooperativeEffect(object);
            }
            // write complex expansion if any
            if (object.getComplexExpansion() != null){
                super.writeAttribute(object.getComplexExpansion().getShortName(), object.getComplexExpansion().getMIIdentifier());
            }
            // write end attributeList
            getStreamWriter().writeEndElement();
        }
        // write checksum
        else if (!object.getChecksums().isEmpty()){
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            for (Object c : object.getChecksums()){
                getChecksumWriter().write((Checksum)c);
            }
            // can only write the FIRST cooperative effect
            if (!object.getCooperativeEffects().isEmpty()){
                writeCooperativeEffect(object);
            }
            // write complex expansion if any
            if (object.getComplexExpansion() != null){
                super.writeAttribute(object.getComplexExpansion().getShortName(), object.getComplexExpansion().getMIIdentifier());
            }
            // write end attributeList
            getStreamWriter().writeEndElement();
        }
        // can only write the FIRST cooperative effect
        else if (!object.getCooperativeEffects().isEmpty()){
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            writeCooperativeEffect(object);
            // write complex expansion if any

            if (object.getComplexExpansion() != null){
                // write start attribute list
                getStreamWriter().writeStartElement("attributeList");
                super.writeAttribute(object.getComplexExpansion().getShortName(), object.getComplexExpansion().getMIIdentifier());
                // write end attributeList
                getStreamWriter().writeEndElement();
            }
            // write end attributeList
            getStreamWriter().writeEndElement();
        }
        // write complex expansion if any
        else if (object.getComplexExpansion() != null){
            // write start attribute list
            getStreamWriter().writeStartElement("attributeList");
            super.writeAttribute(object.getComplexExpansion().getShortName(), object.getComplexExpansion().getMIIdentifier());
            // write end attributeList
            getStreamWriter().writeEndElement();
        }
    }
}
