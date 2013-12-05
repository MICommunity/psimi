package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.xml.cache.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.extension.InferredInteractionParticipant;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML 2.5 writer for an extended inferred interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class Xml25InferredInteractionWriter implements PsiXml25ElementWriter<InferredInteraction> {
    private XMLStreamWriter streamWriter;
    private PsiXml25ObjectCache objectIndex;

    public Xml25InferredInteractionWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex){
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
                // write participants
                for (InferredInteractionParticipant participant : object.getParticipants()){
                    if (participant != null){
                        this.streamWriter.writeStartElement("participant");
                        if (participant.getFeature() != null){
                            this.streamWriter.writeStartElement("participantFeatureRef");
                            this.streamWriter.writeCharacters(Integer.toString(this.objectIndex.extractIdForFeature(participant.getFeature())));
                            // write end feature ref
                            this.streamWriter.writeEndElement();
                        }
                        else if (participant.getParticipant() != null){
                            this.streamWriter.writeStartElement("participantRef");
                            this.streamWriter.writeCharacters(Integer.toString(this.objectIndex.extractIdForParticipant(participant.getParticipant())));
                            // write end feature ref
                            this.streamWriter.writeEndElement();
                        }
                        // write end inferred participant
                        this.streamWriter.writeEndElement();
                    }
                }

                // write experiment references
                if (!object.getExperiments().isEmpty()){
                    this.streamWriter.writeStartElement("experimentRefList");
                    for (Experiment exp : object.getExperiments()){
                        this.streamWriter.writeStartElement("experimentRef");
                        this.streamWriter.writeCharacters(Integer.toString(this.objectIndex.extractIdForExperiment(exp)));
                        this.streamWriter.writeEndElement();
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
