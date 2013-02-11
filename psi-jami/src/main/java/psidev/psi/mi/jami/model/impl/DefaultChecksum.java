package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.checksum.UnambiguousChecksumComparator;

/**
 * Default implementation for Checksum
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/01/13</pre>
 */

public class DefaultChecksum implements Checksum {

    protected CvTerm method;
    protected String value;

    public DefaultChecksum(CvTerm method, String value){
        if (method == null){
            throw new IllegalArgumentException("The method is required and cannot be null");
        }
        this.method = method;
        if (value == null){
            throw new IllegalArgumentException("The checksum value is required and cannot be null");
        }
        this.value = value;
    }

    public CvTerm getMethod() {
        return this.method;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Checksum)){
            return false;
        }

        return UnambiguousChecksumComparator.areEquals(this, (Checksum) o);
    }

    @Override
    public int hashCode() {
        return UnambiguousChecksumComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return method.toString() + ": " + value;
    }
}
