package psidev.psi.mi.xml.xmlindex;

/**
 * Central place where to store the XPath expression leading to key element of the PSI-MI Schema 2.5.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.5.0
 */
public interface PsiMi25ElementXpath {

    public static final String ENTRY_SET = "/entrySet";

    public static final String ENTRY = "/entrySet/entry";

    public static final String SOURCE = "/entrySet/entry/source";

    public static final String AVAILABILITY_LIST = "/entrySet/entry/availabilityList";

    public static final String EXPERIMENT_LIST = "/entrySet/entry/experimentList";
    public static final String EXPERIMENT = "/entrySet/entry/experimentList/experimentDescription";

    public static final String INTERACTOR_LIST = "/entrySet/entry/interactorList";
    public static final String INTERACTOR = "/entrySet/entry/interactorList/interactor";

    public static final String INTERACTION_LIST = "/entrySet/entry/interactionList";
    public static final String INTERACTION = "/entrySet/entry/interactionList/interaction";

    public static final String PARTICIPANT_LIST = "/entrySet/entry/interactionList/interaction/participantList";
    public static final String PARTICIPANT = "/entrySet/entry/interactionList/interaction/participantList/participant";

    public static final String FEATURE_LIST = "/entrySet/entry/interactionList/interaction/participantList/participant/featureList";
    public static final String FEATURE = "/entrySet/entry/interactionList/interaction/participantList/participant/featureList/feature";

    public static final String ATTRIBUTE_LIST = "/entrySet/entry/attributeList";
    public static final String ATTRIBUTE = "/entrySet/entry/attributeList/attribute";
}