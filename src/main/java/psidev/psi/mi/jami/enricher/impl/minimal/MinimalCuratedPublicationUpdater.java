package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Provides minimal update of curated Publication.
 *
 * - enrich minimal properties of publication. See description in MinimalPublicationUpdater
 * - enrich source of a publication if the sourceEnricher is not null. If the source of the publication to enrich
 * is different from the source of the fetched publication (see DefaultCvTermComparator), it will override the source
 * with the source of the fetched publication
 *
 * It will ignore all other properties of a publication
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/08/13
 */
public class MinimalCuratedPublicationUpdater extends MinimalCuratedPublicationEnricher {

    public MinimalCuratedPublicationUpdater(PublicationFetcher fetcher) {
        super(new MinimalPublicationUpdater(fetcher));
    }

    protected MinimalCuratedPublicationUpdater(MinimalPublicationEnricher delegate) {
        super(delegate);
    }

    @Override
    protected void processSource(Publication publicationToEnrich, Publication fetchedPublication) throws EnricherException {
        if (!DefaultCvTermComparator.areEquals(fetchedPublication.getSource(), publicationToEnrich.getSource())){
            Source oldSource = publicationToEnrich.getSource();
            publicationToEnrich.setSource(fetchedPublication.getSource());
            if (getPublicationEnricherListener() != null){
                getPublicationEnricherListener().onSourceUpdated(publicationToEnrich, oldSource);
            }
        }
        else if (getSourceEnricher() != null
                && publicationToEnrich.getSource() != fetchedPublication.getSource()){
            getSourceEnricher().enrich(publicationToEnrich.getSource(), fetchedPublication.getSource());
        }
        if (getSourceEnricher() != null && publicationToEnrich.getSource() != null){
            getSourceEnricher().enrich(publicationToEnrich.getSource());
        }
    }
}
