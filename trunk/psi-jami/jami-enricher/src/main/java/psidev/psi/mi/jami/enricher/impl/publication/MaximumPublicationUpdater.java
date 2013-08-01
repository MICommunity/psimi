package psidev.psi.mi.jami.enricher.impl.publication;

import psidev.psi.mi.jami.bridges.fetcher.PublicationFetcher;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/08/13
 */
public class MaximumPublicationUpdater
        extends MinimumPublicationUpdater{

    public MaximumPublicationUpdater(PublicationFetcher fetcher) {
        super(fetcher);
    }

}
