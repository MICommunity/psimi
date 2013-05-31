package psidev.psi.mi.jami.model.impl;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ModelledParameter;
import psidev.psi.mi.jami.model.ParameterValue;

import java.math.BigDecimal;

/**
 * Unit tester for DefaultModelledParameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class DefaultModelledParameterTest {

    @Test
    public void test_create_parameter_type_and_value() throws Exception {
        ModelledParameter parameter = new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal("5")));

        Assert.assertEquals(new DefaultCvTerm("kd"), parameter.getType());
        Assert.assertEquals(5, parameter.getValue().intValue());
        Assert.assertNull(parameter.getUnit());
        Assert.assertNull(parameter.getUncertainty());
        Assert.assertNotNull(parameter.getPublications());
    }

    @Test
    public void test_create_parameter_with_unit() throws Exception {
        ModelledParameter parameter = new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal("5")), new DefaultCvTerm("molar"));

        Assert.assertEquals(new DefaultCvTerm("kd"), parameter.getType());
        Assert.assertEquals(5, parameter.getValue().intValue());
        Assert.assertNotNull(parameter.getUnit());
        Assert.assertEquals(new DefaultCvTerm("molar"), parameter.getUnit());
        Assert.assertNull(parameter.getUncertainty());
    }

    @Test
    public void test_create_parameter_with_uncertainty() throws Exception {
        ModelledParameter parameter = new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal("5")), new DefaultCvTerm("molar"), new BigDecimal("10"));

        Assert.assertEquals(new DefaultCvTerm("kd"), parameter.getType());
        Assert.assertEquals(5, parameter.getValue().intValue());
        Assert.assertNotNull(parameter.getUnit());
        Assert.assertEquals(new DefaultCvTerm("molar"), parameter.getUnit());
        Assert.assertNotNull(parameter.getUncertainty());
        Assert.assertTrue(parameter.getUncertainty().intValue() == 10);
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_parameter_no_type() throws Exception {
        ModelledParameter parameter = new DefaultModelledParameter(null, new ParameterValue(new BigDecimal("5")));
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_parameter_no_value() throws Exception {
        ModelledParameter parameter = new DefaultModelledParameter(new DefaultCvTerm("kd"), null, null, null);
    }
}
