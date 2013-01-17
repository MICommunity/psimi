package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.*;

import java.util.*;

/**
 * Generic Interactor Comparator.
 *
 * Bioactive entities come first, then proteins, then genes and finally nucleic acids.
 * If two interactors are from the same Interactor interface, it will use a more specific Comparator :
 * - Uses BioactiveEntityComparator for comparing BioactiveEntity objects.
 * - Uses ProteinComparator for comparing Protein objects.
 * - Uses GeneComparator for comparing Gene objects.
 * - Uses NucleicAcidComparator for comparing NucleicAcids objects.
 * - use Comparator<Interactor> for comparing basic interactors that are not one of the above.
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
    protected InteractorBaseComparator interactorBaseComparator;

    /**
     * Creates a new InteractorComparator.
     * @param interactorBaseComparator : required to create more specific comparators and to compare basic interactor objects
     */
    public InteractorComparator(InteractorBaseComparator interactorBaseComparator){
        if (interactorBaseComparator == null){
            throw new IllegalArgumentException("The interactorBaseComparator is required to create more specific interactor comparators and compares basic interactor properties. It cannot be null");
        }
        this.interactorBaseComparator = interactorBaseComparator;
        this.bioactiveEntityComparator = new BioactiveEntityComparator(this.interactorBaseComparator);
        this.geneComparator = new GeneComparator(this.interactorBaseComparator);
        this.proteinComparator = new ProteinComparator(this.interactorBaseComparator);
        this.nucleicAcidComparator = new NucleicAcidComparator(this.interactorBaseComparator);
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

    public InteractorBaseComparator getInteractorBaseComparator() {
        return interactorBaseComparator;
    }

    /**
     *
     *  * Bioactive entities come first, then proteins, then genes and finally nucleic acids.
     * If two interactors are from the same Interactor interface, it will use a more specific Comparator :
     * - Uses BioactiveEntityComparator for comparing BioactiveEntity objects.
     * - Uses ProteinComparator for comparing Protein objects.
     * - Uses GeneComparator for comparing Gene objects.
     * - Uses NucleicAcidComparator for comparing NucleicAcids objects.
     *
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
            if (interactor1 instanceof BioactiveEntity && interactor2 instanceof BioactiveEntity){
                return bioactiveEntityComparator.compare((BioactiveEntity) interactor1, (BioactiveEntity) interactor2);
            }
            // the small molecule is before
            else if (interactor1 instanceof BioactiveEntity){
                return BEFORE;
            }
            else if (interactor2 instanceof BioactiveEntity){
                return AFTER;
            }
            else if (interactor1 instanceof Protein && interactor2 instanceof Protein){
                return proteinComparator.compare((Protein) interactor1, (Protein) interactor2);
            }
            // the protein is before
            else if (interactor1 instanceof Protein){
                return BEFORE;
            }
            else if (interactor2 instanceof Protein){
                return AFTER;
            }
            // both are genes
            else if (interactor1 instanceof Gene && interactor2 instanceof Gene){
                return geneComparator.compare((Gene) interactor1, (Gene) interactor2);
            }
            // the gene is before
            else if (interactor1 instanceof Gene){
                return BEFORE;
            }
            else if (interactor2 instanceof Gene){
                return AFTER;
            }
            // both are nucleic acids
            else if (interactor1 instanceof NucleicAcid && interactor2 instanceof NucleicAcid){
                return nucleicAcidComparator.compare((NucleicAcid) interactor1, (NucleicAcid) interactor2);
            }
            // the nucleic acid is before
            else if (interactor1 instanceof NucleicAcid){
                return BEFORE;
            }
            else if (interactor2 instanceof NucleicAcid){
                return AFTER;
            }

            return EQUAL;
        }
    }
}
