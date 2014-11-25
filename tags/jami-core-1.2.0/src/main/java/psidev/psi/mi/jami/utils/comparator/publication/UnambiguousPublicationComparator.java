package psidev.psi.mi.jami.utils.comparator.publication;

import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.xref.UnambiguousExternalIdentifierComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Unambiguous publication comparator.
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

public class UnambiguousPublicationComparator extends PublicationComparator{

    private static UnambiguousPublicationComparator unambiguousPublicationComparator;

    /**
     * Creates a new UnambiguousPublicationComparator based on UnambiguousExternalIdentifierComparator
     */
    public UnambiguousPublicationComparator() {
        super(new UnambiguousExternalIdentifierComparator());
    }

    public UnambiguousExternalIdentifierComparator getIdentifierComparator() {
        return (UnambiguousExternalIdentifierComparator)super.getIdentifierComparator();
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

        if (pub.getImexId() != null){
            hashcode = 31*hashcode + pub.getImexId().hashCode();
        }
        else if (pub.getPubmedId() != null){
            hashcode = 31*hashcode + pub.getPubmedId().hashCode();
        }
        else if (pub.getDoi() != null){
            hashcode = 31*hashcode + pub.getDoi().hashCode();
        }
        else if (!pub.getIdentifiers().isEmpty()){
            List<Xref> list1 = new ArrayList<Xref>(pub.getIdentifiers());

            Collections.sort(list1, unambiguousPublicationComparator.getIdentifierCollectionComparator().getObjectComparator());
            for (Xref id : list1){
                hashcode = 31*hashcode + UnambiguousExternalIdentifierComparator.hashCode(id);
            }
        }
        else {
            hashcode = 31*hashcode + (pub.getTitle() != null ? pub.getTitle().toLowerCase().trim().hashCode() : 0);

            List<String> authors = pub.getAuthors();
            for (String author : authors){
                hashcode = 31*hashcode + author.toLowerCase().trim().hashCode();
            }

            hashcode = 31*hashcode + (pub.getJournal() != null ? pub.getJournal().toLowerCase().trim().hashCode() : 0);
            hashcode = 31*hashcode + (pub.getPublicationDate() != null ? pub.getPublicationDate().hashCode() : 0);
        }

        return hashcode;
    }
}
