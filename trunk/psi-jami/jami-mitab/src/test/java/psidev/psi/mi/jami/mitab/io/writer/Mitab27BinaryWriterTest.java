package psidev.psi.mi.jami.mitab.io.writer;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.factory.options.InteractionWriterOptions;
import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.io.writer.Mitab27BinaryWriter;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.tab.extension.factory.options.MitabWriterOptions;
import psidev.psi.mi.jami.utils.*;

import java.io.StringWriter;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit tester for Mitab27BinaryWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/06/13</pre>
 */

public class Mitab27BinaryWriterTest {

    @Test
    public void test_mitab_version_and_header(){
        Mitab27BinaryWriter binaryWriter = new Mitab27BinaryWriter();
        Assert.assertEquals(MitabVersion.v2_7, binaryWriter.getVersion());
        Assert.assertTrue(binaryWriter.isWriteHeader());
    }

    @Test(expected = IllegalStateException.class)
    public void test_not_initialised_writer() {
        Mitab27BinaryWriter binaryWriter = new Mitab27BinaryWriter();
        binaryWriter.write(new MitabModelledBinaryInteraction());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_not_initialised_no_options() {
        Mitab27BinaryWriter binaryWriter = new Mitab27BinaryWriter();
        binaryWriter.initialiseContext(null);
    }

    @Test
    public void test_write_binary() throws IllegalParameterException, ParseException {
        StringWriter writer = new StringWriter();
        Mitab27BinaryWriter binaryWriter = new Mitab27BinaryWriter(writer);
        binaryWriter.setWriteHeader(false);

        ModelledBinaryInteraction binary = createModelledBinaryInteraction();

        binaryWriter.write(binary);

        String expected_line = getExpectedMitabLine1();
        Assert.assertEquals(expected_line, writer.toString());
    }

    @Test
    public void test_write_binary_list() throws ParseException, IllegalParameterException {
        StringWriter writer = new StringWriter();
        Mitab27BinaryWriter binaryWriter = new Mitab27BinaryWriter(writer);
        binaryWriter.setWriteHeader(false);

        ModelledBinaryInteraction binary = createModelledBinaryInteraction();

        BinaryInteractionEvidence binary2 = createBinaryInteractionEvidence();

        binaryWriter.write(Arrays.asList((BinaryInteraction) binary, (BinaryInteraction) binary2));

        String expected_line = getExpectedMitabLine1();
        String expected_line2 = getExpectedMitabLine2();
        Assert.assertEquals(expected_line+ MitabUtils.LINE_BREAK+expected_line2, writer.toString());
    }

    @Test
    public void test_write_binary2() throws ParseException, IllegalParameterException {
        StringWriter writer = new StringWriter();
        Mitab27BinaryWriter binaryWriter = new Mitab27BinaryWriter();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MitabWriterOptions.MITAB_HEADER_OPTION, false);
        options.put(InteractionWriterOptions.OUTPUT_OPTION_KEY, writer);
        binaryWriter.initialiseContext(options);

        BinaryInteractionEvidence binary = createBinaryInteractionEvidence();

        String expected_line = getExpectedMitabLine2();

        binaryWriter.write(binary);

        Assert.assertEquals(expected_line, writer.toString());
    }

    private String getExpectedMitabLine1() {
        return "uniprotkb:P12345" +
                "\tuniprotkb:P12347" +
                "\tuniprotkb:P12346|intact:EBI-12345" +
                "\tuniprotkb:P12348|intact:EBI-12346" +
                "\tpsi-mi:protein1(display_short)|psi-mi:full name protein1(display_long)|uniprotkb:brca2(gene name)|uniprotkb:brca2 synonym(gene name synonym)|intact:\\\"bla\\\" author assigned name(author assigned name)" +
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
                "\tgo:\"GO:xxxxx\"(component)" +
                "\tinterpro:\"INTERPRO:xxxxx\"" +
                "\tgo:\"GO:xxxx2\"(process)" +
                "\tcomment:\"test comment (to be reviewed)\"" +
                "\tcaution:sequence withdrawn from uniprot" +
                "\tfigure-legend:Fig 1." +
                "\t-" +
                "\tic50:\"5x10^(-1)\"(molar)" +
                "\t2006/06/06" +
                "\t2007/01/01" +
                "\trogid:xxxx1" +
                "\trogid:xxxx2" +
                "\trigid:xxxx3" +
                "\t-" +
                "\tbinding site region:1..3-6..7,>9->9(\"interpro:xxxx\")" +
                "\t-" +
                "\t2" +
                "\t5" +
                "\t-" +
                "\t-";
    }

    private String getExpectedMitabLine2() {
        return "uniprotkb:P12345" +
                "\tuniprotkb:P12347" +
                "\tuniprotkb:P12346|intact:EBI-12345" +
                "\tuniprotkb:P12348|intact:EBI-12346" +
                "\tpsi-mi:protein1(display_short)|psi-mi:full name protein1(display_long)|uniprotkb:brca2(gene name)|uniprotkb:brca2 synonym(gene name synonym)|intact:\\\"bla\\\" author assigned name(author assigned name)" +
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
                "\tpsi-mi:\"MI:0501\"(enzyme)" +
                "\tpsi-mi:\"MI:xxx5\"(enzyme target)" +
                "\tpsi-mi:\"MI:0496\"(bait)" +
                "\tpsi-mi:\"MI:xxx6\"(prey)" +
                "\tpsi-mi:\"MI:0326\"(protein)" +
                "\tpsi-mi:\"MI:0326\"(protein)" +
                "\tgo:\"GO:xxxxx\"(component)" +
                "\tinterpro:\"INTERPRO:xxxxx\"" +
                "\tgo:\"GO:xxxx2\"(process)" +
                "\tcomment:\"test comment (to be reviewed)\"" +
                "\tcaution:sequence withdrawn from uniprot" +
                "\tfigure-legend:Fig 1.|imex curation" +
                "\ttaxid:-1(in vitro)" +
                "\tic50:\"5x10^(-1)\"(molar)" +
                "\t2006/06/06" +
                "\t2007/01/01" +
                "\trogid:xxxx1" +
                "\trogid:xxxx2" +
                "\trigid:xxxx3" +
                "\ttrue"+
                "\tbinding site region:1..3-6..7,>9->9(\"interpro:xxxx\")" +
                "\t-" +
                "\t2" +
                "\t5" +
                "\tpsi-mi:\"MI:xxxx1\"(western blot)" +
                "\tpsi-mi:\"MI:xxxx2\"(predetermined)";
    }

    private BinaryInteractionEvidence createBinaryInteractionEvidence() throws ParseException, IllegalParameterException {
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
        // biological roles
        participantA.setBiologicalRole(CvTermUtils.createMICvTerm(Participant.ENZYME_ROLE, Participant.ENZYME_ROLE_MI));
        participantB.setBiologicalRole(CvTermUtils.createMICvTerm("enzyme target", "MI:xxx5"));
        // experimental roles
        participantA.setExperimentalRole(CvTermUtils.createMICvTerm(Participant.BAIT_ROLE, Participant.BAIT_ROLE_MI));
        participantB.setExperimentalRole(CvTermUtils.createMICvTerm("prey", "MI:xxx6"));
        // xrefs
        participantA.getXrefs().add(new MitabXref("go", "GO:xxxxx", "component"));
        participantB.getInteractor().getXrefs().add(new MitabXref("interpro", "INTERPRO:xxxxx"));
        // annotations
        participantA.getAnnotations().add(new MitabAnnotation("comment", "test comment (to be reviewed)"));
        participantB.getInteractor().getAnnotations().add(new MitabAnnotation("caution", "sequence withdrawn from uniprot"));
        // checksum
        participantA.getInteractor().getChecksums().add(ChecksumUtils.createRogid("xxxx1"));
        participantB.getInteractor().getChecksums().add(ChecksumUtils.createRogid("xxxx2"));
        // features
        MitabFeatureEvidence feature = new MitabFeatureEvidence(new DefaultCvTerm("binding site", "binding site region", (String)null));
        feature.getRanges().add(RangeUtils.createFuzzyRange(1, 3, 6, 7));
        feature.getRanges().add(RangeUtils.createGreaterThanRange(9));
        feature.setInterpro("interpro:xxxx");
        participantA.addFeature(feature);
        // stc
        participantA.setStoichiometry(2);
        participantB.setStoichiometry(5);
        // participant identification method
        participantA.getIdentificationMethods().add(new MitabCvTerm("western blot", "MI:xxxx1"));
        participantB.getIdentificationMethods().add(new MitabCvTerm("predetermined", "MI:xxxx2"));

        BinaryInteractionEvidence binary = new MitabBinaryInteractionEvidence(participantA, participantB);
        participantA.setInteraction(binary);
        participantB.setInteraction(binary);

        // detection method
        binary.setExperiment(new MitabExperiment(new MitabPublication()));
        binary.getExperiment().setInteractionDetectionMethod(new MitabCvTerm("pull down", "MI:xxx2"));
        // first author
        binary.getExperiment().getPublication().setPublicationDate(MitabUtils.PUBLICATION_YEAR_FORMAT.parse("2006"));
        binary.getExperiment().getPublication().getAuthors().add("author1");
        binary.getExperiment().getPublication().getAuthors().add("author2");
        // publication identifiers
        binary.getExperiment().getPublication().setPubmedId("12345");
        binary.getExperiment().getPublication().assignImexId("IM-1");
        // interaction type
        binary.setInteractionType(CvTermUtils.createMICvTerm("association", "MI:xxxx"));
        // source database
        MitabSource source = new MitabSource("intact");
        source.setMIIdentifier("MI:xxx1");
        binary.getExperiment().getPublication().setSource(source);
        // interaction identifiers
        binary.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxxx"));
        binary.getIdentifiers().add(XrefUtils.createXrefWithQualifier("imex", "IM-1-1", "imex-primary"));
        // confidences
        binary.getConfidences().add(new MitabConfidence("author-score", "high", null));
        // expansion
        binary.setComplexExpansion(CvTermUtils.createMICvTerm(ComplexExpansionMethod.SPOKE_EXPANSION, ComplexExpansionMethod.SPOKE_EXPANSION_MI));
        // xrefs
        binary.getXrefs().add(new MitabXref("go", "GO:xxxx2", "process"));
        // annotations
        binary.getAnnotations().add(new MitabAnnotation("figure-legend", "Fig 1."));
        // parameters
        binary.getParameters().add(new MitabParameter("ic50", "5x10^(-1)", "molar"));
        // creation date
        binary.setCreatedDate(MitabUtils.DATE_FORMAT.parse("2006/06/06"));
        // update date
        binary.setUpdatedDate(MitabUtils.DATE_FORMAT.parse("2007/01/01"));
        // checksum
        binary.getChecksums().add(ChecksumUtils.createRigid("xxxx3"));
        // host organism
        binary.getExperiment().setHostOrganism(new MitabOrganism(-1, "in vitro"));
        // negative
        binary.setNegative(true);
        return binary;
    }

    private ModelledBinaryInteraction createModelledBinaryInteraction() throws IllegalParameterException, ParseException {
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
        // biological roles
        participantA.setBiologicalRole(CvTermUtils.createMICvTerm(Participant.ENZYME_ROLE, Participant.ENZYME_ROLE_MI));
        participantB.setBiologicalRole(CvTermUtils.createMICvTerm("enzyme target", "MI:xxx5"));
        // xrefs
        participantA.getXrefs().add(new MitabXref("go", "GO:xxxxx", "component"));
        participantB.getInteractor().getXrefs().add(new MitabXref("interpro", "INTERPRO:xxxxx"));
        // annotations
        participantA.getAnnotations().add(new MitabAnnotation("comment", "test comment (to be reviewed)"));
        participantB.getInteractor().getAnnotations().add(new MitabAnnotation("caution", "sequence withdrawn from uniprot"));
        // checksum
        participantA.getInteractor().getChecksums().add(ChecksumUtils.createRogid("xxxx1"));
        participantB.getInteractor().getChecksums().add(ChecksumUtils.createRogid("xxxx2"));
        // features
        MitabModelledFeature feature = new MitabModelledFeature(new DefaultCvTerm("binding site", "binding site region", (String)null));
        feature.getRanges().add(RangeUtils.createFuzzyRange(1, 3, 6, 7));
        feature.getRanges().add(RangeUtils.createGreaterThanRange(9));
        feature.setInterpro("interpro:xxxx");
        participantA.addFeature(feature);
        // stoichiometry
        participantA.setStoichiometry(2);
        participantB.setStoichiometry(5);

        ModelledBinaryInteraction binary = new MitabModelledBinaryInteraction(participantA, participantB);
        participantA.setInteraction(binary);
        participantB.setInteraction(binary);

        // interaction type
        binary.setInteractionType(CvTermUtils.createMICvTerm("association", "MI:xxxx"));
        // source database
        MitabSource source = new MitabSource("intact");
        source.setMIIdentifier("MI:xxx1");
        binary.setSource(source);
        // interaction identifiers
        binary.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxxx"));
        // confidences
        binary.getModelledConfidences().add(new MitabConfidence("author-score", "high", null));
        // expansion
        binary.setComplexExpansion(CvTermUtils.createMICvTerm(ComplexExpansionMethod.SPOKE_EXPANSION, ComplexExpansionMethod.SPOKE_EXPANSION_MI));
        // xrefs
        binary.getXrefs().add(new MitabXref("go", "GO:xxxx2", "process"));
        // annotations
        binary.getAnnotations().add(new MitabAnnotation("figure-legend", "Fig 1."));
        // parameters
        binary.getModelledParameters().add(new MitabParameter("ic50", "5x10^(-1)", "molar"));
        // creation date
        binary.setCreatedDate(MitabUtils.DATE_FORMAT.parse("2006/06/06"));
        // update date
        binary.setUpdatedDate(MitabUtils.DATE_FORMAT.parse("2007/01/01"));
        // checksum
        binary.getChecksums().add(ChecksumUtils.createRigid("xxxx3"));

        return binary;
    }
}
