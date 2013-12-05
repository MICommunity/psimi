package psidev.psi.mi.jami.xml.utils;

import psidev.psi.mi.jami.datasource.InteractionWriterOptions;

/**
 * Class that lists all possible options for PsiXmlWriter.
 * The options listed in InteractionWriterOptions are also valid for a PsiXmlWriter
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/12/13</pre>
 */

public class PsiXmlWriterOptions extends InteractionWriterOptions{
    /**
     * The object instance implementing the PsiXml25ObjectCache interface.
     * This object will be used to index MI objects which will be referenced in several places and to assign unique ids.
     * If this option is not provided, it will use a InMemoryIdentityObjectCache by default for compact Xml writers and it
     * will use a InMemoryLightIdentityObjectCache by default for expanded XML writers.
     */
    public static final String ELEMENT_WITH_ID_CACHE_OPTION = "element_id_cache_key";

    /**
     * The PSI-XML flavour which has to be an enum of type PsiXmlType (compact or expanded)
     * If this option is not provided, it will write expanded XML files.
     */
    public static final String XML_TYPE_OPTION = "xml25_type_key";

    /**
     * The option to provide a collection of annotations to write in each entry.
     * If this option is not provided, it will not write any attributes in each entry.
     */
    public static final String XML_ENTRY_ATTRIBUTES_OPTION = "xml25_entry_attributes_key";

    /**
     * The option to select specialised XML 2.5 writers using specific object instances such as extended interactions, participants,
     * features, confidences, parameters, host organisms, xrefs.
     * It has to be a boolean value.
     * If this option is not provided, it will be set to false by default.
     */
    public static final String XML25_EXTENDED_OPTION = "xml25_extended_key";

    /**
     * The option to select XML writers that will write shortLabel, fullName and aliases for named experiments, named interactions,
     * named participants and named features.
     * It has to be a boolean value.
     * If this option is not provided, it will be set to false by default.
     */
    public static final String XML25_NAMES_OPTION = "xml25_names_key";

    /**
     * The option to choose the PSI-XML version. It has to be an enum of type PsiXmlVersion.
     * If this option is not provided, it will write PSI-XML 2.5.4
     */
    public static final String XML_VERSION_OPTION = "xml_version_key";

    /**
     * The option to provide a set of experiment that will be used to list all unique experiments in a compact xml file..
     * If this option is not provided, it will use a default identity set that will be based on object equality.
     */
    public static final String COMPACT_XML_EXPERIMENT_SET_OPTION = "compact_xml_experiment_set_key";

    /**
     * The option to provide a set of interactors that will be used to list all unique interactors in a compact xml file..
     * If this option is not provided, it will use a default identity set that will be based on object equality.
     */
    public static final String COMPACT_XML_INTERACTOR_SET_OPTION = "compact_xml_interactor_set_key";

    /**
     * The option to provide a set of String that will be used to list all unique availabilities in a compact xml file..
     * If this option is not provided, it will use a default HashSet that will be based on object hashcode.
     */
    public static final String COMPACT_XML_AVAILABILITY_SET_OPTION = "compact_xml_availability_set_key";

    /**
     * The option to provide a set of interactions that will be used to list all unique interactions in a compact xml file..
     * If this option is not provided, it will use a default identity set that will be based on object equality.
     */
    public static final String XML_INTERACTION_SET_OPTION = "xml_interaction_set_key";

    /**
     * The option to provide a default release date for each entry source.
     * If this option is not provided, it will use the current date as the release date.
     */
    public static final String DEFAULT_RELEASE_DATE_OPTION = "default_release_date_key";

    /**
     * The option to provide a default source for each entry source in case the interactions do not have a source.
     * If this option is not provided, it will use an unknown source with the default release date.
     */
    public static final String DEFAULT_SOURCE_OPTION = "default_source_key";

    /**
     * The option to write complexes as interactions (false) or as interactors (true) when a participant is in fact a sub-complex.
     * It is a boolean value.
     * If this option is not provided, it will be set to false by default (complexes written as interactions).
     */
    public static final String WRITE_COMPLEX_AS_INTERACTOR_OPTION = "write_complex_as_interactor_key";
}
