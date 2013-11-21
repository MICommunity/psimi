package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.xml.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for Xml25AvailabilityWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/11/13</pre>
 */

public class Xml25AvailabilityWriterTest extends AbstractXml25WriterTest{
    private String availability ="<availability id=\"1\">copyright</availability>";
    private String availability2 ="<availability id=\"2\">copyright</availability>";
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
}
