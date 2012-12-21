package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.Publication;

/**
 * Unambiguous publication comparator.
 * It will only look at IMEx identifiers if both are set.
 * If one IMEx identifier is not set and both identifiers are set, it will only look at the identifiers using UnambiguousExternalIdentifierComparator.
 * If one publication identifier is not set, it will look at first publication title (case insensitive),
 * then the authors (order is taken into account), then the journal (case insensitive) and finally the publication date.
 * - Two publications which are null are equals
 * - The publication which is not null is before null.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public class UnambiguousPublicationComparator extends PublicationComparator {

    private static UnambiguousPublicationComparator unambiguousPublicationComparator;

    /**
     * Creates a new UnambiguousPublicationComparator based on UnambiguousExternalIdentifierComparator
     */
    public UnambiguousPublicationComparator() {
        super(new UnambiguousExternalIdentifierComparator());
    }

    @Override
    public UnambiguousExternalIdentifierComparator getIdentifierComparator() {
        return (UnambiguousExternalIdentifierComparator) identifierComparator;
    }

    @Override
    /**
     * It will only look at IMEx identifiers if both are set.
     * If one IMEx identifier is not set and both identifiers are set, it will only look at the identifiers using UnambiguousExternalIdentifierComparator.
     * If one publication identifier is not set, it will look at first publication title (case insensitive),
     * then the authors (order is taken into account), then the journal (case insensitive) and finally the publication date.
     * - Two publications which are null are equals
     * - The publication which is not null is before null.
     */
    public int compare(Publication publication1, Publication publication2) {
        return super.compare(publication1, publication2);
    }

    /**
     * Use UnambiguousPublicationComparator to know if two publications are equals.
     * @param pub1
     * @param pub2
     * @return true if the two publications are equal
     */
    public static boolean areEquals(Publication pub1, Publication pub2){
        if (unambiguousPublicationComparator == null){
            unambiguousPublicationComparator = new UnambiguousPublicationComparator();
        }

        return unambiguousPublicationComparator.compare(pub1, pub2) == 0;
    }
}
