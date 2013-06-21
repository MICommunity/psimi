package psidev.psi.mi.jami.enricher.util;

import org.junit.Test;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultXrefComparator;

import java.util.ArrayList;
import java.util.Collection;
import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 28/05/13
 * Time: 10:27
 */
public class CollectionUtilsExtraTest {

    @Test
    public void test_that_only_xrefs_not_in_second_argument_are_returned(){

        Xref uniprotA = XrefUtils.createUniprotSecondary("uniprotA");
        Xref uniprotB = XrefUtils.createUniprotSecondary("uniprotB");
        Xref uniprotC = XrefUtils.createUniprotSecondary("uniprotC");
        Xref uniprotX = XrefUtils.createUniprotSecondary("uniprotX");

        ArrayList<Xref> completeXrefList = new ArrayList<Xref>();
        completeXrefList.add(uniprotA);
        completeXrefList.add(uniprotX);


        ArrayList<Xref> toRemoveXrefList = new ArrayList<Xref>();
        toRemoveXrefList.add(uniprotA);
        toRemoveXrefList.add(uniprotB);
        toRemoveXrefList.add(uniprotC);

        assertTrue(completeXrefList.size() == 2);
        assertTrue(toRemoveXrefList.size() == 3);

        Collection < Xref > subtractedXrefs = CollectionManipulationUtils.comparatorSubtract(
                completeXrefList,
                toRemoveXrefList,
                new DefaultXrefComparator());

        assertTrue(completeXrefList.size() == 2);
        assertTrue(toRemoveXrefList.size() == 3);
        assertTrue(subtractedXrefs.size() == 1);

    }

    //public static <Type> Collection<Type> comparatorSubtract(Collection<Type> a, Collection<Type> b, Comparator<Type> c)

}
