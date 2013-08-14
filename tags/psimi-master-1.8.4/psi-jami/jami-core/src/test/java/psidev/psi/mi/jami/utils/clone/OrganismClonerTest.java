package psidev.psi.mi.jami.utils.clone;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;

/**
 * Unit tester for OrganismCloner
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/06/13</pre>
 */

public class OrganismClonerTest {

    @Test
    public void test_copy_basic_organism_properties(){

        Organism sourceOrganism = new DefaultOrganism(9606, "human", "homo sapiens");
        sourceOrganism.setCellType(new DefaultCvTerm("test cell line"));
        sourceOrganism.setCompartment(new DefaultCvTerm("test compartment"));
        sourceOrganism.setTissue(new DefaultCvTerm("test tissue"));
        sourceOrganism.getAliases().add(new DefaultAlias("test synonym"));

        Organism targetOrganism = new DefaultOrganism(-1, "in vitro");

        OrganismCloner.copyAndOverrideOrganismProperties(sourceOrganism, targetOrganism);

        Assert.assertEquals("human", targetOrganism.getCommonName());
        Assert.assertEquals("homo sapiens", targetOrganism.getScientificName());
        Assert.assertEquals(9606, targetOrganism.getTaxId());
        Assert.assertEquals(1, targetOrganism.getAliases().size());
        Assert.assertTrue(targetOrganism.getCellType() == sourceOrganism.getCellType());
        Assert.assertTrue(targetOrganism.getCompartment() == sourceOrganism.getCompartment());
        Assert.assertTrue(targetOrganism.getTissue() == sourceOrganism.getTissue());
        Assert.assertTrue(targetOrganism.getAliases().iterator().next() == sourceOrganism.getAliases().iterator().next());
    }

}
