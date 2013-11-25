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
 * Unti tester for Xml25ExperimentalPreparationWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class Xml25ExperimentalPreparationWriterTest extends AbstractXml25WriterTest {

    private String expPrep = "<experimentalPreparation>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" id=\"MI:0339\" refType=\"identity\"/>\n"+
            "  </xref>\n"+
            "</experimentalPreparation>";
    private String expPrepFullName = "<experimentalPreparation>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "    <fullName>undetermined position</fullName>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" id=\"MI:0339\" refType=\"identity\"/>\n"+
            "  </xref>\n"+
            "</experimentalPreparation>";
    private String expPrepAliases = "<experimentalPreparation>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "    <alias type=\"synonym\">unspecified</alias>\n"+
            "    <alias type=\"test\">test name</alias>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" id=\"MI:0339\" refType=\"identity\"/>\n"+
            "  </xref>\n"+
            "</experimentalPreparation>";
    private String expPrepMod = "<experimentalPreparation>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mod\" id=\"MI:0339\" refType=\"identity\"/>\n"+
            "  </xref>\n"+
            "</experimentalPreparation>";
    private String expPrepPar = "<experimentalPreparation>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-par\" id=\"MI:0339\" refType=\"identity\"/>\n"+
            "  </xref>\n"+
            "</experimentalPreparation>";
    private String expPrepFirstIdentifier = "<experimentalPreparation>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test\" id=\"MI:0339\" refType=\"identity\"/>\n"+
            "    <secondaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "</experimentalPreparation>";
    private String expPrepFirstXref = "<experimentalPreparation>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "</experimentalPreparation>";

    @Test
    public void test_write_cv_no_fullName() throws XMLStreamException, IOException {
        CvTerm expPrep = new DefaultCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);

        Xml25ExperimentalPreparationWriter writer = new Xml25ExperimentalPreparationWriter(createStreamWriter());
        writer.write(expPrep);
        streamWriter.flush();

        Assert.assertEquals(this.expPrep, output.toString());
    }

    @Test
    public void test_write_cv_fullName() throws XMLStreamException, IOException {
        CvTerm expPrep = new DefaultCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
        expPrep.setFullName("undetermined position");

        Xml25ExperimentalPreparationWriter writer = new Xml25ExperimentalPreparationWriter(createStreamWriter());
        writer.write(expPrep);
        streamWriter.flush();

        Assert.assertEquals(this.expPrepFullName, output.toString());
    }

    @Test
    public void test_write_cv_aliases() throws XMLStreamException, IOException {
        CvTerm expPrep = new DefaultCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
        expPrep.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("synonym"), "unspecified"));
        expPrep.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("test"), "test name"));

        Xml25ExperimentalPreparationWriter writer = new Xml25ExperimentalPreparationWriter(createStreamWriter());
        writer.write(expPrep);
        streamWriter.flush();

        Assert.assertEquals(this.expPrepAliases, output.toString());
    }

    @Test
    public void test_write_cv_mod() throws XMLStreamException, IOException {
        CvTerm expPrep = new DefaultCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
        expPrep.setMODIdentifier(expPrep.getMIIdentifier());
        expPrep.setMIIdentifier(null);

        Xml25ExperimentalPreparationWriter writer = new Xml25ExperimentalPreparationWriter(createStreamWriter());
        writer.write(expPrep);
        streamWriter.flush();

        Assert.assertEquals(this.expPrepMod, output.toString());
    }

    @Test
    public void test_write_cv_par() throws XMLStreamException, IOException {
        CvTerm expPrep = new DefaultCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
        expPrep.setPARIdentifier(expPrep.getMIIdentifier());
        expPrep.setMIIdentifier(null);
        Xml25ExperimentalPreparationWriter writer = new Xml25ExperimentalPreparationWriter(createStreamWriter());
        writer.write(expPrep);
        streamWriter.flush();

        Assert.assertEquals(this.expPrepPar, output.toString());
    }

    @Test
    public void test_write_cv_first_identifier() throws XMLStreamException, IOException {
        CvTerm expPrep = new DefaultCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
        expPrep.getIdentifiers().iterator().next().getDatabase().setShortName("test");
        expPrep.getIdentifiers().iterator().next().getDatabase().setMIIdentifier(null);
        expPrep.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        expPrep.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25ExperimentalPreparationWriter writer = new Xml25ExperimentalPreparationWriter(createStreamWriter());
        writer.write(expPrep);
        streamWriter.flush();

        Assert.assertEquals(this.expPrepFirstIdentifier, output.toString());
    }

    @Test
    public void test_write_cv_first_xref() throws XMLStreamException, IOException {
        CvTerm expPrep = new DefaultCvTerm("undetermined");
        expPrep.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        expPrep.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25ExperimentalPreparationWriter writer = new Xml25ExperimentalPreparationWriter(createStreamWriter());
        writer.write(expPrep);
        streamWriter.flush();

        Assert.assertEquals(this.expPrepFirstXref, output.toString());
    }
}