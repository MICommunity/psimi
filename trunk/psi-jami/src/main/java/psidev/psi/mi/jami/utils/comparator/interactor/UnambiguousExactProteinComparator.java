package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

/**
 * Unambiguous exact proteins comparator.
 * It will first use UnambiguousExactInteractorBaseComparator to compare the basic interactor properties
 * If the basic interactor properties are the same, It will look for uniprotkb identifier if both are set. If the uniprotkb identifiers are not both set, it will look at the
 * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the rogids. If at least one rogid is not set, it will look at the gene names.
 * If at least one gene name is not set, it will look at sequence/organism.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousExactProteinComparator extends ProteinComparator {

    private static UnambiguousExactProteinComparator unambiguousExactProteinComparator;

    /**
     * Creates a new DefaultExactProteinComparator. It will uses a DefaultExactInteractorBaseComparator to compare interactor properties and a
     * OrganismTaxIdComparator to compares organism.
     */
    public UnambiguousExactProteinComparator(){
        super(new UnambiguousExactInteractorBaseComparator(), new OrganismTaxIdComparator());
    }

    @Override
    /**
     * It will first use UnambiguousExactInteractorBaseComparator to compare the basic interactor properties
     * If the basic interactor properties are the same, It will look for uniprotkb identifier if both are set. If the uniprotkb identifiers are not both set, it will look at the
     * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the rogids. If at least one rogid is not set, it will look at the gene names.
     * If at least one gene name is not set, it will look at sequence/organism.
     */
    public int compare(Protein protein1, Protein protein2) {
        return super.compare(protein1, protein2);
    }

    @Override
    public UnambiguousExactInteractorBaseComparator getInteractorComparator() {
        return (UnambiguousExactInteractorBaseComparator) this.interactorComparator;
    }

    /**
     * Use DefaultExactProteinComparator to know if two proteins are equals.
     * @param protein1
     * @param protein2
     * @return true if the two proteins are equal
     */
    public static boolean areEquals(Protein protein1, Protein protein2){
        if (unambiguousExactProteinComparator == null){
            unambiguousExactProteinComparator = new UnambiguousExactProteinComparator();
        }

        return unambiguousExactProteinComparator.compare(protein1, protein2) == 0;
    }
}
