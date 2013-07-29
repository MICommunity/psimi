package psidev.psi.mi.jami.enricher.impl.feature;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/07/13
 */
public class MaximumFeatureUpdaterTest {
    /*

    private ParticipantEnricher participantEnricher;
    private ParticipantEnricherStatisticsWriter participantStatisticsWriter;
    private ProteinEnricher proteinEnricher;
    private MockProteinFetcher proteinFetcher;
    private FeatureEnricher featureEnricher;
    private FeatureEnricherStatisticsWriter featureStatisticsWriter;
    private MockCvTermFetcher cvTermFetcher;

    private ProteinEnricherStatisticsWriter proteinStatisticsWriter;


    private static final String TEST_SHORTNAME = "test shortName";
    private static final String TEST_FULLNAME = "test fullName";
    private static final String TEST_AC_FULL_PROT = "P12345";
    private static final String TEST_AC_HALF_PROT = "P11111";

    private static final String TEST_SEQUENCE_OLD = "AAAAAAAAAAAAAAAAAAAACATAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String TEST_SEQUENCE_NEW = "AAAAAAAAAAAAAAAAAAAAAAAAACATAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final int TEST_ORGANISM_ID = 1234;
    private static final String TEST_ORGANISM_COMMON = "Common";
    private static final String TEST_ORGANISM_SCIENTIFIC = "Scientific";


    @Before
    public void setup() throws IOException {
        participantEnricher = new MaximumParticipantUpdater<DefaultParticipant , DefaultFeature>();
        participantStatisticsWriter = new ParticipantEnricherStatisticsWriter(
                new File("participant_success"),
                new File("participant_fail") );
        participantEnricher.setParticipantListener(
                new ParticipantEnricherListenerManager(
                        new ParticipantEnricherLogger(),
                        participantStatisticsWriter
                ) );

        cvTermFetcher = new MockCvTermFetcher();

        participantEnricher.setCvTermEnricher(new MaximumCvTermUpdater(cvTermFetcher));
        participantEnricher.getCvTermEnricher().setCvTermEnricherListener(new CvTermEnricherLogger());

        featureEnricher = participantEnricher.getFeatureEnricher();

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
        proteinStatisticsWriter.close();
        participantStatisticsWriter.close();
    }


    @Test
    public void test() throws EnricherException, IOException {
        Protein protein = new DefaultProtein(TEST_SHORTNAME);
        protein.setUniprotkb(TEST_AC_FULL_PROT);
        protein.setSequence(TEST_SEQUENCE_OLD);

        Participant participant = new DefaultParticipant(protein);
        Feature featureA = new DefaultFeature();
        featureA.getRanges().add(
                new DefaultRange(
                        new DefaultPosition(4),
                        new DefaultPosition(10) ));

        participant.addFeature(featureA);

        participantEnricher.enrichParticipant(participant);
    }


         */



}
