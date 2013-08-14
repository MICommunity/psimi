package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.utils.comparator.ComparatorUtils;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Default Interaction comparator.
 *
 * It will first compare the interaction types using DefaultCvTermComparator.
 * Then it will compare the rigids if both are set (case sensitive).
 * Then it will compare the identifiers if both interactions have at least one identifier and it will look for at least
 * one identical identifier using DefaultExternalIdentifierComparator.
 * If one of the interactions does not have any identifiers, it will compare the shortnames (case sensitive, shortname null comes always after)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class DefaultInteractionBaseComparator {

    /**
     * Use DefaultInteractionBaseComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (interaction1 == null && interaction2 == null){
            return true;
        }
        else if (interaction1 == null || interaction2 == null){
            return false;
        }
        else {

            // First compares interaction type
            CvTerm type1 = interaction1.getInteractionType();
            CvTerm type2 = interaction2.getInteractionType();

            if (!DefaultCvTermComparator.areEquals(type1, type2)){
                return false;
            }

            // then compares rigid if both are set
            String rigid1 = interaction1.getRigid();
            String rigid2 = interaction2.getRigid();
            if (rigid1 != null && rigid2 != null){
                if (!rigid1.equals(rigid2)){
                    return false;
                }
            }

            // then compares identifiers (at least one matching identifier)
            if (!interaction1.getIdentifiers().isEmpty() && !interaction2.getIdentifiers().isEmpty()){
                return ComparatorUtils.findAtLeastOneMatchingIdentifier(interaction1.getIdentifiers(), interaction2.getIdentifiers());
            }
            else {
                // then compares shortnames if both are set
                String shortname1 = interaction1.getShortName();
                String shortname2 = interaction2.getShortName();
                if (shortname1 != null && shortname2 != null){
                    return shortname1.equals(shortname2);
                }
                else if (shortname1 != null || shortname2 != null) {
                    return false;
                }
                else {
                    return true;
                }
            }
        }
    }
}
