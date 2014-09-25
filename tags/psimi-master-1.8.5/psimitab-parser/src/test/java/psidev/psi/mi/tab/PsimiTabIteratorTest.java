package psidev.psi.mi.tab;

import org.junit.Test;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.builder.MitabWriterUtils;
import psidev.psi.mi.tab.model.builder.PsimiTabVersion;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * PsimiTabIterator Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version 1.0
 * @since <pre>02/07/2007</pre>
 */
public class PsimiTabIteratorTest {

    @Test
    public void getInteractionsProcessedCount() throws Exception {

        String[] lines = {
                "uniprotkb:P23367	uniprotkb:P06722	interpro:IPR003594|interpro:IPR002099|go:\"GO:0005515\"|intact:EBI-554913	interpro:IPR004230|uniprotkb:Q9R2X2|uniprotkb:Q9R3A8|uniprotkb:Q9R411|uniprotkb:Q9S6P5|uniprotkb:Q9S6P6|uniprotkb:Q9S6P7|go:\"GO:0005515\"|intact:EBI-545170	gene name:mutL|locus name:b4170	gene name:mutH|gene name synonym:mutR|gene name synonym:prv|locus name:b2831	adenylate cyclase:\"MI:0014\"	-	pubmed:11585365	taxid:562	taxid:562	physical interaction:\"MI:0218\"	-	-	-\n",
                "uniprotkb:P23367	uniprotkb:P06722	-	-	-	-	-	-	-	taxid:562	taxid:562	-	-	-	-\n",
                "uniprotkb:P23909	uniprotkb:P23909	interpro:IPR005748|interpro:IPR000432|interpro:IPR007860|interpro:IPR007696|interpro:IPR007861|interpro:IPR007695|uniprotkb:P71279|go:\"GO:0005515\"|intact:EBI-554920	interpro:IPR005748|interpro:IPR000432|interpro:IPR007860|interpro:IPR007696|interpro:IPR007861|interpro:IPR007695|uniprotkb:P71279|go:\"GO:0005515\"|intact:EBI-554920	gene name:mutS|gene name synonym:fdv|locus name:b2733	gene name:mutS|gene name synonym:fdv|locus name:b2733	adenylate cyclase:\"MI:0014\"	-	pubmed:11585365	taxid:562	taxid:562	physical interaction:\"MI:0218\"	-	-	-\n",
                "uniprotkb:P23367	uniprotkb:P23367	interpro:IPR003594|interpro:IPR002099|go:\"GO:0005515\"|intact:EBI-554913	interpro:IPR003594|interpro:IPR002099|go:\"GO:0005515\"|intact:EBI-554913	gene name:mutL|locus name:b4170	gene name:mutL|locus name:b4170	adenylate cyclase:\"MI:0014\"	-	pubmed:11585365	taxid:562	taxid:562	physical interaction:\"MI:0218\"	-	-	-\n",
                "uniprotkb:P23367	uniprotkb:P09184	interpro:IPR003594|interpro:IPR002099|go:\"GO:0005515\"|intact:EBI-554913	interpro:IPR004603|intact:EBI-765033	gene name:mutL|locus name:b4170	gene name:vsr|locus name:b1960	two hybrid:\"MI:0018\"|adenylate cyclase:\"MI:0014\"	-	pubmed:11585365	taxid:562	taxid:562	physical interaction:\"MI:0218\"	-	-	-\n",
                "uniprotkb:P06722	uniprotkb:P23367	interpro:IPR004230|uniprotkb:Q9R2X2|uniprotkb:Q9R3A8|uniprotkb:Q9R411|uniprotkb:Q9S6P5|uniprotkb:Q9S6P6|uniprotkb:Q9S6P7|go:\"GO:0005515\"|intact:EBI-545170	interpro:IPR003594|interpro:IPR002099|go:\"GO:0005515\"|intact:EBI-554913	gene name:mutH|gene name synonym:mutR|gene name synonym:prv|locus name:b2831	gene name:mutL|locus name:b4170	two hybrid:\"MI:0018\"	-	pubmed:11585365	taxid:562	taxid:562	physical interaction:\"MI:0218\"	-	-	-\n"
        };


        psidev.psi.mi.tab.io.PsimiTabReader reader = new PsimiTabReader();
        Iterator<BinaryInteraction> iterator = reader.iterate(TestHelper.HEADER_TAB_11585365);
        int i = 0;
        while (iterator.hasNext()) {
            assertEquals(lines[i], MitabWriterUtils.buildLine(iterator.next(), PsimiTabVersion.v2_5));
            i++;
        }
        assertEquals(6, ((PsimiTabIterator) iterator).getInteractionsProcessedCount());
    }

    @Test
    public void next() throws Exception {
        psidev.psi.mi.tab.io.PsimiTabReader reader = new PsimiTabReader();
        Iterator<BinaryInteraction> iterator = reader.iterate(TestHelper.HEADER_TAB_11585365);

        // next() should keep returning the next element even if hasNext() hasn't been called.
        BinaryInteraction previous = null;
        int count = 0;
        while (count < 6) {
            // NOTE - we DO NOT call hasNext() on purpose here !!
            BinaryInteraction bi = iterator.next();
            if (previous != null) {
                assertFalse("Repeated call to next keep returning the same object", previous == bi);
                assertFalse("Repeated call to next keep returning similar object", bi.equals(previous));
            }
            previous = bi;
            count++;
        }
    }

    @Test
    public void hasNext() throws Exception {
        psidev.psi.mi.tab.io.PsimiTabReader reader = new PsimiTabReader();
        Iterator<BinaryInteraction> iterator = reader.iterate(TestHelper.HEADER_TAB_11585365);

        // hasNext should not be gready.
        for (int i = 0; i < 20; i++) {
            iterator.hasNext();
            assertEquals(0, ((PsimiTabIterator) iterator).getInteractionsProcessedCount());
        }

        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
            assertEquals(count, ((PsimiTabIterator) iterator).getInteractionsProcessedCount());
        }
    }

    @Test
    public void getInteractionsProcessedCountWithoutHeader() throws Exception {

        String[] lines = {
                "uniprotkb:P23367	uniprotkb:P06722	interpro:IPR003594|interpro:IPR002099|go:\"GO:0005515\"|intact:EBI-554913	interpro:IPR004230|uniprotkb:Q9R2X2|uniprotkb:Q9R3A8|uniprotkb:Q9R411|uniprotkb:Q9S6P5|uniprotkb:Q9S6P6|uniprotkb:Q9S6P7|go:\"GO:0005515\"|intact:EBI-545170	gene name:mutL|locus name:b4170	gene name:mutH|gene name synonym:mutR|gene name synonym:prv|locus name:b2831	adenylate cyclase:\"MI:0014\"	-	pubmed:11585365	taxid:562	taxid:562	physical interaction:\"MI:0218\"	-	-	-\n",
                "uniprotkb:P23367	uniprotkb:P06722	-	-	-	-	-	-	-	taxid:562	taxid:562	-	-	-	-\n",
                "uniprotkb:P23909	uniprotkb:P23909	interpro:IPR005748|interpro:IPR000432|interpro:IPR007860|interpro:IPR007696|interpro:IPR007861|interpro:IPR007695|uniprotkb:P71279|go:\"GO:0005515\"|intact:EBI-554920	interpro:IPR005748|interpro:IPR000432|interpro:IPR007860|interpro:IPR007696|interpro:IPR007861|interpro:IPR007695|uniprotkb:P71279|go:\"GO:0005515\"|intact:EBI-554920	gene name:mutS|gene name synonym:fdv|locus name:b2733	gene name:mutS|gene name synonym:fdv|locus name:b2733	adenylate cyclase:\"MI:0014\"	-	pubmed:11585365	taxid:562	taxid:562	physical interaction:\"MI:0218\"	-	-	-\n",
                "uniprotkb:P23367	uniprotkb:P23367	interpro:IPR003594|interpro:IPR002099|go:\"GO:0005515\"|intact:EBI-554913	interpro:IPR003594|interpro:IPR002099|go:\"GO:0005515\"|intact:EBI-554913	gene name:mutL|locus name:b4170	gene name:mutL|locus name:b4170	adenylate cyclase:\"MI:0014\"	-	pubmed:11585365	taxid:562	taxid:562	physical interaction:\"MI:0218\"	-	-	-\n",
                "uniprotkb:P23367	uniprotkb:P09184	interpro:IPR003594|interpro:IPR002099|go:\"GO:0005515\"|intact:EBI-554913	interpro:IPR004603|intact:EBI-765033	gene name:mutL|locus name:b4170	gene name:vsr|locus name:b1960	two hybrid:\"MI:0018\"|adenylate cyclase:\"MI:0014\"	-	pubmed:11585365	taxid:562	taxid:562	physical interaction:\"MI:0218\"	-	-	-\n",
                "uniprotkb:P06722	uniprotkb:P23367	interpro:IPR004230|uniprotkb:Q9R2X2|uniprotkb:Q9R3A8|uniprotkb:Q9R411|uniprotkb:Q9S6P5|uniprotkb:Q9S6P6|uniprotkb:Q9S6P7|go:\"GO:0005515\"|intact:EBI-545170	interpro:IPR003594|interpro:IPR002099|go:\"GO:0005515\"|intact:EBI-554913	gene name:mutH|gene name synonym:mutR|gene name synonym:prv|locus name:b2831	gene name:mutL|locus name:b4170	two hybrid:\"MI:0018\"	-	pubmed:11585365	taxid:562	taxid:562	physical interaction:\"MI:0218\"	-	-	-\n"
        };


        psidev.psi.mi.tab.io.PsimiTabReader reader = new PsimiTabReader();
        Iterator<BinaryInteraction> iterator = reader.iterate(TestHelper.NOHEADER_TAB_11585365);
        int i = 0;
        while (iterator.hasNext()) {
            assertEquals(lines[i], MitabWriterUtils.buildLine(iterator.next(), PsimiTabVersion.v2_5));
            i++;
        }
        assertEquals(6, ((PsimiTabIterator) iterator).getInteractionsProcessedCount());
    }

	@Test
	public void readEmptyFile() throws Exception {

		//read binary interactions
		psidev.psi.mi.tab.io.PsimiTabReader reader = new psidev.psi.mi.tab.PsimiTabReader();
		File file = TestHelper.getFileByResources("/mitab-samples/emptyFile.txt", PsimiTabIteratorTest.class);
		Collection<BinaryInteraction> interactions = reader.read(file);
		assertEquals(interactions.size(),0);


		// next() should keep returning the next element even if hasNext() hasn't been called.
		interactions.clear();
		Iterator<BinaryInteraction> iterator = reader.iterate(file);
		while (iterator.hasNext()) {
			interactions.add(iterator.next());
		}
		assertEquals(interactions.size(),0);

	}

	@Test
	public void readEmptyFileWithHeader() throws Exception {
		//read binary interactions
		psidev.psi.mi.tab.io.PsimiTabReader reader = new psidev.psi.mi.tab.PsimiTabReader();
		File file = TestHelper.getFileByResources("/mitab-samples/onlyHeader.txt", PsimiTabIteratorTest.class);
		Collection<BinaryInteraction> interactions = reader.read(file);
		assertEquals(interactions.size(),0);


		// next() should keep returning the next element even if hasNext() hasn't been called.
		interactions.clear();
		Iterator<BinaryInteraction> iterator = reader.iterate(file);
		while (iterator.hasNext()) {
			interactions.add(iterator.next());
		}
		assertEquals(interactions.size(),0);
	}
}
