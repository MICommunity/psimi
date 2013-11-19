package psidev.psi.mi.jami.mitab.io.writer;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.expansion.SpokeExpansion;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.io.writer.Mitab25EvidenceWriter;
import psidev.psi.mi.jami.tab.io.writer.Mitab25EvidenceWriter;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.StringWriter;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit tester for Mitab25EvidenceWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/06/13</pre>
 */

public class Mitab25EvidenceWriterTest {

    @Test
    public void test_mitab_version_and_header(){
        Mitab25EvidenceWriter binaryWriter = new Mitab25EvidenceWriter();
        Assert.assertEquals(MitabVersion.v2_5, binaryWriter.getVersion());
        Assert.assertFalse(binaryWriter.isWriteHeader());
    }

    @Test(expected = IllegalStateException.class)
    public void test_not_initialised_writer() {
        Mitab25EvidenceWriter binaryWriter = new Mitab25EvidenceWriter();
        binaryWriter.write(new MitabInteractionEvidence());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_not_initialised_no_options() {
        Mitab25EvidenceWriter binaryWriter = new Mitab25EvidenceWriter();
        binaryWriter.initialiseContext(null);
    }

    @Test
    public void test_write_binary() throws ParseException {
        StringWriter writer = new StringWriter();
        Mitab25EvidenceWriter binaryWriter = new Mitab25EvidenceWriter(writer);
        binaryWriter.setWriteHeader(false);

        InteractionEvidence interaction = createBinaryInteractionEvidence();

        binaryWriter.write(interaction);

        String expected_line = getExpectedMitabLine();
        Assert.assertEquals(expected_line, writer.toString());
    }

    @Test
    public void test_write_binary_list() throws ParseException {
        StringWriter writer = new StringWriter();
        Mitab25EvidenceWriter binaryWriter = new Mitab25EvidenceWriter(writer);
        binaryWriter.setWriteHeader(false);

        InteractionEvidence interaction = createBinaryInteractionEvidence();

        binaryWriter.write(Arrays.asList(interaction, interaction));

        String expected_line = getExpectedMitabLine();
        Assert.assertEquals(expected_line+ MitabUtils.LINE_BREAK+expected_line, writer.toString());
    }

    @Test
    public void test_write_binary2() throws ParseException {
        StringWriter writer = new StringWriter();
        Mitab25EvidenceWriter binaryWriter = new Mitab25EvidenceWriter();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MitabUtils.MITAB_HEADER_OPTION, false);
        options.put(InteractionWriterFactory.OUTPUT_OPTION_KEY, writer);
        options.put(InteractionWriterFactory.COMPLEX_EXPANSION_OPTION_KEY, new SpokeExpansion());
        binaryWriter.initialiseContext(options);

        InteractionEvidence interaction = createBinaryInteractionEvidence();

        binaryWriter.write(interaction);

        String expected_line = getExpectedMitabLine();
        Assert.assertEquals(expected_line, writer.toString());
    }

    private String getExpectedMitabLine() {
        return "uniprotkb:P12349" +
                    "\tuniprotkb:P12345" +
                    "\tuniprotkb:P12350|intact:EBI-12347" +
                    "\tuniprotkb:P12346|intact:EBI-12345" +
                    "\tpsi-mi:protein3(display_short)|psi-mi:full name protein3(display_long)" +
                    "\tpsi-mi:protein1(display_short)|psi-mi:full name protein1(display_long)|uniprotkb:brca2(gene name)|uniprotkb:brca2 synonym(gene name synonym)|intact:\\\"bla\\\" author assigned name(author assigned name)" +
                    "\tpsi-mi:\"MI:xxx2\"(pull down)" +
                    "\tauthor1 et al.(2006)" +
                    "\tpubmed:12345|imex:IM-1" +
                    "\ttaxid:9606(human)|taxid:9606(Homo Sapiens)" +
                    "\ttaxid:9606(human)|taxid:9606(Homo Sapiens)" +
                    "\tpsi-mi:\"MI:xxxx\"(association)" +
                    "\tpsi-mi:\"MI:xxx1\"(intact)" +
                    "\tintact:EBI-xxxx|imex:IM-1-1" +
                    "\tauthor-score:high" +
                "\n"+
                "uniprotkb:P12349" +
                "\tuniprotkb:P12347" +
                "\tuniprotkb:P12350|intact:EBI-12347" +
                "\tuniprotkb:P12348|intact:EBI-12346" +
                "\tpsi-mi:protein3(display_short)|psi-mi:full name protein3(display_long)" +
                "\tpsi-mi:protein2(display_short)|psi-mi:full name protein2(display_long)" +
                "\tpsi-mi:\"MI:xxx2\"(pull down)" +
                "\tauthor1 et al.(2006)" +
                "\tpubmed:12345|imex:IM-1" +
                "\ttaxid:9606(human)|taxid:9606(Homo Sapiens)" +
                "\ttaxid:9606(human)|taxid:9606(Homo Sapiens)" +
                "\tpsi-mi:\"MI:xxxx\"(association)" +
                "\tpsi-mi:\"MI:xxx1\"(intact)" +
                "\tintact:EBI-xxxx|imex:IM-1-1" +
                "\tauthor-score:high";
    }

    private InteractionEvidence createBinaryInteractionEvidence() throws ParseException {
        ParticipantEvidence participantA = new MitabParticipantEvidence(new MitabProtein("protein1", "full name protein1"));
        // add identifiers
        participantA.getInteractor().getIdentifiers().add(XrefUtils.createUniprotIdentity("P12345"));
        participantA.getInteractor().getIdentifiers().add(XrefUtils.createUniprotSecondary("P12346"));
        participantA.getInteractor().getIdentifiers().add(XrefUtils.createXref("intact", "EBI-12345"));
        // add aliases
        participantA.getInteractor().getAliases().add(AliasUtils.createGeneName("brca2"));
        participantA.getInteractor().getAliases().add(AliasUtils.createGeneNameSynonym("brca2 synonym"));
        participantA.getAliases().add(AliasUtils.createAuthorAssignedName("\"bla\" author assigned name"));
        // species
        participantA.getInteractor().setOrganism(new MitabOrganism(9606, "human", "Homo Sapiens"));
        //participantA.getAliases()
        ParticipantEvidence participantB = new MitabParticipantEvidence(new MitabProtein("protein2", "full name protein2"));
        // add identifiers
        participantB.getInteractor().getIdentifiers().add(XrefUtils.createUniprotIdentity("P12347"));
        participantB.getInteractor().getIdentifiers().add(XrefUtils.createUniprotSecondary("P12348"));
        participantB.getInteractor().getIdentifiers().add(XrefUtils.createXref("intact", "EBI-12346"));
        // species
        participantB.getInteractor().setOrganism(new MitabOrganism(9606, "human", "Homo Sapiens"));
        ParticipantEvidence participantC = new MitabParticipantEvidence(new MitabProtein("protein3", "full name protein3"));
        // add identifiers
        participantC.getInteractor().getIdentifiers().add(XrefUtils.createUniprotIdentity("P12349"));
        participantC.getInteractor().getIdentifiers().add(XrefUtils.createUniprotSecondary("P12350"));
        participantC.getInteractor().getIdentifiers().add(XrefUtils.createXref("intact", "EBI-12347"));
        // species
        participantC.getInteractor().setOrganism(new MitabOrganism(9606, "human", "Homo Sapiens"));
        // participant C is the spoke expansion bait
        participantC.setExperimentalRole(new MitabCvTerm("bait"));

        InteractionEvidence interaction = new MitabInteractionEvidence();
        interaction.addParticipant(participantA);
        interaction.addParticipant(participantB);
        interaction.addParticipant(participantC);

        // detection method
        interaction.setExperiment(new MitabExperiment(new MitabPublication()));
        interaction.getExperiment().setInteractionDetectionMethod(new MitabCvTerm("pull down", "MI:xxx2"));
        // first author
        interaction.getExperiment().getPublication().setPublicationDate(MitabUtils.PUBLICATION_YEAR_FORMAT.parse("2006"));
        interaction.getExperiment().getPublication().getAuthors().add("author1");
        interaction.getExperiment().getPublication().getAuthors().add("author2");
        // publication identifiers
        interaction.getExperiment().getPublication().setPubmedId("12345");
        interaction.getExperiment().getPublication().assignImexId("IM-1");
        // interaction type
        interaction.setInteractionType(CvTermUtils.createMICvTerm("association", "MI:xxxx"));
        // source database
        MitabSource source = new MitabSource("intact");
        source.setMIIdentifier("MI:xxx1");
        interaction.getExperiment().getPublication().setSource(source);
        // interaction identifiers
        interaction.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxxx"));
        interaction.getIdentifiers().add(XrefUtils.createXrefWithQualifier("imex", "IM-1-1", "imex-primary"));
        // confidences
        interaction.getConfidences().add(new MitabConfidence("author-score", "high", null));
        return interaction;
    }
}
