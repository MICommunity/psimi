package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Gene;

/**
 * Exact genes comparator.
 * It will look first at ensembl identifier if both are set. If the ensembl identifiers are not both set, it will look at the
 * ensemblGenome identifiers. If at least one ensemblGemome identifiers is not set, it will look at the entrez/gene id. If at least one entrez/gene id is not set, it will look at the refseq identifiers.
 * If the properties of a gene were not enough to compare the genes, it will use ExactInteractorBaseComparator to compare the interactor properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class ExactGeneComparator extends GeneComparator {

    /**
     * Creates a new ExactGeneComparator. It will uses an ExactInteractorBaseComparator to compares interactor properties
     * @param interactorComparator : the exactInteractorComparator to compare interactor properties
     */
    public ExactGeneComparator(ExactInteractorBaseComparator interactorComparator) {
        super(interactorComparator);
    }

    @Override
    /**
     * It will look first at ensembl identifier if both are set. If the ensembl identifiers are not both set, it will look at the
     * ensemblGenome identifiers. If at least one ensemblGemome identifiers is not set, it will look at the entrez/gene id. If at least one entrez/gene id is not set, it will look at the refseq identifiers.
     * If the properties of a gene were not enough to compare the genes, it will use ExactInteractorBaseComparator to compare the interactor properties
     *
     */
    public int compare(Gene gene1, Gene gene2) {
        return super.compare(gene1, gene2);
    }

    @Override
    public ExactInteractorBaseComparator getInteractorComparator() {
        return (ExactInteractorBaseComparator) this.interactorComparator;
    }
}
