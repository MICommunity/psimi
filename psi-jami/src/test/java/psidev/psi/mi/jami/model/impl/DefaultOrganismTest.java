package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Organism;

/**
 * Unit tester for DefaultOrganism
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/02/13</pre>
 */

public class DefaultOrganismTest {

    @Test
    public void test_create_organism_taxid_only(){

        Organism human = new DefaultOrganism(9606);

        Assert.assertTrue(9606 == human.getTaxId());
        Assert.assertNull(human.getCommonName());
        Assert.assertNull(human.getScientificName());
        Assert.assertNull(human.getCellType());
        Assert.assertNull(human.getTissue());
        Assert.assertNull(human.getCompartment());
        Assert.assertNotNull(human.getAliases());
    }

    @Test
    public void test_create_organism_with_name(){

        Organism human = new DefaultOrganism(9606, "human");

        Assert.assertTrue(9606 == human.getTaxId());
        Assert.assertEquals("human", human.getCommonName());
        Assert.assertNull(human.getScientificName());
        Assert.assertNull(human.getCellType());
        Assert.assertNull(human.getTissue());
        Assert.assertNull(human.getCompartment());
        Assert.assertNotNull(human.getAliases());
    }

    @Test
    public void test_create_organism_with_fullName(){

        Organism human = new DefaultOrganism(9606, "human", "Homo Sapiens");

        Assert.assertTrue(9606 == human.getTaxId());
        Assert.assertEquals("human", human.getCommonName());
        Assert.assertEquals("Homo Sapiens", human.getScientificName());
        Assert.assertNull(human.getCellType());
        Assert.assertNull(human.getTissue());
        Assert.assertNull(human.getCompartment());
        Assert.assertNotNull(human.getAliases());
    }

    @Test
    public void test_create_organism_with_cellType(){

        Organism human = new DefaultOrganism(9606, "human", new DefaultCvTerm("293t"), null, null);

        Assert.assertTrue(9606 == human.getTaxId());
        Assert.assertEquals("human", human.getCommonName());
        Assert.assertNull(human.getScientificName());
        Assert.assertEquals(new DefaultCvTerm("293t"), human.getCellType());
        Assert.assertNull(human.getTissue());
        Assert.assertNull(human.getCompartment());
        Assert.assertNotNull(human.getAliases());
    }

    @Test
    public void test_create_organism_with_tissue(){

        Organism human = new DefaultOrganism(9606, "human", null, new DefaultCvTerm("epithelium"), null);

        Assert.assertTrue(9606 == human.getTaxId());
        Assert.assertEquals("human", human.getCommonName());
        Assert.assertNull(human.getScientificName());
        Assert.assertEquals(new DefaultCvTerm("epithelium"), human.getTissue());
        Assert.assertNull(human.getCellType());
        Assert.assertNull(human.getCompartment());
        Assert.assertNotNull(human.getAliases());
    }

    @Test
    public void test_create_organism_with_compartment(){

        Organism human = new DefaultOrganism(9606, "human", null, null, new DefaultCvTerm("brain"));

        Assert.assertTrue(9606 == human.getTaxId());
        Assert.assertEquals("human", human.getCommonName());
        Assert.assertNull(human.getScientificName());
        Assert.assertEquals(new DefaultCvTerm("brain"), human.getCompartment());
        Assert.assertNull(human.getCellType());
        Assert.assertNull(human.getTissue());
        Assert.assertNotNull(human.getAliases());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_organism_wrong_taxid() throws Exception {
        Organism human = new DefaultOrganism(0);
    }
}
