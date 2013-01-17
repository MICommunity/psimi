package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.utils.comparator.alias.UnambiguousAliasComparator;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;
import psidev.psi.mi.jami.utils.comparator.xref.UnambiguousExternalIdentifierComparator;

/**
 * Unambiguous Interactor base comparator.
 * It will first compare the interactor types using UnambiguousCvTermComparator. If both types are equal,
 * it will compare organisms using OrganismTaxIdComparator. If both organisms are equal, it will compare Checksums.
 * If at least one checksum is identical, it will use a UnambiguousInteractorBaseComparator to compare basic Interactor properties.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousExactInteractorBaseComparator extends ExactInteractorBaseComparator{

    private static UnambiguousExactInteractorBaseComparator unambiguousExactInteractorComparator;

    /**
     * Creates a new UnambiguousExactInteractorBaseComparator.
     * It will use a UnambiguousInteractorBaseComparator to compare basic interactor properties, a OrganismTaxIdComparator to compare
     * organisms and a UnambiguousCvTermComparator to compare checksum types and interactor types
     */
    public UnambiguousExactInteractorBaseComparator() {
        super(new UnambiguousExternalIdentifierComparator(), new UnambiguousAliasComparator(), new OrganismTaxIdComparator(), new UnambiguousCvTermComparator());
    }

    @Override
    public UnambiguousExternalIdentifierComparator getIdentifierComparator() {
        return (UnambiguousExternalIdentifierComparator) this.identifierComparator;
    }

    @Override
    public UnambiguousAliasComparator getAliasComparator() {
        return (UnambiguousAliasComparator) this.aliasComparator;
    }

    @Override
    public UnambiguousCvTermComparator getTypeComparator() {
        return (UnambiguousCvTermComparator) this.typeComparator;
    }

    @Override
    /**
     * It will first compare the interactor types using UnambiguousCvTermComparator. If both types are equal,
     * it will compare organisms using OrganismTaxIdComparator. If both organisms are equal, it will compare Checksums.
     * If at least one checksum is identical, it will use a UnambiguousInteractorBaseComparator to compare basic Interactor properties.
     */
    public int compare(Interactor interactor1, Interactor interactor2) {
        return super.compare(interactor1, interactor2);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * Use UnambiguousExacttInteractorBaseComparator to know if two interactors are equals.
     * @param interactor1
     * @param interactor2
     * @return true if the two interactors are equal
     */
    public static boolean areEquals(Interactor interactor1, Interactor interactor2){
        if (unambiguousExactInteractorComparator == null){
            unambiguousExactInteractorComparator = new UnambiguousExactInteractorBaseComparator();
        }

        return unambiguousExactInteractorComparator.compare(interactor1, interactor2) == 0;
    }
}
