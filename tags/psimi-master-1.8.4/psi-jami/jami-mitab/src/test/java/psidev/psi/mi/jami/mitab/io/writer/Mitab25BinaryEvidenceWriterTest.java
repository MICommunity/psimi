package psidev.psi.mi.jami.mitab.io.writer;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.io.writer.Mitab25BinaryEvidenceWriter;
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
 * Unit tester for Mitab25BinaryEvidenceWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/06/13</pre>
 */

public class Mitab25BinaryEvidenceWriterTest {

    @Test
    public void test_mitab_version_and_header(){
        Mitab25BinaryEvidenceWriter binaryWriter = new Mitab25BinaryEvidenceWriter();
        Assert.assertEquals(MitabVersion.v2_5, binaryWriter.getVersion());
        Assert.assertTrue(binaryWriter.isWriteHeader());
    }

    @Test(expected = IllegalStateException.class)
    public void test_not_initialised_writer() {
        Mitab25BinaryEvidenceWriter binaryWriter = new Mitab25BinaryEvidenceWriter();
        binaryWriter.write(new MitabBinaryInteractionEvidence());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_not_initialised_no_options() {
        Mitab25BinaryEvidenceWriter binaryWriter = new Mitab25BinaryEvidenceWriter();
        binaryWriter.initialiseContext(null);
    }

    @Test
    public void test_write_binary() throws ParseException {
        StringWriter writer = new StringWriter();
        Mitab25BinaryEvidenceWriter binaryWriter = new Mitab25BinaryEvidenceWriter(writer);
        binaryWriter.setWriteHeader(false);

        BinaryInteractionEvidence binary = createBinaryInteractionEvidence();

        binaryWriter.write(binary);

        String expected_line = getExpectedMitabLine();
        Assert.assertEquals(expected_line, writer.toString());
    }

    @Test
    public void test_write_binary_list() throws ParseException {
        StringWriter writer = new StringWriter();
        Mitab25BinaryEvidenceWriter binaryWriter = new Mitab25BinaryEvidenceWriter(writer);
        binaryWriter.setWriteHeader(false);

        BinaryInteractionEvidence binary = createBinaryInteractionEvidence();

        binaryWriter.write(Arrays.asList(binary, binary));

        String expected_line = getExpectedMitabLine();
        Assert.assertEquals(expected_line+ MitabUtils.LINE_BREAK+expected_line, writer.toString());
    }

    @Test
    public void test_write_binary2() throws ParseException {
        StringWriter writer = new StringWriter();
        Mitab25BinaryEvidenceWriter binaryWriter = new Mitab25BinaryEvidenceWriter();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MitabUtils.MITAB_HEADER_OPTION, false);
        options.put(InteractionWriterFactory.OUTPUT_OPTION_KEY, writer);
        binaryWriter.initialiseContext(options);

        BinaryInteractionEvidence binary = createBinaryInteractionEvidence();

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

    private BinaryInteractionEvidence createBinaryInteractionEvidence() throws ParseException {
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
        return binary;
    }
}
