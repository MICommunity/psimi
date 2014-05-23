package psidev.psi.mi.jami.mitab.io.writer.extended;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.factory.options.InteractionWriterOptions;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.io.writer.extended.Mitab25ModelledBinaryWriter;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.tab.extension.factory.options.MitabWriterOptions;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit tester for Mitab25ModelledBinaryWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/06/13</pre>
 */

public class Mitab25ModelledBinaryWriterTest {

    @Test
    public void test_mitab_version_and_header(){
        Mitab25ModelledBinaryWriter binaryWriter = new Mitab25ModelledBinaryWriter();
        Assert.assertEquals(MitabVersion.v2_5, binaryWriter.getVersion());
        Assert.assertTrue(binaryWriter.isWriteHeader());
    }

    @Test(expected = IllegalStateException.class)
    public void test_not_initialised_writer() {
        Mitab25ModelledBinaryWriter binaryWriter = new Mitab25ModelledBinaryWriter();
        binaryWriter.write(new MitabModelledBinaryInteraction());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_not_initialised_no_options() {
        Mitab25ModelledBinaryWriter binaryWriter = new Mitab25ModelledBinaryWriter();
        binaryWriter.initialiseContext(null);
    }

    @Test
    public void test_write_binary() {
        StringWriter writer = new StringWriter();
        Mitab25ModelledBinaryWriter binaryWriter = new Mitab25ModelledBinaryWriter(writer);
        binaryWriter.setWriteHeader(false);

        ModelledBinaryInteraction binary = createModelledBinaryInteraction();

        binaryWriter.write(binary);

        String expected_line = getExpectedMitabLine();
        Assert.assertEquals(expected_line, writer.toString());
    }

    @Test
    public void test_write_binary_list() {
        StringWriter writer = new StringWriter();
        Mitab25ModelledBinaryWriter binaryWriter = new Mitab25ModelledBinaryWriter(writer);
        binaryWriter.setWriteHeader(false);

        ModelledBinaryInteraction binary = createModelledBinaryInteraction();

        binaryWriter.write(Arrays.asList(binary, binary));

        String expected_line = getExpectedMitabLine();
        Assert.assertEquals(expected_line+ MitabUtils.LINE_BREAK+expected_line, writer.toString());
    }

    @Test
    public void test_write_binary2() {
        StringWriter writer = new StringWriter();
        Mitab25ModelledBinaryWriter binaryWriter = new Mitab25ModelledBinaryWriter();
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
                    "\tpsi-mi:protein1(display_short)|psi-mi:full name protein1(display_long)|mint:brca2(gene name)|intact:brca2 synonym(gene name synonym)|intact:\\\"bla\\\" author assigned name(author assigned name)" +
                    "\tpsi-mi:protein2(display_short)|psi-mi:full name protein2(display_long)" +
                    "\t-" +
                    "\t-" +
                    "\t-" +
                    "\ttaxid:9606(human)|taxid:9606(Homo Sapiens)" +
                    "\ttaxid:9606(human)|taxid:9606(Homo Sapiens)" +
                    "\tpsi-mi:\"MI:xxxx\"(association)" +
                    "\tpsi-mi:\"MI:xxx1\"(intact)" +
                    "\tintact:EBI-xxxx" +
                    "\tauthor-score:high(text)";
    }

    private ModelledBinaryInteraction createModelledBinaryInteraction() {
        ModelledParticipant participantA = new MitabModelledParticipant(new MitabProtein("protein1", "full name protein1"));
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
        ModelledParticipant participantB = new MitabModelledParticipant(new MitabProtein("protein2", "full name protein2"));
        // add identifiers
        participantB.getInteractor().getIdentifiers().add(XrefUtils.createUniprotIdentity("P12347"));
        participantB.getInteractor().getIdentifiers().add(XrefUtils.createUniprotSecondary("P12348"));
        participantB.getInteractor().getIdentifiers().add(XrefUtils.createXref("intact", "EBI-12346"));
        // species
        participantB.getInteractor().setOrganism(new MitabOrganism(9606, "human", "Homo Sapiens"));

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
        binary.getModelledConfidences().add(new MitabConfidence("author-score", "high", "text"));
        return binary;
    }
}
