package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Interaction;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ParticipantWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25NamedInteractionWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;

/**
 * Abstract class for extended interaction writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractXml25InteractionWriter<I extends Interaction, P extends Participant> extends AbstractXml25NamedInteractionWriter<I,P> {

    private PsiXml25ElementWriter<InferredInteraction> inferredInteractionWriter;

    public AbstractXml25InteractionWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex, PsiXml25ParticipantWriter<P> participantWriter) {
        super(writer, objectIndex, participantWriter);
        this.inferredInteractionWriter = new Xml25InferredInteractionWriter(writer, objectIndex);
    }

    protected AbstractXml25InteractionWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex, PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter, PsiXml25ParticipantWriter<P> participantWriter, PsiXml25ElementWriter<CvTerm> interactionTypeWriter, PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Experiment> experimentWriter, PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25ElementWriter<InferredInteraction> inferredInteractionWriter1) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter, participantWriter, interactionTypeWriter, attributeWriter, new psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25InferredInteractionWriter(writer, objectIndex), experimentWriter,
                aliasWriter);
        inferredInteractionWriter = inferredInteractionWriter1;
    }

    @Override
    protected void writeIntraMolecular(Interaction object) throws XMLStreamException {
        ExtendedPsi25Interaction xmlInteraction = (ExtendedPsi25Interaction)object;
        if (xmlInteraction.isIntraMolecular()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            getStreamWriter().writeStartElement("intraMolecular");
            getStreamWriter().writeCharacters(Boolean.toString(xmlInteraction.isIntraMolecular()));
            // write end intra molecular
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }

    @Override
    protected void writeInteractionType(Interaction object) throws XMLStreamException {
        ExtendedPsi25Interaction xmlInteraction = (ExtendedPsi25Interaction)object;
        if (!xmlInteraction.getInteractionTypes().isEmpty()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (Object type : xmlInteraction.getInteractionTypes()){
                getInteractionTypeWriter().write((CvTerm)type);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
        }
    }

    @Override
    protected void writeInferredInteractions(Interaction object) throws XMLStreamException {
        ExtendedPsi25Interaction xmlInteraction = (ExtendedPsi25Interaction)object;
        if (!xmlInteraction.getInferredInteractions().isEmpty()){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            getStreamWriter().writeStartElement("inferredInteractionList");
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            for (Object inferred : xmlInteraction.getInferredInteractions()){
                this.inferredInteractionWriter.write((InferredInteraction)inferred);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }
}
