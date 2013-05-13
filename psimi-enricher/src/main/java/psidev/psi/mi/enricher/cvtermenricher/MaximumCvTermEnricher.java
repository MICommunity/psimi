package psidev.psi.mi.enricher.cvtermenricher;

import psidev.psi.mi.enricher.cvtermenricher.enricherlistener.event.AdditionEvent;
import psidev.psi.mi.enricher.cvtermenricher.enricherlistener.event.OverwriteEvent;
import psidev.psi.mi.enricher.cvtermenricher.exception.EnrichmentException;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 14:16
 */
public class MaximumCvTermEnricher
        extends MinimumCvTermEnricher
        implements CvTermEnricher{


    public MaximumCvTermEnricher() throws EnrichmentException {
    }

    public void enrichCvTerm(CvTerm cvTermMaster) throws EnrichmentException{
        EnrichmentReport report = new EnrichmentReport();
        CvTerm cvTermEnriched = getEnrichedForm(cvTermMaster, report);

        if(cvTermEnriched.getFullName() != null
                && cvTermMaster.getFullName() != cvTermEnriched.getFullName()){
            String oldname =  cvTermMaster.getFullName();
            cvTermMaster.setFullName(cvTermEnriched.getFullName());
            fireOverwriteEvent(new OverwriteEvent(report.getIdentity(), report.getIdentityType(), "FullName", oldname, cvTermMaster.getFullName()));
        }

        if(cvTermMaster.getFullName() == null
                && cvTermEnriched.getFullName() != null){
            cvTermMaster.setFullName(cvTermEnriched.getFullName());
            fireAdditionEvent(new AdditionEvent(report.getIdentity(), report.getIdentityType(), "FullName", cvTermMaster.getFullName()));
        }


    }

    public void enrichCvTerms(Collection<CvTerm> cvTermMasters) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
