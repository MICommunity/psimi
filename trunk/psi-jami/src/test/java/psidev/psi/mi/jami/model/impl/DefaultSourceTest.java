package psidev.psi.mi.jami.model.impl;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

/**
 * Unit tester for DefaultSource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/02/13</pre>
 */

public class DefaultSourceTest {

    @Test
    public void test_create_source() throws Exception {
        Source intact = new DefaultSource("intact");

        Assert.assertEquals("intact", intact.getShortName());
        Assert.assertNotNull(intact.getAnnotations());
        Assert.assertNotNull(intact.getSynonyms());
        Assert.assertNotNull(intact.getXrefs());

        Assert.assertNull(intact.getUrl());
        Assert.assertNull(intact.getBibRef());
        Assert.assertNull(intact.getPostalAddress());
    }

    @Test
    public void test_create_source_details() throws Exception {
        Source intact = new DefaultSource("intact", "http://ww.ebi.ac.uk/intact/", "hinxton", new DefaultXref(CvTermFactory.createPubmedDatabase(), "12345"));

        Assert.assertEquals("intact", intact.getShortName());
        Assert.assertNotNull(intact.getAnnotations());
        Assert.assertNotNull(intact.getSynonyms());
        Assert.assertNotNull(intact.getXrefs());

        Assert.assertEquals("http://ww.ebi.ac.uk/intact/", intact.getUrl());
        Assert.assertEquals("hinxton", intact.getPostalAddress());
        Assert.assertEquals(new DefaultXref(CvTermFactory.createPubmedDatabase(), "12345"), intact.getBibRef());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_source_no_shortName() throws Exception {
        Source intact = new DefaultSource(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_set_null_shortName() throws Exception {
        Source intact = new DefaultSource("intact");
        intact.setShortName(null);
    }

    @Test
    public void test_create_source_ontologyId() throws Exception {
        Source intact = new DefaultSource("intact", new DefaultXref(CvTermFactory.createPsiMiDatabaseNameOnly(), "MI:0469"));

        Assert.assertEquals("intact", intact.getShortName());
        Assert.assertEquals("MI:0469", intact.getMIIdentifier());
        Assert.assertNotNull(intact.getAnnotations());
        Assert.assertNotNull(intact.getSynonyms());
        Assert.assertNotNull(intact.getXrefs());

        Assert.assertNull(intact.getUrl());
        Assert.assertNull(intact.getBibRef());
        Assert.assertNull(intact.getPostalAddress());
    }

    @Test
    public void test_create_source_fullName() throws Exception {
        Source intact = new DefaultSource("intact", "intact database", new DefaultXref(CvTermFactory.createPsiMiDatabaseNameOnly(), "MI:0469"));

        Assert.assertEquals("intact", intact.getShortName());
        Assert.assertEquals("intact database", intact.getFullName());
        Assert.assertEquals("MI:0469", intact.getMIIdentifier());
        Assert.assertNotNull(intact.getAnnotations());
        Assert.assertNotNull(intact.getSynonyms());
        Assert.assertNotNull(intact.getXrefs());

        Assert.assertNull(intact.getUrl());
        Assert.assertNull(intact.getBibRef());
        Assert.assertNull(intact.getPostalAddress());
    }
}
