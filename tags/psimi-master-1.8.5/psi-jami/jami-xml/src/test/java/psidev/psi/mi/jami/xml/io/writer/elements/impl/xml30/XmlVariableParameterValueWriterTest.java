package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.VariableParameterValue;
import psidev.psi.mi.jami.model.impl.DefaultVariableParameterValue;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for XmlVariableParameterValueWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class XmlVariableParameterValueWriterTest extends AbstractXmlWriterTest {
    private PsiXmlObjectCache elementCache = new InMemoryIdentityObjectCache();

    private String variable_parameter_value = "<variableValue id=\"1\">\n" +
            "  <value>0.5</value>\n" +
            "</variableValue>";
    private String variable_parameter_value_order = "<variableValue id=\"1\" order=\"2\">\n" +
            "  <value>0.5</value>\n" +
            "</variableValue>";
    private String variable_parameter_value_registered = "<variableValue id=\"2\">\n" +
            "  <value>0.5</value>\n" +
            "</variableValue>";

    @Test
    public void test_write_variable_value() throws XMLStreamException, IOException, IllegalRangeException {
        VariableParameterValue value = new DefaultVariableParameterValue("0.5", null);

        XmlVariableParameterValueWriter writer = new XmlVariableParameterValueWriter(createStreamWriter(), elementCache);
        writer.write(value);
        streamWriter.flush();

        Assert.assertEquals(this.variable_parameter_value, output.toString());
    }

    @Test
    public void test_write_variable_value_order() throws XMLStreamException, IOException, IllegalRangeException {
        VariableParameterValue value = new DefaultVariableParameterValue("0.5", null, 2);

        XmlVariableParameterValueWriter writer = new XmlVariableParameterValueWriter(createStreamWriter(), elementCache);
        writer.write(value);
        streamWriter.flush();

        Assert.assertEquals(variable_parameter_value_order, output.toString());
    }

    @Test
    public void test_write_variable_value_already_registered() throws XMLStreamException, IOException, IllegalRangeException {
        this.elementCache.clear();
        this.elementCache.extractIdForVariableParameterValue(new DefaultVariableParameterValue("test", null));

        VariableParameterValue value = new DefaultVariableParameterValue("0.5", null);

        XmlVariableParameterValueWriter writer = new XmlVariableParameterValueWriter(createStreamWriter(), elementCache);
        writer.write(value);
        streamWriter.flush();

        Assert.assertEquals(this.variable_parameter_value_registered, output.toString());
    }
}
