package psidev.psi.mi.jami.utils.comparator.publication;

import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

import java.util.Date;

/**
 * Default curated AbstractPublicationComparator
 * It uses a DefaultPublicationComparator to compares the bibliographic details and then will compare first the curation depth, then the source using DefaultCvTermComparator and then the released date.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public class DefaultCuratedPublicationComparator {

    /**
     * Use DefaultCuratedPublicationComparator to know if two publications are equals.
     * @param publication1
     * @param publication2
     * @return true if the two publications are equal
     */
    public static boolean areEquals(Publication publication1, Publication publication2){
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (publication1 == null && publication2 == null){
            return true;
        }
        else if (publication1 == null || publication2 == null){
            return false;
        }
        else {

            if (!DefaultPublicationComparator.areEquals(publication1, publication2)){
                return false;
            }

            // compares curation depth
            CurationDepth depth1 = publication1.getCurationDepth();
            CurationDepth depth2 = publication2.getCurationDepth();

            if (!depth1.equals(depth2)){
                return false;
            }

            // compares sources
            Source source1 = publication1.getSource();
            Source source2 = publication2.getSource();

            if (!DefaultCvTermComparator.areEquals(source1, source2)){
                return false;
            }

            // compares release dates
            Date date1 = publication1.getReleasedDate();
            Date date2 = publication2.getReleasedDate();

            if (date1 == null && date2 == null){
                return true;
            }
            else if (date1 == null || date2 == null){
                return false;
            }
            else {
                return date1.equals(date2);
            }
        }
    }
}
