package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.*;

import java.util.Comparator;

/**
 * Generic Interactor Comparator.
 *
 * Bioactive entities come first, then proteins, then genes, then nucleic acids, then complexes and finally InteractorSet.
 * If two interactors are from the same Interactor interface, it will use a more specific Comparator :
 * - Uses BioactiveEntityComparator for comparing BioactiveEntity objects.
 * - Uses ProteinComparator for comparing Protein objects.
 * - Uses GeneComparator for comparing Gene objects.
 * - Uses NucleicAcidComparator for comparing NucleicAcids objects.
 * - Uses ComplexComparator for comparing complexes
 * - Uses InteractorCandidatesComparator for comparing interactor candidates
 * - Uses PolymerComparator for comparing polymers
 * - use AbstractInteractorBaseComparator for comparing basic interactors that are not one of the above.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class InteractorComparator implements Comparator<Interactor> {

    protected BioactiveEntityComparator bioactiveEntityComparator;
    protected GeneComparator geneComparator;
    protected ProteinComparator proteinComparator;
    protected NucleicAcidComparator nucleicAcidComparator;
    protected Comparator<Interactor> interactorBaseComparator;
    protected ComplexComparator complexComparator;
    protected InteractorCandidatesComparator interactorCandaidatesComparator;
    protected PolymerComparator polymerComparator;

    /**
     * Creates a new InteractorComparator.
     * @param interactorBaseComparator : required to create more specific comparators and to compare basic interactor objects
     * @param complexComparator : required to compare complex objects
     */
    public InteractorComparator(Comparator<Interactor> interactorBaseComparator, ComplexComparator complexComparator){
        if (interactorBaseComparator == null){
            throw new IllegalArgumentException("The interactorBaseComparator is required to create more specific interactor comparators and compares basic interactor properties. It cannot be null");
        }
        this.interactorBaseComparator = interactorBaseComparator;
        this.bioactiveEntityComparator = new BioactiveEntityComparator(this.interactorBaseComparator);
        this.geneComparator = new GeneComparator(this.interactorBaseComparator);
        this.proteinComparator = new ProteinComparator(this.interactorBaseComparator);
        this.nucleicAcidComparator = new NucleicAcidComparator(this.interactorBaseComparator);
        this.polymerComparator = new PolymerComparator(this.interactorBaseComparator);

        if (complexComparator == null){
            throw new IllegalArgumentException("The ComplexComparator is required to compare complexes. It cannot be null");
        }
        this.complexComparator = complexComparator;
        this.interactorCandaidatesComparator = new InteractorCandidatesComparator(this);
    }

    public BioactiveEntityComparator getBioactiveEntityComparator() {
        return bioactiveEntityComparator;
    }

    public GeneComparator getGeneComparator() {
        return geneComparator;
    }

    public ProteinComparator getProteinComparator() {
        return proteinComparator;
    }

    public NucleicAcidComparator getNucleicAcidComparator() {
        return nucleicAcidComparator;
    }

    public Comparator<Interactor> getInteractorBaseComparator() {
        return interactorBaseComparator;
    }

    public ComplexComparator getComplexComparator() {
        return complexComparator;
    }

    public PolymerComparator getPolymerComparator() {
        return polymerComparator;
    }

    public InteractorCandidatesComparator getInteractorCandaidatesComparator() {
        return interactorCandaidatesComparator;
    }

    /**
     *
     * Bioactive entities come first, then proteins, then genes, then nucleic acids, then complexes and finally InteractorSet.
     * If two interactors are from the same Interactor interface, it will use a more specific Comparator :
     * - Uses BioactiveEntityComparator for comparing BioactiveEntity objects.
     * - Uses ProteinComparator for comparing Protein objects.
     * - Uses GeneComparator for comparing Gene objects.
     * - Uses NucleicAcidComparator for comparing NucleicAcids objects.
     * - Uses InteractorCandidatesComparator for comparing interactor candidates
     * - Uses polymerComparator for comparing Polymer objects.
     * - use AbstractInteractorBaseComparator for comparing basic interactors that are not one of the above.

     * @param interactor1
     * @param interactor2
     * @return
     */
    public int compare(Interactor interactor1, Interactor interactor2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (interactor1 == null && interactor2 == null){
            return EQUAL;
        }
        else if (interactor1 == null){
            return AFTER;
        }
        else if (interactor2 == null){
            return BEFORE;
        }
        else {
            // first check if both interactors are from the same interface

            // both are small molecules
            boolean isBioactiveEntity1 = interactor1 instanceof BioactiveEntity;
            boolean isBioactiveEntity2 = interactor2 instanceof BioactiveEntity;
            if (isBioactiveEntity1 && isBioactiveEntity2){
                return bioactiveEntityComparator.compare((BioactiveEntity) interactor1, (BioactiveEntity) interactor2);
            }
            // the small molecule is before
            else if (isBioactiveEntity1){
                return BEFORE;
            }
            else if (isBioactiveEntity2){
                return AFTER;
            }
            else {
                // both are proteins
                boolean isProtein1 = interactor1 instanceof Protein;
                boolean isProtein2 = interactor2 instanceof Protein;
                if (isProtein1 && isProtein2){
                    return proteinComparator.compare((Protein) interactor1, (Protein) interactor2);
                }
                // the protein is before
                else if (isProtein1){
                    return BEFORE;
                }
                else if (isProtein2){
                    return AFTER;
                }
                else {
                    // both are genes
                    boolean isGene1 = interactor1 instanceof Gene;
                    boolean isGene2 = interactor2 instanceof Gene;
                    if (isGene1 && isGene2){
                        return geneComparator.compare((Gene) interactor1, (Gene) interactor2);
                    }
                    // the gene is before
                    else if (isGene1){
                        return BEFORE;
                    }
                    else if (isGene2){
                        return AFTER;
                    }
                    else {
                        // both are nucleic acids
                        boolean isNucleicAcid1 = interactor1 instanceof NucleicAcid;
                        boolean isNucleicAcid2 = interactor2 instanceof NucleicAcid;
                        if (isNucleicAcid1 && isNucleicAcid2){
                            return nucleicAcidComparator.compare((NucleicAcid) interactor1, (NucleicAcid) interactor2);
                        }
                        // the nucleic acid is before
                        else if (isNucleicAcid1){
                            return BEFORE;
                        }
                        else if (isNucleicAcid2){
                            return AFTER;
                        }
                        else {
                            boolean isPolymer1 = interactor1 instanceof Polymer;
                            boolean isPolymer2 = interactor2 instanceof Polymer;
                            // both are polymers
                            if (isPolymer1 && isPolymer2){
                                return polymerComparator.compare((Polymer) interactor1, (Polymer) interactor2);
                            }
                            // the polymer is before
                            else if (isPolymer1){
                                return BEFORE;
                            }
                            else if (isPolymer2){
                                return AFTER;
                            }
                            else {
                                boolean isComplex1 = interactor1 instanceof Complex;
                                boolean isComplex2 = interactor2 instanceof Complex;
                                // both are complexes
                                if (isComplex1 && isComplex2){
                                    return complexComparator.compare((Complex) interactor1, (Complex) interactor2);
                                }
                                // the complex is before
                                else if (isComplex1){
                                    return BEFORE;
                                }
                                else if (isComplex2){
                                    return AFTER;
                                }
                                else {
                                    // both are interactor candidates
                                    boolean isCandidates1 = interactor1 instanceof InteractorSet;
                                    boolean isCandidates2 = interactor2 instanceof InteractorSet;
                                    if (isCandidates1 && isCandidates2){
                                        return complexComparator.compare((Complex) interactor1, (Complex) interactor2);
                                    }
                                    // the complex is before
                                    else if (isCandidates1){
                                        return BEFORE;
                                    }
                                    else if (isCandidates2){
                                        return AFTER;
                                    }
                                    else {
                                        return interactorBaseComparator.compare(interactor1, interactor2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
