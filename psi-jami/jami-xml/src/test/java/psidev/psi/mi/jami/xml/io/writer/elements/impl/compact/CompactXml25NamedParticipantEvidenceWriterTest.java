package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.RangeUtils;
import psidev.psi.mi.jami.xml.InMemoryIdentityObjectCache;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.AbstractXml25WriterTest;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Unit tester for CompactXml25NamedParticipantEvidenceWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/11/13</pre>
 */

public class CompactXml25NamedParticipantEvidenceWriterTest extends AbstractXml25WriterTest {

    private String participant = "<participant id=\"1\">\n" +
            "  <interactorRef>2</interactorRef>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "  <experimentalRoleList>\n" +
            "    <experimentalRole>\n" +
            "      <names>\n" +
            "        <shortLabel>unspecified role</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </experimentalRole>\n" +
            "  </experimentalRoleList>\n" +
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
            "  <experimentalRoleList>\n" +
            "    <experimentalRole>\n" +
            "      <names>\n" +
            "        <shortLabel>unspecified role</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </experimentalRole>\n" +
            "  </experimentalRoleList>\n" +
            "</participant>";

    private String participant_aliases ="<participant id=\"1\">\n" +
            "  <names>\n" +
            "    <alias type=\"synonym\">participant synonym</alias>\n"+
            "    <alias>test</alias>\n"+
            "  </names>\n" +
            "  <interactorRef>2</interactorRef>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "  <experimentalRoleList>\n" +
            "    <experimentalRole>\n" +
            "      <names>\n" +
            "        <shortLabel>unspecified role</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </experimentalRole>\n" +
            "  </experimentalRoleList>\n" +
            "</participant>";
    private String participant_shortName ="<participant id=\"1\">\n" +
            "  <names>\n" +
            "    <shortLabel>participant test</shortLabel>\n"+
            "    <alias type=\"synonym\">participant synonym</alias>\n"+
            "    <alias>test</alias>\n"+
            "  </names>\n" +
            "  <interactorRef>2</interactorRef>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "  <experimentalRoleList>\n" +
            "    <experimentalRole>\n" +
            "      <names>\n" +
            "        <shortLabel>unspecified role</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </experimentalRole>\n" +
            "  </experimentalRoleList>\n" +
            "</participant>";

    private String participant_fullName ="<participant id=\"1\">\n" +
            "  <names>\n" +
            "    <fullName>participant test</fullName>\n"+
            "    <alias type=\"synonym\">participant synonym</alias>\n"+
            "    <alias>test</alias>\n"+
            "  </names>\n" +
            "  <interactorRef>2</interactorRef>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "  <experimentalRoleList>\n" +
            "    <experimentalRole>\n" +
            "      <names>\n" +
            "        <shortLabel>unspecified role</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </experimentalRole>\n" +
            "  </experimentalRoleList>\n" +
            "</participant>";
    private String participant_xref = "<participant id=\"1\">\n" +
            "  <xref>\n" +
            "    <primaryRef db=\"test2\" id=\"xxxx2\"/>\n" +
            "    <secondaryRef db=\"test\" id=\"xxxx1\"/>\n"+
            "  </xref>\n"+
            "  <interactorRef>2</interactorRef>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "  <experimentalRoleList>\n" +
            "    <experimentalRole>\n" +
            "      <names>\n" +
            "        <shortLabel>unspecified role</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </experimentalRole>\n" +
            "  </experimentalRoleList>\n" +
            "</participant>";
    private String participant_feature = "<participant id=\"1\">\n" +
            "  <interactorRef>2</interactorRef>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "  <experimentalRoleList>\n" +
            "    <experimentalRole>\n" +
            "      <names>\n" +
            "        <shortLabel>unspecified role</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </experimentalRole>\n" +
            "  </experimentalRoleList>\n" +
            "  <featureList>\n" +
            "    <feature id=\"3\">\n" +
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
            "  <interactorRef>2</interactorRef>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "  <experimentalRoleList>\n" +
            "    <experimentalRole>\n" +
            "      <names>\n" +
            "        <shortLabel>unspecified role</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </experimentalRole>\n" +
            "  </experimentalRoleList>\n" +
            "  <attributeList>\n" +
            "    <attribute name=\"test2\"/>\n"+
            "    <attribute name=\"test3\"/>\n"+
            "  </attributeList>\n"+
            "</participant>";
    private String participant_stoichiometry =  "<participant id=\"1\">\n" +
            "  <interactorRef>2</interactorRef>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "  <experimentalRoleList>\n" +
            "    <experimentalRole>\n" +
            "      <names>\n" +
            "        <shortLabel>unspecified role</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </experimentalRole>\n" +
            "  </experimentalRoleList>\n" +
            "  <attributeList>\n" +
            "    <attribute name=\"comment\" nameAc=\"MI:0612\">stoichiometry: 1</attribute>\n"+
            "  </attributeList>\n"+
            "</participant>";
    private String participant_stoichiometry_range =  "<participant id=\"1\">\n" +
            "  <interactorRef>2</interactorRef>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "  <experimentalRoleList>\n" +
            "    <experimentalRole>\n" +
            "      <names>\n" +
            "        <shortLabel>unspecified role</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </experimentalRole>\n" +
            "  </experimentalRoleList>\n" +
            "  <attributeList>\n" +
            "    <attribute name=\"comment\" nameAc=\"MI:0612\">stoichiometry: 1 - 4</attribute>\n"+
            "  </attributeList>\n"+
            "</participant>";
    private String participant_registered = "<participant id=\"2\">\n" +
            "  <interactorRef>3</interactorRef>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "  <experimentalRoleList>\n" +
            "    <experimentalRole>\n" +
            "      <names>\n" +
            "        <shortLabel>unspecified role</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </experimentalRole>\n" +
            "  </experimentalRoleList>\n" +
            "</participant>";
    private String participant_identification = "<participant id=\"1\">\n" +
            "  <interactorRef>2</interactorRef>\n" +
            "  <participantIdentificationMethodList>\n" +
            "    <participantIdentificationMethod>\n" +
            "      <names>\n" +
            "        <shortLabel>inference</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" id=\"MI:0362\" refType=\"identity\"/>\n" +
            "      </xref>\n" +
            "    </participantIdentificationMethod>\n" +
            "  </participantIdentificationMethodList>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "  <experimentalRoleList>\n" +
            "    <experimentalRole>\n" +
            "      <names>\n" +
            "        <shortLabel>unspecified role</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </experimentalRole>\n" +
            "  </experimentalRoleList>\n" +
            "</participant>";
    private String participant_exp_preparation = "<participant id=\"1\">\n" +
            "  <interactorRef>2</interactorRef>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "  <experimentalRoleList>\n" +
            "    <experimentalRole>\n" +
            "      <names>\n" +
            "        <shortLabel>unspecified role</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </experimentalRole>\n" +
            "  </experimentalRoleList>\n" +
            "  <experimentalPreparationList>\n" +
            "    <experimentalPreparation>\n" +
            "      <names>\n" +
            "        <shortLabel>sample process</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" id=\"MI:0342\" refType=\"identity\"/>\n" +
            "      </xref>\n" +
            "    </experimentalPreparation>\n" +
            "  </experimentalPreparationList>\n" +
            "</participant>";
    private String participant_expressed_in = "<participant id=\"1\">\n" +
            "  <interactorRef>2</interactorRef>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "  <experimentalRoleList>\n" +
            "    <experimentalRole>\n" +
            "      <names>\n" +
            "        <shortLabel>unspecified role</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </experimentalRole>\n" +
            "  </experimentalRoleList>\n" +
            "  <hostOrganismList>\n" +
            "    <hostOrganism ncbiTaxId=\"9606\">\n" +
            "      <names>\n" +
            "        <shortLabel>human</shortLabel>\n" +
            "      </names>\n" +
            "    </hostOrganism>\n" +
            "  </hostOrganismList>\n" +
            "</participant>";
    private String participant_parameters = "<participant id=\"1\">\n" +
            "  <interactorRef>2</interactorRef>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "  <experimentalRoleList>\n" +
            "    <experimentalRole>\n" +
            "      <names>\n" +
            "        <shortLabel>unspecified role</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </experimentalRole>\n" +
            "  </experimentalRoleList>\n" +
            "  <parameterList>\n" +
            "    <parameter term=\"kd\" base=\"10\" exponent=\"0\" factor=\"5\">\n" +
            "      <experimentRef>3</experimentRef>\n" +
            "    </parameter>\n"+
            "  </parameterList>\n" +
            "</participant>";
    private String participant_confidences = "<participant id=\"1\">\n" +
            "  <interactorRef>2</interactorRef>\n" +
            "  <biologicalRole>\n" +
            "    <names>\n" +
            "      <shortLabel>unspecified role</shortLabel>\n" +
            "    </names>\n" +
            "    <xref>\n" +
            "      <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "    </xref>\n" +
            "  </biologicalRole>\n" +
            "  <experimentalRoleList>\n" +
            "    <experimentalRole>\n" +
            "      <names>\n" +
            "        <shortLabel>unspecified role</shortLabel>\n" +
            "      </names>\n" +
            "      <xref>\n" +
            "        <primaryRef db=\"psi-mi\" dbAc=\"MI:0488\" id=\"MI:0499\" refType=\"identity\" refTypeAc=\"MI:0356\"/>\n" +
            "      </xref>\n" +
            "    </experimentalRole>\n" +
            "  </experimentalRoleList>\n" +
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
            "</participant>";

    private PsiXml25ObjectCache elementCache = new InMemoryIdentityObjectCache();

    @Test
    public void test_write_participant() throws XMLStreamException, IOException, IllegalRangeException {
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        elementCache.clear();

        CompactXml25NamedParticipantEvidenceWriter writer = new CompactXml25NamedParticipantEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant, output.toString());
    }

    @Test
    public void test_write_participant_complex() throws XMLStreamException, IOException, IllegalRangeException {
        Complex complex = new DefaultComplex("test complex");
        complex.getParticipants().add(new DefaultModelledParticipant(new DefaultProtein("test protein")));
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(complex);
        elementCache.clear();

        CompactXml25NamedParticipantEvidenceWriter writer = new CompactXml25NamedParticipantEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_interaction, output.toString());
    }

    @Test
    public void test_write_participant_complex_as_interactor() throws XMLStreamException, IOException, IllegalRangeException {
        Complex complex = new DefaultComplex("test complex");
        complex.getParticipants().add(new DefaultModelledParticipant(new DefaultProtein("test protein")));
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(complex);
        elementCache.clear();

        CompactXml25NamedParticipantEvidenceWriter writer = new CompactXml25NamedParticipantEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.setComplexAsInteractor(true);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant, output.toString());
    }

    @Test
    public void test_write_participant_complex_no_participants() throws XMLStreamException, IOException, IllegalRangeException {
        Complex complex = new DefaultComplex("test complex");
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(complex);
        elementCache.clear();

        CompactXml25NamedParticipantEvidenceWriter writer = new CompactXml25NamedParticipantEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant, output.toString());
    }

    @Test
    public void test_write_participant_aliases() throws XMLStreamException, IOException, IllegalRangeException {
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        participant.getAliases().add(new DefaultAlias(new DefaultCvTerm("synonym"), "participant synonym"));
        participant.getAliases().add(new DefaultAlias("test"));
        elementCache.clear();

        CompactXml25NamedParticipantEvidenceWriter writer = new CompactXml25NamedParticipantEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_aliases, output.toString());
    }

    @Test
    public void test_write_participant_shortName() throws XMLStreamException, IOException, IllegalRangeException {
        NamedEntity participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        participant.getAliases().add(new DefaultAlias(new DefaultCvTerm("synonym"), "participant synonym"));
        participant.getAliases().add(new DefaultAlias("test"));
        participant.setShortName("participant test");
        elementCache.clear();

        CompactXml25NamedParticipantEvidenceWriter writer = new CompactXml25NamedParticipantEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write((ParticipantEvidence)participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_shortName, output.toString());
    }

    @Test
    public void test_write_participant_fullName() throws XMLStreamException, IOException, IllegalRangeException {
        NamedEntity participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        participant.getAliases().add(new DefaultAlias(new DefaultCvTerm("synonym"), "participant synonym"));
        participant.getAliases().add(new DefaultAlias("test"));
        participant.setFullName("participant test");
        elementCache.clear();

        CompactXml25NamedParticipantEvidenceWriter writer = new CompactXml25NamedParticipantEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write((ParticipantEvidence)participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_fullName, output.toString());
    }

    @Test
    public void test_write_participant_xref() throws XMLStreamException, IOException, IllegalRangeException {
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        participant.getXrefs().add(new DefaultXref(new DefaultCvTerm("test2"), "xxxx2"));
        participant.getXrefs().add(new DefaultXref(new DefaultCvTerm("test"), "xxxx1"));
        elementCache.clear();

        CompactXml25NamedParticipantEvidenceWriter writer = new CompactXml25NamedParticipantEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_xref, output.toString());
    }

    @Test
    public void test_write_participant_feature() throws XMLStreamException, IOException, IllegalRangeException {
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        FeatureEvidence feature = new DefaultFeatureEvidence();
        feature.getRanges().add(RangeUtils.createRangeFromString("1-4"));
        participant.addFeature(feature);
        elementCache.clear();

        CompactXml25NamedParticipantEvidenceWriter writer = new CompactXml25NamedParticipantEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_feature, output.toString());
    }

    @Test
    public void test_write_participant_attributes() throws XMLStreamException, IOException, IllegalRangeException {
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        participant.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test2")));
        participant.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test3")));
        elementCache.clear();

        CompactXml25NamedParticipantEvidenceWriter writer = new CompactXml25NamedParticipantEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_attributes, output.toString());
    }

    @Test
    public void test_write_participant_stoichiometry() throws XMLStreamException, IOException, IllegalRangeException {
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        participant.setStoichiometry(1);
        elementCache.clear();

        CompactXml25NamedParticipantEvidenceWriter writer = new CompactXml25NamedParticipantEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_stoichiometry, output.toString());
    }

    @Test
    public void test_write_participant_stoichiometry_range() throws XMLStreamException, IOException, IllegalRangeException {
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        participant.setStoichiometry(new DefaultStoichiometry(1,4));
        elementCache.clear();

        CompactXml25NamedParticipantEvidenceWriter writer = new CompactXml25NamedParticipantEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_stoichiometry_range, output.toString());
    }

    @Test
    public void test_write_participant_registered() throws XMLStreamException, IOException, IllegalRangeException {
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        elementCache.clear();
        elementCache.extractIdForParticipant(new DefaultParticipant(new DefaultProtein("protein test")));
        elementCache.extractIdForParticipant(participant);

        CompactXml25NamedParticipantEvidenceWriter writer = new CompactXml25NamedParticipantEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_registered, output.toString());
    }

    @Test
    public void test_write_participant_identification() throws XMLStreamException, IOException, IllegalRangeException {
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        participant.getIdentificationMethods().add(new DefaultCvTerm("inference", "MI:0362"));
        elementCache.clear();

        CompactXml25NamedParticipantEvidenceWriter writer = new CompactXml25NamedParticipantEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_identification, output.toString());
    }

    @Test
    public void test_write_participant_exp_preparation() throws XMLStreamException, IOException, IllegalRangeException {
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        participant.getExperimentalPreparations().add(new DefaultCvTerm("sample process","MI:0342"));
        elementCache.clear();

        CompactXml25NamedParticipantEvidenceWriter writer = new CompactXml25NamedParticipantEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_exp_preparation, output.toString());
    }

    @Test
    public void test_write_participant_expressed_in() throws XMLStreamException, IOException, IllegalRangeException {
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        participant.setExpressedInOrganism(new DefaultOrganism(9606, "human"));
        elementCache.clear();

        CompactXml25NamedParticipantEvidenceWriter writer = new CompactXml25NamedParticipantEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_expressed_in, output.toString());
    }

    @Test
    public void test_write_participant_parameters() throws XMLStreamException, IOException, IllegalRangeException {
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        participant.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(5))));
        elementCache.clear();

        CompactXml25NamedParticipantEvidenceWriter writer = new CompactXml25NamedParticipantEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_parameters, output.toString());
    }

    @Test
    public void test_write_participant_confidence() throws XMLStreamException, IOException, IllegalRangeException {
        ParticipantEvidence participant = new DefaultNamedParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        participant.getConfidences().add(new DefaultConfidence(new DefaultCvTerm("intact-miscore"), "0.8"));
        elementCache.clear();

        CompactXml25NamedParticipantEvidenceWriter writer = new CompactXml25NamedParticipantEvidenceWriter(createStreamWriter(), this.elementCache);
        writer.write(participant);
        streamWriter.flush();

        Assert.assertEquals(this.participant_confidences, output.toString());
    }
}
