package psidev.psi.mi.jami.xml.io.writer;

import javanet.staxutils.IndentingXMLStreamWriter;
import org.codehaus.stax2.XMLOutputFactory2;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;

/**
 * Abstract class for test writers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public abstract class AbstractXml25WriterTest {
    protected StringWriter output;
    protected XMLStreamWriter streamWriter;

    protected XMLStreamWriter createStreamWriter() throws XMLStreamException {
        XMLOutputFactory outputFactory = XMLOutputFactory2.newInstance();
        outputFactory.setProperty("com.ctc.wstx.outputValidateStructure", Boolean.FALSE);
        this.output = new StringWriter();
        this.streamWriter = new IndentingXMLStreamWriter(outputFactory.createXMLStreamWriter(this.output));
        return this.streamWriter;
    }
}
