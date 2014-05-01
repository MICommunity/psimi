package psidev.psi.mi.jami.factory;

/**
 * Class that lists all possible options for MIDataSource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/12/13</pre>
 */

public class MIDataSourceOptions {
    /**
     * The option to describe the type of interaction object we want to return.
     * It has to be an enum of type InteractionCategory (evidence, modelled, basic, mixed). If this option is not provided,
     * the default value will vary depending on the type of the datasource.
     */
    public static final String INTERACTION_CATEGORY_OPTION_KEY = "interaction_category_key";

    /**
     * The option to describe the complex type of interaction object we want to return.
     * It has to be an enum of type ComplexType (binary,n-ary, self). If this option is not provided,
     * the default value will vary depending on the type of the datasource.
     */
    public static final String COMPLEX_TYPE_OPTION_KEY = "complex_type_key";

    /**
     * The option to provide a complex expansion method to expand n-ary interactions in binary interactions.
     * It has to be an object instance which implements the ComplexExpansionMethod<T extends Interaction, B extends BinaryInteraction> interface
     * This option make sense in case of datasources that may provide n-ary interactions but will not be recognized by datasources providing
     * only binary interactions.
     */
    public static final String COMPLEX_EXPANSION_OPTION_KEY = "complex_expansion_key";

    /**
     * The option to indicates if we want a datasource only providing an Iterator of interactions (streaming) or if we want a datasource that can return the all collection of
     * interactions (not streaming).
     * It has to be a boolean value.
     * If this option is not provided, it will be true by default
     */
    public static final String STREAMING_OPTION_KEY = "streaming_key";

    /**
     * The input type (for instance psi25_xml, mitab).
     * It must be a String.
     * This option must always be provided to the MIDataSourceFactory to select the adapted datasource implementation
     * that can read the datasource.
     */
    public static final String INPUT_TYPE_OPTION_KEY = "input_format_key";
}
