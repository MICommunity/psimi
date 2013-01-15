package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

/**
 * Unambiguous proteins comparator.
 * It will look first for uniprotkb identifier if both are set. If the uniprotkb identifiers are not both set, it will look at the
 * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the rogids. If at least one rogid is not set, it will look at the gene names.
 * If at least one gene name is not set, it will look at sequence/organism.
 * If the properties of a protein were not enough to compare the proteins, it will use UnambiguousInteractorComparator to compare the interactor properties
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class UnambiguousProteinComparator extends ProteinComparator {

    private static UnambiguousProteinComparator unambiguousProteinComparator;

    /**
     * Creates a new UnambiguousProteinComparator. It will uses a UnambiguousInteractorComparator to compare interactor properties and a
     * OrganismTaxIdComparator to compares organism.
     */
    public UnambiguousProteinComparator(){
        super(new UnambiguousInteractorComparator(), new OrganismTaxIdComparator());
    }

    @Override
    /**
     * It will look first for uniprotkb identifier if both are set. If the uniprotkb identifiers are not both set, it will look at the
     * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the rogids. If at least one rogid is not set, it will look at the gene names.
     * If at least one gene name is not set, it will look at sequence/organism.
     * If the properties of a protein were not enough to compare the proteins, it will use UnambiguousInteractorComparator to compare the interactor properties
     *
     */
    public int compare(Protein protein1, Protein protein2) {
        return super.compare(protein1, protein2);
    }

    @Override
    public UnambiguousInteractorComparator getInteractorComparator() {
        return (UnambiguousInteractorComparator) this.interactorComparator;
    }

    /**
     * Use UnambiguousProteinComparator to know if two proteins are equals.
     * @param protein1
     * @param protein2
     * @return true if the two proteins are equal
     */
    public static boolean areEquals(Protein protein1, Protein protein2){
        if (unambiguousProteinComparator == null){
            unambiguousProteinComparator = new UnambiguousProteinComparator();
        }

        return unambiguousProteinComparator.compare(protein1, protein2) == 0;
    }
}
