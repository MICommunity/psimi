package psidev.psi.mi.jami.enricher.impl.publication;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;
import psidev.psi.mi.jami.model.Publication;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/08/13
 */
public class MinimumPublicationUpdater
        extends AbstractPublicationEnricher{


    public MinimumPublicationUpdater(PublicationFetcher fetcher) {
        super(fetcher);
    }

    @Override
    protected void processPublication(Publication publicationToEnrich) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
