package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.ExternalIdentifier;

/**
 * Default comparator for external identifiers.
 * It compares first the databases using DefaultCvTermComparator and then the ids (case sensitive) but ignores the version.
 *
 * - Two external identifiers which are null are equals
 * - The external identifier which is not null is before null.
 * - If the two external identifiers are set :
 *     - use DefaultCvTermComparator to compare the databases. If both databases are equal, compare the ids (is case sensitive)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class DefaultExternalIdentifierComparator extends ExternalIdentifierComparator {

    public DefaultExternalIdentifierComparator() {
        super(new DefaultCvTermComparator());
    }

    @Override
    public DefaultCvTermComparator getDatabaseComparator(){
        return (DefaultCvTermComparator) databaseComparator;
    }

    @Override
    /**
     * It compares first the databases using DefaultCvTermComparator and then the ids but ignores the version.
     *
     * - Two external identifiers which are null are equals
     * - The external identifier which is not null is before null.
     * - If the two external identifiers are set :
     *     - use DefaultCvTermComparator to compare the databases. If both databases are equal, compare the ids (is case sensitive)
     */
    public int compare(ExternalIdentifier externalIdentifier1, ExternalIdentifier externalIdentifier2) {
        return super.compare(externalIdentifier1, externalIdentifier2);
    }
}
