package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.utils.RangeUtils;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlRangeWriter;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for RangeWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class XmlRangeWriterTest extends AbstractXmlWriterTest {
    private String range = "<featureRange>\n" +
            "  <startStatus>\n" +
            "    <names>\n" +
            "      <shortLabel>certain</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </startStatus>\n" +
            "  <begin position=\"1\"/>\n"+
            "  <endStatus>\n" +
            "    <names>\n" +
            "      <shortLabel>certain</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
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
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0338\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </startStatus>\n" +
            "  <beginInterval begin=\"1\" end=\"2\"/>\n"+
            "  <endStatus>\n" +
            "    <names>\n" +
            "      <shortLabel>range</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0338\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
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
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0338\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </startStatus>\n" +
            "  <beginInterval begin=\"1\" end=\"2\"/>\n"+
            "  <endStatus>\n" +
            "    <names>\n" +
            "      <shortLabel>certain</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </endStatus>\n" +
            "  <end position=\"4\"/>\n"+
            "  <isLink>true</isLink>\n"+
            "</featureRange>";

    @Test
    public void test_write_range() throws XMLStreamException, IOException, IllegalRangeException {
        Range range = RangeUtils.createRangeFromString("1-4");

        XmlRangeWriter writer = new XmlRangeWriter(createStreamWriter());
        writer.write(range);
        streamWriter.flush();

        Assert.assertEquals(this.range, output.toString());
    }

    @Test
    public void test_write_range_interval() throws XMLStreamException, IOException, IllegalRangeException {
        Range range = RangeUtils.createRangeFromString("1..2-4..5");

        XmlRangeWriter writer = new XmlRangeWriter(createStreamWriter());
        writer.write(range);
        streamWriter.flush();

        Assert.assertEquals(range_interval, output.toString());
    }

    @Test
    public void test_write_range_mix() throws XMLStreamException, IOException, IllegalRangeException {
        Range range = RangeUtils.createRangeFromString("1..2-4", true);

        XmlRangeWriter writer = new XmlRangeWriter(createStreamWriter());
        writer.write(range);
        streamWriter.flush();

        Assert.assertEquals(range_mix, output.toString());
    }
}
