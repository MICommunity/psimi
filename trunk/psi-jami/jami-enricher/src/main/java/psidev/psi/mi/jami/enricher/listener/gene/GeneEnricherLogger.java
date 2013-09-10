package psidev.psi.mi.jami.enricher.listener.gene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Xref;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/09/13
 */
public class GeneEnricherLogger
        implements GeneEnricherListener{

    private static final Logger log = LoggerFactory.getLogger(GeneEnricherLogger.class.getName());

    public void onEnrichmentComplete(Gene object, EnrichmentStatus status, String message) {
        log.info(object.toString()+" enrichment complete with status of "+status+", message: "+message);
    }

    public void onEnrichmentError(Gene object, String message, Exception e) {
        log.error(object.toString()+" enrichment error, message: "+message, e);
    }

    public void onEnsemblUpdate(Gene gene, String oldValue) {
        log.info(gene.toString() + " Ensembl updated from " +oldValue);
    }

    public void onEnsemblGenomeUpdate(Gene gene, String oldValue) {
        log.info(gene.toString() + " EnsemblGenomes updated from " +oldValue);
    }

    public void onEntrezGeneIdUpdate(Gene gene, String oldValue) {
        log.info(gene.toString() + " EntrezGeneId updated from " +oldValue);
    }

    public void onRefseqUpdate(Gene gene, String oldValue) {
        log.info(gene.toString() + " Refseq updated from " +oldValue);
    }

    public void onShortNameUpdate(Gene interactor, String oldShortName) {
        log.info("Shortname updated from " +oldShortName + " to " + interactor.getShortName());
    }

    public void onFullNameUpdate(Gene interactor, String oldFullName) {
        log.info("Fullname updated from " +oldFullName + " to " + interactor.getFullName());
    }

    public void onAddedOrganism(Gene interactor) {
        log.info("Added organism to "+interactor.toString());
    }

    public void onAddedInteractorType(Gene interactor) {
        log.info("Added interactor type to "+interactor.toString());
    }

    public void onAddedIdentifier(Gene interactor, Xref added) {
        log.info("Added identifier "+added.toString() + " to gene " + interactor.toString());
    }

    public void onRemovedIdentifier(Gene interactor, Xref removed) {
        log.info("Removed identifier "+removed.toString() + " to gene " + interactor.toString());
    }

    public void onAddedXref(Gene interactor, Xref added) {
        log.info("Added xref "+added.toString() + " to gene " + interactor.toString());
    }

    public void onRemovedXref(Gene interactor, Xref removed) {
        log.info("Removed xref "+removed.toString() + " to gene " + interactor.toString());
    }

    public void onAddedAlias(Gene interactor, Alias added) {
        log.info("Added alias "+added.toString() + " to gene " + interactor.toString());
    }

    public void onRemovedAlias(Gene interactor, Alias removed) {
        log.info("Removed alias "+removed.toString() + " to gene " + interactor.toString());
    }

    public void onAddedChecksum(Gene interactor, Checksum added) {
        log.info("Added checksum "+added.toString() + " to gene " + interactor.toString());
    }

    public void onRemovedChecksum(Gene interactor, Checksum removed) {
        log.info("Removed checksum "+removed.toString() + " to gene " + interactor.toString());
    }
}
