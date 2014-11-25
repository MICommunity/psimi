package psidev.psi.mi.jami.factory.options;

/**
 * Class that lists all possible options for InteractionWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/12/13</pre>
 */

public class InteractionWriterOptions {

    /**
     * The option to select where to write the interactions. It is usually a String(path to a file), OutputStream, File or
     * Writer but it can vary depending on the writers.
     * Some specialised writers may not need this option (such as database writers).
     */
    public static final String OUTPUT_OPTION_KEY = "output_key";

    /**
     * The option to provide a complex expansion method to expand n-ary interactions in binary interactions.
     * It has to be an object instance which implements the ComplexExpansionMethod<T extends Interaction, B extends BinaryInteraction> interface
     * This option make sense in case of writers that can only write binary interactions but it may not be recognized by writers that can write n-ary interactions.
     */
    public static final String COMPLEX_EXPANSION_OPTION_KEY = "complex_expansion_key";

    /**
     * The file format (for instance psi25_xml, mitab).
     * It must be a String.
     * This option must always be provided to the InteractionWriterFactory to select the adapted writer implementation
     * that can write a specific file format. It may not be adapted to more specialised writers such as database writers.
     */
    public static final String OUTPUT_FORMAT_OPTION_KEY = "output_format_key";

    /**
     * The option to describe the complex type of interaction object we want to write.
     * It has to be an enum of type ComplexType (binary,n-ary, self). If this option is not provided,
     * the default value will vary depending on the type of the datasource.
     *
     * Usually, two values can be provided for this option :
     * - n-ary : we want to write interactions with a list of participants. They can have only two participants and so be binary but they should all implement
     * Interaction interface. When the writer only write binary interactions,it is up to them to expand the complexes in several binary interactions
     * so only binary interactions are written. When COMPLEX_TYPE_OPTION_KEY -> binary,
     * the option COMPLEX_EXPANSION_OPTION_KEY should be used to specify the complex expansion in case n-ary interactions need to be expanded
     * - binary : we want to write interactions with only two participants and implementing BinaryInteraction interface.
     * - the other complex types such a self_inter and self_intra are equivalent to n-ary. It should write interactions implementing
     * Interaction interface but not always BinaryInteraction so may need to be exapnded by the writer if it can only write binary interactions
     */
    public static final String COMPLEX_TYPE_OPTION_KEY = "complex_type_key";

    /**
     * The option to describe the type of interaction object we want to write.
     * It has to be an enum of type InteractionCategory(mixed, modelled, evidence, complex, basic). If this option is not provided,
     * the default value will vary depending on the type of the interaction writer.
     * The PSI-MI XML 3.0 writer do not need this option.
     */
    public static final String INTERACTION_CATEGORY_OPTION_KEY = "interaction_category_key";
}
