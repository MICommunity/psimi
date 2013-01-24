package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

/**
 * Unambiguous proteins comparator.
 * It will first use UnambiguousInteractorBaseComparator to compare the basic interactor properties
 * If the basic interactor properties are the same, It will look for uniprotkb identifier (The interactor with non null uniprot id will always come first).
 * If the uniprotkb identifiers are not set, it will look at the
 * Refseq identifiers (The interactor with non null refseq id will always come first). If the Refseq identifiers are not set,
 * it will look at the rogids (The interactor with non null rogid will always come first). If the rogids are not set,
 * it will look at the gene names (The interactor with non null gene name will always come first).
 * If the gene names are not set, it will look at sequence/organism (The interactor with non null sequence will always come first).
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class UnambiguousProteinComparator extends ProteinComparator {

    private static UnambiguousProteinComparator unambiguousProteinComparator;

    /**
     * Creates a new UnambiguousProteinComparator. It will uses a UnambiguousInteractorBaseComparator to compare interactor properties and a
     * OrganismTaxIdComparator to compares organism.
     */
    public UnambiguousProteinComparator(){
        super(new UnambiguousInteractorBaseComparator(), new OrganismTaxIdComparator());
    }

    @Override
    /**
     * It will first use UnambiguousInteractorBaseComparator to compare the basic interactor properties
     * If the basic interactor properties are the same, It will look for uniprotkb identifier (The interactor with non null uniprot id will always come first).
     * If the uniprotkb identifiers are not set, it will look at the
     * Refseq identifiers (The interactor with non null refseq id will always come first). If the Refseq identifiers are not set,
     * it will look at the rogids (The interactor with non null rogid will always come first). If the rogids are not set,
     * it will look at the gene names (The interactor with non null gene name will always come first).
     * If the gene names are not set, it will look at sequence/organism (The interactor with non null sequence will always come first).
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
                return uniprot1.compareTo(uniprot2);
            }
            else if (uniprot1 != null) {
                return BEFORE;
            }
            else if (uniprot2 != null) {
                return AFTER;
            }

            // compares Refseq
            String refseq1 = protein1.getUniprotkb();
            String refseq2 = protein2.getUniprotkb();

            if (refseq1 != null && refseq2 != null){
                return refseq1.compareTo(refseq2);
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
                return rogid1.compareTo(rogid2);
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

            // compares sequences if at least one gene name is not set
            String seq1 = protein1.getSequence();
            String seq2 = protein2.getSequence();

            if (seq1 != null && seq2 != null){
                comp = seq1.compareTo(seq2);
                // if sequences are equal, look at the organism before saying that the proteins are equals.
                if (comp == 0){
                    comp = organismComparator.compare(protein1.getOrganism(), protein2.getOrganism());
                }
            }
            else if (seq1 != null) {
                return BEFORE;
            }
            else if (seq2 != null) {
                return AFTER;
            }
            return comp;
        }
    }

    @Override
    public UnambiguousInteractorBaseComparator getInteractorComparator() {
        return (UnambiguousInteractorBaseComparator) this.interactorComparator;
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
