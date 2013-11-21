package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.xml.io.writer.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for Xml25PrimaryRefWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/11/13</pre>
 */

public class Xml25PrimaryXrefWriterTest extends AbstractXml25WriterTest{

    private String xref_no_database_ac ="<primaryRef db=\"uniprotkb\" id=\"P12345\" refType=\"identity\" refTypeAc=\"MI:0356\"/>";
    private String xref_no_reftype ="<primaryRef db=\"uniprotkb\" dbAc=\"MI:0486\" id=\"P12345\"/>";
    private String xref_no_reftype_ac ="<primaryRef db=\"uniprotkb\" dbAc=\"MI:0486\" id=\"P12345\" refType=\"identity\"/>";
    private String xref ="<primaryRef db=\"uniprotkb\" dbAc=\"MI:0486\" id=\"P12345\" refType=\"identity\" refTypeAc=\"MI:0356\"/>";
    private String xref_version ="<primaryRef db=\"uniprotkb\" dbAc=\"MI:0486\" id=\"P12345\" version=\"2.4\" refType=\"identity\" refTypeAc=\"MI:0356\"/>";

    @Test
    public void test_write_xref_null() throws XMLStreamException, IOException {

        Xml25PrimaryXrefWriter writer = new Xml25PrimaryXrefWriter(createStreamWriter());
        writer.write(null);
        streamWriter.flush();

        Assert.assertEquals("", output.toString());
    }

    @Test
    public void test_write_xref_do_database_ac() throws XMLStreamException, IOException {
        Xref ref = new DefaultXref(new DefaultCvTerm(Xref.UNIPROTKB), "P12345", CvTermUtils.createIdentityQualifier());

        Xml25PrimaryXrefWriter writer = new Xml25PrimaryXrefWriter(createStreamWriter());
        writer.write(ref);
        streamWriter.flush();

        Assert.assertEquals(xref_no_database_ac, output.toString());
    }

    @Test
    public void test_write_xref_no_reftype() throws XMLStreamException, IOException {
        Xref ref = new DefaultXref(new DefaultCvTerm(Xref.UNIPROTKB, Xref.UNIPROTKB_MI), "P12345");

        Xml25PrimaryXrefWriter writer = new Xml25PrimaryXrefWriter(createStreamWriter());
        writer.write(ref);
        streamWriter.flush();

        Assert.assertEquals(xref_no_reftype, output.toString());
    }

    @Test
    public void test_write_xref_no_reftype_ac() throws XMLStreamException, IOException {
        Xref ref = new DefaultXref(new DefaultCvTerm(Xref.UNIPROTKB, Xref.UNIPROTKB_MI), "P12345", new DefaultCvTerm(Xref.IDENTITY));

        Xml25PrimaryXrefWriter writer = new Xml25PrimaryXrefWriter(createStreamWriter());
        writer.write(ref);
        streamWriter.flush();

        Assert.assertEquals(xref_no_reftype_ac, output.toString());
    }

    @Test
    public void test_write_xref() throws XMLStreamException, IOException {
        Xref ref = new DefaultXref(new DefaultCvTerm(Xref.UNIPROTKB, Xref.UNIPROTKB_MI), "P12345", CvTermUtils.createIdentityQualifier());

        Xml25PrimaryXrefWriter writer = new Xml25PrimaryXrefWriter(createStreamWriter());
        writer.write(ref);
        streamWriter.flush();

        Assert.assertEquals(xref, output.toString());
    }

    @Test
    public void test_write_xref_version() throws XMLStreamException, IOException {
        Xref ref = new DefaultXref(new DefaultCvTerm(Xref.UNIPROTKB, Xref.UNIPROTKB_MI), "P12345", "2.4", CvTermUtils.createIdentityQualifier());

        Xml25PrimaryXrefWriter writer = new Xml25PrimaryXrefWriter(createStreamWriter());
        writer.write(ref);
        streamWriter.flush();

        Assert.assertEquals(xref_version, output.toString());
    }

    @Test
    public void test_write_xref_default_qualifier() throws XMLStreamException, IOException {
        Xref ref = new DefaultXref(new DefaultCvTerm(Xref.UNIPROTKB, Xref.UNIPROTKB_MI), "P12345");

        Xml25PrimaryXrefWriter writer = new Xml25PrimaryXrefWriter(createStreamWriter());
        writer.setDefaultRefType("identity");
        writer.setDefaultRefTypeAc("MI:0356");
        writer.write(ref);
        streamWriter.flush();

        Assert.assertEquals(xref, output.toString());
    }
}
