package psidev.psi.mi.tab.expansion;

import org.junit.Test;
import static org.junit.Assert.*;

import psidev.psi.mi.tab.TestHelper;
import psidev.psi.mi.xml.PsimiXmlReader;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.model.Entry;
import psidev.psi.mi.xml.model.EntrySet;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Participant;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * MatrixExpansion Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version 1.0
 * @since <pre>10/13/2006</pre>
 */
public class MatrixExpansionTest {

    @Test public void expand() throws JAXBException, IOException, ConverterException, PsimiXmlReaderException {

        File file = TestHelper.getFileByResources("/psi25-samples/single-nary-interaction.xml", SpokeExpansionTest.class);

        // read PSI 2.5
        PsimiXmlReader xmlReader = new PsimiXmlReader();
        EntrySet entrySet = null;
        entrySet = xmlReader.read( file );
        assertNotNull( entrySet );
        Entry entry = entrySet.getEntries().iterator().next();
        assertEquals( 1, entry.getInteractions().size() );
        Interaction interaction = entry.getInteractions().iterator().next();
        assertNotNull( interaction );

        // Apply matrix model
        ExpansionStrategy expander = new MatrixExpansion();
        Collection<Interaction> interactions = expander.expand( interaction );

        assertNotNull( interactions );
        assertEquals( 3, interactions.size() );
        // 3 is the bait, 5 and 8 the preys, we should find 3 interaction involving participant 3/5, 3/8 and 5/8.
        boolean case_3_5 = false;
        boolean case_3_8 = false;
        boolean case_5_8 = false;

        for ( Interaction i : interactions ) {

            assertEquals( "interaction doesn't have exactly 2 participants.", 2, i.getParticipants().size() );

            if ( hasParticipant( i, 3, 5 ) ) {
                // ok
                case_3_5 = true;
            } else if ( hasParticipant( i, 3, 8 ) ) {
                // ok
                case_3_8 = true;
            } else if ( hasParticipant( i, 5, 8 ) ) {
                // ok
                case_5_8 = true;
            } else {
                fail( "Could not find either participant 3/5 or 3/8 or 5/8." );
            }
        } // for

        assertTrue( "The interaction list didn't contain an interaction with participant 3 and 5.", case_3_5 );
        assertTrue( "The interaction list didn't contain an interaction with participant 3 and 8.", case_3_8 );
        assertTrue( "The interaction list didn't contain an interaction with participant 5 and 8.", case_5_8 );
    }

    private boolean hasParticipant( Interaction i, int p1id, int p2id ) {

        Iterator<Participant> iterator = i.getParticipants().iterator();
        Participant p1 = iterator.next();
        Participant p2 = iterator.next();

        if ( ( p1.getId() == p1id && p2.getId() == p2id )
             ||
             ( p2.getId() == p1id && p1.getId() == p2id ) ) {
            return true;
        }

        return false;
    }
}
