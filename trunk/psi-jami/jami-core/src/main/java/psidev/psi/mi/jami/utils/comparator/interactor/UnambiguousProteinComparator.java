package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Protein;

import java.util.Comparator;

/**
 * Unambiguous proteins comparator.
 * It will first use UnambiguousPolymerComparator to compare the basic interactor properties
 * If the basic interactor properties are the same, It will look for uniprotkb identifier (The interactor with non null uniprot id will always come first).
 * If the uniprotkb identifiers are identical, it will look at the
 * Refseq identifiers (The interactor with non null refseq id will always come first). If the Refseq and uniport identifiers are not set,
 * it will look at the rogids (The interactor with non null rogid will always come first). If the rogids are identical,
 * it will look at the gene names (The interactor with non null gene name will always come first).
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class UnambiguousProteinComparator implements Comparator<Protein> {

    private static UnambiguousProteinComparator unambiguousProteinComparator;
    protected UnambiguousPolymerComparator interactorComparator;

    /**
     * Creates a new UnambiguousProteinComparator. It will uses a UnambiguousPolymerComparator to compare interactor properties and a
     * OrganismTaxIdComparator to compares organism.
     */
    public UnambiguousProteinComparator(){
        this.interactorComparator = new UnambiguousPolymerComparator();
    }

    protected UnambiguousProteinComparator(UnambiguousPolymerComparator polymerBaseComparator){
        this.interactorComparator = polymerBaseComparator != null ? polymerBaseComparator : new UnambiguousPolymerComparator();
    }

    /**
     * It will first use UnambiguousPolymerComparator to compare the basic interactor properties
     * If the basic interactor properties are the same, It will look for uniprotkb identifier (The interactor with non null uniprot id will always come first).
     * If the uniprotkb identifiers are identical, it will look at the
     * Refseq identifiers (The interactor with non null refseq id will always come first). If the Refseq and uniport identifiers are not set,
     * it will look at the rogids (The interactor with non null rogid will always come first). If the rogids are identical,
     * it will look at the gene names (The interactor with non null gene name will always come first).
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
            else if (uniprot1 != null) {
                return BEFORE;
            }
            else if (uniprot2 != null) {
                return AFTER;
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
            else if (refseq1 != null) {
                return BEFORE;
            }
            else if (refseq2 != null) {
                return AFTER;
            }

            // compares rogids
            String rogid1 = protein1.getRogid();
            String rogid2 = protein2.getRogid();

            if (rogid1 != null && rogid2 != null){
                comp = rogid1.compareTo(rogid2);
                if (comp != 0){
                    return comp;
                }
            }
            else if (rogid1 != null) {
                return BEFORE;
            }
            else if (rogid2 != null) {
                return AFTER;
            }

            // compares gene names if at least one rogid identifier is not set
            String gene1 = protein1.getGeneName();
            String gene2 = protein2.getGeneName();

            if (gene1 != null && gene2 != null){
                return gene1.compareTo(gene2);
            }
            else if (gene1 != null) {
                return BEFORE;
            }
            else if (gene2 != null) {
                return AFTER;
            }

            return comp;
        }
    }

    public UnambiguousPolymerComparator getInteractorComparator() {
        return this.interactorComparator;
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
