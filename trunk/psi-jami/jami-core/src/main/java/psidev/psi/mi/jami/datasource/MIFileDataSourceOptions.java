package psidev.psi.mi.jami.datasource;

/**
 * Class that lists all possible options for MIFileDataSource.
 * The options listed in MIDataSourceOptions are also valid for a MIFileDataSource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/12/13</pre>
 */

public class MIFileDataSourceOptions extends MIDataSourceOptions{

    /**
     * The input MI source that contains the interactions. It usually can be a String (path to a file or URL),
     * a File, an InputStream, a URL or a Reader but it can vary depending on the datasources.
     * This option must always be provided to a MIFileDataSource.
     */
    public static final String INPUT_OPTION_KEY = "input_key";

    /**
     * The parser listener instance that will listen to the MI parsing events.
     * It has to be an object instance implementing the MIFileParserListener interface.
     * If it is not provided, the default listener provided depends on the datasources.
     */
    public static final String PARSER_LISTENER_OPTION_KEY = "parser_listener_key";

    /**
     * The file format (for instance psi25_xml, mitab).
     * It must be a String.
     * This option must always be provided to the MIDataSourceFactory to select the adapted datasource implementation
     * that can parse the file.
     */
    public static final String INPUT_FORMAT_OPTION_KEY = "input_format_key";
}
