/**
 *
 */
package psidev.psi.mi.tab.converter.tab2xml;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;
import psidev.psi.mi.tab.PsimiTabReader;
import psidev.psi.mi.tab.TestHelper;
import psidev.psi.mi.tab.converter.xml2tab.Xml2Tab;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.CrossReferenceImpl;
import psidev.psi.mi.xml.PsimiXmlReader;
import psidev.psi.mi.xml.PsimiXmlWriter;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.model.Entry;
import psidev.psi.mi.xml.model.EntrySet;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.model.Participant;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Tab2Xml Tester.
 *
 * @author Nadin Neuhauser (nneuhaus@ebi.ac.uk)
 * @version 1.0
 * @since <pre>23/08/2007</pre>
 */
public class Tab2XmlTest {

    @Test
    // This test runs with the old tabformat version
    public void writer() throws Exception {

        Collection<BinaryInteraction> binaryInteractions = null;
        try {
            File tabFile = TestHelper.getFileByResources( "/mitab-testset/chen.txt", Tab2XmlTest.class );
            assertTrue( tabFile.canRead() );

            PsimiTabReader reader = new PsimiTabReader( tabFile.canRead() );

            binaryInteractions = reader.read( tabFile );

        } catch ( IOException e ) {
            e.printStackTrace();
        } catch ( ConverterException e ) {
            e.printStackTrace();
        }


        EntrySet entrySet = null;
        try {
            Tab2Xml t2x = new Tab2Xml();
            t2x.setInteractorNameBuilder( new InteractorIdBuilder() );

            entrySet = t2x.convert( binaryInteractions );
            assertNotNull( entrySet );
        } catch ( XmlConversionException e ) {
            e.printStackTrace();
        } catch ( IllegalAccessException e ) {
            e.printStackTrace();
        }


        File xmlFile = new File( TestHelper.getTargetDirectory(), "chen.xml" );
        assertTrue( xmlFile.getParentFile().canWrite() );

        PsimiXmlWriter writer = new PsimiXmlWriter();
        writer.write( entrySet, xmlFile );

    }

    @Test
    // This test runs with the new tabformat version
    public void roundTripTest() throws Exception {

        // read original PSI-MI XML 2.5 File and get the originalEntrySet
        File originalXMLFile = TestHelper.getFileByResources( "/psi25-testset/9560268.xml", Tab2XmlTest.class );
        PsimiXmlReader reader = new PsimiXmlReader();
        EntrySet originalEntrySet = reader.read( originalXMLFile );
        assertNotNull( originalEntrySet );
        Assert.assertEquals( 2, originalEntrySet.getEntries().iterator().next().getInteractions().size());

        // convert the originalEntrySet to BinaryInteractions (and save result into MITAB2.5)
        PsimiTabReader tabReader = new PsimiTabReader( true );
        File originalTabFile = TestHelper.getFileByResources( "/mitab-testset/9560268.txt", Tab2XmlTest.class );
        Collection<BinaryInteraction> binaryInteractions = tabReader.read( originalTabFile );
        Assert.assertEquals( 2, binaryInteractions.size() );
        // single interaction with 2 evidences
        final BinaryInteraction bi = binaryInteractions.iterator().next();
        Assert.assertEquals(1, bi.getInteractionTypes().size());

        Xml2Tab x2t = new Xml2Tab();
        x2t.addOverrideSourceDatabase( new CrossReferenceImpl( "MI", "0469", "intact" ) );
//        x2t.setPostProcessor( new ClusterInteractorPairProcessor() );
        Collection<BinaryInteraction> convertedBinaryInteractions = x2t.convert( originalEntrySet );
        assertNotNull( convertedBinaryInteractions );

        Assert.assertEquals( 2, convertedBinaryInteractions.size() );
        final BinaryInteraction cbi = convertedBinaryInteractions.iterator().next();
        Assert.assertEquals(1, cbi.getInteractionTypes().size());

        Tab2Xml t2x = new Tab2Xml();
        t2x.setInteractorNameBuilder( new InteractorUniprotIdBuilder() );

        EntrySet convertedEntrySet = t2x.convert( convertedBinaryInteractions );
        assertNotNull( convertedEntrySet );

        compareEntrySets( originalEntrySet, convertedEntrySet );
    }

    private void compareEntrySets( EntrySet originalEntrySet, EntrySet convertedEntrySet ) {

        assertEquals( originalEntrySet.getLevel(), convertedEntrySet.getLevel() );
        assertEquals( originalEntrySet.getVersion(), convertedEntrySet.getVersion() );
        assertEquals( 0, convertedEntrySet.getMinorVersion() );
        assertEquals( originalEntrySet.getEntries().size(), convertedEntrySet.getEntries().size() );

        Collection<Entry> oEntries = originalEntrySet.getEntries();
        Entry oEntry = oEntries.iterator().next();
        assertTrue( oEntry != null );

        Collection<Entry> cEntries = convertedEntrySet.getEntries();
        Entry cEntry = cEntries.iterator().next();
        assertTrue( cEntry != null );

        assertEquals( 0, cEntry.getExperiments().size() );
        assertEquals( 0, cEntry.getInteractors().size() );
        assertEquals( oEntry.getInteractions().size(), cEntry.getInteractions().size() );
        for ( Interaction interaction : cEntry.getInteractions() ) {

            Assert.assertFalse( interaction.getExperiments().isEmpty() );

            Assert.assertEquals( 2, interaction.getParticipants().size() );
            final Iterator<Participant> participantIterator = interaction.getParticipants().iterator();
            Assert.assertTrue( participantIterator.next().hasInteractor() );
            Assert.assertTrue( participantIterator.next().hasInteractor() );
        }
    }
}
