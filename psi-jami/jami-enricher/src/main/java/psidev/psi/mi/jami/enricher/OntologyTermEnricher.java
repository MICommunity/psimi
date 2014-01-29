package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.enricher.listener.OntologyTermEnricherListener;
import psidev.psi.mi.jami.model.OntologyTerm;

/**
 * The ontologyTerm enricher is an enricher which can enrich either single ontologyTerm or a collection.
 * A ontology term enricher must be initiated with a fetcher.
 * Sub enrichers: none.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/01/14</pre>
 */

public interface OntologyTermEnricher extends MIEnricher<OntologyTerm>{

    /**
     * The fetcher to be used for used to collect data.
     * @return  The fetcher which is currently being used for fetching.
     */
    public OntologyTermFetcher getOntologyTermFetcher();

    /**
     * The ontologyTermEnricherListener to be used.
     * It will be fired at all points where a change is made to the cvTerm
     * @param listener  The listener to use. Can be null.
     */
    public void setOntologyTermEnricherListener(OntologyTermEnricherListener listener);

    /**
     * The current OntologyTermEnricherListener.
     * @return  the current listener. May be null.
     */
    public OntologyTermEnricherListener getOntologyTermEnricherListener();
}
