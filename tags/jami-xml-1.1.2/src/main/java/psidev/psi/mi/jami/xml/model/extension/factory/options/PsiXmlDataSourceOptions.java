package psidev.psi.mi.jami.xml.model.extension.factory.options;

import psidev.psi.mi.jami.factory.options.MIFileDataSourceOptions;

/**
 * Class that lists all possible options for PsiXml 2.5 DataSource.
 * The options listed in MIDataSourceOptions and MIDatasourceOptions are also valid for a PsiXml 2.5 DataSource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/12/13</pre>
 */

public class PsiXmlDataSourceOptions extends MIFileDataSourceOptions{

    /**
     * The object instance implementing the PsiXmlIdCache interface.
     * This object will be used to index XML elements having ids and to resolve references to this objects.
     * If this option is not provided, it will use a InMemoryPsiXmlCache by default (will cache the objects in memory and
     * so cannot be efficient with very large files).
     */
    public static final String ELEMENT_WITH_ID_CACHE_OPTION = "element_id_cache_key";

    /**
     * The option to describe the type of interaction object we want to return.
     * It has to be an enum of type InteractionCategory (evidence, modelled, basic, mixed). If this option is not provided,
     * the default value will be evidence as PSI-XML 2.5 is mainly for exchanging interaction evidences.
     *
     * - evidence : the PSI XML 2.5 datasource will return interactions implementing InteractionEvidence interface and will parse all experimental details
     * - modelled : the PSI XML 2.5 datasource will return interactions implementing ModelledInteraction interface and will ignore all experimental details
     * excepted interaction confidences, parameters and source.
     * - mixed: it is equivalent to evidence for PSI XML 2.5 datasources as we cannot distinguish what is modelled from evidences in PSI XML 2.5
     * - basic: the PSI XML 2.5 datasource will return interactions implementing Interaction interface but will ignore all details related to experiments, confidences,
     * parameters and source. This aims at having a light datasource that only loads basic information about the interactions it parses.
     */
}
