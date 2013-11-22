package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.NamedFeature;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.RangeUtils;
import psidev.psi.mi.jami.xml.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unit tester for Xml25NamedModelledFeatureWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/13</pre>
 */

public class Xml25NamedModelledFeatureWriterTest extends AbstractXml25WriterTest {

    private String feature = "<feature id=\"1\">\n" +
            "  <featureType>\n" +
            "    <names>\n" +
            "      <shortLabel>biological feature</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" id=\"MI:0252\" refType=\"identity\"/>\n" +
            "    </xref>\n" +
            "  </featureType>\n" +
            "  <featureRangeList>\n" +
            "    <featureRange>\n" +
            "      <startStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
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
            "  <featureType>\n" +
            "    <names>\n" +
            "      <shortLabel>biological feature</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" id=\"MI:0252\" refType=\"identity\"/>\n" +
            "    </xref>\n" +
            "  </featureType>\n" +
            "  <featureRangeList>\n" +
            "    <featureRange>\n" +
            "      <startStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
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
            "  <featureType>\n" +
            "    <names>\n" +
            "      <shortLabel>biological feature</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" id=\"MI:0252\" refType=\"identity\"/>\n" +
            "    </xref>\n" +
            "  </featureType>\n" +
            "  <featureRangeList>\n" +
            "    <featureRange>\n" +
            "      <startStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
            "        </xref>\n"+
            "      </endStatus>\n" +
            "      <end position=\"4\"/>\n"+
            "    </featureRange>\n"+
            "  </featureRangeList>\n" +
            "</feature>";

    private String feature_aliases = "<feature id=\"1\">\n" +
            "  <names>\n" +
            "    <alias type=\"synonym\">feature synonym</alias>\n"+
            "    <alias>test</alias>\n"+
            "  </names>\n"+
            "  <featureType>\n" +
            "    <names>\n" +
            "      <shortLabel>biological feature</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" id=\"MI:0252\" refType=\"identity\"/>\n" +
            "    </xref>\n" +
            "  </featureType>\n" +
            "  <featureRangeList>\n" +
            "    <featureRange>\n" +
            "      <startStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
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
            "  <featureType>\n" +
            "    <names>\n" +
            "      <shortLabel>biological feature</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" id=\"MI:0252\" refType=\"identity\"/>\n" +
            "    </xref>\n" +
            "  </featureType>\n" +
            "  <featureRangeList>\n" +
            "    <featureRange>\n" +
            "      <startStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
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
            "  <featureType>\n" +
            "    <names>\n" +
            "      <shortLabel>biological feature</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" id=\"MI:0252\" refType=\"identity\"/>\n" +
            "    </xref>\n" +
            "  </featureType>\n" +
            "  <featureRangeList>\n" +
            "    <featureRange>\n" +
            "      <startStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
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
            "          <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
            "        </xref>\n"+
            "      </endStatus>\n" +
            "      <end position=\"4\"/>\n"+
            "    </featureRange>\n"+
            "  </featureRangeList>\n" +
            "</feature>";
    private String featureAttributes = "<feature id=\"1\">\n" +
            "  <featureType>\n" +
            "    <names>\n" +
            "      <shortLabel>biological feature</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" id=\"MI:0252\" refType=\"identity\"/>\n" +
            "    </xref>\n" +
            "  </featureType>\n" +
            "  <featureRangeList>\n" +
            "    <featureRange>\n" +
            "      <startStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
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

    private String feature_registered = "<feature id=\"2\">\n" +
            "  <featureType>\n" +
            "    <names>\n" +
            "      <shortLabel>biological feature</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" id=\"MI:0252\" refType=\"identity\"/>\n" +
            "    </xref>\n" +
            "  </featureType>\n" +
            "  <featureRangeList>\n" +
            "    <featureRange>\n" +
            "      <startStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
            "        </xref>\n"+
            "      </startStatus>\n" +
            "      <begin position=\"1\"/>\n"+
            "      <endStatus>\n" +
            "        <names>\n" +
            "          <shortLabel>certain</shortLabel>\n"+
            "        </names>\n"+
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" id=\"MI:0335\" refType=\"identity\"/>\n"+
            "        </xref>\n"+
            "      </endStatus>\n" +
            "      <end position=\"4\"/>\n"+
            "    </featureRange>\n"+
            "  </featureRangeList>\n" +
            "</feature>";

    private PsiXml25ObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_feature() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledFeature feature = new DefaultNamedModelledFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        elementCache.clear();

        Xml25NamedModelledFeatureWriter writer = new Xml25NamedModelledFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write(feature);
        streamWriter.flush();

        Assert.assertEquals(this.feature, output.toString());
    }

    @Test
    public void test_write_feature_shortName() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledFeature feature = new DefaultNamedModelledFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        feature.setShortName("test feature");
        elementCache.clear();

        Xml25NamedModelledFeatureWriter writer = new Xml25NamedModelledFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write(feature);
        streamWriter.flush();

        Assert.assertEquals(this.feature_shortName, output.toString());
    }

    @Test
    public void test_write_feature_fullName() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledFeature feature = new DefaultNamedModelledFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        feature.setFullName("test feature");

        elementCache.clear();

        Xml25NamedModelledFeatureWriter writer = new Xml25NamedModelledFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write(feature);
        streamWriter.flush();

        Assert.assertEquals(this.feature_fullName, output.toString());
    }

    @Test
    public void test_write_feature_alias() throws XMLStreamException, IOException, IllegalRangeException {
        NamedFeature feature = new DefaultNamedModelledFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        feature.getAliases().add(new DefaultAlias(new DefaultCvTerm("synonym"), "feature synonym"));
        feature.getAliases().add(new DefaultAlias("test"));

        elementCache.clear();

        Xml25NamedModelledFeatureWriter writer = new Xml25NamedModelledFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write((ModelledFeature)feature);
        streamWriter.flush();

        Assert.assertEquals(this.feature_aliases, output.toString());
    }

    @Test
    public void test_write_feature_identifier() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledFeature feature = new DefaultNamedModelledFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        feature.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxx1"));
        feature.setInterpro("IPRxxxxx");

        elementCache.clear();

        Xml25NamedModelledFeatureWriter writer = new Xml25NamedModelledFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write(feature);
        streamWriter.flush();

        Assert.assertEquals(this.featureIdentifier, output.toString());
    }

    @Test
    public void test_write_feature_xref() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledFeature feature = new DefaultNamedModelledFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        feature.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxx12", new DefaultCvTerm("see-also")));
        feature.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxx1"));

        elementCache.clear();

        Xml25NamedModelledFeatureWriter writer = new Xml25NamedModelledFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write(feature);
        streamWriter.flush();

        Assert.assertEquals(this.featureXref, output.toString());
    }

    @Test
    public void test_write_feature_featureType() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledFeature feature = new DefaultNamedModelledFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        feature.setType(new DefaultCvTerm("biological feature", "MI:xxx1"));

        elementCache.clear();

        Xml25NamedModelledFeatureWriter writer = new Xml25NamedModelledFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write(feature);
        streamWriter.flush();

        Assert.assertEquals(this.feature_featureType, output.toString());
    }

    @Test
    public void test_write_feature_attribute() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledFeature feature = new DefaultNamedModelledFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        feature.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test2")));
        feature.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));

        elementCache.clear();

        Xml25NamedModelledFeatureWriter writer = new Xml25NamedModelledFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write(feature);
        streamWriter.flush();

        Assert.assertEquals(this.featureAttributes, output.toString());
    }

    @Test
    public void test_write_feature_registered() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledFeature feature = new DefaultNamedModelledFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));

        elementCache.clear();
        elementCache.extractIdForFeature(new DefaultFeature());
        elementCache.extractIdForFeature(feature);

        Xml25NamedModelledFeatureWriter writer = new Xml25NamedModelledFeatureWriter(createStreamWriter(), this.elementCache);
        writer.write(feature);
        streamWriter.flush();

        Assert.assertEquals(this.feature_registered, output.toString());
    }
}

