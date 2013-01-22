package psidev.psi.mi.jami.utils.comparator.publication;

import psidev.psi.mi.jami.model.ExternalIdentifier;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.utils.comparator.xref.UnambiguousExternalIdentifierComparator;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Unambiguous publication comparator.
 * It will first compare IMEx identifiers (publication with IMEx will always come first).
 * If both IMEx identifiers are not, it will only compare the identifiers using UnambiguousExternalIdentifierComparator (publications with identifiers will always come first).
 * If both publication identifiers are not set, it will look at first publication title (case insensitive),
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
     * It will first compare IMEx identifiers (publication with IMEx will always come first).
     * If both IMEx identifiers are not, it will only compare the identifiers using UnambiguousExternalIdentifierComparator (publications with identifiers will always come first).
     * If both publication identifiers are not set, it will look at first publication title (case insensitive),
     * then the authors (order is taken into account), then the journal (case insensitive) and finally the publication date.
     * - Two publications which are null are equals
     * - The publication which is not null is before null.
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

            String imexId1 = publication1.getImexId();
            String imexId2 = publication2.getImexId();

            // when both imex ids are not null, can compare only the imex ids
            if (imexId1 != null && imexId2 != null){
                return imexId1.compareTo(imexId2);
            }
            else if (imexId1 != null){
                return BEFORE;
            }
            else if (imexId2 != null){
                return AFTER;
            }
            // if one of the imex ids is null (or both), we need to compare the publication identifier.
            else {
                ExternalIdentifier identifier1 = publication1.getIdentifier();
                ExternalIdentifier identifier2 = publication2.getIdentifier();

                // both identifiers are set, we should only compare identifiers
                if (identifier1 != null && identifier2 != null){
                    return identifierComparator.compare(identifier1, identifier2);
                }
                else if (identifier1 != null){
                    return BEFORE;
                }
                else if (identifier2 != null){
                    return AFTER;
                }
                // use journal, publication date, publication authors and publication title to compare publications
                else {
                    // first compares titles
                    String title1 = publication1.getTitle();
                    String title2 = publication2.getTitle();

                    int comp;
                    if (title1 == null && title2 != null){
                        return AFTER;
                    }
                    else if (title1 != null && title2 == null){
                        return BEFORE;
                    }
                    else if (title1 != null && title2 != null){
                        comp = title1.toLowerCase().trim().compareTo(title2.toLowerCase().trim());
                    }
                    // if both titles are null, compares the authors
                    else {
                        comp = EQUAL;
                    }

                    if (comp != 0){
                        return comp;
                    }

                    // compare authors
                    List<String> authors1 = publication1.getAuthors();
                    List<String> authors2 = publication2.getAuthors();

                    if (authors1.size() > authors2.size()){
                        return AFTER;
                    }
                    else if (authors2.size() > authors1.size()){
                        return BEFORE;
                    }
                    else {
                        Iterator<String> iterator1 = authors1.iterator();
                        Iterator<String> iterator2 = authors2.iterator();
                        int comp2=0;
                        while (comp2 == 0 && iterator1.hasNext() && iterator2.hasNext()){
                            comp2 = iterator1.next().compareTo(iterator2.next());
                        }

                        if (comp2 != 0){
                            return comp2;
                        }
                        else if (iterator1.hasNext()){
                            return AFTER;
                        }
                        else if (iterator2.hasNext()){
                            return BEFORE;
                        }
                        else {
                            // compares journal
                            String journal1 = publication1.getJournal();
                            String journal2 = publication2.getJournal();

                            int comp3;
                            if (journal1 == null && journal2 != null){
                                return AFTER;
                            }
                            else if (journal1 != null && journal2 == null){
                                return BEFORE;
                            }
                            else if (journal2 != null && journal1 != null){
                                comp3 = journal1.toLowerCase().trim().compareTo(journal2.toLowerCase().trim());
                            }
                            // if both journals are null, compares the publication dates
                            else {
                                comp3 = EQUAL;
                            }

                            if (comp3 != 0){
                                return comp3;
                            }
                            // compares publication dates
                            Date date1 = publication1.getPublicationDate();
                            Date date2 = publication1.getPublicationDate();

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
            }
        }
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

    /**
     *
     * @param pub
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Publication pub){
        if (unambiguousPublicationComparator == null){
            unambiguousPublicationComparator = new UnambiguousPublicationComparator();
        }

        if (pub == null){
            return 0;
        }

        int hashcode = 31;
        hashcode = 31*hashcode + (pub.getImexId() != null ? pub.getImexId().hashCode() : 0);

        ExternalIdentifier identifier = pub.getIdentifier();
        if (identifier != null){
            hashcode = 31*hashcode + unambiguousPublicationComparator.getIdentifierComparator().hashCode(identifier);
        }
        else {
            hashcode = 31*hashcode + (pub.getTitle() != null ? pub.getTitle().toLowerCase().trim().hashCode() : 0);

            List<String> authors = pub.getAuthors();
            for (String author : authors){
                hashcode = 31*hashcode + author.hashCode();
            }

            hashcode = 31*hashcode + (pub.getJournal() != null ? pub.getJournal().hashCode() : 0);
            hashcode = 31*hashcode + (pub.getPublicationDate() != null ? pub.getPublicationDate().hashCode() : 0);
        }

        return hashcode;
    }
}
