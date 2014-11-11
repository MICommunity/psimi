package psidev.psi.mi.jami.utils.comparator;

import java.util.Comparator;

/**
 * A MI comparator is a comparator that can also generate a hashCode of a JAMI object that would be
 * consistent with the comparison results
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/08/14</pre>
 */

public interface MIComparator<T> extends Comparator<T>{

    public int computeHashCode(T object);
}
