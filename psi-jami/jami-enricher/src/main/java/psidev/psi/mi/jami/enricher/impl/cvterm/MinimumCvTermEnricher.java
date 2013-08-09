package psidev.psi.mi.jami.enricher.impl.cvterm;

import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.util.XrefMerger;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

/**
 * Provides minimum enrichment of the CvTerm.
 * Will enrich the full name if it is null and the identifiers.
 * As an enricher, no values from the provided CvTerm to enrich will be changed.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/05/13
 */
public class MinimumCvTermEnricher
        extends AbstractCvTermEnricher
        implements CvTermEnricher {

    /**
     * A constructor matching super.
     * @param cvTermFetcher The fetcher to initiate the enricher with.
     *                      If null, an illegal state exception will be thrown at the next enrichment.
     */
    public MinimumCvTermEnricher(CvTermFetcher cvTermFetcher) {
        super(cvTermFetcher);
    }

    /**
     * A method that can be overridden to add to or change the behaviour of enrichment without effecting fetching.
     * @param cvTermToEnrich the CvTerm to enrich
     */
    protected void processCvTerm(CvTerm cvTermToEnrich){

        //ShortName not checked - never null

        //FullName
        if(cvTermToEnrich.getFullName() == null
                && cvTermFetched.getFullName() != null){

            cvTermToEnrich.setFullName(cvTermFetched.getFullName());
            if (getCvTermEnricherListener() != null)
                getCvTermEnricherListener().onFullNameUpdate(cvTermToEnrich, null);
        }

        //Identifiers
        if(! cvTermFetched.getIdentifiers().isEmpty()) {

            XrefMerger merger = new XrefMerger();
            merger.merge(cvTermFetched.getIdentifiers() , cvTermToEnrich.getIdentifiers(), true);

            for(Xref xref: merger.getToAdd()){
                cvTermToEnrich.getIdentifiers().add(xref);
                if(getCvTermEnricherListener() != null)
                    getCvTermEnricherListener().onAddedIdentifier(cvTermToEnrich, xref);
            }
        }
    }

}
