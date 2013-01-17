package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.Component;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.utils.comparator.parameter.ParameterCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.parameter.ParameterComparator;
import psidev.psi.mi.jami.utils.comparator.participant.ComponentCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.participant.ComponentComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 * Basic ComplexComparator.
 *
 * It will first compare the collection of components using ComponentComparator.
 * If the collection of components is the same, it will look at the parameters using ParameterComparator.
 * If the parameters are the same and the collection of components was empty in both complexes, it will look at the default properties of an interactor
 * using InteractorBaseComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class ComplexComparator implements Comparator<Complex> {

    protected InteractorBaseComparator interactorComparator;
    protected ComponentCollectionComparator componentCollectionComparator;
    protected ParameterCollectionComparator parameterCollectionComparator;

    /**
     * Creates a bew ComplexComparator. It needs a InteractorBaseComparator to compares interactor properties
     * @param interactorComparator : comparator for interactor properties. It is required
     */
    public ComplexComparator(InteractorBaseComparator interactorComparator, ComponentComparator componentComparator, ParameterComparator parameterComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The interactor comparator is required to compare complexes. It cannot be null");
        }
        this.interactorComparator = interactorComparator;

        if (componentComparator == null){
            throw new IllegalArgumentException("The Component comparator is required to compare components composing the complexes. It cannot be null");
        }
        this.componentCollectionComparator = new ComponentCollectionComparator(componentComparator);

        if (parameterComparator == null){
            throw new IllegalArgumentException("The Parameter comparator is required to compare complex parameters. It cannot be null");
        }
        this.parameterCollectionComparator = new ParameterCollectionComparator(parameterComparator);
    }

    /**
     * It will first compare the collection of components using ComponentComparator.
     * If the collection of components is the same, it will look at the parameters using ParameterComparator.
     * If the parameters are the same and the collection of components was empty in both complexes, it will look at the default properties of an interactor
     * using InteractorBaseComparator.
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
            // first compares collection of components
            Collection<Component> components1 = complex1.getComponents();
            Collection<Component> components2 = complex2.getComponents();

            int comp = componentCollectionComparator.compare(components1, components2);
            if (comp != 0){
               return comp;
            }

            // then compares collection of parameters
            Collection<Parameter> parameters1 = complex1.getParameters();
            Collection<Parameter> parameters2 = complex2.getParameters();

            comp = parameterCollectionComparator.compare(parameters1, parameters2);
            if (comp != 0){
                return comp;
            }

            // only compares other participant properties if list of interactors is empty
            if (components1.isEmpty() && components2.isEmpty()){
                // compares the interactor properties if the complex properties are not enough
                return interactorComparator.compare(complex1, complex2);
            }
            else {
                return comp;
            }
        }
    }

    public InteractorBaseComparator getInteractorComparator() {
        return interactorComparator;
    }

    public ComponentCollectionComparator getComponentCollectionComparator() {
        return componentCollectionComparator;
    }

    public ParameterCollectionComparator getParameterCollectionComparator() {
        return parameterCollectionComparator;
    }
}
