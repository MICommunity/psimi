package psidev.psi.mi.jami.datasource;

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
     * Some specialised writers may not need this option.
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
     * The option to describe the type of interaction object we want to write.
     * It has to be an enum of type InteractionObjectCategory. If this option is not provided,
     * the default value will vary depending on the type of the interaction writer.
     */
    public static final String INTERACTION_OBJECT_OPTION_KEY = "interaction_object_key";
}
