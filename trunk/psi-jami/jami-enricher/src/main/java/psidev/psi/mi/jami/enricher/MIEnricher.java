package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.enricher.exception.EnricherException;

import java.util.Collection;

/**
 * General interface for enriching MI objects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/09/13</pre>
 */

public interface MIEnricher<T extends Object> {

    /**
     * Enrich an object
     * @param object
     * @throws EnricherException if it cannot enrich the object because cannot connect to a webservice, etc.
     */
    public void enrich(T object) throws EnricherException;

    /**
     * Enriche a collection of objects
     * @param objects
     * @throws EnricherException if it cannot enrich the object because cannot connect to a webservice, etc.
     */
    public void enrich(Collection<T> objects) throws EnricherException;
}
