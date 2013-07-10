package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.enricher.impl.cvterm.CvTermEnricherMinimum;
import psidev.psi.mi.jami.enricher.impl.feature.FeatureEnricherMinimum;
import psidev.psi.mi.jami.enricher.impl.interaction.ModelledInteractionEnricherMinimum;
import psidev.psi.mi.jami.enricher.impl.participant.ModelledParticipantEnricherMinimum;
import psidev.psi.mi.jami.model.ModelledFeature;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 10/07/13
 */
public class EnricherFactory {

    public void test(){


        ModelledInteractionEnricherMinimum interactionEnricher = new ModelledInteractionEnricherMinimum();
        interactionEnricher.setParticipantEnricher(new ModelledParticipantEnricherMinimum());
        interactionEnricher.setCvTermEnricher(new CvTermEnricherMinimum());
        interactionEnricher.setFeatureEnricher(new FeatureEnricherMinimum<ModelledFeature>());

       // interactionEnricher.getParticipantEnricher().get;



    }

}
