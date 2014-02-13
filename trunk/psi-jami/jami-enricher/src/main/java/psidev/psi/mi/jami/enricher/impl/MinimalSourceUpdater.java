package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.SourceFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.SourceEnricherListener;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.utils.comparator.publication.DefaultPublicationComparator;

/**
 * Provides minimum update of a Source.
 *
 * - update minimal properties of CvTerm (see MinimalCvTermUpdater for more details)
 * - update publication properties using publication updater. If the publication in the source to enrich is different from the
 * one from the fetched source (see DefaultPublicationComparator for more details), it will override the publication with the one from the fetched source before enriching it with the publication
 * enricher,
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/01/14</pre>
 */

public class MinimalSourceUpdater extends MinimalSourceEnricher{
    public MinimalSourceUpdater(SourceFetcher cvTermFetcher) {
        super(new MinimalCvTermUpdater<Source>(cvTermFetcher));
    }

    protected MinimalSourceUpdater(MinimalCvTermEnricher<Source> cvEnricher) {
        super(cvEnricher);
    }

    @Override
    protected void processPublication(Source cvTermToEnrich, Source cvTermFetched) throws EnricherException {
        if (!DefaultPublicationComparator.areEquals(cvTermFetched.getPublication(), cvTermToEnrich.getPublication())){
            Publication oldPub = cvTermToEnrich.getPublication();
            cvTermToEnrich.setPublication(cvTermFetched.getPublication());
            if (getCvTermEnricherListener() instanceof SourceEnricherListener){
                ((SourceEnricherListener)getCvTermEnricherListener()).onPublicationUpdate(cvTermToEnrich, oldPub);
            }
        }
        else if (getPublicationEnricher() != null
                && cvTermToEnrich.getPublication() != cvTermFetched.getPublication()){
            getPublicationEnricher().enrich(cvTermToEnrich.getPublication(), cvTermFetched.getPublication());
        }
    }
}
