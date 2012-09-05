package psidev.psi.mi.tab.converter.xml2tab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.tab.TestHelper;
import psidev.psi.mi.tab.expansion.SpokeWithoutBaitExpansion;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.CrossReferenceImpl;
import psidev.psi.mi.xml.PsimiXmlReader;
import psidev.psi.mi.xml.model.EntrySet;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Xml2Tab Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>10/03/2006</pre>
 */

public class Xml2TabTest {

    public static final Log log = LogFactory.getLog(Xml2TabTest.class);

    @Test
    public void convert_noInteractionAvailable() throws Exception {

        File file = TestHelper.getFileByResources("/psi25-samples/8805587.xml", Xml2TabTest.class);

        PsimiXmlReader reader = new PsimiXmlReader();

        EntrySet entrySet = reader.read(file);
        Xml2Tab x2t = new Xml2Tab();
        try {
            Collection<BinaryInteraction> binaryInteractions = x2t.convert(entrySet);
            assertTrue(binaryInteractions.isEmpty());
        } catch (TabConversionException e) {
            fail();
        }
    }

    @Test
    public void convert2() throws Exception {

        File file = TestHelper.getFileByResources("/psi25-samples/11585365.xml", Xml2TabTest.class);
        PsimiXmlReader reader = new PsimiXmlReader();

        EntrySet entrySet = reader.read(file);
        Xml2Tab x2t = new Xml2Tab();
        try {
            Collection<BinaryInteraction> binaryInteractions = x2t.convert(entrySet);
            assertFalse(binaryInteractions.isEmpty());
            assertEquals(8, binaryInteractions.size());

        } catch (TabConversionException e) {
            fail();
        }
    }

    @Test
    public void convertToMitab27() throws Exception {

        File file = TestHelper.getFileByResources("/psi25-samples/19696444.xml", Xml2TabTest.class);
        PsimiXmlReader reader = new PsimiXmlReader();

        EntrySet entrySet = reader.read(file);
        Xml2Tab x2t = new Xml2Tab();
        try {
            Collection<BinaryInteraction> binaryInteractions = x2t.convert(entrySet);
            assertFalse(binaryInteractions.isEmpty());
            assertEquals(7, binaryInteractions.size());

        } catch (TabConversionException e) {
            fail();
        }


    }


    @Test
    public void convertToMitab27WithExpansion() throws Exception {

        File file = TestHelper.getFileByResources("/psi25-samples/19696444.xml", Xml2TabTest.class);
        PsimiXmlReader reader = new PsimiXmlReader();

        EntrySet entrySet = reader.read(file);
        Xml2Tab x2t = new Xml2Tab();
        x2t.setExpansionStrategy(new SpokeWithoutBaitExpansion());

        try {
            Collection<BinaryInteraction> binaryInteractions = x2t.convert(entrySet);
            assertFalse(binaryInteractions.isEmpty());
            assertEquals(12, binaryInteractions.size());

        } catch (TabConversionException e) {
            fail();
        }


    }

    @Test
    public void interactionAc() throws Exception {

        PsimiXmlReader reader = new PsimiXmlReader();
        Collection<BinaryInteraction> binaryInteractions = new ArrayList<BinaryInteraction>();
        Xml2Tab x2t = new Xml2Tab();

        x2t.setExpansionStrategy(new SpokeWithoutBaitExpansion());
        x2t.addOverrideSourceDatabase(new CrossReferenceImpl("MI", "0469", "intact"));


        // read original PSI-MI XML 2.5 File and get the originalEntrySet
        File chen1999 = TestHelper.getFileByResources("/psi25-testset/10551859.xml", Xml2TabTest.class);
        assertTrue(chen1999.canRead());

        EntrySet chen1999EntrySet = reader.read(chen1999);
        assertNotNull(chen1999EntrySet);

        Assert.assertEquals(2, chen1999EntrySet.getEntries().iterator().next().getInteractions().size());

        binaryInteractions.addAll(x2t.convert(chen1999EntrySet));

        File pellegrini2002 = TestHelper.getFileByResources("/psi25-testset/12442171.xml", Xml2TabTest.class);
        assertTrue(pellegrini2002.canRead());

        EntrySet pellegrini2002EntrySet = reader.read(pellegrini2002);
        assertNotNull(pellegrini2002EntrySet);

        Assert.assertEquals(3, pellegrini2002EntrySet.getEntries().iterator().next().getInteractions().size());

        binaryInteractions.addAll(x2t.convert(pellegrini2002EntrySet));

        File esashi2005 = TestHelper.getFileByResources("/psi25-testset/15800615.xml", Xml2TabTest.class);
        assertTrue(esashi2005.canRead());

        EntrySet esashi2005EntrySet = reader.read(esashi2005);
        assertNotNull(esashi2005EntrySet);

        Assert.assertEquals(5, esashi2005EntrySet.getEntries().iterator().next().getInteractions().size());

        binaryInteractions.addAll(x2t.convert(esashi2005EntrySet));

        File chen1998 = TestHelper.getFileByResources("/psi25-testset/9560268.xml", Xml2TabTest.class);
        assertTrue(chen1998.canRead());

        EntrySet chen1998EntrySet = reader.read(chen1998);
        assertNotNull(chen1998EntrySet);

        Assert.assertEquals(2, chen1998EntrySet.getEntries().iterator().next().getInteractions().size());

        binaryInteractions.addAll(x2t.convert(chen1998EntrySet));

        Collection<BinaryInteraction> interactions = new ArrayList<BinaryInteraction>();
        for (BinaryInteraction<?> binaryInteraction : binaryInteractions) {
            boolean a = binaryInteraction.getInteractorA().getIdentifiers().iterator().next().getIdentifier().equals("P51587");
            boolean A = binaryInteraction.getInteractorA().getIdentifiers().iterator().next().getIdentifier().equals("Q06609");
            boolean b = binaryInteraction.getInteractorB().getIdentifiers().iterator().next().getIdentifier().equals("Q06609");
            boolean B = binaryInteraction.getInteractorB().getIdentifiers().iterator().next().getIdentifier().equals("P51587");

            if (A && B || a && b) {
                String pubid = binaryInteraction.getPublications().get(0).getIdentifier();
                if (pubid.equals("10551859")) {
                    assertEquals(2, binaryInteraction.getInteractionAcs().size());
                }
                if (pubid.equals("12442171")) {
                    assertEquals(1, binaryInteraction.getInteractionAcs().size());
                }
                if (pubid.equals("15800615")) {
                    assertEquals(5, binaryInteraction.getInteractionAcs().size());
                }
                if (pubid.equals("9560268")) {
                    assertEquals(2, binaryInteraction.getInteractionAcs().size());
                }

                interactions.add(binaryInteraction);
            }
        }

        assertEquals(4, interactions.size());
    }

    @Test
    public void negative_interactions() throws Exception {

        // negative interactions shouldn't be converted into MITAB 2.5
        File file = TestHelper.getFileByResources("/psi25-testset/simple-negative.xml", Xml2TabTest.class);
        PsimiXmlReader reader = new PsimiXmlReader();

        EntrySet entrySet = reader.read(file);
        Xml2Tab x2t = new Xml2Tab();
        try {
            Collection<BinaryInteraction> binaryInteractions = x2t.convert(entrySet);
            assertFalse(binaryInteractions.isEmpty());
            assertEquals(true, binaryInteractions.iterator().next().isNegativeInteraction());

        } catch (TabConversionException e) {
            fail();
        }

    }
}