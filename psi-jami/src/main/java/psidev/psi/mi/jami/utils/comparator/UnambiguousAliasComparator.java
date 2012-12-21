package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.Alias;

/**
 * Unambiguous alias comparator.
 * It will first compare alias types using UnambiguousCvTermComparator and then alias names (case sensitive)
 * - Two aliases which are null are equals
 * - The alias which is not null is before null.
 * - If the alias types are not set, compares the names (case sensitive)
 * - If both alias types are set, use UnambiguousCvTermComparator to compare the alias types. If they are equals, compares the names (case sensitive)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class UnambiguousAliasComparator extends AliasComparator {

    private static UnambiguousAliasComparator unambiguousAliasComparator;

    /**
     * Creates a new AliasComparator with DefaultCvTermComparator
     */
    public UnambiguousAliasComparator() {
        super(new UnambiguousCvTermComparator());
    }

    @Override
    public UnambiguousCvTermComparator getTypeComparator() {
        return (UnambiguousCvTermComparator) this.typeComparator;
    }

    @Override
    /**
     * It will first compare alias types using UnambiguousCvTermComparator and then alias names (case sensitive)
     * - Two aliases which are null are equals
     * - The alias which is not null is before null.
     * - If the alias types are not set, compares the names (case sensitive)
     * - If both alias types are set, use UnambiguousCvTermComparator to compare the alias types. If they are equals, compares the names (case sensitive)
     * @param alias1
     * @param alias2
     */
    public int compare(Alias alias1, Alias alias2) {
        return super.compare(alias1, alias2);
    }

    /**
     * Use UnambiguousAliasComparator to know if two aliases are equals.
     * @param alias1
     * @param alias2
     * @return true if the two aliases are equal
     */
    public static boolean areEquals(Alias alias1, Alias alias2){
        if (unambiguousAliasComparator == null){
            unambiguousAliasComparator = new UnambiguousAliasComparator();
        }

        return unambiguousAliasComparator.compare(alias1, alias2) == 0;
    }
}
