package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.model.Source;

/**
 * The source enricher is an enricher which can enrich either single source or a collection.
 * A source enricher must be initiated with a fetcher.
 * Sub- enrichers: publication enricher. Will not enrich the publication if the publication enricher is null
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/01/14</pre>
 */

public interface SourceEnricher extends CvTermEnricher<Source>{

    /**
     * The current publication enricher.
     * @return  the current listener. May be null.
     */
    public PublicationEnricher getPublicationEnricher();

    public void setPublicationEnricher(PublicationEnricher enricher);
}
