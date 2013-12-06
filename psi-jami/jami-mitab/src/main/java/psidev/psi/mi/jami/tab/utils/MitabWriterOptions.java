package psidev.psi.mi.jami.tab.utils;

import psidev.psi.mi.jami.datasource.InteractionWriterOptions;

/**
 * Class that lists all the options for a Mitab writer.
 * The options listed in InteractionWriterOptions are also valid for a MitabWriter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/12/13</pre>
 */

public class MitabWriterOptions extends InteractionWriterOptions{

    /**
     * The mitab version. It must be an enum of type MitabVersion.
     * If it is not provided, it will be considered as 2.7 by default
     */
    public static final String MITAB_VERSION_OPTION = "mitab_version_key";
    /**
     * The option to write or not the MITAB header. It must be a boolean value
     * If it is not provided, it will be considered as true by default
     */
    public static final String MITAB_HEADER_OPTION = "mitab_header_key";
    /**
     * The option to select more specialised writers with extended MITAB objects (features and confidences).
     * It must be a boolean value
     * If it is not provided, it will be considered as false.
     */
    public static final String MITAB_EXTENDED_OPTION = "mitab_extended_key";
}
