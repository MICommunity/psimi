package psidev.psi.mi.jami.utils.comparator;

/**
 * Strict comparator for external identifiers:
 * - Two external identifiers which are null are equals
 * - The external identifier which is not null is before null.
 * - If the two external identifiers are set :
 *     - use StrictCvTermComparator to compare the databases. If both databases are equal, compare the ids (is case sensitive)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class StrictExternalIdentifierComparator extends ExternalIdentifierComparator<StrictCvTermComparator>{
    @Override
    protected void instantiateDatabaseComparator() {
        this.databaseComparator = new StrictCvTermComparator();
    }
}
