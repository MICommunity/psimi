package psidev.psi.mi.jami.enricher.impl;

import org.apache.commons.collections.map.IdentityMap;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.OntologyTermEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.CvTermEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.OntologyTermEnricherListener;
import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

import java.util.Collection;
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

    private CvTermEnricher<OntologyTerm> cvEnricher = null;

    private Map<OntologyTerm, OntologyTerm> processedTerms;

    /**
     * A constructor matching super.
     * @param cvTermFetcher The fetcher to initiate the enricher with.
     *                      If null, an illegal state exception will be thrown at the next enrichment.
     */
    public MinimalOntologyTermEnricher(OntologyTermFetcher cvTermFetcher) {
        this.cvEnricher = new MinimalCvTermEnricher<OntologyTerm>(cvTermFetcher);
        this.processedTerms = new IdentityMap();
    }

    protected MinimalOntologyTermEnricher(CvTermEnricher<OntologyTerm> cvEnricher) {
        if (cvEnricher == null){
           throw new IllegalArgumentException("The cv term enricher cannot be null in ontology term enricher");
        }
        this.cvEnricher = cvEnricher;
        this.processedTerms = new IdentityMap();
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
        // process definition
        processDefinition(cvTermToEnrich, cvTermFetched);
        // process parents
        processParents(cvTermToEnrich, cvTermFetched);
        // process children
        processChildren(cvTermToEnrich, cvTermFetched);
    }

    protected void processDefinition(OntologyTerm cvTermToEnrich, OntologyTerm cvTermFetched) {
        if (cvTermToEnrich.getDefinition() == null && cvTermFetched.getDefinition() != null){
            String oldDef = cvTermToEnrich.getDefinition();
            cvTermToEnrich.setDefinition(cvTermFetched.getDefinition());
            if (getCvTermEnricherListener() instanceof OntologyTermEnricherListener){
                ((OntologyTermEnricherListener)getCvTermEnricherListener()).onDefinitionUpdate(cvTermToEnrich, oldDef);
            }
        }
    }

    protected void processChildren(OntologyTerm cvTermToEnrich, OntologyTerm cvTermFetched) throws EnricherException {
        mergeOntologyTerms(cvTermToEnrich, cvTermToEnrich.getParents(), cvTermFetched.getParents(), false, true);
    }

    protected void processParents(OntologyTerm cvTermToEnrich, OntologyTerm cvTermFetched) throws EnricherException {
        mergeOntologyTerms(cvTermToEnrich, cvTermToEnrich.getChildren(), cvTermFetched.getChildren(), false, false);
    }

    @Override
    protected void onEnrichedVersionNotFound(OntologyTerm cvTermToEnrich) {
        if(getCvTermEnricherListener() != null)
            getCvTermEnricherListener().onEnrichmentComplete(cvTermToEnrich, EnrichmentStatus.FAILED, "The ontology term does not exist.");
    }

    @Override
    public void enrich(OntologyTerm cvTermToEnrich, OntologyTerm cvTermFetched) throws EnricherException {
        this.cvEnricher.enrich(cvTermToEnrich, cvTermFetched);
        processOntologyTerm(cvTermToEnrich, cvTermFetched);
        if(getCvTermEnricherListener() != null) getCvTermEnricherListener().onEnrichmentComplete(cvTermToEnrich, EnrichmentStatus.SUCCESS, "Ontology term enriched successfully.");
    }

    @Override
    public OntologyTerm find(OntologyTerm objectToEnrich) throws EnricherException {
        return ((AbstractMIEnricher<OntologyTerm>)this.cvEnricher).find(objectToEnrich);
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
        if (remove){
            while(termIterator.hasNext()){
                OntologyTerm term = termIterator.next();
                boolean containsTerm = false;
                for (OntologyTerm term2 : fetchedTerms){
                    // identical terms
                    if (DefaultCvTermComparator.areEquals(term, term2)){
                        containsTerm = true;
                        break;
                    }
                }
                // remove term not in second list
                if (!containsTerm){
                    termIterator.remove();
                    if (getCvTermEnricherListener() instanceof OntologyTermEnricherListener){
                        if (isParentCollection){
                            ((OntologyTermEnricherListener)getCvTermEnricherListener()).onRemovedParent(termToEnrich, term);
                        }
                        else{
                            ((OntologyTermEnricherListener)getCvTermEnricherListener()).onRemovedChild(termToEnrich, term);
                        }
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
                    // enrich terms that are here
                    if (!this.processedTerms.containsKey(term2)){
                        this.processedTerms.put(term2, term2);
                        this.cvEnricher.enrich(term2, term);
                    }
                    break;
                }
            }
            // add missing xref not in second list
            if (!containsTerm){
                toEnrichTerms.add(term);
                if (getCvTermEnricherListener() instanceof OntologyTermEnricherListener){
                    if (isParentCollection){
                        ((OntologyTermEnricherListener)getCvTermEnricherListener()).onAddedParent(termToEnrich, term);
                    }
                    else{
                        ((OntologyTermEnricherListener)getCvTermEnricherListener()).onAddedChild(termToEnrich, term);
                    }
                }
            }
        }
    }

    public CvTermFetcher<OntologyTerm> getCvTermFetcher() {
        return this.cvEnricher.getCvTermFetcher();
    }

    public void setCvTermEnricherListener(CvTermEnricherListener<OntologyTerm> listener) {
        this.cvEnricher.setCvTermEnricherListener(listener);
    }

    public CvTermEnricherListener<OntologyTerm> getCvTermEnricherListener() {
        return this.cvEnricher.getCvTermEnricherListener();
    }
}
