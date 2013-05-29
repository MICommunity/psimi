package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Gene;

/**
 * Default exact gene comparator.
 * It will first use DefaultExactInteractorBaseComparator to compare the basic interactor properties
 * If the basic interactor properties are the same, It will look at ensembl identifier if both are set. If the ensembl identifiers are not both set or are identical, it will look at the
 * ensemblGenome identifiers. If at least one ensemblGemome identifiers is not set or both are identical, it will look at the entrez/gene id. If at least one entrez/gene id is not set or both are identical, it will look at the refseq identifiers.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultExactGeneComparator extends DefaultGeneComparator {

    private static DefaultExactGeneComparator defaultExactGeneComparator;

    /**
     * Creates a new DefaultExactGeneComparator. It will uses a DefaultExactInteractorBaseComparator to compare interactor properties
     */
    public DefaultExactGeneComparator(){
        super(new DefaultExactInteractorBaseComparator());
    }

    @Override
    /**
     * It will first use DefaultExactInteractorBaseComparator to compare the basic interactor properties
     * If the basic interactor properties are the same, It will look at ensembl identifier if both are set. If the ensembl identifiers are not both set or are identical, it will look at the
     * ensemblGenome identifiers. If at least one ensemblGemome identifiers is not set or both are identical, it will look at the entrez/gene id. If at least one entrez/gene id is not set or both are identical, it will look at the refseq identifiers.
     *
     */
    public int compare(Gene gene1, Gene gene2) {
        return super.compare(gene1, gene2);
    }

    @Override
    public DefaultExactInteractorBaseComparator getInteractorComparator() {
        return (DefaultExactInteractorBaseComparator) this.interactorComparator;
    }

    /**
     * Use DefaultGeneComparator to know if two genes are equals.
     * @param gene1
     * @param gene2
     * @return true if the two genes are equal
     */
    public static boolean areEquals(Gene gene1, Gene gene2){
        if (defaultExactGeneComparator == null){
            defaultExactGeneComparator = new DefaultExactGeneComparator();
        }

        return defaultExactGeneComparator.compare(gene1, gene2) == 0;
    }
}
