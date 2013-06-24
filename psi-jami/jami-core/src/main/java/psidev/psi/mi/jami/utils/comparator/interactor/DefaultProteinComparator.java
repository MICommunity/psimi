package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Protein;

/**
 * Default proteins comparator.
 * It will first use DefaultPolymerComparator to compare the basic interactor properties
 * If the basic interactor properties are the same, It will look for uniprotkb identifier if both are set. If the uniprotkb identifiers are not both set or are identical, it will look at the
 * Refseq identifiers. If at least one Refseq/uniprot identifiers is not set, it will look at the rogids. If at least one rogid is not set or both are identical, it will look at the gene names.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class DefaultProteinComparator {

    /**
     * Use DefaultProteinComparator to know if two proteins are equals.
     * @param protein1
     * @param protein2
     * @return true if the two proteins are equal
     */
    public static boolean areEquals(Protein protein1, Protein protein2){
        if (protein1 == null && protein2 == null){
            return true;
        }
        else if (protein1 == null || protein2 == null){
            return false;
        }
        else {

            // First compares the basic interactor properties
            if (!DefaultPolymerComparator.areEquals(protein1, protein2)){
                return false;
            }

            // then compares uniprot identifiers
            String uniprot1 = protein1.getUniprotkb();
            String uniprot2 = protein2.getUniprotkb();

            if (uniprot1 != null && uniprot2 != null){
                return uniprot1.equals(uniprot2);
            }

            // compares Refseq
            String refseq1 = protein1.getRefseq();
            String refseq2 = protein2.getRefseq();

            if (refseq1 != null && refseq2 != null){
                return refseq1.equals(refseq2);
            }

            // compares rogids if at least one refseq identifier is not set
            String rogid1 = protein1.getRogid();
            String rogid2 = protein2.getRogid();

            if (rogid1 != null && rogid2 != null){
                return rogid1.equals(rogid2);
            }

            // compares gene names if at least one rogid identifier is not set
            String gene1 = protein1.getGeneName();
            String gene2 = protein2.getGeneName();

            if (gene1 != null && gene2 != null){
                return gene1.equals(gene2);
            }

            return true;
        }
    }
}
