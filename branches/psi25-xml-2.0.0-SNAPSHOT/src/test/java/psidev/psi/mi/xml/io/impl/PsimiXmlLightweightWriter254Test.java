package psidev.psi.mi.xml.io.impl;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.xmlindex.IndexedEntry;
import psidev.psi.mi.xml.xmlindex.PsimiXmlIndexerTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;

/**
 * PsimiXmlLightweightWriter Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.5.0
 */
public class PsimiXmlLightweightWriter254Test {

    @Test
    public void writeFile() throws Exception {

        File file = new File( PsimiXmlIndexerTest.class.getResource("/sample-xml/intact/10320477.254.xml").getFile() );
        PsimiXmlLightweightReader253 reader = new PsimiXmlLightweightReader253( file );
        final List<IndexedEntry> indexedEntries = reader.getIndexedEntries();
        Assert.assertNotNull( indexedEntries );
        Assert.assertEquals( 1, indexedEntries.size() );
        final IndexedEntry entry = indexedEntries.iterator().next();

        final File outputFile = new File( getTargetDirectory(), "10320477.output.254.xml" );
        PsimiXmlLightweightWriter254 writer = new PsimiXmlLightweightWriter254( outputFile );

        writer.writeStartDocument();
        writer.writeStartEntry( entry.unmarshallSource(), entry.unmarshallAvailabilityList() );

        final Iterator<Interaction> iterator = entry.unmarshallInteractionIterator();
        while ( iterator.hasNext() ) {
            Interaction interaction = iterator.next();
            writer.writeInteraction( interaction );
        }

        writer.writeEndEntry( entry.unmarshallAttributeList() );
        writer.writeEndDocument();

        final String separator = System.getProperty( "line.separator" );
        if( separator.length() == 2 ) {
            Assert.assertEquals( 43957, outputFile.length() );
        } else if( separator.length() == 1 ){
            Assert.assertEquals( 24356, outputFile.length() );
        } else  {
            Assert.fail( "System.getProperty( \"line.separator\" ) returned a unsupported separator of length: " + separator.length() );
        }
    }

    private File getTargetDirectory() {
        String outputDirPath = PsimiXmlLightweightWriter254Test.class.getResource( "/" ).getFile();
        Assert.assertNotNull( outputDirPath );
        File outputDir = new File( outputDirPath );
        // we are in test-classes, move one up
        outputDir = outputDir.getParentFile();
        Assert.assertNotNull( outputDir );
        Assert.assertTrue( outputDir.isDirectory() );
        Assert.assertEquals( "target", outputDir.getName() );
        return outputDir;
    }

    private void printFile( File outputFile ) throws Exception {
        BufferedReader in = new BufferedReader( new FileReader( outputFile ) );
        String str;
        while ( ( str = in.readLine() ) != null ) {
            System.out.println( str );
        }
        in.close();
    }
}