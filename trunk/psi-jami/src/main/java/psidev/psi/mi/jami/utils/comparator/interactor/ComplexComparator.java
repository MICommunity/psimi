package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.Component;
import psidev.psi.mi.jami.utils.comparator.participant.ComponentCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.participant.ComponentComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 * Basic ComplexComparator.
 *
 * It will first look at the default properties of an interactor using InteractorBaseComparator.
 * If the basic interactor properties are the same, It will first compare the collection of components using ComponentComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class ComplexComparator implements Comparator<Complex> {

    protected InteractorBaseComparator interactorComparator;
    protected ComponentCollectionComparator componentCollectionComparator;

    /**
     * Creates a bew ComplexComparator. It needs a InteractorBaseComparator to compares interactor properties
     * @param interactorComparator : comparator for interactor properties. It is required
     */
    public ComplexComparator(InteractorBaseComparator interactorComparator, ComponentComparator componentComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The interactor comparator is required to compare complexes. It cannot be null");
        }
        this.interactorComparator = interactorComparator;

        if (componentComparator == null){
            throw new IllegalArgumentException("The Component comparator is required to compare components composing the complexes. It cannot be null");
        }
        this.componentCollectionComparator = new ComponentCollectionComparator(componentComparator);
    }

    /**
     * It will first look at the default properties of an interactor using InteractorBaseComparator.
     * If the basic interactor properties are the same, It will first compare the collection of components using ComponentComparator.
     *
     * @param complex1
     * @param complex2
     * @return
     */
    public int compare(Complex complex1, Complex complex2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (complex1 == null && complex2 == null){
            return EQUAL;
        }
        else if (complex1 == null){
            return AFTER;
        }
        else if (complex2 == null){
            return BEFORE;
        }
        else {
            // compares the basic interactor properties first
            int comp = interactorComparator.compare(complex1, complex2);
            if (comp != 0){
                return comp;
            }

            // then compares collection of components
            Collection<Component> components1 = complex1.getComponents();
            Collection<Component> components2 = complex2.getComponents();

            return componentCollectionComparator.compare(components1, components2);
        }
    }

    public InteractorBaseComparator getInteractorComparator() {
        return interactorComparator;
    }

    public ComponentCollectionComparator getComponentCollectionComparator() {
        return componentCollectionComparator;
    }
}
