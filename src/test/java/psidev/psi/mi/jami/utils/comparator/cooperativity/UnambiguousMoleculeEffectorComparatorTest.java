package psidev.psi.mi.jami.utils.comparator.cooperativity;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.MoleculeEffector;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultModelledParticipant;
import psidev.psi.mi.jami.model.impl.DefaultMoleculeEffector;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for UnambiguousMoleculeEffectorComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class UnambiguousMoleculeEffectorComparatorTest {

    private UnambiguousMoleculeEffectorComparator comparator = new UnambiguousMoleculeEffectorComparator();

    @Test
    public void test_molecule_effector_null_after() throws Exception {
        MoleculeEffector mol1 = null;
        MoleculeEffector mol2 = new DefaultMoleculeEffector(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));

        Assert.assertTrue(comparator.compare(mol1, mol2) > 0);
        Assert.assertTrue(comparator.compare(mol2, mol1) < 0);

        Assert.assertFalse(UnambiguousMoleculeEffectorComparator.areEquals(mol1, mol2));
        Assert.assertTrue(UnambiguousMoleculeEffectorComparator.hashCode(mol1) != UnambiguousMoleculeEffectorComparator.hashCode(mol2));
    }

    @Test
    public void test_different_molecule_effector() throws Exception {
        MoleculeEffector mol1 = new DefaultMoleculeEffector(new DefaultModelledParticipant(new DefaultProtein("test protein", XrefUtils.createIdentityXref(Xref.UNIPROTKB, "P12345"))));
        MoleculeEffector mol2 = new DefaultMoleculeEffector(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));

        Assert.assertTrue(comparator.compare(mol1, mol2) < 0);
        Assert.assertTrue(comparator.compare(mol2, mol1) > 0);

        Assert.assertFalse(UnambiguousMoleculeEffectorComparator.areEquals(mol1, mol2));
        Assert.assertTrue(UnambiguousMoleculeEffectorComparator.hashCode(mol1) != UnambiguousMoleculeEffectorComparator.hashCode(mol2));
    }

    @Test
    public void test_same_molecule_effector() throws Exception {
        MoleculeEffector mol1 = new DefaultMoleculeEffector(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));
        MoleculeEffector mol2 = new DefaultMoleculeEffector(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));

        Assert.assertTrue(comparator.compare(mol1, mol2) == 0);
        Assert.assertTrue(comparator.compare(mol2, mol1) == 0);

        Assert.assertTrue(UnambiguousMoleculeEffectorComparator.areEquals(mol1, mol2));
        Assert.assertTrue(UnambiguousMoleculeEffectorComparator.hashCode(mol1) == UnambiguousMoleculeEffectorComparator.hashCode(mol2));
    }
}
