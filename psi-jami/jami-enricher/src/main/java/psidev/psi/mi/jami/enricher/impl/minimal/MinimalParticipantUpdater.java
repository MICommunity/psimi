package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultExactInteractorComparator;

/**
 * The participant enricher is an enricher which can enrich either single participant or a collection.
 * The participant enricher has subEnrichers and no fetchers.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/06/13
 */
public class MinimalParticipantUpdater<P extends Entity , F extends Feature>
        extends MinimalParticipantEnricher<P,F>  {

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

    @Override
    public void processBiologicalRole(P objectToEnrich, P objectSource) throws EnricherException {
        if (!DefaultCvTermComparator.areEquals(objectToEnrich.getBiologicalRole(), objectSource.getBiologicalRole())){
            CvTerm old = objectToEnrich.getBiologicalRole();
            objectToEnrich.setBiologicalRole(objectSource.getBiologicalRole());
            if (getParticipantEnricherListener() != null){
                getParticipantEnricherListener().onBiologicalRoleUpdate(objectToEnrich, old);
            }
        }
        else if (getCvTermEnricher() != null
                && objectToEnrich.getBiologicalRole() != objectSource.getBiologicalRole()){
            getCvTermEnricher().enrich(objectToEnrich.getBiologicalRole(), objectSource.getBiologicalRole());
        }
        // nothing to do here
        processBiologicalRole(objectToEnrich);
    }

    @Override
    protected void processAliases(P objectToEnrich, P objectSource) {
        EnricherUtils.mergeAliases(objectToEnrich, objectToEnrich.getAliases(), objectSource.getAliases(), true, getParticipantEnricherListener());
    }
}
