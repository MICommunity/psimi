package psidev.psi.mi.jami.json;

import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteractionEvidence;
import psidev.psi.mi.jami.exception.DataSourceWriterException;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Unti tester for MIJsonBinaryWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/07/13</pre>
 */

public class MIJsonBinaryWriterTest {

    @Test(expected = IllegalStateException.class)
    public void test_not_initialised_writer() throws DataSourceWriterException {
        MIJsonBinaryWriter binaryWriter = new MIJsonBinaryWriter();
        binaryWriter.write(new DefaultBinaryInteractionEvidence());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_not_initialised_no_options() throws DataSourceWriterException {
        MIJsonBinaryWriter binaryWriter = new MIJsonBinaryWriter();
        binaryWriter.initialiseContext(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_create_writer_no_ontology_fetcher() throws DataSourceWriterException {
        MIJsonBinaryWriter binaryWriter = new MIJsonBinaryWriter();
        binaryWriter.initialiseContext(null);
    }

    private String getJson() {
        return "{}";
    }

    private BinaryInteractionEvidence createBinaryInteractionEvidence() throws ParseException {
        ParticipantEvidence participantA = new DefaultParticipantEvidence(new DefaultProtein("protein1", "full name protein1"));
        // add identifiers
        participantA.getInteractor().getIdentifiers().add(XrefUtils.createUniprotIdentity("P12345"));
        participantA.getInteractor().getIdentifiers().add(XrefUtils.createUniprotSecondary("P12346"));
        participantA.getInteractor().getIdentifiers().add(XrefUtils.createXref("intact", "EBI-12345"));
        // add aliases
        participantA.getInteractor().getAliases().add(AliasUtils.createGeneName("brca2"));
        participantA.getInteractor().getAliases().add(AliasUtils.createGeneNameSynonym("brca2 synonym"));
        participantA.getAliases().add(AliasUtils.createAuthorAssignedName("\"bla\" author assigned name"));
        // species
        participantA.getInteractor().setOrganism(new DefaultOrganism(9606, "human", "Homo Sapiens"));
        //participantA.getAliases()
        ParticipantEvidence participantB = new DefaultParticipantEvidence(new DefaultProtein("protein2", "full name protein2"));
        // add identifiers
        participantB.getInteractor().getIdentifiers().add(XrefUtils.createUniprotIdentity("P12347"));
        participantB.getInteractor().getIdentifiers().add(XrefUtils.createUniprotSecondary("P12348"));
        participantB.getInteractor().getIdentifiers().add(XrefUtils.createXref("intact", "EBI-12346"));
        // species
        participantB.getInteractor().setOrganism(new DefaultOrganism(9606, "human", "Homo Sapiens"));

        BinaryInteractionEvidence binary = new DefaultBinaryInteractionEvidence(participantA, participantB);
        participantA.setInteractionEvidence(binary);
        participantB.setInteractionEvidence(binary);

        // detection method
        binary.setExperiment(new DefaultExperiment(new DefaultPublication()));
        binary.getExperiment().setInteractionDetectionMethod(new DefaultCvTerm("pull down", "MI:xxx2"));
        // first author
        binary.getExperiment().getPublication().setPublicationDate(new SimpleDateFormat("yyyy").parse("2006"));
        binary.getExperiment().getPublication().getAuthors().add("author1");
        binary.getExperiment().getPublication().getAuthors().add("author2");
        // publication identifiers
        binary.getExperiment().getPublication().setPubmedId("12345");
        binary.getExperiment().getPublication().assignImexId("IM-1");
        // interaction type
        binary.setInteractionType(CvTermUtils.createMICvTerm("association", "MI:xxxx"));
        // source database
        Source source = new DefaultSource("intact");
        source.setMIIdentifier("MI:xxx1");
        binary.getExperiment().getPublication().setSource(source);
        // interaction identifiers
        binary.getIdentifiers().add(XrefUtils.createIdentityXref("intact", "EBI-xxxx"));
        binary.getIdentifiers().add(XrefUtils.createXrefWithQualifier("imex", "IM-1-1", "imex-primary"));
        // confidences
        binary.getConfidences().add(new DefaultConfidence(new DefaultCvTerm("author-score"), "high"));
        return binary;
    }
}
