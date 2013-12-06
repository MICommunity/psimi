package psidev.psi.mi.jami.json;

import psidev.psi.mi.jami.datasource.InteractionWriterOptions;

/**
 * Class that lists all possible options for MIJson writers.
 * All options listed in InteractionWriterOptions are also valid for MIJson writers.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/12/13</pre>
 */

public class MIJsonWriterOptions extends InteractionWriterOptions {

    /**
     * Option to load an object instance implementing the OntologyFetcher interface.
     * This object can fetch ontology terms,
     */
    public static final String ONTOLOGY_FETCHER_OPTION_KEY = "ontology_fetcher_key";

}
