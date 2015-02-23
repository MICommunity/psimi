package psidev.psi.mi.jami.utils.comparator.experiment;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.VariableParameterValue;
import psidev.psi.mi.jami.model.impl.DefaultVariableParameter;
import psidev.psi.mi.jami.model.impl.DefaultVariableParameterValue;

/**
 * Unit tester for VariableParameterValue
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/05/13</pre>
 */

public class VariableParameterValueComparatorTest {

    private VariableParameterValueComparator comparator = new VariableParameterValueComparator();

    @Test
    public void test_variable_parameter_value_null_after() throws Exception {
        VariableParameterValue value1 = null;
        VariableParameterValue value2 = new DefaultVariableParameterValue("G0 phase", null);

        Assert.assertTrue(comparator.compare(value1, value2) > 0);
        Assert.assertTrue(comparator.compare(value2, value1) < 0);

        Assert.assertFalse(VariableParameterValueComparator.areEquals(value1, value2));
        Assert.assertTrue(VariableParameterValueComparator.hashCode(value1) != VariableParameterValueComparator.hashCode(value2));
    }

    @Test
    public void test_variable_parameter_value_different_values() throws Exception {
        VariableParameterValue value1 = new DefaultVariableParameterValue("S phase", new DefaultVariableParameter("cell cycle"));
        VariableParameterValue value2 = new DefaultVariableParameterValue("G0 phase", null);

        Assert.assertTrue(comparator.compare(value1, value2) > 0);
        Assert.assertTrue(comparator.compare(value2, value1) < 0);

        Assert.assertFalse(VariableParameterValueComparator.areEquals(value1, value2));
        Assert.assertTrue(VariableParameterValueComparator.hashCode(value1) != VariableParameterValueComparator.hashCode(value2));
    }

    @Test
    public void test_variable_parameter_value_same_values() throws Exception {
        VariableParameterValue value1 = new DefaultVariableParameterValue("G0 phase", new DefaultVariableParameter("cell cycle"));
        VariableParameterValue value2 = new DefaultVariableParameterValue("G0 phase", null);

        Assert.assertTrue(comparator.compare(value1, value2) == 0);
        Assert.assertTrue(comparator.compare(value2, value1) == 0);

        Assert.assertTrue(VariableParameterValueComparator.areEquals(value1, value2));
        Assert.assertTrue(VariableParameterValueComparator.hashCode(value1) == VariableParameterValueComparator.hashCode(value2));
    }

    @Test
    public void test_variable_parameter_value_same_values_case_insensitive() throws Exception {
        VariableParameterValue value1 = new DefaultVariableParameterValue("G0 PHASE ", new DefaultVariableParameter("cell cycle"));
        VariableParameterValue value2 = new DefaultVariableParameterValue("G0 phase", null);

        Assert.assertTrue(comparator.compare(value1, value2) == 0);
        Assert.assertTrue(comparator.compare(value2, value1) == 0);

        Assert.assertTrue(VariableParameterValueComparator.areEquals(value1, value2));
        Assert.assertTrue(VariableParameterValueComparator.hashCode(value1) == VariableParameterValueComparator.hashCode(value2));
    }

    @Test
    public void test_variable_parameter_value_same_values_different_order() throws Exception {
        VariableParameterValue value1 = new DefaultVariableParameterValue("S phase", new DefaultVariableParameter("cell cycle"), 1);
        VariableParameterValue value2 = new DefaultVariableParameterValue("S phase", null, 2);

        Assert.assertTrue(comparator.compare(value1, value2) < 0);
        Assert.assertTrue(comparator.compare(value2, value1) > 0);

        Assert.assertFalse(VariableParameterValueComparator.areEquals(value1, value2));
        Assert.assertTrue(VariableParameterValueComparator.hashCode(value1) != VariableParameterValueComparator.hashCode(value2));
    }

    @Test
    public void test_variable_parameter_value_order_null_after() throws Exception {
        VariableParameterValue value1 = new DefaultVariableParameterValue("S phase", new DefaultVariableParameter("cell cycle"));
        VariableParameterValue value2 = new DefaultVariableParameterValue("S phase", null, 2);

        Assert.assertTrue(comparator.compare(value1, value2) > 0);
        Assert.assertTrue(comparator.compare(value2, value1) < 0);

        Assert.assertFalse(VariableParameterValueComparator.areEquals(value1, value2));
        Assert.assertTrue(VariableParameterValueComparator.hashCode(value1) != VariableParameterValueComparator.hashCode(value2));
    }

    @Test
    public void test_variable_parameter_value_same_order() throws Exception {
        VariableParameterValue value1 = new DefaultVariableParameterValue("G0 phase", new DefaultVariableParameter("cell cycle"), 1);
        VariableParameterValue value2 = new DefaultVariableParameterValue("G0 phase", null, 1);

        Assert.assertTrue(comparator.compare(value1, value2) == 0);
        Assert.assertTrue(comparator.compare(value2, value1) == 0);

        Assert.assertTrue(VariableParameterValueComparator.areEquals(value1, value2));
        Assert.assertTrue(VariableParameterValueComparator.hashCode(value1) == VariableParameterValueComparator.hashCode(value2));
    }
}
