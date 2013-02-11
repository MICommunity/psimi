package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.confidence.UnambiguousConfidenceComparator;

/**
 * Default implementation for Confidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/01/13</pre>
 */

public class DefaultConfidence implements Confidence {

    protected CvTerm type;
    protected String value;
    protected CvTerm unit;

    public DefaultConfidence(CvTerm type, String value){
        if (type == null){
            throw new IllegalArgumentException("The confidence type is required and cannot be null");
        }
        this.type = type;
        if (value == null){
            throw new IllegalArgumentException("The confidence value is required and cannot be null");
        }
        this.value = value;
    }

    public DefaultConfidence(CvTerm type, String value, CvTerm unit){
        this(type, value);
        this.unit = unit;
    }

    public CvTerm getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public CvTerm getUnit() {
        return this.unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Confidence)){
            return false;
        }

        return UnambiguousConfidenceComparator.areEquals(this, (Confidence) o);
    }

    @Override
    public String toString() {
        return type.toString() + ": " + value + (unit != null ? "("+unit.toString()+")" : "");
    }

    @Override
    public int hashCode() {
        return UnambiguousConfidenceComparator.hashCode(this);
    }
}
