package psidev.psi.mi.jami.enricher.impl.experiment;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.cvterm.MockCvTermFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.publication.MockPublicationFetcher;
import psidev.psi.mi.jami.enricher.ExperimentEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.cvterm.MinimumCvTermEnricher;
import psidev.psi.mi.jami.enricher.impl.organism.MinimumOrganismEnricher;
import psidev.psi.mi.jami.enricher.impl.publication.MinimumPublicationEnricher;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.impl.DefaultExperiment;
import psidev.psi.mi.jami.model.impl.DefaultPublication;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 02/08/13
 */
public class MinimumExperimentEnricherTest {

    Publication persistentPublication = null;
    Experiment persistentExperiment = null;

    ExperimentEnricher experimentEnricher;

    @Before
    public void setup(){
        persistentPublication = new DefaultPublication();
        persistentExperiment = new DefaultExperiment(persistentPublication);

        experimentEnricher = new MinimumExperimentEnricher();
    }


    @Test
    public void test_enriching_an_experiment_without_fields_or_enrichers()
            throws EnricherException {
        experimentEnricher.enrichExperiment(persistentExperiment);
    }

    /**
     * Show that even when fields are null but enrichers are present, the enrichment is successful.
     * @throws EnricherException
     */
    @Test
    public void test_enriching_an_experiment_without_fields()
            throws EnricherException {

        experimentEnricher.setCvTermEnricher(
                new MinimumCvTermEnricher(
                        new MockCvTermFetcher()));
        experimentEnricher.setOrganismEnricher(
                new MinimumOrganismEnricher(
                        new MockOrganismFetcher()));
        experimentEnricher.setPublicationEnricher(
                new MinimumPublicationEnricher(
                        new MockPublicationFetcher()));

        experimentEnricher.enrichExperiment(persistentExperiment);
    }

    @Test
    public void test_enriching_an_experiment_with_an_interaction_detection_method()
            throws EnricherException {

        experimentEnricher.setCvTermEnricher(
                new MinimumCvTermEnricher(
                        new MockCvTermFetcher()));

        experimentEnricher.enrichExperiment(persistentExperiment);
    }


    @Test
    public void test_enriching_an_experiment_with_a_publication()
            throws EnricherException {

        experimentEnricher.setPublicationEnricher(
                new MinimumPublicationEnricher(
                        new MockPublicationFetcher()));

        experimentEnricher.enrichExperiment(persistentExperiment);
    }

    @Test
    public void test_enriching_an_experiment_with_an_organism()
            throws EnricherException {

        experimentEnricher.setOrganismEnricher(
                new MinimumOrganismEnricher(
                        new MockOrganismFetcher()));

        experimentEnricher.enrichExperiment(persistentExperiment);
    }




}
