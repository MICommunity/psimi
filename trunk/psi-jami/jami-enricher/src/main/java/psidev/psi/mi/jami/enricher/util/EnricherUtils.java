package psidev.psi.mi.jami.enricher.util;

import psidev.psi.mi.jami.enricher.*;
import psidev.psi.mi.jami.enricher.listener.protein.ProteinEnricherListener;
import psidev.psi.mi.jami.enricher.listener.protein.ProteinEnricherListenerManager;
import psidev.psi.mi.jami.listener.IdentifiersChangeListener;
import psidev.psi.mi.jami.listener.XrefsChangeListener;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;

import java.util.Collection;
import java.util.Iterator;

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

    /**
     * Takes two lists of Xrefs and produces a list of those to add and those to remove.
     *
     * It will add in toEnrichXrefs all xref from fetchedXrefs that are not there. It will also remove extra identifiers from toEnrichXrefs
     * if remove boolean is true.
     *
     *
     * @param termToEnrich     The object to enrich
     * @param fetchedXrefs      The new xrefs to be added.
     * @param remove: if true, we remove xrefs that are not in enriched list
     * @param isIdentifier if true compare identifiers, otherwise xrefs
     */
    public static <T extends Object> void mergeXrefs(T termToEnrich, Collection<Xref> toEnrichXrefs, Collection<Xref> fetchedXrefs , boolean remove,
                              boolean isIdentifier, XrefsChangeListener<T> xrefListener, IdentifiersChangeListener<T> identifierListener){

        Iterator<Xref> refIterator = toEnrichXrefs.iterator();
        // remove xrefs in toEnrichXrefs that are not in fetchedXrefs
        while(refIterator.hasNext()){
            Xref ref = refIterator.next();
            boolean containsRef = false;
            for (Xref ref2 : toEnrichXrefs){
                // identical xrefs and qualifier null. Only compare as identifier
                if (DefaultXrefComparator.areEquals(ref, ref2)){
                    containsRef = true;
                    break;
                }
            }
            // remove xref not in second list
            if (remove && !containsRef){
                refIterator.remove();
                if (isIdentifier){
                    if (identifierListener != null){
                        identifierListener.onRemovedIdentifier(termToEnrich, ref);
                    }
                }
                else{
                    if (xrefListener != null){
                        xrefListener.onRemovedXref(termToEnrich, ref);
                    }
                }
            }
        }

        // add xrefs from fetchedXrefs that are not in toEnrichXref
        refIterator = fetchedXrefs.iterator();
        while(refIterator.hasNext()){
            Xref ref = refIterator.next();
            boolean containsRef = false;
            for (Xref ref2 : toEnrichXrefs){
                // identical xrefs
                if (DefaultXrefComparator.areEquals(ref, ref2)){
                    containsRef = true;
                    break;
                }
            }
            // add missing xref not in second list
            if (!containsRef){
                toEnrichXrefs.add(ref);
                if (isIdentifier){
                    if (identifierListener != null){
                        identifierListener.onAddedIdentifier(termToEnrich, ref);
                    }
                }
                else{
                    if (xrefListener != null){
                        xrefListener.onAddedXref(termToEnrich, ref);
                    }
                }
            }
        }
    }
}
