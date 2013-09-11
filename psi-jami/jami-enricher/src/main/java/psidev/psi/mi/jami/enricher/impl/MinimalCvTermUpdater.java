package psidev.psi.mi.jami.enricher.impl;


import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CvTerm;


/**
 * Provides minimum updating of the CvTerm.
 * Will update the short name, full name and xrefs of the CvTerm to enrich.
 * As an updater, values from the provided CvTerm to enrich may be overwritten.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public class MinimalCvTermUpdater extends MinimalCvTermEnricher{

    /**
     * A constructor matching super.
     * @param cvTermFetcher The fetcher to initiate the enricher with.
     *                      If null, an illegal state exception will be thrown at the next enrichment.
     */
    public MinimalCvTermUpdater(CvTermFetcher cvTermFetcher) {
        super(cvTermFetcher);
    }

    /**
     * A method that can be overridden to add to or change the behaviour of enrichment without effecting fetching.
     * @param cvTermToEnrich the CvTerm to enrich
     */
    @Override
    protected void processCvTerm(CvTerm cvTermToEnrich, CvTerm cvTermFetched){
        super.processCvTerm(cvTermToEnrich, cvTermFetched);

        // == Short Name ====================================================================
        processShortabel(cvTermToEnrich, cvTermFetched);
    }

    protected void processShortabel(CvTerm cvTermToEnrich, CvTerm cvTermFetched) {
        if(cvTermFetched.getShortName() != null
                && ! cvTermFetched.getShortName().equalsIgnoreCase(cvTermToEnrich.getShortName())){

            String oldValue = cvTermToEnrich.getShortName();
            cvTermToEnrich.setShortName(cvTermFetched.getShortName());
            if (getCvTermEnricherListener() != null)
                getCvTermEnricherListener().onShortNameUpdate(cvTermToEnrich, oldValue);
        }
    }

    @Override
    protected void processIdentifiers(CvTerm cvTermToEnrich, CvTerm cvTermFetched) {
        EnricherUtils.mergeXrefs(cvTermToEnrich, cvTermToEnrich.getIdentifiers(), cvTermFetched.getIdentifiers(), true, true,
                getCvTermEnricherListener(), getCvTermEnricherListener());
    }

    @Override
    protected void processFullName(CvTerm cvTermToEnrich, CvTerm cvTermFetched) {
        // == Full Name ======================================================================
        if((cvTermFetched.getFullName() != null && !cvTermFetched.getFullName().equals(cvTermToEnrich.getFullName()))
             || (cvTermFetched.getFullName() == null
                && cvTermFetched != null)){

            String oldValue = cvTermToEnrich.getFullName();
            cvTermToEnrich.setFullName(cvTermFetched.getFullName());
            if (getCvTermEnricherListener() != null)
                getCvTermEnricherListener().onFullNameUpdate(cvTermToEnrich, oldValue);
        }
    }
}
