package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultExperiment;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.xml.cache.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.extension.XmlParameter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Unit tester for Xml25ParameterWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class Xml25ParameterWriterTest extends AbstractXml25WriterTest {
    private String parameter = "<parameter term=\"kd\" base=\"10\" " +
            "exponent=\"0\" factor=\"5\">\n" +
            "  <experimentRef>1</experimentRef>\n" +
            "</parameter>";
    private String parameterTermAc = "<parameter term=\"kd\" termAc=\"MI:xxxx\" base=\"10\" " +
            "exponent=\"0\" factor=\"5\">\n" +
            "  <experimentRef>1</experimentRef>\n" +
            "</parameter>";
    private String parameterUnit = "<parameter term=\"kd\" unit=\"molar\" base=\"10\" " +
            "exponent=\"0\" factor=\"5\">\n" +
            "  <experimentRef>1</experimentRef>\n" +
            "</parameter>";
    private String parameterUnitAc = "<parameter term=\"kd\" unit=\"molar\" unitAc=\"MI:xxxx\" base=\"10\" " +
            "exponent=\"0\" factor=\"5\">\n" +
            "  <experimentRef>1</experimentRef>\n" +
            "</parameter>";
    private String parameterDifferentValue = "<parameter term=\"kd\" base=\"10\" " +
            "exponent=\"3\" factor=\"5\">\n" +
            "  <experimentRef>1</experimentRef>\n" +
            "</parameter>";
    private String parameterUncertainty = "<parameter term=\"kd\" base=\"10\" " +
            "exponent=\"0\" factor=\"5\" uncertainty=\"10\">\n" +
            "  <experimentRef>2</experimentRef>\n" +
            "</parameter>";

    private String parameterExistingExperiment = "<parameter term=\"kd\" base=\"10\" " +
            "exponent=\"0\" factor=\"5\">\n" +
            "  <experimentRef>2</experimentRef>\n" +
            "</parameter>";
    private PsiXml25ObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_parameter_null() throws XMLStreamException, IOException {

        Xml25ParameterWriter writer = new Xml25ParameterWriter(createStreamWriter(), elementCache);
        writer.write(null);
        streamWriter.flush();

        Assert.assertEquals("", output.toString());
    }

    @Test
    public void test_write_parameter() throws XMLStreamException, IOException {
        XmlParameter param = new XmlParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5)), null, null);
        this.elementCache.clear();

        Xml25ParameterWriter writer = new Xml25ParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameter, output.toString());
    }

    @Test
    public void test_write_parameter_termAc() throws XMLStreamException, IOException {
        XmlParameter param = new XmlParameter(new DefaultCvTerm("kd", "MI:xxxx"), new ParameterValue(new BigDecimal(5)), null, null);
        this.elementCache.clear();

        Xml25ParameterWriter writer = new Xml25ParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameterTermAc, output.toString());
    }

    @Test
    public void test_write_parameter_unit() throws XMLStreamException, IOException {
        XmlParameter param = new XmlParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5)),
                null, new DefaultCvTerm("molar"));
        this.elementCache.clear();

        Xml25ParameterWriter writer = new Xml25ParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameterUnit, output.toString());
    }

    @Test
    public void test_write_parameter_unitAc() throws XMLStreamException, IOException {
        XmlParameter param = new XmlParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5)),
                null, new DefaultCvTerm("molar","MI:xxxx"));
        this.elementCache.clear();

        Xml25ParameterWriter writer = new Xml25ParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameterUnitAc, output.toString());
    }

    @Test
    public void test_write_parameter_different_value() throws XMLStreamException, IOException {
        XmlParameter param = new XmlParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5),(short)10,(short)3), null, null);
        this.elementCache.clear();

        Xml25ParameterWriter writer = new Xml25ParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameterDifferentValue, output.toString());
    }

    @Test
    public void test_write_parameter_uncertainty() throws XMLStreamException, IOException {
        XmlParameter param = new XmlParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5)), new BigDecimal(10), null);
        this.elementCache.clear();
        Experiment exp = new DefaultExperiment(new DefaultPublication("12345"));
        this.elementCache.extractIdForExperiment(new DefaultExperiment(new DefaultPublication("123")));
        this.elementCache.extractIdForExperiment(exp);
        param.setExperiment(exp);

        Xml25ParameterWriter writer = new Xml25ParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameterUncertainty, output.toString());
    }

    @Test
    public void test_write_parameter_different_experiment() throws XMLStreamException, IOException {
        XmlParameter param = new XmlParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5)), null, null);
        this.elementCache.clear();
        Experiment exp = new DefaultExperiment(new DefaultPublication("12345"));
        this.elementCache.extractIdForExperiment(new DefaultExperiment(new DefaultPublication("123")));
        this.elementCache.extractIdForExperiment(exp);

        Xml25ParameterWriter writer = new Xml25ParameterWriter(createStreamWriter(), elementCache);
        writer.setDefaultExperiment(exp);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(parameterExistingExperiment, output.toString());
    }
}
