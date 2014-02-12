package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.ParticipantEvidenceEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExperimentalEntity;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public class MinimalParticipantEvidenceEnricher<P extends ExperimentalEntity, F extends FeatureEvidence> extends MinimalParticipantEnricher<P , F> {

    @Override
    protected void processOtherProperties(P participantEvidenceToEnrich)
            throws EnricherException {

        if(getCvTermEnricher() != null){
            processExperimentalRole(participantEvidenceToEnrich);
            processIdentificationMethods(participantEvidenceToEnrich);
        }
    }

    protected void processIdentificationMethods(P participantEvidenceToEnrich) throws EnricherException {
        if (!participantEvidenceToEnrich.getIdentificationMethods().isEmpty()){
            getCvTermEnricher().enrich(participantEvidenceToEnrich.getIdentificationMethods());
        }
    }

    protected void processExperimentalRole(P participantEvidenceToEnrich) throws EnricherException {
        if (participantEvidenceToEnrich.getExperimentalRole() != null){
            getCvTermEnricher().enrich(participantEvidenceToEnrich.getExperimentalRole());
        }
    }

    @Override
    protected void processOtherProperties(P participantEvidenceToEnrich, P objectSource)
            throws EnricherException {

        // exp roles
        processExperimentalRole(participantEvidenceToEnrich, objectSource);
        // exp identifications
        processIdentificationMethods(participantEvidenceToEnrich, objectSource);

        processOtherProperties(participantEvidenceToEnrich);
    }

    protected void processIdentificationMethods(P participantEvidenceToEnrich, P objectSource) throws EnricherException {
        mergeIdentificationMethods(participantEvidenceToEnrich, participantEvidenceToEnrich.getIdentificationMethods(), objectSource.getIdentificationMethods(), false);

        processIdentificationMethods(participantEvidenceToEnrich);
    }

    protected void processExperimentalRole(P participantEvidenceToEnrich, P objectSource) throws EnricherException {
        // nothing to do

        processExperimentalRole(participantEvidenceToEnrich);
    }

    protected void mergeIdentificationMethods(P termToEnrich, Collection<CvTerm> toEnrichTerms, Collection<CvTerm> fetchedTerms , boolean remove) throws EnricherException {

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
                    ((ParticipantEvidenceEnricherListener)getParticipantEnricherListener()).onRemovedIdentificationMethod(termToEnrich, term);
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
                    ((ParticipantEvidenceEnricherListener)getParticipantEnricherListener()).onAddedIdentificationMethod(termToEnrich, term);
                }
            }
        }
    }
}
