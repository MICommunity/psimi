package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.CooperativityEvidence;
import psidev.psi.mi.jami.model.impl.DefaultCooperativityEvidence;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for XmlCooperativityEvidenceWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class XmlCooperativityEvidenceWriterTest extends AbstractXmlWriterTest {
    private String cooperativity_evidence = "<cooperativityEvidenceDescription>\n" +
            "  <bibref>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "    <attributeList>\n" +
            "      <attribute name=\"publication title\" nameAc=\"MI:1091\">test title</attribute>\n"+
            "    </attributeList>\n"+
            "  </bibref>\n"+
            "</cooperativityEvidenceDescription>";
    private String cooperativity_evidence_methods = "<cooperativityEvidenceDescription>\n" +
            "  <bibref>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "    <attributeList>\n" +
            "      <attribute name=\"publication title\" nameAc=\"MI:1091\">test title</attribute>\n"+
            "    </attributeList>\n"+
            "  </bibref>\n" +
            "  <evidenceMethodList>\n" +
            "    <evidenceMethod>\n" +
            "      <names>\n" +
            "        <shortLabel>inferred</shortLabel>\n"+
            "      </names>\n"+
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0362\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "      </xref>\n"+
            "    </evidenceMethod>\n"+
            "  </evidenceMethodList>\n"+
            "</cooperativityEvidenceDescription>";

    @Test
    public void test_write_cooperativity_evidence() throws XMLStreamException, IOException, IllegalRangeException {
        CooperativityEvidence ev = new DefaultCooperativityEvidence(new DefaultPublication("xxxxxx"));
        ev.getPublication().setTitle("test title");

        XmlCooperativityEvidenceWriter writer = new XmlCooperativityEvidenceWriter(createStreamWriter());
        writer.write(ev);
        streamWriter.flush();

        Assert.assertEquals(this.cooperativity_evidence, output.toString());
    }

    @Test
    public void test_write_cooperativity_evidence_methods() throws XMLStreamException, IOException, IllegalRangeException {
        CooperativityEvidence ev = new DefaultCooperativityEvidence(new DefaultPublication("xxxxxx"));
        ev.getPublication().setTitle("test title");
        ev.getEvidenceMethods().add(new DefaultCvTerm("inferred", "MI:0362"));

        XmlCooperativityEvidenceWriter writer = new XmlCooperativityEvidenceWriter(createStreamWriter());
        writer.write(ev);
        streamWriter.flush();

        Assert.assertEquals(cooperativity_evidence_methods, output.toString());
    }
}
