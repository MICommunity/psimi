package psidev.psi.mi.jami.enricher.listener.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.BioactiveEntityEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.Xref;

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
        log.info(object.toString()+" enrichment complete with status of "+status+", message: "+message);
    }

    public void onEnrichmentError(BioactiveEntity object, String message, Exception e) {
        log.error(object.toString()+" enrichment error for "+object.toString()+", message: "+message, e);
    }

    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId) {
        log.info(bioactiveEntity.toString()+" has old Chebi "+oldId+
                " and new Chebi "+bioactiveEntity.getChebi());
    }

    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile) {
        log.info(bioactiveEntity.toString()+" has old Smile "+oldSmile+
                " and new Smile "+bioactiveEntity.getSmile());
    }

    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey) {
        log.info(bioactiveEntity.toString()+" has old Inchi key "+oldKey+
                " and new Inchi key "+bioactiveEntity.getStandardInchiKey());
    }

    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi) {
        log.info(bioactiveEntity.toString()+" has old Inchi "+oldInchi+
                " and new Inchi "+bioactiveEntity.getStandardInchi());
    }

    public void onShortNameUpdate(BioactiveEntity interactor, String oldShortName) {
        log.info(interactor.toString()+" has old shortName "+oldShortName+
                " and new shortName "+interactor.getShortName()+"]");
    }

    public void onFullNameUpdate(BioactiveEntity interactor, String oldFullName) {
        log.info(interactor.toString()+" has old fullName "+oldFullName+
                " and new fullName "+interactor.getFullName());
    }

    public void onAddedOrganism(BioactiveEntity interactor) {
        log.info(interactor.toString()+" has organism added "+interactor.getOrganism());
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
}
