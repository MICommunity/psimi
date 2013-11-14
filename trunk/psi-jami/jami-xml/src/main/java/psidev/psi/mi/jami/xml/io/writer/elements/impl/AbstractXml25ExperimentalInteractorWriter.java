package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.xml.PsiXml25ObjectIndex;
import psidev.psi.mi.jami.xml.extension.ExperimentalInteractor;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;

/**
 * Abstract class for experimental interactor XML 2.5 writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public abstract class AbstractXml25ExperimentalInteractorWriter implements PsiXml25ElementWriter<ExperimentalInteractor>{
    private XMLStreamWriter2 streamWriter;
    private PsiXml25ObjectIndex objectIndex;

    public AbstractXml25ExperimentalInteractorWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the AbstractXml25ExperimentalInteractorWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the Xml25InteractorWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
    }
    @Override
    public void write(ExperimentalInteractor object) throws MIIOException {
        try {
            // write start
            this.streamWriter.writeStartElement("experimentalInteractor");
            // write interactor
            writeInteractor(object.getInteractor());
            // write experiment refs
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
                this.streamWriter.writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end experimental interactor
            this.streamWriter.writeEndElement();

        } catch (XMLStreamException e) {
            throw new MIIOException("Impossible to write the experimental interactor : "+object.toString(), e);
        }
    }

    protected abstract void writeInteractor(Interactor interactor) throws XMLStreamException;

    protected XMLStreamWriter2 getStreamWriter() {
        return streamWriter;
    }

    protected PsiXml25ObjectIndex getObjectIndex() {
        return objectIndex;
    }
}
