package psidev.psi.mi.jami.utils.clone;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.impl.*;

import java.util.Date;

/**
 * Unit tester for PublicationCloner
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/06/13</pre>
 */

public class PublicationClonerTest {

    @Test
    public void test_copy_basic_publication_properties(){

        Publication sourcePublication = new DefaultPublication("12345");
        sourcePublication.setDoi("doi2=");
        sourcePublication.setPublicationDate(new Date(1));
        sourcePublication.setReleasedDate(new Date());
        sourcePublication.setSource(new DefaultSource("dip"));
        sourcePublication.assignImexId("IM-1");
        sourcePublication.setCurationDepth(CurationDepth.IMEx);
        sourcePublication.setTitle("test title");
        sourcePublication.setJournal("test journal");
        sourcePublication.getAuthors().add("test author");
        sourcePublication.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"),"test comment"));
        sourcePublication.getExperiments().add(new DefaultExperiment(null));

        Publication targetPublication = new DefaultPublication("12346");
        targetPublication.setDoi("doi3=");

        PublicationCloner.copyAndOverridePublicationProperties(sourcePublication, targetPublication);

        Assert.assertEquals("12345", targetPublication.getPubmedId());
        Assert.assertEquals("doi2=", targetPublication.getDoi());
        Assert.assertEquals("IM-1", targetPublication.getImexId());
        Assert.assertEquals("test title", targetPublication.getTitle());
        Assert.assertEquals("test journal", targetPublication.getJournal());
        Assert.assertEquals(CurationDepth.IMEx, targetPublication.getCurationDepth());
        Assert.assertEquals(2, targetPublication.getIdentifiers().size());
        Assert.assertEquals(1, targetPublication.getXrefs().size());
        Assert.assertEquals(1, targetPublication.getAnnotations().size());
        Assert.assertEquals(1, targetPublication.getAuthors().size());
        Assert.assertEquals(0, targetPublication.getExperiments().size());
        Assert.assertEquals("test author", targetPublication.getAuthors().iterator().next());
        Assert.assertTrue(targetPublication.getAnnotations().iterator().next() == sourcePublication.getAnnotations().iterator().next());
        Assert.assertTrue(targetPublication.getXrefs().iterator().next() == sourcePublication.getXrefs().iterator().next());
        Assert.assertTrue(targetPublication.getIdentifiers().iterator().next() == sourcePublication.getIdentifiers().iterator().next());
        Assert.assertTrue(targetPublication.getPublicationDate() == sourcePublication.getPublicationDate());
        Assert.assertTrue(targetPublication.getReleasedDate() == sourcePublication.getReleasedDate());

    }
}
