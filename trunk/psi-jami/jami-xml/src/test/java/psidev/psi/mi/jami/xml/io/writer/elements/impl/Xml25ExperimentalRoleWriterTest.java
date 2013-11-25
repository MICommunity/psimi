package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.xml.io.writer.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for Xml25ExperimentalRoleWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class Xml25ExperimentalRoleWriterTest extends AbstractXml25WriterTest {

    private String expRole = "<experimentalRole>\n" +
            "  <names>\n" +
            "    <shortLabel>unspecified role</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</experimentalRole>";
    private String expRoleFullName = "<experimentalRole>\n" +
            "  <names>\n" +
            "    <shortLabel>unspecified role</shortLabel>\n"+
            "    <fullName>unspecified role</fullName>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</experimentalRole>";
    private String expRoleAliases = "<experimentalRole>\n" +
            "  <names>\n" +
            "    <shortLabel>unspecified role</shortLabel>\n"+
            "    <alias type=\"synonym\">unspecified</alias>\n"+
            "    <alias type=\"test\">test name</alias>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</experimentalRole>";
    private String expRoleMod = "<experimentalRole>\n" +
            "  <names>\n" +
            "    <shortLabel>unspecified role</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mod\" id=\"MI:0499\" refType=\"identity\"/>\n"+
            "  </xref>\n"+
            "</experimentalRole>";
    private String expRolePar = "<experimentalRole>\n" +
            "  <names>\n" +
            "    <shortLabel>unspecified role</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-par\" id=\"MI:0499\" refType=\"identity\"/>\n"+
            "  </xref>\n"+
            "</experimentalRole>";
    private String expRoleFirstIdentifier = "<experimentalRole>\n" +
            "  <names>\n" +
            "    <shortLabel>unspecified role</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    <secondaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "</experimentalRole>";
    private String expRoleFirstXref = "<experimentalRole>\n" +
            "  <names>\n" +
            "    <shortLabel>unspecified role</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "</experimentalRole>";

    @Test
    public void test_write_cv_no_fullName() throws XMLStreamException, IOException {
        CvTerm expRole = CvTermUtils.createUnspecifiedRole();

        Xml25ExperimentalRoleWriter writer = new Xml25ExperimentalRoleWriter(createStreamWriter());
        writer.write(expRole);
        streamWriter.flush();

        Assert.assertEquals(this.expRole, output.toString());
    }

    @Test
    public void test_write_cv_fullName() throws XMLStreamException, IOException {
        CvTerm expRole = CvTermUtils.createUnspecifiedRole();
        expRole.setFullName("unspecified role");

        Xml25ExperimentalRoleWriter writer = new Xml25ExperimentalRoleWriter(createStreamWriter());
        writer.write(expRole);
        streamWriter.flush();

        Assert.assertEquals(this.expRoleFullName, output.toString());
    }

    @Test
    public void test_write_cv_aliases() throws XMLStreamException, IOException {
        CvTerm expRole = CvTermUtils.createUnspecifiedRole();
        expRole.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("synonym"), "unspecified"));
        expRole.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("test"), "test name"));

        Xml25ExperimentalRoleWriter writer = new Xml25ExperimentalRoleWriter(createStreamWriter());
        writer.write(expRole);
        streamWriter.flush();

        Assert.assertEquals(this.expRoleAliases, output.toString());
    }

    @Test
    public void test_write_cv_mod() throws XMLStreamException, IOException {
        CvTerm expRole = CvTermUtils.createUnspecifiedRole();
        expRole.setMODIdentifier(expRole.getMIIdentifier());
        expRole.setMIIdentifier(null);

        Xml25ExperimentalRoleWriter writer = new Xml25ExperimentalRoleWriter(createStreamWriter());
        writer.write(expRole);
        streamWriter.flush();

        Assert.assertEquals(this.expRoleMod, output.toString());
    }

    @Test
    public void test_write_cv_par() throws XMLStreamException, IOException {
        CvTerm expRole = CvTermUtils.createUnspecifiedRole();
        expRole.setPARIdentifier(expRole.getMIIdentifier());
        expRole.setMIIdentifier(null);
        Xml25ExperimentalRoleWriter writer = new Xml25ExperimentalRoleWriter(createStreamWriter());
        writer.write(expRole);
        streamWriter.flush();

        Assert.assertEquals(this.expRolePar, output.toString());
    }

    @Test
    public void test_write_cv_first_identifier() throws XMLStreamException, IOException {
        CvTerm expRole = CvTermUtils.createUnspecifiedRole();
        expRole.getIdentifiers().iterator().next().getDatabase().setShortName("test");
        expRole.getIdentifiers().iterator().next().getDatabase().setMIIdentifier(null);
        expRole.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        expRole.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25ExperimentalRoleWriter writer = new Xml25ExperimentalRoleWriter(createStreamWriter());
        writer.write(expRole);
        streamWriter.flush();

        Assert.assertEquals(this.expRoleFirstIdentifier, output.toString());
    }

    @Test
    public void test_write_cv_first_xref() throws XMLStreamException, IOException {
        CvTerm expRole = new DefaultCvTerm("unspecified role");
        expRole.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        expRole.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25ExperimentalRoleWriter writer = new Xml25ExperimentalRoleWriter(createStreamWriter());
        writer.write(expRole);
        streamWriter.flush();

        Assert.assertEquals(this.expRoleFirstXref, output.toString());
    }
}
