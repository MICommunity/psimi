package psidev.psi.mi.jami.enricher.impl.cvterm.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.listener.CvTermChangeListener;
import psidev.psi.mi.jami.model.CvTerm;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
public interface CvTermEnricherListener
        extends CvTermChangeListener , EnricherListener{

    public void onCvTermEnriched(CvTerm cvTerm, EnrichmentStatus status , String message);

}
