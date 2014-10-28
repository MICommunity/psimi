package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Polymer;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Unit tester for Polymer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/05/13</pre>
 */

public class DefaultPolymerTest {

    @Test
    public void create_polymer_with_shortname(){

        Polymer polymer = new DefaultPolymer("test");

        Assert.assertEquals(CvTermUtils.createPolymerInteractorType(), polymer.getInteractorType());
        Assert.assertEquals("test", polymer.getShortName());
        Assert.assertNotNull(polymer.getAliases());
        Assert.assertNotNull(polymer.getIdentifiers());
        Assert.assertNotNull(polymer.getXrefs());
        Assert.assertNotNull(polymer.getAnnotations());
        Assert.assertNotNull(polymer.getChecksums());
        Assert.assertNull(polymer.getOrganism());

        polymer = new DefaultPolymer("test", (String) null);
        Assert.assertEquals(CvTermUtils.createPolymerInteractorType(), polymer.getInteractorType());
    }

    @Test
    public void create_gene_set_interactor_type_null(){

        Polymer interactor = new DefaultPolymer("test", CvTermUtils.createGeneInteractorType());
        Assert.assertEquals(CvTermUtils.createGeneInteractorType(), interactor.getInteractorType());

        interactor.setInteractorType(null);
        Assert.assertEquals(CvTermUtils.createPolymerInteractorType(), interactor.getInteractorType());

        interactor.setInteractorType(CvTermUtils.createGeneInteractorType());
        Assert.assertEquals(CvTermUtils.createGeneInteractorType(), interactor.getInteractorType());
    }
}
