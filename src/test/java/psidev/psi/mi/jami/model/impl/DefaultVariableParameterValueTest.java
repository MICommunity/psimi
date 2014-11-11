package psidev.psi.mi.jami.model.impl;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.VariableParameterValue;

/**
 * Unit tester for DefaultVariableParameterValue
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/05/13</pre>
 */

public class DefaultVariableParameterValueTest {

    @Test
    public void test_create_variable_parameter_value() throws Exception {
        VariableParameterValue value = new DefaultVariableParameterValue("S phase", null);

        Assert.assertEquals("S phase", value.getValue());
        Assert.assertNull(value.getOrder());
        Assert.assertNull(value.getVariableParameter());
    }

    @Test
    public void test_create_variable_parameter_value_with_order() throws Exception {
        VariableParameterValue value = new DefaultVariableParameterValue("S phase", null, 1);

        Assert.assertEquals("S phase", value.getValue());
        Assert.assertEquals(new Integer(1), value.getOrder());
        Assert.assertNull(value.getVariableParameter());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_variable_parameter_value_no_value() throws Exception {
        VariableParameterValue value = new DefaultVariableParameterValue(null, null, 1);
    }
}
