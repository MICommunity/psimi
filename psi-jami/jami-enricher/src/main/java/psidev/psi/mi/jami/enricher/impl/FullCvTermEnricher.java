package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.alias.DefaultAliasComparator;
import psidev.psi.mi.jami.utils.comparator.annotation.DefaultAnnotationComparator;

import java.util.Collection;
import java.util.Iterator;

/**
 * Provides maximum enrichment of the CvTerm.
 * Will enrich all aspects covered by the minimum enricher as well as enriching the Aliases.
 * As an enricher, no values from the provided CvTerm to enrich will be changed.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public class FullCvTermEnricher
        extends MinimalCvTermEnricher{

    /**
     * A constructor matching super.
     * @param cvTermFetcher The fetcher to initiate the enricher with.
     *                      If null, an illegal state exception will be thrown at the next enrichment.
     */
    public FullCvTermEnricher(CvTermFetcher cvTermFetcher) {
        super(cvTermFetcher);
    }

    /**
     * Strategy for the cvTerm enrichment.
     * This method can be overwritten to change how the cvTerm is enriched.
     * @param cvTermToEnrich   The protein to be enriched.
     */
    @Override
    protected void processCvTerm(CvTerm cvTermToEnrich, CvTerm termFetched){
        processMinimalUpdates(cvTermToEnrich, termFetched);

        // == Xrefs =======================================================
        processXrefs(cvTermToEnrich, termFetched);

        // == Synonyms =======================================================
        processSynonyms(cvTermToEnrich, termFetched);

        // == Annotations =======================================================
        processAnnotations(cvTermToEnrich, termFetched);
    }

    protected void processMinimalUpdates(CvTerm cvTermToEnrich, CvTerm termFetched) {
        super.processCvTerm(cvTermToEnrich, termFetched);
    }

    protected void processAnnotations(CvTerm cvTermToEnrich, CvTerm termFetched) {
        mergeAnnotations(cvTermToEnrich, termFetched.getAnnotations(), false);
    }

    protected void processSynonyms(CvTerm cvTermToEnrich, CvTerm termFetched) {
        mergeSynonyms(cvTermToEnrich, termFetched.getSynonyms(), false);
    }

    protected void processXrefs(CvTerm cvTermToEnrich, CvTerm cvTermFetched) {
        mergeXrefs(cvTermToEnrich, cvTermFetched.getXrefs(), false, false);
    }

    protected void mergeAnnotations(CvTerm termToEnrich, Collection<Annotation> fetchedAnnotations , boolean remove){
        Collection<Annotation> toEnrichAnnotations = termToEnrich.getAnnotations();
        Iterator<Annotation> annotIterator = toEnrichAnnotations.iterator();
        // remove annotation in toEnrichAnnotations that are not in fetchedAnnotations
        while(annotIterator.hasNext()){
            Annotation annotation = annotIterator.next();
            boolean containsAnnot = false;
            for (Annotation annotation2 : toEnrichAnnotations){
                // identical annotations
                if (DefaultAnnotationComparator.areEquals(annotation, annotation2)){
                    containsAnnot = true;
                    break;
                }
            }
            // remove annotation not in second list
            if (remove && !containsAnnot){
                annotIterator.remove();
                if (getCvTermEnricherListener() != null){
                    getCvTermEnricherListener().onRemovedAnnotation(termToEnrich, annotation);
                }
            }
        }

        // add annotations from fetchedAnnotations that are not in toEnrichAnnotations
        annotIterator = fetchedAnnotations.iterator();
        while(annotIterator.hasNext()){
            Annotation annotation = annotIterator.next();
            boolean containsAnnot = false;
            for (Annotation annotation2 : toEnrichAnnotations){
                // identical aliases
                if (DefaultAnnotationComparator.areEquals(annotation, annotation2)){
                    containsAnnot = true;
                    break;
                }
            }
            // add missing annotation not in second list
            if (!containsAnnot){
                toEnrichAnnotations.add(annotation);
                if (getCvTermEnricherListener() != null){
                    getCvTermEnricherListener().onAddedAnnotation(termToEnrich, annotation);
                }
            }
        }
    }

    protected void mergeSynonyms(CvTerm termToEnrich, Collection<Alias> fetchedAliases , boolean remove){
        Collection<Alias> toEnrichAliases = termToEnrich.getSynonyms();
        Iterator<Alias> aliasIterator = toEnrichAliases.iterator();
        // remove aliases in toEnrichAliases that are not in fetchedAliases
        while(aliasIterator.hasNext()){
            Alias alias = aliasIterator.next();
            boolean containsAlias = false;
            for (Alias alias2 : toEnrichAliases){
                // identical aliases
                if (DefaultAliasComparator.areEquals(alias, alias2)){
                    containsAlias = true;
                    break;
                }
            }
            // remove alias not in second list
            if (remove && !containsAlias){
                aliasIterator.remove();
                if (getCvTermEnricherListener() != null){
                    getCvTermEnricherListener().onRemovedSynonym(termToEnrich, alias);
                }
            }
        }

        // add xrefs from fetchedXrefs that are not in toEnrichXref
        aliasIterator = fetchedAliases.iterator();
        while(aliasIterator.hasNext()){
            Alias alias = aliasIterator.next();
            boolean containsAlias = false;
            for (Alias alias2 : toEnrichAliases){
                // identical aliases
                if (DefaultAliasComparator.areEquals(alias, alias2)){
                    containsAlias = true;
                    break;
                }
            }
            // add missing xref not in second list
            if (!containsAlias){
                toEnrichAliases.add(alias);
                if (getCvTermEnricherListener() != null){
                    getCvTermEnricherListener().onAddedSynonym(termToEnrich, alias);
                }
            }
        }
    }
}
