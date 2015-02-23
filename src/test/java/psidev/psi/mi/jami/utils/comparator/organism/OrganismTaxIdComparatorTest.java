package psidev.psi.mi.jami.utils.comparator.organism;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;

/**
 * Unit tester for OrganismTaxId
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/02/13</pre>
 */

public class OrganismTaxIdComparatorTest {

    private OrganismTaxIdComparator comparator = new OrganismTaxIdComparator();

    @Test
    public void test_organism_null_after() throws Exception {
        Organism organism1 = null;
        Organism organism2 = new DefaultOrganism(9606);

        Assert.assertTrue(comparator.compare(organism1, organism2) > 0);
        Assert.assertTrue(comparator.compare(organism2, organism1) < 0);

        Assert.assertFalse(OrganismTaxIdComparator.areEquals(organism1, organism2));
        Assert.assertTrue(OrganismTaxIdComparator.hashCode(organism1) != OrganismTaxIdComparator.hashCode(organism2));
    }

    @Test
    public void test_ordered_taxids() throws Exception {
        Organism organism1 = new DefaultOrganism(-1);
        Organism organism2 = new DefaultOrganism(9606);

        Assert.assertTrue(comparator.compare(organism1, organism2) < 0);
        Assert.assertTrue(comparator.compare(organism2, organism1) > 0);

        Assert.assertFalse(OrganismTaxIdComparator.areEquals(organism1, organism2));
        Assert.assertTrue(OrganismTaxIdComparator.hashCode(organism1) != OrganismTaxIdComparator.hashCode(organism2));
    }

    @Test
    public void test_same_taxIds() throws Exception {
        Organism organism1 = new DefaultOrganism(9606);
        Organism organism2 = new DefaultOrganism(9606);

        Assert.assertTrue(comparator.compare(organism1, organism2) == 0);
        Assert.assertTrue(comparator.compare(organism2, organism1) == 0);

        Assert.assertTrue(OrganismTaxIdComparator.areEquals(organism1, organism2));
        Assert.assertTrue(OrganismTaxIdComparator.hashCode(organism1) == OrganismTaxIdComparator.hashCode(organism2));
    }

    @Test
    public void test_same_taxIds_ignoreCellType() throws Exception {
        Organism organism1 = new DefaultOrganism(9606, "human", "homo sapiens");
        Organism organism2 = new DefaultOrganism(9606, "human-293t", new DefaultCvTerm("293t"), null, null);

        Assert.assertTrue(comparator.compare(organism1, organism2) == 0);
        Assert.assertTrue(comparator.compare(organism2, organism1) == 0);

        Assert.assertTrue(OrganismTaxIdComparator.areEquals(organism1, organism2));
        Assert.assertTrue(OrganismTaxIdComparator.hashCode(organism1) == OrganismTaxIdComparator.hashCode(organism2));
    }
}
