package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.model.Interactor;

/**
 * A basic minimal enricher for interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class BasicInteractorEnricher extends AbstractInteractorEnricher<Interactor>{

    private InteractorEnricherListener listener;

    public void enrich(Interactor objectToEnrich) throws EnricherException {
        if(objectToEnrich == null)
            throw new IllegalArgumentException("Cannot enrich a null interactor.");

        // Interactor type
        processInteractorType(objectToEnrich, null);

        // Organism
        processOrganism(objectToEnrich, null);
    }

    @Override
    public InteractorEnricherListener<Interactor> getListener() {
        return this.listener;
    }

    @Override
    public void setListener(InteractorEnricherListener<Interactor> listener) {
        if (listener instanceof InteractorEnricherListener){
            this.listener = (InteractorEnricherListener)listener;
        }
        else if (listener == null){
            this.listener = null;
        }
        else{
            throw new IllegalArgumentException("A InteractorEnricherListener is expected and we tried to set a " + listener.getClass().getCanonicalName() + " instead");
        }
    }

    @Override
    protected Interactor fetchEnrichedVersionFrom(Interactor objectToEnrich) throws EnricherException {
        return null;
    }

    @Override
    protected void onEnrichedVersionNotFound(Interactor objectToEnrich) throws EnricherException {
        // nothing to do
    }

    @Override
    protected boolean isFullEnrichment() {
        return false;
    }

    @Override
    protected void onCompletedEnrichment(Interactor objectToEnrich) {
        if(getListener() != null)
            getListener().onEnrichmentComplete(
                    objectToEnrich , EnrichmentStatus.SUCCESS , "The interactor has been successfully enriched.");
    }

    @Override
    protected void onInteractorCheckFailure(Interactor objectToEnrich, Interactor fetchedObject) {
        // do nothing
    }

    @Override
    protected void processInteractorType(Interactor entityToEnrich, Interactor fetched) throws EnricherException {
        if (getCvTermEnricher() != null && entityToEnrich.getInteractorType() != null){
            getCvTermEnricher().enrich(entityToEnrich.getInteractorType());
        }
    }

    @Override
    protected void processOrganism(Interactor entityToEnrich, Interactor fetched) throws EnricherException {
        if (getOrganismEnricher() != null && entityToEnrich.getOrganism() != null){
            getOrganismEnricher().enrich(entityToEnrich.getOrganism());
        }
    }
}
