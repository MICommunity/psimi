package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.utils.comparator.participant.StoichiometryComparator;

/**
 * Default implementation for stoichiometry
 *
 * Notes: The equals and hashcode methods have been overridden to be consistent with StoichiometryComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public class DefaultStoichiometry implements Stoichiometry{

    private int minValue;
    private int maxValue;

    public DefaultStoichiometry(int value){

        this.minValue = value;
        this.maxValue = value;
    }

    public DefaultStoichiometry(int minValue, int maxValue){
        if (minValue > maxValue){
           throw new IllegalArgumentException("The minValue " + minValue + " cannot be bigger than the maxValue " + maxValue);
        }

        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return this.minValue;
    }

    public int getMaxValue() {
        return this.maxValue;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o){
            return true;
        }

        if (!(o instanceof Stoichiometry)){
            return false;
        }

        return StoichiometryComparator.areEquals(this, (Stoichiometry) o);
    }

    @Override
    public int hashCode() {
        return StoichiometryComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return "minValue: " + minValue + ", maxValue: " + maxValue;
    }
}
