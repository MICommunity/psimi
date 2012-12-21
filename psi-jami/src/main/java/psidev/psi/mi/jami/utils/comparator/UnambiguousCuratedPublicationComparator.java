package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.Publication;

/**
 * Unambiguous curated publication comparator.
 * It uses a UnambiguousPublicationComparator to compares the bibliographic details and then will compare first the curation depth and then the released date.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public class UnambiguousCuratedPublicationComparator extends CuratedPublicationComparator{

    private static UnambiguousCuratedPublicationComparator unambiguousCuratedPublicationComparator;

    /**
     * Creates a new DefaultCuratedPublicationComparator based on DefaultPublicationComparator
     */
    public UnambiguousCuratedPublicationComparator() {
        super(new UnambiguousPublicationComparator());
    }

    @Override
    public UnambiguousPublicationComparator getPublicationComparator() {
        return (UnambiguousPublicationComparator) publicationComparator;
    }

    @Override
    /**
     * It uses a UnambiguousPublicationComparator to compares the bibliographic details and then will compare first the curation depth and then the released date.
     */
    public int compare(Publication publication1, Publication publication2) {
        return super.compare(publication1, publication2);
    }

    /**
     * Use UnambiguousCuratedPublicationComparator to know if two publications are equals.
     * @param pub1
     * @param pub2
     * @return true if the two publications are equal
     */
    public static boolean areEquals(Publication pub1, Publication pub2){
        if (unambiguousCuratedPublicationComparator == null){
            unambiguousCuratedPublicationComparator = new UnambiguousCuratedPublicationComparator();
        }

        return unambiguousCuratedPublicationComparator.compare(pub1, pub2) == 0;
    }
}
