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

    /**
     * Adds the FeatureEnricher as a listener of the ProteinEnricher,
     * creating a listener manager if there is already a listener (which isn't the feature enricher)
     * @param enricher
     */
    public static void linkFeatureToProtein(InteractionEnricher enricher){

        FeatureEnricher featureEnricher = enricher.getParticipantEnricher().getFeatureEnricher();
        ProteinEnricherListener listener = enricher.getParticipantEnricher().getProteinEnricher().getProteinEnricherListener();
        if(listener != null && listener != featureEnricher) {
            ProteinEnricherListenerManager manager = new ProteinEnricherListenerManager();
            manager.addEnricherListener(listener);
            manager.addEnricherListener(featureEnricher);
            enricher.getParticipantEnricher().getProteinEnricher().setProteinEnricherListener(manager);
        } else {
            enricher.getParticipantEnricher().getProteinEnricher().setProteinEnricherListener(featureEnricher);
        }
    }

    /**
     * Takes the interactionEnricher CvTermEnricher and sets it as the enricher for all other cvTerms.
     * @param enricher
     */
    public static void unifyCvTermEnrichers(InteractionEnricher enricher){

        CvTermEnricher cvTermEnricher = enricher.getCvTermEnricher();
        enricher.getParticipantEnricher().setCvTermEnricher(cvTermEnricher);
        enricher.getParticipantEnricher().getFeatureEnricher().setCvTermEnricher(cvTermEnricher);

    }

}
