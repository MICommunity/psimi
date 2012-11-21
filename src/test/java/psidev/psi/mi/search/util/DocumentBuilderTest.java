/**
 * 
 */
package psidev.psi.mi.search.util;


import junit.framework.Assert;
import org.apache.lucene.document.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.tab.model.BinaryInteraction;

/**
 * TODO comment this!
 * 
 * @author Nadin Neuhauser (nneuhaus@ebi.ac.uk)
 * @version $Id: DocumentBuilderTest.java 
 */
public class DocumentBuilderTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateDocumentFromPsimiTabLine() throws Exception{
		
		String psiMiTabLine = "uniprotkb:Q08641	uniprotkb:P26781	gene name:ABP140	gene name:RPS11A	locus name:YOR239W/YOR240W	gene name synonym:RPS18A|locus name:YDR025W|orf name:YD9813.03|gene name:RPS11B|gene name synonym:RPS18B|locus name:YBR048W|orf name:YBR0501	MI:0676(tap)	-	pubmed:16429126	taxid:4932(yeast)	taxid:4932(yeast)	MI:0218(physical interaction)	MI:0469(intact)	intact:EBI-785909	-	";
        DocumentBuilder builder = new DefaultDocumentBuilder();
        Document doc = builder.createDocumentFromPsimiTabLine(psiMiTabLine);

		Assert.assertEquals(39, doc.getFields().size());
	}

	@Test
	public void testCreateBinaryInteraction() throws Exception{
		String psiMiTabLine = "uniprotkb:Q08641	uniprotkb:P26781	gene name:ABP140	gene name:RPS11A	locus name:YOR239W/YOR240W	gene name synonym:RPS18A|locus name:YDR025W|orf name:YD9813.03|gene name:RPS11B|gene name synonym:RPS18B|locus name:YBR048W|orf name:YBR0501	MI:0676(tap)	-	pubmed:16429126	taxid:4932(yeast)	taxid:4932(yeast)	MI:0218(physical interaction)	MI:0469(intact)	intact:EBI-785909	-	";
        DocumentBuilder builder = new DefaultDocumentBuilder();
        Document doc = builder.createDocumentFromPsimiTabLine(psiMiTabLine);

		BinaryInteraction interaction = (BinaryInteraction) builder.createData(doc);
		Assert.assertNotNull(interaction);
		
	}

}
