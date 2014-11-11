package psidev.psi.mi.jami.utils.comparator.organism;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;

/**
 * Unit tester for UnambiguousOrganismComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/02/13</pre>
 */

public class UnambiguousOrganismComparatorTest {
    private UnambiguousOrganismComparator comparator = new UnambiguousOrganismComparator();

    @Test
    public void test_organism_null_after() throws Exception {
        Organism organism1 = null;
        Organism organism2 = new DefaultOrganism(9606);

        Assert.assertTrue(comparator.compare(organism1, organism2) > 0);
        Assert.assertTrue(comparator.compare(organism2, organism1) < 0);

        Assert.assertFalse(UnambiguousOrganismComparator.areEquals(organism1, organism2));
        Assert.assertTrue(UnambiguousOrganismComparator.hashCode(organism1) != UnambiguousOrganismComparator.hashCode(organism2));
    }

    @Test
    public void test_organism_compare_taxid() throws Exception {
        Organism organism1 = new DefaultOrganism(-1);
        Organism organism2 = new DefaultOrganism(9606, "human");

        Assert.assertTrue(comparator.compare(organism1, organism2) < 0);
        Assert.assertTrue(comparator.compare(organism2, organism1) > 0);

        Assert.assertFalse(UnambiguousOrganismComparator.areEquals(organism1, organism2));
        Assert.assertTrue(UnambiguousOrganismComparator.hashCode(organism1) != UnambiguousOrganismComparator.hashCode(organism2));
    }

    @Test
    public void test_organism_compare_cellType() throws Exception {
        Organism organism1 = new DefaultOrganism(9606, "human");
        Organism organism2 = new DefaultOrganism(9606, "human-293t", new DefaultCvTerm("293t"), null, null);

        Assert.assertTrue(comparator.compare(organism1, organism2) != 0);
        Assert.assertTrue(comparator.compare(organism2, organism1) != 0);

        Assert.assertFalse(UnambiguousOrganismComparator.areEquals(organism1, organism2));
        Assert.assertTrue(UnambiguousOrganismComparator.hashCode(organism1) != UnambiguousOrganismComparator.hashCode(organism2));
    }

    @Test
    public void test_organism_same_celltype() throws Exception {
        Organism organism1 = new DefaultOrganism(9606, "human", new DefaultCvTerm("293t"), null, null);
        Organism organism2 = new DefaultOrganism(9606, "human-293t", new DefaultCvTerm("293t"),null, null);

        Assert.assertTrue(comparator.compare(organism1, organism2) == 0);
        Assert.assertTrue(comparator.compare(organism2, organism1) == 0);

        Assert.assertTrue(UnambiguousOrganismComparator.areEquals(organism1, organism2));
        Assert.assertTrue(UnambiguousOrganismComparator.hashCode(organism1) == UnambiguousOrganismComparator.hashCode(organism2));
    }

    @Test
    public void test_organism_compare_same_celltype_different_tissues() throws Exception {
        Organism organism1 = new DefaultOrganism(9606, "human", new DefaultCvTerm("293t"), null, null);
        Organism organism2 = new DefaultOrganism(9606, "human-293t", null, new DefaultCvTerm("epithelium"), null);

        Assert.assertTrue(comparator.compare(organism1, organism2) != 0);
        Assert.assertTrue(comparator.compare(organism2, organism1) != 0);

        Assert.assertFalse(UnambiguousOrganismComparator.areEquals(organism1, organism2));
        Assert.assertTrue(UnambiguousOrganismComparator.hashCode(organism1) != UnambiguousOrganismComparator.hashCode(organism2));
    }

    @Test
    public void test_organism_compare_tissue() throws Exception {
        Organism organism1 = new DefaultOrganism(9606, "human");
        Organism organism2 = new DefaultOrganism(9606, "human-293t", null, new DefaultCvTerm("epithelium"), null);

        Assert.assertTrue(comparator.compare(organism1, organism2) != 0);
        Assert.assertTrue(comparator.compare(organism2, organism1) != 0);

        Assert.assertFalse(UnambiguousOrganismComparator.areEquals(organism1, organism2));
        Assert.assertTrue(UnambiguousOrganismComparator.hashCode(organism1) != UnambiguousOrganismComparator.hashCode(organism2));
    }

    @Test
    public void test_organism_same_tissue() throws Exception {
        Organism organism1 = new DefaultOrganism(9606, "human", null, new DefaultCvTerm("epithelium"), null);
        Organism organism2 = new DefaultOrganism(9606, "human-293t", null, new DefaultCvTerm("epithelium"), null);

        Assert.assertTrue(comparator.compare(organism1, organism2) == 0);
        Assert.assertTrue(comparator.compare(organism2, organism1) == 0);

        Assert.assertTrue(UnambiguousOrganismComparator.areEquals(organism1, organism2));
        Assert.assertTrue(UnambiguousOrganismComparator.hashCode(organism1) == UnambiguousOrganismComparator.hashCode(organism2));
    }

    @Test
    public void test_organism_compare_compartment() throws Exception {
        Organism organism1 = new DefaultOrganism(9606, "human");
        Organism organism2 = new DefaultOrganism(9606, "human-293t", null, null, new DefaultCvTerm("brain"));

        Assert.assertTrue(comparator.compare(organism1, organism2) != 0);
        Assert.assertTrue(comparator.compare(organism2, organism1) != 0);

        Assert.assertFalse(UnambiguousOrganismComparator.areEquals(organism1, organism2));
        Assert.assertTrue(UnambiguousOrganismComparator.hashCode(organism1) != UnambiguousOrganismComparator.hashCode(organism2));
    }

    @Test
    public void test_organism_same_compartment() throws Exception {
        Organism organism1 = new DefaultOrganism(9606, "human", null, null, new DefaultCvTerm("brain"));
        Organism organism2 = new DefaultOrganism(9606, "human-293t", null, null, new DefaultCvTerm("brain"));

        Assert.assertTrue(comparator.compare(organism1, organism2) == 0);
        Assert.assertTrue(comparator.compare(organism2, organism1) == 0);

        Assert.assertTrue(UnambiguousOrganismComparator.areEquals(organism1, organism2));
        Assert.assertTrue(UnambiguousOrganismComparator.hashCode(organism1) == UnambiguousOrganismComparator.hashCode(organism2));
    }
}
