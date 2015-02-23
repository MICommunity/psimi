package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.CooperativityEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.Preassembly;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for XmlPreassemblyWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class XmlPreassemblyWriterTest extends AbstractXmlWriterTest {
    private PsiXmlObjectCache elementCache = new InMemoryIdentityObjectCache();

    private String preassembly = "<preassembly>\n" +
            "  <cooperativityEvidenceList>\n" +
            "    <cooperativityEvidenceDescription>\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "        <attributeList>\n" +
            "          <attribute name=\"publication title\" nameAc=\"MI:1091\">test title</attribute>\n"+
            "        </attributeList>\n"+
            "      </bibref>\n"+
            "    </cooperativityEvidenceDescription>\n"+
            "  </cooperativityEvidenceList>\n" +
            "  <affectedInteractionList>\n" +
            "    <affectedInteractionRef>1</affectedInteractionRef>\n" +
            "  </affectedInteractionList>\n"+
            "  <cooperativeEffectOutcome>\n" +
            "    <names>\n" +
            "      <shortLabel>positive cooperative effect</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:1154\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </cooperativeEffectOutcome>\n"+
            "</preassembly>";
    private String preassembly_response = "<preassembly>\n" +
            "  <cooperativityEvidenceList>\n" +
            "    <cooperativityEvidenceDescription>\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "        <attributeList>\n" +
            "          <attribute name=\"publication title\" nameAc=\"MI:1091\">test title</attribute>\n"+
            "        </attributeList>\n"+
            "      </bibref>\n"+
            "    </cooperativityEvidenceDescription>\n"+
            "  </cooperativityEvidenceList>\n" +
            "  <affectedInteractionList>\n" +
            "    <affectedInteractionRef>1</affectedInteractionRef>\n" +
            "  </affectedInteractionList>\n"+
            "  <cooperativeEffectOutcome>\n" +
            "    <names>\n" +
            "      <shortLabel>positive cooperative effect</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:1154\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </cooperativeEffectOutcome>\n"+
            "  <cooperativeEffectResponse>\n" +
            "    <names>\n" +
            "      <shortLabel>binding site hiding</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:1173\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </cooperativeEffectResponse>\n"+
            "</preassembly>";
    private String preassembly_attributes = "<preassembly>\n" +
            "  <cooperativityEvidenceList>\n" +
            "    <cooperativityEvidenceDescription>\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "        <attributeList>\n" +
            "          <attribute name=\"publication title\" nameAc=\"MI:1091\">test title</attribute>\n"+
            "        </attributeList>\n"+
            "      </bibref>\n"+
            "    </cooperativityEvidenceDescription>\n"+
            "  </cooperativityEvidenceList>\n" +
            "  <affectedInteractionList>\n" +
            "    <affectedInteractionRef>1</affectedInteractionRef>\n" +
            "  </affectedInteractionList>\n"+
            "  <cooperativeEffectOutcome>\n" +
            "    <names>\n" +
            "      <shortLabel>positive cooperative effect</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:1154\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </cooperativeEffectOutcome>\n"+
            "  <attributeList>\n" +
            "    <attribute name=\"test3\"/>\n"+
            "  </attributeList>\n"+
            "</preassembly>";
    private String preassembly_already_registered = "<preassembly>\n" +
            "  <cooperativityEvidenceList>\n" +
            "    <cooperativityEvidenceDescription>\n" +
            "      <bibref>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "        <attributeList>\n" +
            "          <attribute name=\"publication title\" nameAc=\"MI:1091\">test title</attribute>\n"+
            "        </attributeList>\n"+
            "      </bibref>\n"+
            "    </cooperativityEvidenceDescription>\n"+
            "  </cooperativityEvidenceList>\n" +
            "  <affectedInteractionList>\n" +
            "    <affectedInteractionRef>2</affectedInteractionRef>\n" +
            "  </affectedInteractionList>\n"+
            "  <cooperativeEffectOutcome>\n" +
            "    <names>\n" +
            "      <shortLabel>positive cooperative effect</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:1154\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </cooperativeEffectOutcome>\n"+
            "</preassembly>";

    @Test
    public void test_write_preassembly() throws XMLStreamException, IOException, IllegalRangeException {
        Preassembly effect = new DefaultPreassemby(CvTermUtils.createMICvTerm("positive cooperative effect","MI:1154"));
        CooperativityEvidence ev = new DefaultCooperativityEvidence(new DefaultPublication("xxxxxx"));
        ev.getPublication().setTitle("test title");
        effect.getCooperativityEvidences().add(ev);
        effect.getAffectedInteractions().add(new DefaultModelledInteraction());

        XmlPreAssemblyWriter writer = new XmlPreAssemblyWriter(createStreamWriter(), elementCache);
        writer.write(effect);
        streamWriter.flush();

        Assert.assertEquals(this.preassembly, output.toString());
    }

    @Test
    public void test_write_preassembly_response() throws XMLStreamException, IOException, IllegalRangeException {
        Preassembly effect = new DefaultPreassemby(CvTermUtils.createMICvTerm("positive cooperative effect","MI:1154"));
        CooperativityEvidence ev = new DefaultCooperativityEvidence(new DefaultPublication("xxxxxx"));
        ev.getPublication().setTitle("test title");
        effect.getCooperativityEvidences().add(ev);
        effect.getAffectedInteractions().add(new DefaultModelledInteraction());
        effect.setResponse(CvTermUtils.createMICvTerm("binding site hiding","MI:1173"));

        XmlPreAssemblyWriter writer = new XmlPreAssemblyWriter(createStreamWriter(), elementCache);
        writer.write(effect);
        streamWriter.flush();

        Assert.assertEquals(preassembly_response, output.toString());
    }

    @Test
    public void test_write_preassembly_attributes() throws XMLStreamException, IOException, IllegalRangeException {
        Preassembly effect = new DefaultPreassemby(CvTermUtils.createMICvTerm("positive cooperative effect","MI:1154"));
        CooperativityEvidence ev = new DefaultCooperativityEvidence(new DefaultPublication("xxxxxx"));
        ev.getPublication().setTitle("test title");
        effect.getCooperativityEvidences().add(ev);
        effect.getAffectedInteractions().add(new DefaultModelledInteraction());
        effect.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));

        XmlPreAssemblyWriter writer = new XmlPreAssemblyWriter(createStreamWriter(), elementCache);
        writer.write(effect);
        streamWriter.flush();

        Assert.assertEquals(preassembly_attributes, output.toString());
    }

    @Test
    public void test_write_modelled_interaction_already_registered() throws XMLStreamException {
        ModelledInteraction interaction = new DefaultModelledInteraction();
        this.elementCache.clear();
        this.elementCache.extractIdForInteraction(new DefaultModelledInteraction());
        this.elementCache.extractIdForInteraction(interaction);

        Preassembly effect = new DefaultPreassemby(CvTermUtils.createMICvTerm("positive cooperative effect","MI:1154"));
        CooperativityEvidence ev = new DefaultCooperativityEvidence(new DefaultPublication("xxxxxx"));
        ev.getPublication().setTitle("test title");
        effect.getCooperativityEvidences().add(ev);
        effect.getAffectedInteractions().add(interaction);

        XmlPreAssemblyWriter writer = new XmlPreAssemblyWriter(createStreamWriter(), elementCache);
        writer.write(effect);
        streamWriter.flush();

        Assert.assertEquals(this.preassembly_already_registered, output.toString());
    }
}
