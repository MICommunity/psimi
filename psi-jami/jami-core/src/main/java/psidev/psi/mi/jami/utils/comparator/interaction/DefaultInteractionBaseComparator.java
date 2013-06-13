package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultExternalIdentifierComparator;

import java.util.*;

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

public class DefaultInteractionBaseComparator implements Comparator<Interaction> {

    private static DefaultInteractionBaseComparator defaultInteractionComparator;
    private DefaultCvTermComparator cvTermComparator;
    private DefaultExternalIdentifierComparator identifierComparator;
    /**
     * Creates a new DefaultInteractionBaseComparator. It will use a DefaultParticipantBaseComparator to
     * compare participants and DefaultCvTermcomparator to compare interaction types
     */
    public DefaultInteractionBaseComparator() {
        this.identifierComparator = new DefaultExternalIdentifierComparator();
        this.cvTermComparator = new DefaultCvTermComparator();
    }

    public DefaultExternalIdentifierComparator getIdentifierComparator() {
        return this.identifierComparator;
    }

    public DefaultCvTermComparator getCvTermComparator() {
        return cvTermComparator;
    }

    /**
     * It will first compare the interaction types using DefaultCvTermComparator.
     * Then it will compare the rigids if both are set (case sensitive).
     * Then it will compare the identifiers if both interactions have at least one identifier and it will look for at least
     * one identical identifier using DefaultExternalIdentifierComparator.
     * If one of the interactions does not have any identifiers, it will compare the shortnames (case sensitive, shortname null comes always after)
     *
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (interaction1 == null && interaction2 == null){
            return EQUAL;
        }
        else if (interaction1 == null){
            return AFTER;
        }
        else if (interaction2 == null){
            return BEFORE;
        }
        else {

            // First compares interaction type
            CvTerm type1 = interaction1.getInteractionType();
            CvTerm type2 = interaction2.getInteractionType();

            int comp = cvTermComparator.compare(type1, type2);
            if (comp != 0){
                return comp;
            }

            // then compares rigid if both are set
            String rigid1 = interaction1.getRigid();
            String rigid2 = interaction2.getRigid();
            if (rigid1 != null && rigid2 != null){
                comp = rigid1.compareTo(rigid2);
                if (comp != 0){
                    return comp;
                }
            }

            // then compares identifiers (at least one matching identifier)
            if (!interaction1.getIdentifiers().isEmpty() && !interaction2.getIdentifiers().isEmpty()){
                List<Xref> ids1 = new ArrayList<Xref>(interaction1.getIdentifiers());
                List<Xref> ids2 = new ArrayList<Xref>(interaction2.getIdentifiers());
                // sort the collections first
                Collections.sort(ids1, identifierComparator);
                Collections.sort(ids2, identifierComparator);
                // get an iterator
                Iterator<Xref> iterator1 = ids1.iterator();
                Iterator<Xref> iterator2 = ids2.iterator();

                // at least one external identifier must match
                Xref altid1 = iterator1.next();
                Xref altid2 = iterator2.next();
                comp = identifierComparator.compare(altid1, altid2);
                while (comp != 0 && altid1 != null && altid2 != null){
                    // altid1 is before altid2
                    if (comp < 0){
                        // we need to get the next element from ids1
                        if (iterator1.hasNext()){
                            altid1 = iterator1.next();
                            comp = identifierComparator.compare(altid1, altid2);
                        }
                        // ids 1 is empty, we can stop here
                        else {
                            altid1 = null;
                        }
                    }
                    // altid2 is before altid1
                    else {
                        // we need to get the next element from ids2
                        if (iterator2.hasNext()){
                            altid2 = iterator2.next();
                            comp = identifierComparator.compare(altid1, altid2);
                        }
                        // ids 2 is empty, we can stop here
                        else {
                            altid2 = null;
                        }
                    }
                }

                return comp;
            }
            else {
                // then compares shortnames if both are set
                String shortname1 = interaction1.getShortName();
                String shortname2 = interaction2.getShortName();
                if (shortname1 != null && shortname2 != null){
                    return shortname1.compareTo(shortname2);
                }
                else if (shortname1 != null) {
                    return BEFORE;
                }
                else if (shortname2 != null){
                    return AFTER;
                }
            }

            return comp;
        }
    }

    /**
     * Use DefaultInteractionBaseComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (defaultInteractionComparator == null){
            defaultInteractionComparator = new DefaultInteractionBaseComparator();
        }

        return defaultInteractionComparator.compare(interaction1, interaction2) == 0;
    }
}
