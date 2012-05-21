/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.builder.DocumentDefinition;
import psidev.psi.mi.tab.model.builder.MitabDocumentDefinition;
import psidev.psi.mi.xml.converter.ConverterException;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;

/**
 * Utility class allowing users to write PSIMITAB format.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public class PsimiTabWriter {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( PsimiTabWriter.class );

    public static final String NEW_LINE = System.getProperty( "line.separator" );

    boolean headerAlreadyWritten = false;

    private boolean headerEnabled = true;

    private DocumentDefinition documentDefinition;


    ///////////////////////////////
    // Constructors

    public PsimiTabWriter() {
        this(true);
    }

    public PsimiTabWriter( boolean headerEnabled ) {
        this.headerEnabled = headerEnabled;
        this.documentDefinition = new MitabDocumentDefinition();
    }

    public PsimiTabWriter(DocumentDefinition documentDefinition, boolean headerEnabled) {
        this.documentDefinition = documentDefinition;
        this.headerEnabled = headerEnabled;
    }

    ///////////////////////////////
    // Handling of the filter

    public boolean hasHeaderEnabled() {
        return headerEnabled;
    }

    public void setHeaderEnabled( boolean headerEnabled ) {
        this.headerEnabled = headerEnabled;
    }

    //////////////////////////
    // Writer public methods

    public void writeOrAppend( BinaryInteraction binaryInteraction, File file, boolean createFile ) throws IOException {
        if ( file == null ) {
            throw new IllegalArgumentException( "You must give a non null file." );
        }

        if ( file.exists() ) {
            if (createFile && log.isWarnEnabled()){
                log.warn( file.getAbsolutePath() + " is going to be overwritten" );
            }

            if ( !file.canWrite() ) {
                throw new IllegalArgumentException( "You must give a writeable file." );
            }
        }

        Writer writer;
        if (createFile && hasHeaderEnabled()) {
            writer = new BufferedWriter(new FileWriter(file, false)) ;
            writer.write( buildHeaderLine() + NEW_LINE );
        } else{
            writer = new BufferedWriter(new FileWriter(file, true));
        }

        String line = documentDefinition.interactionToString( binaryInteraction );
        writer.write( line + NEW_LINE );
        writer.close();
    }

    public void writeOrAppend(Collection<BinaryInteraction> interactions, File file, boolean createFile) throws IOException, ConverterException {
         if ( file == null ) {
            throw new IllegalArgumentException( "You must give a non null file." );
        }

        if ( file.exists() ) {
            if (createFile && log.isWarnEnabled()){
                log.warn( file.getAbsolutePath() + " is going to be overwritten" );
            }

            if ( !file.canWrite() ) {
                throw new IllegalArgumentException( "You must give a writeable file." );
            }
        }

        Writer writer;
        if (createFile ) {
            write(interactions, file);
        } else{
            //appending
            writer = new BufferedWriter(new FileWriter(file, true));

            for ( Iterator<BinaryInteraction> iter = interactions.iterator(); iter.hasNext(); ) {
                BinaryInteraction binaryInteraction = iter.next();
                String line = documentDefinition.interactionToString(binaryInteraction);
                writer.append( line + NEW_LINE );
            }
            writer.close();
        }
    }

    public void write( Collection<BinaryInteraction> interactions, File file ) throws IOException, ConverterException {

        if ( file == null ) {
            throw new IllegalArgumentException( "You must give a non null file." );
        }

        if ( file.exists() ) {
            log.warn( file.getAbsolutePath() + " is going to be overwritten" );

            if ( !file.canWrite() ) {
                throw new IllegalArgumentException( "You must give a writeable file." );
            }
        }

        // write content to file
        Writer out = new BufferedWriter( new FileWriter( file ) );

        write( interactions, out );

        // flush & close
        out.flush();
        out.close();
    }

    public void write( Collection<BinaryInteraction> interactions, OutputStream os ) throws IOException, ConverterException {
        final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
        write( interactions, writer);
        writer.flush();

        // close writer
        writer.close();
    }

    public void write( Collection<BinaryInteraction> interactions, Writer writer ) throws IOException, ConverterException {

        if ( !headerAlreadyWritten && hasHeaderEnabled() ) {
            writer.write( buildHeaderLine() + NEW_LINE );
            headerAlreadyWritten = true;
        }

        for ( BinaryInteraction binaryInteraction : interactions ) {
            String line = documentDefinition.interactionToString(binaryInteraction);
            writer.write( line + NEW_LINE );
        }
        writer.flush();
    }

    public void write( Collection<BinaryInteraction> interactions, PrintStream ps ) throws IOException, ConverterException {
        final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ps));
        write( interactions, writer);
        writer.close();
    }

    public void write( BinaryInteraction interaction, OutputStream os ) throws IOException {
        final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
        write( interaction, writer);
        writer.close();
    }

    public void write( BinaryInteraction interaction, Writer writer ) throws IOException {

        if ( !headerAlreadyWritten && hasHeaderEnabled() ) {
            writer.write( buildHeaderLine() + NEW_LINE );
            headerAlreadyWritten = true;
        }

        String line = documentDefinition.interactionToString(interaction);
        writer.write( ( line + NEW_LINE ) );
    }

    /////////////////////
    // Utility

    private String buildHeaderLine() {
        StringBuilder sb = new StringBuilder( 256 );

        if( documentDefinition == null ) {
            throw new IllegalStateException( "You cannot request a header without giving a valid documentDefinition." );
        }
        int columnCount = documentDefinition.getColumnsCount();

        sb.append("#");

        for (int i=0; i<columnCount; i++) {
            if (i > 0) {
                sb.append("\t");
            }
            String columnName = documentDefinition.getColumnDefinition(i).getColumnName();
            sb.append(columnName);
        }

        return sb.toString();
    }
}