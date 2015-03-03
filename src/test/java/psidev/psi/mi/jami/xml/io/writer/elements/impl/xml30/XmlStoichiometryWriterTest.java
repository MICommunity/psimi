package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.model.impl.DefaultStoichiometry;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for StoichiometryWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class XmlStoichiometryWriterTest extends AbstractXmlWriterTest {
    private String stoichiometry = "<stoichiometry value=\"1\"/>";
    private String stoichiometryRange = "<stoichiometryRange minValue=\"1\" maxValue=\"2\"/>";

    @Test
    public void test_write_stoichiometry() throws XMLStreamException, IOException, IllegalRangeException {
        Stoichiometry stc = new DefaultStoichiometry(1);

        XmlStoichiometryWriter writer = new XmlStoichiometryWriter(createStreamWriter());
        writer.write(stc);
        streamWriter.flush();

        Assert.assertEquals(this.stoichiometry, output.toString());
    }

    @Test
    public void test_write_stoichiometryRange() throws XMLStreamException, IOException, IllegalRangeException {
        Stoichiometry stc = new DefaultStoichiometry(1,2);

        XmlStoichiometryWriter writer = new XmlStoichiometryWriter(createStreamWriter());
        writer.write(stc);
        streamWriter.flush();

        Assert.assertEquals(stoichiometryRange, output.toString());
    }

    @Test
    public void test_write_no_stoichiometry() throws XMLStreamException, IOException, IllegalRangeException {
        Stoichiometry stc = new DefaultStoichiometry(0);

        XmlStoichiometryWriter writer = new XmlStoichiometryWriter(createStreamWriter());
        writer.write(stc);
        streamWriter.flush();

        Assert.assertEquals("", output.toString());
    }
}
