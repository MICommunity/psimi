package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.utils.RangeUtils;
import psidev.psi.mi.jami.xml.io.writer.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for RangeWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class Xml25RangeWriterTest extends AbstractXml25WriterTest {
    private String range = "<featureRange>\n" +
            "  <startStatus>\n" +
            "    <names>\n" +
            "      <shortLabel>certain</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
            "    </xref>\n"+
            "  </startStatus>\n" +
            "  <begin position=\"1\"/>\n"+
            "  <endStatus>\n" +
            "    <names>\n" +
            "      <shortLabel>certain</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
            "    </xref>\n"+
            "  </endStatus>\n" +
            "  <end position=\"4\"/>\n"+
            "</featureRange>";
    private String range_interval = "<featureRange>\n" +
            "  <startStatus>\n" +
            "    <names>\n" +
            "      <shortLabel>range</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" id=\"MI:0338\" refType=\"identity\"/>\n"+
            "    </xref>\n"+
            "  </startStatus>\n" +
            "  <beginInterval begin=\"1\" end=\"2\"/>\n"+
            "  <endStatus>\n" +
            "    <names>\n" +
            "      <shortLabel>range</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" id=\"MI:0338\" refType=\"identity\"/>\n"+
            "    </xref>\n"+
            "  </endStatus>\n" +
            "  <endInterval begin=\"4\" end=\"5\"/>\n"+
            "</featureRange>";
    private String range_mix = "<featureRange>\n" +
            "  <startStatus>\n" +
            "    <names>\n" +
            "      <shortLabel>range</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" id=\"MI:0338\" refType=\"identity\"/>\n"+
            "    </xref>\n"+
            "  </startStatus>\n" +
            "  <beginInterval begin=\"1\" end=\"2\"/>\n"+
            "  <endStatus>\n" +
            "    <names>\n" +
            "      <shortLabel>certain</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
            "    </xref>\n"+
            "  </endStatus>\n" +
            "  <end position=\"4\"/>\n"+
            "  <isLink>true</isLink>\n"+
            "</featureRange>";

    @Test
    public void test_write_range() throws XMLStreamException, IOException, IllegalRangeException {
        Range range = RangeUtils.createRangeFromString("1-4");

        Xml25RangeWriter writer = new Xml25RangeWriter(createStreamWriter());
        writer.write(range);
        streamWriter.flush();

        Assert.assertEquals(this.range, output.toString());
    }

    @Test
    public void test_write_range_interval() throws XMLStreamException, IOException, IllegalRangeException {
        Range range = RangeUtils.createRangeFromString("1..2-4..5");

        Xml25RangeWriter writer = new Xml25RangeWriter(createStreamWriter());
        writer.write(range);
        streamWriter.flush();

        Assert.assertEquals(range_interval, output.toString());
    }

    @Test
    public void test_write_range_mix() throws XMLStreamException, IOException, IllegalRangeException {
        Range range = RangeUtils.createRangeFromString("1..2-4", true);

        Xml25RangeWriter writer = new Xml25RangeWriter(createStreamWriter());
        writer.write(range);
        streamWriter.flush();

        Assert.assertEquals(range_mix, output.toString());
    }
}
