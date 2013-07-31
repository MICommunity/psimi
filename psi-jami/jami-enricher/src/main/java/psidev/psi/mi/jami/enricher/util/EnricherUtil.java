package psidev.psi.mi.jami.enricher.util;


import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.enricher.*;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherListenerManager;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 10/07/13
 */
public class EnricherUtil {



    /**
     * Adds the FeatureEnricher as a listener of the ProteinEnricher.
     *
     * If the ProteinEnricher already has a listener, if is checked to see if it is the featureEnricher.
     * if so, no changes need to be made.
     * If it is not the featureEnricher, it is checked to see if it is a listenerManager,
     * if so, the feature enricher is added to the manager.
     * If not, a new manager is created and both listeners are added.
     *
     * @param enricher
     */
    public static void linkFeatureEnricherToProteinEnricher(InteractionEnricher enricher){

        FeatureEnricher featureEnricher = enricher.getParticipantEnricher().getFeatureEnricher();
        ProteinEnricher proteinEnricher = enricher.getParticipantEnricher().getProteinEnricher();
        linkFeatureEnricherToProteinEnricher(featureEnricher , proteinEnricher);

    }

    public static void linkFeatureEnricherToProteinEnricher(
            FeatureEnricher featureEnricher , ProteinEnricher proteinEnricher ){

        if(featureEnricher == null || proteinEnricher == null ) return;

        if(featureEnricher instanceof ProteinListeningFeatureEnricher){
            ProteinListeningFeatureEnricher listeningFeatureEnricher =
                    (ProteinListeningFeatureEnricher) featureEnricher;

            ProteinEnricherListener listener = proteinEnricher.getProteinEnricherListener();
            if(listener != null && listener != featureEnricher) {
                if(listener instanceof ProteinEnricherListenerManager){
                    ProteinEnricherListenerManager manager = (ProteinEnricherListenerManager) listener;
                    manager.addEnricherListener(listeningFeatureEnricher);
                } else {
                    proteinEnricher.setProteinEnricherListener(
                            new ProteinEnricherListenerManager(
                                    listener,
                                    listeningFeatureEnricher ));
                }
            } else {
                proteinEnricher.setProteinEnricherListener(listeningFeatureEnricher);
            }
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
