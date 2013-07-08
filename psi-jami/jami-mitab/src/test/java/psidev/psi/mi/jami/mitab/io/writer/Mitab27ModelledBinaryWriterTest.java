package psidev.psi.mi.jami.mitab.io.writer;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.io.writer.Mitab27ModelledBinaryWriter;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.*;

import java.io.StringWriter;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit tester for Mitab27ModelledBinaryWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/06/13</pre>
 */

public class Mitab27ModelledBinaryWriterTest {

    @Test
    public void test_mitab_version_and_header(){
        Mitab27ModelledBinaryWriter binaryWriter = new Mitab27ModelledBinaryWriter();
        Assert.assertEquals(MitabVersion.v2_7, binaryWriter.getVersion());
        Assert.assertTrue(binaryWriter.isWriteHeader());
    }

    @Test(expected = IllegalStateException.class)
    public void test_not_initialised_writer() {
        Mitab27ModelledBinaryWriter binaryWriter = new Mitab27ModelledBinaryWriter();
        binaryWriter.write(new MitabModelledBinaryInteraction());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_not_initialised_no_options() {
        Mitab27ModelledBinaryWriter binaryWriter = new Mitab27ModelledBinaryWriter();
        binaryWriter.initialiseContext(null);
    }

    @Test
    public void test_write_binary() throws IllegalParameterException, ParseException {
        StringWriter writer = new StringWriter();
        Mitab27ModelledBinaryWriter binaryWriter = new Mitab27ModelledBinaryWriter(writer);
        binaryWriter.setWriteHeader(false);

        ModelledBinaryInteraction binary = createModelledBinaryInteraction();

        binaryWriter.write(binary);

        String expected_line = getExpectedMitabLine();
        Assert.assertEquals(expected_line, writer.toString());
    }

    @Test
    public void test_write_binary_list() throws IllegalParameterException, ParseException {
        StringWriter writer = new StringWriter();
        Mitab27ModelledBinaryWriter binaryWriter = new Mitab27ModelledBinaryWriter(writer);
        binaryWriter.setWriteHeader(false);

        ModelledBinaryInteraction binary = createModelledBinaryInteraction();

        binaryWriter.write(Arrays.asList(binary, binary));

        String expected_line = getExpectedMitabLine();
        Assert.assertEquals(expected_line+ MitabUtils.LINE_BREAK+expected_line, writer.toString());
    }

    @Test
    public void test_write_binary2() throws IllegalParameterException, ParseException {
        StringWriter writer = new StringWriter();
        Mitab27ModelledBinaryWriter binaryWriter = new Mitab27ModelledBinaryWriter();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MitabUtils.MITAB_HEADER_OPTION, false);
        options.put(InteractionWriterFactory.OUTPUT_OPTION_KEY, writer);
        binaryWriter.initialiseContext(options);

        ModelledBinaryInteraction binary = createModelledBinaryInteraction();

        binaryWriter.write(binary);

        String expected_line = getExpectedMitabLine();
        Assert.assertEquals(expected_line, writer.toString());
    }

    private String getExpectedMitabLine() {
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
        MitabFeature feature = new MitabFeature(new DefaultCvTerm("binding site", "binding site region", (String)null));
        feature.getRanges().add(RangeUtils.createFuzzyRange(1, 3, 6, 7));
        feature.getRanges().add(RangeUtils.createGreaterThanRange(9));
        feature.setInterpro("interpro:xxxx");
        participantA.addModelledFeature(feature);
        // stoichiometry
        participantA.setStoichiometry(2);
        participantB.setStoichiometry(5);

        ModelledBinaryInteraction binary = new MitabModelledBinaryInteraction(participantA, participantB);
        participantA.setModelledInteraction(binary);
        participantB.setModelledInteraction(binary);

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
