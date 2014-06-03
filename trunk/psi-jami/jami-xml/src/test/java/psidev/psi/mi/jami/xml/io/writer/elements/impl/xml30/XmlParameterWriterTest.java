package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultParameter;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Unit tester for XmlParameterWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class XmlParameterWriterTest extends AbstractXmlWriterTest {
    private String parameter = "<parameter term=\"kd\" base=\"10\" " +
            "exponent=\"0\" factor=\"5\"/>";
    private String parameterTermAc = "<parameter term=\"kd\" termAc=\"MI:xxxx\" base=\"10\" " +
            "exponent=\"0\" factor=\"5\"/>";
    private String parameterUnit = "<parameter term=\"kd\" unit=\"molar\" base=\"10\" " +
            "exponent=\"0\" factor=\"5\"/>";
    private String parameterUnitAc = "<parameter term=\"kd\" unit=\"molar\" unitAc=\"MI:xxxx\" base=\"10\" " +
            "exponent=\"0\" factor=\"5\"/>";
    private String parameterDifferentValue = "<parameter term=\"kd\" base=\"10\" " +
            "exponent=\"3\" factor=\"5\"/>";
    private String parameterUncertainty = "<parameter term=\"kd\" base=\"10\" " +
            "exponent=\"0\" factor=\"5\" uncertainty=\"10\"/>";

    private PsiXmlObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_parameter_null() throws XMLStreamException, IOException {

        XmlParameterWriter writer = new XmlParameterWriter(createStreamWriter(), elementCache);
        writer.write(null);
        streamWriter.flush();

        Assert.assertEquals("", output.toString());
    }

    @Test
    public void test_write_parameter() throws XMLStreamException, IOException {
        Parameter param = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5)));
        this.elementCache.clear();

        XmlParameterWriter writer = new XmlParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameter, output.toString());
    }

    @Test
    public void test_write_parameter_termAc() throws XMLStreamException, IOException {
        Parameter param = new DefaultParameter(new DefaultCvTerm("kd", "MI:xxxx"), new ParameterValue(new BigDecimal(5)));
        this.elementCache.clear();

        XmlParameterWriter writer = new XmlParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameterTermAc, output.toString());
    }

    @Test
    public void test_write_parameter_unit() throws XMLStreamException, IOException {
        Parameter param = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5)),
                new DefaultCvTerm("molar"));
        this.elementCache.clear();

        XmlParameterWriter writer = new XmlParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameterUnit, output.toString());
    }

    @Test
    public void test_write_parameter_unitAc() throws XMLStreamException, IOException {
        Parameter param = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5)),
                new DefaultCvTerm("molar","MI:xxxx"));
        this.elementCache.clear();

        XmlParameterWriter writer = new XmlParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameterUnitAc, output.toString());
    }

    @Test
    public void test_write_parameter_different_value() throws XMLStreamException, IOException {
        Parameter param = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5),(short)10,(short)3));
        this.elementCache.clear();

        XmlParameterWriter writer = new XmlParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameterDifferentValue, output.toString());
    }

    @Test
    public void test_write_parameter_uncertainty() throws XMLStreamException, IOException {
        Parameter param = new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5)), new BigDecimal(10));
        this.elementCache.clear();

        XmlParameterWriter writer = new XmlParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameterUncertainty, output.toString());
    }
}