package psidev.psi.mi.enricher.cvtermenricher;

import psidev.psi.mi.enricher.cvtermenricher.exception.EnrichmentException;
import psidev.psi.mi.enricher.cvtermenricher.listener.EnricherEventProcessorImp;
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
        CvTerm cvTermEnriched = getEnrichedForm(cvTermMaster);
    }

    public void enrichCvTerms(Collection<CvTerm> cvTermMasters) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
