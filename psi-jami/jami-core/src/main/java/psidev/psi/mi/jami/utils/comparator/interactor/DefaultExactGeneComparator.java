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

public class DefaultExactGeneComparator {

    /**
     * Use DefaultGeneComparator to know if two genes are equals.
     * @param gene1
     * @param gene2
     * @return true if the two genes are equal
     */
    public static boolean areEquals(Gene gene1, Gene gene2){
        if (gene1 == null && gene2 == null){
            return true;
        }
        else if (gene1 == null || gene2 == null){
            return false;
        }
        else {
            // First compares the interactor properties
            if (!DefaultExactInteractorBaseComparator.areEquals(gene1, gene2)){
                return false;
            }

            // first compares ensembl identifiers
            String ensembl1 = gene1.getEnsembl();
            String ensembl2 = gene2.getEnsembl();

            if (ensembl1 != null && ensembl2 != null){
                return ensembl1.equals(ensembl2);
            }

            // compares ensemblGenomes identifier if at least one ensembl identifier is not set
            String ensemblGenome1 = gene1.getEnsembleGenome();
            String ensemblGenome2 = gene2.getEnsembleGenome();

            if (ensemblGenome1 != null && ensemblGenome2 != null){
                return ensemblGenome1.equals(ensemblGenome2);
            }

            // compares entrez/gene Id if at least one ensemblGenomes identifier is not set
            String geneId1 = gene1.getEntrezGeneId();
            String geneId2 = gene2.getEntrezGeneId();

            if (geneId1 != null && geneId2 != null){
                return geneId1.equals(geneId2);
            }

            // compares refseq identifier if at least one reseq identifier is not set
            String refseq1 = gene1.getRefseq();
            String refseq2 = gene2.getRefseq();

            if (refseq1 != null && refseq2 != null){
                return refseq1.equals(refseq2);
            }

            return true;
        }
    }
}
