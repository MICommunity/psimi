package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interactor;

/**
 * The participant enricher is an enricher which can enrich either single participant or a collection.
 * The participant enricher has subEnrichers and no fetchers.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/06/13
 */
public class FullParticipantUpdater<P extends Entity , F extends Feature>
        extends FullParticipantEnricher<P,F>  {

    @Override
    protected void processCausalRelationships(P objectToEnrich, P objectSource) throws EnricherException {
        EnricherUtils.mergeCausalRelationships(objectToEnrich, objectToEnrich.getCausalRelationships(), objectSource.getCausalRelationships(), true, getParticipantEnricherListener());
    }

    @Override
    protected void processXrefs(P objectToEnrich, P objectSource) {
        EnricherUtils.mergeXrefs(objectToEnrich, objectToEnrich.getXrefs(), objectSource.getXrefs(), true, false, getParticipantEnricherListener(),
                null);
    }

    @Override
    protected void processAnnotations(P objectToEnrich, P objectSource) {
        EnricherUtils.mergeAnnotations(objectToEnrich, objectToEnrich.getAnnotations(), objectSource.getAnnotations(), true, getParticipantEnricherListener());
    }

    @Override
    protected void processInteractor(P objectToEnrich, P objectSource) throws EnricherException {
        if (objectToEnrich.getInteractor() != objectSource.getInteractor()){
            Interactor old = objectToEnrich.getInteractor();
            objectToEnrich.setInteractor(objectSource.getInteractor());
            if (getParticipantEnricherListener() != null){
                getParticipantEnricherListener().onInteractorUpdate(objectToEnrich, old);
            }
        }
        // nothing to do here
        processInteractor(objectToEnrich);
    }

    @Override
    protected void processFeatures(P objectToEnrich, P objectSource) throws EnricherException {
        EnricherUtils.mergeFeatures(objectToEnrich, objectToEnrich.getFeatures(), objectSource.getFeatures(), true, getParticipantEnricherListener(),
                getFeatureEnricher());
        processFeatures(objectToEnrich);
    }

    @Override
    protected void processBiologicalRole(P objectToEnrich, P objectSource) throws EnricherException {
        if (objectToEnrich.getBiologicalRole() != objectSource.getBiologicalRole()){
            CvTerm old = objectToEnrich.getBiologicalRole();
            objectToEnrich.setBiologicalRole(objectSource.getBiologicalRole());
            if (getParticipantEnricherListener() != null){
                getParticipantEnricherListener().onBiologicalRoleUpdate(objectToEnrich, old);
            }
        }
        // nothing to do here
        processBiologicalRole(objectToEnrich);
    }

    @Override
    protected void processAliases(P objectToEnrich, P objectSource) {
        EnricherUtils.mergeAliases(objectToEnrich, objectToEnrich.getAliases(), objectSource.getAliases(), true, getParticipantEnricherListener());
    }
}
