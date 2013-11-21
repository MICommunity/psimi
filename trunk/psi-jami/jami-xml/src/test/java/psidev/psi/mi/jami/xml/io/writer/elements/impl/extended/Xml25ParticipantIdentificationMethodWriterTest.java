package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.xml.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.ExperimentalCvTerm;
import psidev.psi.mi.jami.xml.io.writer.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for Xml25ParticipantIdentificationMethodWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/13</pre>
 */

public class Xml25ParticipantIdentificationMethodWriterTest extends AbstractXml25WriterTest {

    private String detection = "<participantIdentificationMethod>\n" +
            "  <names>\n" +
            "    <shortLabel>inferred</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0362\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</participantIdentificationMethod>";
    private String detectionFullName = "<participantIdentificationMethod>\n" +
            "  <names>\n" +
            "    <shortLabel>inferred</shortLabel>\n"+
            "    <fullName>inference</fullName>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0362\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</participantIdentificationMethod>";
    private String detectionAliases = "<participantIdentificationMethod>\n" +
            "  <names>\n" +
            "    <shortLabel>inferred</shortLabel>\n"+
            "    <alias type=\"synonym\">unspecified</alias>\n"+
            "    <alias type=\"test\">test name</alias>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0362\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</participantIdentificationMethod>";
    private String detectionMod = "<participantIdentificationMethod>\n" +
            "  <names>\n" +
            "    <shortLabel>inferred</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mod\" dbAc=\"MI:0897\" id=\"MI:0362\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</participantIdentificationMethod>";
    private String detectionPar = "<participantIdentificationMethod>\n" +
            "  <names>\n" +
            "    <shortLabel>inferred</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-par\" id=\"MI:0362\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "</participantIdentificationMethod>";
    private String detectionFirstIdentifier = "<participantIdentificationMethod>\n" +
            "  <names>\n" +
            "    <shortLabel>inferred</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test\" id=\"MI:0362\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    <secondaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "</participantIdentificationMethod>";
    private String detectionFirstXref = "<participantIdentificationMethod>\n" +
            "  <names>\n" +
            "    <shortLabel>inferred</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test2\" id=\"xxxxx2\"/>\n"+
            "    <secondaryRef db=\"test3\" id=\"xxxxx3\"/>\n"+
            "  </xref>\n"+
            "</participantIdentificationMethod>";
    private String expPrepExperiments = "<participantIdentificationMethod>\n" +
            "  <names>\n" +
            "    <shortLabel>inferred</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0362\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "  <experimentRefList>\n" +
            "    <experimentRef>1</experimentRef>\n"+
            "    <experimentRef>2</experimentRef>\n"+
            "  </experimentRefList>\n"+
            "</participantIdentificationMethod>";
    private String expPrepExperiments2 = "<participantIdentificationMethod>\n" +
            "  <names>\n" +
            "    <shortLabel>inferred</shortLabel>\n"+
            "  </names>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0362\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "  </xref>\n"+
            "  <experimentRefList>\n" +
            "    <experimentRef>2</experimentRef>\n"+
            "    <experimentRef>3</experimentRef>\n"+
            "  </experimentRefList>\n"+
            "</participantIdentificationMethod>";
    private PsiXml25ObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_cv_no_fullName() throws XMLStreamException, IOException {
        CvTerm method = new ExperimentalCvTerm("inferred", "MI:0362");

        Xml25ParticipantIdentificationMethodWriter writer = new Xml25ParticipantIdentificationMethodWriter(createStreamWriter(), this.elementCache);
        writer.write(method);
        streamWriter.flush();

        Assert.assertEquals(this.detection, output.toString());
    }

    @Test
    public void test_write_cv_fullName() throws XMLStreamException, IOException {
        CvTerm method = new ExperimentalCvTerm("inferred", "MI:0362");
        method.setFullName("inference");

        Xml25ParticipantIdentificationMethodWriter writer = new Xml25ParticipantIdentificationMethodWriter(createStreamWriter(), this.elementCache);
        writer.write(method);
        streamWriter.flush();

        Assert.assertEquals(this.detectionFullName, output.toString());
    }

    @Test
    public void test_write_cv_aliases() throws XMLStreamException, IOException {
        CvTerm method = new ExperimentalCvTerm("inferred", "MI:0362");
        method.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("synonym"), "unspecified"));
        method.getSynonyms().add(new DefaultAlias(new DefaultCvTerm("test"), "test name"));

        Xml25ParticipantIdentificationMethodWriter writer = new Xml25ParticipantIdentificationMethodWriter(createStreamWriter(), this.elementCache);
        writer.write(method);
        streamWriter.flush();

        Assert.assertEquals(this.detectionAliases, output.toString());
    }

    @Test
    public void test_write_cv_mod() throws XMLStreamException, IOException {
        CvTerm method = new ExperimentalCvTerm("inferred", "MI:0362");
        method.setMODIdentifier(method.getMIIdentifier());
        method.setMIIdentifier(null);

        Xml25ParticipantIdentificationMethodWriter writer = new Xml25ParticipantIdentificationMethodWriter(createStreamWriter(), this.elementCache);
        writer.write(method);
        streamWriter.flush();

        Assert.assertEquals(this.detectionMod, output.toString());
    }

    @Test
    public void test_write_cv_par() throws XMLStreamException, IOException {
        CvTerm method = new ExperimentalCvTerm("inferred", "MI:0362");
        method.setPARIdentifier(method.getMIIdentifier());
        method.setMIIdentifier(null);
        Xml25ParticipantIdentificationMethodWriter writer = new Xml25ParticipantIdentificationMethodWriter(createStreamWriter(), this.elementCache);
        writer.write(method);
        streamWriter.flush();

        Assert.assertEquals(this.detectionPar, output.toString());
    }

    @Test
    public void test_write_cv_first_identifier() throws XMLStreamException, IOException {
        CvTerm method = new ExperimentalCvTerm("inferred", "MI:0362");
        method.getIdentifiers().iterator().next().getDatabase().setShortName("test");
        method.getIdentifiers().iterator().next().getDatabase().setMIIdentifier(null);
        method.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        method.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25ParticipantIdentificationMethodWriter writer = new Xml25ParticipantIdentificationMethodWriter(createStreamWriter(), this.elementCache);
        writer.write(method);
        streamWriter.flush();

        Assert.assertEquals(this.detectionFirstIdentifier, output.toString());
    }

    @Test
    public void test_write_cv_first_xref() throws XMLStreamException, IOException {
        CvTerm method = new ExperimentalCvTerm("inferred");
        method.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxxx2"));
        method.getXrefs().add(new DefaultXref(new DefaultCvTerm("test3"), "xxxxx3"));

        Xml25ParticipantIdentificationMethodWriter writer = new Xml25ParticipantIdentificationMethodWriter(createStreamWriter(), this.elementCache);
        writer.write(method);
        streamWriter.flush();

        Assert.assertEquals(this.detectionFirstXref, output.toString());
    }

    @Test
    public void test_write_cv_exp_references() throws XMLStreamException, IOException {
        ExperimentalCvTerm method = new ExperimentalCvTerm("inferred", "MI:0362");
        method.getExperiments().add(new DefaultExperiment(new DefaultPublication("P12345")));
        method.getExperiments().add(new DefaultExperiment(new DefaultPublication("P123456")));
        this.elementCache.clear();

        Xml25ParticipantIdentificationMethodWriter writer = new Xml25ParticipantIdentificationMethodWriter(createStreamWriter(), this.elementCache);
        writer.write(method);
        streamWriter.flush();

        Assert.assertEquals(this.expPrepExperiments, output.toString());
    }

    @Test
    public void test_write_cv_exp_references_alreadyRegistered() throws XMLStreamException, IOException {
        ExperimentalCvTerm method = new ExperimentalCvTerm("inferred", "MI:0362");
        Experiment exp1 = new DefaultExperiment(new DefaultPublication("P12345"));
        Experiment exp2 = new DefaultExperiment(new DefaultPublication("P123456"));
        method.getExperiments().add(exp1);
        method.getExperiments().add(exp2);
        this.elementCache.clear();
        this.elementCache.extractIdForExperiment(new DefaultExperiment(new DefaultPublication("P1234")));
        this.elementCache.extractIdForExperiment(exp1);
        this.elementCache.extractIdForExperiment(exp2);

        Xml25ParticipantIdentificationMethodWriter writer = new Xml25ParticipantIdentificationMethodWriter(createStreamWriter(), this.elementCache);
        writer.write(method);
        streamWriter.flush();

        Assert.assertEquals(this.expPrepExperiments2, output.toString());
    }
}
