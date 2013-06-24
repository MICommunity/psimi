package psidev.psi.mi.jami.utils.comparator.publication;

import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.utils.comparator.ComparatorUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Default publication comparator
 * It will only look at IMEx identifiers if both are set.
 * If one IMEx identifier is not set and both identifiers are set, it will only look at the identifiers using DefaultExternalIdentifierComparator.
 * If one publication identifier is not set, it will look at first publication title (case insensitive),
 * then the authors (order is taken into account), then the journal (case insensitive) and finally the publication date.
 * - Two publications which are null are equals
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public class DefaultPublicationComparator {

    /**
     * Use DefaultPublicationComparator to know if two publications are equals.
     * @param publication1
     * @param publication2
     * @return true if the two publications are equal
     */
    public static boolean areEquals(Publication publication1, Publication publication2){

        if (publication1 == null && publication2 == null){
            return true;
        }
        else if (publication1 == null || publication2 == null){
            return false;
        }
        else {

            String imexId1 = publication1.getImexId();
            String imexId2 = publication2.getImexId();

            boolean hasImexId1 = imexId1 != null;
            boolean hasImexId2 = imexId2 != null;

            // when both imex ids are not null, can compare only the imex ids
            if (hasImexId1 && hasImexId2){
                return imexId1.equals(imexId2);
            }
            // if one of the imex ids is null (or both), we need to compare the publication identifier.
            else {

                String pubmed1 = publication1.getPubmedId();
                String pubmed2 = publication2.getPubmedId();
                boolean hasPubmedId1 = pubmed1 != null;
                boolean hasPubmedId2 = pubmed2 != null;

                // both pubmeds are set, we should only compare pubmeds
                if (hasPubmedId1 && hasPubmedId2){
                    return pubmed1.equals(pubmed2);
                }
                // compare doi
                else {
                    String doi1 = publication1.getDoi();
                    String doi2 = publication2.getDoi();
                    boolean hasDoiId1 = doi1 != null;
                    boolean hasDoiId2 = doi2 != null;

                    // both doi are set, we should only compare dois
                    if (hasDoiId1 && hasDoiId2){
                        return doi1.equals(doi2);
                    }
                    // compare other identifier
                    else if (!publication1.getIdentifiers().isEmpty() && !publication2.getIdentifiers().isEmpty()){
                        return ComparatorUtils.findAtLeastOneMatchingIdentifier(publication1.getIdentifiers(), publication2.getIdentifiers());
                    }
                    // use journal, publication date, publication authors and publication title to compare publications
                    else {
                        // first compares titles
                        String title1 = publication1.getTitle();
                        String title2 = publication2.getTitle();

                        boolean comp;
                        if (title1 == null && title2 != null){
                            return false;
                        }
                        else if (title1 != null && title2 == null){
                            return false;
                        }
                        else if (title1 != null && title2 != null){
                            comp = title1.toLowerCase().trim().equals(title2.toLowerCase().trim());
                        }
                        // if both titles are null, compares the authors
                        else {
                            comp = true;
                        }

                        if (!comp){
                            return comp;
                        }

                        // compare authors
                        List<String> authors1 = publication1.getAuthors();
                        List<String> authors2 = publication2.getAuthors();

                        if (authors1.size() != authors2.size()){
                            return false;
                        }
                        else {
                            Iterator<String> iterator1 = authors1.iterator();
                            Iterator<String> iterator2 = authors2.iterator();
                            boolean comp2 = true;
                            while (comp2 && iterator1.hasNext() && iterator2.hasNext()){
                                comp2 = iterator1.next().toLowerCase().trim().equals(iterator2.next().toLowerCase().trim());
                            }

                            if (!comp2){
                                return comp2;
                            }
                            else if (iterator1.hasNext() || iterator2.hasNext()){
                                return false;
                            }
                            else {
                                // compares journal
                                String journal1 = publication1.getJournal();
                                String journal2 = publication2.getJournal();

                                boolean comp3;
                                if (journal1 == null && journal2 != null){
                                    return false;
                                }
                                else if (journal1 != null && journal2 == null){
                                    return false;
                                }
                                else if (journal2 != null && journal1 != null){
                                    comp3 = journal1.toLowerCase().trim().equals(journal2.toLowerCase().trim());
                                }
                                // if both journals are null, compares the publication dates
                                else {
                                    comp3 = true;
                                }

                                if (!comp3){
                                    return comp3;
                                }
                                // compares publication dates
                                Date date1 = publication1.getPublicationDate();
                                Date date2 = publication2.getPublicationDate();

                                if (date1 == null && date2 == null){
                                }
                                else if (date1 == null || date2 == null){
                                    return false;
                                }
                                else if (date1.equals(date2)){
                                    return true;
                                }

                                // if we had one imex id
                                if (hasImexId1 || hasImexId2){
                                    return false;
                                }
                                // we had one pubmed id
                                else if (hasPubmedId1 || hasPubmedId2){
                                    return false;
                                }
                                // we had one doi id
                                else if (hasDoiId1 || hasDoiId2){
                                    return false;
                                }
                                // we had publication with identifiers
                                else if (!publication1.getIdentifiers().isEmpty() || !publication2.getIdentifiers().isEmpty()){
                                    return false;
                                }
                                // the publications are the same
                                else{
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
