package psidev.psi.mi.tab;

import org.junit.Test;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.builder.PsimiTabVersion;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * PsimiTabColumn Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version 1.0
 * @since <pre>01/30/2007</pre>
 */
public class RoundTripTest {

    @Test
    public void roundTripMitab25() throws Exception {

        PsimiTabReader mitabReader = new PsimiTabReader();
        Collection<BinaryInteraction> interactions = mitabReader.read(TestHelper.MITAB25_2LINES_HEADER);
        assertEquals(2, interactions.size());

        // convert it back to a String

        PsimiTabWriter mitabWriter = new PsimiTabWriter();
        StringWriter stringWriter = new StringWriter();

        BufferedWriter bw = new BufferedWriter(stringWriter);
        mitabWriter.writeMitabHeader(bw);
        mitabWriter.write(interactions, bw);

        assertNotNull(bw);

        assertEquals(TestHelper.MITAB25_2LINES_HEADER, stringWriter.getBuffer().toString());

        bw.close();
    }

    @Test
    public void roundTripMitab26() throws Exception {
        PsimiTabReader mitabReader = new PsimiTabReader();
        Collection<BinaryInteraction> interactions = mitabReader.read(TestHelper.MITAB26_LINE_HEADER);
        assertEquals(1, interactions.size());

        // convert it back to a String

        PsimiTabWriter mitabWriter = new PsimiTabWriter(PsimiTabVersion.v2_6);
        StringWriter stringWriter = new StringWriter();

        BufferedWriter bw = new BufferedWriter(stringWriter);
        mitabWriter.writeMitabHeader(bw);
        mitabWriter.write(interactions, bw);

        assertNotNull(bw);

        assertEquals(TestHelper.MITAB26_LINE_HEADER, stringWriter.getBuffer().toString());

        bw.close();
    }

    @Test
    public void roundTripMitab27() throws Exception {
        PsimiTabReader mitabReader = new PsimiTabReader();
        Collection<BinaryInteraction> interactions = mitabReader.read(TestHelper.MITAB27_LINE_HEADER);
        assertEquals(1, interactions.size());

        // convert it back to a String

        PsimiTabWriter mitabWriter = new PsimiTabWriter(PsimiTabVersion.v2_7);
        StringWriter stringWriter = new StringWriter();

        BufferedWriter bw = new BufferedWriter(stringWriter);
        mitabWriter.writeMitabHeader(bw);
        mitabWriter.write(interactions, bw);

        assertNotNull(bw);

        assertEquals(TestHelper.MITAB27_LINE_HEADER, stringWriter.getBuffer().toString());

        bw.close();
    }

    @Test
    public void roundTripMitab28() throws Exception {
        PsimiTabReader mitabReader = new PsimiTabReader();
        Collection<BinaryInteraction> interactions = mitabReader.read(TestHelper.MITAB28_LINE_HEADER);
        assertEquals(1, interactions.size());

        // convert it back to a String

        PsimiTabWriter mitabWriter = new PsimiTabWriter(PsimiTabVersion.v2_8);
        StringWriter stringWriter = new StringWriter();

        BufferedWriter bw = new BufferedWriter(stringWriter);
        mitabWriter.writeMitabHeader(bw);
        mitabWriter.write(interactions, bw);

        assertNotNull(bw);

        assertEquals(TestHelper.MITAB28_LINE_HEADER, stringWriter.getBuffer().toString());

        bw.close();
    }
}
