package psidev.psi.mi.jami.utils.comparator.publication;

import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultExternalIdentifierComparator;

import java.util.*;

/**
 * Default publication comparator
 * It will only look at IMEx identifiers if both are set.
 * If one IMEx identifier is not set and both identifiers are set, it will only look at the identifiers using DefaultExternalIdentifierComparator.
 * If one publication identifier is not set, it will look at first publication title (case insensitive),
 * then the authors (order is taken into account), then the journal (case insensitive) and finally the publication date.
 * - Two publications which are null are equals
 * - The publication which is not null is before null.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public class DefaultPublicationComparator extends AbstractPublicationComparator {

    private static DefaultPublicationComparator defaultPublicationComparator;

    /**
     * Creates a new DefaultPublicationComparator based on DefaultExternalIdentifierComparator
     */
    public DefaultPublicationComparator() {
        super(new DefaultExternalIdentifierComparator());
    }

    @Override
    public DefaultExternalIdentifierComparator getIdentifierComparator() {
        return (DefaultExternalIdentifierComparator) identifierComparator;
    }

    @Override
    /**
     * It will only look at IMEx identifiers if both are set.
     * If one IMEx identifier is not set and both identifiers are set, it will only look at the identifiers using DefaultExternalIdentifierComparator.
     * If one publication identifier is not set, it will look at first publication title (case insensitive),
     * then the authors (order is taken into account, case insensitive), then the journal (case insensitive) and finally the publication date.
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

            boolean hasImexId1 = imexId1 != null;
            boolean hasImexId2 = imexId2 != null;

            // when both imex ids are not null, can compare only the imex ids
            if (hasImexId1 && hasImexId2){
                return imexId1.compareTo(imexId2);
            }
            // if one of the imex ids is null (or both), we need to compare the publication identifier.
            else {

                String pubmed1 = publication1.getPubmedId();
                String pubmed2 = publication2.getPubmedId();
                boolean hasPubmedId1 = pubmed1 != null;
                boolean hasPubmedId2 = pubmed2 != null;

                // both pubmeds are set, we should only compare pubmeds
                if (hasPubmedId1 && hasPubmedId2){
                    return pubmed1.compareTo(pubmed2);
                }
                // compare doi
                else {
                    String doi1 = publication1.getDoi();
                    String doi2 = publication2.getDoi();
                    boolean hasDoiId1 = doi1 != null;
                    boolean hasDoiId2 = doi2 != null;

                    // both doi are set, we should only compare dois
                    if (hasDoiId1 && hasDoiId2){
                        return doi1.compareTo(doi2);
                    }
                    // compare other identifier
                    else if (!publication1.getIdentifiers().isEmpty() && !publication2.getIdentifiers().isEmpty()){
                        List<Xref> ids1 = new ArrayList<Xref>(publication1.getIdentifiers());
                        List<Xref> ids2 = new ArrayList<Xref>(publication2.getIdentifiers());
                        // sort the collections first
                        Collections.sort(ids1, identifierComparator);
                        Collections.sort(ids2, identifierComparator);
                        // get an iterator
                        Iterator<Xref> iterator1 = ids1.iterator();
                        Iterator<Xref> iterator2 = ids2.iterator();

                        // at least one external identifier must match
                        Xref altid1 = iterator1.next();
                        Xref altid2 = iterator2.next();
                        int comp = identifierComparator.compare(altid1, altid2);
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
                                comp2 = iterator1.next().toLowerCase().trim().compareTo(iterator2.next().toLowerCase().trim());
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
                                Date date2 = publication2.getPublicationDate();

                                if (date1 == null && date2 == null){
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

                                // if we had one imex id
                                if (hasImexId1){
                                    return BEFORE;
                                }
                                else if (hasImexId2){
                                    return AFTER;
                                }
                                // we had one pubmed id
                                else if (hasPubmedId1){
                                    return BEFORE;
                                }
                                else if (hasImexId2){
                                    return AFTER;
                                }
                                // we had one doi id
                                else if (hasDoiId1){
                                    return BEFORE;
                                }
                                else if (hasDoiId2){
                                    return AFTER;
                                }
                                // we had publication with identifiers
                                else if (!publication1.getIdentifiers().isEmpty()){
                                    return BEFORE;
                                }
                                else if (!publication2.getIdentifiers().isEmpty()){
                                    return AFTER;
                                }
                                // the publications are the same
                                else{
                                    return EQUAL;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Use DefaultPublicationComparator to know if two publications are equals.
     * @param pub1
     * @param pub2
     * @return true if the two publications are equal
     */
    public static boolean areEquals(Publication pub1, Publication pub2){
        if (defaultPublicationComparator == null){
            defaultPublicationComparator = new DefaultPublicationComparator();
        }

        return defaultPublicationComparator.compare(pub1, pub2) == 0;
    }
}
