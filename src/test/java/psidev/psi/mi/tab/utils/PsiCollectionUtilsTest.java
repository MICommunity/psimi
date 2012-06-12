/**
 * 
 */
package psidev.psi.mi.tab.utils;

import org.junit.Test;
import static org.junit.Assert.*;


import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author mmichaut
 * @version $Id$
 * @since 22 mai 07
 */
public class PsiCollectionUtilsTest {

	/**
	 * Test method for {@link psidev.psi.mi.tab.utils.PsiCollectionUtils#intersection(java.util.Collection, java.util.Collection)}.
	 */
    @Test
	public final void intersection() {
		Collection<String> colA = new ArrayList<String>();
		colA.add("A");
		colA.add("B");
		colA.add("C");
		colA.add("D");
		
		Collection<String> colB = new ArrayList<String>();
		colB.add("A");
		colB.add("C");
		colB.add("E");
		colB.add("F");
		
		Collection<String> psiInter = PsiCollectionUtils.intersection(colA, colB);
		Collection<String> commonInter = CollectionUtils.intersection(colA, colB);
		
		assertNotNull(psiInter);
		assertNotNull(commonInter);
		
		assertFalse(psiInter.isEmpty());
		assertFalse(commonInter.isEmpty());		

		assertEquals(psiInter.size(), 2);
		assertEquals(commonInter.size(), 2);
		
		assertTrue(psiInter.contains("A"));
		assertTrue(psiInter.contains("C"));
		assertTrue(commonInter.contains("A"));
		assertTrue(commonInter.contains("C"));

		for (String string : psiInter) {
			assertTrue(colA.contains(string));
			assertTrue(colB.contains(string));
		}
		
		for (String string : commonInter) {
			assertTrue(colA.contains(string));
			assertTrue(colB.contains(string));
		}
		
		assertEquals(psiInter, commonInter);
	}
}