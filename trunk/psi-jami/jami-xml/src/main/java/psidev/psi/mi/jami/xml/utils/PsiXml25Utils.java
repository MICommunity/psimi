package psidev.psi.mi.jami.xml.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Utility class for psixml
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/13</pre>
 */

public class PsiXml25Utils {

    public static final String UNSPECIFIED = "unspecified";
    public final static DateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
    public static final String STOICHIOMETRY_PREFIX = "stoichiometry: ";
    public static final String ENTRYSET_TAG = "entrySet";
    public static final String ENTRY_TAG = "entry";
    public static final String EXPERIMENTLIST_TAG = "experimentList";
    public static final String INTERACTORLIST_TAG = "interactorList";
    public static final String INTERACTIONLIST_TAG = "interactionList";
    public static final String EXPERIMENT_TAG = "experimentDescription";
    public static final String INTERACTOR_TAG = "interactor";
    public static final String INTERACTION_TAG = "interaction";
    public static final String SOURCE_TAG = "source";
    public static final String AVAILABILITYLIST_TAG = "availabilityList";
    public static final String AVAILABILITY_TAG = "availability";
    public static final String ATTRIBUTELIST_TAG = "attributeList";
    public static final String ATTRIBUTE_TAG = "attribute";
    public static final String ELEMENT_WITH_ID_CACHE_OPTION = "element_id_cache_key";
    public static final String COMPLEX_CACHE_OPTION = "complex_id_cache_key";
}
