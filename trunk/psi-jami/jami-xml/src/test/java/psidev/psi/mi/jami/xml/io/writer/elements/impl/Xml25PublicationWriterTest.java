package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.text.ParseException;

/**
 * Unit tester for Xml25PublicationWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/13</pre>
 */

public class Xml25PublicationWriterTest extends AbstractXml25WriterTest {
    private String bibRef_xref_pubmed ="<bibref>\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    <secondaryRef db=\"test\" id=\"12345\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    <secondaryRef db=\"test2\" id=\"12346\"/>\n" +
            "  </xref>\n"+
            "</bibref>";
    private String bibRef_xref_pubmed_primary ="<bibref>\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"primary-reference\" refTypeAc=\"MI:0358\"/>\n"+
            "    <secondaryRef db=\"test\" id=\"12345\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    <secondaryRef db=\"test2\" id=\"12346\"/>\n" +
            "  </xref>\n"+
            "</bibref>";
    private String bibRef_xref_doi ="<bibref>\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"doi\" dbAc=\"MI:0574\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    <secondaryRef db=\"test\" id=\"12345\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    <secondaryRef db=\"test2\" id=\"12346\"/>\n" +
            "  </xref>\n"+
            "</bibref>";
    private String bibRef_xref_doi_primary ="<bibref>\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"doi\" dbAc=\"MI:0574\" id=\"xxxxxx\" refType=\"primary-reference\" refTypeAc=\"MI:0358\"/>\n"+
            "    <secondaryRef db=\"test\" id=\"12345\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    <secondaryRef db=\"test2\" id=\"12346\"/>\n" +
            "  </xref>\n"+
            "</bibref>";
    private String bibRef_xref_first_identifier ="<bibref>\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"test\" id=\"xxxxxx\" refType=\"secondary\"/>\n"+
            "    <secondaryRef db=\"test\" id=\"12345\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    <secondaryRef db=\"test2\" id=\"12346\"/>\n" +
            "  </xref>\n"+
            "</bibref>";
    private String bibRef_xref_first_identifier_add_qualifier ="<bibref>\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"test\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    <secondaryRef db=\"test\" id=\"12345\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    <secondaryRef db=\"test2\" id=\"12346\"/>\n" +
            "  </xref>\n"+
            "</bibref>";
    private String bibRef_attributes ="<bibref>\n" +
            "  <attributeList>\n" +
            "    <attribute name=\"publication title\" nameAc=\"MI:1091\">test title</attribute>\n"+
            "    <attribute name=\"journal\" nameAc=\"MI:0885\">test journal</attribute>\n"+
            "    <attribute name=\"publication year\" nameAc=\"MI:0886\">2006</attribute>\n"+
            "    <attribute name=\"curation depth\" nameAc=\"MI:0955\">imex curation</attribute>\n"+
            "    <attribute name=\"imex curation\" nameAc=\"MI:0959\"/>\n"+
            "    <attribute name=\"author-list\" nameAc=\"MI:0636\">author1, author2, author3</attribute>\n"+
            "    <attribute name=\"test3\"/>\n"+
            "  </attributeList>\n"+
            "</bibref>";
    private String bibRef_xref_first_xref ="<bibref>\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"test\" id=\"xxxxxx\" refType=\"see-also\"/>\n"+
            "    <secondaryRef db=\"test\" id=\"12345\"/>\n" +
            "    <secondaryRef db=\"test2\" id=\"12346\"/>\n" +
            "  </xref>\n"+
            "</bibref>";
    private String bibRef_xref_first_xref_no_qualifier ="<bibref>\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"test\" id=\"xxxxxx\"/>\n"+
            "    <secondaryRef db=\"test\" id=\"12345\"/>\n" +
            "    <secondaryRef db=\"test2\" id=\"12346\"/>\n" +
            "  </xref>\n"+
            "</bibref>";

    @Test
    public void test_write_publication_pubmed() throws XMLStreamException, IOException {
        Publication pub = new DefaultPublication("xxxxxx");
        pub.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test"),"12345"));
        pub.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"),"12346"));
        pub.setTitle("test title");

        Xml25PublicationWriter writer = new Xml25PublicationWriter(createStreamWriter());
        writer.write(pub);
        streamWriter.flush();

        Assert.assertEquals(bibRef_xref_pubmed, output.toString());
    }

    @Test
    public void test_write_publication_pubmed_primary() throws XMLStreamException, IOException {
        Publication pub = new DefaultPublication();
        pub.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test"),"12345"));
        pub.getIdentifiers().add(new DefaultXref(new DefaultCvTerm(Xref.PUBMED, Xref.PUBMED_MI), "xxxxxx"));
        pub.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"),"12346"));
        pub.setTitle("test title");

        Xml25PublicationWriter writer = new Xml25PublicationWriter(createStreamWriter());
        writer.write(pub);
        streamWriter.flush();

        Assert.assertEquals(bibRef_xref_pubmed_primary, output.toString());
    }

    @Test
    public void test_write_publication_doi() throws XMLStreamException, IOException {
        Publication pub = new DefaultPublication();
        pub.setDoi("xxxxxx");
        pub.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test"),"12345"));
        pub.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"),"12346"));
        pub.setTitle("test title");

        Xml25PublicationWriter writer = new Xml25PublicationWriter(createStreamWriter());
        writer.write(pub);
        streamWriter.flush();

        Assert.assertEquals(bibRef_xref_doi, output.toString());
    }

    @Test
    public void test_write_publication_doi_primary() throws XMLStreamException, IOException {
        Publication pub = new DefaultPublication();
        pub.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test"),"12345"));
        pub.getIdentifiers().add(new DefaultXref(new DefaultCvTerm(Xref.DOI, Xref.DOI_MI), "xxxxxx"));
        pub.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"),"12346"));
        pub.setTitle("test title");

        Xml25PublicationWriter writer = new Xml25PublicationWriter(createStreamWriter());
        writer.write(pub);
        streamWriter.flush();

        Assert.assertEquals(bibRef_xref_doi_primary, output.toString());
    }

    @Test
    public void test_write_publication_first_identifier() throws XMLStreamException, IOException {
        Publication pub = new DefaultPublication();
        pub.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test"), "xxxxxx", new DefaultCvTerm("secondary")));
        pub.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test"),"12345"));
        pub.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"),"12346"));
        pub.setTitle("test title");

        Xml25PublicationWriter writer = new Xml25PublicationWriter(createStreamWriter());
        writer.write(pub);
        streamWriter.flush();

        Assert.assertEquals(bibRef_xref_first_identifier, output.toString());
    }

    @Test
    public void test_write_publication_first_identifier_identity() throws XMLStreamException, IOException {
        Publication pub = new DefaultPublication();
        pub.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test"), "xxxxxx"));
        pub.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test"),"12345"));
        pub.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"),"12346"));
        pub.setTitle("test title");

        Xml25PublicationWriter writer = new Xml25PublicationWriter(createStreamWriter());
        writer.write(pub);
        streamWriter.flush();

        Assert.assertEquals(bibRef_xref_first_identifier_add_qualifier, output.toString());
    }

    @Test
    public void test_write_publication_attributes() throws XMLStreamException, IOException, ParseException {
        Publication pub = new DefaultPublication();
        pub.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxxxx", new DefaultCvTerm("see-also")));
        pub.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"),"12345"));
        pub.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"),"12346"));
        pub.setTitle("test title");
        pub.setJournal("test journal");
        pub.setPublicationDate(PsiXml25Utils.YEAR_FORMAT.parse("2006"));
        pub.setCurationDepth(CurationDepth.IMEx);
        pub.getAuthors().add("author1");
        pub.getAuthors().add("author2");
        pub.getAuthors().add("author3");

        pub.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));

        Xml25PublicationWriter writer = new Xml25PublicationWriter(createStreamWriter());
        writer.write(pub);
        streamWriter.flush();

        Assert.assertEquals(bibRef_attributes, output.toString());
    }

    @Test
    public void test_write_publication_first_xref() throws XMLStreamException, IOException {
        Publication pub = new DefaultPublication();
        pub.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxxxx", new DefaultCvTerm("see-also")));
        pub.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"),"12345"));
        pub.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"),"12346"));

        Xml25PublicationWriter writer = new Xml25PublicationWriter(createStreamWriter());
        writer.write(pub);
        streamWriter.flush();

        Assert.assertEquals(bibRef_xref_first_xref, output.toString());
    }

    @Test
    public void test_write_publication_first_xref_no_qualifier() throws XMLStreamException, IOException {
        Publication pub = new DefaultPublication();
        pub.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxxxx"));
        pub.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"),"12345"));
        pub.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"),"12346"));

        Xml25PublicationWriter writer = new Xml25PublicationWriter(createStreamWriter());
        writer.write(pub);
        streamWriter.flush();

        Assert.assertEquals(bibRef_xref_first_xref_no_qualifier, output.toString());
    }
}
