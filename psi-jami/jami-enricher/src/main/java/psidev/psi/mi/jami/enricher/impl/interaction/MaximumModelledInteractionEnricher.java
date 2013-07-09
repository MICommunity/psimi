package psidev.psi.mi.jami.enricher.impl.interaction;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.impl.cvterm.MaximumCvTermEnricher;
import psidev.psi.mi.jami.model.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class MaximumModelledInteractionEnricher
    extends MaximumInteractionEnricher <ModelledInteraction, ModelledParticipant, ModelledFeature>{







    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new MaximumCvTermEnricher();
        return cvTermEnricher;
    }
}
