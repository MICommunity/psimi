package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.RangeUtils;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.cache.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXmlWriterTest;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlFeatureWriter;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for XmlFeatureWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/13</pre>
 */

public class XmlFeatureWriterTest extends AbstractXmlWriterTest {

    private String feature = "<feature id=\"1\">\n" +
            "  <featureRangeList>\n" +
            "    <featureRange>\n" +
            "      <startStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </endStatus>\n" +
            "      <end position=\"4\"/>\n"+
            "    </featureRange>\n"+
            "  </featureRangeList>\n" +
            "</feature>";

    private String feature_shortName = "<feature id=\"1\">\n" +
            "  <names>\n" +
            "    <shortLabel>test feature</shortLabel>\n"+
            "  </names>\n"+
            "  <featureRangeList>\n" +
            "    <featureRange>\n" +
            "      <startStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </endStatus>\n" +
            "      <end position=\"4\"/>\n"+
            "    </featureRange>\n"+
            "  </featureRangeList>\n" +
            "</feature>";

    private String feature_fullName = "<feature id=\"1\">\n" +
            "  <names>\n" +
            "    <fullName>test feature</fullName>\n"+
            "  </names>\n"+
            "  <featureRangeList>\n" +
            "    <featureRange>\n" +
            "      <startStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </endStatus>\n" +
            "      <end position=\"4\"/>\n"+
            "    </featureRange>\n"+
            "  </featureRangeList>\n" +
            "</feature>";

    private String featureIdentifier = "<feature id=\"1\">\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"interpro\" dbAc=\"MI:0449\" id=\"IPRxxxxx\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    <secondaryRef db=\"test\" id=\"xxxx1\"/>\n"+
            "  </xref>\n"+
            "  <featureRangeList>\n" +
            "    <featureRange>\n" +
            "      <startStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </endStatus>\n" +
            "      <end position=\"4\"/>\n"+
            "    </featureRange>\n"+
            "  </featureRangeList>\n" +
            "</feature>";
    private String featureXref = "<feature id=\"1\">\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"test\" id=\"xxxx12\" refType=\"see-also\"/>\n" +
            "    <secondaryRef db=\"test\" id=\"xxxx1\"/>\n"+
            "  </xref>\n"+
            "  <featureRangeList>\n" +
            "    <featureRange>\n" +
            "      <startStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </endStatus>\n" +
            "      <end position=\"4\"/>\n"+
            "    </featureRange>\n"+
            "  </featureRangeList>\n" +
            "</feature>";
    private String feature_featureType = "<feature id=\"1\">\n" +
            "  <featureType>\n" +
            "    <names>\n" +
            "      <shortLabel>biological feature</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:xxx1\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </featureType>\n" +
            "  <featureRangeList>\n" +
            "    <featureRange>\n" +
            "      <startStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </endStatus>\n" +
            "      <end position=\"4\"/>\n"+
            "    </featureRange>\n"+
            "  </featureRangeList>\n" +
            "</feature>";
    private String featureAttributes = "<feature id=\"1\">\n" +
            "  <featureRangeList>\n" +
            "    <featureRange>\n" +
            "      <startStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </endStatus>\n" +
            "      <end position=\"4\"/>\n"+
            "    </featureRange>\n"+
            "  </featureRangeList>\n" +
            "  <attributeList>\n" +
            "    <attribute name=\"test2\"/>\n"+
            "    <attribute name=\"test3\"/>\n"+
            "  </attributeList>\n"+
            "</feature>";

    private String feature_interactionEffect = "<feature id=\"1\">\n" +
            "  <featureRangeList>\n" +
            "    <featureRange>\n" +
            "      <startStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </endStatus>\n" +
            "      <end position=\"4\"/>\n"+
            "    </featureRange>\n"+
            "  </featureRangeList>\n" +
            "  <attributeList>\n" +
            "    <attribute name=\"decreasing-ptm\" nameAc=\"MI:1223\"/>\n"+
            "  </attributeList>\n"+
            "</feature>";

    private String feature_interactionDependency = "<feature id=\"1\">\n" +
            "  <featureRangeList>\n" +
            "    <featureRange>\n" +
            "      <startStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </endStatus>\n" +
            "      <end position=\"4\"/>\n"+
            "    </featureRange>\n"+
            "  </featureRangeList>\n" +
            "  <attributeList>\n" +
            "    <attribute name=\"resulting-ptm\" nameAc=\"MI:0639\"/>\n"+
            "  </attributeList>\n"+
            "</feature>";

    private String feature_participantRef = "<feature id=\"1\">\n" +
            "  <featureRangeList>\n" +
            "    <featureRange>\n" +
            "      <startStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </endStatus>\n" +
            "      <end position=\"4\"/>\n"+
            "    </featureRange>\n"+
            "  </featureRangeList>\n" +
            "  <attributeList>\n" +
            "    <attribute name=\"participant-ref\" nameAc=\"MI:1151\">2</attribute>\n"+
            "  </attributeList>\n"+
            "</feature>";

    private String feature_registered = "<feature id=\"2\">\n" +
            "  <featureRangeList>\n" +
            "    <featureRange>\n" +
            "      <startStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "        </xref>\n"+
            "      </endStatus>\n" +
            "      <end position=\"4\"/>\n"+
            "    </featureRange>\n"+
            "  </featureRangeList>\n" +
            "</feature>";

    private PsiXmlObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_feature() throws XMLStreamException, IOException, IllegalRangeException {
        Feature feature = new DefaultFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        elementCache.clear();

        XmlFeatureWriter writer = new XmlFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write(feature);
        streamWriter.flush();

        Assert.assertEquals(this.feature, output.toString());
    }

    @Test
    public void test_write_feature_shortName() throws XMLStreamException, IOException, IllegalRangeException {
        Feature feature = new DefaultFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        feature.setShortName("test feature");
        elementCache.clear();

        XmlFeatureWriter writer = new XmlFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write(feature);
        streamWriter.flush();

        Assert.assertEquals(this.feature_shortName, output.toString());
    }

    @Test
    public void test_write_feature_fullName() throws XMLStreamException, IOException, IllegalRangeException {
        Feature feature = new DefaultFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        feature.setFullName("test feature");

        elementCache.clear();

        XmlFeatureWriter writer = new XmlFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write(feature);
        streamWriter.flush();

        Assert.assertEquals(this.feature_fullName, output.toString());
    }

    @Test
    public void test_write_feature_identifier() throws XMLStreamException, IOException, IllegalRangeException {
        Feature feature = new DefaultFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        feature.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxx1"));
        feature.setInterpro("IPRxxxxx");

        elementCache.clear();

        XmlFeatureWriter writer = new XmlFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write(feature);
        streamWriter.flush();

        Assert.assertEquals(this.featureIdentifier, output.toString());
    }

    @Test
    public void test_write_feature_xref() throws XMLStreamException, IOException, IllegalRangeException {
        Feature feature = new DefaultFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        feature.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxx12", new DefaultCvTerm("see-also")));
        feature.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxx1"));

        elementCache.clear();

        XmlFeatureWriter writer = new XmlFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write(feature);
        streamWriter.flush();

        Assert.assertEquals(this.featureXref, output.toString());
    }

    @Test
    public void test_write_feature_featureType() throws XMLStreamException, IOException, IllegalRangeException {
        Feature feature = new DefaultFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        feature.setType(new DefaultCvTerm("biological feature", "MI:xxx1"));

        elementCache.clear();

        XmlFeatureWriter writer = new XmlFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write(feature);
        streamWriter.flush();

        Assert.assertEquals(this.feature_featureType, output.toString());
    }

    @Test
    public void test_write_feature_attribute() throws XMLStreamException, IOException, IllegalRangeException {
        Feature feature = new DefaultFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        feature.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test2")));
        feature.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));

        elementCache.clear();

        XmlFeatureWriter writer = new XmlFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write(feature);
        streamWriter.flush();

        Assert.assertEquals(this.featureAttributes, output.toString());
    }

    @Test
    public void test_write_feature_interaction_effect() throws XMLStreamException, IOException, IllegalRangeException {
        Feature feature = new DefaultFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        feature.setRole(CvTermUtils.createMICvTerm("decreasing-ptm","MI:1223"));
        elementCache.clear();

        XmlFeatureWriter writer = new XmlFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write(feature);
        streamWriter.flush();

        Assert.assertEquals(this.feature_interactionEffect, output.toString());
    }

    @Test
    public void test_write_feature_interaction_dependency() throws XMLStreamException, IOException, IllegalRangeException {
        Feature feature = new DefaultFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        feature.setRole(CvTermUtils.createMICvTerm("resulting-ptm", "MI:0639"));

        elementCache.clear();

        XmlFeatureWriter writer = new XmlFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write(feature);
        streamWriter.flush();

        Assert.assertEquals(this.feature_interactionDependency, output.toString());
    }

    @Test
    public void test_write_feature_participant_ref() throws XMLStreamException, IOException, IllegalRangeException {
        Feature feature = new DefaultFeature();
        Range range = RangeUtils.createRangeFromString("1-4");
        feature.getRanges().add(range);
        range.setParticipant(new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));
        elementCache.clear();

        XmlFeatureWriter writer = new XmlFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write(feature);
        streamWriter.flush();

        Assert.assertEquals(this.feature_participantRef, output.toString());
    }

    @Test
    public void test_write_feature_registered() throws XMLStreamException, IOException, IllegalRangeException {
        Feature feature = new DefaultFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));

        elementCache.clear();
        elementCache.extractIdForFeature(new DefaultFeature());
        elementCache.extractIdForFeature(feature);

        XmlFeatureWriter writer = new XmlFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write(feature);
        streamWriter.flush();

        Assert.assertEquals(this.feature_registered, output.toString());
    }
}
