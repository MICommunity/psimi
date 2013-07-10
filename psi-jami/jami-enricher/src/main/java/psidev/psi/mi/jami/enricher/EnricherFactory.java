package psidev.psi.mi.jami.enricher;


import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherListenerManager;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 10/07/13
 */
public class EnricherFactory {

    public void linkFeatureToProtein(InteractionEnricher enricher){

        FeatureEnricher featureEnricher = enricher.getParticipantEnricher().getFeatureEnricher();
        ProteinEnricherListener listener = enricher.getParticipantEnricher().getProteinEnricher().getProteinEnricherListener();
        if(listener != null) {
            ProteinEnricherListenerManager manager = new ProteinEnricherListenerManager();
            manager.addEnricherListener(listener);
            manager.addEnricherListener(featureEnricher);
            enricher.getParticipantEnricher().getProteinEnricher().setProteinEnricherListener(manager);
        } else {
            enricher.getParticipantEnricher().getProteinEnricher().setProteinEnricherListener(featureEnricher);
        }
    }

    public void unifyCvTermEnricher(InteractionEnricher enricher){
        CvTermEnricher cvTermEnricher = enricher.getCvTermEnricher();
        enricher.getParticipantEnricher().setCvTermEnricher(cvTermEnricher);
        enricher.getParticipantEnricher().getFeatureEnricher().setCvTermEnricher(cvTermEnricher);
    }

}
