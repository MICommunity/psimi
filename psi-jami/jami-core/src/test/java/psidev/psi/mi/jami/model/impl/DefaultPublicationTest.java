package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.ExperimentUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.util.Date;

/**
 * Unit tester for DefaultPublication
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/13</pre>
 */

public class DefaultPublicationTest {

    @Test
    public void test_create_empty_Publication(){
        Publication pub = new DefaultPublication();

        Assert.assertEquals(CurationDepth.undefined, pub.getCurationDepth());
        Assert.assertNotNull(pub.getAnnotations());
        Assert.assertNotNull(pub.getXrefs());
        Assert.assertNotNull(pub.getIdentifiers());
        Assert.assertNotNull(pub.getAuthors());
        Assert.assertNotNull(pub.getExperiments());
    }

    @Test
    public void test_create_pubmed_Publication(){
        Publication pub = new DefaultPublication(XrefUtils.createPubmedIdentity("123456"));

        Assert.assertEquals(CurationDepth.undefined, pub.getCurationDepth());
        Assert.assertEquals("123456", pub.getPubmedId());
        Assert.assertNull(pub.getDoi());
        Assert.assertNull(pub.getImexId());
        Assert.assertEquals(0, pub.getXrefs().size());
        Assert.assertEquals(1, pub.getIdentifiers().size());
    }

    @Test
    public void test_create_doi_Publication(){
        Publication pub = new DefaultPublication(XrefUtils.createDoiIdentity("111adtest"));

        Assert.assertEquals(CurationDepth.undefined, pub.getCurationDepth());
        Assert.assertEquals("111adtest", pub.getDoi());
        Assert.assertNull(pub.getPubmedId());
        Assert.assertNull(pub.getImexId());
        Assert.assertEquals(0, pub.getXrefs().size());
        Assert.assertEquals(1, pub.getIdentifiers().size());
    }

    @Test
    public void test_create_pubmed_Publication_mimix_intact(){
        Publication pub = new DefaultPublication(XrefUtils.createPubmedIdentity("123456"), CurationDepth.MIMIx, new DefaultSource("intact"));

        Assert.assertEquals(CurationDepth.MIMIx, pub.getCurationDepth());
        Assert.assertEquals("123456", pub.getPubmedId());
        Assert.assertNull(pub.getDoi());
        Assert.assertNull(pub.getImexId());
        Assert.assertEquals(0, pub.getXrefs().size());
        Assert.assertEquals(1, pub.getIdentifiers().size());
        Assert.assertEquals(new DefaultSource("intact"), pub.getSource());
    }

    @Test
    public void test_create_Publication_imex_intact(){
        Publication pub = new DefaultPublication(XrefUtils.createPubmedIdentity("123456"), "IM-1", new DefaultSource("intact"));

        Assert.assertEquals(CurationDepth.IMEx, pub.getCurationDepth());
        Assert.assertEquals("123456", pub.getPubmedId());
        Assert.assertNull(pub.getDoi());
        Assert.assertEquals("IM-1", pub.getImexId());
        Assert.assertEquals(1, pub.getXrefs().size());
        Assert.assertEquals(1, pub.getIdentifiers().size());
        Assert.assertEquals(new DefaultSource("intact"), pub.getSource());
    }

    @Test
    public void test_create_pubmed_Publication2(){
        Publication pub = new DefaultPublication("123456");

        Assert.assertEquals(CurationDepth.undefined, pub.getCurationDepth());
        Assert.assertEquals("123456", pub.getPubmedId());
        Assert.assertNull(pub.getDoi());
        Assert.assertNull(pub.getImexId());
        Assert.assertEquals(0, pub.getXrefs().size());
        Assert.assertEquals(1, pub.getIdentifiers().size());

        Assert.assertEquals(XrefUtils.createPubmedIdentity("123456"), pub.getIdentifiers().iterator().next());
    }

    @Test
    public void create_publication_title_journal(){
        Publication pub = new DefaultPublication("test publication", "proteomics", new Date(System.currentTimeMillis()));

        Assert.assertEquals(CurationDepth.undefined, pub.getCurationDepth());
        Assert.assertNull(pub.getPubmedId());
        Assert.assertNull(pub.getDoi());
        Assert.assertNull(pub.getImexId());
        Assert.assertEquals("test publication", pub.getTitle());
        Assert.assertEquals("proteomics", pub.getJournal());
        Assert.assertNotNull(pub.getPublicationDate());
    }

    @Test
    public void test_set_valid_curation_depth(){
        Publication pub = new DefaultPublication();
        pub.setCurationDepth(CurationDepth.MIMIx);

        Assert.assertEquals(CurationDepth.MIMIx, pub.getCurationDepth());
    }

    @Test
    public void test_set_null_curation_depth(){
        Publication pub = new DefaultPublication();
        pub.setCurationDepth(null);

        Assert.assertEquals(CurationDepth.undefined, pub.getCurationDepth());
    }

    @Test
    public void test_assign_imex_id(){
        Publication pub = new DefaultPublication();
        pub.assignImexId("IM-1");

        Assert.assertEquals(CurationDepth.IMEx, pub.getCurationDepth());
        Assert.assertEquals("IM-1", pub.getImexId());
        Assert.assertEquals(1, pub.getXrefs().size());
        Assert.assertEquals(new DefaultXref(CvTermUtils.createImexDatabase(), "IM-1", CvTermUtils.createImexPrimaryQualifier()), pub.getXrefs().iterator().next());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_assign_imex_id_reset_imex_curation(){
        Publication pub = new DefaultPublication();
        pub.assignImexId("IM-1");

        pub.setCurationDepth(CurationDepth.MIMIx);
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_assign_imex_id_reset_imex_null(){
        Publication pub = new DefaultPublication();
        pub.assignImexId("IM-1");

        pub.assignImexId(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_assign_imex_id_reset_empty_imex(){
        Publication pub = new DefaultPublication();
        pub.assignImexId("IM-1");

        pub.assignImexId("");
    }

    @Test
    public void test_remove_imex_id(){
        // assign IMEx id
        Publication pub = new DefaultPublication();
        pub.assignImexId("IM-1");
        Assert.assertEquals(CurationDepth.IMEx, pub.getCurationDepth());
        Assert.assertEquals("IM-1", pub.getImexId());

        // remove IMEx id xref
        pub.getXrefs().remove(new DefaultXref(CvTermUtils.createImexDatabase(), "IM-1", CvTermUtils.createImexPrimaryQualifier()));
        Assert.assertNull(pub.getImexId());

        // add new IMEx id xref
        pub.getXrefs().add(new DefaultXref(CvTermUtils.createImexDatabase(), "IM-2", CvTermUtils.createImexPrimaryQualifier()));
        Assert.assertEquals("IM-2", pub.getImexId());

        // clear xrefs
        pub.getXrefs().clear();
        Assert.assertNull(pub.getImexId());

        // add new IMEX xref which is not imex-primary
        pub.getXrefs().add(new DefaultXref(CvTermUtils.createImexDatabase(), "IM-2"));
        Assert.assertNull(pub.getImexId());
    }

    @Test
    public void test_add_remove_pubmed(){
        Publication pub = new DefaultPublication();

        Assert.assertEquals(CurationDepth.undefined, pub.getCurationDepth());
        Assert.assertNull(pub.getPubmedId());

        pub.getIdentifiers().add(XrefUtils.createPubmedIdentity("123456"));
        Assert.assertEquals("123456", pub.getPubmedId());

        pub.getIdentifiers().remove(XrefUtils.createPubmedIdentity("123456"));
        Assert.assertNull(pub.getPubmedId());

        pub.getIdentifiers().add(XrefUtils.createXref("pubmed", "123456"));
        Assert.assertEquals("123456", pub.getPubmedId());

        pub.getIdentifiers().clear();
        Assert.assertNull(pub.getPubmedId());

        pub.setPubmedId("123456");
        Assert.assertEquals("123456", pub.getPubmedId());
        Assert.assertEquals(1, pub.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createPubmedIdentity("123456"), pub.getIdentifiers().iterator().next());

        pub.getIdentifiers().add(XrefUtils.createXref("pubmed", "123457"));
        Assert.assertEquals("123456", pub.getPubmedId());
        Assert.assertEquals(2, pub.getIdentifiers().size());

        pub.getIdentifiers().clear();
        Assert.assertNull(pub.getPubmedId());
        Assert.assertEquals(0, pub.getIdentifiers().size());
    }

    @Test
    public void test_add_remove_doi(){
        Publication pub = new DefaultPublication();

        Assert.assertEquals(CurationDepth.undefined, pub.getCurationDepth());
        Assert.assertNull(pub.getDoi());

        pub.getIdentifiers().add(XrefUtils.createDoiIdentity("11aa1"));
        Assert.assertEquals("11aa1", pub.getDoi());

        pub.getIdentifiers().remove(XrefUtils.createDoiIdentity("11aa1"));
        Assert.assertNull(pub.getDoi());

        pub.getIdentifiers().add(XrefUtils.createXref("doi", "11aa1"));
        Assert.assertEquals("11aa1", pub.getDoi());

        pub.getIdentifiers().clear();
        Assert.assertNull(pub.getDoi());

        pub.setDoi("11aa1");
        Assert.assertEquals("11aa1", pub.getDoi());
        Assert.assertEquals(1, pub.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createDoiIdentity("11aa1"), pub.getIdentifiers().iterator().next());

        pub.getIdentifiers().add(XrefUtils.createXref("doi", "11aa2"));
        Assert.assertEquals("11aa1", pub.getDoi());
        Assert.assertEquals(2, pub.getIdentifiers().size());

        pub.getIdentifiers().clear();
        Assert.assertNull(pub.getDoi());
        Assert.assertEquals(0, pub.getIdentifiers().size());
    }

    @Test
    public void test_add_remove_experiments(){
        Publication pub = new DefaultPublication();
        Experiment exp = ExperimentUtils.createExperimentWithoutPublication();

        Assert.assertNull(exp.getPublication());

        pub.addExperiment(exp);
        Assert.assertEquals(pub, exp.getPublication());
        Assert.assertEquals(1, pub.getExperiments().size());

        pub.removeExperiment(exp);
        Assert.assertNull(exp.getPublication());
        Assert.assertEquals(0, pub.getExperiments().size());

        pub.getExperiments().add(exp);
        Assert.assertNull(exp.getPublication());
        Assert.assertEquals(1, pub.getExperiments().size());

        pub.getExperiments().remove(exp);
        Assert.assertNull(exp.getPublication());
        Assert.assertEquals(0, pub.getExperiments().size());
    }
}
