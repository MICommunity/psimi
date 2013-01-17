package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

import java.util.Comparator;

/**
 * Basic proteins comparator.
 * It will look first for uniprotkb identifier if both are set. If the uniprotkb identifiers are not both set, it will look at the
 * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the rogids. If at least one rogid is not set, it will look at the gene names.
 * If at least one gene name is not set, it will look at sequence/organism.
 * If the properties of a protein were not enough to compare the proteins, it will use InteractorBaseComparator to compare the interactor properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class ProteinComparator implements Comparator<Protein>{

    protected InteractorBaseComparator interactorComparator;
    protected OrganismTaxIdComparator organismComparator;

    /**
     * Creates a new ProteinComparator. It needs a InteractorBaseComparator to compares interactor properties and it will creates a new OrganismTaxIdComparator
     * @param interactorComparator : comparator for interactor properties. It is required
     */
    public ProteinComparator(InteractorBaseComparator interactorComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The interactor comparator is required to compare proteins. It cannot be null");
        }
        this.interactorComparator = interactorComparator;
        this.organismComparator = new OrganismTaxIdComparator();

    }

    /**
     * Creates a new ProteinComparator. It needs a InteractorBaseComparator to compares interactor properties and a OrganismComparator
     * to compare the sequence and organism. If the organism comparator is null,it will creates a new OrganismTaxIdComparator
     * @param interactorComparator : comparator for interactor properties. It is required
     * @param organismComparator : comparator for organism
     */
    public ProteinComparator(InteractorBaseComparator interactorComparator, OrganismTaxIdComparator organismComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The interactor comparator is required to compare proteins. It cannot be null");
        }
        this.interactorComparator = interactorComparator;
        if (organismComparator == null){
            this.organismComparator = new OrganismTaxIdComparator();
        }
        else {
            this.organismComparator = organismComparator;
        }
    }

    /**
     * It will look first for uniprotkb identifier if both are set. If the uniprotkb identifiers are not both set, it will look at the
     * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the rogids. If at least one rogid is not set, it will look at the gene names.
     * If at least one gene name is not set, it will look at sequence/organism.
     * If the properties of a protein were not enough to compare the proteins, it will use InteractorBaseComparator to compare the interactor properties
     *
     * @param protein1
     * @param protein2
     * @return
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
            // first compares uniprot identifiers
            String uniprot1 = protein1.getUniprotkb();
            String uniprot2 = protein2.getUniprotkb();

            if (uniprot1 != null && uniprot2 != null){
                return uniprot1.compareTo(uniprot2);
            }

            // compares Refseq if at least one uniprot identifier is not set
            String refseq1 = protein1.getUniprotkb();
            String refseq2 = protein2.getUniprotkb();

            if (refseq1 != null && refseq2 != null){
                return refseq1.compareTo(refseq2);
            }

            // compares rogids if at least one refseq identifier is not set
            String rogid1 = protein1.getRogid();
            String rogid2 = protein2.getRogid();

            if (refseq1 != null && refseq2 != null){
                return rogid1.compareTo(rogid2);
            }

            // compares gene names if at least one rogid identifier is not set
            String gene1 = protein1.getGeneName();
            String gene2 = protein2.getGeneName();

            if (gene1 != null && gene2 != null){
                return gene1.compareTo(gene2);
            }

            // compares sequences if at least one gene name is not set
            String seq1 = protein1.getSequence();
            String seq2 = protein2.getSequence();

            if (seq1 != null && seq2 != null){
                int comp = seq1.compareTo(seq2);
                // if sequences are equal, look at the organism before saying that the proteins are equals.
                if (comp == 0){
                    comp = organismComparator.compare(protein1.getOrganism(), protein2.getOrganism());
                }
                return comp;
            }

            // compares the interactor properties if the protein properties are not enough
            return interactorComparator.compare(protein1, protein2);
        }
    }

    public InteractorBaseComparator getInteractorComparator() {
        return interactorComparator;
    }
}
