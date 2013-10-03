package psidev.psi.mi.jami.enricher.impl;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.fetcher.mock.MockCvTermFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mock.MockPublicationFetcher;
import psidev.psi.mi.jami.enricher.ExperimentEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.ExperimentEnricherListener;
import psidev.psi.mi.jami.enricher.listener.impl.ExperimentEnricherListenerManager;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultExperiment;
import psidev.psi.mi.jami.model.impl.DefaultPublication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 02/08/13
 */
public class BasicExperimentEnricherTest {

    Publication persistentPublication = null;
    Experiment persistentExperiment = null;

    ExperimentEnricher experimentEnricher;

    @Before
    public void setup(){
        persistentPublication = new DefaultPublication();
        persistentExperiment = new DefaultExperiment(persistentPublication);

        experimentEnricher = new BasicExperimentEnricher();
    }

    /**
     * Show that the experiment enricher does not fail if the enrichers are not present
     * @throws EnricherException
     */
    @Test
    public void test_enriching_an_experiment_without_fields_or_enrichers()
            throws EnricherException {
        experimentEnricher.enrich(persistentExperiment);
    }

    /**
     * Show that even when fields are null but enrichers are present, the enrichment is still successful.
     * @throws EnricherException
     */
    @Test
    public void test_enriching_an_experiment_without_fields()
            throws EnricherException {

        experimentEnricher.setCvTermEnricher(
                new MinimalCvTermEnricher(
                        new MockCvTermFetcher()));
        experimentEnricher.setPublicationEnricher(
                new MinimalPublicationEnricher(
                        new MockPublicationFetcher()));

        experimentEnricher.setExperimentEnricherListener( new ExperimentEnricherListenerManager(
                //new ExperimentEnricherLogger(),
                new ExperimentEnricherListener(){
                    public void onEnrichmentComplete(Experiment experiment, EnrichmentStatus status, String message) {
                        assertTrue(experiment == persistentExperiment);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }

                    public void onEnrichmentError(Experiment object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));

        experimentEnricher.enrich(persistentExperiment);
    }

    @Test
    public void test_enriching_an_experiment_with_an_interaction_detection_method()
            throws EnricherException {

        experimentEnricher.setCvTermEnricher(
                new MinimalCvTermEnricher(
                        new MockCvTermFetcher()));

        experimentEnricher.setExperimentEnricherListener( new ExperimentEnricherListenerManager(
                //new ExperimentEnricherLogger(),
                new ExperimentEnricherListener(){
                    public void onEnrichmentComplete(Experiment experiment, EnrichmentStatus status, String message) {
                        assertTrue(experiment == persistentExperiment);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }
                    public void onEnrichmentError(Experiment object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));

        persistentExperiment.setInteractionDetectionMethod(new DefaultCvTerm("other short name"));

        experimentEnricher.enrich(persistentExperiment);


    }


    @Test
    public void test_enriching_an_experiment_with_a_publication()
            throws EnricherException {

        experimentEnricher.setPublicationEnricher(
                new MinimalPublicationEnricher(
                        new MockPublicationFetcher()));

        experimentEnricher.setExperimentEnricherListener( new ExperimentEnricherListenerManager(
                //new ExperimentEnricherLogger(),
                new ExperimentEnricherListener(){
                    public void onEnrichmentComplete(Experiment experiment, EnrichmentStatus status, String message) {
                        assertTrue(experiment == persistentExperiment);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }
                    public void onEnrichmentError(Experiment object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));

        experimentEnricher.enrich(persistentExperiment);
    }

    @Test
    public void test_enriching_an_experiment_with_an_organism()
            throws EnricherException {

        experimentEnricher.setExperimentEnricherListener( new ExperimentEnricherListenerManager(
                //new ExperimentEnricherLogger(),
                new ExperimentEnricherListener(){
                    public void onEnrichmentComplete(Experiment experiment, EnrichmentStatus status, String message) {
                        assertTrue(experiment == persistentExperiment);
                        assertEquals(EnrichmentStatus.SUCCESS , status);
                    }
                    public void onEnrichmentError(Experiment object, String message, Exception e) {
                        Assert.fail();
                    }
                }
        ));

        experimentEnricher.enrich(persistentExperiment);
    }




}
