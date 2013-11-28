package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.extension.ExperimentalCvTerm;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for Xml25ExperimentalPreparationWriter
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
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0339\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</experimentalPreparation>";
    private String expPrepFullName = "<experimentalPreparation>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "    <fullName>undetermined position</fullName>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0339\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</experimentalPreparation>";
    private String expPrepAliases = "<experimentalPreparation>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "    <alias type=\"synonym\">unspecified</alias>\n"+
            "    <alias type=\"test\">test name</alias>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0339\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</experimentalPreparation>";
    private String expPrepMod = "<experimentalPreparation>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mod\" dbAc=\"MI:0897\" id=\"MI:0339\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</experimentalPreparation>";
    private String expPrepPar = "<experimentalPreparation>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-par\" id=\"MI:0339\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</experimentalPreparation>";
    private String expPrepFirstIdentifier = "<experimentalPreparation>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test\" id=\"MI:0339\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
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
    private String expPrepExperiments = "<experimentalPreparation>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0339\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "  <experimentRefList>\n" +
            "    <experimentRef>1</experimentRef>\n"+
            "    <experimentRef>2</experimentRef>\n"+
            "  </experimentRefList>\n"+
            "</experimentalPreparation>";
    private String expPrepExperiments2 = "<experimentalPreparation>\n" +
            "  <names>\n" +
            "    <shortLabel>undetermined</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0339\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "  <experimentRefList>\n" +
            "    <experimentRef>2</experimentRef>\n"+
            "    <experimentRef>3</experimentRef>\n"+
            "  </experimentRefList>\n"+
            "</experimentalPreparation>";
    private PsiXml25ObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_cv_no_fullName() throws XMLStreamException, IOException {
        CvTerm expPrep = new ExperimentalCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
        this.elementCache.clear();

        Xml25ExperimentalPreparationWriter writer = new Xml25ExperimentalPreparationWriter(createStreamWriter(), this.elementCache);
        writer.write(expPrep);
        streamWriter.flush();

        Assert.assertEquals(this.expPrep, output.toString());
    }

    @Test
    public void test_write_cv_fullName() throws XMLStreamException, IOException {
        CvTerm expPrep = new ExperimentalCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
        expPrep.setFullName("undetermined position");
        this.elementCache.clear();

        Xml25ExperimentalPreparationWriter writer = new Xml25ExperimentalPreparationWriter(createStreamWriter(), this.elementCache);
        writer.write(expPrep);
        streamWriter.flush();

        Assert.assertEquals(this.expPrepFullName, output.toString());
    }

    @Test
    public void test_write_cv_aliases() throws XMLStreamException, IOException {
        CvTerm expPrep = new ExperimentalCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
        expPrep.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("synonym"), "unspecified"));
        expPrep.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("test"), "test name"));
        this.elementCache.clear();

        Xml25ExperimentalPreparationWriter writer = new Xml25ExperimentalPreparationWriter(createStreamWriter(), this.elementCache);
        writer.write(expPrep);
        streamWriter.flush();

        Assert.assertEquals(this.expPrepAliases, output.toString());
    }

    @Test
    public void test_write_cv_mod() throws XMLStreamException, IOException {
        CvTerm expPrep = new ExperimentalCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
        expPrep.setMODIdentifier(expPrep.getMIIdentifier());
        expPrep.setMIIdentifier(null);
        this.elementCache.clear();

        Xml25ExperimentalPreparationWriter writer = new Xml25ExperimentalPreparationWriter(createStreamWriter(), this.elementCache);
        writer.write(expPrep);
        streamWriter.flush();

        Assert.assertEquals(this.expPrepMod, output.toString());
    }

    @Test
    public void test_write_cv_par() throws XMLStreamException, IOException {
        CvTerm expPrep = new ExperimentalCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
        expPrep.setPARIdentifier(expPrep.getMIIdentifier());
        expPrep.setMIIdentifier(null);
        this.elementCache.clear();
        Xml25ExperimentalPreparationWriter writer = new Xml25ExperimentalPreparationWriter(createStreamWriter(),this.elementCache);
        writer.write(expPrep);
        streamWriter.flush();

        Assert.assertEquals(this.expPrepPar, output.toString());
    }

    @Test
    public void test_write_cv_first_identifier() throws XMLStreamException, IOException {
        CvTerm expPrep = new ExperimentalCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
        expPrep.getIdentifiers().iterator().next().getDatabase().setShortName("test");
        expPrep.getIdentifiers().iterator().next().getDatabase().setMIIdentifier(null);
        expPrep.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        expPrep.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));
        this.elementCache.clear();

        Xml25ExperimentalPreparationWriter writer = new Xml25ExperimentalPreparationWriter(createStreamWriter(),this.elementCache);
        writer.write(expPrep);
        streamWriter.flush();

        Assert.assertEquals(this.expPrepFirstIdentifier, output.toString());
    }

    @Test
    public void test_write_cv_first_xref() throws XMLStreamException, IOException {
        CvTerm expPrep = new ExperimentalCvTerm(Position.UNDETERMINED);
        expPrep.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        expPrep.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));
        this.elementCache.clear();

        Xml25ExperimentalPreparationWriter writer = new Xml25ExperimentalPreparationWriter(createStreamWriter(),this.elementCache);
        writer.write(expPrep);
        streamWriter.flush();

        Assert.assertEquals(this.expPrepFirstXref, output.toString());
    }

    @Test
    public void test_write_cv_exp_references() throws XMLStreamException, IOException {
        ExperimentalCvTerm expPrep = new ExperimentalCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
        expPrep.getExperiments().add(new DefaultExperiment(new DefaultPublication("P12345")));
        expPrep.getExperiments().add(new DefaultExperiment(new DefaultPublication("P123456")));
        this.elementCache.clear();

        Xml25ExperimentalPreparationWriter writer = new Xml25ExperimentalPreparationWriter(createStreamWriter(), this.elementCache);
        writer.write(expPrep);
        streamWriter.flush();

        Assert.assertEquals(this.expPrepExperiments, output.toString());
    }

    @Test
    public void test_write_cv_exp_references_alreadyRegistered() throws XMLStreamException, IOException {
        ExperimentalCvTerm expPrep = new ExperimentalCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI);
        Experiment exp1 = new DefaultExperiment(new DefaultPublication("P12345"));
        Experiment exp2 = new DefaultExperiment(new DefaultPublication("P123456"));
        expPrep.getExperiments().add(exp1);
        expPrep.getExperiments().add(exp2);
        this.elementCache.clear();
        this.elementCache.extractIdForExperiment(new DefaultExperiment(new DefaultPublication("P1234")));
        this.elementCache.extractIdForExperiment(exp1);
        this.elementCache.extractIdForExperiment(exp2);

        Xml25ExperimentalPreparationWriter writer = new Xml25ExperimentalPreparationWriter(createStreamWriter(), this.elementCache);
        writer.write(expPrep);
        streamWriter.flush();

        Assert.assertEquals(this.expPrepExperiments2, output.toString());
    }
}