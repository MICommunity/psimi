package psidev.psi.mi.jami.mitab.io.writer;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.expansion.SpokeExpansion;
import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.io.writer.Mitab25ModelledInteractionWriter;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit tester for Mitab25ModelledInteractionWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/06/13</pre>
 */

public class Mitab25ModelledInteractionWriterTest {

    @Test
    public void test_mitab_version_and_header(){
        Mitab25ModelledInteractionWriter binaryWriter = new Mitab25ModelledInteractionWriter();
        Assert.assertEquals(MitabVersion.v2_5, binaryWriter.getVersion());
        Assert.assertFalse(binaryWriter.isWriteHeader());
    }

    @Test(expected = IllegalStateException.class)
    public void test_not_initialised_writer() throws DataSourceWriterException {
        Mitab25ModelledInteractionWriter binaryWriter = new Mitab25ModelledInteractionWriter();
        binaryWriter.write(new MitabModelledInteraction());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_not_initialised_no_options() throws DataSourceWriterException {
        Mitab25ModelledInteractionWriter binaryWriter = new Mitab25ModelledInteractionWriter();
        binaryWriter.initialiseContext(null);
    }

    @Test
    public void test_write_interaction() throws DataSourceWriterException {
        StringWriter writer = new StringWriter();
        Mitab25ModelledInteractionWriter interactionWriter = new Mitab25ModelledInteractionWriter(writer);
        interactionWriter.setWriteHeader(false);

        ModelledInteraction binary = createModelledInteraction();

        interactionWriter.write(binary);

        String expected_line = getExpectedMitabLine();
        Assert.assertEquals(expected_line, writer.toString());
    }

    @Test
    public void test_write_binary_list() throws DataSourceWriterException {
        StringWriter writer = new StringWriter();
        Mitab25ModelledInteractionWriter binaryWriter = new Mitab25ModelledInteractionWriter(writer);
        binaryWriter.setWriteHeader(false);

        ModelledInteraction binary = createModelledInteraction();

        binaryWriter.write(Arrays.asList(binary, binary));

        String expected_line = getExpectedMitabLine();
        Assert.assertEquals(expected_line+ MitabUtils.LINE_BREAK+expected_line, writer.toString());
    }

    @Test
    public void test_write_binary2() throws DataSourceWriterException {
        StringWriter writer = new StringWriter();
        Mitab25ModelledInteractionWriter binaryWriter = new Mitab25ModelledInteractionWriter();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(MitabUtils.MITAB_HEADER_OPTION, false);
        options.put(InteractionWriterFactory.WRITER_OPTION_KEY, writer);
        options.put(InteractionWriterFactory.COMPLEX_EXPANSION_OPTION_KEY, new SpokeExpansion());
        binaryWriter.initialiseContext(options);

        ModelledInteraction binary = createModelledInteraction();

        binaryWriter.write(binary);

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
                "\t-" +
                "\t-" +
                "\t-" +
                "\ttaxid:9606(human)|taxid:9606(Homo Sapiens)" +
                "\ttaxid:9606(human)|taxid:9606(Homo Sapiens)" +
                "\tpsi-mi:\"MI:xxxx\"(association)" +
                "\tpsi-mi:\"MI:xxx1\"(intact)" +
                "\tintact:EBI-xxxx" +
                "\tauthor-score:high" +
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
                "\tauthor-score:high";
    }

    private ModelledInteraction createModelledInteraction() {
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
        participantC.setBiologicalRole(new MitabCvTerm("enzyme"));

        ModelledInteraction interaction = new MitabModelledInteraction();
        interaction.addModelledParticipant(participantA);
        interaction.addModelledParticipant(participantB);
        interaction.addModelledParticipant(participantC);

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
        return interaction;
    }
}
