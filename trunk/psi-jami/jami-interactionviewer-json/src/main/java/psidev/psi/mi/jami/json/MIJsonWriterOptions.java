package psidev.psi.mi.jami.json;

import psidev.psi.mi.jami.factory.options.InteractionWriterOptions;

/**
 * Class that lists all possible options for InteractionViewerJson writers.
 * All options listed in InteractionWriterOptions are also valid for InteractionViewerJson writers.
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

    /**
     * The json type. It should be an enum of type MIJsonType. If it is not provided, it will be MIJsonType.n_ary_only
     */
    public static final String MI_JSON_TYPE = "interaction_viewer_json";

    /**
     * The json format value to use with MIJsonWriterOptions.OUTPUT_FORMAT_OPTION_KEY
     */
    public static final String MI_JSON_FORMAT = "interaction_viewer_json";

}
