package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.xml.PsiXml25ObjectIndex;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.extension.InferredInteractionParticipant;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;

/**
 * XML 2.5 writer for an extended inferred interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class Xml25InferredInteractionWriter implements PsiXml25ElementWriter<InferredInteraction> {
    private XMLStreamWriter2 streamWriter;
    private PsiXml25ObjectIndex objectIndex;

    public Xml25InferredInteractionWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25InferredInteractionWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the Xml25InferredInteractionWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
    }

    @Override
    public void write(InferredInteraction object) throws MIIOException {
        try {
            if (object != null){
                // write start
                this.streamWriter.writeStartElement("inferredInteraction");
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                // write participants
                for (InferredInteractionParticipant participant : object.getParticipants()){
                    if (participant != null){
                        this.streamWriter.writeStartElement("participant");
                        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                        if (participant.getFeature() != null){
                            this.streamWriter.writeStartElement("participantFeatureRef");
                            this.streamWriter.writeCharacters(Integer.toString(this.objectIndex.extractIdFor(participant.getFeature())));
                            // write end feature ref
                            this.streamWriter.writeEndElement();
                            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                        }
                        else if (participant.getParticipant() != null){
                            this.streamWriter.writeStartElement("participantRef");
                            this.streamWriter.writeCharacters(Integer.toString(this.objectIndex.extractIdFor(participant.getParticipant())));
                            // write end feature ref
                            this.streamWriter.writeEndElement();
                            this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                        }
                        // write end inferred participant
                        this.streamWriter.writeEndElement();
                        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                    }
                }

                // write experiment references
                if (!object.getExperiments().isEmpty()){
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                    this.streamWriter.writeStartElement("experimentRefList");
                    this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                    for (Experiment exp : object.getExperiments()){
                        this.streamWriter.writeStartElement("experimentRef");
                        this.streamWriter.writeCharacters(Integer.toString(this.objectIndex.extractIdFor(exp)));
                        this.streamWriter.writeEndElement();
                        this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
                    }
                    this.streamWriter.writeEndElement();
                }

                // write end inferred interaction
                this.streamWriter.writeEndElement();
            }
        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the inferred interaction : "+object.toString(), e);
        }
    }
}
