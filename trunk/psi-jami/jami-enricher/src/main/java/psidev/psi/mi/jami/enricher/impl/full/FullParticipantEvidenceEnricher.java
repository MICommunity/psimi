package psidev.psi.mi.jami.enricher.impl.full;


import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalParticipantEvidenceEnricher;
import psidev.psi.mi.jami.enricher.listener.ParticipantEvidenceEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExperimentalEntity;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public class FullParticipantEvidenceEnricher<P extends ExperimentalEntity> extends MinimalParticipantEvidenceEnricher<P> {

    @Override
    public void processOtherProperties(P participantEvidenceToEnrich)
            throws EnricherException {
        super.processOtherProperties(participantEvidenceToEnrich);

        if(!participantEvidenceToEnrich.getExperimentalPreparations().isEmpty() && getCvTermEnricher() != null){
            getCvTermEnricher().enrich(participantEvidenceToEnrich.getExperimentalPreparations());
        }
    }

    @Override
    public void processOtherProperties(P participantEvidenceToEnrich, P objectSource) throws EnricherException {
        super.processOtherProperties(participantEvidenceToEnrich, objectSource);

        // process xrefs
        processXrefs(participantEvidenceToEnrich, objectSource);
        // process confidences
        processConfidences(participantEvidenceToEnrich, objectSource);
        // process parameters
        processParameters(participantEvidenceToEnrich, objectSource);
        // process experimental preparation
        processExperimentalPreparations(participantEvidenceToEnrich, objectSource);
    }

    protected void processExperimentalPreparations(P participantEvidenceToEnrich, P objectSource) throws EnricherException {
        mergeExperimentalPreparations(participantEvidenceToEnrich, participantEvidenceToEnrich.getExperimentalPreparations(), objectSource.getExperimentalPreparations(),
                false);
    }

    protected void processParameters(P participantEvidenceToEnrich, P objectSource) {
        EnricherUtils.mergeParameters(participantEvidenceToEnrich, participantEvidenceToEnrich.getParameters(), objectSource.getParameters(), false,
                (getParticipantEnricherListener() instanceof ParticipantEvidenceEnricherListener ? (ParticipantEvidenceEnricherListener)getParticipantEnricherListener() : null));
    }

    protected void processConfidences(P participantEvidenceToEnrich, P objectSource) {
        EnricherUtils.mergeConfidences(participantEvidenceToEnrich, participantEvidenceToEnrich.getConfidences(), objectSource.getConfidences(), false,
                (getParticipantEnricherListener() instanceof ParticipantEvidenceEnricherListener ? (ParticipantEvidenceEnricherListener)getParticipantEnricherListener() : null));
    }

    protected void processXrefs(P participantEvidenceToEnrich, P objectSource) {
        EnricherUtils.mergeXrefs(participantEvidenceToEnrich, participantEvidenceToEnrich.getXrefs(), objectSource.getXrefs(), false, false,
                getParticipantEnricherListener(), null);
    }

    protected void mergeExperimentalPreparations(P termToEnrich, Collection<CvTerm> toEnrichTerms, Collection<CvTerm> fetchedTerms , boolean remove) throws EnricherException {

        Iterator<CvTerm> termIterator = toEnrichTerms.iterator();
        // remove xrefs in toEnrichXrefs that are not in fetchedXrefs
        while(termIterator.hasNext()){
            CvTerm term = termIterator.next();
            boolean containsTerm = false;
            for (CvTerm term2 : fetchedTerms){
                // identical terms
                if (DefaultCvTermComparator.areEquals(term, term2)){
                    containsTerm = true;
                    // enrich terms that are here
                    getCvTermEnricher().enrich(term, term2);
                    break;
                }
            }
            // remove term not in second list
            if (remove && !containsTerm){
                termIterator.remove();
                if (getParticipantEnricherListener() instanceof ParticipantEvidenceEnricherListener){
                    ((ParticipantEvidenceEnricherListener)getParticipantEnricherListener()).onRemovedExperimentalPreparation(termToEnrich, term);
                }
            }
        }

        // add terms from fetchedTerms that are not in toEnrichTerm
        termIterator = fetchedTerms.iterator();
        while(termIterator.hasNext()){
            CvTerm term = termIterator.next();
            boolean containsTerm = false;
            for (CvTerm term2 : toEnrichTerms){
                // identical terms
                if (DefaultCvTermComparator.areEquals(term, term2)){
                    if (getCvTermEnricher() != null){
                        getCvTermEnricher().enrich(term2, term);
                    }
                    containsTerm = true;
                    break;
                }
            }
            // add missing xref not in second list
            if (!containsTerm){
                toEnrichTerms.add(term);
                if (getParticipantEnricherListener() instanceof ParticipantEvidenceEnricherListener){
                    ((ParticipantEvidenceEnricherListener)getParticipantEnricherListener()).onAddedExperimentalPreparation(termToEnrich, term);
                }
            }
        }
    }
}
