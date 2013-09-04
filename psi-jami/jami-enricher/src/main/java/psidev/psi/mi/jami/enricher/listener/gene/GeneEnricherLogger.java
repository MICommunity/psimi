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

    protected static final Logger log = LoggerFactory.getLogger(GeneEnricherLogger.class.getName());

    public void onEnrichmentComplete(Gene object, EnrichmentStatus status, String message) {
        log.info(object.toString()+" enrichment complete with status of "+status+", message: "+message);
    }

    public void onEnsemblUpdate(Gene gene, String oldValue) {
        log.info(gene.toString() + " Ensembl updated from " +oldValue);
    }

    public void onEnsemblGenomeUpdate(Gene gene, String oldValue) {
        log.info(gene.toString() + " EnsemblGenomes updated from " +oldValue);
    }

    public void onEntrezGeneIdUpdate(Gene gene, String oldValue) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onRefseqUpdate(Gene gene, String oldValue) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onShortNameUpdate(Gene interactor, String oldShortName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onFullNameUpdate(Gene interactor, String oldFullName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAddedOrganism(Gene interactor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAddedInteractorType(Gene interactor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAddedIdentifier(Gene interactor, Xref added) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onRemovedIdentifier(Gene interactor, Xref removed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAddedXref(Gene interactor, Xref added) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onRemovedXref(Gene interactor, Xref removed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAddedAlias(Gene interactor, Alias added) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onRemovedAlias(Gene interactor, Alias removed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onAddedChecksum(Gene interactor, Checksum added) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onRemovedChecksum(Gene interactor, Checksum removed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
