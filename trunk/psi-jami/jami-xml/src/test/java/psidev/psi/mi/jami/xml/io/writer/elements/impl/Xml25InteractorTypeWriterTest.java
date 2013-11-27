package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for Xml25InteractorTypeWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class Xml25InteractorTypeWriterTest extends AbstractXml25WriterTest {

    private String type = "<interactorType>\n" +
            "  <names>\n" +
            "    <shortLabel>inferred</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" id=\"MI:0362\" refType=\"identity\"/>\n"+
            "  </xref>\n"+
            "</interactorType>";
    private String typeFullName = "<interactorType>\n" +
            "  <names>\n" +
            "    <shortLabel>inferred</shortLabel>\n"+
            "    <fullName>inference</fullName>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" id=\"MI:0362\" refType=\"identity\"/>\n"+
            "  </xref>\n"+
            "</interactorType>";
    private String typeAliases = "<interactorType>\n" +
            "  <names>\n" +
            "    <shortLabel>inferred</shortLabel>\n"+
            "    <alias type=\"synonym\">unspecified</alias>\n"+
            "    <alias type=\"test\">test name</alias>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" id=\"MI:0362\" refType=\"identity\"/>\n"+
            "  </xref>\n"+
            "</interactorType>";
    private String typeMod = "<interactorType>\n" +
            "  <names>\n" +
            "    <shortLabel>inferred</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mod\" id=\"MI:0362\" refType=\"identity\"/>\n"+
            "  </xref>\n"+
            "</interactorType>";
    private String typePar = "<interactorType>\n" +
            "  <names>\n" +
            "    <shortLabel>inferred</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-par\" id=\"MI:0362\" refType=\"identity\"/>\n"+
            "  </xref>\n"+
            "</interactorType>";
    private String typeFirstIdentifier = "<interactorType>\n" +
            "  <names>\n" +
            "    <shortLabel>inferred</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test\" id=\"MI:0362\" refType=\"identity\"/>\n"+
            "    <secondaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "</interactorType>";
    private String typeFirstXref = "<interactorType>\n" +
            "  <names>\n" +
            "    <shortLabel>inferred</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "</interactorType>";

    @Test
    public void test_write_cv_no_fullName() throws XMLStreamException, IOException {
        CvTerm type = new DefaultCvTerm("inferred", "MI:0362");

        Xml25InteractorTypeWriter writer = new Xml25InteractorTypeWriter(createStreamWriter());
        writer.write(type);
        streamWriter.flush();

        Assert.assertEquals(this.type, output.toString());
    }

    @Test
    public void test_write_cv_fullName() throws XMLStreamException, IOException {
        CvTerm type = new DefaultCvTerm("inferred", "MI:0362");
        type.setFullName("inference");

        Xml25InteractorTypeWriter writer = new Xml25InteractorTypeWriter(createStreamWriter());
        writer.write(type);
        streamWriter.flush();

        Assert.assertEquals(this.typeFullName, output.toString());
    }

    @Test
    public void test_write_cv_aliases() throws XMLStreamException, IOException {
        CvTerm type = new DefaultCvTerm("inferred", "MI:0362");
        type.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("synonym"), "unspecified"));
        type.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("test"), "test name"));

        Xml25InteractorTypeWriter writer = new Xml25InteractorTypeWriter(createStreamWriter());
        writer.write(type);
        streamWriter.flush();

        Assert.assertEquals(this.typeAliases, output.toString());
    }

    @Test
    public void test_write_cv_mod() throws XMLStreamException, IOException {
        CvTerm type = new DefaultCvTerm("inferred", "MI:0362");
        type.setMODIdentifier(type.getMIIdentifier());
        type.setMIIdentifier(null);

        Xml25InteractorTypeWriter writer = new Xml25InteractorTypeWriter(createStreamWriter());
        writer.write(type);
        streamWriter.flush();

        Assert.assertEquals(this.typeMod, output.toString());
    }

    @Test
    public void test_write_cv_par() throws XMLStreamException, IOException {
        CvTerm type = new DefaultCvTerm("inferred", "MI:0362");
        type.setPARIdentifier(type.getMIIdentifier());
        type.setMIIdentifier(null);
        Xml25InteractorTypeWriter writer = new Xml25InteractorTypeWriter(createStreamWriter());
        writer.write(type);
        streamWriter.flush();

        Assert.assertEquals(this.typePar, output.toString());
    }

    @Test
    public void test_write_cv_first_identifier() throws XMLStreamException, IOException {
        CvTerm type = new DefaultCvTerm("inferred", "MI:0362");
        type.getIdentifiers().iterator().next().getDatabase().setShortName("test");
        type.getIdentifiers().iterator().next().getDatabase().setMIIdentifier(null);
        type.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        type.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25InteractorTypeWriter writer = new Xml25InteractorTypeWriter(createStreamWriter());
        writer.write(type);
        streamWriter.flush();

        Assert.assertEquals(this.typeFirstIdentifier, output.toString());
    }

    @Test
    public void test_write_cv_first_xref() throws XMLStreamException, IOException {
        CvTerm type = new DefaultCvTerm("inferred");
        type.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        type.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25InteractorTypeWriter writer = new Xml25InteractorTypeWriter(createStreamWriter());
        writer.write(type);
        streamWriter.flush();

        Assert.assertEquals(this.typeFirstXref, output.toString());
    }
}

