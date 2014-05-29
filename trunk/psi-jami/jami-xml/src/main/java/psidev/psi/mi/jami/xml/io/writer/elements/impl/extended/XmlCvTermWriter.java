package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import javax.xml.stream.XMLStreamWriter;

/**
 * CvTerm writers for cvs without attributes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */
public class XmlCvTermWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlCvTermWriter {

    public XmlCvTermWriter(XMLStreamWriter writer){
        super(writer);
    }

    @Override
    protected void initialiseXrefWriter() {
        super.setXrefWriter(new XmlDbXrefWriter(getStreamWriter()));
    }
}
