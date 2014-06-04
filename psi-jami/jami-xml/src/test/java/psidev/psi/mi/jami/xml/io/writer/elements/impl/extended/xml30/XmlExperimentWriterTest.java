package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;
import psidev.psi.mi.jami.xml.model.extension.XmlExperiment;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.text.ParseException;

/**
 * Unit tester for XmlExperimentWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/13</pre>
 */

public class XmlExperimentWriterTest extends AbstractXmlWriterTest {

    private String experiment_imex ="<experimentDescription id=\"1\">\n" +
            "  <bibref>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "      <secondaryRef db=\"imex\" dbAc=\"MI:0670\" id=\"IM-1\" refType=\"imex-primary\" refTypeAc=\"MI:0662\"/>\n"+
            "    </xref>\n"+
            "    <attributeList>\n" +
            "      <attribute name=\"curation depth\" nameAc=\"MI:0955\">imex curation</attribute>\n" +
            "      <attribute name=\"imex curation\" nameAc=\"MI:0959\"/>\n" +
            "    </attributeList>\n"+
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
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0686\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "  </interactionDetectionMethod>\n"+
            "</experimentDescription>";
    private String experiment_attributes ="<experimentDescription id=\"1\">\n" +
            "  <bibref>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"pubmed\" dbAc=\"MI:0446\" id=\"xxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "    </xref>\n"+
            "    <attributeList>\n" +
            "      <attribute name=\"test3\"/>\n" +
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
            "    <attributeList>\n" +
            "      <attribute name=\"publication title\" nameAc=\"MI:1091\">test title</attribute>\n"+
            "      <attribute name=\"journal\" nameAc=\"MI:0885\">test journal</attribute>\n"+
            "      <attribute name=\"publication year\" nameAc=\"MI:0886\">2006</attribute>\n"+
            "      <attribute name=\"curation depth\" nameAc=\"MI:0955\">imex curation</attribute>\n"+
            "      <attribute name=\"imex curation\" nameAc=\"MI:0959\"/>\n"+
            "      <attribute name=\"author-list\" nameAc=\"MI:0636\">author1, author2, author3</attribute>\n"+
            "      <attribute name=\"test3\"/>\n"+
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
            "  <attributeList>\n" +
            "    <attribute name=\"test3\"/>\n"+
            "  </attributeList>\n"+
            "</experimentDescription>";
    private String experiment_variable_parameter = "<experimentDescription id=\"1\">\n" +
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
            "  <variableParameterList>\n" +
            "    <variableParameter>\n" +
            "      <description>test description</description>\n" +
            "      <variableValueList>\n" +
            "        <variableValue id=\"2\">\n" +
            "          <value>0.5</value>\n" +
            "        </variableValue>\n"+
            "      </variableValueList>\n" +
            "    </variableParameter>\n"+
            "  </variableParameterList>\n" +
            "</experimentDescription>";

    private PsiXmlObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_experiment_imex() throws XMLStreamException, IOException {
        Experiment exp = new XmlExperiment(new DefaultPublication("xxxxxx"));
        exp.getPublication().assignImexId("IM-1");
        exp.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxxxx"));
        exp.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"),"12345"));
        exp.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"),"12346"));
        elementCache.clear();

        XmlExperimentWriter writer = new XmlExperimentWriter(createStreamWriter(), elementCache);
        writer.write(exp);
        streamWriter.flush();

        Assert.assertEquals(experiment_imex, output.toString());
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

        XmlExperimentWriter writer = new XmlExperimentWriter(createStreamWriter(), elementCache);
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
        pub.setPublicationDate(PsiXmlUtils.YEAR_FORMAT.parse("2006"));
        pub.setCurationDepth(CurationDepth.IMEx);
        pub.getAuthors().add("author1");
        pub.getAuthors().add("author2");
        pub.getAuthors().add("author3");
        pub.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));
        exp.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));
        elementCache.clear();

        XmlExperimentWriter writer = new XmlExperimentWriter(createStreamWriter(), elementCache);
        writer.write(exp);
        streamWriter.flush();

        Assert.assertEquals(experiment_pub_attributes, output.toString());
    }

    @Test
    public void test_write_experiment_variable_parameter() throws XMLStreamException, IOException {
        Experiment exp = new DefaultExperiment(new DefaultPublication("xxxxxx"));
        VariableParameter param = new DefaultVariableParameter("test description");
        VariableParameterValue value = new DefaultVariableParameterValue("0.5", param);
        param.getVariableValues().add(value);
        exp.getVariableParameters().add(param);
        elementCache.clear();

        XmlExperimentWriter writer = new XmlExperimentWriter(createStreamWriter(), elementCache);
        writer.write(exp);
        streamWriter.flush();

        Assert.assertEquals(this.experiment_variable_parameter, output.toString());
    }
}
