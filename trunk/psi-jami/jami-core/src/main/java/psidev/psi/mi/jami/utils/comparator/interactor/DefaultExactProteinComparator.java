package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Protein;

/**
 * Default exact proteins comparator.
 * It will first use DefaultExactPolymerComparator to compare the basic interactor properties
 * If the basic interactor properties are the same, It will look for uniprotkb identifier if both are set. If the uniprotkb identifiers are not both set or are identical, it will look at the
 * Refseq identifiers. If at least one Refseq/uniprot identifiers is not set, it will look at the rogids. If at least one rogid is not set or both are identical, it will look at the gene names.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultExactProteinComparator extends DefaultProteinComparator {

    private static DefaultExactProteinComparator defaultExactProteinComparator;

    /**
     * Creates a new DefaultExactProteinComparator. It will uses a DefaultExactPolymerComparator to compare interactor properties and a
     * OrganismTaxIdComparator to compares organism.
     */
    public DefaultExactProteinComparator(){
        super(new DefaultExactPolymerComparator());
    }

    @Override
    /**
     * It will first use DefaultExactPolymerComparator to compare the basic interactor properties
     * If the basic interactor properties are the same, It will look for uniprotkb identifier if both are set. If the uniprotkb identifiers are not both set or are identical, it will look at the
     * Refseq identifiers. If at least one Refseq/uniprot identifiers is not set, it will look at the rogids. If at least one rogid is not set or both are identical, it will look at the gene names.
     *
     */
    public int compare(Protein protein1, Protein protein2) {
        return super.compare(protein1, protein2);
    }

    @Override
    public DefaultExactPolymerComparator getInteractorComparator() {
        return (DefaultExactPolymerComparator) this.interactorComparator;
    }

    /**
     * Use DefaultExactProteinComparator to know if two proteins are equals.
     * @param protein1
     * @param protein2
     * @return true if the two proteins are equal
     */
    public static boolean areEquals(Protein protein1, Protein protein2){
        if (defaultExactProteinComparator == null){
            defaultExactProteinComparator = new DefaultExactProteinComparator();
        }

        return defaultExactProteinComparator.compare(protein1, protein2) == 0;
    }
}
