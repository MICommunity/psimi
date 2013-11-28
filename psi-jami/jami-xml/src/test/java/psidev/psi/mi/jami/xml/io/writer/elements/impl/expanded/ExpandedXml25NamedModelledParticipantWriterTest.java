package psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.NamedEntity;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.RangeUtils;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Unti tester for ExapndedXml25NamedModelledParticipantWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/11/13</pre>
 */

public class ExpandedXml25NamedModelledParticipantWriterTest extends AbstractXml25WriterTest {

    private String participant = "<participant id=\"1\">\n" +
            "  <interactor id=\"2\">\n" +
            "    <names>\n" +
            "      <shortLabel>protein test</shortLabel>\n" +
            "    </names>\n" +
            "    <interactorType>\n" +
            "      <names>\n" +
            "        <shortLabel>protein</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </interactorType>\n" +
            "  </interactor>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "</participant>";

    private String participant_interaction = "<participant id=\"1\">\n" +
            "  <interactionRef>2</interactionRef>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "</participant>";

    private String participant_complex = "<participant id=\"1\">\n" +
            "  <interactor id=\"2\">\n" +
            "    <names>\n" +
            "      <shortLabel>test complex</shortLabel>\n" +
            "    </names>\n" +
            "    <interactorType>\n" +
            "      <names>\n" +
            "        <shortLabel>complex</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0314\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </interactorType>\n" +
            "  </interactor>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "</participant>";

    private String participant_aliases ="<participant id=\"1\">\n" +
            "  <names>\n" +
            "    <alias type=\"synonym\">participant synonym</alias>\n"+
            "    <alias>test</alias>\n"+
            "  </names>\n" +
            "  <interactor id=\"2\">\n" +
            "    <names>\n" +
            "      <shortLabel>protein test</shortLabel>\n" +
            "    </names>\n" +
            "    <interactorType>\n" +
            "      <names>\n" +
            "        <shortLabel>protein</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </interactorType>\n" +
            "  </interactor>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "</participant>";

    private String participant_shortName ="<participant id=\"1\">\n" +
            "  <names>\n" +
            "    <shortLabel>participant test</shortLabel>\n"+
            "    <alias type=\"synonym\">participant synonym</alias>\n"+
            "    <alias>test</alias>\n"+
            "  </names>\n" +
            "  <interactor id=\"2\">\n" +
            "    <names>\n" +
            "      <shortLabel>protein test</shortLabel>\n" +
            "    </names>\n" +
            "    <interactorType>\n" +
            "      <names>\n" +
            "        <shortLabel>protein</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </interactorType>\n" +
            "  </interactor>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "</participant>";

    private String participant_fullName ="<participant id=\"1\">\n" +
            "  <names>\n" +
            "    <fullName>participant test</fullName>\n"+
            "    <alias type=\"synonym\">participant synonym</alias>\n"+
            "    <alias>test</alias>\n"+
            "  </names>\n" +
            "  <interactor id=\"2\">\n" +
            "    <names>\n" +
            "      <shortLabel>protein test</shortLabel>\n" +
            "    </names>\n" +
            "    <interactorType>\n" +
            "      <names>\n" +
            "        <shortLabel>protein</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </interactorType>\n" +
            "  </interactor>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "</participant>";

    private String participant_xref = "<participant id=\"1\">\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"test2\" id=\"xxxx2\"/>\n" +
            "    <secondaryRef db=\"test\" id=\"xxxx1\"/>\n"+
            "  </xref>\n"+
            "  <interactor id=\"2\">\n" +
            "    <names>\n" +
            "      <shortLabel>protein test</shortLabel>\n" +
            "    </names>\n" +
            "    <interactorType>\n" +
            "      <names>\n" +
            "        <shortLabel>protein</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </interactorType>\n" +
            "  </interactor>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "</participant>";
    private String participant_feature = "<participant id=\"1\">\n" +
            "  <interactor id=\"2\">\n" +
            "    <names>\n" +
            "      <shortLabel>protein test</shortLabel>\n" +
            "    </names>\n" +
            "    <interactorType>\n" +
            "      <names>\n" +
            "        <shortLabel>protein</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </interactorType>\n" +
            "  </interactor>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "  <featureList>\n" +
            "    <feature id=\"3\">\n" +
            "      <featureType>\n" +
            "        <names>\n" +
            "          <shortLabel>biological feature</shortLabel>\n" +
            "        </names>\n" +
            "        <xref>\n" +
            "          <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0252\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "        </xref>\n" +
            "      </featureType>\n" +
            "      <featureRangeList>\n" +
            "        <featureRange>\n" +
            "          <startStatus>\n" +
            "            <names>\n" +
            "              <shortLabel>certain</shortLabel>\n"+
            "            </names>\n"+
            "            <xref>\n" +
            "              <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "            </xref>\n"+
            "          </startStatus>\n" +
            "          <begin position=\"1\"/>\n"+
            "          <endStatus>\n" +
            "            <names>\n" +
            "              <shortLabel>certain</shortLabel>\n"+
            "            </names>\n"+
            "            <xref>\n" +
            "              <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0335\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n"+
            "            </xref>\n"+
            "          </endStatus>\n" +
            "          <end position=\"4\"/>\n"+
            "        </featureRange>\n"+
            "      </featureRangeList>\n" +
            "    </feature>\n"+
            "  </featureList>\n"+
            "</participant>";
    private String participant_attributes =  "<participant id=\"1\">\n" +
            "  <interactor id=\"2\">\n" +
            "    <names>\n" +
            "      <shortLabel>protein test</shortLabel>\n" +
            "    </names>\n" +
            "    <interactorType>\n" +
            "      <names>\n" +
            "        <shortLabel>protein</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </interactorType>\n" +
            "  </interactor>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "  <attributeList>\n" +
            "    <attribute name=\"test2\"/>\n"+
            "    <attribute name=\"test3\"/>\n"+
            "  </attributeList>\n"+
            "</participant>";
    private String participant_stoichiometry =  "<participant id=\"1\">\n" +
            "  <interactor id=\"2\">\n" +
            "    <names>\n" +
            "      <shortLabel>protein test</shortLabel>\n" +
            "    </names>\n" +
            "    <interactorType>\n" +
            "      <names>\n" +
            "        <shortLabel>protein</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </interactorType>\n" +
            "  </interactor>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "  <attributeList>\n" +
            "    <attribute name=\"comment\" nameAc=\"MI:0612\">stoichiometry: 1</attribute>\n"+
            "  </attributeList>\n"+
            "</participant>";
    private String participant_stoichiometry_range =  "<participant id=\"1\">\n" +
            "  <interactor id=\"2\">\n" +
            "    <names>\n" +
            "      <shortLabel>protein test</shortLabel>\n" +
            "    </names>\n" +
            "    <interactorType>\n" +
            "      <names>\n" +
            "        <shortLabel>protein</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </interactorType>\n" +
            "  </interactor>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "  <attributeList>\n" +
            "    <attribute name=\"comment\" nameAc=\"MI:0612\">stoichiometry: 1 - 4</attribute>\n"+
            "  </attributeList>\n"+
            "</participant>";
    private String participant_registered = "<participant id=\"2\">\n" +
            "  <interactor id=\"3\">\n" +
            "    <names>\n" +
            "      <shortLabel>protein test</shortLabel>\n" +
            "    </names>\n" +
            "    <interactorType>\n" +
            "      <names>\n" +
            "        <shortLabel>protein</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0326\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </interactorType>\n" +
            "  </interactor>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "</participant>";

    private PsiXml25ObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_participant() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledParticipant participant = new DefaultNamedModelledParticipant(new DefaultProtein("protein test"));
        elementCache.clear();

        ExpandedXml25NamedModelledParticipantWriter writer = new ExpandedXml25NamedModelledParticipantWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant, output.toString());
    }

    @Test
    public void test_write_participant_complex() throws XMLStreamException, IOException, IllegalRangeException {
        Complex complex = new DefaultComplex("test complex");
        complex.getParticipants().add(new DefaultModelledParticipant(new DefaultProtein("test protein")));
        ModelledParticipant participant = new DefaultNamedModelledParticipant(complex);
        elementCache.clear();

        ExpandedXml25NamedModelledParticipantWriter writer = new ExpandedXml25NamedModelledParticipantWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_interaction, output.toString());
    }

    @Test
    public void test_write_participant_complex_as_interactor() throws XMLStreamException, IOException, IllegalRangeException {
        Complex complex = new DefaultComplex("test complex");
        complex.getParticipants().add(new DefaultModelledParticipant(new DefaultProtein("test protein")));
        ModelledParticipant participant = new DefaultNamedModelledParticipant(complex);
        elementCache.clear();

        ExpandedXml25NamedModelledParticipantWriter writer = new ExpandedXml25NamedModelledParticipantWriter(createStreamWriter(), this.elementCache);
        writer.setComplexAsInteractor(true);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_complex, output.toString());
    }

    @Test
    public void test_write_participant_complex_no_participants() throws XMLStreamException, IOException, IllegalRangeException {
        Complex complex = new DefaultComplex("test complex");
        ModelledParticipant participant = new DefaultNamedModelledParticipant(complex);
        elementCache.clear();

        ExpandedXml25NamedModelledParticipantWriter writer = new ExpandedXml25NamedModelledParticipantWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_complex, output.toString());
    }

    @Test
    public void test_write_participant_aliases() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledParticipant participant = new DefaultNamedModelledParticipant(new DefaultProtein("protein test"));
        participant.getAliases().add(new DefaultAlias(new DefaultCvTerm("synonym"), "participant synonym"));
        participant.getAliases().add(new DefaultAlias("test"));
        elementCache.clear();

        ExpandedXml25NamedModelledParticipantWriter writer = new ExpandedXml25NamedModelledParticipantWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_aliases, output.toString());
    }

    @Test
    public void test_write_participant_shortName() throws XMLStreamException, IOException, IllegalRangeException {
        NamedEntity participant = new DefaultNamedModelledParticipant(new DefaultProtein("protein test"));
        participant.getAliases().add(new DefaultAlias(new DefaultCvTerm("synonym"), "participant synonym"));
        participant.getAliases().add(new DefaultAlias("test"));
        participant.setShortName("participant test");
        elementCache.clear();

        ExpandedXml25NamedModelledParticipantWriter writer = new ExpandedXml25NamedModelledParticipantWriter(createStreamWriter(), this.elementCache);
        writer.write((ModelledParticipant)participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_shortName, output.toString());
    }

    @Test
    public void test_write_participant_fullName() throws XMLStreamException, IOException, IllegalRangeException {
        NamedEntity participant = new DefaultNamedModelledParticipant(new DefaultProtein("protein test"));
        participant.getAliases().add(new DefaultAlias(new DefaultCvTerm("synonym"), "participant synonym"));
        participant.getAliases().add(new DefaultAlias("test"));
        participant.setFullName("participant test");
        elementCache.clear();

        ExpandedXml25NamedModelledParticipantWriter writer = new ExpandedXml25NamedModelledParticipantWriter(createStreamWriter(), this.elementCache);
        writer.write((ModelledParticipant)participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_fullName, output.toString());
    }

    @Test
    public void test_write_participant_xref() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledParticipant participant = new DefaultNamedModelledParticipant(new DefaultProtein("protein test"));
        participant.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxx2"));
        participant.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxx1"));
        elementCache.clear();

        ExpandedXml25NamedModelledParticipantWriter writer = new ExpandedXml25NamedModelledParticipantWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_xref, output.toString());
    }

    @Test
    public void test_write_participant_feature() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledParticipant participant = new DefaultNamedModelledParticipant(new DefaultProtein("protein test"));
        ModelledFeature feature = new DefaultModelledFeature();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        participant.addFeature(feature);
        elementCache.clear();

        ExpandedXml25NamedModelledParticipantWriter writer = new ExpandedXml25NamedModelledParticipantWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_feature, output.toString());
    }

    @Test
    public void test_write_participant_attributes() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledParticipant participant = new DefaultNamedModelledParticipant(new DefaultProtein("protein test"));
        participant.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test2")));
        participant.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));
        elementCache.clear();

        ExpandedXml25NamedModelledParticipantWriter writer = new ExpandedXml25NamedModelledParticipantWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_attributes, output.toString());
    }

    @Test
    public void test_write_participant_stoichiometry() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledParticipant participant = new DefaultNamedModelledParticipant(new DefaultProtein("protein test"));
        participant.setStoichiometry(1);
        elementCache.clear();

        ExpandedXml25NamedModelledParticipantWriter writer = new ExpandedXml25NamedModelledParticipantWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_stoichiometry, output.toString());
    }

    @Test
    public void test_write_participant_stoichiometry_range() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledParticipant participant = new DefaultNamedModelledParticipant(new DefaultProtein("protein test"));
        participant.setStoichiometry(new DefaultStoichiometry(1,4));
        elementCache.clear();

        ExpandedXml25NamedModelledParticipantWriter writer = new ExpandedXml25NamedModelledParticipantWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_stoichiometry_range, output.toString());
    }

    @Test
    public void test_write_participant_registered() throws XMLStreamException, IOException, IllegalRangeException {
        ModelledParticipant participant = new DefaultNamedModelledParticipant(new DefaultProtein("protein test"));
        elementCache.clear();
        elementCache.extractIdForParticipant(new DefaultParticipant(new DefaultProtein("protein test")));
        elementCache.extractIdForParticipant(participant);

        ExpandedXml25NamedModelledParticipantWriter writer = new ExpandedXml25NamedModelledParticipantWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_registered, output.toString());
    }
}
