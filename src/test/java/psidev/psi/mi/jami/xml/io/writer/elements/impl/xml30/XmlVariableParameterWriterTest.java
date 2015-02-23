package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.VariableParameter;
import psidev.psi.mi.jami.model.VariableParameterValue;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultVariableParameter;
import psidev.psi.mi.jami.model.impl.DefaultVariableParameterValue;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for XmlVariableParameterWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class XmlVariableParameterWriterTest extends AbstractXmlWriterTest {
    private PsiXmlObjectCache elementCache = new InMemoryIdentityObjectCache();

    private String variable_parameter = "<variableParameter>\n" +
            "  <description>test description</description>\n" +
            "  <variableValueList>\n" +
            "    <variableValue id=\"1\">\n" +
            "      <value>0.5</value>\n" +
            "    </variableValue>\n"+
            "  </variableValueList>\n" +
            "</variableParameter>";
    private String variable_parameter_unit = "<variableParameter>\n" +
            "  <description>test description</description>\n" +
            "  <unit>\n" +
            "    <names>\n" +
            "      <shortLabel>test unit</shortLabel>\n"+
            "    </names>\n"+
            "  </unit>\n" +
            "  <variableValueList>\n" +
            "    <variableValue id=\"1\">\n" +
            "      <value>0.5</value>\n" +
            "    </variableValue>\n"+
            "  </variableValueList>\n" +
            "</variableParameter>";

    @Test
    public void test_write_variable_parameter() throws XMLStreamException, IOException, IllegalRangeException {
        VariableParameter param = new DefaultVariableParameter("test description");
        VariableParameterValue value = new DefaultVariableParameterValue("0.5", param);
        param.getVariableValues().add(value);

        XmlVariableParameterWriter writer = new XmlVariableParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(this.variable_parameter, output.toString());
    }

    @Test
    public void test_write_variable_parameter_unit() throws XMLStreamException, IOException, IllegalRangeException {
        VariableParameter param = new DefaultVariableParameter("test description");
        VariableParameterValue value = new DefaultVariableParameterValue("0.5", param);
        param.getVariableValues().add(value);
        param.setUnit(new DefaultCvTerm("test unit"));

        XmlVariableParameterWriter writer = new XmlVariableParameterWriter(createStreamWriter(), elementCache);
        writer.write(param);
        streamWriter.flush();

        Assert.assertEquals(variable_parameter_unit, output.toString());
    }
}
