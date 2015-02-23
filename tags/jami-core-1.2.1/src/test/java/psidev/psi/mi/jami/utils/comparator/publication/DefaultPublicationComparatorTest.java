package psidev.psi.mi.jami.utils.comparator.publication;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.util.Date;

/**
 * Unit tester for DefaultPublicationComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/13</pre>
 */

public class DefaultPublicationComparatorTest {

    @Test
    public void test_publication_null_after() throws Exception {
        Publication pub1 = null;
        Publication pub2 = new DefaultPublication();

        Assert.assertFalse(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_imexid_null_after() throws Exception {
        Publication pub1 = new DefaultPublication();
        Publication pub2 = new DefaultPublication();
        pub2.assignImexId("IM-1");

        Assert.assertFalse(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_different_imexid() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.assignImexId("IM-2");
        Publication pub2 = new DefaultPublication();
        pub2.assignImexId("IM-1");

        Assert.assertFalse(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_same_imexid() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.assignImexId("IM-1");
        Publication pub2 = new DefaultPublication();
        pub2.assignImexId("IM-1");

        Assert.assertTrue(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_same_imexid_different_pubmed_equality() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.assignImexId("IM-1");
        pub1.setPubmedId("123456");
        Publication pub2 = new DefaultPublication();
        pub2.assignImexId("IM-1");
        pub2.setPubmedId("123457");

        Assert.assertTrue(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_pubmedId_null_after() throws Exception {
        Publication pub1 = new DefaultPublication();
        Publication pub2 = new DefaultPublication();
        pub2.setPubmedId("12345");

        Assert.assertFalse(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_one_imexId_different_pubmedId() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.assignImexId("IM-1");
        pub1.setPubmedId("12346");
        Publication pub2 = new DefaultPublication();
        pub2.setPubmedId("12345");

        Assert.assertFalse(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_one_imexId_same_pubmedId() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.assignImexId("IM-1");
        pub1.setPubmedId("12346");
        Publication pub2 = new DefaultPublication();
        pub2.setPubmedId("12346");

        Assert.assertTrue(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_same_pubmedid_different_doi_equality() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.assignImexId("IM-1");
        pub1.setPubmedId("123456");
        Publication pub2 = new DefaultPublication();
        pub2.setPubmedId("123456");
        pub2.setDoi("11aa2");

        Assert.assertTrue(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_doi_null_after() throws Exception {
        Publication pub1 = new DefaultPublication();
        Publication pub2 = new DefaultPublication();
        pub2.setDoi("11aa12");

        Assert.assertFalse(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_one_pubmedId_different_doi() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.setPubmedId("12346");
        pub1.setDoi("11a112");
        Publication pub2 = new DefaultPublication();
        pub2.setDoi("11a111");

        Assert.assertFalse(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_one_pubmedId_same_doi() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.setPubmedId("12346");
        pub1.setDoi("11a112");
        Publication pub2 = new DefaultPublication();
        pub2.setDoi("11a112");

        Assert.assertTrue(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_same_doi_different_identifiers_equality() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.assignImexId("IM-1");
        pub1.setPubmedId("123456");
        pub1.setDoi("11aa2");
        Publication pub2 = new DefaultPublication();
        pub2.setDoi("11aa2");
        pub2.getIdentifiers().add(XrefUtils.createXref("test", "TEST-1"));
        Assert.assertTrue(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_title_null_after() throws Exception {
        Publication pub1 = new DefaultPublication();
        Publication pub2 = new DefaultPublication();
        pub2.setTitle("title test");

        Assert.assertFalse(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_one_identifier_same_titles_before() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.setTitle("title test");
        pub1.setPubmedId("12345");
        Publication pub2 = new DefaultPublication();
        pub2.setTitle("title test");

        Assert.assertFalse(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_same_titles_case_insensitive() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.setTitle("title TEst");
        Publication pub2 = new DefaultPublication();
        pub2.setTitle("Title test");

        Assert.assertTrue(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_one_doi_at_least_one_identifier_equality() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.setDoi("11a112");
        pub1.getIdentifiers().add(XrefUtils.createXref("test", "TEST-2"));
        Publication pub2 = new DefaultPublication();
        pub2.setDoi("11a112");
        pub2.getIdentifiers().add(XrefUtils.createXref("test", "TEST-1"));
        pub2.getIdentifiers().add(XrefUtils.createXref("test", "TEST-2"));

        Assert.assertTrue(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_at_least_one_identifier_different_title_equality() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.assignImexId("IM-1");
        pub1.setPubmedId("123456");
        pub1.setDoi("11aa2");
        pub1.setTitle("test title 2");
        pub1.getIdentifiers().add(XrefUtils.createXref("test", "TEST-2"));
        Publication pub2 = new DefaultPublication();
        pub2.getIdentifiers().add(XrefUtils.createXref("test", "TEST-1"));
        pub2.getIdentifiers().add(XrefUtils.createXref("test", "TEST-2"));
        pub2.setTitle("Test title");

        Assert.assertTrue(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_empty_identifiers_null_after() throws Exception {
        Publication pub1 = new DefaultPublication();
        Publication pub2 = new DefaultPublication();
        pub2.getIdentifiers().add(XrefUtils.createXref("test", "TEST-1"));

        Assert.assertFalse(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_one_doi_different_identifiers() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.setDoi("11a112");
        pub1.getIdentifiers().add(XrefUtils.createXref("test", "TEST-2"));
        pub1.setTitle("test title");
        Publication pub2 = new DefaultPublication();
        pub2.getIdentifiers().add(XrefUtils.createXref("test", "TEST-1"));

        Assert.assertFalse(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_same_titles_journal_insensitive() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.setTitle("title TEst");
        pub1.setJournal("PROTEOMICS");
        Publication pub2 = new DefaultPublication();
        pub2.setTitle("Title test");
        pub2.setJournal("proteomics");

        Assert.assertTrue(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_same_titles_different_journals() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.setTitle("title TEst ");
        pub1.setJournal("PROTEOMICS");
        Publication pub2 = new DefaultPublication();
        pub2.setTitle("Title test");
        pub2.setJournal(" cell ");

        Assert.assertFalse(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_same_titles_journal_date() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.setTitle("title TEst");
        pub1.setJournal("PROTEOMICS");
        Publication pub2 = new DefaultPublication();
        pub2.setTitle("Title test");
        pub2.setJournal("proteomics");

        Date date = new Date();
        pub1.setPublicationDate(date);
        pub2.setPublicationDate(date);

        Assert.assertTrue(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_same_titles_journals_different_date() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.setTitle("title TEst");
        pub1.setJournal("PROTEOMICS");
        pub1.setPubmedId("12345");
        pub1.setPublicationDate(new Date(System.currentTimeMillis()));
        Publication pub2 = new DefaultPublication();
        pub2.setTitle("Title test");
        pub2.setJournal("PROTEOMICS");
        pub2.setPublicationDate(new Date(System.currentTimeMillis()));

        Assert.assertFalse(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_same_authors() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.getAuthors().add("author1");
        pub1.getAuthors().add("author2");
        Publication pub2 = new DefaultPublication();
        pub2.getAuthors().add("author1");
        pub2.getAuthors().add("author2");

        Assert.assertTrue(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_different_authors() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.getAuthors().add("author1");
        Publication pub2 = new DefaultPublication();
        pub2.getAuthors().add("author1");
        pub2.getAuthors().add("author2");

        Assert.assertFalse(DefaultPublicationComparator.areEquals(pub1, pub2));
    }

    @Test
    public void test_same_authors_different_order() throws Exception {
        Publication pub1 = new DefaultPublication();
        pub1.getAuthors().add("author2");
        pub1.getAuthors().add("author1");
        Publication pub2 = new DefaultPublication();
        pub2.getAuthors().add("author1");
        pub2.getAuthors().add("author2");

        Assert.assertFalse(DefaultPublicationComparator.areEquals(pub1, pub2));
    }
}
