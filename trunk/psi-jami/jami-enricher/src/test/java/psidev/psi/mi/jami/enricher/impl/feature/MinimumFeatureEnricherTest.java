package psidev.psi.mi.jami.enricher.impl.feature;



import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.cvterm.MockCvTermFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.protein.MockProteinFetcher;
import psidev.psi.mi.jami.enricher.*;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.cvterm.MinimumCvTermEnricher;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherListenerManager;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherLogger;
import psidev.psi.mi.jami.enricher.impl.participant.MinimumParticipantEnricher;
import psidev.psi.mi.jami.enricher.impl.protein.MinimumProteinEnricher;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;

import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/07/13
 */
public class MinimumFeatureEnricherTest {

    protected static final Logger log = LoggerFactory.getLogger(MinimumFeatureEnricherTest.class.getName());

    private ParticipantEnricher participantEnricher;
    private ProteinEnricher proteinEnricher;
    private MockProteinFetcher proteinFetcher;
    private FeatureEnricher featureEnricher;
    private CvTermEnricher cvTermEnricher;
    private MockCvTermFetcher cvTermFetcher;

    private Participant persistentParticipant;
    private Feature persistentFeature;


    private static final String TEST_OLD_SHORTNAME = "test old shortName";
    private static final String TEST_OLD_FULLNAME = "test old fullName";
    private static final String TEST_SHORTNAME = "test shortName";
    private static final String TEST_FULLNAME = "test fullName";
    private static final String TEST_AC_FULL_PROT = "P12345";
    private static final String TEST_AC_HALF_PROT = "P11111";
    private static final String TEST_AC_CUSTOM_PROT = "P99999";

    private static final String TEST_OLD_SEQUENCE = "AAAAAAAAAAAAAAAAAAAAAcatAAAAAAAAAAAAAAAAAAA";
    private static final String TEST_SEQUENCE_NEW = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAcatAAAAAAAAAAAAAAAAAAAAA";
    private static final int TEST_ORGANISM_ID = 1234;
    private static final String TEST_ORGANISM_COMMON = "Common";
    private static final String TEST_ORGANISM_SCIENTIFIC = "Scientific";


    @Before
    public void setup() throws IOException {
        cvTermFetcher = new MockCvTermFetcher();
        cvTermEnricher = new MinimumCvTermEnricher(cvTermFetcher);


        featureEnricher = new MinimumFeatureEnricher();

        proteinFetcher = new MockProteinFetcher();
        proteinEnricher = new MinimumProteinEnricher(proteinFetcher);

        participantEnricher = new MinimumParticipantEnricher();
        participantEnricher.setFeatureEnricher(featureEnricher);
        participantEnricher.setProteinEnricher(proteinEnricher);
        assertNotNull(proteinEnricher.getProteinEnricherListener());
        assertTrue(proteinEnricher.getProteinEnricherListener() == featureEnricher);

        Protein fullProtein = new DefaultProtein(TEST_SHORTNAME, TEST_FULLNAME );
        fullProtein.setUniprotkb(TEST_AC_FULL_PROT);
        fullProtein.setSequence(TEST_SEQUENCE_NEW);
        fullProtein.setOrganism(new DefaultOrganism(TEST_ORGANISM_ID, TEST_ORGANISM_COMMON, TEST_ORGANISM_SCIENTIFIC));
        proteinFetcher.addNewProtein(TEST_AC_FULL_PROT, fullProtein);

        Protein halfProtein = new DefaultProtein(TEST_SHORTNAME);
        halfProtein.setUniprotkb(TEST_AC_HALF_PROT);
        halfProtein.setOrganism(new DefaultOrganism(-3));
        proteinFetcher.addNewProtein(TEST_AC_HALF_PROT, halfProtein);

        persistentParticipant = null;
        persistentFeature = null;
    }

    // == FEATURE ONLY =============================================================================

    @Test
    public void test_that_non_sequence_reliant_feature_data_is_successful_without_CvTermEnricher() throws EnricherException, IOException {

        persistentFeature = new DefaultFeature("Featurea","featurea");

        persistentFeature.getRanges().add( new DefaultRange(new DefaultPosition(4),new DefaultPosition(10) ));
        assertNull(persistentFeature.getType());
        assertEquals(1 , persistentFeature.getRanges().size());
        assertEquals(Collections.EMPTY_LIST , persistentFeature.getAnnotations());

        featureEnricher.setFeatureEnricherListener( new FeatureEnricherListenerManager(
                new FeatureEnricherLogger() ,
                new FeatureEnricherListener() {
                    public void onEnrichmentComplete(Feature feature, EnrichmentStatus status, String message) {
                        assertTrue(feature == persistentFeature);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }

                    public void onUpdatedRange(Feature feature, Range updated, String message) {fail();}
                    public void onInvalidRange(Feature feature, Range invalid, String message)  {fail();}
                    public void onShortNameUpdate(Feature feature, String oldShortName)  {fail();}
                    public void onFullNameUpdate(Feature feature, String oldFullName)  {fail();}
                    public void onInterproUpdate(Feature feature, String oldInterpro) {fail();}
                    public void onTypeAdded(Feature feature, CvTerm oldType)  {fail();}
                    public void onAddedIdentifier(Feature feature, Xref added)  {fail();}
                    public void onRemovedIdentifier(Feature feature, Xref removed)  {fail();}
                    public void onAddedXref(Feature feature, Xref added) {fail();}
                    public void onRemovedXref(Feature feature, Xref removed) {fail();}
                    public void onAddedAnnotation(Feature feature, Annotation added)  {fail();}
                    public void onRemovedAnnotation(Feature feature, Annotation removed) {fail();}
                    public void onAddedRange(Feature feature, Range added)  {fail();}
                    public void onRemovedRange(Feature feature, Range removed)  {fail();}
                }
        ));

        featureEnricher.enrichFeature(persistentFeature);

        assertNull(persistentFeature.getType());
        assertEquals(1 , persistentFeature.getRanges().size());
        assertEquals(Collections.EMPTY_LIST , persistentFeature.getAnnotations());
    }


    //TODO
    @Test
    public void test_that_non_sequence_reliant_feature_data_is_successful_with_CvTermEnricher() throws EnricherException, IOException {
        featureEnricher.setCvTermEnricher(cvTermEnricher);
        cvTermFetcher.addCvTerm(TEST_AC_CUSTOM_PROT , new DefaultCvTerm(TEST_SHORTNAME , TEST_FULLNAME, TEST_AC_CUSTOM_PROT));

        persistentFeature = new DefaultFeature("Featurea","featurea");

        persistentFeature.getRanges().add( new DefaultRange(new DefaultPosition(4),new DefaultPosition(10) ));
        persistentFeature.setType(new DefaultCvTerm(TEST_SHORTNAME, TEST_AC_CUSTOM_PROT));
        assertNotNull(persistentFeature.getType());
        assertNull(persistentFeature.getType().getFullName());
        assertEquals(1 , persistentFeature.getRanges().size());
        assertEquals(Collections.EMPTY_LIST , persistentFeature.getAnnotations());

        featureEnricher.setFeatureEnricherListener( new FeatureEnricherListenerManager(
                new FeatureEnricherLogger() ,
                new FeatureEnricherListener() {
                    public void onEnrichmentComplete(Feature feature, EnrichmentStatus status, String message) {
                        assertTrue(feature == persistentFeature);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }

                    public void onUpdatedRange(Feature feature, Range updated, String message) {fail();}
                    public void onInvalidRange(Feature feature, Range invalid, String message)  {fail();}
                    public void onShortNameUpdate(Feature feature, String oldShortName)  {fail();}
                    public void onFullNameUpdate(Feature feature, String oldFullName)  {fail();}
                    public void onInterproUpdate(Feature feature, String oldInterpro) {fail();}
                    public void onTypeAdded(Feature feature, CvTerm oldType)  {fail();}
                    public void onAddedIdentifier(Feature feature, Xref added)  {fail();}
                    public void onRemovedIdentifier(Feature feature, Xref removed)  {fail();}
                    public void onAddedXref(Feature feature, Xref added) {fail();}
                    public void onRemovedXref(Feature feature, Xref removed) {fail();}
                    public void onAddedAnnotation(Feature feature, Annotation added)  {fail();}
                    public void onRemovedAnnotation(Feature feature, Annotation removed) {fail();}
                    public void onAddedRange(Feature feature, Range added)  {fail();}
                    public void onRemovedRange(Feature feature, Range removed)  {fail();}
                }
        ));

        featureEnricher.enrichFeature(persistentFeature);


        assertNotNull(persistentFeature.getType().getFullName());
        assertEquals(1 , persistentFeature.getRanges().size());
        assertEquals(Collections.EMPTY_LIST , persistentFeature.getAnnotations());
    }



    // == Complete PARTICIPANT =================================================================================



    @Test
    public void test_annotations_added_for_featureEnricher_where_protein_has_no_sequence() throws EnricherException, IOException {
        Protein protein = new DefaultProtein(TEST_SHORTNAME);
        protein.setUniprotkb(TEST_AC_FULL_PROT);
        persistentParticipant = new DefaultParticipant(protein);

        persistentFeature = new DefaultFeature("Featurea","featurea");
        persistentFeature.getRanges().add( new DefaultRange(new DefaultPosition(4),new DefaultPosition(10) ));
        persistentParticipant.addFeature(persistentFeature);

        assertEquals(1 , persistentFeature.getRanges().size());
        assertEquals(Collections.EMPTY_LIST , persistentFeature.getAnnotations());

        featureEnricher.setFeatureEnricherListener( new FeatureEnricherListenerManager(
                new FeatureEnricherLogger() ,
                new FeatureEnricherListener() {
                    public void onEnrichmentComplete(Feature feature, EnrichmentStatus status, String message) {
                        assertTrue(feature == persistentFeature);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }

                    public void onUpdatedRange(Feature feature, Range updated, String message) {fail();}
                    public void onInvalidRange(Feature feature, Range invalid, String message)  {
                        assertTrue(feature == persistentFeature);
                        assertNotNull(invalid);

                    }
                    public void onShortNameUpdate(Feature feature, String oldShortName)  {fail();}
                    public void onFullNameUpdate(Feature feature, String oldFullName)  {fail();}
                    public void onInterproUpdate(Feature feature, String oldInterpro) {fail();}
                    public void onTypeAdded(Feature feature, CvTerm oldType)  {fail();}
                    public void onAddedIdentifier(Feature feature, Xref added)  {fail();}
                    public void onRemovedIdentifier(Feature feature, Xref removed)  {fail();}
                    public void onAddedXref(Feature feature, Xref added) {fail();}
                    public void onRemovedXref(Feature feature, Xref removed) {fail();}
                    public void onAddedAnnotation(Feature feature, Annotation added)  {
                        assertTrue(feature == persistentFeature);
                        //TODO assertTrue(added.getTopic().getFullName().toLowerCase().contains("caution"));
                    }
                    public void onRemovedAnnotation(Feature feature, Annotation removed) {fail();}
                    public void onAddedRange(Feature feature, Range added)  {fail();}
                    public void onRemovedRange(Feature feature, Range removed)  {fail();}
                }
        ));

        participantEnricher.enrichParticipant(persistentParticipant);

        assertEquals(1 , persistentFeature.getRanges().size());
        assertEquals(1 , persistentFeature.getAnnotations().size());
    }

    @Test
    public void test_annotations_added_for_featureEnricher_where_protein_has_too_short_a_sequence() throws EnricherException, IOException {
        Protein protein = new DefaultProtein(TEST_SHORTNAME);
        protein.setUniprotkb(TEST_AC_FULL_PROT);
        protein.setSequence("ACGACTA");
        persistentParticipant = new DefaultParticipant(protein);

        persistentFeature = new DefaultFeature("Featurea","featurea");
        persistentFeature.getRanges().add( new DefaultRange(new DefaultPosition(4),new DefaultPosition(10) ));
        persistentParticipant.addFeature(persistentFeature);

        assertEquals(1 , persistentFeature.getRanges().size());
        assertEquals(Collections.EMPTY_LIST , persistentFeature.getAnnotations());

        featureEnricher.setFeatureEnricherListener( new FeatureEnricherListenerManager(
                new FeatureEnricherLogger() ,
                new FeatureEnricherListener() {
                    public void onEnrichmentComplete(Feature feature, EnrichmentStatus status, String message) {
                        assertTrue(feature == persistentFeature);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }

                    public void onUpdatedRange(Feature feature, Range updated, String message) {fail();}
                    public void onInvalidRange(Feature feature, Range invalid, String message)  {
                        assertTrue(feature == persistentFeature);
                        assertNotNull(invalid);
                    }
                    public void onShortNameUpdate(Feature feature, String oldShortName)  {fail();}
                    public void onFullNameUpdate(Feature feature, String oldFullName)  {fail();}
                    public void onInterproUpdate(Feature feature, String oldInterpro) {fail();}
                    public void onTypeAdded(Feature feature, CvTerm oldType)  {fail();}
                    public void onAddedIdentifier(Feature feature, Xref added)  {fail();}
                    public void onRemovedIdentifier(Feature feature, Xref removed)  {fail();}
                    public void onAddedXref(Feature feature, Xref added) {fail();}
                    public void onRemovedXref(Feature feature, Xref removed) {fail();}
                    public void onAddedAnnotation(Feature feature, Annotation added)  {
                        assertTrue(feature == persistentFeature);
                        assertTrue(added.getTopic().getShortName().toLowerCase().contains("caution"));
                    }
                    public void onRemovedAnnotation(Feature feature, Annotation removed) {fail();}
                    public void onAddedRange(Feature feature, Range added)  {fail();}
                    public void onRemovedRange(Feature feature, Range removed)  {fail();}
                }
        ));
        participantEnricher.enrichParticipant(persistentParticipant);

        assertEquals(1 , persistentFeature.getRanges().size());
        assertEquals(1 , persistentFeature.getAnnotations().size());
    }






    public void test_b() throws EnricherException, IOException {
        Protein protein = new DefaultProtein(TEST_SHORTNAME);
        protein.setUniprotkb(TEST_AC_FULL_PROT);
        //protein.setSequence(TEST_SEQUENCE_OLD);

        persistentParticipant = new DefaultParticipant(protein);

        Feature featureB = new DefaultFeature("Featureb","featureb");
        featureB.getRanges().add( new DefaultRange( new DefaultPosition(7), new DefaultPosition(15) ));
        persistentParticipant.addFeature(featureB);

        Feature featureA = new DefaultFeature("Featurea","featurea");
        featureA.getRanges().add( new DefaultRange(new DefaultPosition(4),new DefaultPosition(10) ));
        persistentParticipant.addFeature(featureA);


        log.info(persistentParticipant.getFeatures().toString() );


    }





    public void test() throws EnricherException, IOException {
        Protein protein = new DefaultProtein(TEST_SHORTNAME);
        protein.setUniprotkb(TEST_AC_FULL_PROT);
        //protein.setSequence(TEST_SEQUENCE_OLD);

        Participant participant = new DefaultParticipant(protein);

        Feature featureB = new DefaultFeature("Featureb","featureb");
        featureB.getRanges().add( new DefaultRange( new DefaultPosition(7), new DefaultPosition(15) ));
        participant.addFeature(featureB);

        Feature featureA = new DefaultFeature("Featurea","featurea");
        featureA.getRanges().add( new DefaultRange(new DefaultPosition(4),new DefaultPosition(10) ));
        participant.addFeature(featureA);


        log.info(participant.getFeatures().toString() );


    }







}
