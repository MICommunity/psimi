package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.XMLStreamWriter2;
import org.junit.Test;
import psidev.psi.mi.jami.xml.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Unit tester for Xml25AvailabilityWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/11/13</pre>
 */

public class Xml25AvailabilityWriterTest {
    private String availability ="<availability id=\"1\">copyright</availability>";
    private String availability2 ="<availability id=\"2\">copyright</availability>";
    private StringWriter output;
    private XMLStreamWriter2 streamWriter;
    private PsiXml25ObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_availability_not_in_cache() throws XMLStreamException, IOException {
        String availability = "copyright";
        this.elementCache.clear();

        Xml25AvailabilityWriter writer = new Xml25AvailabilityWriter(createStreamWriter(), this.elementCache);
        writer.write(availability);
        streamWriter.flush();

        Assert.assertEquals(this.availability, output.toString());
    }

    @Test
    public void test_write_availability_in_cache() throws XMLStreamException, IOException {
        String availability = "copyright";

        this.elementCache.clear();
        Assert.assertEquals(1, this.elementCache.extractIdForAvailability("first copyright"));
        Assert.assertEquals(2, this.elementCache.extractIdForAvailability(availability));

        Xml25AvailabilityWriter writer = new Xml25AvailabilityWriter(createStreamWriter(), this.elementCache);
        writer.write(availability);
        streamWriter.flush();

        Assert.assertEquals(this.availability2, output.toString());
    }


    private XMLStreamWriter2 createStreamWriter() throws XMLStreamException {
        XMLOutputFactory outputFactory = XMLOutputFactory2.newInstance();
        this.output = new StringWriter();
        this.streamWriter = (XMLStreamWriter2)outputFactory.createXMLStreamWriter(this.output);
        return this.streamWriter;
    }
}
