package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.xml.cache.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.extension.ExperimentalCvTerm;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXml25WriterTest;

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
            "    <primaryRef db=\"psi-mod\" dbAc=\"MI:0897\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</experimentalRole>";
    private String expRolePar = "<experimentalRole>\n" +
            "  <names>\n" +
            "    <shortLabel>unspecified role</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-par\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
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
    private String expRoleExperiments = "<experimentalRole>\n" +
            "  <names>\n" +
            "    <shortLabel>unspecified role</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "  <experimentRefList>\n" +
            "    <experimentRef>1</experimentRef>\n"+
            "    <experimentRef>2</experimentRef>\n"+
            "  </experimentRefList>\n"+
            "</experimentalRole>";
    private String expRoleExperiments2 = "<experimentalRole>\n" +
            "  <names>\n" +
            "    <shortLabel>unspecified role</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "  <experimentRefList>\n" +
            "    <experimentRef>2</experimentRef>\n"+
            "    <experimentRef>3</experimentRef>\n"+
            "  </experimentRefList>\n"+
            "</experimentalRole>";
    private PsiXml25ObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_cv_no_fullName() throws XMLStreamException, IOException {
        CvTerm expRole = new ExperimentalCvTerm(Participant.UNSPECIFIED_ROLE, Participant.UNSPECIFIED_ROLE_MI);

        Xml25ExperimentalRoleWriter writer = new Xml25ExperimentalRoleWriter(createStreamWriter(), this.elementCache);
        writer.write(expRole);
        streamWriter.flush();

        Assert.assertEquals(this.expRole, output.toString());
    }

    @Test
    public void test_write_cv_fullName() throws XMLStreamException, IOException {
        CvTerm expRole = new ExperimentalCvTerm(Participant.UNSPECIFIED_ROLE, Participant.UNSPECIFIED_ROLE_MI);
        expRole.setFullName("unspecified role");

        Xml25ExperimentalRoleWriter writer = new Xml25ExperimentalRoleWriter(createStreamWriter(), this.elementCache);
        writer.write(expRole);
        streamWriter.flush();

        Assert.assertEquals(this.expRoleFullName, output.toString());
    }

    @Test
    public void test_write_cv_aliases() throws XMLStreamException, IOException {
        CvTerm expRole = new ExperimentalCvTerm(Participant.UNSPECIFIED_ROLE, Participant.UNSPECIFIED_ROLE_MI);
        expRole.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("synonym"), "unspecified"));
        expRole.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("test"), "test name"));

        Xml25ExperimentalRoleWriter writer = new Xml25ExperimentalRoleWriter(createStreamWriter(), this.elementCache);
        writer.write(expRole);
        streamWriter.flush();

        Assert.assertEquals(this.expRoleAliases, output.toString());
    }

    @Test
    public void test_write_cv_mod() throws XMLStreamException, IOException {
        CvTerm expRole = new ExperimentalCvTerm(Participant.UNSPECIFIED_ROLE, Participant.UNSPECIFIED_ROLE_MI);
        expRole.setMODIdentifier(expRole.getMIIdentifier());
        expRole.setMIIdentifier(null);

        Xml25ExperimentalRoleWriter writer = new Xml25ExperimentalRoleWriter(createStreamWriter(), this.elementCache);
        writer.write(expRole);
        streamWriter.flush();

        Assert.assertEquals(this.expRoleMod, output.toString());
    }

    @Test
    public void test_write_cv_par() throws XMLStreamException, IOException {
        CvTerm expRole = new ExperimentalCvTerm(Participant.UNSPECIFIED_ROLE, Participant.UNSPECIFIED_ROLE_MI);
        expRole.setPARIdentifier(expRole.getMIIdentifier());
        expRole.setMIIdentifier(null);
        Xml25ExperimentalRoleWriter writer = new Xml25ExperimentalRoleWriter(createStreamWriter(), this.elementCache);
        writer.write(expRole);
        streamWriter.flush();

        Assert.assertEquals(this.expRolePar, output.toString());
    }

    @Test
    public void test_write_cv_first_identifier() throws XMLStreamException, IOException {
        CvTerm expRole = new ExperimentalCvTerm(Participant.UNSPECIFIED_ROLE, Participant.UNSPECIFIED_ROLE_MI);
        expRole.getIdentifiers().iterator().next().getDatabase().setShortName("test");
        expRole.getIdentifiers().iterator().next().getDatabase().setMIIdentifier(null);
        expRole.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        expRole.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25ExperimentalRoleWriter writer = new Xml25ExperimentalRoleWriter(createStreamWriter(), this.elementCache);
        writer.write(expRole);
        streamWriter.flush();

        Assert.assertEquals(this.expRoleFirstIdentifier, output.toString());
    }

    @Test
    public void test_write_cv_first_xref() throws XMLStreamException, IOException {
        CvTerm expRole = new ExperimentalCvTerm(Participant.UNSPECIFIED_ROLE);
        expRole.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        expRole.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25ExperimentalRoleWriter writer = new Xml25ExperimentalRoleWriter(createStreamWriter(), this.elementCache);
        writer.write(expRole);
        streamWriter.flush();

        Assert.assertEquals(this.expRoleFirstXref, output.toString());
    }

    @Test
    public void test_write_cv_exp_references() throws XMLStreamException, IOException {
        ExperimentalCvTerm expRole = new ExperimentalCvTerm(Participant.UNSPECIFIED_ROLE, Participant.UNSPECIFIED_ROLE_MI);
        expRole.getExperiments().add(new DefaultExperiment(new DefaultPublication("P12345")));
        expRole.getExperiments().add(new DefaultExperiment(new DefaultPublication("P123456")));
        this.elementCache.clear();

        Xml25ExperimentalRoleWriter writer = new Xml25ExperimentalRoleWriter(createStreamWriter(), this.elementCache);
        writer.write(expRole);
        streamWriter.flush();

        Assert.assertEquals(this.expRoleExperiments, output.toString());
    }

    @Test
    public void test_write_cv_exp_references_alreadyRegistered() throws XMLStreamException, IOException {
        ExperimentalCvTerm expRole = new ExperimentalCvTerm(Participant.UNSPECIFIED_ROLE, Participant.UNSPECIFIED_ROLE_MI);
        Experiment exp1 = new DefaultExperiment(new DefaultPublication("P12345"));
        Experiment exp2 = new DefaultExperiment(new DefaultPublication("P123456"));
        expRole.getExperiments().add(exp1);
        expRole.getExperiments().add(exp2);
        this.elementCache.clear();
        this.elementCache.extractIdForExperiment(new DefaultExperiment(new DefaultPublication("P1234")));
        this.elementCache.extractIdForExperiment(exp1);
        this.elementCache.extractIdForExperiment(exp2);

        Xml25ExperimentalRoleWriter writer = new Xml25ExperimentalRoleWriter(createStreamWriter(), this.elementCache);
        writer.write(expRole);
        streamWriter.flush();

        Assert.assertEquals(this.expRoleExperiments2, output.toString());
    }
}