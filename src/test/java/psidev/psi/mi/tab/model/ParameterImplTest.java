package psidev.psi.mi.tab.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 16/07/2012
 * Time: 13:41
 * To change this template use File | Settings | File Templates.
 */
public class ParameterImplTest {
    @Test
    public void testParameterOnlyFactor() throws Exception {

        Parameter parameter = new ParameterImpl("Kd", "4.0", "molar");

        assertEquals(parameter.getFactor(), Double.valueOf(4.0));
        assertEquals(parameter.getBase(), Integer.valueOf(10));
        assertEquals(parameter.getExponent(), Integer.valueOf(0));
        assertEquals(parameter.getUncertainty(), Double.valueOf(0.0));
        assertEquals(parameter.getUnit(), "molar");
        assertEquals(parameter.getType(), "Kd");


    }

    @Test
    public void testParameterFactorAndBase() throws Exception {

        Parameter parameter = new ParameterImpl("Kd", "4.0x2", "molar");

        assertEquals(parameter.getFactor(), Double.valueOf(4.0));
        assertEquals(parameter.getBase(), Integer.valueOf(2));
        assertEquals(parameter.getExponent(), Integer.valueOf(0));
        assertEquals(parameter.getUncertainty(), Double.valueOf(0.0));
        assertEquals(parameter.getUnit(), "molar");
        assertEquals(parameter.getType(), "Kd");


    }

    @Test
    public void testParameterFactorBaseAndExponent() throws Exception {

        Parameter parameter = new ParameterImpl("Kd", "4.0x3^2", "molar");

        assertEquals(parameter.getFactor(), Double.valueOf(4.0));
        assertEquals(parameter.getBase(), Integer.valueOf(3));
        assertEquals(parameter.getExponent(), Integer.valueOf(2));
        assertEquals(parameter.getUncertainty(), Double.valueOf(0.0));
        assertEquals(parameter.getUnit(), "molar");
        assertEquals(parameter.getType(), "Kd");

    }

    @Test
    public void testParameterAll() throws Exception {

        Parameter parameter = new ParameterImpl("Kd", "4.0x2^5 ~0.3", "molar");

        assertEquals(parameter.getFactor(), Double.valueOf(4.0));
        assertEquals(parameter.getBase(), Integer.valueOf(2));
        assertEquals(parameter.getExponent(), Integer.valueOf(5));
        assertEquals(parameter.getUncertainty(), Double.valueOf(0.3));
        assertEquals(parameter.getUnit(), "molar");
        assertEquals(parameter.getType(), "Kd");

    }

    @Test
    public void testParameterOnlyNegFactor() throws Exception {

        Parameter parameter = new ParameterImpl("Kd", "-4.0", "molar");

        assertEquals(parameter.getFactor(), Double.valueOf(-4.0));
        assertEquals(parameter.getBase(), Integer.valueOf(10));
        assertEquals(parameter.getExponent(), Integer.valueOf(0));
        assertEquals(parameter.getUncertainty(), Double.valueOf(0.0));
        assertEquals(parameter.getUnit(), "molar");
        assertEquals(parameter.getType(), "Kd");


    }

    @Test
    public void testParameterNegFactorAndBase() throws Exception {

        Parameter parameter = new ParameterImpl("Kd", "-4.0x-2", "molar");

        assertEquals(parameter.getFactor(), Double.valueOf(-4.0));
        assertEquals(parameter.getBase(), Integer.valueOf(-2));
        assertEquals(parameter.getExponent(), Integer.valueOf(0));
        assertEquals(parameter.getUncertainty(), Double.valueOf(0.0));
        assertEquals(parameter.getUnit(), "molar");
        assertEquals(parameter.getType(), "Kd");


    }

    @Test
    public void testParameterNegFactorBaseAndExponent() throws Exception {

        Parameter parameter = new ParameterImpl("Kd", "-4.0x-12^-2", "molar");

        assertEquals(parameter.getFactor(), Double.valueOf(-4.0));
        assertEquals(parameter.getBase(), Integer.valueOf(-12));
        assertEquals(parameter.getExponent(), Integer.valueOf(-2));
        assertEquals(parameter.getUncertainty(), Double.valueOf(0.0));
        assertEquals(parameter.getUnit(), "molar");
        assertEquals(parameter.getType(), "Kd");

    }

    @Test
    public void testParameterAllNeg() throws Exception {

        Parameter parameter = new ParameterImpl("Kd", "-4.0x-12^-2 ~-0.3", "molar");

        assertEquals(parameter.getFactor(), Double.valueOf(-4.0));
        assertEquals(parameter.getBase(), Integer.valueOf(-12));
        assertEquals(parameter.getExponent(), Integer.valueOf(-2));
        assertEquals(parameter.getUncertainty(), Double.valueOf(-0.3));
        assertEquals(parameter.getUnit(), "molar");
        assertEquals(parameter.getType(), "Kd");

    }

	@Test
	public void testParameterDifferentConstructor() throws Exception {

		Parameter parameterOne = new ParameterImpl("Kd", "1.62 x10^-8 ~ 0.01", "molar");
		Parameter parameterTwo = new ParameterImpl("Kd", 1.62, 10, -8, 0.01, "molar");

		assertEquals(parameterOne.getFactor(), Double.valueOf(1.62));
		assertEquals(parameterOne.getBase(), Integer.valueOf(10));
		assertEquals(parameterOne.getExponent(), Integer.valueOf(-8));
		assertEquals(parameterOne.getUncertainty(), Double.valueOf(0.01));
		assertEquals(parameterOne.getUnit(), "molar");
		assertEquals(parameterOne.getType(), "Kd");
		assertEquals(parameterOne.getValue(),"1.62x10^-8 ~0.01");

		assertEquals(parameterTwo.getFactor(), Double.valueOf(1.62));
		assertEquals(parameterTwo.getBase(), Integer.valueOf(10));
		assertEquals(parameterTwo.getExponent(), Integer.valueOf(-8));
		assertEquals(parameterTwo.getUncertainty(), Double.valueOf(0.01));
		assertEquals(parameterTwo.getUnit(), "molar");
		assertEquals(parameterTwo.getType(), "Kd");
		assertEquals(parameterTwo.getValue(),"1.62x10^-8 ~0.01");

		assertEquals(parameterOne, parameterTwo);


	}
}
