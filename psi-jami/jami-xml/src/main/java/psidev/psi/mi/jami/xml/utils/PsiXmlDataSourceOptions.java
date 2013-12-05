package psidev.psi.mi.jami.xml.utils;

import psidev.psi.mi.jami.datasource.MIFileDataSourceOptions;

/**
 * Class that lists all possible options for PsiXmlDataSource.
 * The options listed in MIDataSourceOptions and MIDatasourceOptions are also valid for a PsiXmlDataSource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/12/13</pre>
 */

public class PsiXmlDataSourceOptions extends MIFileDataSourceOptions{

    /**
     * The object instance implementing the PsiXml25IdCache interface.
     * This object will be used to index XML elements having ids and to resolve references to this objects.
     * If this option is not provided, it will use a InMemoryPsiXml25Cache by default (will cache the objects in memory and
     * so cannot be efficient with very large files).
     */
    public static final String ELEMENT_WITH_ID_CACHE_OPTION = "element_id_cache_key";
}
