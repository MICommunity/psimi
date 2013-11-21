package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.xml.io.writer.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for Xml25ConfidenceTypeWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class Xml25ConfidenceTypeWriterTest extends AbstractXml25WriterTest {

    private String cv = "<unit>\n" +
            "  <names>\n" +
            "    <shortLabel>molar</shortLabel>\n"+
            "  </names>\n"+
            "</unit>";
    private String cvFullName = "<unit>\n" +
            "  <names>\n" +
            "    <shortLabel>molar</shortLabel>\n"+
            "    <fullName>molar</fullName>\n"+
            "  </names>\n"+
            "</unit>";
    private String cvAliases = "<unit>\n" +
            "  <names>\n" +
            "    <shortLabel>molar</shortLabel>\n"+
            "    <alias type=\"synonym\">unspecified</alias>\n"+
            "    <alias type=\"test\">test name</alias>\n"+
            "  </names>\n"+
            "</unit>";
    private String cvFirstIdentifier = "<unit>\n" +
            "  <names>\n" +
            "    <shortLabel>molar</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    <secondaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "</unit>";
    private String cvFirstXref = "<unit>\n" +
            "  <names>\n" +
            "    <shortLabel>molar</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "</unit>";
    private String cvAttributes = "<unit>\n" +
            "  <names>\n" +
            "    <shortLabel>molar</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "  <attributeList>\n" +
            "    <attribute name=\"test2\"/>\n"+
            "    <attribute name=\"test3\"/>\n"+
            "  </attributeList>\n"+
            "</unit>";

    @Test
    public void test_write_cv_no_fullName() throws XMLStreamException, IOException {
        CvTerm type = new DefaultCvTerm("molar");

        Xml25ConfidenceTypeWriter writer = new Xml25ConfidenceTypeWriter(createStreamWriter());
        writer.write(type);
        streamWriter.flush();

        Assert.assertEquals(this.cv, output.toString());
    }

    @Test
    public void test_write_cv_fullName() throws XMLStreamException, IOException {
        CvTerm type = new DefaultCvTerm("molar");
        type.setFullName("molar");

        Xml25ConfidenceTypeWriter writer = new Xml25ConfidenceTypeWriter(createStreamWriter());
        writer.write(type);
        streamWriter.flush();

        Assert.assertEquals(this.cvFullName, output.toString());
    }

    @Test
    public void test_write_cv_aliases() throws XMLStreamException, IOException {
        CvTerm type = new DefaultCvTerm("molar");
        type.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("synonym"), "unspecified"));
        type.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("test"), "test name"));

        Xml25ConfidenceTypeWriter writer = new Xml25ConfidenceTypeWriter(createStreamWriter());
        writer.write(type);
        streamWriter.flush();

        Assert.assertEquals(this.cvAliases, output.toString());
    }

    @Test
    public void test_write_cv_first_identifier() throws XMLStreamException, IOException {
        CvTerm type = new DefaultCvTerm("molar");
        type.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test"), "MI:0499"));
        type.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        type.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25ConfidenceTypeWriter writer = new Xml25ConfidenceTypeWriter(createStreamWriter());
        writer.write(type);
        streamWriter.flush();

        Assert.assertEquals(this.cvFirstIdentifier, output.toString());
    }

    @Test
    public void test_write_cv_first_xref() throws XMLStreamException, IOException {
        CvTerm type = new DefaultCvTerm("molar");
        type.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        type.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25ConfidenceTypeWriter writer = new Xml25ConfidenceTypeWriter(createStreamWriter());
        writer.write(type);
        streamWriter.flush();

        Assert.assertEquals(this.cvFirstXref, output.toString());
    }

    @Test
    public void test_write_cv_attributes() throws XMLStreamException, IOException {
        CvTerm type = new DefaultCvTerm("molar");
        type.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        type.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));
        type.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test2")));
        type.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));
        Xml25ConfidenceTypeWriter writer = new Xml25ConfidenceTypeWriter(createStreamWriter());
        writer.write(type);
        streamWriter.flush();

        Assert.assertEquals(this.cvAttributes, output.toString());
    }
}
