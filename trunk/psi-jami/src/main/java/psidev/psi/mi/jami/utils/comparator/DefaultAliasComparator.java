package psidev.psi.mi.jami.utils.comparator;

/**
 * Default alias comparator.
 *
 * - Two aliases which are null are equals
 * - The alias which is not null is before null.
 * - If the alias types are not set, compares the names (case sensitive)
 * - If both alias types are set, use DefaultCvTermComparator to compare the alias types. If they are equals, compares the names (case sensitive)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class DefaultAliasComparator extends AbstractAliasComparator<DefaultCvTermComparator> {

    @Override
    protected void instantiateTypeComparator() {
        this.typeComparator = new DefaultCvTermComparator();
    }
}
