package psidev.psi.mi.jami.utils.comparator.experiment;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.VariableParameterValueSet;
import psidev.psi.mi.jami.model.impl.DefaultVariableParameterValue;
import psidev.psi.mi.jami.model.impl.DefaultVariableParameterValueSet;

/**
 * Unit tester for VariableParameterSetComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/05/13</pre>
 */

public class VariableParameterValueSetComparatorTest {

    private VariableParameterValueSetComparator comparator = new VariableParameterValueSetComparator();

    @Test
    public void test_variable_parameter_value_set_null_after() throws Exception {
        VariableParameterValueSet valueSet1 = null;
        VariableParameterValueSet valueSet2 = new DefaultVariableParameterValueSet();

        Assert.assertTrue(comparator.compare(valueSet1, valueSet2) > 0);
        Assert.assertTrue(comparator.compare(valueSet2, valueSet1) < 0);

        Assert.assertFalse(VariableParameterValueSetComparator.areEquals(valueSet1, valueSet2));
        Assert.assertTrue(VariableParameterValueSetComparator.hashCode(valueSet1) != VariableParameterValueSetComparator.hashCode(valueSet2));
    }

    @Test
    public void test_variable_parameter_value_set_bigger_after() throws Exception {
        VariableParameterValueSet valueSet1 = new DefaultVariableParameterValueSet();
        valueSet1.add(new DefaultVariableParameterValue("S phase", null));
        VariableParameterValueSet valueSet2 = new DefaultVariableParameterValueSet();

        Assert.assertTrue(comparator.compare(valueSet1, valueSet2) > 0);
        Assert.assertTrue(comparator.compare(valueSet2, valueSet1) < 0);

        Assert.assertFalse(VariableParameterValueSetComparator.areEquals(valueSet1, valueSet2));
        Assert.assertTrue(VariableParameterValueSetComparator.hashCode(valueSet1) != VariableParameterValueSetComparator.hashCode(valueSet2));
    }

    @Test
    public void test_variable_parameter_value_set_different_values() throws Exception {
        VariableParameterValueSet valueSet1 = new DefaultVariableParameterValueSet();
        valueSet1.add(new DefaultVariableParameterValue("S phase", null));
        VariableParameterValueSet valueSet2 = new DefaultVariableParameterValueSet();
        valueSet2.add(new DefaultVariableParameterValue("G phase", null));

        Assert.assertTrue(comparator.compare(valueSet1, valueSet2) > 0);
        Assert.assertTrue(comparator.compare(valueSet2, valueSet1) < 0);

        Assert.assertFalse(VariableParameterValueSetComparator.areEquals(valueSet1, valueSet2));
        Assert.assertTrue(VariableParameterValueSetComparator.hashCode(valueSet1) != VariableParameterValueSetComparator.hashCode(valueSet2));
    }

    @Test
    public void test_variable_parameter_value_set_same_values() throws Exception {
        VariableParameterValueSet valueSet1 = new DefaultVariableParameterValueSet();
        valueSet1.add(new DefaultVariableParameterValue("S phase", null));
        valueSet1.add(new DefaultVariableParameterValue("G phase", null));
        VariableParameterValueSet valueSet2 = new DefaultVariableParameterValueSet();
        valueSet2.add(new DefaultVariableParameterValue("G phase", null));
        valueSet2.add(new DefaultVariableParameterValue("S phase", null));

        Assert.assertTrue(comparator.compare(valueSet1, valueSet2) == 0);
        Assert.assertTrue(comparator.compare(valueSet2, valueSet1) == 0);

        Assert.assertTrue(VariableParameterValueSetComparator.areEquals(valueSet1, valueSet2));
        Assert.assertTrue(VariableParameterValueSetComparator.hashCode(valueSet1) == VariableParameterValueSetComparator.hashCode(valueSet2));
    }
}
