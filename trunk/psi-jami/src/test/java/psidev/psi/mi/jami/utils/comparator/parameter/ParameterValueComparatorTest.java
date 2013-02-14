package psidev.psi.mi.jami.utils.comparator.parameter;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ParameterValue;

import java.math.BigDecimal;

/**
 * Unit tester for ParameterValueComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class ParameterValueComparatorTest {

    private ParameterValueComparator comparator = new ParameterValueComparator();

    @Test
    public void test_parameter_value_null_after() throws Exception {
        ParameterValue parameter1 = null;
        ParameterValue parameter2 = new ParameterValue(new BigDecimal("5"));

        Assert.assertTrue(comparator.compare(parameter1, parameter2) > 0);
        Assert.assertTrue(comparator.compare(parameter2, parameter1) < 0);

        Assert.assertFalse(ParameterValueComparator.areEquals(parameter1, parameter2));
        Assert.assertTrue(ParameterValueComparator.hashCode(parameter1) != ParameterValueComparator.hashCode(parameter2));
    }

    @Test
    public void test_parameter_value_comparison() throws Exception {
        ParameterValue parameter1 = new ParameterValue(new BigDecimal("5"), (short)10, (short)0);
        ParameterValue parameter2 = new ParameterValue(new BigDecimal("5"));

        Assert.assertTrue(comparator.compare(parameter1, parameter2) == 0);
        Assert.assertTrue(comparator.compare(parameter2, parameter1) == 0);

        Assert.assertTrue(ParameterValueComparator.areEquals(parameter1, parameter2));
        Assert.assertTrue(ParameterValueComparator.hashCode(parameter1) == ParameterValueComparator.hashCode(parameter2));
    }

    @Test
    public void test_parameter_value_different_exponent() throws Exception {
        ParameterValue parameter1 = new ParameterValue(new BigDecimal("5"), (short)10, (short)-1);
        ParameterValue parameter2 = new ParameterValue(new BigDecimal("5"));

        Assert.assertTrue(comparator.compare(parameter1, parameter2) < 0);
        Assert.assertTrue(comparator.compare(parameter2, parameter1) > 0);

        Assert.assertFalse(ParameterValueComparator.areEquals(parameter1, parameter2));
        Assert.assertTrue(ParameterValueComparator.hashCode(parameter1) != ParameterValueComparator.hashCode(parameter2));
    }
}
