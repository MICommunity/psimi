package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Stoichiometry;

/**
 * Unit tester for DefaultStoichiometry
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class DefaultStoichiometryTest {

    @Test
    public void create_simple_stoichiometry(){

        Stoichiometry stc = new DefaultStoichiometry(1);

        Assert.assertEquals(1, stc.getMinValue());
        Assert.assertEquals(1, stc.getMaxValue());
    }

    @Test
    public void create_stoichiometry_range(){

        Stoichiometry stc = new DefaultStoichiometry(1, 3);

        Assert.assertEquals(1, stc.getMinValue());
        Assert.assertEquals(3, stc.getMaxValue());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_wrong_range_stoichiometry() throws Exception {
        Stoichiometry stc = new DefaultStoichiometry(3, 1);
    }
}
