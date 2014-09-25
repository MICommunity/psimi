package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.VariableParameterValue;
import psidev.psi.mi.jami.model.VariableParameterValueSet;
import psidev.psi.mi.jami.model.impl.DefaultVariableParameterValue;
import psidev.psi.mi.jami.model.impl.DefaultVariableParameterValueSet;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for XmlVariableParameterValueSetWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class XmlVariableParameterValueSetWriterTest extends AbstractXmlWriterTest {
    private PsiXmlObjectCache elementCache = new InMemoryIdentityObjectCache();

    private String variable_parameter_value_set = "<experimentalVariableValues>\n" +
            "  <variableValueRef>1</variableValueRef>\n" +
            "</experimentalVariableValues>";
    private String variable_parameter_value_set_empty = "";
    private String variable_parameter_value_set_registered = "<experimentalVariableValues>\n" +
            "  <variableValueRef>2</variableValueRef>\n" +
            "</experimentalVariableValues>";

    @Test
    public void test_write_variable_value_set() throws XMLStreamException, IOException, IllegalRangeException {
        VariableParameterValueSet valueSet = new DefaultVariableParameterValueSet();
        valueSet.add(new DefaultVariableParameterValue("test", null));

        XmlVariableParameterValueSetWriter writer = new XmlVariableParameterValueSetWriter(createStreamWriter(), elementCache);
        writer.write(valueSet);
        streamWriter.flush();

        Assert.assertEquals(this.variable_parameter_value_set, output.toString());
    }

    @Test
    public void test_write_variable_value_set_empty() throws XMLStreamException, IOException, IllegalRangeException {
        VariableParameterValueSet valueSet = new DefaultVariableParameterValueSet();

        XmlVariableParameterValueSetWriter writer = new XmlVariableParameterValueSetWriter(createStreamWriter(), elementCache);
        writer.write(valueSet);
        streamWriter.flush();

        Assert.assertEquals(variable_parameter_value_set_empty, output.toString());
    }

    @Test
    public void test_write_variable_value_set_registered() throws XMLStreamException, IOException, IllegalRangeException {
        VariableParameterValue v = new DefaultVariableParameterValue("test", null);
        this.elementCache.clear();
        this.elementCache.extractIdForVariableParameterValue(new DefaultVariableParameterValue("test2", null));
        this.elementCache.extractIdForVariableParameterValue(v);

        VariableParameterValueSet valueSet = new DefaultVariableParameterValueSet();
        valueSet.add(v);

        XmlVariableParameterValueSetWriter writer = new XmlVariableParameterValueSetWriter(createStreamWriter(), elementCache);
        writer.write(valueSet);
        streamWriter.flush();

        Assert.assertEquals(this.variable_parameter_value_set_registered, output.toString());
    }
}
