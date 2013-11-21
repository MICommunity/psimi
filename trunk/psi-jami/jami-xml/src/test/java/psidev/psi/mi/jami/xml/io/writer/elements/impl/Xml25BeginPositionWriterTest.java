package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.utils.PositionUtils;
import psidev.psi.mi.jami.xml.io.writer.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for Xml25BeginPositionWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class Xml25BeginPositionWriterTest extends AbstractXml25WriterTest{
    private String begin_position ="<startStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>certain</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
            "  </xref>\n"+
            "</startStatus>\n" +
            "<begin position=\"1\"/>";
    private String begin_interval ="<startStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>range</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" id=\"MI:0338\" refType=\"identity\"/>\n"+
            "  </xref>\n"+
            "</startStatus>\n" +
            "<beginInterval begin=\"1\" end=\"4\"/>";
    private String undetermined ="<startStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" id=\"MI:0339\" refType=\"identity\"/>\n"+
            "  </xref>\n"+
            "</startStatus>";

    @Test
    public void test_write_begin_position() throws XMLStreamException, IOException {
        Position pos = PositionUtils.createCertainPosition(1);

        Xml25BeginPositionWriter writer = new Xml25BeginPositionWriter(createStreamWriter());
        writer.write(pos);
        streamWriter.flush();

        Assert.assertEquals(begin_position, output.toString());
    }

    @Test
    public void test_write_begin_interval() throws XMLStreamException, IOException {
        Position pos = PositionUtils.createFuzzyPosition(1, 4);

        Xml25BeginPositionWriter writer = new Xml25BeginPositionWriter(createStreamWriter());
        writer.write(pos);
        streamWriter.flush();

        Assert.assertEquals(begin_interval, output.toString());
    }

    @Test
    public void test_write_begin_undetermined() throws XMLStreamException, IOException {
        Position pos = PositionUtils.createUndeterminedPosition();

        Xml25BeginPositionWriter writer = new Xml25BeginPositionWriter(createStreamWriter());
        writer.write(pos);
        streamWriter.flush();

        Assert.assertEquals(undetermined, output.toString());
    }
}
