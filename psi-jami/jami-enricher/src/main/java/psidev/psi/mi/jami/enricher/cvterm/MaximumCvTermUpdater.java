package psidev.psi.mi.jami.enricher.cvterm;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.event.OverwriteReport;
import psidev.psi.mi.jami.enricher.exception.EnrichmentException;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Collection;

/**
 *
 * Date: 13/05/13
 * Time: 14:16
 */
public class MaximumCvTermUpdater
        extends MinimumCvTermEnricher
        implements CvTermEnricher {


    public MaximumCvTermUpdater() throws EnrichmentException {
    }

    /**
     * Enrichment and update of a single CvTerm.
     * If update takes place, the ToEnrich will be edited.
     *
     * @param cvTermToEnrich  a CvTerm to update
     * @throws EnrichmentException
     */
    public void enrichCvTerm(CvTerm cvTermToEnrich)
            throws EnrichmentException {

        CvTerm cvTermEnriched = getEnrichedForm(cvTermToEnrich);
        if(cvTermEnriched == null){
            //log.debug("cvTerm was null");
            return;
        }
        //Enrich and update
        enrichCvTerm(cvTermToEnrich, cvTermEnriched);
        //Fire the report
        fireEnricherEvent(enricherEvent);
    }


    /**
     * Compares two CvTerms and updates the ToEnrich with any fields that it is missing or mismatched.
     * The minimum enricher is run first to add any missing fields,
     * then, the full name and short name are overwritten.
     * @param cvTermToEnrich      The cvTerm to be updated
     * @param cvTermEnriched    The cvTerm containing the data to update the ToEnrich with.
     * @throws EnrichmentException
     */
    public void enrichCvTerm(CvTerm cvTermToEnrich, CvTerm cvTermEnriched)
            throws EnrichmentException{

        super.enrichCvTerm(cvTermToEnrich, cvTermEnriched);

        //Check full name
        if(cvTermEnriched.getFullName() != null){
            if(!cvTermToEnrich.getFullName().equals(cvTermEnriched.getFullName())){
                String oldname =  cvTermToEnrich.getFullName();
                cvTermToEnrich.setFullName(cvTermEnriched.getFullName());
                addOverwriteReport(new OverwriteReport(
                        "FullName", cvTermToEnrich.getFullName(), oldname));
            }
        }

        //Overwrite shortname
        if(!cvTermToEnrich.getShortName().equals(cvTermEnriched.getShortName())){
            String oldname =  cvTermToEnrich.getShortName();
            cvTermToEnrich.setShortName(cvTermEnriched.getShortName());
            addOverwriteReport(new OverwriteReport(
                    "ShortName", cvTermToEnrich.getShortName(), oldname));
        }
    }

    public void enrichCvTerms(Collection<CvTerm> cvTermsToEnrich) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
