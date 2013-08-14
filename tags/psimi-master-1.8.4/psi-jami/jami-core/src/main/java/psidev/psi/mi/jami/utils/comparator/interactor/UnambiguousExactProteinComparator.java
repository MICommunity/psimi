package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Protein;

/**
 * Unambiguous exact proteins comparator.
 * It will first use UnambiguousExactPolymerComparator to compare the basic interactor properties
 * If the basic interactor properties are the same, It will look for uniprotkb identifier (The interactor with non null uniprot id will always come first).
 * If the uniprotkb identifiers are identical, it will look at the
 * Refseq identifiers (The interactor with non null refseq id will always come first). If the Refseq and uniport identifiers are not set,
 * it will look at the rogids (The interactor with non null rogid will always come first). If the rogids are identical,
 * it will look at the gene names (The interactor with non null gene name will always come first).
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousExactProteinComparator extends UnambiguousProteinComparator {

    private static UnambiguousExactProteinComparator unambiguousExactProteinComparator;

    /**
     * Creates a new DefaultExactProteinComparator. It will uses a DefaultExactPolymerComparator to compare interactor properties and a
     * OrganismTaxIdComparator to compares organism.
     */
    public UnambiguousExactProteinComparator(){
        super(new UnambiguousExactPolymerComparator());
    }

    @Override
    /**
     * It will first use UnambiguousExactPolymerComparator to compare the basic interactor properties
     * If the basic interactor properties are the same, It will look for uniprotkb identifier (The interactor with non null uniprot id will always come first).
     * If the uniprotkb identifiers are identical, it will look at the
     * Refseq identifiers (The interactor with non null refseq id will always come first). If the Refseq and uniport identifiers are not set,
     * it will look at the rogids (The interactor with non null rogid will always come first). If the rogids are identical,
     * it will look at the gene names (The interactor with non null gene name will always come first).
     */
    public int compare(Protein protein1, Protein protein2) {
        return super.compare(protein1, protein2);
    }

    @Override
    public UnambiguousExactPolymerComparator getInteractorComparator() {
        return (UnambiguousExactPolymerComparator) this.interactorComparator;
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
