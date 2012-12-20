package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.ExternalIdentifier;

/**
 * Unambiguous comparator for external identifiers.
 * It compares first the databases using UnambiguousCvTermComparator and then the ids (case sensitive) but ignores the version.
 *
 * - Two external identifiers which are null are equals
 * - The external identifier which is not null is before null.
 * - If the two external identifiers are set :
 *     - use UnambiguousCvTermComparator to compare the databases. If both databases are equal, compare the ids (is case sensitive)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class UnambiguousExternalIdentifierComparator extends ExternalIdentifierComparator {

    public UnambiguousExternalIdentifierComparator() {
        super(new UnambiguousCvTermComparator());
    }

    public UnambiguousExternalIdentifierComparator(UnambiguousCvTermComparator comparator) {
        super(comparator);
    }

    @Override
    public UnambiguousCvTermComparator getDatabaseComparator(){
        return (UnambiguousCvTermComparator) databaseComparator;
    }

    @Override
    /**
     * It compares first the databases using UnambiguousCvTermComparator and then the ids but ignores the version.
     *
     * - Two external identifiers which are null are equals
     * - The external identifier which is not null is before null.
     * - If the two external identifiers are set :
     *     - use UnambiguousCvTermComparator to compare the databases. If both databases are equal, compare the ids (is case sensitive)
     */
    public int compare(ExternalIdentifier externalIdentifier1, ExternalIdentifier externalIdentifier2) {
        return super.compare(externalIdentifier1, externalIdentifier2);
    }
}
