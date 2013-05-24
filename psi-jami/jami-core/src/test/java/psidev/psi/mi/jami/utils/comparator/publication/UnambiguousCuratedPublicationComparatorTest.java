package psidev.psi.mi.jami.utils.comparator.publication;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.model.impl.DefaultSource;

import java.util.Date;

/**
 * Unit tester for UnambiguousCuratedPublication
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/13</pre>
 */

public class UnambiguousCuratedPublicationComparatorTest {

    private UnambiguousCuratedPublicationComparator comparator = new UnambiguousCuratedPublicationComparator();

    @Test
    public void test_compare_same_pubmed_different_curation_depth(){
        Publication pub1 = new DefaultPublication();
        pub1.setPubmedId("12345");
        pub1.setCurationDepth(CurationDepth.IMEx);
        Publication pub2 = new DefaultPublication();
        pub2.setPubmedId("12345");
        pub2.setCurationDepth(CurationDepth.MIMIx);

        Assert.assertTrue(comparator.compare(pub1, pub2) < 0);
        Assert.assertTrue(comparator.compare(pub2, pub1) > 0);

        Assert.assertFalse(UnambiguousCuratedPublicationComparator.areEquals(pub1, pub2));
        Assert.assertTrue(UnambiguousCuratedPublicationComparator.hashCode(pub1) != UnambiguousCuratedPublicationComparator.hashCode(pub2));
    }

    @Test
    public void test_compare_same_pubmed_same_curation_depth(){
        Publication pub1 = new DefaultPublication();
        pub1.setPubmedId("12345");
        pub1.setCurationDepth(CurationDepth.IMEx);
        Publication pub2 = new DefaultPublication();
        pub2.setPubmedId("12345");
        pub2.setCurationDepth(CurationDepth.IMEx);

        Assert.assertTrue(comparator.compare(pub1, pub2) == 0);
        Assert.assertTrue(comparator.compare(pub2, pub1) == 0);

        Assert.assertTrue(UnambiguousCuratedPublicationComparator.areEquals(pub1, pub2));
        Assert.assertTrue(UnambiguousCuratedPublicationComparator.hashCode(pub1) == UnambiguousCuratedPublicationComparator.hashCode(pub2));
    }

    @Test
    public void test_compare_same_pubmed_different_source(){
        Publication pub1 = new DefaultPublication();
        pub1.setPubmedId("12345");
        pub1.setCurationDepth(CurationDepth.IMEx);
        pub1.setSource(new DefaultSource("intact"));
        Publication pub2 = new DefaultPublication();
        pub2.setPubmedId("12345");
        pub2.setCurationDepth(CurationDepth.IMEx);
        pub2.setSource(new DefaultSource("mint"));

        Assert.assertTrue(comparator.compare(pub1, pub2) < 0);
        Assert.assertTrue(comparator.compare(pub2, pub1) > 0);

        Assert.assertFalse(UnambiguousCuratedPublicationComparator.areEquals(pub1, pub2));
        Assert.assertTrue(UnambiguousCuratedPublicationComparator.hashCode(pub1) != UnambiguousCuratedPublicationComparator.hashCode(pub2));
    }

    @Test
    public void test_compare_same_pubmed_same_source(){
        Publication pub1 = new DefaultPublication();
        pub1.setPubmedId("12345");
        pub1.setCurationDepth(CurationDepth.IMEx);
        pub1.setSource(new DefaultSource("intact"));
        Publication pub2 = new DefaultPublication();
        pub2.setPubmedId("12345");
        pub2.setCurationDepth(CurationDepth.IMEx);
        pub2.setSource(new DefaultSource("intact"));

        Assert.assertTrue(comparator.compare(pub1, pub2) == 0);
        Assert.assertTrue(comparator.compare(pub2, pub1) == 0);

        Assert.assertTrue(UnambiguousCuratedPublicationComparator.areEquals(pub1, pub2));
        Assert.assertTrue(UnambiguousCuratedPublicationComparator.hashCode(pub1) == UnambiguousCuratedPublicationComparator.hashCode(pub2));
    }

    @Test
    public void test_compare_same_pubmed_different_released_date(){
        Publication pub1 = new DefaultPublication();
        pub1.setPubmedId("12345");
        pub1.setCurationDepth(CurationDepth.IMEx);
        pub1.setSource(new DefaultSource("intact"));
        pub1.setReleasedDate(new Date(System.currentTimeMillis()-1));
        Publication pub2 = new DefaultPublication();
        pub2.setPubmedId("12345");
        pub2.setCurationDepth(CurationDepth.IMEx);
        pub2.setSource(new DefaultSource("intact"));
        pub2.setReleasedDate(new Date(System.currentTimeMillis()));

        Assert.assertTrue(comparator.compare(pub1, pub2) < 0);
        Assert.assertTrue(comparator.compare(pub2, pub1) > 0);

        Assert.assertFalse(UnambiguousCuratedPublicationComparator.areEquals(pub1, pub2));
        Assert.assertTrue(UnambiguousCuratedPublicationComparator.hashCode(pub1) != UnambiguousCuratedPublicationComparator.hashCode(pub2));
    }

    @Test
    public void test_compare_same_pubmed_same_released_date(){
        Publication pub1 = new DefaultPublication();
        pub1.setPubmedId("12345");
        pub1.setCurationDepth(CurationDepth.IMEx);
        pub1.setSource(new DefaultSource("intact"));
        pub1.setReleasedDate(new Date(System.currentTimeMillis()));
        Publication pub2 = new DefaultPublication();
        pub2.setPubmedId("12345");
        pub2.setCurationDepth(CurationDepth.IMEx);
        pub2.setSource(new DefaultSource("intact"));
        pub2.setReleasedDate(pub1.getReleasedDate());

        Assert.assertTrue(comparator.compare(pub1, pub2) == 0);
        Assert.assertTrue(comparator.compare(pub2, pub1) == 0);

        Assert.assertTrue(UnambiguousCuratedPublicationComparator.areEquals(pub1, pub2));
        Assert.assertTrue(UnambiguousCuratedPublicationComparator.hashCode(pub1) == UnambiguousCuratedPublicationComparator.hashCode(pub2));
    }
}
