package psidev.psi.mi.jami.utils.comparator;

import java.util.*;

/**
 * A comparator for collections.
 *
 * Two collections are equals if they have the same content and the same size.
 * The smallest collection will come before the longest collection.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/12/12</pre>
 */

public class CollectionComparator<T> implements Comparator<Collection<? extends T>> {

    protected Comparator<T> objectComparator;

    /**
     * Creates a new CollectionComparator. It requires a Comparator for the obejcts in the Collection
     * @param objectComparator
     */
    public CollectionComparator(Comparator<T> objectComparator){
        if (objectComparator == null){
            throw new IllegalArgumentException("The Object comparator is required for comparing Collections of objects and it cannot be null");
        }
        this.objectComparator = objectComparator;
    }

    public Comparator<T> getObjectComparator() {
        return objectComparator;
    }

    /**
     * Two collections are equals if they have the same content and the same size.
     * The smallest collection will come before the longest collection.
     * @param ts1
     * @param ts2
     * @return
     */
    public int compare(Collection<? extends T> ts1, Collection<? extends T> ts2) {

        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (ts1 == null && ts2 == null){
            return EQUAL;
        }
        else if (ts1 == null){
            return AFTER;
        }
        else if (ts2 == null){
            return BEFORE;
        }
        else {

            // compare collection size
            if (ts1.size() < ts2.size()){
                return BEFORE;
            }
            else if (ts1.size() > ts2.size()){
                return AFTER;
            }
            else {
                List<T> list1 = new ArrayList<T>(ts1);
                List<T> list2 = new ArrayList<T>(ts2);

                Collections.sort(list1, objectComparator);
                Collections.sort(list2, objectComparator);

                Iterator<T> iterator1 = list1.iterator();
                Iterator<T> iterator2 = list2.iterator();
                int comp2 = 0;
                while (comp2 == 0 && iterator1.hasNext() && iterator2.hasNext()){
                    comp2 = objectComparator.compare(iterator1.next(), iterator2.next());
                }

                if (comp2 != 0){
                    return comp2;
                }
                else if (iterator1.hasNext()){
                    return AFTER;
                }
                else if (iterator2.hasNext()){
                    return BEFORE;
                }
                else {
                    return comp2;
                }
            }
        }
    }
}
