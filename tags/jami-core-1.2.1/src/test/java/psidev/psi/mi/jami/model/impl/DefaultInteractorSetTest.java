package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.InteractorPool;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Unit tester for DefaultInteractorPool
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/05/13</pre>
 */

public class DefaultInteractorSetTest {

    @Test
    public void create_interactor_set_with_shortname(){

        InteractorPool interactor = new DefaultInteractorPool("test");

        Assert.assertEquals(CvTermUtils.createMoleculeSetType(), interactor.getInteractorType());
        Assert.assertEquals("test", interactor.getShortName());
        Assert.assertNotNull(interactor.getAliases());
        Assert.assertNotNull(interactor.getIdentifiers());
        Assert.assertNotNull(interactor.getXrefs());
        Assert.assertNotNull(interactor.getAnnotations());
        Assert.assertNotNull(interactor.getChecksums());
        Assert.assertNull(interactor.getOrganism());
        Assert.assertEquals(0, interactor.size());

        interactor = new DefaultInteractorPool("test", (CvTerm) null);
        Assert.assertEquals(CvTermUtils.createMoleculeSetType(), interactor.getInteractorType());
    }

    @Test
    public void create_gene_set_interactor_type_null(){

        InteractorPool interactor = new DefaultInteractorPool("test", CvTermUtils.createNucleicAcidInteractorType());
        Assert.assertEquals(CvTermUtils.createNucleicAcidInteractorType(), interactor.getInteractorType());

        interactor.setInteractorType(null);
        Assert.assertEquals(CvTermUtils.createMoleculeSetType(), interactor.getInteractorType());

        interactor.setInteractorType(CvTermUtils.createGeneInteractorType());
        Assert.assertEquals(CvTermUtils.createGeneInteractorType(), interactor.getInteractorType());

        Assert.assertEquals(0, interactor.size());
    }
}
