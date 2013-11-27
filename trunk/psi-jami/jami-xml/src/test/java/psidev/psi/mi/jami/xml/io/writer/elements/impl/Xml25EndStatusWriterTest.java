package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.CvTermUtils;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unti tester for Xml25EndStatusWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class Xml25EndStatusWriterTest extends AbstractXml25WriterTest {

    private String endStatus = "<endStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0339\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</endStatus>";
    private String endStatusFullName = "<endStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "    <fullName>undetermined position</fullName>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0339\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</endStatus>";
    private String endStatusAliases = "<endStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "    <alias type=\"synonym\">unspecified</alias>\n"+
            "    <alias type=\"test\">test name</alias>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0339\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</endStatus>";
    private String endStatusMod = "<endStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mod\" id=\"MI:0339\" refType=\"identity\"/>\n"+
            "  </xref>\n"+
            "</endStatus>";
    private String endStatusPar = "<endStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-par\" id=\"MI:0339\" refType=\"identity\"/>\n"+
            "  </xref>\n"+
            "</endStatus>";
    private String endStatusFirstIdentifier = "<endStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test\" id=\"MI:0339\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    <secondaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "</endStatus>";
    private String endStatusFirstXref = "<endStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "</endStatus>";

    @Test
    public void test_write_cv_no_fullName() throws XMLStreamException, IOException {
        CvTerm endStatus = CvTermUtils.createUndeterminedStatus();

        Xml25EndStatusWriter writer = new Xml25EndStatusWriter(createStreamWriter());
        writer.write(endStatus);
        streamWriter.flush();

        Assert.assertEquals(this.endStatus, output.toString());
    }

    @Test
    public void test_write_cv_fullName() throws XMLStreamException, IOException {
        CvTerm endStatus = CvTermUtils.createUndeterminedStatus();
        endStatus.setFullName("undetermined position");

        Xml25EndStatusWriter writer = new Xml25EndStatusWriter(createStreamWriter());
        writer.write(endStatus);
        streamWriter.flush();

        Assert.assertEquals(this.endStatusFullName, output.toString());
    }

    @Test
    public void test_write_cv_aliases() throws XMLStreamException, IOException {
        CvTerm endStatus = CvTermUtils.createUndeterminedStatus();
        endStatus.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("synonym"), "unspecified"));
        endStatus.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("test"), "test name"));

        Xml25EndStatusWriter writer = new Xml25EndStatusWriter(createStreamWriter());
        writer.write(endStatus);
        streamWriter.flush();

        Assert.assertEquals(this.endStatusAliases, output.toString());
    }

    @Test
    public void test_write_cv_mod() throws XMLStreamException, IOException {
        CvTerm endStatus = CvTermUtils.createUndeterminedStatus();
        endStatus.setMODIdentifier(endStatus.getMIIdentifier());
        endStatus.setMIIdentifier(null);

        Xml25EndStatusWriter writer = new Xml25EndStatusWriter(createStreamWriter());
        writer.write(endStatus);
        streamWriter.flush();

        Assert.assertEquals(this.endStatusMod, output.toString());
    }

    @Test
    public void test_write_cv_par() throws XMLStreamException, IOException {
        CvTerm endStatus = CvTermUtils.createUndeterminedStatus();
        endStatus.setPARIdentifier(endStatus.getMIIdentifier());
        endStatus.setMIIdentifier(null);
        Xml25EndStatusWriter writer = new Xml25EndStatusWriter(createStreamWriter());
        writer.write(endStatus);
        streamWriter.flush();

        Assert.assertEquals(this.endStatusPar, output.toString());
    }

    @Test
    public void test_write_cv_first_identifier() throws XMLStreamException, IOException {
        CvTerm endStatus = CvTermUtils.createUndeterminedStatus();
        endStatus.getIdentifiers().iterator().next().getDatabase().setShortName("test");
        endStatus.getIdentifiers().iterator().next().getDatabase().setMIIdentifier(null);
        endStatus.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        endStatus.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25EndStatusWriter writer = new Xml25EndStatusWriter(createStreamWriter());
        writer.write(endStatus);
        streamWriter.flush();

        Assert.assertEquals(this.endStatusFirstIdentifier, output.toString());
    }

    @Test
    public void test_write_cv_first_xref() throws XMLStreamException, IOException {
        CvTerm endStatus = new DefaultCvTerm("undetermined");
        endStatus.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        endStatus.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25EndStatusWriter writer = new Xml25EndStatusWriter(createStreamWriter());
        writer.write(endStatus);
        streamWriter.flush();

        Assert.assertEquals(this.endStatusFirstXref, output.toString());
    }
}
