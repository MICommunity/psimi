package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.ResultingSequence;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;
import psidev.psi.mi.jami.model.impl.DefaultResultingSequence;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.RangeUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;

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
    private PsiXmlObjectCache elementCache = new InMemoryIdentityObjectCache();

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
    private String range_resulting_sequence = "<featureRange>\n" +
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
            "  <resultingSequence>\n" +
            "    <originalSequence>AATGCC</originalSequence>\n" +
            "    <newSequence>AATAG</newSequence>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"ensembl\" dbAc=\"MI:0476\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </resultingSequence>\n"+
            "</featureRange>";
    private String range_participant_ref = "<featureRange>\n" +
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
            "  <participantRef>1</participantRef>\n"+
            "</featureRange>";
    private String range_participant_ref_already_registered = "<featureRange>\n" +
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
            "  <participantRef>2</participantRef>\n"+
            "</featureRange>";

    @Test
    public void test_write_range() throws XMLStreamException, IOException, IllegalRangeException {
        Range range = RangeUtils.createRangeFromString("1-4");

        XmlRangeWriter writer = new XmlRangeWriter(createStreamWriter(), this.elementCache);
        writer.write(range);
        streamWriter.flush();

        Assert.assertEquals(this.range, output.toString());
    }

    @Test
    public void test_write_range_interval() throws XMLStreamException, IOException, IllegalRangeException {
        Range range = RangeUtils.createRangeFromString("1..2-4..5");

        XmlRangeWriter writer = new XmlRangeWriter(createStreamWriter(), this.elementCache);
        writer.write(range);
        streamWriter.flush();

        Assert.assertEquals(range_interval, output.toString());
    }

    @Test
    public void test_write_range_mix() throws XMLStreamException, IOException, IllegalRangeException {
        Range range = RangeUtils.createRangeFromString("1..2-4", true);

        XmlRangeWriter writer = new XmlRangeWriter(createStreamWriter(), this.elementCache);
        writer.write(range);
        streamWriter.flush();

        Assert.assertEquals(range_mix, output.toString());
    }

    @Test
    public void test_write_range_resulting_sequence() throws XMLStreamException, IOException, IllegalRangeException {
        Range range = RangeUtils.createRangeFromString("1-4");
        ResultingSequence res = new DefaultResultingSequence("AATGCC","AATAG");
        res.getXrefs().add(XrefUtils.createEnsemblIdentity("xxxxxx"));
        range.setResultingSequence(res);

        XmlRangeWriter writer = new XmlRangeWriter(createStreamWriter(), this.elementCache);
        writer.write(range);
        streamWriter.flush();

        Assert.assertEquals(this.range_resulting_sequence, output.toString());
    }

    @Test
    public void test_write_range_participant() throws XMLStreamException, IOException, IllegalRangeException {
        Range range = RangeUtils.createRangeFromString("1-4");
        range.setParticipant(new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));

        XmlRangeWriter writer = new XmlRangeWriter(createStreamWriter(), this.elementCache);
        writer.write(range);
        streamWriter.flush();

        Assert.assertEquals(this.range_participant_ref, output.toString());
    }

    @Test
    public void test_write_range_participant_registered() throws XMLStreamException, IOException, IllegalRangeException {
        Participant participant = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor());
        this.elementCache.clear();
        this.elementCache.extractIdForParticipant(new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));
        this.elementCache.extractIdForParticipant(participant);

        Range range = RangeUtils.createRangeFromString("1-4");
        range.setParticipant(participant);

        XmlRangeWriter writer = new XmlRangeWriter(createStreamWriter(), this.elementCache);
        writer.write(range);
        streamWriter.flush();

        Assert.assertEquals(this.range_participant_ref_already_registered, output.toString());
    }
}
