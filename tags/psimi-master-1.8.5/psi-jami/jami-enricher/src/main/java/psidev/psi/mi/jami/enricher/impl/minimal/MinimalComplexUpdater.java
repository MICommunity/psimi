package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.enricher.ComplexEnricher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.model.Complex;

/**
 * Minimal updater for complexes
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class MinimalComplexUpdater extends MinimalModelledInteractionUpdater<Complex> implements ComplexEnricher {

    private MinimalInteractorBaseUpdater<Complex> interactorEnricher = null;

    public MinimalComplexUpdater(){
        super();
        this.interactorEnricher = new MinimalInteractorBaseUpdater<Complex>();
    }

    /**
     * Strategy for the Interaction enrichment.
     * This method can be overwritten to change how the interaction is enriched.
     * @param interactionToEnrich   The interaction to be enriched.
     */
    protected void processOtherProperties(Complex interactionToEnrich) throws EnricherException {
        super.processOtherProperties(interactionToEnrich);

        // interactor properties

        // Interactor type
        this.interactorEnricher.processInteractorType(interactionToEnrich, null);

        // Organism
        this.interactorEnricher.processOrganism(interactionToEnrich, null);
    }

    @Override
    protected void processOtherProperties(Complex objectToEnrich, Complex objectSource) throws EnricherException {
        super.processOtherProperties(objectToEnrich, objectSource);

        // interactor properties

        // Interactor type
        this.interactorEnricher.processInteractorType(objectToEnrich, objectSource);

        // Organism
        this.interactorEnricher.processOrganism(objectToEnrich, objectSource);

        // FULL NAME
        this.interactorEnricher.processFullName(objectToEnrich, objectSource);

        //ALIASES
        this.interactorEnricher.processAliases(objectToEnrich, objectSource);
    }

    public InteractorFetcher<Complex> getInteractorFetcher() {
        return this.interactorEnricher.getInteractorFetcher();
    }

    public void setListener(InteractorEnricherListener<Complex> listener) {
        this.interactorEnricher.setListener(listener);
    }

    public InteractorEnricherListener<Complex> getListener() {
        return this.interactorEnricher.getListener();
    }

    public void setCvTermEnricher(CvTermEnricher cvTermEnricher) {
        this.interactorEnricher.setCvTermEnricher(cvTermEnricher);
    }

    public void setOrganismEnricher(OrganismEnricher organismEnricher) {
        this.interactorEnricher.setOrganismEnricher(organismEnricher);
    }

    public OrganismEnricher getOrganismEnricher() {
        return this.interactorEnricher.getOrganismEnricher();
    }
}
