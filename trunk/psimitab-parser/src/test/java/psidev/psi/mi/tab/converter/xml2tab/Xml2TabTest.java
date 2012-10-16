package psidev.psi.mi.tab.converter.xml2tab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.tab.TestHelper;
import psidev.psi.mi.tab.expansion.ExpansionStrategy;
import psidev.psi.mi.tab.expansion.MatrixExpansion;
import psidev.psi.mi.tab.expansion.SpokeExpansion;
import psidev.psi.mi.tab.expansion.SpokeWithoutBaitExpansion;
import psidev.psi.mi.tab.io.PsimiTabWriter;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.tab.model.CrossReferenceImpl;
import psidev.psi.mi.tab.model.Interactor;
import psidev.psi.mi.tab.model.builder.PsimiTabVersion;
import psidev.psi.mi.xml.PsimiXmlReader;
import psidev.psi.mi.xml.model.EntrySet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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

	private int lineCount(File file) throws IOException {

		BufferedReader in = new BufferedReader(new FileReader(file));
		String line;
		int count = 0;
		while ((line = in.readLine()) != null) {
			// process line here
			count++;
		}
		in.close();

		return count;
	}

	@Test
	public void convertHomoDimerInteraction() throws Exception {
		//interaction with out participants

		File file = TestHelper.getFileByResources("/psi25-samples/8805587.xml", Xml2TabTest.class);

		PsimiXmlReader reader = new PsimiXmlReader();

		EntrySet entrySet = reader.read(file);
		Xml2Tab x2t = new Xml2Tab();
		try {
			Collection<BinaryInteraction> binaryInteractions = x2t.convert(entrySet);
			assertEquals(1, binaryInteractions.size());
			BinaryInteraction binaryInteraction = binaryInteractions.iterator().next();

			assertEquals(false, isIntraMolecular(binaryInteraction));

			File tabFile = new File(TestHelper.getTargetDirectory(), "8805587.txt");

			assertTrue(tabFile.getParentFile().canWrite());

			PsimiTabWriter writer = new psidev.psi.mi.tab.PsimiTabWriter(PsimiTabVersion.v2_7);

			writer.write(binaryInteractions, tabFile);


			if(tabFile.exists()){
				tabFile.deleteOnExit();
			}

		} catch (TabConversionException e) {
			fail();
		}
	}

	private Boolean isIntraMolecular(BinaryInteraction<?> binaryInteraction) {
		Interactor A = binaryInteraction.getInteractorA();
		Interactor B = binaryInteraction.getInteractorB();

		if ((A == null && B != null && !B.isEmpty())) {
			//We have only one participant and we only check the first stoichiometry
			return true;

		} else if (B == null && A != null && !A.isEmpty()) {
			return true;
		}

		return false;
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
			//Without expasion
			List<BinaryInteraction> binaryInteractions = (List<BinaryInteraction>) x2t.convert(entrySet);
			assertFalse(binaryInteractions.isEmpty());
			assertEquals(10, binaryInteractions.size());

			File tabFile = new File(TestHelper.getTargetDirectory(), "19696444_without_expansion.txt");
			assertTrue(tabFile.getParentFile().canWrite());

			PsimiTabWriter writer = new psidev.psi.mi.tab.PsimiTabWriter(PsimiTabVersion.v2_7);

			writer.write(binaryInteractions, tabFile);

			assertEquals((Integer) 2 , binaryInteractions.get(6).getInteractorA().getStoichiometry().get(0));
			assertEquals((Integer) 0,binaryInteractions.get(6).getInteractorB().getStoichiometry().get(0));
			assertEquals((Integer) 3,binaryInteractions.get(7).getInteractorA().getStoichiometry().get(0));
			assertEquals((Integer) 0,binaryInteractions.get(7).getInteractorB().getStoichiometry().get(0));
			assertEquals((Integer) 2,binaryInteractions.get(8).getInteractorA().getStoichiometry().get(0));
			assertEquals((Integer) 0,binaryInteractions.get(8).getInteractorB().getStoichiometry().get(0));


			assertTrue(tabFile.exists());
			assertEquals(10, lineCount(tabFile));

			if (tabFile.exists()) {
				tabFile.deleteOnExit();
			}

		} catch (TabConversionException e) {
			fail();
		}


	}


	@Test
	public void convertToMitab27WithSpokeExpansion() throws Exception {

		File file = TestHelper.getFileByResources("/psi25-samples/19696444.xml", Xml2TabTest.class);
		PsimiXmlReader reader = new PsimiXmlReader();

		EntrySet entrySet = reader.read(file);
		Xml2Tab x2t = new Xml2Tab();
		ExpansionStrategy expansionStrategy = new SpokeExpansion();

		x2t.setExpansionStrategy(expansionStrategy);

		try {
			//With expansion
			Collection<BinaryInteraction> binaryInteractions = x2t.convert(entrySet);

			assertFalse(binaryInteractions.isEmpty());
			assertEquals(15, binaryInteractions.size());

			File tab2File = new File(TestHelper.getTargetDirectory(), "19696444_with_spoke_expansion.txt");


			assertTrue(tab2File.getParentFile().canWrite());

			PsimiTabWriter writer = new psidev.psi.mi.tab.PsimiTabWriter(PsimiTabVersion.v2_7);
			writer.write(binaryInteractions, tab2File);

			assertTrue(tab2File.exists());
			assertEquals(15, lineCount(tab2File));

			if (tab2File.exists()) {
				tab2File.deleteOnExit();
			}

		} catch (TabConversionException e) {
			fail();
		}
	}

	@Test
	public void convertToMitab27WithSpokeWithoutBaitExpansion() throws Exception {

		File file = TestHelper.getFileByResources("/psi25-samples/19696444.xml", Xml2TabTest.class);
		PsimiXmlReader reader = new PsimiXmlReader();

		EntrySet entrySet = reader.read(file);
		Xml2Tab x2t = new Xml2Tab();
		ExpansionStrategy expansionStrategy = new SpokeWithoutBaitExpansion();

		x2t.setExpansionStrategy(expansionStrategy);

		try {
			//With expansion
			Collection<BinaryInteraction> binaryInteractions = x2t.convert(entrySet);

			assertFalse(binaryInteractions.isEmpty());
			assertEquals(15, binaryInteractions.size());

			File tab2File = new File(TestHelper.getTargetDirectory(), "19696444_with_spoke_no_bait_expansion.txt");

			assertTrue(tab2File.getParentFile().canWrite());

			PsimiTabWriter writer = new psidev.psi.mi.tab.PsimiTabWriter(PsimiTabVersion.v2_7);
			writer.write(binaryInteractions, tab2File);

			assertTrue(tab2File.exists());
			assertEquals(15, lineCount(tab2File));

			if (tab2File.exists()) {
				tab2File.deleteOnExit();
			}
		} catch (TabConversionException e) {
			fail();
		}
	}

	@Test
	public void convertToMitab27WithMatrixExpansion() throws Exception {

		File file = TestHelper.getFileByResources("/psi25-samples/19696444.xml", Xml2TabTest.class);
		PsimiXmlReader reader = new PsimiXmlReader();

		EntrySet entrySet = reader.read(file);
		Xml2Tab x2t = new Xml2Tab();
		ExpansionStrategy expansionStrategy = new MatrixExpansion();

		x2t.setExpansionStrategy(expansionStrategy);

		try {
			//With expansion
			Collection<BinaryInteraction> binaryInteractions = x2t.convert(entrySet);

			assertFalse(binaryInteractions.isEmpty());
			assertEquals(19, binaryInteractions.size());

			File tab2File = new File(TestHelper.getTargetDirectory(), "19696444_with_matrix.txt");


			assertTrue(tab2File.getParentFile().canWrite());

			PsimiTabWriter writer = new psidev.psi.mi.tab.PsimiTabWriter(PsimiTabVersion.v2_7);
			writer.write(binaryInteractions, tab2File);

			assertTrue(tab2File.exists());
			assertEquals(19, lineCount(tab2File));

			if (tab2File.exists()) {
				tab2File.deleteOnExit();
			}

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
	public void negativeInteractions() throws Exception {

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

	@Test
	public void intraMolecularInteractions() throws Exception {

		// negative interactions shouldn't be converted into MITAB 2.5
		File file = TestHelper.getFileByResources("/psi25-testset/intraMolecular.xml", Xml2TabTest.class);
		PsimiXmlReader reader = new PsimiXmlReader();

		EntrySet entrySet = reader.read(file);
		Xml2Tab x2t = new Xml2Tab();
		try {
			Collection<BinaryInteraction> binaryInteractions = x2t.convert(entrySet);
			assertFalse(binaryInteractions.isEmpty());

			File tabFile = new File(TestHelper.getTargetDirectory(), "intraMolecular.txt");
			assertTrue(tabFile.getParentFile().canWrite());

			PsimiTabWriter writer = new psidev.psi.mi.tab.PsimiTabWriter(PsimiTabVersion.v2_7);

			writer.write(binaryInteractions, tabFile);

			if (tabFile.exists()) {
				tabFile.deleteOnExit();
			}

		} catch (TabConversionException e) {
			fail();
		}

	}
}