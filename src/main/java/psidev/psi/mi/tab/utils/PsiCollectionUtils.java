/**
 * 
 */
package psidev.psi.mi.tab.utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author mmichaut
 * @version $Id$
 * @since 22 mai 07
 * 
 * Replace CollectionUtils implementation of intersection of 2 Collections;
 * The merging process of different mitab files is too long;
 * With this implementation (and other imrovments in equals or hashCode, it is faster);
 * .
 */
public class PsiCollectionUtils {
	
	/**
	 * New implementation of the intersection between 2 collections;
	 * The implementation of Jakarta-Commons CollectionUtils.intersection seems not to be optimzed;
	 * .
	 * @param a
	 * @param b
	 * @return
	 */
	public static Collection intersection(final Collection a, final Collection b) {
        Collection list = new ArrayList();
        Collection a1 = a;
        Collection b1 = b;
        
        if (a1.size()>b1.size()) {
        	a1 = b;
        	b1 = a;
        }
        
        for (Object object : a1) {
			if (b1.contains(object)) {
				list.add(object);
			}
		}
        
        return list;
    }

}
