package psidev.psi.mi.jami.enricher.util;

import psidev.psi.mi.jami.enricher.*;
import psidev.psi.mi.jami.enricher.listener.protein.ProteinEnricherListener;
import psidev.psi.mi.jami.enricher.listener.protein.ProteinEnricherListenerManager;

/**
 * Utilities for enrichers.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 10/07/13
 */
public class EnricherUtils {

    /* Characters to be used for new rows, new columns, blank cells */
    public static final String NEW_LINE = "\n";
    public static final String NEW_EVENT = "\t";
    public static final String BLANK_SPACE = "-";

    /**
     * Adds the FeatureEnricher as a listener of the ProteinEnricher.
     *
     * If the ProteinEnricher already has a listener, it is checked to see if it is the featureEnricher.
     * If so, no changes need to be made.
     * If it is not the featureEnricher, it is checked to see if it is a listenerManager,
     * if so, the feature enricher is added to the manager.
     * If not, a new manager is created and both listeners are added.
     *
     * @param featureEnricher   The feature enricher to be added to the protein enricher
     * @param proteinEnricher   The protein enricher to have a feature enricher
     */
    public static void linkFeatureEnricherToProteinEnricher(
            FeatureEnricher featureEnricher , ProteinEnricher proteinEnricher ){
        // Do nothing if either is null
        if(featureEnricher == null || proteinEnricher == null ) return;

        // Do nothing if the feature enricher is already the listener
        if(featureEnricher == proteinEnricher.getProteinEnricherListener()) return;

        // Check the feature enricher listens
        if(featureEnricher instanceof ProteinListeningFeatureEnricher){
            // Cast the feature enricher
            ProteinListeningFeatureEnricher listeningFeatureEnricher = (ProteinListeningFeatureEnricher) featureEnricher;

            ProteinEnricherListener listener = proteinEnricher.getProteinEnricherListener();
            if(listener != null ) {
                // If the listener is a manger, add it to that
                if(listener instanceof ProteinEnricherListenerManager){
                    ProteinEnricherListenerManager manager = (ProteinEnricherListenerManager) listener;
                    manager.addEnricherListener(listeningFeatureEnricher);
                }
                // Else create a new manager and add the current listener
                else {
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
}
