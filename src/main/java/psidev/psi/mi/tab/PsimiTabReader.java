/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab;

import psidev.psi.mi.tab.converter.txt2tab.MitabLineException;
import psidev.psi.mi.tab.converter.txt2tab.behaviour.UnparseableLineBehaviour;
import psidev.psi.mi.tab.model.*;
import psidev.psi.mi.tab.model.builder.DocumentDefinition;
import psidev.psi.mi.tab.model.builder.MitabDocumentDefinition;
import psidev.psi.mi.xml.converter.ConverterException;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Utility class allowing users to read PSIMITAB data.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public class PsimiTabReader {

    private DocumentDefinition documentDefinition;

    /**
     * has the input got a header.
     * That is, if true, the first line sis skipped.
     */
    private boolean hasHeaderLine = true;

    /**
     * Collection of interactions build.
     */
    private Collection<BinaryInteraction> interactions;

    /**
     * Strategy that defines how the reader behaves when encountering unparseable line.
     */
    private UnparseableLineBehaviour unparseableLineBehaviour;

    
    ///////////////////////////
    // Constructor

    public PsimiTabReader( boolean hasHeaderLine ) {
        this(new MitabDocumentDefinition(), hasHeaderLine);
    }

    public PsimiTabReader(DocumentDefinition documentDefinition, boolean hasHeaderLine) {
        this.documentDefinition = documentDefinition;
        this.hasHeaderLine = hasHeaderLine;
    }

    ////////////////////////////
    // Getters and Setters

    public boolean isHasHeaderLine() {
        return hasHeaderLine;
    }

    public void setHasHeaderLine( boolean hasHeaderLine ) {
        this.hasHeaderLine = hasHeaderLine;
    }

    public UnparseableLineBehaviour getUnparseableLineBehaviour() {
        return unparseableLineBehaviour;
    }

    public void setUnparseableLineBehaviour( UnparseableLineBehaviour unparseableLineBehaviour ) {
        this.unparseableLineBehaviour = unparseableLineBehaviour;
    }

    /**
     * If true, will make sure redundant objects are pooled to save memory.
     * @param enabled
     */
    public void setEnableObjectPooling( boolean enabled ) {
        // propagate this request to all factories
        CrossReferenceFactory.getInstance().setCacheEnabled( enabled );
        InteractionTypeFactory.getInstance().setCacheEnabled( enabled );
        InteractionDetectionMethodFactory.getInstance().setCacheEnabled( enabled );
        OrganismFactory.getInstance().setCacheEnabled( enabled );
    }

    //////////////////////////
    // Public methods

    public Collection<BinaryInteraction> read( String s ) throws IOException, ConverterException {
        return read( new ByteArrayInputStream( s.getBytes() ) );
    }

    public Iterator<BinaryInteraction> iterate( String s ) throws IOException, ConverterException {
        final ByteArrayInputStream is = new ByteArrayInputStream( s.getBytes() );
        final InputStreamReader reader = new InputStreamReader( is );
        return new PsimiTabIterator( documentDefinition, reader, hasHeaderLine );
    }

    public Collection<BinaryInteraction> read( File file ) throws IOException, ConverterException {
        return read( new FileReader( file ) );
    }

    public Iterator<BinaryInteraction> iterate( File file ) throws IOException, ConverterException {
        return new PsimiTabIterator( documentDefinition, new FileReader( file ), hasHeaderLine );
    }

    public Collection<BinaryInteraction> read( InputStream is ) throws IOException, ConverterException {
         return read( new InputStreamReader( is ) );
    }

    public Collection<BinaryInteraction> read( Reader reader ) throws IOException, ConverterException {
        interactions = new ArrayList<BinaryInteraction>();

        BufferedReader in = new BufferedReader( reader );
        String line;

        int lineIndex = 1;

        if ( hasHeaderLine ) {
            String l = in.readLine(); // skip header line
            lineIndex++;
        }

        while ( ( line = in.readLine() ) != null ) {
            try {
                BinaryInteraction interaction = documentDefinition.interactionFromString(line);
                processInteraction( interaction );
                lineIndex++;
            }
            catch ( Throwable e ) {
                in.close();
                throw new ConverterException( "Exception parsing line " + lineIndex+": "+line, e );
            }
        }

        in.close();

        return interactions;
    }

    public Iterator<BinaryInteraction> iterate( InputStream is ) throws IOException, ConverterException {
        return new PsimiTabIterator( documentDefinition, new InputStreamReader( is ), hasHeaderLine );
    }

    /**
     * Reads MITAB data and provides an iterator on interactions.
     *
     * @param r reader on PSIMITAB data
     * @return a non null Iterator of BinaryInteraction
     * @throws IOException
     * @throws ConverterException
     *
     * @since 1.5.2
     */
    public Iterator<BinaryInteraction> iterate( Reader r ) throws IOException, ConverterException {
        return new PsimiTabIterator( documentDefinition, r, hasHeaderLine );
    }

    public Collection<BinaryInteraction> read( URL url ) throws IOException, ConverterException {
        return read( url.openStream() );
    }

    public BinaryInteraction readLine( String str) {
        return documentDefinition.interactionFromString(str);
    }

    protected void processInteraction( BinaryInteraction interaction ) throws MitabLineException {
        interactions.add( interaction );
    }

}