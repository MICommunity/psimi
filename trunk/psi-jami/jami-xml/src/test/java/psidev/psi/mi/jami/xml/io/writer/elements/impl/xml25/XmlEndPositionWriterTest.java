package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.utils.PositionUtils;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlEndPositionWriter;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester of XmlEndPositionWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class XmlEndPositionWriterTest extends AbstractXmlWriterTest {
    private String end_position ="<endStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>certain</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</endStatus>\n" +
            "<end position=\"1\"/>";
    private String end_interval ="<endStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>range</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0338\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</endStatus>\n" +
            "<endInterval begin=\"1\" end=\"4\"/>";
    private String undetermined ="<endStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0339\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</endStatus>";

    @Test
    public void test_write_begin_position() throws XMLStreamException, IOException {
        Position pos = PositionUtils.createCertainPosition(1);

        XmlEndPositionWriter writer = new XmlEndPositionWriter(createStreamWriter());
        writer.write(pos);
        streamWriter.flush();

        Assert.assertEquals(end_position, output.toString());
    }

    @Test
    public void test_write_begin_interval() throws XMLStreamException, IOException {
        Position pos = PositionUtils.createFuzzyPosition(1, 4);

        XmlEndPositionWriter writer = new XmlEndPositionWriter(createStreamWriter());
        writer.write(pos);
        streamWriter.flush();

        Assert.assertEquals(end_interval, output.toString());
    }

    @Test
    public void test_write_begin_undetermined() throws XMLStreamException, IOException {
        Position pos = PositionUtils.createUndeterminedPosition();

        XmlEndPositionWriter writer = new XmlEndPositionWriter(createStreamWriter());
        writer.write(pos);
        streamWriter.flush();

        Assert.assertEquals(undetermined, output.toString());
    }
}
