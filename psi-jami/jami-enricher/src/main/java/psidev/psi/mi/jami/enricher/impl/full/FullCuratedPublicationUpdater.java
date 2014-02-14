package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.enricher.SourceEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalCuratedPublicationUpdater;
import psidev.psi.mi.jami.enricher.listener.PublicationEnricherListener;
import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Publication;

import java.util.Date;

/**
 * Provides full update of Publication.
 *
 * - update full properties as described in FullPublicationUpdater
 *  - update released date. If the released date of the publication to enrich
 * is different from the released date of the fetched publication, it will override the released date
 * with the released date of the fetched publication
 * - update curation depth. If the curation depth of the publication to enrich
 * is different from the curation depth of the fetched publication, it will override the curation depth
 * with the curation depth of the fetched publication
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/08/13
 */
public class FullCuratedPublicationUpdater extends FullCuratedPublicationEnricher{

    private MinimalCuratedPublicationUpdater minimalPublicationUpdater;

    public FullCuratedPublicationUpdater(PublicationFetcher fetcher) {
        super(new FullPublicationUpdater(fetcher));
        this.minimalPublicationUpdater = new MinimalCuratedPublicationUpdater(fetcher);
    }

    @Override
    public void processPublication(Publication publicationToEnrich, Publication fetchedPublication) throws EnricherException {
        this.minimalPublicationUpdater.processPublication(publicationToEnrich, fetchedPublication);
        // other properties
        processOtherProperties(publicationToEnrich, fetchedPublication);
    }

    @Override
    protected void processCurationDepth(Publication publicationToEnrich, Publication fetched) {
        if ((publicationToEnrich.getCurationDepth() != null &&
                publicationToEnrich.getCurationDepth().equals(fetched.getCurationDepth())
        || (publicationToEnrich.getCurationDepth() == null && fetched.getCurationDepth() != null))){
            CurationDepth old = publicationToEnrich.getCurationDepth();
            publicationToEnrich.setCurationDepth(fetched.getCurationDepth());
            if (getPublicationEnricherListener() != null){
                getPublicationEnricherListener().onCurationDepthUpdate(publicationToEnrich, old);
            }
        }
    }

    @Override
    protected void processReleasedDate(Publication publicationToEnrich, Publication fetched) {
        if ((publicationToEnrich.getReleasedDate() != null &&
                publicationToEnrich.getReleasedDate().equals(fetched.getReleasedDate())
                || (publicationToEnrich.getReleasedDate() == null && fetched.getReleasedDate() != null))){
            Date old = publicationToEnrich.getReleasedDate();
            publicationToEnrich.setReleasedDate(fetched.getReleasedDate());
            if (getPublicationEnricherListener() != null){
                getPublicationEnricherListener().onReleaseDateUpdated(publicationToEnrich, old);
            }
        }
    }

    @Override
    public void setSourceEnricher(SourceEnricher cvTermEnricher) {
        this.minimalPublicationUpdater.setSourceEnricher(cvTermEnricher);
    }

    @Override
    public void setPublicationEnricherListener(PublicationEnricherListener listener) {
        this.minimalPublicationUpdater.setPublicationEnricherListener(listener);
    }

    @Override
    public PublicationFetcher getPublicationFetcher() {
        return this.minimalPublicationUpdater.getPublicationFetcher();
    }

    @Override
    public SourceEnricher getSourceEnricher() {
        return this.minimalPublicationUpdater.getSourceEnricher();
    }

    @Override
    public PublicationEnricherListener getPublicationEnricherListener() {
        return this.minimalPublicationUpdater.getPublicationEnricherListener();
    }

    @Override
    public Publication find(Publication publicationToEnrich) throws EnricherException {
        return this.minimalPublicationUpdater.find(publicationToEnrich);
    }
}
