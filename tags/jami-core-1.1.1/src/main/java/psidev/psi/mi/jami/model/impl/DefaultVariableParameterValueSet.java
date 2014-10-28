package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.VariableParameterValue;
import psidev.psi.mi.jami.model.VariableParameterValueSet;
import psidev.psi.mi.jami.utils.comparator.experiment.VariableParameterValueSetComparator;

import java.util.*;

/**
 * Default implementation for VariableParameterValueSet
 *
 * Notes: The equals and hashcode methods have been overridden to be consistent with VariableParameterValueSetComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class DefaultVariableParameterValueSet implements VariableParameterValueSet{

    private Set<VariableParameterValue> variableParameterValues;

    public DefaultVariableParameterValueSet(){
        initialiseVatiableParameterValuesSet();
    }

    protected void initialiseVatiableParameterValuesSet(){
        this.variableParameterValues = new HashSet<VariableParameterValue>();
    }

    protected void initialiseVatiableParameterValuesSetWith(Set<VariableParameterValue> paramValues){
        if (paramValues == null){
            this.variableParameterValues = Collections.EMPTY_SET;
        }
        else {
            this.variableParameterValues = paramValues;
        }
    }

    public int size() {
        return variableParameterValues.size();
    }

    public boolean isEmpty() {
        return variableParameterValues.isEmpty();
    }

    public boolean contains(Object o) {
        return variableParameterValues.contains(o);
    }

    public Iterator<VariableParameterValue> iterator() {
        return variableParameterValues.iterator();
    }

    public Object[] toArray() {
        return variableParameterValues.toArray();
    }

    public <T> T[] toArray(T[] ts) {
        return variableParameterValues.toArray(ts);
    }

    public boolean add(VariableParameterValue variableParameterValue) {
        return variableParameterValues.add(variableParameterValue);
    }

    public boolean remove(Object o) {
        return variableParameterValues.remove(o);
    }

    public boolean containsAll(Collection<?> objects) {
        return variableParameterValues.containsAll(objects);
    }

    public boolean addAll(Collection<? extends VariableParameterValue> variableParameterValues) {
        return this.variableParameterValues.addAll(variableParameterValues);
    }

    public boolean retainAll(Collection<?> objects) {
        return variableParameterValues.retainAll(objects);
    }

    public boolean removeAll(Collection<?> objects) {
        return variableParameterValues.removeAll(objects);
    }

    public void clear() {
        variableParameterValues.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof VariableParameterValueSet)){
            return false;
        }

        return VariableParameterValueSetComparator.areEquals(this, (VariableParameterValueSet) o);
    }

    @Override
    public int hashCode() {
        return VariableParameterValueSetComparator.hashCode(this);
    }
}
