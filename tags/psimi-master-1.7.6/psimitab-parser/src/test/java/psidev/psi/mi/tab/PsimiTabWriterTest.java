package psidev.psi.mi.tab;

import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import psidev.psi.mi.tab.converter.xml2tab.Xml2Tab;
import psidev.psi.mi.tab.converter.xml2tab.Xml2TabTest;
import psidev.psi.mi.tab.mock.PsimiTabMockBuilder;
import psidev.psi.mi.tab.model.AliasImpl;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.CrossReferenceImpl;
import psidev.psi.mi.tab.model.Interactor;
import psidev.psi.mi.tab.model.builder.MitabDocumentDefinition;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;

/**
 * PsimiTabWriter Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version 1.0
 * @since <pre>10/04/2006</pre>
 */
public class PsimiTabWriterTest {

    private int lineCount( File file ) throws IOException {

        BufferedReader in = new BufferedReader( new FileReader( file ) );
        String line;
        int count = 0;
        while ( ( line = in.readLine() ) != null ) {
            // process line here
            count++;
        }
        in.close();

        return count;
    }

    @Test
    public void writeToFile() throws Exception {
        File file = TestHelper.getFileByResources( "/psi25-samples/11585365.xml", Xml2TabTest.class );
        File outputFile = new File( file.getAbsolutePath() + ".tab" );

        if ( outputFile.exists() ) {
            outputFile.delete();
        }

        // convert into Tab object model
        Xml2Tab xml2tab = new Xml2Tab();
        Collection<BinaryInteraction> interactions = xml2tab.convert( file );
        assertEquals( 8, interactions.size() );

        // write it our to a file
        PsimiTabWriter writer = new PsimiTabWriter( new MitabDocumentDefinition(), true );
        writer.write( interactions, outputFile );

        assertTrue( outputFile.exists() );
        assertEquals( 9, lineCount( outputFile ) );
    }

    @Test
    public void appendOrWrite() throws Exception {
        File file = TestHelper.getFileByResources( "/mitab-testset/chen.txt", Xml2TabTest.class );
        File outputFile = new File( file.getAbsolutePath() + ".tab" );

        //read binary interactions
        PsimiTabReader reader = new PsimiTabReader( new MitabDocumentDefinition(), true );
        Collection<BinaryInteraction> interactions = reader.read( file );

        // write binary interactions
        PsimiTabWriter writer = new PsimiTabWriter( new MitabDocumentDefinition(), true );
        boolean first = true;
        for ( Iterator<BinaryInteraction> iter = interactions.iterator(); iter.hasNext(); ) {
            BinaryInteraction binaryInteraction = iter.next();
            writer.writeOrAppend( binaryInteraction, outputFile, first );
            first = false;
        }
        assertTrue( outputFile.exists() );
        assertEquals( interactions.size() + 1, lineCount( outputFile ) );
    }

    @Test
    public void appendOrWriteCollection() throws Exception {
        File file = TestHelper.getFileByResources( "/mitab-testset/chen.txt", Xml2TabTest.class );
        File outputFile = new File( file.getAbsolutePath() + ".tab" );
        if ( outputFile.exists() ) {
            outputFile.delete();
        }

        //read binary interactions
        PsimiTabReader reader = new PsimiTabReader( true );
        Collection<BinaryInteraction> interactions = reader.read( file );

        // write binary interactions
        PsimiTabWriter writer = new PsimiTabWriter( new MitabDocumentDefinition(), true );
        writer.setHeaderEnabled( true );
        boolean first = true;
        writer.writeOrAppend( interactions, outputFile, first );
        assertTrue( outputFile.exists() );
        assertEquals( interactions.size() + 1, lineCount( outputFile ) );
    }

    @Test
    public void appendCollection() throws Exception {
        File file = TestHelper.getFileByResources( "/mitab-testset/chen.txt", Xml2TabTest.class );
        File outputFile = new File( file.getAbsolutePath() + ".tab" );
        if ( outputFile.exists() ) {
            outputFile.delete();
        }

        //read binary interactions
        PsimiTabReader reader = new PsimiTabReader( true );
        Collection<BinaryInteraction> interactions = reader.read( file );

        // write binary interactions
        PsimiTabWriter writer = new PsimiTabWriter( new MitabDocumentDefinition(), true );
        writer.setHeaderEnabled( true );
        boolean first = false;
        writer.writeOrAppend( interactions, outputFile, first );
        assertTrue( outputFile.exists() );
        assertEquals( interactions.size(), lineCount( outputFile ) );
    }

    @Test
    public void write_single_interaction() throws Exception {
        File file = TestHelper.getFileByResources( "/mitab-testset/chen.txt", Xml2TabTest.class );
        File outputFile = new File( file.getAbsolutePath() + ".tab" );
        if ( outputFile.exists() ) {
            outputFile.delete();
        }

        //read binary interactions
        PsimiTabReader reader = new PsimiTabReader( true );
        Collection<BinaryInteraction> interactions = reader.read( file );

        // write binary interactions
        PsimiTabWriter writer = new PsimiTabWriter( new MitabDocumentDefinition(), true );

        StringWriter outputWriter = new StringWriter( 1024 );
        for ( BinaryInteraction interaction : interactions ) {
            writer.write( interaction, outputWriter );
        }

        final String result = outputWriter.getBuffer().toString();
        final String[] lines = result.split( "\n" );
        assertEquals( 8, lines.length );
    }

    @Test
    public void escapeSpecialCharactersRoundtrip() throws Exception {

        // Checks that the PsimiTabWriter is escaping special characters, also that the PsimiTabReader can read it back

        PsimiTabMockBuilder mockBuilder = new PsimiTabMockBuilder();
        final Interactor a = mockBuilder.createInteractor( 9606, "uniprotkb", "P12345", "nice\tprotein" );
        a.getAlternativeIdentifiers().add( new CrossReferenceImpl( "uni:prot", "g(en)e", "la\tla") );
        a.getAliases().add(new AliasImpl( "uniprotkb", "a:l|i(as)A" ) );

        final Interactor b = mockBuilder.createInteractor( 9606, "uniprotkb", "P12345", "ni:ce(protein)" );
        b.getAlternativeIdentifiers().add( new CrossReferenceImpl( "uni:prot", "g(en)e", "la\tla") );
        b.getAliases().add(new AliasImpl( "uniprotkb", "a:l|i(as)A" ) );

        final BinaryInteraction bi = mockBuilder.createInteraction( a, b );

        bi.getPublications().add( new CrossReferenceImpl( "pub:med", "12345|678", "ti(t)le" ) );
        bi.getSourceDatabases().add( new CrossReferenceImpl( "M|I", "00:00", "in(ta)ct") );
        bi.getInteractionAcs().add( new CrossReferenceImpl( "in(ta)ct", "EB:I-1234567", "i|d" ) );

        // write it to a String
        PsimiTabWriter writer = new PsimiTabWriter( false );

        StringWriter sw = new StringWriter( 512 );
        writer.write( bi, sw );

        final String line = sw.getBuffer().toString();

        // now parse it again and check that the format is fine

        PsimiTabReader reader = new PsimiTabReader( false );
        StringReader sr = new StringReader( line );
        final Collection<BinaryInteraction> interactions = reader.read( sr );
        Assert.assertNotNull( interactions );
        Assert.assertEquals( 1, interactions.size() );
        final BinaryInteraction bi2 = interactions.iterator().next();

        Assert.assertEquals( bi, bi2 );
    }
}