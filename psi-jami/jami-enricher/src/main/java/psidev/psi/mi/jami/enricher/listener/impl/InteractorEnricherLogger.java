package psidev.psi.mi.jami.enricher.listener.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.model.*;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class InteractorEnricherLogger implements InteractorEnricherListener<Interactor> {

    private static final Logger log = LoggerFactory.getLogger(InteractorEnricherLogger.class.getName());

    public void onEnrichmentComplete(Interactor object, EnrichmentStatus status, String message) {
        log.info("Bioactive entity "+object.toString()+" has been enriched with status of "+status+", message: "+message);
    }

    public void onEnrichmentError(Interactor object, String message, Exception e) {
        log.error("Enrichment error for bioactive entity "+object.toString()+", message: "+message, e);
    }

    public void onShortNameUpdate(Interactor interactor, String oldShortName) {
        log.info("Updated shortName of bioactive entity "+interactor.toString()+" from "+oldShortName+
                " to "+interactor.getShortName());
    }

    public void onFullNameUpdate(Interactor interactor, String oldFullName) {
        log.info("Updated fullName of bioactive entity "+interactor.toString()+" from "+oldFullName+
                " to "+interactor.getShortName());
    }

    public void onAddedOrganism(Interactor interactor) {
        log.info("Added organism to "+interactor.getOrganism());
    }

    public void onAddedInteractorType(Interactor interactor) {
        log.info(interactor.toString()+" has organism added "+interactor.getInteractorType());
    }

    public void onAddedIdentifier(Interactor interactor, Xref added) {
        log.info(interactor.toString()+" has identifier added "+added.toString());
    }

    public void onRemovedIdentifier(Interactor interactor, Xref removed) {
        log.info(interactor.toString()+" has identifier removed "+removed.toString());
    }

    public void onAddedXref(Interactor interactor, Xref added) {
        log.info(interactor.toString()+" has xref added "+added.toString());
    }

    public void onRemovedXref(Interactor interactor, Xref removed) {
        log.info(interactor.toString()+" has xref removed "+removed.toString());
    }

    public void onAddedAlias(Interactor interactor, Alias added) {
        log.info(interactor.toString()+" has alias added "+added.toString());
    }

    public void onRemovedAlias(Interactor interactor, Alias removed) {
        log.info(interactor.toString()+" has alias removed "+removed.toString());
    }

    public void onAddedChecksum(Interactor interactor, Checksum added) {
        log.info(interactor.toString()+" has checksum added "+added.toString());
    }

    public void onRemovedChecksum(Interactor interactor, Checksum removed) {
        log.info(interactor.toString()+" has checksum removed "+removed.toString());
    }

    public void onAddedAnnotation(Interactor o, Annotation added) {
        log.info(o.toString()+" has annotation added "+added.toString());    }

    public void onRemovedAnnotation(Interactor o, Annotation removed) {
        log.info(o.toString()+" has annotation removed "+removed.toString());
    }
}