package psidev.psi.mi.jami.tab.extension.factory.options;

import psidev.psi.mi.jami.factory.options.InteractionWriterOptions;

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

    /**
     * The option to describe the type of interaction object we want to write.
     * It has to be an enum of type InteractionCategory(mixed, modelled, evidence, basic).
     * If this option is not provided, the default value will be mixed as users may want to write all kind of interactions.
     *
     * - evidence : the MITAB writer will write interactions implementing InteractionEvidence interface and will write all experimental details
     * - modelled : the MITAB writer will write interactions implementing ModelledInteraction interface and will ignore all experimental details
     * excepted interaction confidences, parameters and source.
     * - mixed: the MITAB writer can write any kind of interaction and delegates to specialised writers in case it finds interaction evidences/modelled interactions, etc.
     * - basic: the MITAB writer will write interactions implementing Interaction interface but will ignore all details related to experiments, confidences,
     * parameters and source. This aims at having a light MITAB file that only contains basic information about the interactions.
     */
}
