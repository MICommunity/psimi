package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Gene;

/**
 * Default gene comparator.
 * It will look first at ensembl identifier if both are set. If the ensembl identifiers are not both set, it will look at the
 * ensemblGenome identifiers. If at least one ensemblGemome identifiers is not set, it will look at the entrez/gene id. If at least one entrez/gene id is not set, it will look at the refseq identifiers.
 * If the properties of a gene were not enough to compare the proteins, it will use DefaultInteractorBaseComparator to compare the interactor properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class DefaultGeneComparator extends GeneComparator {

    private static DefaultGeneComparator defaultGeneComparator;

    /**
     * Creates a new DefaultGeneComparator. It will uses a DefaultInteractorBaseComparator to compare interactor properties
     */
    public DefaultGeneComparator(){
        super(new DefaultInteractorBaseComparator());
    }

    @Override
    /**
     * It will look first at ensembl identifier if both are set. If the ensembl identifiers are not both set, it will look at the
     * ensemblGenome identifiers. If at least one ensemblGemome identifiers is not set, it will look at the entrez/gene id. If at least one entrez/gene id is not set, it will look at the refseq identifiers.
     * If the properties of a gene were not enough to compare the genes, it will use DefaultInteractorBaseComparator to compare the interactor properties
     *
     */
    public int compare(Gene gene1, Gene gene2) {
        return super.compare(gene1, gene2);
    }

    @Override
    public DefaultInteractorBaseComparator getInteractorComparator() {
        return (DefaultInteractorBaseComparator) this.interactorComparator;
    }

    /**
     * Use DefaultGeneComparator to know if two genes are equals.
     * @param gene1
     * @param gene2
     * @return true if the two genes are equal
     */
    public static boolean areEquals(Gene gene1, Gene gene2){
        if (defaultGeneComparator == null){
            defaultGeneComparator = new DefaultGeneComparator();
        }

        return defaultGeneComparator.compare(gene1, gene2) == 0;
    }
}
