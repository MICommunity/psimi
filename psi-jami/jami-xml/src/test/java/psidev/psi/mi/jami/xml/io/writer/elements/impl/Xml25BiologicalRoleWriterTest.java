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
 * Unit tester for Xml25BiologicalRoleWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class Xml25BiologicalRoleWriterTest extends AbstractXml25WriterTest{

    private String bioRole = "<biologicalRole>\n" +
            "  <names>\n" +
            "    <shortLabel>unspecified role</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</biologicalRole>";
    private String bioRoleFullName = "<biologicalRole>\n" +
            "  <names>\n" +
            "    <shortLabel>unspecified role</shortLabel>\n"+
            "    <fullName>unspecified role</fullName>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</biologicalRole>";
    private String bioRoleAliases = "<biologicalRole>\n" +
            "  <names>\n" +
            "    <shortLabel>unspecified role</shortLabel>\n"+
            "    <alias type=\"synonym\">unspecified</alias>\n"+
            "    <alias type=\"test\">test name</alias>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</biologicalRole>";
    private String bioRoleMod = "<biologicalRole>\n" +
            "  <names>\n" +
            "    <shortLabel>unspecified role</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mod\" id=\"MI:0499\" refType=\"identity\"/>\n"+
            "  </xref>\n"+
            "</biologicalRole>";
    private String bioRolePar = "<biologicalRole>\n" +
            "  <names>\n" +
            "    <shortLabel>unspecified role</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-par\" id=\"MI:0499\" refType=\"identity\"/>\n"+
            "  </xref>\n"+
            "</biologicalRole>";
    private String bioRoleFirstIdentifier = "<biologicalRole>\n" +
            "  <names>\n" +
            "    <shortLabel>unspecified role</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    <secondaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "</biologicalRole>";
    private String bioRoleFirstXref = "<biologicalRole>\n" +
            "  <names>\n" +
            "    <shortLabel>unspecified role</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "</biologicalRole>";

    @Test
    public void test_write_cv_no_fullName() throws XMLStreamException, IOException {
        CvTerm bioRole = CvTermUtils.createUnspecifiedRole();
                
        Xml25BiologicalRoleWriter writer = new Xml25BiologicalRoleWriter(createStreamWriter());
        writer.write(bioRole);
        streamWriter.flush();

        Assert.assertEquals(this.bioRole, output.toString());
    }

    @Test
    public void test_write_cv_fullName() throws XMLStreamException, IOException {
        CvTerm bioRole = CvTermUtils.createUnspecifiedRole();
        bioRole.setFullName("unspecified role");

        Xml25BiologicalRoleWriter writer = new Xml25BiologicalRoleWriter(createStreamWriter());
        writer.write(bioRole);
        streamWriter.flush();

        Assert.assertEquals(this.bioRoleFullName, output.toString());
    }

    @Test
    public void test_write_cv_aliases() throws XMLStreamException, IOException {
        CvTerm bioRole = CvTermUtils.createUnspecifiedRole();
        bioRole.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("synonym"), "unspecified"));
        bioRole.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("test"), "test name"));

        Xml25BiologicalRoleWriter writer = new Xml25BiologicalRoleWriter(createStreamWriter());
        writer.write(bioRole);
        streamWriter.flush();

        Assert.assertEquals(this.bioRoleAliases, output.toString());
    }

    @Test
    public void test_write_cv_mod() throws XMLStreamException, IOException {
        CvTerm bioRole = CvTermUtils.createUnspecifiedRole();
        bioRole.setMODIdentifier(bioRole.getMIIdentifier());
        bioRole.setMIIdentifier(null);

        Xml25BiologicalRoleWriter writer = new Xml25BiologicalRoleWriter(createStreamWriter());
        writer.write(bioRole);
        streamWriter.flush();

        Assert.assertEquals(this.bioRoleMod, output.toString());
    }

    @Test
    public void test_write_cv_par() throws XMLStreamException, IOException {
        CvTerm bioRole = CvTermUtils.createUnspecifiedRole();
        bioRole.setPARIdentifier(bioRole.getMIIdentifier());
        bioRole.setMIIdentifier(null);
        Xml25BiologicalRoleWriter writer = new Xml25BiologicalRoleWriter(createStreamWriter());
        writer.write(bioRole);
        streamWriter.flush();

        Assert.assertEquals(this.bioRolePar, output.toString());
    }

    @Test
    public void test_write_cv_first_identifier() throws XMLStreamException, IOException {
        CvTerm bioRole = CvTermUtils.createUnspecifiedRole();
        bioRole.getIdentifiers().iterator().next().getDatabase().setShortName("test");
        bioRole.getIdentifiers().iterator().next().getDatabase().setMIIdentifier(null);
        bioRole.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        bioRole.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25BiologicalRoleWriter writer = new Xml25BiologicalRoleWriter(createStreamWriter());
        writer.write(bioRole);
        streamWriter.flush();

        Assert.assertEquals(this.bioRoleFirstIdentifier, output.toString());
    }

    @Test
    public void test_write_cv_first_xref() throws XMLStreamException, IOException {
        CvTerm bioRole = new DefaultCvTerm("unspecified role");
        bioRole.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        bioRole.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25BiologicalRoleWriter writer = new Xml25BiologicalRoleWriter(createStreamWriter());
        writer.write(bioRole);
        streamWriter.flush();

        Assert.assertEquals(this.bioRoleFirstXref, output.toString());
    }
}
