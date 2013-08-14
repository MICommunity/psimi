package psidev.psi.mi.jami.utils.comparator.publication;

import psidev.psi.mi.jami.model.CurationDepth;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Source;

import java.util.Comparator;
import java.util.Date;

/**
 * Simple comparator for curated publications.
 * It uses a AbstractPublicationComparator to compares the bibliographic details and then will compare first the curation depth, then the source and then the released date.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public class CuratedPublicationComparator implements Comparator<Publication> {

    protected Comparator<Publication> publicationComparator;
    protected Comparator<CvTerm> sourceComparator;

    /**
     * Creates a new CuratedPublicationComparator.
     * @param pubComparator : the comparator for the publication (not curation information). It is required
     * @param sourceComparator : the source comparator
     */
    public CuratedPublicationComparator(Comparator<Publication> pubComparator, Comparator<CvTerm> sourceComparator){
        if (pubComparator == null){
            throw new IllegalArgumentException("The publication comparator is required to compare basic publication bibliographic properties. It cannot be null");
        }
        this.publicationComparator = pubComparator;
        if (sourceComparator == null){
            throw new IllegalArgumentException("The source comparator is required to compare publication sources. It cannot be null");
        }
        this.sourceComparator = sourceComparator;
    }

    public Comparator<Publication> getPublicationComparator() {
        return publicationComparator;
    }

    public Comparator<CvTerm> getSourceComparator() {
        return sourceComparator;
    }

    /**
     * It uses a AbstractPublicationComparator to compares the bibliographic details and then will compare first the curation depth, then the source and then the released date.
     * @param publication1
     * @param publication2
     * @return
     */
    public int compare(Publication publication1, Publication publication2) {

        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (publication1 == null && publication2 == null){
            return EQUAL;
        }
        else if (publication1 == null){
            return AFTER;
        }
        else if (publication2 == null){
            return BEFORE;
        }
        else {

            int comp = publicationComparator.compare(publication1, publication2);
            if (comp != 0){
                return comp;
            }

            // compares curation depth
            CurationDepth depth1 = publication1.getCurationDepth();
            CurationDepth depth2 = publication2.getCurationDepth();

            int comp2 = depth1.compareTo(depth2);
            if (comp2 != 0){
                return comp2;
            }

            // compares sources
            Source source1 = publication1.getSource();
            Source source2 = publication2.getSource();

            comp2 = sourceComparator.compare(source1, source2);
            if (comp2 != 0){
                return comp2;
            }

            // compares release dates
            Date date1 = publication1.getReleasedDate();
            Date date2 = publication2.getReleasedDate();

            if (date1 == null && date2 == null){
                 return EQUAL;
            }
            else if (date1 == null){
                return AFTER;
            }
            else if (date2 == null){
                return BEFORE;
            }
            else if (date1.before(date2)){
                return BEFORE;
            }
            else if (date2.before(date1)){
                return AFTER;
            }
            else {
                return EQUAL;
            }
        }
    }
}
