package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Gene;

/**
 * Unambiguous exact gene comparator.
 * It will first use UnambiguousExactInteractorBaseComparator to compare the basic interactor properties
 * If the basic interactor properties are the same, It will look at ensembl identifier if both are set. If the ensembl identifiers are not both set, it will look at the
 * ensemblGenome identifiers. If at least one ensemblGemome identifiers is not set, it will look at the entrez/gene id. If at least one entrez/gene id is not set, it will look at the refseq identifiers.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousExactGeneComparator extends GeneComparator {

    private static UnambiguousExactGeneComparator unambiguousExactGeneComparator;

    /**
     * Creates a new UnambiguousExactGeneComparator. It will uses a DefaultExatctInteractorBaseComparator to compare interactor properties
     */
    public UnambiguousExactGeneComparator(){
        super(new UnambiguousExactInteractorBaseComparator());
    }

    @Override
    /**
     * It will first use UnambiguousExactInteractorBaseComparator to compare the basic interactor properties
     * If the basic interactor properties are the same, It will look at ensembl identifier if both are set. If the ensembl identifiers are not both set, it will look at the
     * ensemblGenome identifiers. If at least one ensemblGemome identifiers is not set, it will look at the entrez/gene id. If at least one entrez/gene id is not set, it will look at the refseq identifiers.
     *
     */
    public int compare(Gene gene1, Gene gene2) {
        return super.compare(gene1, gene2);
    }

    @Override
    public UnambiguousExactInteractorBaseComparator getInteractorComparator() {
        return (UnambiguousExactInteractorBaseComparator) this.interactorComparator;
    }

    /**
     * Use DefaultGeneComparator to know if two genes are equals.
     * @param gene1
     * @param gene2
     * @return true if the two genes are equal
     */
    public static boolean areEquals(Gene gene1, Gene gene2){
        if (unambiguousExactGeneComparator == null){
            unambiguousExactGeneComparator = new UnambiguousExactGeneComparator();
        }

        return unambiguousExactGeneComparator.compare(gene1, gene2) == 0;
    }
}
