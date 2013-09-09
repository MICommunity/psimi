package psidev.psi.mi.jami.utils.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for lists
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/09/13</pre>
 */

public class ListUtils {

    // It chops a list into view sublists of length L using sublist
    // sublist returns a view of the portion of this list between the specified fromIndex,
    // inclusive, and toIndex, exclusive. (If fromIndex and toIndex are equal, the returned list is empty.)
    // The returned list is backed by this list, so non-structural changes in the returned list are reflected
    // in this list, and vice-versa. The returned list supports all of the optional
    // list operations supported by this list.
    // In this case as we are not going to modify the lists is more efficient to use sublist
    public static <T> List<List<T>> splitter(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(list.subList(i, Math.min(N, i + L))
            );
        }
        return parts;
    }
}
