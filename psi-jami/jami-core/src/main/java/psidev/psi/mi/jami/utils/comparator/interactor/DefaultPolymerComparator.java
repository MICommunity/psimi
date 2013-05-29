package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Polymer;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

import java.util.Comparator;

/**
 * Default polymer comparator.
 * It will first use DefaultInteractorBaseComparator to compare the basic interactor properties
 * If the basic interactor properties are the same, it will only look at sequence/organism(case insensitive) if both interactors have sequence.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public class DefaultPolymerComparator extends AbstractPolymerComparator {

    private static DefaultPolymerComparator defaultPolymerComparator;

    /**
     * Creates a new DefaultPolymerComparator. It will uses a DefaultInteractorBaseComparator to compare interactor properties and a
     * OrganismTaxIdComparator to compares organism.
     */
    public DefaultPolymerComparator(){
        super(new DefaultInteractorBaseComparator(), new OrganismTaxIdComparator());
    }

    protected DefaultPolymerComparator(Comparator<Interactor> interactorBaseComparator){
        super(interactorBaseComparator != null ? interactorBaseComparator : new DefaultInteractorBaseComparator(), new OrganismTaxIdComparator());
    }

    @Override
    /**
     * It will first use DefaultInteractorBaseComparator to compare the basic interactor properties
     * If the basic interactor properties are the same, it will only look at sequence/organism (case insensitive) if both interactors have sequence.
     */
    public int compare(Polymer polymer1, Polymer polymer2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (polymer1 == null && polymer2 == null){
            return EQUAL;
        }
        else if (polymer1 == null){
            return AFTER;
        }
        else if (polymer2 == null){
            return BEFORE;
        }
        else {

            // First compares the basic interactor properties
            int comp = interactorComparator.compare(polymer1, polymer2);
            if (comp != 0){
                return comp;
            }

            // compares sequences
            String seq1 = polymer1.getSequence();
            String seq2 = polymer2.getSequence();

            if (seq1 != null && seq2 != null){
                comp = seq1.toLowerCase().trim().compareTo(seq2.toLowerCase().trim());
                // if sequences are equal, look at the organism before saying that the polymers are equals.
                if (comp == 0){
                    comp = organismComparator.compare(polymer1.getOrganism(), polymer2.getOrganism());
                }
            }
            return comp;
        }
    }

    /**
     * Use DefaultPolymerComparator to know if two proteins are equals.
     * @param polymer1
     * @param polymer2
     * @return true if the two polymers are equal
     */
    public static boolean areEquals(Polymer polymer1, Polymer polymer2){
        if (defaultPolymerComparator == null){
            defaultPolymerComparator = new DefaultPolymerComparator();
        }

        return defaultPolymerComparator.compare(polymer1, polymer2) == 0;
    }
}
