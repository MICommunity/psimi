package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.InteractorPool;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for XmlInteractorWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/13</pre>
 */

public class XmlInteractorWriterTest extends AbstractXmlWriterTest {

    private String interactor = "<interactor id=\"1\">\n" +
            "  <names>\n" +
            "    <shortLabel>protein test</shortLabel>\n" +
            "  </names>\n" +
            "  <interactorType>\n" +
            "    <names>\n" +
            "      <shortLabel>protein</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </interactorType>\n" +
            "</interactor>";

    private String interactor_fullName = "<interactor id=\"1\">\n" +
            "  <names>\n" +
            "    <shortLabel>protein test</shortLabel>\n" +
            "    <fullName>protein full name</fullName>\n" +
            "  </names>\n" +
            "  <interactorType>\n" +
            "    <names>\n" +
            "      <shortLabel>protein</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </interactorType>\n" +
            "</interactor>";

    private String interactor_aliases = "<interactor id=\"1\">\n" +
            "  <names>\n" +
            "    <shortLabel>protein test</shortLabel>\n" +
            "    <alias type=\"synonym\">protein synonym</alias>\n" +
            "    <alias>test</alias>\n" +
            "  </names>\n" +
            "  <interactorType>\n" +
            "    <names>\n" +
            "      <shortLabel>protein</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </interactorType>\n" +
            "</interactor>";

    private String interactorIdentifier = "<interactor id=\"1\">\n" +
            "  <names>\n" +
            "    <shortLabel>protein test</shortLabel>\n" +
            "  </names>\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"uniprotkb\" dbAc=\"MI:0486\" id=\"P22216\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    <secondaryRef db=\"rcsb pdb\" dbAc=\"MI:0460\" id=\"2JQI\"/>\n"+
            "  </xref>\n" +
            "  <interactorType>\n" +
            "    <names>\n" +
            "      <shortLabel>protein</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </interactorType>\n" +
            "</interactor>";
    private String interactorXref = "<interactor id=\"1\">\n" +
            "  <names>\n" +
            "    <shortLabel>protein test</shortLabel>\n" +
            "  </names>\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"rcsb pdb\" dbAc=\"MI:0460\" id=\"2JQI\"/>\n" +
            "    <secondaryRef db=\"test\" id=\"P22216\"/>\n"+
            "  </xref>\n" +
            "  <interactorType>\n" +
            "    <names>\n" +
            "      <shortLabel>protein</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </interactorType>\n" +
            "</interactor>";
    private String interactorOrganism = "<interactor id=\"1\">\n" +
            "  <names>\n" +
            "    <shortLabel>protein test</shortLabel>\n" +
            "  </names>\n" +
            "  <interactorType>\n" +
            "    <names>\n" +
            "      <shortLabel>protein</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </interactorType>\n" +
            "  <organism ncbiTaxId=\"9606\">\n" +
            "    <names>\n" +
            "      <shortLabel>human</shortLabel>\n" +
            "    </names>\n" +
            "  </organism>\n" +
            "</interactor>";
    private String interactorSequence = "<interactor id=\"1\">\n" +
            "  <names>\n" +
            "    <shortLabel>protein test</shortLabel>\n" +
            "  </names>\n" +
            "  <interactorType>\n" +
            "    <names>\n" +
            "      <shortLabel>protein</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </interactorType>\n" +
            "  <sequence>AAGGLLA</sequence>\n" +
            "</interactor>";
    private String interactorAttributes = "<interactor id=\"1\">\n" +
            "  <names>\n" +
            "    <shortLabel>protein test</shortLabel>\n" +
            "  </names>\n" +
            "  <interactorType>\n" +
            "    <names>\n" +
            "      <shortLabel>protein</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </interactorType>\n" +
            "  <attributeList>\n" +
            "    <attribute name=\"test2\"/>\n"+
            "    <attribute name=\"test3\"/>\n"+
            "  </attributeList>\n"+
            "</interactor>";

    private String interactor_registered = "<interactor id=\"2\">\n" +
            "  <names>\n" +
            "    <shortLabel>protein test</shortLabel>\n" +
            "  </names>\n" +
            "  <interactorType>\n" +
            "    <names>\n" +
            "      <shortLabel>protein</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </interactorType>\n" +
            "</interactor>";
    private String interactorPool = "<interactor id=\"1\">\n" +
            "  <names>\n" +
            "    <shortLabel>pool test</shortLabel>\n" +
            "  </names>\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"uniprotkb\" dbAc=\"MI:0486\" id=\"P12345\" refType=\"set member\" refTypeAc=\"MI:1341\"/>\n" +
            "    <secondaryRef db=\"uniprotkb\" dbAc=\"MI:0486\" id=\"P12346\" refType=\"set member\" refTypeAc=\"MI:1341\"/>\n" +
            "  </xref>\n" +
            "  <interactorType>\n" +
            "    <names>\n" +
            "      <shortLabel>molecule set</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:1304\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </interactorType>\n" +
            "</interactor>";
    private String interactorPool_sequences = "<interactor id=\"1\">\n" +
            "  <names>\n" +
            "    <shortLabel>pool test</shortLabel>\n" +
            "  </names>\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"uniprotkb\" dbAc=\"MI:0486\" id=\"P12345\" refType=\"set member\" refTypeAc=\"MI:1341\"/>\n" +
            "    <secondaryRef db=\"uniprotkb\" dbAc=\"MI:0486\" id=\"P12346\" refType=\"set member\" refTypeAc=\"MI:1341\"/>\n" +
            "  </xref>\n" +
            "  <interactorType>\n" +
            "    <names>\n" +
            "      <shortLabel>molecule set</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:1304\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </interactorType>\n" +
            "  <sequence>AAGGLLA</sequence>\n" +
            "</interactor>";
    private PsiXmlObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_interactor() throws XMLStreamException, IOException {
        Interactor interactor = new DefaultProtein("protein test");
        elementCache.clear();

        XmlInteractorWriter writer = new XmlInteractorWriter(createStreamWriter(), this.elementCache);
        writer.write(interactor);
        streamWriter.flush();

        Assert.assertEquals(this.interactor, output.toString());
    }

    @Test
    public void test_write_interactor_fullName() throws XMLStreamException, IOException {
        Interactor interactor = new DefaultProtein("protein test");
        interactor.setFullName("protein full name");
        elementCache.clear();

        XmlInteractorWriter writer = new XmlInteractorWriter(createStreamWriter(), this.elementCache);
        writer.write(interactor);
        streamWriter.flush();

        Assert.assertEquals(interactor_fullName, output.toString());
    }

    @Test
    public void test_write_interactor_aliases() throws XMLStreamException, IOException {
        Interactor interactor = new DefaultProtein("protein test");
        interactor.getAliases().add(new DefaultAlias(new DefaultCvTerm("synonym"), "protein synonym"));
        interactor.getAliases().add(new DefaultAlias("test"));

        elementCache.clear();

        XmlInteractorWriter writer = new XmlInteractorWriter(createStreamWriter(), this.elementCache);
        writer.write(interactor);
        streamWriter.flush();

        Assert.assertEquals(interactor_aliases, output.toString());
    }

    @Test
    public void test_write_interactor_identifiers() throws XMLStreamException, IOException {
        Interactor interactor = new DefaultProtein("protein test");
        interactor.getXrefs().add(new DefaultXref(new DefaultCvTerm("rcsb pdb", "MI:0460"), "2JQI"));
        interactor.getIdentifiers().add(new DefaultXref(new DefaultCvTerm(Xref.UNIPROTKB, Xref.UNIPROTKB_MI), "P22216", new DefaultCvTerm(Xref.IDENTITY, Xref.IDENTITY_MI)));
        elementCache.clear();

        XmlInteractorWriter writer = new XmlInteractorWriter(createStreamWriter(), this.elementCache);
        writer.write(interactor);
        streamWriter.flush();

        Assert.assertEquals(interactorIdentifier, output.toString());
    }

    @Test
    public void test_write_interactor_xref() throws XMLStreamException, IOException {
        Interactor interactor = new DefaultProtein("protein test");
        interactor.getXrefs().add(new DefaultXref(new DefaultCvTerm("rcsb pdb", "MI:0460"), "2JQI"));
        interactor.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "P22216"));
        elementCache.clear();

        XmlInteractorWriter writer = new XmlInteractorWriter(createStreamWriter(), this.elementCache);
        writer.write(interactor);
        streamWriter.flush();

        Assert.assertEquals(interactorXref, output.toString());
    }

    @Test
    public void test_write_interactor_organism() throws XMLStreamException, IOException {
        Interactor interactor = new DefaultProtein("protein test");
        interactor.setOrganism(new DefaultOrganism(9606, "human"));
        elementCache.clear();

        XmlInteractorWriter writer = new XmlInteractorWriter(createStreamWriter(), this.elementCache);
        writer.write(interactor);
        streamWriter.flush();

        Assert.assertEquals(interactorOrganism, output.toString());
    }

    @Test
    public void test_write_interactor_sequence() throws XMLStreamException, IOException {
        Protein interactor = new DefaultProtein("protein test");
        interactor.setSequence("AAGGLLA");
        elementCache.clear();

        XmlInteractorWriter writer = new XmlInteractorWriter(createStreamWriter(), this.elementCache);
        writer.write(interactor);
        streamWriter.flush();

        Assert.assertEquals(interactorSequence, output.toString());
    }

    @Test
    public void test_write_interactor_pool() throws XMLStreamException, IOException {
        InteractorPool interactor = new DefaultInteractorPool("pool test");
        Protein protein1 = new DefaultProtein("protein test");
        protein1.setUniprotkb("P12345");
        Protein protein2 = new DefaultProtein("protein test2");
        protein2.setUniprotkb("P12346");
        interactor.add(protein1);
        interactor.add(protein2);
        elementCache.clear();

        XmlInteractorWriter writer = new XmlInteractorWriter(createStreamWriter(), this.elementCache);
        writer.write(interactor);
        streamWriter.flush();

        Assert.assertEquals(interactorPool, output.toString());
    }

    @Test
    public void test_write_interactor_pool_sequence() throws XMLStreamException, IOException {
        InteractorPool interactor = new DefaultInteractorPool("pool test");
        Protein protein1 = new DefaultProtein("protein test");
        protein1.setUniprotkb("P12345");
        Protein protein2 = new DefaultProtein("protein test2");
        protein2.setUniprotkb("P12346");
        interactor.add(protein1);
        interactor.add(protein2);
        // same sequences, will be written
        protein1.setSequence("AAGGLLA");
        protein2.setSequence("AAGGLLA");
        elementCache.clear();

        XmlInteractorWriter writer = new XmlInteractorWriter(createStreamWriter(), this.elementCache);
        writer.write(interactor);
        streamWriter.flush();

        Assert.assertEquals(interactorPool_sequences, output.toString());
    }

    @Test
    public void test_write_interactor_pool_no_sequence() throws XMLStreamException, IOException {
        InteractorPool interactor = new DefaultInteractorPool("pool test");
        Protein protein1 = new DefaultProtein("protein test");
        protein1.setUniprotkb("P12345");
        Protein protein2 = new DefaultProtein("protein test2");
        protein2.setUniprotkb("P12346");
        interactor.add(protein1);
        interactor.add(protein2);
        // different sequences, will not be written
        protein1.setSequence("AAGGLLA");
        protein2.setSequence("AAGG");
        elementCache.clear();

        XmlInteractorWriter writer = new XmlInteractorWriter(createStreamWriter(), this.elementCache);
        writer.write(interactor);
        streamWriter.flush();

        Assert.assertEquals(interactorPool, output.toString());
    }

    @Test
    public void test_write_interactor_attributes() throws XMLStreamException, IOException {
        Interactor interactor = new DefaultProtein("protein test");
        interactor.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test2")));
        interactor.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));
        elementCache.clear();

        XmlInteractorWriter writer = new XmlInteractorWriter(createStreamWriter(), this.elementCache);
        writer.write(interactor);
        streamWriter.flush();

        Assert.assertEquals(interactorAttributes, output.toString());
    }

    @Test
    public void test_write_interactor_registered() throws XMLStreamException, IOException {
        Interactor interactor = new DefaultProtein("protein test");
        elementCache.clear();
        elementCache.extractIdForInteractor(new DefaultInteractor("test interactor"));
        elementCache.extractIdForInteractor(interactor);

        XmlInteractorWriter writer = new XmlInteractorWriter(createStreamWriter(), this.elementCache);
        writer.write(interactor);
        streamWriter.flush();

        Assert.assertEquals(interactor_registered, output.toString());
    }
}
