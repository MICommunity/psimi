package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author  Gabriel Aldam (galdam@ebi.ac.uk)
 * @since   13/06/13
 */
public abstract class AbstractFeatureEnricher <F extends Feature>
        implements FeatureEnricher<F>{


    protected FeatureEnricherListener listener;
    protected CvTermEnricher cvTermEnricher;

    public void enrichFeature(F featureToEnrich) throws EnricherException {
        if(featureToEnrich == null) throw new IllegalArgumentException("Feature enricher was passed a null feature.");

        processFeature(featureToEnrich);

        if(listener != null) listener.onFeatureEnriched(featureToEnrich, EnrichmentStatus.SUCCESS, null);
    }

    public abstract void processFeature(F featureToEnrich) throws EnricherException;


    public void setFeatureEnricherListener(FeatureEnricherListener featureEnricherListener) {
        this.listener = featureEnricherListener;
    }

    public FeatureEnricherListener getFeatureEnricherListener() {
        return listener;
    }

    public void setCvTermEnricher(CvTermEnricher cvTermEnricher){
        this.cvTermEnricher = cvTermEnricher;
    }

    public CvTermEnricher getCvTermEnricher(){
        return cvTermEnricher;
    }


    protected Protein oldSequenceSource;
    protected String oldSequence;
    protected Protein lastEnrichedSequence;

    public void onSequenceUpdate(Protein protein, String oldSequence) {
        this.oldSequence = oldSequence;
        this.oldSequenceSource = protein;
    }

    public void onProteinEnriched(Protein protein, EnrichmentStatus status, String message) {
        this.lastEnrichedSequence = protein;
    }

    public void onProteinRemapped(Protein protein, String oldUniprot) {}
    public void onUniprotKbUpdate(Protein protein, String oldUniprot)  {}
    public void onRefseqUpdate(Protein protein, String oldRefseq) {}
    public void onGeneNameUpdate(Protein protein, String oldGeneName) {}
    public void onRogidUpdate(Protein protein, String oldRogid) {}
    public void onShortNameUpdate(Protein protein, String oldShortName) {}
    public void onFullNameUpdate(Protein protein, String oldFullName) {}
    public void onAddedInteractorType(Protein protein) {}
    public void onAddedOrganism(Protein protein)  {}
    public void onAddedIdentifier(Protein protein, Xref added)  {}
    public void onRemovedIdentifier(Protein protein, Xref removed) {}
    public void onAddedXref(Protein protein, Xref added) {}
    public void onRemovedXref(Protein protein, Xref removed) {}
    public void onAddedAlias(Protein protein, Alias added)  {}
    public void onRemovedAlias(Protein protein, Alias removed) {}
    public void onAddedChecksum(Protein protein, Checksum added) {}
    public void onRemovedChecksum(Protein protein, Checksum removed) {}
}
