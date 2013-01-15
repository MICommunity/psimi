package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Protein;

/**
 * Exact proteins comparator.
 * It will look first for uniprotkb identifier if both are set. If the uniprotkb identifiers are not both set, it will look at the
 * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the rogids. If at least one rogid is not set, it will look at the gene names.
 * If at least one gene name is not set, it will look at sequence/organism.
 * If the properties of a protein were not enough to compare the proteins, it will use ExactInteractorComparator to compare the interactor properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class ExactProteinComparator extends ProteinComparator {

    /**
     * Creates a new ExactProteinComparator. It needs an ExactInteractorComparator to compare interactor properties.
     * @param interactorComparator : ExactInteractorComparator to compare interactor properties
     */
    public ExactProteinComparator(ExactInteractorComparator interactorComparator) {
        super(interactorComparator);
    }

    @Override
    /**
     * It will look first for uniprotkb identifier if both are set. If the uniprotkb identifiers are not both set, it will look at the
     * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the rogids. If at least one rogid is not set, it will look at the gene names.
     * If at least one gene name is not set, it will look at sequence/organism.
     * If the properties of a protein were not enough to compare the proteins, it will use ExactInteractorComparator to compare the interactor properties
     *
     */
    public int compare(Protein protein1, Protein protein2) {
        return super.compare(protein1, protein2);
    }

    @Override
    public ExactInteractorComparator getInteractorComparator() {
        return (ExactInteractorComparator) this.interactorComparator;
    }
}
