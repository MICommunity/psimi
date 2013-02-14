package psidev.psi.mi.jami.utils.comparator.parameter;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultParameter;

import java.math.BigDecimal;

/**
 * Unit tester of UnmabiguousParameterComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/02/13</pre>
 */

public class UnambiguousParameterComparatorTest {

    private UnambiguousParameterComparator comparator = new UnambiguousParameterComparator();

    @Test
    public void test_parameter_null_after() throws Exception {
        Parameter parameter1 = null;
        Parameter parameter2 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal("5")));

        Assert.assertTrue(comparator.compare(parameter1, parameter2) > 0);
        Assert.assertTrue(comparator.compare(parameter2, parameter1) < 0);

        Assert.assertFalse(UnambiguousParameterComparator.areEquals(parameter1, parameter2));
        Assert.assertTrue(UnambiguousParameterComparator.hashCode(parameter1) != UnambiguousParameterComparator.hashCode(parameter2));
    }

    @Test
    public void test_parameter_type_comparison() throws Exception {
        Parameter parameter1 = new DefaultParameter(new DefaultCvTerm("ec50"), new ParameterValue(new BigDecimal("5")));
        Parameter parameter2 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal("5")));

        Assert.assertTrue(comparator.compare(parameter1, parameter2) != 0);
        Assert.assertTrue(comparator.compare(parameter2, parameter1) != 0);

        Assert.assertFalse(UnambiguousParameterComparator.areEquals(parameter1, parameter2));
        Assert.assertTrue(UnambiguousParameterComparator.hashCode(parameter1) != UnambiguousParameterComparator.hashCode(parameter2));

        Parameter parameter3 = new DefaultParameter(new DefaultCvTerm("KD"), new ParameterValue(new BigDecimal("10")));
        Parameter parameter4 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal("10")));

        Assert.assertTrue(comparator.compare(parameter3, parameter4) == 0);
        Assert.assertTrue(comparator.compare(parameter4, parameter3) == 0);

        Assert.assertTrue(UnambiguousParameterComparator.areEquals(parameter3, parameter4));
        Assert.assertTrue(UnambiguousParameterComparator.hashCode(parameter3) == UnambiguousParameterComparator.hashCode(parameter4));
    }

    @Test
    public void test_parameter_unit_comparison() throws Exception {
        // same parameter but different unit
        Parameter parameter1 = new DefaultParameter(new DefaultCvTerm("ec50"), new ParameterValue(new BigDecimal("5")), new DefaultCvTerm("molar"));
        Parameter parameter2 = new DefaultParameter(new DefaultCvTerm("ec50"), new ParameterValue(new BigDecimal("5")));

        Assert.assertTrue(comparator.compare(parameter1, parameter2) < 0);
        Assert.assertTrue(comparator.compare(parameter2, parameter1) > 0);

        Assert.assertFalse(UnambiguousParameterComparator.areEquals(parameter1, parameter2));
        Assert.assertTrue(UnambiguousParameterComparator.hashCode(parameter1) != UnambiguousParameterComparator.hashCode(parameter2));

        Parameter parameter3 = new DefaultParameter(new DefaultCvTerm("KD"), new ParameterValue(new BigDecimal("10")), new DefaultCvTerm("molar"));
        Parameter parameter4 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal("10")), new DefaultCvTerm("molar"));

        Assert.assertTrue(comparator.compare(parameter3, parameter4) == 0);
        Assert.assertTrue(comparator.compare(parameter4, parameter3) == 0);

        Assert.assertTrue(UnambiguousParameterComparator.areEquals(parameter3, parameter4));
        Assert.assertTrue(UnambiguousParameterComparator.hashCode(parameter3) == UnambiguousParameterComparator.hashCode(parameter4));
    }

    @Test
    public void test_parameter_value_comparison() throws Exception {
        // same parameter but different values
        Parameter parameter1 = new DefaultParameter(new DefaultCvTerm("ec50"), new ParameterValue(new BigDecimal("5")), new DefaultCvTerm("molar"));
        Parameter parameter2 = new DefaultParameter(new DefaultCvTerm("ec50"), new ParameterValue(new BigDecimal("10")), new DefaultCvTerm("molar"));

        Assert.assertTrue(comparator.compare(parameter1, parameter2) < 0);
        Assert.assertTrue(comparator.compare(parameter2, parameter1) > 0);

        Assert.assertFalse(UnambiguousParameterComparator.areEquals(parameter1, parameter2));
        Assert.assertTrue(UnambiguousParameterComparator.hashCode(parameter1) != UnambiguousParameterComparator.hashCode(parameter2));

        Parameter parameter3 = new DefaultParameter(new DefaultCvTerm("KD"), new ParameterValue(new BigDecimal("10")));
        Parameter parameter4 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal("10")));

        Assert.assertTrue(comparator.compare(parameter3, parameter4) == 0);
        Assert.assertTrue(comparator.compare(parameter4, parameter3) == 0);

        Assert.assertTrue(UnambiguousParameterComparator.areEquals(parameter3, parameter4));
        Assert.assertTrue(UnambiguousParameterComparator.hashCode(parameter3) == UnambiguousParameterComparator.hashCode(parameter4));
    }

    @Test
    public void test_parameter_uncertainty_comparison() throws Exception {
        // same parameter but different values
        Parameter parameter1 = new DefaultParameter(new DefaultCvTerm("ec50"), new ParameterValue(new BigDecimal("5")), new DefaultCvTerm("molar"), new BigDecimal("1"));
        Parameter parameter2 = new DefaultParameter(new DefaultCvTerm("ec50"), new ParameterValue(new BigDecimal("10")), new DefaultCvTerm("molar"), new BigDecimal("3"));

        Assert.assertTrue(comparator.compare(parameter1, parameter2) < 0);
        Assert.assertTrue(comparator.compare(parameter2, parameter1) > 0);

        Assert.assertFalse(UnambiguousParameterComparator.areEquals(parameter1, parameter2));
        Assert.assertTrue(UnambiguousParameterComparator.hashCode(parameter1) != UnambiguousParameterComparator.hashCode(parameter2));

        Parameter parameter3 = new DefaultParameter(new DefaultCvTerm("KD"), new ParameterValue(new BigDecimal("10")), new DefaultCvTerm("molar"), new BigDecimal("1"));
        Parameter parameter4 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal("10")), new DefaultCvTerm("molar"), new BigDecimal("1"));

        Assert.assertTrue(comparator.compare(parameter3, parameter4) == 0);
        Assert.assertTrue(comparator.compare(parameter4, parameter3) == 0);

        Assert.assertTrue(UnambiguousParameterComparator.areEquals(parameter3, parameter4));
        Assert.assertTrue(UnambiguousParameterComparator.hashCode(parameter3) == UnambiguousParameterComparator.hashCode(parameter4));
    }
}
