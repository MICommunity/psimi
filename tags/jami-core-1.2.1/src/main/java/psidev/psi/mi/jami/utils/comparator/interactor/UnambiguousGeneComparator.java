package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Gene;

/**
 * Unambiguous gene comparator.
 * It will first use UnambiguousInteractorBaseComparator to compare the basic interactor properties
 * If the basic interactor properties are the same, It will look at ensembl identifier (the interactor with non null ensembl identifier will always come first). If the ensembl identifiers are not set, it will look at the
 * ensemblGenome identifiers (the interactor with non null ensembl genome identifier will always come first). If the ensemblGemome identifiers are not set,
 * it will look at the entrez/gene id (the interactor with non null entrez/gene id will always come first).
 * If the entrez/gene ids are not set, it will look at the refseq identifiers (the interactor with non null refseq identifier will always come first).
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class UnambiguousGeneComparator extends GeneComparator {

    private static UnambiguousGeneComparator unambiguousGeneComparator;

    /**
     * Creates a new UnambiguousGeneComparator. It will uses a UnambiguousInteractorBaseComparator to compare interactor properties
     */
    public UnambiguousGeneComparator(){
        super(new UnambiguousInteractorBaseComparator());
    }

    public UnambiguousInteractorBaseComparator getInteractorComparator() {
        return (UnambiguousInteractorBaseComparator)super.getInteractorComparator();
    }

    /**
     * Use UnambiguousGeneComparator to know if two genes are equals.
     * @param gene1
     * @param gene2
     * @return true if the two genes are equal
     */
    public static boolean areEquals(Gene gene1, Gene gene2){
        if (unambiguousGeneComparator == null){
            unambiguousGeneComparator = new UnambiguousGeneComparator();
        }

        return unambiguousGeneComparator.compare(gene1, gene2) == 0;
    }
}
