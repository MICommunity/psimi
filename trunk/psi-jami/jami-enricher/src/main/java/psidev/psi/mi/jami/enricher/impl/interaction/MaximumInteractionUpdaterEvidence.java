package psidev.psi.mi.jami.enricher.impl.interaction;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.impl.cvterm.MaximumCvTermUpdater;
import psidev.psi.mi.jami.enricher.impl.interaction.MaximumInteractionUpdater;
import psidev.psi.mi.jami.model.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class MaximumInteractionUpdaterEvidence
        extends MaximumInteractionEnricher<InteractionEvidence, ParticipantEvidence, FeatureEvidence> {





    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new MaximumCvTermUpdater();
        return cvTermEnricher;
    }
}
