package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.Alias;

/**
 * Default alias comparator.
 * It will first compare alias types using DefaultCvTermComparator and then alias names (case sensitive)

 * - Two aliases which are null are equals
 * - The alias which is not null is before null.
 * - If the alias types are not set, compares the names (case sensitive)
 * - If both alias types are set, use DefaultCvTermComparator to compare the alias types. If they are equals, compares the names (case sensitive)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class DefaultAliasComparator extends AliasComparator {

    /**
     * Creates a new AliasComparator with DefaultCvTermComparator
     */
    public DefaultAliasComparator() {
        super(new DefaultCvTermComparator());
    }

    @Override
    public DefaultCvTermComparator getTypeComparator() {
        return (DefaultCvTermComparator) this.typeComparator;
    }

    @Override
    /**
     * It will first compare alias types using DefaultCvTermComparator and then alias names (case sensitive)
     * - Two aliases which are null are equals
     * - The alias which is not null is before null.
     * - If the alias types are not set, compares the names (case sensitive)
     * - If both alias types are set, use DefaultCvTermComparator to compare the alias types. If they are equals, compares the names (case sensitive)
     *  @param alias1
     *  @parma alias2
     */
    public int compare(Alias alias1, Alias alias2) {
        return super.compare(alias1, alias2);
    }
}
