package psidev.psi.mi.jami.utils.comparator.experiment;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.VariableParameter;
import psidev.psi.mi.jami.model.impl.DefaultVariableParameter;
import psidev.psi.mi.jami.model.impl.DefaultVariableParameterValue;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Unit tester for UnambiguousVariableParameterComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/05/13</pre>
 */

public class UnambiguousVariableParameterComparatorTest {

    private UnambiguousVariableParameterComparator comparator = new UnambiguousVariableParameterComparator();

    @Test
    public void test_variable_parameter_null_after() throws Exception {
        VariableParameter param1 = null;
        VariableParameter param2 = new DefaultVariableParameter("cell cycle");

        Assert.assertTrue(comparator.compare(param1, param2) > 0);
        Assert.assertTrue(comparator.compare(param2, param1) < 0);

        Assert.assertFalse(UnambiguousVariableParameterComparator.areEquals(param1, param2));
        Assert.assertTrue(UnambiguousVariableParameterComparator.hashCode(param1) != UnambiguousVariableParameterComparator.hashCode(param2));
    }

    @Test
    public void test_variable_parameter_different_description() throws Exception {
        VariableParameter param1 = new DefaultVariableParameter("PMA treatment");
        VariableParameter param2 = new DefaultVariableParameter("cell cycle");

        Assert.assertTrue(comparator.compare(param1, param2) > 0);
        Assert.assertTrue(comparator.compare(param2, param1) < 0);

        Assert.assertFalse(UnambiguousVariableParameterComparator.areEquals(param1, param2));
        Assert.assertTrue(UnambiguousVariableParameterComparator.hashCode(param1) != UnambiguousVariableParameterComparator.hashCode(param2));
    }

    @Test
    public void test_variable_parameter_same_description() throws Exception {
        VariableParameter param1 = new DefaultVariableParameter("cell cycle");
        VariableParameter param2 = new DefaultVariableParameter("cell cycle");

        Assert.assertTrue(comparator.compare(param1, param2) == 0);
        Assert.assertTrue(comparator.compare(param2, param1) == 0);

        Assert.assertTrue(UnambiguousVariableParameterComparator.areEquals(param1, param2));
        Assert.assertTrue(UnambiguousVariableParameterComparator.hashCode(param1) == UnambiguousVariableParameterComparator.hashCode(param2));
    }

    @Test
    public void test_variable_parameter_same_description_case_insensitive() throws Exception {
        VariableParameter param1 = new DefaultVariableParameter("cell CYCLE ");
        VariableParameter param2 = new DefaultVariableParameter("cell cycle");

        Assert.assertTrue(comparator.compare(param1, param2) == 0);
        Assert.assertTrue(comparator.compare(param2, param1) == 0);

        Assert.assertTrue(UnambiguousVariableParameterComparator.areEquals(param1, param2));
        Assert.assertTrue(UnambiguousVariableParameterComparator.hashCode(param1) == UnambiguousVariableParameterComparator.hashCode(param2));
    }

    @Test
    public void test_variable_parameter_same_description_different_units() throws Exception {
        VariableParameter param1 = new DefaultVariableParameter("param test");
        param1.setUnit(CvTermUtils.createMICvTerm("test unit", "MI:xxx2"));
        VariableParameter param2 = new DefaultVariableParameter("param test");
        param2.setUnit(CvTermUtils.createMICvTerm("test unit", "MI:xxx1"));

        Assert.assertTrue(comparator.compare(param1, param2) > 0);
        Assert.assertTrue(comparator.compare(param2, param1) < 0);

        Assert.assertFalse(UnambiguousVariableParameterComparator.areEquals(param1, param2));
        Assert.assertTrue(UnambiguousVariableParameterComparator.hashCode(param1) != UnambiguousVariableParameterComparator.hashCode(param2));
    }

    @Test
    public void test_variable_parameter_same_description_same_units() throws Exception {
        VariableParameter param1 = new DefaultVariableParameter("param test");
        param1.setUnit(CvTermUtils.createMICvTerm("test unit", "MI:xxx1"));
        VariableParameter param2 = new DefaultVariableParameter("param test");
        param2.setUnit(CvTermUtils.createMICvTerm("test unit", "MI:xxx1"));

        Assert.assertTrue(comparator.compare(param1, param2) == 0);
        Assert.assertTrue(comparator.compare(param2, param1) == 0);

        Assert.assertTrue(UnambiguousVariableParameterComparator.areEquals(param1, param2));
        Assert.assertTrue(UnambiguousVariableParameterComparator.hashCode(param1) == UnambiguousVariableParameterComparator.hashCode(param2));
    }

    @Test
    public void test_variable_parameter_same_unit_different_values() throws Exception {
        VariableParameter param1 = new DefaultVariableParameter("param test");
        param1.setUnit(CvTermUtils.createMICvTerm("test unit", "MI:xxx1"));
        param1.getVariableValues().add(new DefaultVariableParameterValue("5", param1));
        VariableParameter param2 = new DefaultVariableParameter("param test");
        param2.setUnit(CvTermUtils.createMICvTerm("test unit", "MI:xxx1"));
        param2.getVariableValues().add(new DefaultVariableParameterValue("4", param2));

        Assert.assertTrue(comparator.compare(param1, param2) > 0);
        Assert.assertTrue(comparator.compare(param2, param1) < 0);

        Assert.assertFalse(UnambiguousVariableParameterComparator.areEquals(param1, param2));
        Assert.assertTrue(UnambiguousVariableParameterComparator.hashCode(param1) != UnambiguousVariableParameterComparator.hashCode(param2));
    }

    @Test
    public void test_variable_parameter_same_values() throws Exception {
        VariableParameter param1 = new DefaultVariableParameter("param test");
        param1.setUnit(CvTermUtils.createMICvTerm("test unit", "MI:xxx1"));
        param1.getVariableValues().add(new DefaultVariableParameterValue("5", param1));
        VariableParameter param2 = new DefaultVariableParameter("param test");
        param2.setUnit(CvTermUtils.createMICvTerm("test unit", "MI:xxx1"));
        param2.getVariableValues().add(new DefaultVariableParameterValue("5", param2));

        Assert.assertTrue(comparator.compare(param1, param2) == 0);
        Assert.assertTrue(comparator.compare(param2, param1) == 0);

        Assert.assertTrue(UnambiguousVariableParameterComparator.areEquals(param1, param2));
        Assert.assertTrue(UnambiguousVariableParameterComparator.hashCode(param1) == UnambiguousVariableParameterComparator.hashCode(param2));
    }
}
