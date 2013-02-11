/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.txt2tab.behaviour;

import psidev.psi.mi.tab.converter.txt2tab.MitabLineException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Write unparseable lines to a file.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06-Feb-2007</pre>
 */
public class WriteToFileUnparseableLine implements UnparseableLineBehaviour {

    private static final String NEW_LINE = System.getProperty( "line.separator" );

    ///////////////////////
    // Instance variables

    /**
     * File where to write the unparseable lines.
     */
    private File file;

    /**
     * Writer to the given file.
     */
    private BufferedWriter out;

    /**
     * If set to true,
     */
    private boolean quietMode = true;

    /////////////////////////////
    // Constructor

    public WriteToFileUnparseableLine( File file ) {
        if ( file == null ) {
            throw new IllegalArgumentException( "You must give a non null file." );
        }

        if ( file.exists() && !file.canWrite() ) {
            throw new IllegalArgumentException( "Cannot write file: " + file.getAbsolutePath() );
        }

        this.file = file;
    }

    public WriteToFileUnparseableLine( File file, boolean quietMode ) {
        this( file );
        this.quietMode = quietMode;
    }

    ////////////////////
    //

    public void closeFile() {
        try {
            out.close();
        } catch ( IOException e ) {
            if ( !quietMode ) {
                // propagate as a RuntimeException
                throw new RuntimeException( "Error upon closing file: " + file.getAbsolutePath(), e );
            }
        }
    }

    public File getFile() {
        return file;
    }

    //////////////////////////////
    // UnparseableLineBehaviour

    public void respond( String line, MitabLineException t ) throws MitabLineException {
        try {
            if ( out == null ) {
                out = new BufferedWriter( new FileWriter( file ) );
            }

            // write the line to file
            out.write( line );
            out.write( NEW_LINE );
            out.flush();

        } catch ( IOException e ) {
            if ( !quietMode ) {
                // propagate as a RuntimeException
                throw new RuntimeException( "Error upon writing unparseable line to file: " + file.getAbsolutePath(), e );
            }
        }
    }

    public boolean propagateException() {
        return false;
    }
}