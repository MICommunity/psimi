package psidev.psi.mi.jami.mitab.io.writer;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.io.writer.Mitab25ModelledBinaryWriter;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.StringWriter;

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
    public void test_not_initialised_writer() throws DataSourceWriterException {
        Mitab25ModelledBinaryWriter binaryWriter = new Mitab25ModelledBinaryWriter();
        binaryWriter.write(new MitabModelledBinaryInteraction());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_not_initialised_no_options() throws DataSourceWriterException {
        Mitab25ModelledBinaryWriter binaryWriter = new Mitab25ModelledBinaryWriter();
        binaryWriter.initialiseContext(null);
    }

    @Test
    public void test_write_binary() throws DataSourceWriterException {
        StringWriter writer = new StringWriter();
        Mitab25ModelledBinaryWriter binaryWriter = new Mitab25ModelledBinaryWriter(writer);

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
        participantB.getInteractor().getIdentifiers().add(XrefUtils.createUniprotIdentity("P12345"));
        participantB.getInteractor().getIdentifiers().add(XrefUtils.createUniprotSecondary("P12346"));
        participantB.getInteractor().getIdentifiers().add(XrefUtils.createXref("intact", "EBI-12345"));
        // add aliases
        participantB.getInteractor().getAliases().add(AliasUtils.createGeneName("brca2"));
        participantB.getInteractor().getAliases().add(AliasUtils.createGeneNameSynonym("brca2 synonym"));
        participantB.getAliases().add(AliasUtils.createAuthorAssignedName("\"bla\" author assigned name"));
        // species
        participantB.getInteractor().setOrganism(new MitabOrganism(9606, "human", "Homo Sapiens"));

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

        binaryWriter.write(binary);
    }
}
