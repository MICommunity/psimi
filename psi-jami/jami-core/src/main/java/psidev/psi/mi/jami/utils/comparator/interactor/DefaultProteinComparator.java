package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Protein;

import java.util.Comparator;

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

public class DefaultProteinComparator implements Comparator<Protein> {

    private static DefaultProteinComparator defaultProteinComparator;
    protected DefaultPolymerComparator interactorComparator;

    /**
     * Creates a new DefaultProteinComparator. It will uses a DefaultPolymerComparator to compare interactor properties and a
     * OrganismTaxIdComparator to compares organism.
     */
    public DefaultProteinComparator(){
        this.interactorComparator = new DefaultPolymerComparator();
    }

    protected DefaultProteinComparator(DefaultPolymerComparator polymerBaseComparator){
        this.interactorComparator = polymerBaseComparator != null ? polymerBaseComparator : new DefaultPolymerComparator();
    }

    /**
     * It will first use DefaultPolymerComparator to compare the basic interactor properties
     * If the basic interactor properties are the same, It will look for uniprotkb identifier if both are set. If the uniprotkb identifiers are not both set or are identical, it will look at the
     * Refseq identifiers. If at least one Refseq/uniprot identifiers is not set, it will look at the rogids. If at least one rogid is not set or both are identical, it will look at the gene names.
     *
     */
    public int compare(Protein protein1, Protein protein2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (protein1 == null && protein2 == null){
            return EQUAL;
        }
        else if (protein1 == null){
            return AFTER;
        }
        else if (protein2 == null){
            return BEFORE;
        }
        else {

            // First compares the basic interactor properties
            int comp = interactorComparator.compare(protein1, protein2);
            if (comp != 0){
                return comp;
            }

            // then compares uniprot identifiers
            String uniprot1 = protein1.getUniprotkb();
            String uniprot2 = protein2.getUniprotkb();

            if (uniprot1 != null && uniprot2 != null){
                comp = uniprot1.compareTo(uniprot2);
                if (comp != 0){
                   return comp;
                }
            }

            // compares Refseq
            String refseq1 = protein1.getRefseq();
            String refseq2 = protein2.getRefseq();

            if (refseq1 != null && refseq2 != null){
                comp = refseq1.compareTo(refseq2);
                if (comp != 0){
                    return comp;
                }
            }

            // compares rogids if at least one refseq identifier is not set
            String rogid1 = protein1.getRogid();
            String rogid2 = protein2.getRogid();

            if (rogid1 != null && rogid2 != null){
                comp = rogid1.compareTo(rogid2);
                if (comp != 0){
                   return comp;
                }
            }

            // compares gene names if at least one rogid identifier is not set
            String gene1 = protein1.getGeneName();
            String gene2 = protein2.getGeneName();

            if (gene1 != null && gene2 != null){
                return gene1.compareTo(gene2);
            }

            return comp;
        }
    }

    public DefaultPolymerComparator getInteractorComparator() {
        return (DefaultPolymerComparator) this.interactorComparator;
    }

    /**
     * Use DefaultProteinComparator to know if two proteins are equals.
     * @param protein1
     * @param protein2
     * @return true if the two proteins are equal
     */
    public static boolean areEquals(Protein protein1, Protein protein2){
        if (defaultProteinComparator == null){
            defaultProteinComparator = new DefaultProteinComparator();
        }

        return defaultProteinComparator.compare(protein1, protein2) == 0;
    }
}
