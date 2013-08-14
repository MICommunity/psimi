package psidev.psi.mi.jami.utils;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultParameter;

import java.math.BigDecimal;

/**
 * Unit tester for ParameterUtils
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/06/13</pre>
 */

public class ParameterUtilsTest {

    @Test
    public void test_get_parameter_value_as_string(){

        Parameter param1 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3)));
        Parameter param2 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3), (short)10, (short)-4));
        Parameter param3 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3)), new BigDecimal(1));
        Parameter param4 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3), (short)10, (short)-4), new BigDecimal(1));

        Assert.assertEquals("3", ParameterUtils.getParameterValueAsString(param1));
        Assert.assertEquals("3x10^(-4)", ParameterUtils.getParameterValueAsString(param2));
        Assert.assertEquals("3 ~1", ParameterUtils.getParameterValueAsString(param3));
        Assert.assertEquals("3x10^(-4) ~1", ParameterUtils.getParameterValueAsString(param4));
    }

    @Test
    public void test_parse_parameter() throws IllegalParameterException {

        Parameter param1 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3)));
        Parameter param2 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3), (short)10, (short)-4));
        Parameter param3 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3)), new BigDecimal(1));
        Parameter param4 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3), (short)10, (short)-4), new BigDecimal(1));

        Assert.assertEquals(param1, ParameterUtils.createParameterFromString(new DefaultCvTerm("kd"), "3"));
        Assert.assertEquals(param2, ParameterUtils.createParameterFromString(new DefaultCvTerm("kd"), "3x10^(-4)"));
        Assert.assertEquals(param3, ParameterUtils.createParameterFromString(new DefaultCvTerm("kd"), "3 ~1"));
        Assert.assertEquals(param4, ParameterUtils.createParameterFromString(new DefaultCvTerm("kd"), "3x10^(-4) ~1"));
    }

    @Test
    public void test_parse_parameter_type_as_string() throws IllegalParameterException {

        Parameter param1 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3)));
        Parameter param2 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3), (short)10, (short)-4));
        Parameter param3 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3)), new BigDecimal(1));
        Parameter param4 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3), (short)10, (short)-4), new BigDecimal(1));

        Assert.assertEquals(param1, ParameterUtils.createParameterFromString("kd", "3"));
        Assert.assertEquals(param2, ParameterUtils.createParameterFromString("kd", "3x10^(-4)"));
        Assert.assertEquals(param3, ParameterUtils.createParameterFromString("kd", "3 ~1"));
        Assert.assertEquals(param4, ParameterUtils.createParameterFromString("kd", "3x10^(-4) ~1"));
    }

    @Test
    public void test_parse_parameter_with_unit() throws IllegalParameterException {

        Parameter param1 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3)), new DefaultCvTerm("molar"));
        Parameter param2 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3), (short)10, (short)-4), new DefaultCvTerm("molar"));
        Parameter param3 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3)), new DefaultCvTerm("molar"), new BigDecimal(1));
        Parameter param4 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3), (short)10, (short)-4), new DefaultCvTerm("molar"), new BigDecimal(1));

        Assert.assertEquals(param1, ParameterUtils.createParameterFromString(new DefaultCvTerm("kd"), "3", new DefaultCvTerm("molar")));
        Assert.assertEquals(param2, ParameterUtils.createParameterFromString(new DefaultCvTerm("kd"), "3x10^(-4)", new DefaultCvTerm("molar")));
        Assert.assertEquals(param3, ParameterUtils.createParameterFromString(new DefaultCvTerm("kd"), "3 ~1", new DefaultCvTerm("molar")));
        Assert.assertEquals(param4, ParameterUtils.createParameterFromString(new DefaultCvTerm("kd"), "3x10^(-4) ~1", new DefaultCvTerm("molar")));
    }

    @Test
    public void test_parse_parameter_type_as_string_with_unit() throws IllegalParameterException {

        Parameter param1 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3)), new DefaultCvTerm("molar"));
        Parameter param2 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3), (short)10, (short)-4), new DefaultCvTerm("molar"));
        Parameter param3 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3)), new DefaultCvTerm("molar"), new BigDecimal(1));
        Parameter param4 = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3), (short)10, (short)-4), new DefaultCvTerm("molar"), new BigDecimal(1));

        Assert.assertEquals(param1, ParameterUtils.createParameterFromString("kd", "3", "molar"));
        Assert.assertEquals(param2, ParameterUtils.createParameterFromString("kd", "3x10^(-4)", "molar"));
        Assert.assertEquals(param3, ParameterUtils.createParameterFromString("kd", "3 ~1", "molar"));
        Assert.assertEquals(param4, ParameterUtils.createParameterFromString("kd", "3x10^(-4) ~1", "molar"));
    }
}
