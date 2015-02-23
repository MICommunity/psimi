package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.CvTermUtils;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for XmlAliasWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/11/13</pre>
 */

public class XmlAliasWriterTest extends AbstractXmlWriterTest {
    private String alias_no_type="<alias>BRCA2</alias>";
    private String alias_not_type_ac="<alias type=\"gene name\">BRCA2</alias>";
    private String alias_with_full_type="<alias type=\"gene name\" typeAc=\"MI:0301\">BRCA2</alias>";

    @Test
    public void test_write_alias_null() throws XMLStreamException, IOException {

        XmlAliasWriter writer = new XmlAliasWriter(createStreamWriter());
        writer.write(null);
        streamWriter.flush();

        Assert.assertEquals("", output.toString());
    }

    @Test
    public void test_write_alias_no_type() throws XMLStreamException, IOException {
        Alias alias = new DefaultAlias("BRCA2");

        XmlAliasWriter writer = new XmlAliasWriter(createStreamWriter());
        writer.write(alias);
        streamWriter.flush();

        Assert.assertEquals(alias_no_type, output.toString());
    }

    @Test
    public void test_write_alias_no_type_id() throws XMLStreamException, IOException {
        Alias alias = new DefaultAlias(new DefaultCvTerm("gene name"), "BRCA2");

        XmlAliasWriter writer = new XmlAliasWriter(createStreamWriter());
        writer.write(alias);
        streamWriter.flush();

        Assert.assertEquals(alias_not_type_ac, output.toString());
    }

    @Test
    public void test_write_alias_with_type() throws XMLStreamException, IOException {
        Alias alias = new DefaultAlias(CvTermUtils.createGeneNameAliasType(), "BRCA2");

        XmlAliasWriter writer = new XmlAliasWriter(createStreamWriter());
        writer.write(alias);
        streamWriter.flush();

        Assert.assertEquals(alias_with_full_type, output.toString());
    }
}
