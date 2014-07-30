package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.AllostericEffectorType;
import psidev.psi.mi.jami.model.MoleculeEffector;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultModelledEntityComparator;

/**
 * Unit tester for DefaultMoleculeEffector
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class DefaultMoleculeEffectorTest {

    @Test
    public void test_create_molecule_effector(){

        MoleculeEffector effector = new DefaultMoleculeEffector(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));

        Assert.assertEquals(AllostericEffectorType.molecule, effector.getEffectorType());
        Assert.assertTrue(DefaultModelledEntityComparator.areEquals(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()), effector.getMolecule(), true));
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_molecule_effector_molecule_null() throws Exception {
        MoleculeEffector effector = new DefaultMoleculeEffector(null);
    }
}
