package psidev.psi.mi.jami.enricher.listener.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.BioactiveEntityEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.*;

/**
 * A logging listener. It will display a message when each event is fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class BioactiveEntityEnricherLogger
        implements BioactiveEntityEnricherListener {

    private static final Logger log = LoggerFactory.getLogger(BioactiveEntityEnricherLogger.class.getName());

    public void onEnrichmentComplete(BioactiveEntity object, EnrichmentStatus status, String message) {
        log.info("Bioactive entity "+object.toString()+" has been enriched with status of "+status+", message: "+message);
    }

    public void onEnrichmentError(BioactiveEntity object, String message, Exception e) {
        log.error("Enrichment error for bioactive entity "+object.toString()+", message: "+message, e);
    }

    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId) {
        log.info("Updated CHEBI identifier of bioactive entity "+bioactiveEntity.toString()+" from "+oldId+
                " to "+bioactiveEntity.getChebi());
    }

    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile) {
        log.info("Updated SMILE of bioactive entity "+bioactiveEntity.toString()+" from "+oldSmile+
                " to "+bioactiveEntity.getSmile());
    }

    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey) {
        log.info("Updated standard InchI key of bioactive entity "+bioactiveEntity.toString()+" from "+oldKey+
                " to "+bioactiveEntity.getStandardInchiKey());
    }

    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi) {
        log.info("Updated standard inchi of bioactive entity "+bioactiveEntity.toString()+" from "+oldInchi+
                " to "+bioactiveEntity.getStandardInchi());
    }

    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName) {
        log.info("Updated shortName of bioactive entity "+interactor.toString()+" from "+oldShortName+
                " to "+interactor.getShortName());
    }

    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) {
        log.info("Updated fullName of bioactive entity "+interactor.toString()+" from "+oldFullName+
                " to "+interactor.getShortName());
    }

    public void onAddedOrganism(BioactiveEntity interactor) {
        log.info("Added organism to "+interactor.getOrganism());
    }

    public void onAddedInteractorType(BioactiveEntity interactor) {
        log.info(interactor.toString()+" has organism added "+interactor.getInteractorType());
    }

    public void onAddedIdentifier(BioactiveEntity interactor, Xref added) {
        log.info(interactor.toString()+" has identifier added "+added.toString());
    }

    public void onRemovedIdentifier(BioactiveEntity interactor, Xref removed) {
        log.info(interactor.toString()+" has identifier removed "+removed.toString());
    }

    public void onAddedXref(BioactiveEntity interactor, Xref added) {
        log.info(interactor.toString()+" has xref added "+added.toString());
    }

    public void onRemovedXref(BioactiveEntity interactor, Xref removed) {
        log.info(interactor.toString()+" has xref removed "+removed.toString());
    }

    public void onAddedAlias(BioactiveEntity interactor, Alias added) {
        log.info(interactor.toString()+" has alias added "+added.toString());
    }

    public void onRemovedAlias(BioactiveEntity interactor, Alias removed) {
        log.info(interactor.toString()+" has alias removed "+removed.toString());
    }

    public void onAddedChecksum(BioactiveEntity interactor, Checksum added) {
        log.info(interactor.toString()+" has checksum added "+added.toString());
    }

    public void onRemovedChecksum(BioactiveEntity interactor, Checksum removed) {
        log.info(interactor.toString()+" has checksum removed "+removed.toString());
    }

    public void onAddedAnnotation(BioactiveEntity o, Annotation added) {
        log.info(o.toString()+" has annotation added "+added.toString());    }

    public void onRemovedAnnotation(BioactiveEntity o, Annotation removed) {
        log.info(o.toString()+" has annotation removed "+removed.toString());
    }
}
