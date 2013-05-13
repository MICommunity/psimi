package psidev.psi.mi.enricher.cvtermenricher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.enricher.cvtermenricher.exception.BadIdentifierException;
import psidev.psi.mi.enricher.cvtermenricher.exception.EnrichmentException;
import psidev.psi.mi.enricher.cvtermenricher.exception.MissingIdentifierException;
import psidev.psi.mi.enricher.cvtermenricher.listener.event.AdditionEvent;
import psidev.psi.mi.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.enricher.cvtermenricher.listener.EnricherEventProcessorImp;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 08/05/13
 * Time: 14:19
 */
public class MinimumCvTermEnricher
        extends EnricherEventProcessorImp
        implements CvTermEnricher{

    private CvTermEnricherUtil util;

    public MinimumCvTermEnricher() throws EnrichmentException {
        util = new CvTermEnricherUtil();
    }

    public void enrichCvTerm(CvTerm cvTermMaster) throws EnrichmentException{
        CvTerm cvTermEnriched = util.getEnrichedForm(cvTermMaster);

        if(cvTermMaster.getFullName() == null
                && cvTermEnriched.getFullName() != null){
            cvTermMaster.setFullName(cvTermEnriched.getFullName());
            fireAdditionEvent(new AdditionEvent("Fullname" , null, cvTermMaster.getFullName()));
        }
    }

    public void enrichCvTerms(Collection<CvTerm> cvTermMasters) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
