package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.xml.cache.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Experiment;
import psidev.psi.mi.jami.xml.extension.XmlExperiment;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXml25WriterTest;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.text.ParseException;

/**
 * Unit tester for Xml25ExperimentWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/13</pre>
 */

public class Xml25ExperimentWriterTest extends AbstractXml25WriterTest {
    private String experiment = "<experimentDescription id=\"1\">\n" +
            "  <bibref>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </bibref>\n"+
            "  <interactionDetectionMethod>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified method</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </interactionDetectionMethod>\n"+
            "</experimentDescription>";
    private String experiment_default_bibref ="<experimentDescription id=\"1\">\n" +
            "  <bibref>\n" +
            "    <attributeList>\n" +
            "      <attribute name=\"publication title\" nameAc=\"MI:1091\">Mock publication for experiments that do not have a publication reference</attribute>\n"+
            "    </attributeList>\n"+
            "  </bibref>\n"+
            "  <interactionDetectionMethod>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified method</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </interactionDetectionMethod>\n"+
            "</experimentDescription>";
    private String experiment_xref = "<experimentDescription id=\"1\">\n" +
            "  <bibref>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </bibref>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test\" id=\"xxxxxx\"/>\n"+
            "    <secondaryRef db=\"test\" id=\"12345\"/>\n" +
            "    <secondaryRef db=\"test2\" id=\"12346\"/>\n" +
            "  </xref>\n"+
            "  <interactionDetectionMethod>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified method</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n"+
            "  </interactionDetectionMethod>\n"+
            "</experimentDescription>";
    private String experiment_imex ="<experimentDescription id=\"1\">\n" +
            "  <bibref>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "      <secondaryRef db=\"imex\" dbAc=\"MI:0670\" id=\"IM-1\" refType=\"imex-primary\" refTypeAc=\"MI:0662\"/>\n"+
            "    </xref>\n"+
            "  </bibref>\n"+
            "  <xref>\n" +
            "    <primaryRef db=\"test\" id=\"xxxxxx\"/>\n"+
            "    <secondaryRef db=\"test\" id=\"12345\"/>\n" +
            "    <secondaryRef db=\"test2\" id=\"12346\"/>\n" +
            "    <secondaryRef db=\"imex\" dbAc=\"MI:0670\" id=\"IM-1\" refType=\"imex-primary\" refTypeAc=\"MI:0662\"/>\n" +
            "  </xref>\n"+
            "  <interactionDetectionMethod>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified method</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </interactionDetectionMethod>\n"+
            "  <attributeList>\n" +
            "    <attribute name=\"curation depth\" nameAc=\"MI:0955\">imex curation</attribute>\n" +
            "    <attribute name=\"imex curation\" nameAc=\"MI:0959\"/>\n" +
            "  </attributeList>\n"+
            "</experimentDescription>";
    private String experiment_host ="<experimentDescription id=\"1\">\n" +
            "  <bibref>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </bibref>\n"+
            "  <hostOrganismList>\n" +
            "    <hostOrganism ncbiTaxId=\"9606\">\n" +
            "      <names>\n" +
            "        <shortLabel>human</shortLabel>\n"+
            "      </names>\n"+
            "    </hostOrganism>\n"+
            "    <hostOrganism ncbiTaxId=\"-1\">\n" +
            "      <names>\n" +
            "        <shortLabel>in vitro</shortLabel>\n"+
            "      </names>\n"+
            "    </hostOrganism>\n"+
            "  </hostOrganismList>\n"+
            "  <interactionDetectionMethod>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified method</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </interactionDetectionMethod>\n"+
            "</experimentDescription>";
    private String experiment_confidence ="<experimentDescription id=\"1\">\n" +
            "  <bibref>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </bibref>\n"+
            "  <interactionDetectionMethod>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified method</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </interactionDetectionMethod>\n"+
            "  <confidenceList>\n" +
            "    <confidence>\n" +
            "      <unit>\n" +
            "        <names>\n" +
            "          <shortLabel>intact-miscore</shortLabel>\n"+
            "        </names>\n"+
            "      </unit>\n" +
            "      <value>0.8</value>\n" +
            "    </confidence>\n"+
            "  </confidenceList>\n" +
            "</experimentDescription>";
    private String experiment_attributes ="<experimentDescription id=\"1\">\n" +
            "  <bibref>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </bibref>\n"+
            "  <interactionDetectionMethod>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified method</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </interactionDetectionMethod>\n"+
            "  <attributeList>\n" +
            "    <attribute name=\"test3\"/>\n"+
            "    <attribute name=\"publication title\" nameAc=\"MI:1091\">test title</attribute>\n"+
            "    <attribute name=\"journal\" nameAc=\"MI:0885\">test journal</attribute>\n"+
            "    <attribute name=\"publication year\" nameAc=\"MI:0886\">2006</attribute>\n"+
            "    <attribute name=\"curation depth\" nameAc=\"MI:0955\">imex curation</attribute>\n"+
            "    <attribute name=\"imex curation\" nameAc=\"MI:0959\"/>\n"+
            "    <attribute name=\"author-list\" nameAc=\"MI:0636\">author1, author2, author3</attribute>\n"+
            "  </attributeList>\n"+
            "</experimentDescription>";
    private String experiment_pub_attributes ="<experimentDescription id=\"1\">\n" +
            "  <names>\n" +
            "    <fullName>test title</fullName>\n" +
            "  </names>\n"+
            "  <bibref>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </bibref>\n"+
            "  <interactionDetectionMethod>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified method</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </interactionDetectionMethod>\n"+
            "  <attributeList>\n" +
            "    <attribute name=\"test3\"/>\n"+
            "    <attribute name=\"publication title\" nameAc=\"MI:1091\">test title</attribute>\n"+
            "    <attribute name=\"journal\" nameAc=\"MI:0885\">test journal</attribute>\n"+
            "    <attribute name=\"publication year\" nameAc=\"MI:0886\">2006</attribute>\n"+
            "    <attribute name=\"curation depth\" nameAc=\"MI:0955\">imex curation</attribute>\n"+
            "    <attribute name=\"imex curation\" nameAc=\"MI:0959\"/>\n"+
            "    <attribute name=\"author-list\" nameAc=\"MI:0636\">author1, author2, author3</attribute>\n"+
            "  </attributeList>\n"+
            "</experimentDescription>";
    private String experiment_registered ="<experimentDescription id=\"2\">\n" +
            "  <bibref>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </bibref>\n"+
            "  <interactionDetectionMethod>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified method</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </interactionDetectionMethod>\n"+
            "</experimentDescription>";
    private String experiment_names = "<experimentDescription id=\"1\">\n" +
            "  <names>\n" +
            "    <shortLabel>author-2013-1</shortLabel>\n"+
            "    <fullName>experiment description</fullName>\n"+
            "    <alias type=\"synonym\">experiment synonym</alias>\n"+
            "    <alias>test</alias>\n"+
            "  </names>\n"+
            "  <bibref>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </bibref>\n"+
            "  <interactionDetectionMethod>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified method</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </interactionDetectionMethod>\n"+
            "</experimentDescription>";
    private String experiment_participant_method = "<experimentDescription id=\"1\">\n" +
            "  <bibref>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </bibref>\n"+
            "  <interactionDetectionMethod>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified method</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </interactionDetectionMethod>\n"+
            "  <participantIdentificationMethod>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified method</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </participantIdentificationMethod>\n"+
            "</experimentDescription>";
    private String experiment_feature_method = "<experimentDescription id=\"1\">\n" +
            "  <bibref>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </bibref>\n"+
            "  <interactionDetectionMethod>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified method</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </interactionDetectionMethod>\n"+
            "  <featureDetectionMethod>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified method</shortLabel>\n"+
            "    </names>\n"+
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </featureDetectionMethod>\n"+
            "</experimentDescription>";

    private PsiXml25ObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_experiment() throws XMLStreamException, IOException {
        Experiment exp = new XmlExperiment(new DefaultPublication("xxxxxx"));

        elementCache.clear();

        Xml25ExperimentWriter writer = new Xml25ExperimentWriter(createStreamWriter(), elementCache);
        writer.write(exp);
        streamWriter.flush();

        Assert.assertEquals(experiment, output.toString());
    }

    @Test
    public void test_write_experiment_defaultPublication() throws XMLStreamException, IOException {
        Experiment exp = new XmlExperiment(null);

        elementCache.clear();

        Xml25ExperimentWriter writer = new Xml25ExperimentWriter(createStreamWriter(), elementCache);
        writer.write(exp);
        streamWriter.flush();

        Assert.assertEquals(experiment_default_bibref, output.toString());
    }

    @Test
    public void test_write_experiment_xref() throws XMLStreamException, IOException {
        Experiment exp = new XmlExperiment(new DefaultPublication("xxxxxx"));
        exp.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxxxx"));
        exp.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"),"12345"));
        exp.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"),"12346"));
        elementCache.clear();

        Xml25ExperimentWriter writer = new Xml25ExperimentWriter(createStreamWriter(), elementCache);
        writer.write(exp);
        streamWriter.flush();

        Assert.assertEquals(experiment_xref, output.toString());
    }

    @Test
    public void test_write_experiment_imex() throws XMLStreamException, IOException {
        Experiment exp = new XmlExperiment(new DefaultPublication("xxxxxx"));
        exp.getPublication().assignImexId("IM-1");
        exp.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxxxx"));
        exp.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"),"12345"));
        exp.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"),"12346"));
        elementCache.clear();

        Xml25ExperimentWriter writer = new Xml25ExperimentWriter(createStreamWriter(), elementCache);
        writer.write(exp);
        streamWriter.flush();

        Assert.assertEquals(experiment_imex, output.toString());
    }

    @Test
    public void test_write_experiment_host() throws XMLStreamException, IOException {
        ExtendedPsi25Experiment exp = new XmlExperiment(new DefaultPublication("xxxxxx"));
        exp.setHostOrganism(new DefaultOrganism(9606, "human"));
        exp.getHostOrganisms().add(new DefaultOrganism(-1, "in vitro"));
        elementCache.clear();

        Xml25ExperimentWriter writer = new Xml25ExperimentWriter(createStreamWriter(), elementCache);
        writer.write(exp);
        streamWriter.flush();

        Assert.assertEquals(experiment_host, output.toString());
    }

    @Test
    public void test_write_experiment_confidence() throws XMLStreamException, IOException {
        Experiment exp = new XmlExperiment(new DefaultPublication("xxxxxx"));
        exp.getConfidences().add(new DefaultConfidence(new DefaultCvTerm("intact-miscore"),"0.8"));
        elementCache.clear();

        Xml25ExperimentWriter writer = new Xml25ExperimentWriter(createStreamWriter(), elementCache);
        writer.write(exp);
        streamWriter.flush();

        Assert.assertEquals(experiment_confidence, output.toString());
    }

    @Test
    public void test_write_experiment_attributes() throws XMLStreamException, IOException, ParseException {
        Experiment exp = new XmlExperiment(new DefaultPublication("xxxxxx"));
        Publication pub = exp.getPublication();
        pub.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));
        exp.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));
        exp.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm(Annotation.PUBLICATION_TITLE, Annotation.PUBLICATION_TITLE_MI), "test title"));
        exp.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm(Annotation.PUBLICATION_JOURNAL, Annotation.PUBLICATION_JOURNAL_MI), "test journal"));
        exp.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm(Annotation.PUBLICATION_YEAR, Annotation.PUBLICATION_YEAR_MI), "2006"));
        exp.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm(Annotation.CURATION_DEPTH, Annotation.CURATION_DEPTH_MI), "imex curation"));
        exp.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm(Annotation.IMEX_CURATION, Annotation.IMEX_CURATION_MI)));
        exp.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm(Annotation.AUTHOR, Annotation.AUTHOR_MI), "author1, author2, author3"));

        elementCache.clear();

        Xml25ExperimentWriter writer = new Xml25ExperimentWriter(createStreamWriter(), elementCache);
        writer.write(exp);
        streamWriter.flush();

        Assert.assertEquals(experiment_attributes, output.toString());
    }

    @Test
    public void test_write_experiment_pub_attributes() throws XMLStreamException, IOException, ParseException {
        Experiment exp = new XmlExperiment(new DefaultPublication("xxxxxx"));
        Publication pub = exp.getPublication();
        pub.setTitle("test title");
        pub.setJournal("test journal");
        pub.setPublicationDate(PsiXml25Utils.YEAR_FORMAT.parse("2006"));
        pub.setCurationDepth(CurationDepth.IMEx);
        pub.getAuthors().add("author1");
        pub.getAuthors().add("author2");
        pub.getAuthors().add("author3");
        pub.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));
        exp.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));
        elementCache.clear();

        Xml25ExperimentWriter writer = new Xml25ExperimentWriter(createStreamWriter(), elementCache);
        writer.write(exp);
        streamWriter.flush();

        Assert.assertEquals(experiment_pub_attributes, output.toString());
    }

    @Test
    public void test_write_experiment_already_registered() throws XMLStreamException, IOException {
        Experiment exp = new XmlExperiment(new DefaultPublication("xxxxxx"));
        this.elementCache.clear();
        this.elementCache.extractIdForExperiment(new DefaultExperiment(new DefaultPublication("P1234")));
        this.elementCache.extractIdForExperiment(exp);

        Xml25ExperimentWriter writer = new Xml25ExperimentWriter(createStreamWriter(), elementCache);
        writer.write(exp);
        streamWriter.flush();

        Assert.assertEquals(experiment_registered, output.toString());
    }

    @Test
    public void test_write_experiment_names() throws XMLStreamException, IOException {
        NamedExperiment exp = new XmlExperiment(new DefaultPublication("xxxxxx"));
        exp.setShortName("author-2013-1");
        exp.setFullName("experiment description");
        exp.getAliases().add(new DefaultAlias(new DefaultCvTerm("synonym"), "experiment synonym"));
        exp.getAliases().add(new DefaultAlias("test"));

        this.elementCache.clear();

        Xml25ExperimentWriter writer = new Xml25ExperimentWriter(createStreamWriter(), elementCache);
        writer.write(exp);
        streamWriter.flush();

        Assert.assertEquals(experiment_names, output.toString());
    }

    @Test
    public void test_write_experiment_participantIdentificationMethod() throws XMLStreamException, IOException {
        ExtendedPsi25Experiment exp = new XmlExperiment(new DefaultPublication("xxxxxx"));
        exp.setParticipantIdentificationMethod(exp.getInteractionDetectionMethod());
        elementCache.clear();

        Xml25ExperimentWriter writer = new Xml25ExperimentWriter(createStreamWriter(), elementCache);
        writer.write(exp);
        streamWriter.flush();

        Assert.assertEquals(experiment_participant_method, output.toString());
    }

    @Test
    public void test_write_experiment_featureDetectionMethod() throws XMLStreamException, IOException {
        ExtendedPsi25Experiment exp = new XmlExperiment(new DefaultPublication("xxxxxx"));
        exp.setFeatureDetectionMethod(exp.getInteractionDetectionMethod());
        elementCache.clear();

        Xml25ExperimentWriter writer = new Xml25ExperimentWriter(createStreamWriter(), elementCache);
        writer.write(exp);
        streamWriter.flush();

        Assert.assertEquals(experiment_feature_method, output.toString());
    }
}
