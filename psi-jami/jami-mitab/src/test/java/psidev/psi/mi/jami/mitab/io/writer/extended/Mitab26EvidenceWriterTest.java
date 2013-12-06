package psidev.psi.mi.jami.mitab.io.writer.extended;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.expansion.SpokeExpansion;
import psidev.psi.mi.jami.datasource.InteractionWriterOptions;
import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.io.writer.extended.Mitab26EvidenceWriter;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.tab.utils.MitabWriterOptions;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.StringWriter;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit tester for Mitab26EvidenceWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/06/13</pre>
 */

public class Mitab26EvidenceWriterTest {

    @Test
    public void test_mitab_version_and_header(){
        Mitab26EvidenceWriter binaryWriter = new Mitab26EvidenceWriter();
        Assert.assertEquals(MitabVersion.v2_6, binaryWriter.getVersion());
        Assert.assertFalse(binaryWriter.isWriteHeader());
    }

    @Test(expected = IllegalStateException.class)
    public void test_not_initialised_writer() {
        Mitab26EvidenceWriter binaryWriter = new Mitab26EvidenceWriter();
        binaryWriter.write(new MitabInteractionEvidence());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_not_initialised_no_options() {
        Mitab26EvidenceWriter binaryWriter = new Mitab26EvidenceWriter();
        binaryWriter.initialiseContext(null);
    }

    @Test
    public void test_write_binary() throws ParseException, IllegalParameterException {
        StringWriter writer = new StringWriter();
        Mitab26EvidenceWriter binaryWriter = new Mitab26EvidenceWriter(writer);
        binaryWriter.setWriteHeader(false);

        InteractionEvidence interaction = createBinaryInteractionEvidence();

        binaryWriter.write(interaction);

        String expected_line = getExpectedMitabLine();
        Assert.assertEquals(expected_line, writer.toString());
    }

    @Test
    public void test_write_binary_list() throws ParseException, IllegalParameterException {
        StringWriter writer = new StringWriter();
        Mitab26EvidenceWriter binaryWriter = new Mitab26EvidenceWriter(writer);
        binaryWriter.setWriteHeader(false);

        InteractionEvidence interaction = createBinaryInteractionEvidence();

        binaryWriter.write(Arrays.asList(interaction, interaction));

        String expected_line = getExpectedMitabLine();
        Assert.assertEquals(expected_line+ MitabUtils.LINE_BREAK+expected_line, writer.toString());
    }

    @Test
    public void test_write_binary2() throws ParseException, IllegalParameterException {
        StringWriter writer = new StringWriter();
        Mitab26EvidenceWriter binaryWriter = new Mitab26EvidenceWriter();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MitabWriterOptions.MITAB_HEADER_OPTION, false);
        options.put(InteractionWriterOptions.OUTPUT_OPTION_KEY, writer);
        options.put(InteractionWriterOptions.COMPLEX_EXPANSION_OPTION_KEY, new SpokeExpansion());
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
                "\tpsi-mi:protein1(display_short)|psi-mi:full name protein1(display_long)|mint:brca2(gene name)|intact:brca2 synonym(gene name synonym)|intact:\\\"bla\\\" author assigned name(author assigned name)" +
                "\tpsi-mi:\"MI:xxx2\"(pull down)" +
                "\tauthor1 et al.(2006)" +
                "\tpubmed:12345|imex:IM-1" +
                "\ttaxid:9606(human)|taxid:9606(Homo Sapiens)" +
                "\ttaxid:9606(human)|taxid:9606(Homo Sapiens)" +
                "\tpsi-mi:\"MI:xxxx\"(association)" +
                "\tpsi-mi:\"MI:xxx1\"(intact)" +
                "\tintact:EBI-xxxx|imex:IM-1-1" +
                "\tauthor-score:high(text)" +
                "\tpsi-mi:\"MI:1060\"(spoke expansion)" +
                "\tpsi-mi:\"MI:xxx5\"(enzyme target)" +
                "\tpsi-mi:\"MI:xxx5\"(enzyme target)" +
                "\tpsi-mi:\"MI:0496\"(bait)" +
                "\tpsi-mi:\"MI:xxx6\"(prey)" +
                "\tpsi-mi:\"MI:0326\"(protein)" +
                "\tpsi-mi:\"MI:0326\"(protein)" +
                "\tintact:EBI-x(see-also)" +
                "\tgo:\"GO:xxxxx\"(component)" +
                "\tgo:\"GO:xxxx2\"(process)" +
                "\tisoform-comment:test comment" +
                "\tcomment:\"test comment (to be reviewed)\"" +
                "\tfigure-legend:Fig 1.|imex curation" +
                "\ttaxid:-1(in vitro)" +
                "\tic50:\"5x10^(-1)\"(molar)" +
                "\t2006/06/06" +
                "\t2007/01/01" +
                "\trogid:xxxx4" +
                "\trogid:xxxx1" +
                "\trigid:xxxx3" +
                "\ttrue"+
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
                "\tauthor-score:high(text)"+
                "\tpsi-mi:\"MI:1060\"(spoke expansion)" +
                "\tpsi-mi:\"MI:xxx5\"(enzyme target)" +
                "\tpsi-mi:\"MI:xxx5\"(enzyme target)" +
                "\tpsi-mi:\"MI:0496\"(bait)" +
                "\tpsi-mi:\"MI:xxx6\"(prey)" +
                "\tpsi-mi:\"MI:0326\"(protein)" +
                "\tpsi-mi:\"MI:0326\"(protein)" +
                "\tintact:EBI-x(see-also)" +
                "\tinterpro:\"INTERPRO:xxxxx\"" +
                "\tgo:\"GO:xxxx2\"(process)" +
                "\tisoform-comment:test comment" +
                "\tcaution:sequence withdrawn from uniprot" +
                "\tfigure-legend:Fig 1.|imex curation" +
                "\ttaxid:-1(in vitro)" +
                "\tic50:\"5x10^(-1)\"(molar)" +
                "\t2006/06/06" +
                "\t2007/01/01" +
                "\trogid:xxxx4" +
                "\trogid:xxxx2" +
                "\trigid:xxxx3" +
                "\ttrue";
    }

    private InteractionEvidence createBinaryInteractionEvidence() throws ParseException, IllegalParameterException {
        ParticipantEvidence participantA = new MitabParticipantEvidence(new MitabProtein("protein1", "full name protein1"));
        // add identifiers
        participantA.getInteractor().getIdentifiers().add(XrefUtils.createUniprotIdentity("P12345"));
        participantA.getInteractor().getIdentifiers().add(XrefUtils.createUniprotSecondary("P12346"));
        participantA.getInteractor().getIdentifiers().add(XrefUtils.createXref("intact", "EBI-12345"));
        // add aliases
        participantA.getInteractor().getAliases().add(new MitabAlias("mint", "brca2", "gene name"));
        participantA.getInteractor().getAliases().add(new MitabAlias("intact", "brca2 synonym", "gene name synonym"));
        participantA.getAliases().add(new MitabAlias("intact", "\"bla\" author assigned name", "author assigned name"));
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
        participantC.setExperimentalRole(new MitabCvTerm("bait", Participant.BAIT_ROLE_MI));
        // biological roles
        participantA.setBiologicalRole(CvTermUtils.createMICvTerm("enzyme target", "MI:xxx5"));
        participantB.setBiologicalRole(CvTermUtils.createMICvTerm("enzyme target", "MI:xxx5"));
        participantC.setBiologicalRole(CvTermUtils.createMICvTerm("enzyme target", "MI:xxx5"));
        // experimental roles
        participantA.setExperimentalRole(CvTermUtils.createMICvTerm("prey", "MI:xxx6"));
        participantB.setExperimentalRole(CvTermUtils.createMICvTerm("prey", "MI:xxx6"));
        // xrefs
        participantA.getXrefs().add(new MitabXref("go", "GO:xxxxx", "component"));
        participantB.getInteractor().getXrefs().add(new MitabXref("interpro", "INTERPRO:xxxxx"));
        participantC.getXrefs().add(new MitabXref("intact","EBI-x","see-also"));
        // annotations
        participantA.getAnnotations().add(new MitabAnnotation("comment", "test comment (to be reviewed)"));
        participantB.getInteractor().getAnnotations().add(new MitabAnnotation("caution", "sequence withdrawn from uniprot"));
        participantC.getAnnotations().add(new MitabAnnotation("isoform-comment", "test comment"));
        // checksum
        participantA.getInteractor().getChecksums().add(ChecksumUtils.createRogid("xxxx1"));
        participantB.getInteractor().getChecksums().add(ChecksumUtils.createRogid("xxxx2"));
        participantC.getInteractor().getChecksums().add(ChecksumUtils.createRogid("xxxx4"));

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
        interaction.getConfidences().add(new MitabConfidence("author-score", "high", "text"));
        // xrefs
        interaction.getXrefs().add(new MitabXref("go", "GO:xxxx2", "process"));
        // annotations
        interaction.getAnnotations().add(new MitabAnnotation("figure-legend", "Fig 1."));
        // parameters
        interaction.getParameters().add(new MitabParameter("ic50", "5x10^(-1)", "molar"));
        // creation date
        interaction.setCreatedDate(MitabUtils.DATE_FORMAT.parse("2006/06/06"));
        // update date
        interaction.setUpdatedDate(MitabUtils.DATE_FORMAT.parse("2007/01/01"));
        // checksum
        interaction.getChecksums().add(ChecksumUtils.createRigid("xxxx3"));
        // host organism
        interaction.getExperiment().setHostOrganism(new MitabOrganism(-1, "in vitro"));
        // negative
        interaction.setNegative(true);
        return interaction;
    }
}
