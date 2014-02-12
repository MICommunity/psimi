package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.DefaultOrganismComparator;
import psidev.psi.mi.jami.utils.comparator.publication.DefaultCuratedPublicationComparator;

/**
 * Minimal updater for experiments
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class MinimalExperimentUpdater extends MinimalExperimentEnricher{

    public MinimalExperimentUpdater(){
       super();
    }

    @Override
    protected void processInteractionDetectionMethod(Experiment experimentToEnrich, Experiment objectSource) throws EnricherException {

        if (!DefaultCvTermComparator.areEquals(experimentToEnrich.getInteractionDetectionMethod(), objectSource.getInteractionDetectionMethod())){
            CvTerm old = experimentToEnrich.getInteractionDetectionMethod();
            experimentToEnrich.setInteractionDetectionMethod(objectSource.getInteractionDetectionMethod());
            if (getExperimentEnricherListener() != null){
                getExperimentEnricherListener().onInteractionDetectionMethodUpdate(experimentToEnrich, old);
            }
        }
        else if (getCvTermEnricher() != null
                && experimentToEnrich.getInteractionDetectionMethod() != experimentToEnrich.getInteractionDetectionMethod()){
            getCvTermEnricher().enrich(experimentToEnrich.getInteractionDetectionMethod(), objectSource.getInteractionDetectionMethod());
        }
        processInteractionDetectionMethod(experimentToEnrich);
    }

    @Override
    protected void processPublication(Experiment experimentToEnrich, Experiment objectSource) throws EnricherException {
        if (!DefaultCuratedPublicationComparator.areEquals(experimentToEnrich.getPublication(), objectSource.getPublication())){
            Publication old = experimentToEnrich.getPublication();
            experimentToEnrich.setPublication(objectSource.getPublication());
            if (getExperimentEnricherListener() != null){
                getExperimentEnricherListener().onPublicationUpdate(experimentToEnrich, old);
            }
        }
        else if (getPublicationEnricher() != null
                && experimentToEnrich.getPublication() != experimentToEnrich.getPublication()){
            getPublicationEnricher().enrich(experimentToEnrich.getPublication(), objectSource.getPublication());
        }
        processPublication(experimentToEnrich);
    }

    @Override
    protected void processOrganism(Experiment experimentToEnrich, Experiment objectSource) throws EnricherException {
        if (!DefaultOrganismComparator.areEquals(experimentToEnrich.getHostOrganism(), objectSource.getHostOrganism())){
            Organism old = experimentToEnrich.getHostOrganism();
            experimentToEnrich.setHostOrganism(objectSource.getHostOrganism());
            if (getExperimentEnricherListener() != null){
                getExperimentEnricherListener().onHostOrganismUpdate(experimentToEnrich, old);
            }
        }
        else if (getOrganismEnricher() != null
                && experimentToEnrich.getHostOrganism() != experimentToEnrich.getHostOrganism()){
            getOrganismEnricher().enrich(experimentToEnrich.getHostOrganism(), objectSource.getHostOrganism());
        }
        processOrganism(experimentToEnrich);
    }
}
