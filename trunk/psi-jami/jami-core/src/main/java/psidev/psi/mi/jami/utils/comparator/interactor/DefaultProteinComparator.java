package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

/**
 * Default proteins comparator.
 * It will first use DefaultInteractorBaseComparator to compare the basic interactor properties
 * If the basic interactor properties are the same, It will look for uniprotkb identifier if both are set. If the uniprotkb identifiers are not both set, it will look at the
 * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the rogids. If at least one rogid is not set, it will look at the gene names.
 * If at least one gene name is not set, it will look at sequence/organism.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class DefaultProteinComparator extends ProteinComparator {

    private static DefaultProteinComparator defaultProteinComparator;

    /**
     * Creates a new DefaultProteinComparator. It will uses a DefaultInteractorBaseComparator to compare interactor properties and a
     * OrganismTaxIdComparator to compares organism.
     */
    public DefaultProteinComparator(){
        super(new DefaultInteractorBaseComparator(), new OrganismTaxIdComparator());
    }

    @Override
    /**
     * It will first use DefaultInteractorBaseComparator to compare the basic interactor properties
     * If the basic interactor properties are the same, It will look for uniprotkb identifier if both are set. If the uniprotkb identifiers are not both set, it will look at the
     * Refseq identifiers. If at least one Refseq identifiers is not set, it will look at the rogids. If at least one rogid is not set, it will look at the gene names.
     * If at least one gene name is not set, it will look at sequence/organism.
     */
    public int compare(Protein protein1, Protein protein2) {
        return super.compare(protein1, protein2);
    }

    @Override
    public DefaultInteractorBaseComparator getInteractorComparator() {
        return (DefaultInteractorBaseComparator) this.interactorComparator;
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
