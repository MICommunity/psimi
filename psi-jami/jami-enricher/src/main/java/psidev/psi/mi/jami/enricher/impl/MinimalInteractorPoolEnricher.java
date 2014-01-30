package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.InteractorEnricher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.InteractorPool;

import java.util.Collection;

/**
 * A basic minimal enricher for interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class MinimalInteractorPoolEnricher implements InteractorEnricher<InteractorPool> {

    private InteractorEnricher<Interactor> interactorEnricher;

    public MinimalInteractorPoolEnricher(){
        this.interactorEnricher = new MinimalInteractorEnricher<Interactor>();
    }

    protected MinimalInteractorPoolEnricher(InteractorEnricher<Interactor> interactorEnricher){
        this.interactorEnricher = interactorEnricher != null ? interactorEnricher : new MinimalInteractorEnricher<Interactor>();
    }

    public InteractorFetcher<InteractorPool> getInteractorFetcher() {
        return null;
    }

    public void setListener(InteractorEnricherListener<InteractorPool> listener) {

    }

    public InteractorEnricherListener<InteractorPool> getListener() {
        return null;
    }

    public void setCvTermEnricher(CvTermEnricher cvTermEnricher) {

    }

    public CvTermEnricher getCvTermEnricher() {
        return null;
    }

    public void setOrganismEnricher(OrganismEnricher organismEnricher) {

    }

    public OrganismEnricher getOrganismEnricher() {
        return null;
    }

    public void enrich(InteractorPool object) throws EnricherException {

    }

    public void enrich(Collection<InteractorPool> objects) throws EnricherException {

    }

    public void enrich(InteractorPool objectToEnrich, InteractorPool objectSource) throws EnricherException {

    }
}
