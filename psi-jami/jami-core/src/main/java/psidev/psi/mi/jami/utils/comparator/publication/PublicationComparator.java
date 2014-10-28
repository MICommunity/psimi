package psidev.psi.mi.jami.utils.comparator.publication;

import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;
import psidev.psi.mi.jami.utils.comparator.xref.XrefsCollectionComparator;

import java.util.*;

/**
 * publication comparator.
 * It will first compare IMEx identifiers (publication with IMEx will always come first).
 * If both IMEx identifiers are not, it will only compare the identifiers (pubmed, then doi, then all identifiers) using UnambiguousExternalIdentifierComparator (publications with identifiers will always come first).
 * If both publication identifiers are not set, it will look at first publication title (case insensitive),
 * then the authors (order is taken into account), then the journal (case insensitive) and finally the publication date.
 * - Two publications which are null are equals
 * - The publication which is not null is before null.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public class PublicationComparator implements Comparator<Publication>{

    private CollectionComparator<Xref> identifierCollectionComparator;
    private Comparator<Xref> identifierComparator;

    public PublicationComparator(Comparator<Xref> identifierComparator) {
        if (identifierComparator == null){
            throw new IllegalArgumentException("The identifier comparator cannot be null in a publication comparator");
        }
        this.identifierComparator = identifierComparator;
        this.identifierCollectionComparator = new XrefsCollectionComparator(identifierComparator);
    }

    public PublicationComparator(CollectionComparator<Xref> identifiersComparator) {
        if (identifierComparator == null){
            throw new IllegalArgumentException("The identifiers comparator cannot be null in a publication comparator");
        }
        this.identifierCollectionComparator = identifiersComparator;
        this.identifierComparator = this.identifierCollectionComparator.getObjectComparator();
    }

    public Comparator<Xref> getIdentifierComparator() {
        return identifierComparator;
    }

    public CollectionComparator<Xref> getIdentifierCollectionComparator() {
        return identifierCollectionComparator;
    }

    /**
     * It will first compare IMEx identifiers (publication with IMEx will always come first).
     * If both IMEx identifiers are not, it will only compare the identifiers (pubmed, then doi, then all identifiers) using UnambiguousExternalIdentifierComparator (publications with identifiers will always come first).
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
                String pubmed1 = publication1.getPubmedId();
                String pubmed2 = publication2.getPubmedId();
                String doi1 = publication1.getDoi();
                String doi2 = publication2.getDoi();

                // both pubmed are set, we should only compare pubmeds
                if (pubmed1 != null && pubmed2 != null){
                    return pubmed1.compareTo(pubmed2);
                }
                else if (pubmed1 != null){
                    return BEFORE;
                }
                else if (pubmed2 != null){
                    return AFTER;
                }
                // both dois are set, we should only compare dois
                else if (doi1 != null && doi2 != null){
                    return doi1.compareTo(doi2);
                }
                else if (doi1 != null){
                    return BEFORE;
                }
                else if (doi2 != null){
                    return AFTER;
                }
                // compare all identifiers first
                else if (!publication1.getIdentifiers().isEmpty() || !publication2.getIdentifiers().isEmpty()){

                    Collection<Xref> identifiers1 = publication1.getIdentifiers();
                    Collection<Xref> identifiers2 = publication2.getIdentifiers();
                    return identifierCollectionComparator.compare(identifiers1, identifiers2);
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
                            Date date2 = publication2.getPublicationDate();

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
}
