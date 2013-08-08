package psidev.psi.mi.jami.enricher.listener.cvterm;

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

}
