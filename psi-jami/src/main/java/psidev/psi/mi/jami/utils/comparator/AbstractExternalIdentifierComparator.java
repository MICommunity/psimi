package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExternalIdentifier;

import java.util.Comparator;

/**
 * Abstract comparator for External identifier.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public abstract class AbstractExternalIdentifierComparator<T extends AbstractCvTermComparator> implements Comparator<ExternalIdentifier>  {

    protected T databaseComparator;

    public AbstractExternalIdentifierComparator(){
        instantiateDatabaseComparator();
    }

    /**
     * Create the database comparator
     */
    protected abstract void instantiateDatabaseComparator();

    /**
     * Compares first the databases of externalIdentifier1 and externalIdentifier2. If the two databases are equals, it will compare the
     * ids and it is case sensitive.
     * - Two external identifiers which are null are equals
     * - The external identifier which is not null is before null.
     * @param externalIdentifier1
     * @param externalIdentifier2
     * @return
     */
    public int compare(ExternalIdentifier externalIdentifier1, ExternalIdentifier externalIdentifier2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        CvTerm database1 = externalIdentifier1.getDatabase();
        CvTerm database2 = externalIdentifier2.getDatabase();

        int comp = databaseComparator.compare(database1, database2);
        if (comp != 0){
            return comp;
        }
        // check identifiers which cannot be null
        String id1 = externalIdentifier1.getId();
        String id2 = externalIdentifier2.getId();

        return id1.compareTo(id2);
    }
}
