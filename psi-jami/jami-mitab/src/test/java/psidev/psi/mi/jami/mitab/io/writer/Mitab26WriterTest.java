package psidev.psi.mi.jami.mitab.io.writer;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.expansion.SpokeExpansion;
import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.io.writer.Mitab26Writer;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.StringWriter;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit tester for Mitab26Writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/06/13</pre>
 */

public class Mitab26WriterTest {

    @Test
    public void test_mitab_version_and_header(){
        Mitab26Writer binaryWriter = new Mitab26Writer();
        Assert.assertEquals(MitabVersion.v2_6, binaryWriter.getVersion());
        Assert.assertFalse(binaryWriter.isWriteHeader());
    }

    @Test(expected = IllegalStateException.class)
    public void test_not_initialised_writer() {
        Mitab26Writer binaryWriter = new Mitab26Writer();
        binaryWriter.write(new MitabModelledBinaryInteraction());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_not_initialised_no_options() {
        Mitab26Writer binaryWriter = new Mitab26Writer();
        binaryWriter.initialiseContext(null);
    }

    @Test
    public void test_write_binary() throws IllegalParameterException, ParseException {
        StringWriter writer = new StringWriter();
        Mitab26Writer binaryWriter = new Mitab26Writer(writer);
        binaryWriter.setWriteHeader(false);

        ModelledInteraction binary = createModelledBinaryInteraction();

        binaryWriter.write(binary);

        String expected_line = getExpectedMitabLine1();
        Assert.assertEquals(expected_line, writer.toString());
    }

    @Test
    public void test_write_binary_list() throws ParseException, IllegalParameterException {
        StringWriter writer = new StringWriter();
        Mitab26Writer binaryWriter = new Mitab26Writer(writer);
        binaryWriter.setWriteHeader(false);

        ModelledInteraction binary = createModelledBinaryInteraction();

        InteractionEvidence binary2 = createBinaryInteractionEvidence();

        binaryWriter.write(Arrays.asList((Interaction<? extends Participant>) binary, (Interaction<? extends Participant>) binary2));

        String expected_line = getExpectedMitabLine1();
        String expected_line2 = getExpectedMitabLine2();
        Assert.assertEquals(expected_line+ MitabUtils.LINE_BREAK+expected_line2, writer.toString());
    }

    @Test
    public void test_write_binary2() throws ParseException, IllegalParameterException {
        StringWriter writer = new StringWriter();
        Mitab26Writer binaryWriter = new Mitab26Writer();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MitabUtils.MITAB_HEADER_OPTION, false);
        options.put(InteractionWriterFactory.OUTPUT_OPTION_KEY, writer);
        options.put(InteractionWriterFactory.COMPLEX_EXPANSION_OPTION_KEY, new SpokeExpansion());
        binaryWriter.initialiseContext(options);

        InteractionEvidence binary = createBinaryInteractionEvidence();

        String expected_line = getExpectedMitabLine2();

        binaryWriter.write(binary);

        Assert.assertEquals(expected_line, writer.toString());
    }

    private String getExpectedMitabLine1() {
        return "uniprotkb:P12349" +
                "\tuniprotkb:P12345" +
                "\tuniprotkb:P12350|intact:EBI-12347" +
                "\tuniprotkb:P12346|intact:EBI-12345" +
                "\tpsi-mi:protein3(display_short)|psi-mi:full name protein3(display_long)" +
                "\tpsi-mi:protein1(display_short)|psi-mi:full name protein1(display_long)|uniprotkb:brca2(gene name)|uniprotkb:brca2 synonym(gene name synonym)|intact:\\\"bla\\\" author assigned name(author assigned name)" +
                "\t-" +
                "\t-" +
                "\t-" +
                "\ttaxid:9606(human)|taxid:9606(Homo Sapiens)" +
                "\ttaxid:9606(human)|taxid:9606(Homo Sapiens)" +
                "\tpsi-mi:\"MI:xxxx\"(association)" +
                "\tpsi-mi:\"MI:xxx1\"(intact)" +
                "\tintact:EBI-xxxx" +
                "\tauthor-score:high" +
                "\tpsi-mi:\"MI:1060\"(spoke expansion)" +
                "\tpsi-mi:\"MI:0501\"(enzyme)" +
                "\tpsi-mi:\"MI:xxx5\"(enzyme target)" +
                "\t-" +
                "\t-" +
                "\tpsi-mi:\"MI:0326\"(protein)" +
                "\tpsi-mi:\"MI:0326\"(protein)" +
                "\tintact:EBI-x(see-also)" +
                "\tgo:\"GO:xxxxx\"(component)" +
                "\tgo:\"GO:xxxx2\"(process)" +
                "\tisoform-comment:test comment" +
                "\tcomment:\"test comment (to be reviewed)\"" +
                "\tfigure-legend:Fig 1." +
                "\t-" +
                "\tic50:\"5x10^(-1)\"(molar)" +
                "\t2006/06/06" +
                "\t2007/01/01" +
                "\trogid:xxxx4" +
                "\trogid:xxxx1" +
                "\trigid:xxxx3" +
                "\t-" +
                "\n"+
                "uniprotkb:P12349" +
                "\tuniprotkb:P12347" +
                "\tuniprotkb:P12350|intact:EBI-12347" +
                "\tuniprotkb:P12348|intact:EBI-12346" +
                "\tpsi-mi:protein3(display_short)|psi-mi:full name protein3(display_long)" +
                "\tpsi-mi:protein2(display_short)|psi-mi:full name protein2(display_long)" +
                "\t-" +
                "\t-" +
                "\t-" +
                "\ttaxid:9606(human)|taxid:9606(Homo Sapiens)" +
                "\ttaxid:9606(human)|taxid:9606(Homo Sapiens)" +
                "\tpsi-mi:\"MI:xxxx\"(association)" +
                "\tpsi-mi:\"MI:xxx1\"(intact)" +
                "\tintact:EBI-xxxx" +
                "\tauthor-score:high" +
                "\tpsi-mi:\"MI:1060\"(spoke expansion)" +
                "\tpsi-mi:\"MI:0501\"(enzyme)" +
                "\tpsi-mi:\"MI:xxx5\"(enzyme target)" +
                "\t-" +
                "\t-" +
                "\tpsi-mi:\"MI:0326\"(protein)" +
                "\tpsi-mi:\"MI:0326\"(protein)" +
                "\tintact:EBI-x(see-also)" +
                "\tinterpro:\"INTERPRO:xxxxx\"" +
                "\tgo:\"GO:xxxx2\"(process)" +
                "\tisoform-comment:test comment" +
                "\tcaution:sequence withdrawn from uniprot" +
                "\tfigure-legend:Fig 1." +
                "\t-" +
                "\tic50:\"5x10^(-1)\"(molar)" +
                "\t2006/06/06" +
                "\t2007/01/01" +
                "\trogid:xxxx4" +
                "\trogid:xxxx2" +
                "\trigid:xxxx3" +
                "\t-";
    }

    private String getExpectedMitabLine2() {
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
                "\tauthor-score:high"+
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
        interaction.getConfidences().add(new MitabConfidence("author-score", "high", null));
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

    private ModelledInteraction createModelledBinaryInteraction() throws ParseException, IllegalParameterException {
        ModelledParticipant participantA = new MitabModelledParticipant(new MitabProtein("protein1", "full name protein1"));
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
        ModelledParticipant participantB = new MitabModelledParticipant(new MitabProtein("protein2", "full name protein2"));
        // add identifiers
        participantB.getInteractor().getIdentifiers().add(XrefUtils.createUniprotIdentity("P12347"));
        participantB.getInteractor().getIdentifiers().add(XrefUtils.createUniprotSecondary("P12348"));
        participantB.getInteractor().getIdentifiers().add(XrefUtils.createXref("intact", "EBI-12346"));
        // species
        participantB.getInteractor().setOrganism(new MitabOrganism(9606, "human", "Homo Sapiens"));
        ModelledParticipant participantC = new MitabModelledParticipant(new MitabProtein("protein3", "full name protein3"));
        // add identifiers
        participantC.getInteractor().getIdentifiers().add(XrefUtils.createUniprotIdentity("P12349"));
        participantC.getInteractor().getIdentifiers().add(XrefUtils.createUniprotSecondary("P12350"));
        participantC.getInteractor().getIdentifiers().add(XrefUtils.createXref("intact", "EBI-12347"));
        // species
        participantC.getInteractor().setOrganism(new MitabOrganism(9606, "human", "Homo Sapiens"));
        // participant C is the spoke expansion bait
        participantC.setBiologicalRole(new MitabCvTerm("enzyme","MI:0501"));
        // biological roles
        participantA.setBiologicalRole(CvTermUtils.createMICvTerm("enzyme target", "MI:xxx5"));
        participantB.setBiologicalRole(CvTermUtils.createMICvTerm("enzyme target", "MI:xxx5"));
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

        ModelledInteraction interaction = new MitabModelledInteraction();
        interaction.addParticipant(participantA);
        interaction.addParticipant(participantB);
        interaction.addParticipant(participantC);

        // interaction type
        interaction.setInteractionType(CvTermUtils.createMICvTerm("association", "MI:xxxx"));
        // source database
        MitabSource source = new MitabSource("intact");
        source.setMIIdentifier("MI:xxx1");
        interaction.setSource(source);
        // interaction identifiers
        interaction.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxxx"));
        // confidences
        interaction.getModelledConfidences().add(new MitabConfidence("author-score", "high", null));
        // xrefs
        interaction.getXrefs().add(new MitabXref("go", "GO:xxxx2", "process"));
        // annotations
        interaction.getAnnotations().add(new MitabAnnotation("figure-legend", "Fig 1."));
        // parameters
        interaction.getModelledParameters().add(new MitabParameter("ic50", "5x10^(-1)", "molar"));
        // creation date
        interaction.setCreatedDate(MitabUtils.DATE_FORMAT.parse("2006/06/06"));
        // update date
        interaction.setUpdatedDate(MitabUtils.DATE_FORMAT.parse("2007/01/01"));
        // checksum
        interaction.getChecksums().add(ChecksumUtils.createRigid("xxxx3"));
        return interaction;
    }
}
