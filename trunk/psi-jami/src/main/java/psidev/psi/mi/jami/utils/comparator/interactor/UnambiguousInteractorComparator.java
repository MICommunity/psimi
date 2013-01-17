package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;

/**
 * Unambiguous generic Interactor Comparator.
 *
 * Bioactive entities come first, then proteins, then genes and finally nucleic acids.
 * If two interactors are from the same Interactor interface, it will use a more specific Comparator :
 * - Uses UnambiguousBioactiveEntityComparator for comparing BioactiveEntity objects.
 * - Uses UnambiguousProteinComparator for comparing Protein objects.
 * - Uses UnambiguousGeneComparator for comparing Gene objects.
 * - Uses UnambiguousNucleicAcidComparator for comparing NucleicAcids objects.
 * - use UnambiguousInteractorComparator for comparing basic interactors that are not one of the above.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousInteractorComparator extends InteractorComparator{

    private static UnambiguousInteractorComparator unambiguousInteractorComparator;

    /**
     * Creates a new UnambiguousInteractorComparator.
     * - Uses UnambiguousBioactiveEntityComparator for comparing BioactiveEntity objects.
     * - Uses UnambiguousProteinComparator for comparing Protein objects.
     * - Uses UnambiguousGeneComparator for comparing Gene objects.
     * - Uses UnambiguousNucleicAcidComparator for comparing NucleicAcids objects.
     * - use UnambiguousInteractorComparator for comparing basic interactors that are not one of the above.
     */
    public UnambiguousInteractorComparator() {
        super(new DefaultInteractorBaseComparator());
    }

    @Override
    public UnambiguousInteractorBaseComparator getInteractorBaseComparator() {
        return (UnambiguousInteractorBaseComparator) this.interactorBaseComparator;
    }

    @Override
    /**
     * Bioactive entities come first, then proteins, then genes and finally nucleic acids.
     * If two interactors are from the same Interactor interface, it will use a more specific Comparator :
     * - Uses UnambiguousBioactiveEntityComparator for comparing BioactiveEntity objects.
     * - Uses UnambiguousProteinComparator for comparing Protein objects.
     * - Uses UnambiguousGeneComparator for comparing Gene objects.
     * - Uses UnambiguousNucleicAcidComparator for comparing NucleicAcids objects.
     * - use UnambiguousInteractorComparator for comparing basic interactors that are not one of the above.
     */
    public int compare(Interactor interactor1, Interactor interactor2) {
        return super.compare(interactor1, interactor2);
    }

    /**
     * Use UnambiguousInteractorComparator to know if two interactors are equals.
     * @param interactor1
     * @param interactor2
     * @return true if the two interactors are equal
     */
    public static boolean areEquals(Interactor interactor1, Interactor interactor2){
        if (unambiguousInteractorComparator == null){
            unambiguousInteractorComparator = new UnambiguousInteractorComparator();
        }

        return unambiguousInteractorComparator.compare(interactor1, interactor2) == 0;
    }
}
