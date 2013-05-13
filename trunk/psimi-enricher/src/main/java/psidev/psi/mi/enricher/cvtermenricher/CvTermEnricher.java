package psidev.psi.mi.enricher.cvtermenricher;

import psidev.psi.mi.enricher.cvtermenricher.exception.EnrichmentException;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 13:13
 */
public interface CvTermEnricher {
    public void enrichCvTerm(CvTerm cvTermMaster)  throws EnrichmentException;
    public void enrichCvTerms(Collection<CvTerm> cvTermMasters);
}
