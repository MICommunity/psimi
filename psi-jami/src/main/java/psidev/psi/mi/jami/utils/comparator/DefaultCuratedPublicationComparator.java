package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.Publication;

/**
 * Default curated PublicationComparator
 * It uses a DefaultPublicationComparator to compares the bibliographic details and then will compare first the curation depth and then the released date.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public class DefaultCuratedPublicationComparator extends CuratedPublicationComparator {

    private static DefaultCuratedPublicationComparator defaultCuratedPublicationComparator;

    /**
     * Creates a new DefaultCuratedPublicationComparator based on DefaultPublicationComparator
     */
    public DefaultCuratedPublicationComparator() {
        super(new DefaultPublicationComparator());
    }

    @Override
    public DefaultPublicationComparator getPublicationComparator() {
        return (DefaultPublicationComparator) publicationComparator;
    }

    @Override
    /**
     * It uses a DefaultPublicationComparator to compares the bibliographic details and then will compare first the curation depth and then the released date.
     */
    public int compare(Publication publication1, Publication publication2) {
        return super.compare(publication1, publication2);
    }

    /**
     * Use DefaultCuratedPublicationComparator to know if two publications are equals.
     * @param pub1
     * @param pub2
     * @return true if the two publications are equal
     */
    public static boolean areEquals(Publication pub1, Publication pub2){
        if (defaultCuratedPublicationComparator == null){
            defaultCuratedPublicationComparator = new DefaultCuratedPublicationComparator();
        }

        return defaultCuratedPublicationComparator.compare(pub1, pub2) == 0;
    }
}
