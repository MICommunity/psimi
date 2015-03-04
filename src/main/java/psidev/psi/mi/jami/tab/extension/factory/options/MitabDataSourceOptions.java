package psidev.psi.mi.jami.tab.extension.factory.options;

import psidev.psi.mi.jami.factory.options.MIFileDataSourceOptions;

/**
 * Class that lists all possible options for MIFileDataSource reading MITAB files.
 * The options listed in MIDataSourceOptions and MIFileDataSource are also valid for a MitabDataSourceOptions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/05/14</pre>
 */

public class MitabDataSourceOptions extends MIFileDataSourceOptions{

    /*
     * The option to describe the type of interaction object we want to return.
     * It has to be an enum of type InteractionCategory (evidence, modelled, basic, mixed). If this option is not provided,
     * the default value will be evidence as MITAB is mainly for exchanging interaction evidences.
     *
     * - evidence : the MITAB datasource will return interactions implementing InteractionEvidence interface and will parse all experimental details
     * - modelled : the MITAB datasource will return interactions implementing ModelledInteraction interface and will ignore all experimental details
     * excepted interaction confidences, parameters and source.
     * - mixed: it is equivalent to evidence for MITAB datasources as we cannot distinguish what is modelled from evidences in MITAB
     * - basic: the MITAB datasource will return interactions implementing Interaction interface but will ignore all details related to experiments, confidences,
     * parameters and source. This aims at having a light datasource that only loads basic information about the interactions it parses.
     */
}
