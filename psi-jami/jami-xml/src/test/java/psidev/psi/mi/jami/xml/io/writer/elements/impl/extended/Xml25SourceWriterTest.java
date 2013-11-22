package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Source;
import psidev.psi.mi.jami.xml.extension.XmlSource;
import psidev.psi.mi.jami.xml.io.writer.AbstractXml25WriterTest;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.text.ParseException;

/**
 * Unit tester for Xml25SourceWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/13</pre>
 */

public class Xml25SourceWriterTest extends AbstractXml25WriterTest {
    private String source ="<source releaseDate=\"2013-09-02Z\">\n" +
            "  <names>\n" +
            "    <shortLabel>intact</shortLabel>\n"+
            "  </names>\n"+
            "</source>";
    private String source_fullName ="<source releaseDate=\"2013-09-02Z\">\n" +
            "  <names>\n" +
            "    <shortLabel>intact</shortLabel>\n"+
            "    <fullName>IntAct</fullName>\n"+
            "  </names>\n"+
            "</source>";
    private String source_aliases ="<source releaseDate=\"2013-09-02Z\">\n" +
            "  <names>\n" +
            "    <shortLabel>intact</shortLabel>\n"+
            "    <alias type=\"synonym\">intact synonym</alias>\n"+
            "    <alias>test alias</alias>\n"+
            "  </names>\n"+
            "</source>";
    private String source_bibref ="<source releaseDate=\"2013-09-02Z\">\n" +
            "  <names>\n" +
            "    <shortLabel>intact</shortLabel>\n"+
            "  </names>\n"+
            "  <bibref>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </bibref>\n"+
            "</source>";
    private String source_identifier ="<source releaseDate=\"2013-09-02Z\">\n" +
            "  <names>\n" +
            "    <shortLabel>intact</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    <secondaryRef db=\"test2\" id=\"12346\"/>\n" +
            "  </xref>\n"+
            "</source>";
    private String source_xref ="<source releaseDate=\"2013-09-02Z\">\n" +
            "  <names>\n" +
            "    <shortLabel>intact</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test\" id=\"xxxxxx\"/>\n"+
            "    <secondaryRef db=\"test2\" id=\"12346\"/>\n" +
            "  </xref>\n"+
            "</source>";
    private String source_attributes ="<source releaseDate=\"2013-09-02Z\">\n" +
            "  <names>\n" +
            "    <shortLabel>intact</shortLabel>\n"+
            "  </names>\n"+
            "  <attributeList>\n" +
            "    <attribute name=\"url\" nameAc=\"MI:0614\">http://www.ebi.ac.uk/intact/</attribute>\n"+
            "    <attribute name=\"postaladdress\">test address</attribute>\n"+
            "    <attribute name=\"test3\"/>\n"+
            "  </attributeList>\n"+
            "</source>";
    private String source_no_release ="<source>\n" +
            "  <names>\n" +
            "    <shortLabel>intact</shortLabel>\n"+
            "  </names>\n"+
            "</source>";
    private String source_release ="<source release=\"release_test\">\n" +
            "  <names>\n" +
            "    <shortLabel>intact</shortLabel>\n"+
            "  </names>\n"+
            "</source>";

    @Test
    public void test_write_source() throws XMLStreamException, IOException {
        ExtendedPsi25Source source = new XmlSource("intact");
        try {
            DatatypeFactory datatypeFactory = null;
            datatypeFactory = DatatypeFactory.newInstance();
            source.setReleaseDate(datatypeFactory.newXMLGregorianCalendar("2013-09-02+00:00"));

        } catch (DatatypeConfigurationException e) {
            System.out.println(e);
        }

        Xml25SourceWriter writer = new Xml25SourceWriter(createStreamWriter());
        writer.write(source);
        streamWriter.flush();

        Assert.assertEquals(this.source, output.toString());
    }

    @Test
    public void test_write_source_fullname() throws XMLStreamException, IOException {
        ExtendedPsi25Source source = new XmlSource("intact");
        source.setFullName("IntAct");
        try {
            DatatypeFactory datatypeFactory = null;
            datatypeFactory = DatatypeFactory.newInstance();
            source.setReleaseDate(datatypeFactory.newXMLGregorianCalendar("2013-09-02+00:00"));

        } catch (DatatypeConfigurationException e) {
            System.out.println(e);
        }

        Xml25SourceWriter writer = new Xml25SourceWriter(createStreamWriter());
        writer.write(source);
        streamWriter.flush();

        Assert.assertEquals(source_fullName, output.toString());
    }

    @Test
    public void test_write_source_aliases() throws XMLStreamException, IOException {
        ExtendedPsi25Source source = new XmlSource("intact");
        source.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("synonym"),"intact synonym"));
        source.getSynonyms().add(new DefaultAlias("test alias"));

        Xml25SourceWriter writer = new Xml25SourceWriter(createStreamWriter());
        try {
            DatatypeFactory datatypeFactory = null;
            datatypeFactory = DatatypeFactory.newInstance();
            writer.setDefaultReleaseDate(datatypeFactory.newXMLGregorianCalendar("2013-09-02+00:00"));

        } catch (DatatypeConfigurationException e) {
            System.out.println(e);
        }
        writer.write(source);
        streamWriter.flush();

        Assert.assertEquals(source_aliases, output.toString());
    }

    @Test
    public void test_write_source_bibref() throws XMLStreamException, IOException {
        ExtendedPsi25Source source = new XmlSource("intact");
        source.setPublication(new DefaultPublication("xxxxxx"));
        try {
            DatatypeFactory datatypeFactory = null;
            datatypeFactory = DatatypeFactory.newInstance();
            source.setReleaseDate(datatypeFactory.newXMLGregorianCalendar("2013-09-02+00:00"));

        } catch (DatatypeConfigurationException e) {
            System.out.println(e);
        }

        Xml25SourceWriter writer = new Xml25SourceWriter(createStreamWriter());
        writer.write(source);
        streamWriter.flush();

        Assert.assertEquals(source_bibref, output.toString());
    }

    @Test
    public void test_write_source_first_identifier() throws XMLStreamException, IOException {
        ExtendedPsi25Source source = new XmlSource("intact");
        source.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test"),"xxxxxx"));
        source.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"),"12346"));

        Xml25SourceWriter writer = new Xml25SourceWriter(createStreamWriter());
        try {
            DatatypeFactory datatypeFactory = null;
            datatypeFactory = DatatypeFactory.newInstance();
            writer.setDefaultReleaseDate(datatypeFactory.newXMLGregorianCalendar("2013-09-02+00:00"));

        } catch (DatatypeConfigurationException e) {
            System.out.println(e);
        }
        writer.write(source);
        streamWriter.flush();

        Assert.assertEquals(source_identifier, output.toString());
    }

    @Test
    public void test_write_source_first_xref() throws XMLStreamException, IOException {
        ExtendedPsi25Source source = new XmlSource("intact");
        source.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"),"xxxxxx"));
        source.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"),"12346"));
        try {
            DatatypeFactory datatypeFactory = null;
            datatypeFactory = DatatypeFactory.newInstance();
            source.setReleaseDate(datatypeFactory.newXMLGregorianCalendar("2013-09-02+00:00"));

        } catch (DatatypeConfigurationException e) {
            System.out.println(e);
        }

        Xml25SourceWriter writer = new Xml25SourceWriter(createStreamWriter());
        writer.write(source);
        streamWriter.flush();

        Assert.assertEquals(source_xref, output.toString());
    }

    @Test
    public void test_write_source_attributes() throws XMLStreamException, IOException, ParseException {
        ExtendedPsi25Source source = new XmlSource("intact");
        source.setUrl("http://www.ebi.ac.uk/intact/");
        source.setPostalAddress("test address");
        source.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));

        Xml25SourceWriter writer = new Xml25SourceWriter(createStreamWriter());
        try {
            DatatypeFactory datatypeFactory = null;
            datatypeFactory = DatatypeFactory.newInstance();
            writer.setDefaultReleaseDate(datatypeFactory.newXMLGregorianCalendar("2013-09-02+00:00"));

        } catch (DatatypeConfigurationException e) {
            System.out.println(e);
        }
        writer.write(source);
        streamWriter.flush();

        Assert.assertEquals(source_attributes, output.toString());
    }

    @Test
    public void test_write_source_no_release() throws XMLStreamException, IOException {
        ExtendedPsi25Source source = new XmlSource("intact");

        Xml25SourceWriter writer = new Xml25SourceWriter(createStreamWriter());
        writer.write(source);
        streamWriter.flush();

        Assert.assertEquals(this.source_no_release, output.toString());
    }

    @Test
    public void test_write_source_release() throws XMLStreamException, IOException {
        ExtendedPsi25Source source = new XmlSource("intact");
        source.setRelease("release_test");
        Xml25SourceWriter writer = new Xml25SourceWriter(createStreamWriter());
        writer.write(source);
        streamWriter.flush();

        Assert.assertEquals(this.source_release, output.toString());
    }
}
