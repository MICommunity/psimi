package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.ResultingSequence;
import psidev.psi.mi.jami.model.impl.DefaultResultingSequence;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for ResultingSequenceWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class XmlResultingSequenceWriterTest extends AbstractXmlWriterTest {
    private String resultingSequence = "<resultingSequence>\n" +
            "  <originalSequence>AATGCC</originalSequence>\n" +
            "  <newSequence>AATAG</newSequence>\n" +
            "</resultingSequence>";
    private String resultingSequence_xref = "<resultingSequence>\n" +
            "  <originalSequence>AATGCC</originalSequence>\n" +
            "  <newSequence>AATAG</newSequence>\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"ensembl\" dbAc=\"MI:0476\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</resultingSequence>";
    private String resultingSequence_xref_only = "<resultingSequence>\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"ensembl\" dbAc=\"MI:0476\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</resultingSequence>";

    @Test
    public void test_write_resulting_sequences() throws XMLStreamException, IOException, IllegalRangeException {
        ResultingSequence res = new DefaultResultingSequence("AATGCC","AATAG");

        XmlResultingSequenceWriter writer = new XmlResultingSequenceWriter(createStreamWriter());
        writer.write(res);
        streamWriter.flush();

        Assert.assertEquals(this.resultingSequence, output.toString());
    }

    @Test
    public void test_write_resulting_sequences_with_xrefs() throws XMLStreamException, IOException, IllegalRangeException {
        ResultingSequence res = new DefaultResultingSequence("AATGCC","AATAG");
        res.getXrefs().add(XrefUtils.createEnsemblIdentity("xxxxxx"));

        XmlResultingSequenceWriter writer = new XmlResultingSequenceWriter(createStreamWriter());
        writer.write(res);
        streamWriter.flush();

        Assert.assertEquals(resultingSequence_xref, output.toString());
    }

    @Test
    public void test_write_xrefs_only() throws XMLStreamException, IOException, IllegalRangeException {
        ResultingSequence res = new DefaultResultingSequence();
        res.getXrefs().add(XrefUtils.createEnsemblIdentity("xxxxxx"));

        XmlResultingSequenceWriter writer = new XmlResultingSequenceWriter(createStreamWriter());
        writer.write(res);
        streamWriter.flush();

        Assert.assertEquals(resultingSequence_xref_only, output.toString());
    }
}
