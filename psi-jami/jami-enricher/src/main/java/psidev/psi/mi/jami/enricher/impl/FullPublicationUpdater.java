package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.enricher.SourceEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.PublicationEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Publication;

import java.util.Date;

/**
 * An enricher for publications which can enrich either a single publication or a collection.
 * The publicationEnricher has no subEnrichers. The publicationEnricher must be initiated with a fetcher.
 *
 * As an updater, any fields which contradict the fetched entry will be overwritten.
 *
 * At the maximum level, the publication updater overwrites the minimum level fields pubmedId and authors.
 * It also updates the fields for DOI, Title, Journal, Publication Date, Xrefs and release date.
 *
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/08/13
 */
public class FullPublicationUpdater extends FullPublicationEnricher{

    private MinimalPublicationUpdater minimalPublicationUpdater;

    public FullPublicationUpdater(PublicationFetcher fetcher) {
        super(fetcher);
        this.minimalPublicationUpdater = new MinimalPublicationUpdater(fetcher);
    }

    @Override
    protected void processMinimalEnrichment(Publication publicationToEnrich, Publication fetched) throws EnricherException {
        this.minimalPublicationUpdater.processPublication(publicationToEnrich, fetched);
    }

    @Override
    protected void processXrefs(Publication publicationToEnrich, Publication fetched) {
        EnricherUtils.mergeXrefs(publicationToEnrich, publicationToEnrich.getXrefs(), fetched.getXrefs(), true, false,
                getPublicationEnricherListener(), getPublicationEnricherListener());
    }

    @Override
    protected void processAnnotations(Publication publicationToEnrich, Publication fetched) {
        EnricherUtils.mergeAnnotations(publicationToEnrich, publicationToEnrich.getAnnotations(), fetched.getAnnotations(), true,
                getPublicationEnricherListener());
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
    protected void processJournal(Publication publicationToEnrich, Publication fetched) {
        if((fetched.getJournal() != null && !fetched.getJournal().equals(publicationToEnrich.getJournal()))
                || (fetched.getJournal() == null && publicationToEnrich.getJournal() != null)) {
            String oldJournal = publicationToEnrich.getJournal();
            publicationToEnrich.setTitle(fetched.getJournal());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onJournalUpdated(publicationToEnrich, oldJournal);
        }
    }

    @Override
    protected void processPublicationTitle(Publication publicationToEnrich, Publication fetched) {
        if((fetched.getTitle() != null && !fetched.getTitle().equals(publicationToEnrich.getTitle()))
                || (fetched.getTitle() == null && publicationToEnrich.getTitle() != null)) {
            String oldTitle = publicationToEnrich.getTitle();
            publicationToEnrich.setTitle(fetched.getTitle());
            if(getPublicationEnricherListener() != null)
                getPublicationEnricherListener().onTitleUpdated(publicationToEnrich , oldTitle);
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
