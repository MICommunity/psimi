package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.enricher.SourceEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalPublicationUpdater;
import psidev.psi.mi.jami.enricher.listener.PublicationEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Publication;

import java.util.Date;

/**
 * Provides full update of Publication.
 *
 * - update minimal properties as described in MinimalPublicationUpdater
 * - update publication title. If the title of the publication to enrich
 * is different from the title of the fetched publication, it will override the title
 * with the title of the fetched publication
 * - update publication journal. If the journal of the publication to enrich
 * is different from the journal of the fetched publication, it will override the journal
 * with the journal of the fetched publication
 *  - update released date. If the released date of the publication to enrich
 * is different from the released date of the fetched publication, it will override the released date
 * with the released date of the fetched publication
 * - update curation depth. If the curation depth of the publication to enrich
 * is different from the curation depth of the fetched publication, it will override the curation depth
 * with the curation depth of the fetched publication
 * - update xrefs (imex, etc.) of a publication. It will use DefaultXrefComparator to compare xrefs and add missing xrefs.
 * It will also remove xrefs that are not in the fetched publication
 * - update annotations of a publication. It will use DefaultAnnotationComparator to compare annotations and add missing annotations.
 * It will also remove annotations that are not in the fetched publication
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
    public void processPublication(Publication publicationToEnrich, Publication fetchedPublication) throws EnricherException {
        this.minimalPublicationUpdater.processPublication(publicationToEnrich, fetchedPublication);
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
