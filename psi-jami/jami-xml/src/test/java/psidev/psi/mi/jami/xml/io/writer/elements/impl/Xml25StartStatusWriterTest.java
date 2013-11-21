package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.xml.io.writer.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester of Xml25StartStatusWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class Xml25StartStatusWriterTest extends AbstractXml25WriterTest {

    private String startStatus = "<startStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0339\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</startStatus>";
    private String startStatusFullName = "<startStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "    <fullName>undetermined position</fullName>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0339\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</startStatus>";
    private String startStatusAliases = "<startStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "    <alias type=\"synonym\">unspecified</alias>\n"+
            "    <alias type=\"test\">test name</alias>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0339\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</startStatus>";
    private String startStatusMod = "<startStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mod\" dbAc=\"MI:0897\" id=\"MI:0339\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</startStatus>";
    private String endStatusPar = "<startStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-par\" id=\"MI:0339\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</startStatus>";
    private String startStatusFirstIdentifier = "<startStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test\" id=\"MI:0339\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    <secondaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "</startStatus>";
    private String startStatusFirstXref = "<startStatus>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "</startStatus>";

    @Test
    public void test_write_cv_no_fullName() throws XMLStreamException, IOException {
        CvTerm startStatus = new DefaultCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);

        Xml25StartStatusWriter writer = new Xml25StartStatusWriter(createStreamWriter());
        writer.write(startStatus);
        streamWriter.flush();

        Assert.assertEquals(this.startStatus, output.toString());
    }

    @Test
    public void test_write_cv_fullName() throws XMLStreamException, IOException {
        CvTerm startStatus = new DefaultCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
        startStatus.setFullName("undetermined position");

        Xml25StartStatusWriter writer = new Xml25StartStatusWriter(createStreamWriter());
        writer.write(startStatus);
        streamWriter.flush();

        Assert.assertEquals(this.startStatusFullName, output.toString());
    }

    @Test
    public void test_write_cv_aliases() throws XMLStreamException, IOException {
        CvTerm startStatus = new DefaultCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
        startStatus.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("synonym"), "unspecified"));
        startStatus.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("test"), "test name"));

        Xml25StartStatusWriter writer = new Xml25StartStatusWriter(createStreamWriter());
        writer.write(startStatus);
        streamWriter.flush();

        Assert.assertEquals(this.startStatusAliases, output.toString());
    }

    @Test
    public void test_write_cv_mod() throws XMLStreamException, IOException {
        CvTerm startStatus = new DefaultCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
        startStatus.setMODIdentifier(startStatus.getMIIdentifier());
        startStatus.setMIIdentifier(null);

        Xml25StartStatusWriter writer = new Xml25StartStatusWriter(createStreamWriter());
        writer.write(startStatus);
        streamWriter.flush();

        Assert.assertEquals(this.startStatusMod, output.toString());
    }

    @Test
    public void test_write_cv_par() throws XMLStreamException, IOException {
        CvTerm startStatus = new DefaultCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
        startStatus.setPARIdentifier(startStatus.getMIIdentifier());
        startStatus.setMIIdentifier(null);
        Xml25StartStatusWriter writer = new Xml25StartStatusWriter(createStreamWriter());
        writer.write(startStatus);
        streamWriter.flush();

        Assert.assertEquals(this.endStatusPar, output.toString());
    }

    @Test
    public void test_write_cv_first_identifier() throws XMLStreamException, IOException {
        CvTerm startStatus = new DefaultCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
        startStatus.getIdentifiers().iterator().next().getDatabase().setShortName("test");
        startStatus.getIdentifiers().iterator().next().getDatabase().setMIIdentifier(null);
        startStatus.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        startStatus.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25StartStatusWriter writer = new Xml25StartStatusWriter(createStreamWriter());
        writer.write(startStatus);
        streamWriter.flush();

        Assert.assertEquals(this.startStatusFirstIdentifier, output.toString());
    }

    @Test
    public void test_write_cv_first_xref() throws XMLStreamException, IOException {
        CvTerm startStatus = new DefaultCvTerm("undetermined");
        startStatus.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        startStatus.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25StartStatusWriter writer = new Xml25StartStatusWriter(createStreamWriter());
        writer.write(startStatus);
        streamWriter.flush();

        Assert.assertEquals(this.startStatusFirstXref, output.toString());
    }
}
