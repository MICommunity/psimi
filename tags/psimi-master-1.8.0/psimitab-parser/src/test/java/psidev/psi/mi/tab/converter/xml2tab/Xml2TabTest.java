package psidev.psi.mi.tab.converter.xml2tab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.tab.TestHelper;
import psidev.psi.mi.tab.expansion.SpokeWithoutBaitExpansion;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.tab.model.CrossReferenceImpl;
import psidev.psi.mi.xml.PsimiXmlReader;
import psidev.psi.mi.xml.model.EntrySet;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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


		//12 interactions, but one is expanded (three participants)
		//EBI-297231 in 12442171

		assertEquals(13, binaryInteractions.size());


		Collection<BinaryInteraction> interactions10551859 = new ArrayList<BinaryInteraction>();
		Collection<BinaryInteraction> interactions12442171 = new ArrayList<BinaryInteraction>();
		Collection<BinaryInteraction> interactions15800615 = new ArrayList<BinaryInteraction>();
		Collection<BinaryInteraction> interactions9560268 = new ArrayList<BinaryInteraction>();

		for (BinaryInteraction<?> binaryInteraction : binaryInteractions) {
			String pubid = binaryInteraction.getPublications().get(0).getIdentifier();
			if (pubid.equals("10551859")) {
				interactions10551859.add(binaryInteraction);
			}
			if (pubid.equals("12442171")) {
				interactions12442171.add(binaryInteraction);
			}
			if (pubid.equals("15800615")) {
				interactions15800615.add(binaryInteraction);
			}
			if (pubid.equals("9560268")) {
				interactions9560268.add(binaryInteraction);
			}
		}

		assertEquals(2, interactions10551859.size());
		assertEquals(4, interactions12442171.size());
		assertEquals(5, interactions15800615.size());
		assertEquals(2, interactions9560268.size());

		Set<CrossReference> interactionAcSet10551859 = new HashSet<CrossReference>();
		Set<CrossReference> interactionAcSet12442171 = new HashSet<CrossReference>();
		Set<CrossReference> interactionAcSet15800615 = new HashSet<CrossReference>();
		Set<CrossReference> interactionAcSet9560268 = new HashSet<CrossReference>();

		for (BinaryInteraction<?> binaryInteraction : binaryInteractions) {
			String pubid = binaryInteraction.getPublications().get(0).getIdentifier();
			if (pubid.equals("10551859")) {
				interactionAcSet10551859.addAll(binaryInteraction.getInteractionAcs());
			}
			if (pubid.equals("12442171")) {
				interactionAcSet12442171.addAll(binaryInteraction.getInteractionAcs());
			}
			if (pubid.equals("15800615")) {
				interactionAcSet15800615.addAll(binaryInteraction.getInteractionAcs());
			}
			if (pubid.equals("9560268")) {
				interactionAcSet9560268.addAll(binaryInteraction.getInteractionAcs());
			}
		}

		assertEquals(2, interactionAcSet10551859.size());
		assertEquals(3, interactionAcSet12442171.size());
		assertEquals(5, interactionAcSet15800615.size());
		assertEquals(2, interactionAcSet9560268.size());

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