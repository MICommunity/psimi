package psidev.psi.mi.tab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import psidev.psi.mi.tab.model.BinaryInteraction;

import java.io.StringWriter;
import java.util.Collection;

/**
 * PsimiTabColumn Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version 1.0
 * @since <pre>01/30/2007</pre>
 */
public class RoundTripTest {

    public static final String NEW_LINE = System.getProperty( "line.separator" );

    public static final String MITAB_2_LINE_WITH_HEADER =
            "#ID(s) interactor A\tID(s) interactor B\tAlt. ID(s) interactor A\tAlt. ID(s) interactor B\tAlias(es) interactor A\tAlias(es) interactor B\tInteraction detection method(s)\tPublication 1st author(s)\tPublication Identifier(s)\tTaxid interactor A\tTaxid interactor B\tInteraction type(s)\tSource database(s)\tInteraction identifier(s)\tConfidence value(s)" + NEW_LINE +
            "uniprotkb:Q8I0U6|intact:EBI-825868\tuniprotkb:Q8I0U6|intact:EBI-825868\tuniprotkb:PFA0110w(gene name)\tuniprotkb:PFA0110w(gene name)\tintact:MAL1P1.13\tintact:MAL1P1.13\tpsi-mi:\"MI:0398\"(two hybrid pooling)\tLacount et al. (2005)\tpubmed:16267556\ttaxid:36329(plaf7)\ttaxid:36329(plaf7)\tpsi-mi:\"MI:0218\"(physical interaction)\tpsi-mi:\"MI:0469\"(intact)\tintact:EBI-840450\t-" + NEW_LINE +
            "uniprotkb:P23367\tuniprotkb:P06722\t-\t-\t-\t-\t-\t-\t-\ttaxid:562\ttaxid:562\t-\t-\t-\t-" + NEW_LINE;


    @Test
    public void roundTrip() throws Exception {

        PsimiTabReader mitabReader = new psidev.psi.mi.tab.io.PsimiTabReader( );
        Collection<BinaryInteraction> interactions = mitabReader.read( MITAB_2_LINE_WITH_HEADER );
        assertEquals( 2, interactions.size() );

        // convert it back to a String

        PsimiTabWriter mitabWriter = new psidev.psi.mi.tab.io.PsimiTabWriter();
        StringWriter sw = new StringWriter();
        mitabWriter.writeMitabHeader(sw );
        mitabWriter.write( interactions, sw );
        assertNotNull( sw.getBuffer() );
        String output = sw.getBuffer().toString();
        
        assertEquals( MITAB_2_LINE_WITH_HEADER, output );
    }
}
