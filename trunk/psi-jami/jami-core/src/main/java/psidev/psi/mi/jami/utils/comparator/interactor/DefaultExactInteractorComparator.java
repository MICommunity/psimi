package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;

/**
 * Default exact Interactor Comparator.
 *
 * Bioactive entities come first, then proteins, then genes, then nucleic acids, then complexes and finally InteractorSet.
 * If two interactors are from the same Interactor interface, it will use a more specific Comparator :
 * - Uses DefaultExactBioactiveEntityComparator for comparing BioactiveEntity objects.
 * - Uses DefaultExactProteinComparator for comparing Protein objects.
 * - Uses DefaultExactGeneComparator for comparing Gene objects.
 * - Uses DefaultExactNucleicAcidComparator for comparing NucleicAcids objects.
 * - Uses DefaultExactPolymerComparator for comparing Polymer objects
 * - Uses DefaultExactComplexComparator for comparing complexes
 * - Uses DefaultExactInteractorSetComparator for comparing interactor candidates
 * - use DefaultExactInteractorBaseComparator for comparing basic interactors that are not one of the above..
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultExactInteractorComparator extends InteractorComparator {

    private static DefaultExactInteractorComparator defaultExactInteractorComparator;

    /**
     * Creates a new DefaultExactInteractorComparator.
     * - Uses DefaultExactBioactiveEntityComparator for comparing BioactiveEntity objects.
     * - Uses DefaultExactProteinComparator for comparing Protein objects.
     * - Uses DefaultExactGeneComparator for comparing Gene objects.
     * - Uses DefaultExactNucleicAcidComparator for comparing NucleicAcids objects.
     * - Uses DefaultExactPolymerComparator for comparing Polymer objects
     * - Uses DefaultExactComplexComparator for comparing complexes
     * - Uses DefaultExactInteractorSetComparator for comparing interactor candidates
     * - use DefaultExactInteractorBaseComparator for comparing basic interactors that are not one of the above..
     */
    public DefaultExactInteractorComparator() {
        super(new DefaultInteractorBaseComparator(), new DefaultExactComplexComparator(), new DefaultExactPolymerComparator(),
                new DefaultExactBioactiveEntityComparator(), new DefaultExactGeneComparator(), new DefaultExactNucleicAcidComparator(),
                new DefaultExactProteinComparator());
    }

    @Override
    public DefaultExactInteractorBaseComparator getInteractorBaseComparator() {
        return (DefaultExactInteractorBaseComparator) this.interactorBaseComparator;
    }

    @Override
    public DefaultExactComplexComparator getComplexComparator() {
        return (DefaultExactComplexComparator) this.complexComparator;
    }

    @Override
    public DefaultExactBioactiveEntityComparator getBioactiveEntityComparator() {
        return (DefaultExactBioactiveEntityComparator) super.getBioactiveEntityComparator();
    }

    @Override
    public DefaultExactPolymerComparator getPolymerComparator() {
        return (DefaultExactPolymerComparator) super.getPolymerComparator();
    }

    @Override
    public DefaultExactGeneComparator getGeneComparator() {
        return (DefaultExactGeneComparator) super.getGeneComparator();
    }

    @Override
    public DefaultExactNucleicAcidComparator getNucleicAcidComparator() {
        return (DefaultExactNucleicAcidComparator) super.getNucleicAcidComparator();
    }

    @Override
    public DefaultExactProteinComparator getProteinComparator() {
        return (DefaultExactProteinComparator) super.getProteinComparator();
    }

    @Override
    /**
     * Bioactive entities come first, then proteins, then genes, then nucleic acids, then complexes and finally InteractorSet.
     * If two interactors are from the same Interactor interface, it will use a more specific Comparator :
     * - Uses DefaultExactBioactiveEntityComparator for comparing BioactiveEntity objects.
     * - Uses DefaultExactProteinComparator for comparing Protein objects.
     * - Uses DefaultExactGeneComparator for comparing Gene objects.
     * - Uses DefaultExactNucleicAcidComparator for comparing NucleicAcids objects.
     * - Uses DefaultExactPolymerComparator for comparing Polymer objects
     * - Uses DefaultExactComplexComparator for comparing complexes
     * - Uses DefaultExactInteractorSetComparator for comparing interactor candidates
     * - use DefaultExactInteractorBaseComparator for comparing basic interactors that are not one of the above..
     */
    public int compare(Interactor interactor1, Interactor interactor2) {
        return super.compare(interactor1, interactor2);
    }

    /**
     * Use DefaultExactInteractorComparator to know if two interactors are equals.
     * @param interactor1
     * @param interactor2
     * @return true if the two interactors are equal
     */
    public static boolean areEquals(Interactor interactor1, Interactor interactor2){
        if (defaultExactInteractorComparator == null){
            defaultExactInteractorComparator = new DefaultExactInteractorComparator();
        }

        return defaultExactInteractorComparator.compare(interactor1, interactor2) == 0;
    }
}
