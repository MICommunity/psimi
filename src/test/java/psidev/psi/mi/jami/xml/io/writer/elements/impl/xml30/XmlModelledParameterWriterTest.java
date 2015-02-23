package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ModelledParameter;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultModelledParameter;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Unit tester for XmlModelledParameterWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class XmlModelledParameterWriterTest extends AbstractXmlWriterTest {
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
    private String parameter_bibref = "<parameter term=\"kd\" base=\"10\" " +
            "exponent=\"0\" factor=\"5\">\n" +
            "  <bibref>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "    <attributeList>\n" +
            "      <attribute name=\"publication title\" nameAc=\"MI:1091\">test title</attribute>\n"+
            "    </attributeList>\n"+
            "  </bibref>\n"+
            "</parameter>";

    private PsiXmlObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_parameter_null() throws XMLStreamException, IOException {

        XmlModelledParameterWriter writer = new XmlModelledParameterWriter(createStreamWriter(), elementCache);
        writer.write(null);
        streamWriter.flush();

        Assert.assertEquals("", output.toString());
    }

    @Test
    public void test_write_parameter() throws XMLStreamException, IOException {
        Parameter param = new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5)));
        this.elementCache.clear();

        XmlModelledParameterWriter writer = new XmlModelledParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameter, output.toString());
    }

    @Test
    public void test_write_parameter_termAc() throws XMLStreamException, IOException {
        Parameter param = new DefaultModelledParameter(new DefaultCvTerm("kd", "MI:xxxx"), new ParameterValue(new BigDecimal(5)));
        this.elementCache.clear();

        XmlModelledParameterWriter writer = new XmlModelledParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameterTermAc, output.toString());
    }

    @Test
    public void test_write_parameter_unit() throws XMLStreamException, IOException {
        Parameter param = new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5)),
                new DefaultCvTerm("molar"));
        this.elementCache.clear();

        XmlModelledParameterWriter writer = new XmlModelledParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameterUnit, output.toString());
    }

    @Test
    public void test_write_parameter_unitAc() throws XMLStreamException, IOException {
        Parameter param = new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5)),
                new DefaultCvTerm("molar","MI:xxxx"));
        this.elementCache.clear();

        XmlModelledParameterWriter writer = new XmlModelledParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameterUnitAc, output.toString());
    }

    @Test
    public void test_write_parameter_different_value() throws XMLStreamException, IOException {
        Parameter param = new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5),(short)10,(short)3));
        this.elementCache.clear();

        XmlModelledParameterWriter writer = new XmlModelledParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameterDifferentValue, output.toString());
    }

    @Test
    public void test_write_parameter_uncertainty() throws XMLStreamException, IOException {
        Parameter param = new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5)), new BigDecimal(10));
        this.elementCache.clear();

        XmlModelledParameterWriter writer = new XmlModelledParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameterUncertainty, output.toString());
    }

    @Test
    public void test_write_parameter_bibref() throws XMLStreamException, IOException {
        ModelledParameter param = new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5)));
        param.setPublication(new DefaultPublication("xxxxxx"));
        param.getPublication().setTitle("test title");
        this.elementCache.clear();

        XmlModelledParameterWriter writer = new XmlModelledParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameter_bibref, output.toString());
    }
}