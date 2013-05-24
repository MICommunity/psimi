package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for Feature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/05/13</pre>
 */

public class DefaultFeatureTest {

    @Test
    public void test_create_empty_feature(){

        Feature feature = new DefaultFeature();

        Assert.assertNull(feature.getInteractionEffect());
        Assert.assertNull(feature.getInteractionDependency());
        Assert.assertNull(feature.getInterpro());
        Assert.assertNull(feature.getFullName());
        Assert.assertNull(feature.getShortName());
        Assert.assertNotNull(feature.getIdentifiers());
        Assert.assertNotNull(feature.getXrefs());
        Assert.assertNotNull(feature.getAnnotations());
        Assert.assertNotNull(feature.getRanges());
    }

    @Test
    public void test_create_feature_withName(){

        Feature feature = new DefaultFeature("test", "test tag");

        Assert.assertEquals("test", feature.getShortName());
        Assert.assertEquals("test tag", feature.getFullName());
    }

    @Test
    public void test_create_feature_withType(){

        Feature feature = new DefaultFeature("test", "test tag", CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));

        Assert.assertEquals("test", feature.getShortName());
        Assert.assertEquals("test tag", feature.getFullName());
        Assert.assertNotNull(feature.getType());
        Assert.assertEquals(CvTermUtils.createMICvTerm("binding site", "MI:xxxx"), feature.getType());
    }

    @Test
    public void test_create_interpro_feature(){
        Feature feature = new DefaultFeature("test", "test feature", "INTERPRO-TEST");

        Assert.assertEquals("test", feature.getShortName());
        Assert.assertEquals("test feature", feature.getFullName());
        Assert.assertNotNull(feature.getInterpro());
        Assert.assertEquals("INTERPRO-TEST", feature.getInterpro());
        Assert.assertEquals(1, feature.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.INTERPRO, Xref.INTERPRO_MI, "INTERPRO-TEST"), feature.getIdentifiers().iterator().next());
    }

    @Test
    public void test_add_remove_interpro(){
        Feature feature = new DefaultFeature("test", "test feature", "INTERPRO-TEST");

        feature.getIdentifiers().clear();
        Assert.assertNull(feature.getInterpro());

        feature.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.INTERPRO, Xref.INTERPRO_MI, "INTERPRO-TEST"));
        Assert.assertNotNull(feature.getInterpro());
        Assert.assertEquals("INTERPRO-TEST", feature.getInterpro());

        feature.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.INTERPRO, Xref.INTERPRO_MI, "INTERPRO-TEST2"));
        Assert.assertNotNull(feature.getInterpro());
        Assert.assertEquals("INTERPRO-TEST", feature.getInterpro());

        feature.getIdentifiers().remove(XrefUtils.createIdentityXref(Xref.INTERPRO, Xref.INTERPRO_MI, "INTERPRO-TEST"));
        Assert.assertNotNull(feature.getInterpro());
        Assert.assertEquals("INTERPRO-TEST2", feature.getInterpro());

        feature.getIdentifiers().clear();
        feature.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.INTERPRO, Xref.INTERPRO_MI, "INTERPRO-TEST"));
        feature.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.INTERPRO, Xref.INTERPRO_MI, "INTERPRO-TEST2"));
        feature.setInterpro(null);
        Assert.assertEquals(0, feature.getIdentifiers().size());

        feature.getIdentifiers().add(XrefUtils.createXref(Xref.INTERPRO, Xref.INTERPRO_MI, "INTERPRO-TEST"));
        Assert.assertNotNull(feature.getInterpro());
        Assert.assertEquals("INTERPRO-TEST", feature.getInterpro());
        feature.getIdentifiers().add(XrefUtils.createIdentityXref(Xref.INTERPRO, Xref.INTERPRO_MI, "INTERPRO-TEST2"));
        Assert.assertNotNull(feature.getInterpro());
        Assert.assertEquals("INTERPRO-TEST2", feature.getInterpro());
    }
}
