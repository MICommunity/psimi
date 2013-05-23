package psidev.psi.mi.jami.enricher.cvterm;

import psidev.psi.mi.jami.enricher.enricher.CvTermEnricher;
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
     * If update takes place, the master will be edited.
     *
     * @param cvTermMaster  a CvTerm to update
     * @throws EnrichmentException
     */
    public void enrichCvTerm(CvTerm cvTermMaster)
            throws EnrichmentException {

        CvTerm cvTermEnriched = getEnrichedForm(cvTermMaster);
        if(cvTermEnriched == null){
            //log.debug("cvTerm was null");
            return;
        }
        //Enrich and update
        enrichCvTerm(cvTermMaster, cvTermEnriched);
        //Fire the report
        fireEnricherEvent(enricherEvent);
    }


    /**
     * Compares two CvTerms and updates the master with any fields that it is missing or mismatched.
     * The minimum enricher is run first to add any missing fields,
     * then, the full name and short name are overwritten.
     * @param cvTermMaster      The cvTerm to be updated
     * @param cvTermEnriched    The cvTerm containing the data to update the master with.
     * @throws EnrichmentException
     */
    public void enrichCvTerm(CvTerm cvTermMaster, CvTerm cvTermEnriched)
            throws EnrichmentException{

        super.enrichCvTerm(cvTermMaster, cvTermEnriched);

        //Check full name
        if(cvTermEnriched.getFullName() != null){
            if(!cvTermMaster.getFullName().equals(cvTermEnriched.getFullName())){
                String oldname =  cvTermMaster.getFullName();
                cvTermMaster.setFullName(cvTermEnriched.getFullName());
                addOverwriteReport(new OverwriteReport(
                        "FullName", cvTermMaster.getFullName(), oldname));
            }
        }

        //Overwrite shortname
        if(!cvTermMaster.getShortName().equals(cvTermEnriched.getShortName())){
            String oldname =  cvTermMaster.getShortName();
            cvTermMaster.setShortName(cvTermEnriched.getShortName());
            addOverwriteReport(new OverwriteReport(
                    "ShortName", cvTermMaster.getShortName(), oldname));
        }
    }

    public void enrichCvTerms(Collection<CvTerm> cvTermMasters) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
