package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultExactInteractorComparator;

/**
 * The participant enricher is an enricher which can enrich either single participant or a collection.
 * The participant enricher has subEnrichers and no fetchers.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/06/13
 */
public class MinimalEntityUpdater<P extends Entity, F extends Feature>
        extends MinimalEntityEnricher<P,F>  {

    @Override
    public void processInteractor(P objectToEnrich, P objectSource) throws EnricherException {
        if (!DefaultExactInteractorComparator.areEquals(objectToEnrich.getInteractor(), objectSource.getInteractor())){
            Interactor old = objectToEnrich.getInteractor();
            objectToEnrich.setInteractor(objectSource.getInteractor());
            if (getParticipantEnricherListener() != null){
                getParticipantEnricherListener().onInteractorUpdate(objectToEnrich, old);
            }
        }
        else if (getInteractorEnricher() != null
                && objectToEnrich.getInteractor() != objectSource.getInteractor()){
            getInteractorEnricher().enrich(objectToEnrich.getInteractor(), objectSource.getInteractor());
        }
        // nothing to do here
        processInteractor(objectToEnrich);
    }

    @Override
    public void processFeatures(P objectToEnrich, P objectSource) throws EnricherException {
        EnricherUtils.mergeFeatures(objectToEnrich, objectToEnrich.getFeatures(), objectSource.getFeatures(), true, getParticipantEnricherListener(),
                getFeatureEnricher());
        processFeatures(objectToEnrich);
    }
}
