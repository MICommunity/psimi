package psidev.psi.mi.jami.enricher.impl.feature.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.*;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/07/13
 */
public class FeatureEnricherLogger
        implements FeatureEnricherListener {


    protected static final Logger log = LoggerFactory.getLogger(FeatureEnricherLogger.class.getName());


    public void onEnrichmentComplete(Feature feature, EnrichmentStatus status, String message) {
        log.info(feature.toString()+" enrichment complete with status ["+status+"], message: "+message);
    }

    public void onShortNameUpdate(Feature feature, String oldShortName) {
        log.info(feature+" has old shortname "+oldShortName+" new shortname "+feature.getShortName());
    }

    public void onFullNameUpdate(Feature feature, String oldFullName) {
        log.info(feature+" has old full name "+oldFullName+" new full name "+feature.getFullName());
    }

    public void onInterproUpdate(Feature feature, String oldInterpro) {
        log.info(feature+" has old interpro "+oldInterpro+" new interpro "+feature.getInterpro());
    }

    public void onTypeAdded(Feature feature, CvTerm oldType) {
        log.info(feature+" has old type "+oldType+" new type "+feature.getType());
    }

    public void onAddedIdentifier(Feature feature, Xref added) {
        log.info(feature+" has added identifier "+added.toString());
    }

    public void onRemovedIdentifier(Feature feature, Xref removed) {
        log.info(feature+" has removed identifier "+removed.toString());
    }

    public void onAddedXref(Feature feature, Xref added) {
        log.info(feature+" has added xref "+added.toString());
    }

    public void onRemovedXref(Feature feature, Xref removed) {
        log.info(feature+" has removed xref "+removed.toString());
    }

    public void onAddedAnnotation(Feature feature, Annotation added) {
        log.info(feature+" has added annotation "+added.toString());
    }

    public void onRemovedAnnotation(Feature feature, Annotation removed) {
        log.info(feature+" has removed annotation "+removed.toString());
    }

    public void onAddedRange(Feature feature, Range added) {
        log.info(feature+" has added range "+added.toString());
    }

    public void onInvalidRange(Feature feature, Range invalid, String message) {
        log.info(feature+" has updated range "+invalid.toString()+", message reads: "+ message);
    }

    public void onUpdatedRange(Feature feature, Range updated, String message) {
        log.info(feature+" has updated range "+updated.toString()+", message reads: "+ message);
    }

    public void onRemovedRange(Feature feature, Range removed) {
        log.info(feature+" has removed range "+removed.toString());
    }
}
