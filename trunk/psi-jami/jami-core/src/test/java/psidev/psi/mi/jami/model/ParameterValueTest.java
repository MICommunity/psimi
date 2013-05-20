package psidev.psi.mi.jami.model;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Unit tester for ParameterValue
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class ParameterValueTest {

    @Test
    public void test_default_parameter_value() throws Exception {
        ParameterValue parameterValue = new ParameterValue(new BigDecimal("5"));

        Assert.assertTrue(parameterValue.getBase() == 10);
        Assert.assertTrue(parameterValue.getExponent() == 0);
        Assert.assertTrue(parameterValue.getFactor().equals(new BigDecimal("5")));
        Assert.assertTrue(parameterValue.doubleValue() == 5d);
        Assert.assertTrue(parameterValue.floatValue() == 5f);
        Assert.assertTrue(parameterValue.intValue() == 5);
        Assert.assertTrue(parameterValue.longValue() == 5);
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_alias_no_factor() throws Exception {
        ParameterValue parameterValue = new ParameterValue(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_alias_no_factor_2() throws Exception {
        ParameterValue parameterValue = new ParameterValue(null, (short)10, (short)0);
    }
}
