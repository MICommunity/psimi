package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

/**
 * Default Interactor base comparator.
 * It will first compare the interactor types using DefaultCvTermComparator. If both types are equal,
 * it will compare organisms using OrganismTaxIdComparator. If both organisms are equal, it will compare Checksums.
 * If at least one checksum is identical, it will use a DefaultInteractorBaseComparator to compare basic Interactor properties.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultExactInteractorBaseComparator extends ExactInteractorBaseComparator {

    private static DefaultExactInteractorBaseComparator defaultExactInteractorComparator;

    /**
     * Creates a new DefaultExactInteractorBaseComparator.
     * It will use a DefaultInteractorBaseComparator to compare basic interactor properties, a OrganismTaxIdComparator to compare
     * organisms and a DefaultCvTermComparator to compare checksum types and interactor types
     */
    public DefaultExactInteractorBaseComparator() {
        super(new DefaultInteractorBaseComparator(), new OrganismTaxIdComparator(), new DefaultCvTermComparator());
    }

    @Override
    public DefaultInteractorBaseComparator getInteractorComparator() {
        return (DefaultInteractorBaseComparator) this.interactorComparator;
    }

    @Override
    public DefaultCvTermComparator getTypeComparator() {
        return (DefaultCvTermComparator) this.typeComparator;
    }

    @Override
    /**
     * It will first compare the interactor types using DefaultCvTermComparator. If both types are equal,
     * it will compare organisms using OrganismTaxIdComparator. If both organisms are equal, it will compare Checksums.
     * If at least one checksum is identical, it will use a DefaultInteractorBaseComparator to compare basic Interactor properties.
     */
    public int compare(Interactor interactor1, Interactor interactor2) {
        return super.compare(interactor1, interactor2);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * Use DefaulExacttInteractorBaseComparator to know if two interactors are equals.
     * @param interactor1
     * @param interactor2
     * @return true if the two interactors are equal
     */
    public static boolean areEquals(Interactor interactor1, Interactor interactor2){
        if (defaultExactInteractorComparator == null){
            defaultExactInteractorComparator = new DefaultExactInteractorBaseComparator();
        }

        return defaultExactInteractorComparator.compare(interactor1, interactor2) == 0;
    }
}
