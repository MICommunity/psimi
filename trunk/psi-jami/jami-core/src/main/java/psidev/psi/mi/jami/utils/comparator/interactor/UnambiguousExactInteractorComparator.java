package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;

/**
 * Unambiguous exact Interactor Comparator.
 *
 * Bioactive entities come first, then proteins, then genes, then nucleic acids, then complexes and finally InteractorSet.
 * If two interactors are from the same Interactor interface, it will use a more specific Comparator :
 * - Uses UnambiguousExactBioactiveEntityComparator for comparing BioactiveEntity objects.
 * - Uses UnambiguousExactProteinComparator for comparing Protein objects.
 * - Uses UnambiguousExactGeneComparator for comparing Gene objects.
 * - Uses UnambiguousExactNucleicAcidComparator for comparing NucleicAcids objects.
 * - Uses UnambiguousExactPolymerComparator for comparing Polymer objects
 * - Uses UnambiguousExactComplexComparator for comparing complexes
 * - Uses UnambiguousExactInteractorSetComparator for comparing interactor candidates
 * - use UnambiguousExactInteractorBaseComparator for comparing basic interactors that are not one of the above..
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousExactInteractorComparator extends InteractorComparator {

    private static UnambiguousExactInteractorComparator unambiguousExactInteractorComparator;

    /**
     * Creates a new UnambiguousExactInteractorComparator.
     * - Uses UnambiguousExactBioactiveEntityComparator for comparing BioactiveEntity objects.
     * - Uses UnambiguousExactProteinComparator for comparing Protein objects.
     * - Uses UnambiguousExactGeneComparator for comparing Gene objects.
     * - Uses UnambiguousExactNucleicAcidComparator for comparing NucleicAcids objects.
     * - Uses UnambiguousExactPolymerComparator for comparing Polymer objects
     * - Uses UnambiguousExactComplexComparator for comparing complexes
     * - Uses UnambiguousExactInteractorSetComparator for comparing interactor candidates
     * - use UnambiguousExactInteractorBaseComparator for comparing basic interactors that are not one of the above..
     * */
    public UnambiguousExactInteractorComparator() {
        super(new UnambiguousExactInteractorBaseComparator(), new UnambiguousExactComplexComparator(), new UnambiguousExactPolymerComparator(),
                new UnambiguousExactBioactiveEntityComparator());
    }

    @Override
    public UnambiguousExactInteractorBaseComparator getInteractorBaseComparator() {
        return (UnambiguousExactInteractorBaseComparator) this.interactorBaseComparator;
    }

    @Override
    public UnambiguousExactComplexComparator getComplexComparator() {
        return (UnambiguousExactComplexComparator) this.complexComparator;
    }

    @Override
    public UnambiguousExactPolymerComparator getPolymerComparator() {
        return (UnambiguousExactPolymerComparator) super.getPolymerComparator();
    }

    @Override
    public UnambiguousExactBioactiveEntityComparator getBioactiveEntityComparator() {
        return (UnambiguousExactBioactiveEntityComparator) super.getBioactiveEntityComparator();
    }

    @Override
    /**
     * Bioactive entities come first, then proteins, then genes, then nucleic acids, then complexes and finally InteractorSet.
     * If two interactors are from the same Interactor interface, it will use a more specific Comparator :
     * - Uses UnambiguousExactBioactiveEntityComparator for comparing BioactiveEntity objects.
     * - Uses UnambiguousExactProteinComparator for comparing Protein objects.
     * - Uses UnambiguousExactGeneComparator for comparing Gene objects.
     * - Uses UnambiguousExactNucleicAcidComparator for comparing NucleicAcids objects.
     * - Uses UnambiguousExactPolymerComparator for comparing Polymer objects
     * - Uses UnambiguousExactComplexComparator for comparing complexes
     * - Uses UnambiguousExactInteractorSetComparator for comparing interactor candidates
     * - use UnambiguousExactInteractorBaseComparator for comparing basic interactors that are not one of the above..
     */
    public int compare(Interactor interactor1, Interactor interactor2) {
        return super.compare(interactor1, interactor2);
    }

    /**
     * Use UnambiguousExactInteractorComparator to know if two interactors are equals.
     * @param interactor1
     * @param interactor2
     * @return true if the two interactors are equal
     */
    public static boolean areEquals(Interactor interactor1, Interactor interactor2){
        if (unambiguousExactInteractorComparator == null){
            unambiguousExactInteractorComparator = new UnambiguousExactInteractorComparator();
        }

        return unambiguousExactInteractorComparator.compare(interactor1, interactor2) == 0;
    }
}
