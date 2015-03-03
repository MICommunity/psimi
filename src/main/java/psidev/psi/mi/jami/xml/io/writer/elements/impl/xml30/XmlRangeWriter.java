package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.ResultingSequence;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlBeginPositionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlEndPositionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlRangeWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Xml 3.0 writer for a feature range
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class XmlRangeWriter extends AbstractXmlRangeWriter {

    private PsiXmlElementWriter<ResultingSequence> resultingSequenceWriter;
    private PsiXmlObjectCache objectIndex;

    public XmlRangeWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        super(writer);
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the XmlRangeWriter. It is necessary for generating an id to a participant");
        }
        this.objectIndex = objectIndex;
    }

    public PsiXmlElementWriter<ResultingSequence> getResultingSequenceWriter() {
        if (this.resultingSequenceWriter == null){
            initialiseResultingSequenceWriter();
        }
        return resultingSequenceWriter;
    }

    protected void initialiseResultingSequenceWriter() {
        this.resultingSequenceWriter = new XmlResultingSequenceWriter(getStreamWriter());
    }

    public void setResultingSequenceWriter(PsiXmlElementWriter<ResultingSequence> resultingSequenceWriter) {
        this.resultingSequenceWriter = resultingSequenceWriter;
    }

    @Override
    protected void writeOtherProperties(Range object) throws XMLStreamException {
        // resulting sequence to write only when we have resulting sequence or xrefs
        if (object.getResultingSequence() != null
                && (object.getResultingSequence().getNewSequence() != null
                || !object.getResultingSequence().getXrefs().isEmpty())){
             getResultingSequenceWriter().write(object.getResultingSequence());
        }

        // participant reference
        if (object.getParticipant() != null){
            getStreamWriter().writeStartElement("participantRef");
            getStreamWriter().writeCharacters(Integer.toString(this.objectIndex.extractIdForParticipant(object.getParticipant())));
            getStreamWriter().writeEndElement();
        }
    }

    @Override
    protected void initialiseStartPositionWriter() {
        super.setStartPositionWriter(new XmlBeginPositionWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseEndPositionWriter() {
        super.setEndPositionWriter(new XmlEndPositionWriter(getStreamWriter()));
    }

}