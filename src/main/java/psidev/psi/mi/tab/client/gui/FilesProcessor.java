/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.client.gui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.PsimiTabWriter;
import psidev.psi.mi.tab.converter.xml2tab.Xml2Tab;
import psidev.psi.mi.tab.expansion.ExpansionStrategy;
import psidev.psi.mi.tab.expansion.MatrixExpansion;
import psidev.psi.mi.tab.expansion.SpokeExpansion;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.processor.PostProcessorStrategy;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Module taking care of converting the files dropped in the GUI in to PSIMITAB.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Nov-2006</pre>
 */
public class FilesProcessor {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( FilesProcessor.class );

    public static final int MAX_FILE_DISPLAY = 7;

    public void process( JFrame frame, File[] files,
                         ExpansionStrategy expansionStrategy,
                         PostProcessorStrategy postProcessorStrategy,
                         boolean aggregateFiles ) {

        java.util.List<File> inputFiles = new ArrayList<File>( files.length );
        java.util.List<File> unselectedFiles = new ArrayList<File>( files.length );

        for ( int i = 0; i < files.length; i++ ) {
            File file = files[ i ];
            if ( file.isDirectory() || file.getName().endsWith( ".xml" ) ) {
                inputFiles.add( file );
            } else {
                unselectedFiles.add( file );
            }
        }

        if ( !unselectedFiles.isEmpty() ) {
            String message = "Some of the file your have dropped are not XML files:";
            int count = 0;
            boolean stop = false;
            for ( Iterator<File> iterator = unselectedFiles.iterator(); iterator.hasNext() && !stop; ) {
                File file = iterator.next();

                message += "\n- " + file.getName();

                count++;
                if( count >= MAX_FILE_DISPLAY && iterator.hasNext() ) {
                    message += "\n- ... " + (unselectedFiles.size() - MAX_FILE_DISPLAY) + " more.";
                    stop = true; // leave the loop.
                }
            }
            message += "\nPlease try again.";

            JOptionPane.showMessageDialog( frame,
                                           message,
                                           "Invalid file(s)",
                                           JOptionPane.ERROR_MESSAGE );
        }

        if ( inputFiles.isEmpty() ) {
            String message = "No input file to be processed, please drop one or use File > Open files...";
            JOptionPane.showMessageDialog( frame,
                                           message,
                                           "No input file.",
                                           JOptionPane.WARNING_MESSAGE );
        } else {

            // start convertion
            Xml2Tab x2t = new Xml2Tab();

            // configure it
            x2t.setPostProcessor( postProcessorStrategy );

            PsimiTabWriter fileWriter = new PsimiTabWriter();

            if ( aggregateFiles ) {

                log.debug( "Aggregate conversion starts..." );

                // build output file name
                File output = buildOutputFile( inputFiles.get( 0 ), aggregateFiles, expansionStrategy );

                try {
                    long start = System.currentTimeMillis();

                    Collection<BinaryInteraction> interactions = x2t.convert( inputFiles );
                    fileWriter.write( interactions, output );

                    long stop = System.currentTimeMillis();
                    log.debug( "conversion took: " + ( stop - start ) + "ms" );

                    String message = "Your file" + ( files.length > 1 ? "s were" : " was" ) + " converted succesfully." +
                                     "\nLocation: " + output.getAbsolutePath();

                    JOptionPane.showMessageDialog( frame,
                                                   message,
                                                   "Conversion complete",
                                                   JOptionPane.INFORMATION_MESSAGE );

                } catch ( Exception e ) {
                    e.printStackTrace();
                    String message = e.getMessage();
                    JOptionPane.showMessageDialog( frame,
                                                   message,
                                                   "Error - " + e.getClass().getSimpleName(),
                                                   JOptionPane.ERROR_MESSAGE );
                }

            } else {

                // one output file per input file.
                log.debug( "Non aggregative conversion starts..." );

                int ok = 0;
                int failed = 0;

                for ( File inputFile : inputFiles ) {

                    // build output filename
                    File output = buildOutputFile( inputFile, aggregateFiles, expansionStrategy );

                    try {
                        long start = System.currentTimeMillis();

                        List<File> singleFile = new ArrayList<File>( 1 );
                        singleFile.add( inputFile );

                        Collection<BinaryInteraction> interactions = x2t.convert( singleFile );
                        fileWriter.write( interactions, output );

                        long stop = System.currentTimeMillis();
                        log.debug( "conversion took: " + ( stop - start ) + "ms" );

                        ok++;

                    } catch ( Exception e ) {
                        e.printStackTrace();
                        String message = e.getMessage();
                        JOptionPane.showMessageDialog( frame,
                                                       message,
                                                       "Error - " + e.getClass().getSimpleName(),
                                                       JOptionPane.ERROR_MESSAGE );
                        failed++;
                    }

                }

                String message = ok + " file" + ( ok > 1 ? "s were" : " was" ) + " converted succesfully.";
                if ( failed > 0 ) {
                    message += "\n" + failed + " failed to be converted.";
                }

                JOptionPane.showMessageDialog( frame,
                                               message,
                                               "Conversion complete.",
                                               JOptionPane.INFORMATION_MESSAGE );
            }
        }
    }

    private File buildOutputFile( File file, boolean aggregateName, ExpansionStrategy expansionStrategy ) {

        String baseFilename;
        if ( aggregateName ) {

            baseFilename = file.getParentFile().getAbsolutePath();
            baseFilename += File.separator + file.getParentFile().getName();

        } else {

            // make sure it only happens for the extention of the file, not on one of the sub-dir
            baseFilename = file.getAbsolutePath().replaceAll( ".xml", "" );

        }

        if ( expansionStrategy != null ) {
            if ( expansionStrategy instanceof SpokeExpansion ) {
                baseFilename += ".spoke";
            } else if ( expansionStrategy instanceof MatrixExpansion ) {
                baseFilename += ".matrix";
            }
        }

        File output = new File( baseFilename + ".xls" );
        int i = 1;
        while ( output.exists() ) {
            log.warn( output.getName() + " exists, change filename." );
            i++;
            output = new File( baseFilename + ".v" + i + ".xls" );
        }

        return output;
    }
}