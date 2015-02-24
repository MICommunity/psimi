package psidev.psi.mi.jami.enricher.impl.minimal;


import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CvTerm;


/**
 * Provides minimal update of cv term.
 *
 * - update shortName of CvTerm if different from the shortname of the fetched CvTerm
 * - update fullname of CvTerm if different from the fullName of the fetched CvTerm
 * - update identifiers of CvTerm. It will add missing identifiers and remove any existing identifiers that are not in the fetched CvTerm using DefaultXrefComparator
 *
 * It will ignore all other properties of a CvTerm
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public class MinimalCvTermUpdater<C extends CvTerm> extends MinimalCvTermEnricher<C>{

    /**
     * A constructor matching super.
     * @param cvTermFetcher The fetcher to initiate the enricher with.
     *                      If null, an illegal state exception will be thrown at the next enrichment.
     */
    public MinimalCvTermUpdater(CvTermFetcher<C> cvTermFetcher) {
        super(cvTermFetcher);
    }

    /**
     * A method that can be overridden to add to or change the behaviour of enrichment without effecting fetching.
     * @param cvTermToEnrich the CvTerm to enrich
     */
    @Override
    public void processCvTerm(C cvTermToEnrich, C cvTermFetched) throws EnricherException {
        super.processCvTerm(cvTermToEnrich, cvTermFetched);

        // == Short Name ====================================================================
        processShortabel(cvTermToEnrich, cvTermFetched);
    }

    protected void processShortabel(C cvTermToEnrich, C cvTermFetched) throws EnricherException{
        if(cvTermFetched.getShortName() != null
                && ! cvTermFetched.getShortName().equalsIgnoreCase(cvTermToEnrich.getShortName())){

            String oldValue = cvTermToEnrich.getShortName();
            cvTermToEnrich.setShortName(cvTermFetched.getShortName());
            if (getCvTermEnricherListener() != null)
                getCvTermEnricherListener().onShortNameUpdate(cvTermToEnrich, oldValue);
        }
    }

    @Override
    protected void processIdentifiers(C cvTermToEnrich, C cvTermFetched) throws EnricherException{
        EnricherUtils.mergeXrefs(cvTermToEnrich, cvTermToEnrich.getIdentifiers(), cvTermFetched.getIdentifiers(), true, true,
                getCvTermEnricherListener(), getCvTermEnricherListener());
    }

    @Override
    protected void processFullName(C cvTermToEnrich, C cvTermFetched) throws EnricherException{
        // == Full Name ======================================================================
        if((cvTermFetched.getFullName() != null && !cvTermFetched.getFullName().equals(cvTermToEnrich.getFullName()))
             || (cvTermFetched.getFullName() == null
                && cvTermToEnrich.getFullName() != null)){

            String oldValue = cvTermToEnrich.getFullName();
            cvTermToEnrich.setFullName(cvTermFetched.getFullName());
            if (getCvTermEnricherListener() != null)
                getCvTermEnricherListener().onFullNameUpdate(cvTermToEnrich, oldValue);
        }
    }
}
