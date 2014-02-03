package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.listener.FeatureEvidenceChangeListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

import java.util.Collection;
import java.util.Iterator;

/**
 * Minimal enricher for feature evidence
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class MinimalFeatureEvidenceEnricher extends MinimalFeatureEnricher<FeatureEvidence> {

    @Override
    protected void processOtherProperties(FeatureEvidence featureToEnrich) throws EnricherException {
        processDetectionMethods(featureToEnrich);
    }

    @Override
    protected void processOtherProperties(FeatureEvidence featureToEnrich, FeatureEvidence objectSource) throws EnricherException {
        processDetectionMethods(featureToEnrich, objectSource);
    }

    protected void processDetectionMethods(FeatureEvidence featureToEnrich) throws EnricherException {
        if(getCvTermEnricher() != null)
            getCvTermEnricher().enrich(featureToEnrich.getDetectionMethods());
    }

    protected void processDetectionMethods(FeatureEvidence featureToEnrich, FeatureEvidence source) throws EnricherException {
        mergeDetectionMethods(featureToEnrich, featureToEnrich.getDetectionMethods(), source.getDetectionMethods(), false);
        processDetectionMethods(featureToEnrich);
    }

    /**
     * Takes two lists of detection methods and produces a list of those to add and those to remove.
     *
     * It will add in toEnrichTerms all detection methods from fetchedTerms that are not there. It will also remove extra detection methods from toEnrichTerms
     * if remove boolean is true.
     *
     *
     * @param termToEnrich     The object to enrich
     * @param fetchedTerms      The new terms to be added.
     * @param remove: if true, we remove terms that are not in enriched list
     */
    protected void mergeDetectionMethods(FeatureEvidence termToEnrich, Collection<CvTerm> toEnrichTerms, Collection<CvTerm> fetchedTerms , boolean remove) throws EnricherException {

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
                if (getFeatureEnricherListener() instanceof FeatureEvidenceChangeListener){
                    ((FeatureEvidenceChangeListener)getFeatureEnricherListener()).onRemovedDetectionMethod(termToEnrich, term);
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
                    containsTerm = true;
                    break;
                }
            }
            // add missing xref not in second list
            if (!containsTerm){
                toEnrichTerms.add(term);
                if (getFeatureEnricherListener() instanceof FeatureEvidenceChangeListener){
                    ((FeatureEvidenceChangeListener)getFeatureEnricherListener()).onAddedDetectionMethod(termToEnrich, term);
                }
            }
        }
    }
}
