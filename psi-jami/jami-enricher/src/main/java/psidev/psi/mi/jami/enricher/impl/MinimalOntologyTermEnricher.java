package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.OntologyTermEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.OntologyTermEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Provides minimum enrichment of the OntologyTerm.
 * Will enrich the full name if it is null and the identifiers.
 * As an enricher, no values from the provided OntologyTerm to enrich will be changed.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/05/13
 */
public class MinimalOntologyTermEnricher extends AbstractMIEnricher<OntologyTerm> implements OntologyTermEnricher{

    private CvTermEnricher cvEnricher = null;

    private Map<OntologyTerm, OntologyTerm> processedTerms;

    /**
     * A constructor matching super.
     * @param cvTermFetcher The fetcher to initiate the enricher with.
     *                      If null, an illegal state exception will be thrown at the next enrichment.
     */
    public MinimalOntologyTermEnricher(OntologyTermFetcher cvTermFetcher) {
        this.cvEnricher = new MinimalCvTermEnricher(cvTermFetcher);
        this.processedTerms = new IdentityHashMap<OntologyTerm, OntologyTerm>();
    }

    protected MinimalOntologyTermEnricher(CvTermEnricher cvEnricher) {
        if (cvEnricher == null){
           throw new IllegalArgumentException("The cv term enricher cannot be null in ontology term enricher");
        }
        this.cvEnricher = cvEnricher;
        this.processedTerms = new IdentityHashMap<OntologyTerm, OntologyTerm>();
    }

    /**
     * The fetcher to be used for used for fetcher.
     * @return  The fetcher which is being used for fetching.
     */
    public OntologyTermFetcher getOntologyTermFetcher() {
        return (OntologyTermFetcher)this.cvEnricher.getCvTermFetcher();
    }

    /**
     * The ontologyTermEnricherListener to be used.
     * It will be fired at all points where a change is made to the cvTerm
     * @param listener  The listener to use. Can be null.
     */
    public void setOntologyTermEnricherListener(OntologyTermEnricherListener listener) {
        this.cvEnricher.setCvTermEnricherListener(listener);
    }
    /**
     * The current OntologyTermEnricherListener.
     * @return  the current listener. May be null.
     */
    public OntologyTermEnricherListener getOntologyTermEnricherListener() {
        return (OntologyTermEnricherListener)this.cvEnricher.getCvTermEnricherListener();
    }

    @Override
    public void enrich(OntologyTerm objectToEnrich) throws EnricherException {
        this.processedTerms.clear();
        this.processedTerms.put(objectToEnrich, objectToEnrich);
        super.enrich(objectToEnrich);
    }

    /**
     * A method that can be overridden to add to or change the behaviour of enrichment without effecting fetching.
     * @param cvTermToEnrich the CvTerm to enrich
     */
    protected void processOntologyTerm(OntologyTerm cvTermToEnrich, OntologyTerm cvTermFetched) throws EnricherException {
        // process parents
        processParents(cvTermToEnrich, cvTermFetched);
        // process children
        processChildren(cvTermToEnrich, cvTermFetched);
    }

    protected void processChildren(OntologyTerm cvTermToEnrich, OntologyTerm cvTermFetched) throws EnricherException {
        mergeOntologyTerms(cvTermToEnrich, cvTermToEnrich.getParents(), cvTermFetched.getParents(), false, true);
    }

    protected void processParents(OntologyTerm cvTermToEnrich, OntologyTerm cvTermFetched) throws EnricherException {
        mergeOntologyTerms(cvTermToEnrich, cvTermToEnrich.getChildren(), cvTermFetched.getChildren(), false, false);
    }

    @Override
    protected void onEnrichedVersionNotFound(OntologyTerm cvTermToEnrich) {
        if(getOntologyTermEnricherListener() != null)
            getOntologyTermEnricherListener().onEnrichmentComplete(cvTermToEnrich, EnrichmentStatus.FAILED, "The ontology term does not exist.");
    }

    @Override
    public void enrich(OntologyTerm cvTermToEnrich, OntologyTerm cvTermFetched) throws EnricherException {
        this.cvEnricher.enrich(cvTermToEnrich, cvTermFetched);
        processOntologyTerm(cvTermToEnrich, cvTermFetched);
        if(getOntologyTermEnricherListener() != null) getOntologyTermEnricherListener().onEnrichmentComplete(cvTermToEnrich, EnrichmentStatus.SUCCESS, "Ontology term enriched successfully.");
    }

    @Override
    public OntologyTerm find(OntologyTerm objectToEnrich) throws EnricherException {
        return (OntologyTerm)((AbstractMIEnricher<CvTerm>)this.cvEnricher).find(objectToEnrich);
    }

    /**
     * Takes two lists of ontology terms and produces a list of those to add and those to remove.
     *
     * It will add in toEnrichTerms all xref from fetchedTerms that are not there. It will also remove extra identifiers from toEnrichTerms
     * if remove boolean is true.
     *
     *
     * @param termToEnrich     The object to enrich
     * @param fetchedTerms      The new terms to be added.
     * @param remove: if true, we remove terms that are not in enriched list
     */
    protected void mergeOntologyTerms(OntologyTerm termToEnrich, Collection<OntologyTerm> toEnrichTerms, Collection<OntologyTerm> fetchedTerms , boolean remove,boolean isParentCollection) throws EnricherException {

        Iterator<OntologyTerm> termIterator = toEnrichTerms.iterator();
        // remove xrefs in toEnrichXrefs that are not in fetchedXrefs
        while(termIterator.hasNext()){
            OntologyTerm term = termIterator.next();
            boolean containsTerm = false;
            for (OntologyTerm term2 : fetchedTerms){
                // identical terms
                if (DefaultCvTermComparator.areEquals(term, term2)){
                    containsTerm = true;
                    // enrich terms that are here
                    if (!this.processedTerms.containsKey(term)){
                        this.processedTerms.put(term, term);
                        this.cvEnricher.enrich(term, term2);
                    }
                    break;
                }
            }
            // remove term not in second list
            if (remove && !containsTerm){
                termIterator.remove();
                if (getOntologyTermEnricherListener() != null){
                    if (isParentCollection){
                        getOntologyTermEnricherListener().onRemovedParent(termToEnrich, term);
                    }
                    else{
                        getOntologyTermEnricherListener().onRemovedChild(termToEnrich, term);
                    }
                }
            }
        }

        // add terms from fetchedTerms that are not in toEnrichTerm
        termIterator = fetchedTerms.iterator();
        while(termIterator.hasNext()){
            OntologyTerm term = termIterator.next();
            boolean containsTerm = false;
            for (OntologyTerm term2 : toEnrichTerms){
                // identical terms
                if (DefaultCvTermComparator.areEquals(term, term2)){
                    containsTerm = true;
                    break;
                }
            }
            // add missing xref not in second list
            if (!containsTerm){
                toEnrichTerms.add(term);
                if (getOntologyTermEnricherListener() != null){
                    if (isParentCollection){
                        getOntologyTermEnricherListener().onAddedParent(termToEnrich, term);
                    }
                    else{
                        getOntologyTermEnricherListener().onAddedChild(termToEnrich, term);
                    }
                }
            }
        }
    }
}
