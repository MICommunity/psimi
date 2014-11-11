package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for DefaultInteractor
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/05/13</pre>
 */

public class DefaultInteractorTest {

    @Test
    public void create_unknow_interactor_with_shortname(){

        Interactor interactor = new DefaultInteractor("test");

        Assert.assertEquals(CvTermUtils.createUnknownInteractorType(), interactor.getInteractorType());
        Assert.assertEquals("test", interactor.getShortName());
        Assert.assertNotNull(interactor.getAliases());
        Assert.assertNotNull(interactor.getIdentifiers());
        Assert.assertNotNull(interactor.getXrefs());
        Assert.assertNotNull(interactor.getAnnotations());
        Assert.assertNotNull(interactor.getChecksums());
        Assert.assertNull(interactor.getOrganism());

        interactor = new DefaultInteractor("test", (String) null);
        Assert.assertEquals(CvTermUtils.createUnknownInteractorType(), interactor.getInteractorType());
    }

    @Test
    public void create_gene_set_interactor_type_null(){

        Interactor interactor = new DefaultInteractor("test", CvTermUtils.createGeneInteractorType());
        Assert.assertEquals(CvTermUtils.createGeneInteractorType(), interactor.getInteractorType());

        interactor.setInteractorType(null);
        Assert.assertEquals(CvTermUtils.createUnknownInteractorType(), interactor.getInteractorType());

        interactor.setInteractorType(CvTermUtils.createGeneInteractorType());
        Assert.assertEquals(CvTermUtils.createGeneInteractorType(), interactor.getInteractorType());
    }

    @Test
    public void create_interactor_with_shortname_fullName_interactorType(){

        Interactor interactor = new DefaultInteractor("test", "test interactor", CvTermUtils.createGeneInteractorType());

        Assert.assertEquals(CvTermUtils.createGeneInteractorType(), interactor.getInteractorType());
        Assert.assertEquals("test", interactor.getShortName());
        Assert.assertEquals("test interactor", interactor.getFullName());
    }

    @Test
    public void create_interactor_with_shortname_organism_interactorType(){

        Interactor interactor = new DefaultInteractor("test", CvTermUtils.createGeneInteractorType(), new DefaultOrganism(9606));

        Assert.assertEquals(CvTermUtils.createGeneInteractorType(), interactor.getInteractorType());
        Assert.assertEquals("test", interactor.getShortName());
        Assert.assertEquals(new DefaultOrganism(9606), interactor.getOrganism());
    }

    @Test
    public void create_interactor_with_unique_id(){

        Interactor interactor = new DefaultInteractor("test", XrefUtils.createIdentityXref(Xref.UNIPROTKB, Xref.UNIPROTKB_MI, "P12345"));

        Assert.assertEquals("test", interactor.getShortName());
        Assert.assertEquals(1, interactor.getIdentifiers().size());
        Assert.assertEquals(XrefUtils.createIdentityXref(Xref.UNIPROTKB, Xref.UNIPROTKB_MI, "P12345"), interactor.getIdentifiers().iterator().next());
    }
}
