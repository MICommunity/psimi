package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;

/**
 * Default generic Interactor Comparator.
 *
 * Bioactive entities come first, then proteins, then genes and finally nucleic acids.
 * If two interactors are from the same Interactor interface, it will use a more specific Comparator :
 * - Uses DefaultBioactiveEntityComparator for comparing BioactiveEntity objects.
 * - Uses DefaultProteinComparator for comparing Protein objects.
 * - Uses DefaultGeneComparator for comparing Gene objects.
 * - Uses DefaultNucleicAcidComparator for comparing NucleicAcids objects.
 * - use DefaultInteractorComparator for comparing basic interactors that are not one of the above.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultInteractorComparator extends InteractorComparator{

    private static DefaultInteractorComparator defaultInteractorComparator;

    /**
     * Creates a new DefaultInteractorComparator.
     * - Uses DefaultBioactiveEntityComparator for comparing BioactiveEntity objects.
     * - Uses DefaultProteinComparator for comparing Protein objects.
     * - Uses DefaultGeneComparator for comparing Gene objects.
     * - Uses DefaultNucleicAcidComparator for comparing NucleicAcids objects.
     * - use DefaultInteractorComparator for comparing basic interactors that are not one of the above.
     */
    public DefaultInteractorComparator() {
        super(new DefaultInteractorBaseComparator());
    }

    @Override
    public DefaultInteractorBaseComparator getInteractorBaseComparator() {
        return (DefaultInteractorBaseComparator) this.interactorBaseComparator;
    }

    @Override
    /**
     * Bioactive entities come first, then proteins, then genes and finally nucleic acids.
     * If two interactors are from the same Interactor interface, it will use a more specific Comparator :
     * - Uses DefaultBioactiveEntityComparator for comparing BioactiveEntity objects.
     * - Uses DefaultProteinComparator for comparing Protein objects.
     * - Uses DefaultGeneComparator for comparing Gene objects.
     * - Uses DefaultNucleicAcidComparator for comparing NucleicAcids objects.
     * - use DefaultInteractorComparator for comparing basic interactors that are not one of the above.
     */
    public int compare(Interactor interactor1, Interactor interactor2) {
        return super.compare(interactor1, interactor2);
    }

    /**
     * Use DefaultInteractorComparator to know if two interactors are equals.
     * @param interactor1
     * @param interactor2
     * @return true if the two interactors are equal
     */
    public static boolean areEquals(Interactor interactor1, Interactor interactor2){
        if (defaultInteractorComparator == null){
            defaultInteractorComparator = new DefaultInteractorComparator();
        }

        return defaultInteractorComparator.compare(interactor1, interactor2) == 0;
    }
}
