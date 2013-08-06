package psidev.psi.mi.jami.enricher.impl.cvterm.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.listener.CvTermChangeListener;
import psidev.psi.mi.jami.model.CvTerm;

/**
 * An extension of the CvTermChangeListener
 * with specific methods related to the process of enriching.
 * Each method will be fired after the change has been made to the CvTerm
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public interface CvTermEnricherListener
        extends CvTermChangeListener , EnricherListener<CvTerm>{

    /**
     * Fired upon the completion of a CvTerm enrichment.
     * @param cvTerm    The CvTerm which was being enriched
     * @param status    The status of the enrichment
     * @param message   A message containing additional information if any is provided.
     */
    public void onEnrichmentComplete(CvTerm cvTerm, EnrichmentStatus status , String message);

}
