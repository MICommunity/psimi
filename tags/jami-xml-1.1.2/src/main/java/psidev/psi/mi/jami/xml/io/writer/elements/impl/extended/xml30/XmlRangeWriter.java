package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30;

import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlBeginPositionWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlEndPositionWriter;

import javax.xml.stream.XMLStreamWriter;

/**
 * Xml 3.0 writer for a feature range
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class XmlRangeWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlRangeWriter {

    public XmlRangeWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        super(writer, objectIndex);
    }

    protected void initialiseResultingSequenceWriter() {
        super.setResultingSequenceWriter(new XmlResultingSequenceWriter(getStreamWriter()));
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
