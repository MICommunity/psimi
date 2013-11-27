package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester of Xml25CompartmentWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class Xml25CompartmentWriterTest extends AbstractXml25WriterTest {

    private String cv = "<compartment>\n" +
            "  <names>\n" +
            "    <shortLabel>293t</shortLabel>\n"+
            "  </names>\n"+
            "</compartment>";
    private String cvFullName = "<compartment>\n" +
            "  <names>\n" +
            "    <shortLabel>293t</shortLabel>\n"+
            "    <fullName>Human 293t</fullName>\n"+
            "  </names>\n"+
            "</compartment>";
    private String cvAliases = "<compartment>\n" +
            "  <names>\n" +
            "    <shortLabel>293t</shortLabel>\n"+
            "    <alias type=\"synonym\">unspecified</alias>\n"+
            "    <alias type=\"test\">test name</alias>\n"+
            "  </names>\n"+
            "</compartment>";
    private String cvFirstIdentifier = "<compartment>\n" +
            "  <names>\n" +
            "    <shortLabel>293t</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    <secondaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "</compartment>";
    private String cvFirstXref = "<compartment>\n" +
            "  <names>\n" +
            "    <shortLabel>293t</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "</compartment>";
    private String cvAttributes = "<compartment>\n" +
            "  <names>\n" +
            "    <shortLabel>293t</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "  <attributeList>\n" +
            "    <attribute name=\"test2\"/>\n"+
            "    <attribute name=\"test3\"/>\n"+
            "  </attributeList>\n"+
            "</compartment>";

    @Test
    public void test_write_cv_no_fullName() throws XMLStreamException, IOException {
        CvTerm compartment = new DefaultCvTerm("293t");

        Xml25CompartmentWriter writer = new Xml25CompartmentWriter(createStreamWriter());
        writer.write(compartment);
        streamWriter.flush();

        Assert.assertEquals(this.cv, output.toString());
    }

    @Test
    public void test_write_cv_fullName() throws XMLStreamException, IOException {
        CvTerm compartment = new DefaultCvTerm("293t");
        compartment.setFullName("Human 293t");

        Xml25CompartmentWriter writer = new Xml25CompartmentWriter(createStreamWriter());
        writer.write(compartment);
        streamWriter.flush();

        Assert.assertEquals(this.cvFullName, output.toString());
    }

    @Test
    public void test_write_cv_aliases() throws XMLStreamException, IOException {
        CvTerm compartment = new DefaultCvTerm("293t");
        compartment.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("synonym"), "unspecified"));
        compartment.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("test"), "test name"));

        Xml25CompartmentWriter writer = new Xml25CompartmentWriter(createStreamWriter());
        writer.write(compartment);
        streamWriter.flush();

        Assert.assertEquals(this.cvAliases, output.toString());
    }

    @Test
    public void test_write_cv_first_identifier() throws XMLStreamException, IOException {
        CvTerm compartment = new DefaultCvTerm("293t");
        compartment.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test"), "MI:0499"));
        compartment.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        compartment.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25CompartmentWriter writer = new Xml25CompartmentWriter(createStreamWriter());
        writer.write(compartment);
        streamWriter.flush();

        Assert.assertEquals(this.cvFirstIdentifier, output.toString());
    }

    @Test
    public void test_write_cv_first_xref() throws XMLStreamException, IOException {
        CvTerm compartment = new DefaultCvTerm("293t");
        compartment.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        compartment.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25CompartmentWriter writer = new Xml25CompartmentWriter(createStreamWriter());
        writer.write(compartment);
        streamWriter.flush();

        Assert.assertEquals(this.cvFirstXref, output.toString());
    }

    @Test
    public void test_write_cv_attributes() throws XMLStreamException, IOException {
        CvTerm compartment = new DefaultCvTerm("293t");
        compartment.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        compartment.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));
        compartment.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test2")));
        compartment.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));
        Xml25CompartmentWriter writer = new Xml25CompartmentWriter(createStreamWriter());
        writer.write(compartment);
        streamWriter.flush();

        Assert.assertEquals(this.cvAttributes, output.toString());
    }
}
