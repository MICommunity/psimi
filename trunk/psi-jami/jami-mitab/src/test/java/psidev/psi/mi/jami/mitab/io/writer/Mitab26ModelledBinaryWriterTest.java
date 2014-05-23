package psidev.psi.mi.jami.mitab.io.writer;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.factory.options.InteractionWriterOptions;
import psidev.psi.mi.jami.exception.IllegalParameterException;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.io.writer.Mitab26ModelledBinaryWriter;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.tab.extension.factory.options.MitabWriterOptions;
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
 * Unit tester for Mitab26ModelledBinaryWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/06/13</pre>
 */

public class Mitab26ModelledBinaryWriterTest {

    @Test
    public void test_mitab_version_and_header(){
        Mitab26ModelledBinaryWriter binaryWriter = new Mitab26ModelledBinaryWriter();
        Assert.assertEquals(MitabVersion.v2_6, binaryWriter.getVersion());
        Assert.assertTrue(binaryWriter.isWriteHeader());
    }

    @Test(expected = IllegalStateException.class)
    public void test_not_initialised_writer() {
        Mitab26ModelledBinaryWriter binaryWriter = new Mitab26ModelledBinaryWriter();
        binaryWriter.write(new MitabModelledBinaryInteraction());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_not_initialised_no_options() {
        Mitab26ModelledBinaryWriter binaryWriter = new Mitab26ModelledBinaryWriter();
        binaryWriter.initialiseContext(null);
    }

    @Test
    public void test_write_binary() throws IllegalParameterException, ParseException {
        StringWriter writer = new StringWriter();
        Mitab26ModelledBinaryWriter binaryWriter = new Mitab26ModelledBinaryWriter(writer);
        binaryWriter.setWriteHeader(false);

        ModelledBinaryInteraction binary = createModelledBinaryInteraction();

        binaryWriter.write(binary);

        String expected_line = getExpectedMitabLine();
        Assert.assertEquals(expected_line, writer.toString());
    }

    @Test
    public void test_write_binary_list() throws IllegalParameterException, ParseException {
        StringWriter writer = new StringWriter();
        Mitab26ModelledBinaryWriter binaryWriter = new Mitab26ModelledBinaryWriter(writer);
        binaryWriter.setWriteHeader(false);

        ModelledBinaryInteraction binary = createModelledBinaryInteraction();

        binaryWriter.write(Arrays.asList(binary, binary));

        String expected_line = getExpectedMitabLine();
        Assert.assertEquals(expected_line+ MitabUtils.LINE_BREAK+expected_line, writer.toString());
    }

    @Test
    public void test_write_binary2() throws IllegalParameterException, ParseException {
        StringWriter writer = new StringWriter();
        Mitab26ModelledBinaryWriter binaryWriter = new Mitab26ModelledBinaryWriter();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MitabWriterOptions.MITAB_HEADER_OPTION, false);
        options.put(InteractionWriterOptions.OUTPUT_OPTION_KEY, writer);
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
