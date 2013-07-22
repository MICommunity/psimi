package psidev.psi.mi.jami.enricher.impl.interaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.cvterm.MockCvTermFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.protein.MockProteinFetcher;
import psidev.psi.mi.jami.enricher.*;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.cvterm.CvTermEnricherMinimum;
import psidev.psi.mi.jami.enricher.impl.cvterm.CvTermUpdaterMaximum;
import psidev.psi.mi.jami.enricher.impl.cvterm.CvTermUpdaterMinimum;
import psidev.psi.mi.jami.enricher.impl.cvterm.listener.CvTermEnricherLogger;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherListenerManager;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherLogger;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherStatisticsWriter;
import psidev.psi.mi.jami.enricher.impl.interaction.listener.InteractionEnricherListenerManager;
import psidev.psi.mi.jami.enricher.impl.interaction.listener.InteractionEnricherLogger;
import psidev.psi.mi.jami.enricher.impl.interaction.listener.InteractionEnricherStatisticsWriter;
import psidev.psi.mi.jami.enricher.impl.participant.ParticipantEnricherMinimum;
import psidev.psi.mi.jami.enricher.impl.participant.ParticipantUpdaterMaximum;
import psidev.psi.mi.jami.enricher.impl.participant.ParticipantUpdaterMinimum;
import psidev.psi.mi.jami.enricher.impl.participant.listener.ParticipantEnricherListenerManager;
import psidev.psi.mi.jami.enricher.impl.participant.listener.ParticipantEnricherLogger;
import psidev.psi.mi.jami.enricher.impl.participant.listener.ParticipantEnricherStatisticsWriter;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherListenerManager;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherLogger;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherStatisticsWriter;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.impl.*;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 12/07/13
 */
public class InteractionUpdaterMaximumTest {



    private InteractionEnricher interactionEnricher;
    private InteractionEnricherStatisticsWriter interactionStatisticsWriter;

    private CvTermEnricher cvTermEnricher;
    private MockCvTermFetcher cvTermFetcher;

    private ParticipantEnricher participantEnricher;
    private ParticipantEnricherStatisticsWriter participantStatisticsWriter ;

    private FeatureEnricher featureEnricher;
    private FeatureEnricherStatisticsWriter featureStatisticsWriter;

    private ProteinEnricher proteinEnricher;
    private MockProteinFetcher proteinFetcher;
    private ProteinEnricherStatisticsWriter proteinStatisticsWriter;


    private static final String TEST_SHORTNAME = "test shortName";
    private static final String TEST_FULLNAME = "test fullName";
    private static final String TEST_AC_FULL_PROT = "P12345";
    private static final String TEST_AC_HALF_PROT = "P11111";

    private static final String TEST_SEQUENCE_OLD = "AAAAACCCCCCCCCGGGGGGGGGGGGTTTTTTTT";
    private static final String TEST_SEQUENCE_NEW = "TTTAAAAACCCCCCCCCGGGGGGGGGGGGTTTTTTTT";
    private static final int TEST_ORGANISM_ID = 1234;
    private static final String TEST_ORGANISM_COMMON = "Common";
    private static final String TEST_ORGANISM_SCIENTIFIC = "Scientific";


    @Before
    public void setUp() throws IOException {
        interactionEnricher = new InteractionUpdaterMaximum();
        interactionStatisticsWriter = new InteractionEnricherStatisticsWriter(
                new File("Success_features.txt"),
                new File("Fail_Features.txt") );
        interactionEnricher.setInteractionEnricherListener(
                new InteractionEnricherListenerManager(
                        new InteractionEnricherLogger(),
                        interactionStatisticsWriter ));


        cvTermEnricher = interactionEnricher.getCvTermEnricher();
        cvTermFetcher = new MockCvTermFetcher();
        cvTermEnricher.setCvTermFetcher(cvTermFetcher);
        cvTermEnricher.setCvTermEnricherListener(new CvTermEnricherLogger());

        participantEnricher = interactionEnricher.getParticipantEnricher();
        participantEnricher.setCvTermEnricher(cvTermEnricher);
        participantStatisticsWriter = new ParticipantEnricherStatisticsWriter(
                new File("participant_success"),
                new File("participant_fail") );
        participantEnricher.setParticipantListener(
                new ParticipantEnricherListenerManager(
                        new ParticipantEnricherLogger(),
                        participantStatisticsWriter
                ) );


        featureEnricher = participantEnricher.getFeatureEnricher();
        featureEnricher.setCvTermEnricher(cvTermEnricher);

        featureStatisticsWriter = new FeatureEnricherStatisticsWriter(
                new File("Success_features.txt"),
                new File("Fail_Features.txt") );
        featureEnricher.setFeatureEnricherListener(
                new FeatureEnricherListenerManager(
                        featureStatisticsWriter,
                        new FeatureEnricherLogger() ));


        proteinEnricher = participantEnricher.getProteinEnricher();
        proteinFetcher = new MockProteinFetcher();
        proteinEnricher.setProteinFetcher(proteinFetcher);


        proteinStatisticsWriter = new ProteinEnricherStatisticsWriter(
                new File("Success_protein.txt"),
                new File("Fail_protein.txt") );
        proteinEnricher.setProteinEnricherListener(
                new ProteinEnricherListenerManager(
                        proteinStatisticsWriter,
                        new ProteinEnricherLogger(),
                        (ProteinListeningFeatureEnricher) featureEnricher
                ));



        //=================

        Protein fullProtein = new DefaultProtein(TEST_SHORTNAME, TEST_FULLNAME );
        fullProtein.setUniprotkb(TEST_AC_FULL_PROT);
        fullProtein.setSequence(TEST_SEQUENCE_NEW);
        fullProtein.setOrganism(new DefaultOrganism(TEST_ORGANISM_ID, TEST_ORGANISM_COMMON, TEST_ORGANISM_SCIENTIFIC));
        proteinFetcher.addNewProtein(TEST_AC_FULL_PROT, fullProtein);

        Protein halfProtein = new DefaultProtein(TEST_SHORTNAME);
        halfProtein.setUniprotkb(TEST_AC_HALF_PROT);
        halfProtein.setOrganism(new DefaultOrganism(-3));
        proteinFetcher.addNewProtein(TEST_AC_HALF_PROT, halfProtein);

    }


    @After
    public void closeDown() throws IOException {
        featureStatisticsWriter.close();
        interactionStatisticsWriter.close();
        proteinStatisticsWriter.close();
        participantStatisticsWriter.close();
    }

    @Test
    public void test_default_enrichers_are_same_type(){
        assertTrue(interactionEnricher.getCvTermEnricher() instanceof CvTermUpdaterMaximum);
        assertTrue(interactionEnricher.getParticipantEnricher() instanceof ParticipantUpdaterMaximum);
    }

    @Test
    public void test() throws EnricherException {
        Interaction interactionA = new DefaultInteraction();

        Protein proteinA = new DefaultProtein(TEST_SHORTNAME);
        proteinA.setUniprotkb(TEST_AC_FULL_PROT);
        //protein.setSequence(TEST_SEQUENCE_OLD);

        Participant participantA = new DefaultParticipant(proteinA);
        interactionA.addParticipant(participantA);

        Feature featureB = new DefaultFeature("Featureb","featureb");
        featureB.getRanges().add( new DefaultRange( new DefaultPosition(7), new DefaultPosition(15) ));
        participantA.addFeature(featureB);

        Feature featureA = new DefaultFeature("Featurea","featurea");
        featureA.getRanges().add( new DefaultRange(new DefaultPosition(4),new DefaultPosition(10) ));
        participantA.addFeature(featureA);

        interactionEnricher.enrichInteraction(interactionA);
    }
}
