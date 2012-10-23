/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.directoryProcessor;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Select file based on matching the filename with regular expression.
 * <p/>
 * By default the processing is done recursively.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Jan-2007</pre>
 */
public class PatternBasedFilenameSelection implements InputDirectoryProcessorStrategy {

    private boolean recursive = true;
    private Pattern filenameInclude;

    public PatternBasedFilenameSelection( boolean recursive, Pattern filenameIncludePattern ) {
        if ( filenameIncludePattern == null ) {
            throw new IllegalArgumentException( "The filename pattern must not be null." );
        }
        this.recursive = recursive;
        this.filenameInclude = filenameIncludePattern;
    }

    public Collection<File> process( File directory ) {

        if( directory == null ) {
            throw new IllegalArgumentException( "You must give a non null directory." );
        }

        if( ! directory.isDirectory() ) {
            throw new IllegalArgumentException( "You must give a directory." );
        }

        if( ! directory.canRead() ) {
            throw new IllegalArgumentException( "Read access must be granted before processing this directory." );
        }

        Collection<File> collectedFiles = new ArrayList<File>( );

        Stack<File> stack = new Stack<File>();
        stack.push( directory );

        while ( !stack.isEmpty() ) {

            File file = stack.pop();

            File[] files = file.listFiles( new FilenameFilter() {
                public boolean accept( File dir, String name ) {

                    if ( dir.isDirectory() ) {
                        return true;
                    }

                    if ( name.endsWith( ".xml" ) ) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } );

            for ( int i = 0; i < files.length; i++ ) {
                File file1 = files[ i ];

                if ( file1.isDirectory() ) {
                    if ( recursive ) {
                        stack.push( file1 );
                    }
                } else {
                    // process the file
                    Matcher matcher = filenameInclude.matcher( file1.getName() );
                    if( matcher.matches() ) {
                        // filename matches the filter, add it.                        
                        collectedFiles.add( file1 );
                    }
                }
            } // files of the current dir
        } // stack

        return collectedFiles;
    }
}