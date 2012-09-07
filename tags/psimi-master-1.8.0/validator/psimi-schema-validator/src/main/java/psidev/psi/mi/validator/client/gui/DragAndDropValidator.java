/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.validator.client.gui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.validator.client.gui.dnd.DragAndDropComponent;
import psidev.psi.mi.validator.client.gui.dnd.FilesDroppedListener;
import psidev.psi.mi.validator.client.gui.util.GuiUserPreferences;
import psidev.psi.mi.validator.client.gui.view.validator.ValidatorTable;
import psidev.psi.mi.validator.client.gui.view.validator.ValidatorTableRow;
import psidev.psi.mi.validator.extension.Mi25Validator;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.Validator;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.preferences.UserPreferences;
import psidev.psi.tools.validator.util.Log4jConfigurator;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Simple Swing front end for validating PSI-MI XML files.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17-Jan-2006</pre>
 */
public class DragAndDropValidator {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( DragAndDropValidator.class );

    final static private class XmlFileFilter extends javax.swing.filechooser.FileFilter {
        public boolean accept( File file ) {

            // show all directories and file with an xml extension.

            if ( file.isDirectory() ) {
                return true;
            }

            String filename = file.getName();
            return filename.endsWith( ".xml" );
        }

        public String getDescription() {
            return "*.xml";
        }
    }

    private static final String VERSION = "0.1.1";
    private static final String AUTHORS = "Samuel Kerrien";
    private static final String NEW_LINE = System.getProperty( "line.separator" );

    private static GuiUserPreferences userPreferences = new GuiUserPreferences();


    /**
     * Sets up the menu bar of the main window.
     *
     * @param frame the frame onto which we attach the menu bar
     * @param table the table that allow to command the validator.
     * @param level the level of log message.
     */
    public static void setupMenus( final JFrame frame,
                                   final Validator validator,
                                   final ValidatorTable table,
                                   final MessageLevel level ) {

        // Create the fileMenu bar
        JMenuBar menuBar = new JMenuBar();

        final UserPreferences preferences = validator.getUserPreferences();

        ////////////////////////////
        // 1. Create a file menu
        JMenu fileMenu = new JMenu( "File" );
        menuBar.add( fileMenu );

        // Create a file Menu items
        JMenuItem openFile = new JMenuItem( "Open file..." );
        openFile.addActionListener( new ActionListener() {

            public void actionPerformed( ActionEvent e ) {

                GuiUserPreferences guiPrefs = new GuiUserPreferences( preferences );

                File lastDirectory = userPreferences.getLastOpenedDirectory();

                JFileChooser fileChooser = new JFileChooser( lastDirectory );
                fileChooser.addChoosableFileFilter( new XmlFileFilter() );

                // Open file dialog.
                fileChooser.showOpenDialog( frame );

                File selected = fileChooser.getSelectedFile();

                if ( selected != null ) {
                    userPreferences.setLastOpenedDirectory( selected.getParentFile() );

                    // start validation of the selected file.
                    ValidatorTableRow row = new ValidatorTableRow( selected, level, true );

                    table.addTableRow( row );
                }
            }
        } );
        fileMenu.add( openFile );

        // exit menu item
        JMenuItem exit = new JMenuItem( "Exit" );
        exit.addActionListener( new ActionListener() {

            public void actionPerformed( ActionEvent e ) {

                System.exit( 0 );
            }
        } );
        fileMenu.add( exit );

        ///////////////////////////////
        // 2. TODO setup messages menu

//        JMenu msgMenu = new JMenu( "Messages" );
//        menuBar.add( msgMenu );
//
//        // Create a clear all messages item
//        JMenuItem clearAll = new JMenuItem( "Clear all" );
//        openFile.addActionListener( new ActionListener() {
//
//            public void actionPerformed( ActionEvent e ) {
//
//                // remove all elements
//                table.removeAllRows( );
//            }
//        } );
//        msgMenu.add( clearAll );

        //////////////////////////////
        // Log level

        // TODO create a menu that allows to switch log level at run time.

        /////////////////////////
        // 3. setup help menu
        JMenu helpMenu = new JMenu( "Help" );
        menuBar.add( helpMenu );

        JMenuItem about = new JMenuItem( "About..." );
        about.addActionListener( new ActionListener() {

            public void actionPerformed( ActionEvent e ) {
                JOptionPane msg = new JOptionPane( "Version " + VERSION + NEW_LINE + "Authors: " + AUTHORS );
                JDialog dialog = msg.createDialog( frame, "About PSI Validator" );
                dialog.setResizable( true );
                dialog.pack();
                dialog.setVisible( true );
            }
        } );
        helpMenu.add( about );

        // Install the fileMenu bar in the frame
        frame.setJMenuBar( menuBar );
    }

    /**
     * Runs a sample program that shows dropped files
     */
    public static void main( String[] args ) throws FileNotFoundException, ValidatorException {

        // TODO ValidatorException should popup instead of appearing in the background (ie. console)

        if ( args.length == 0 || args.length > 2 ) {
            System.err.println( "Usage: DragAndDropValidator <validator_config_file> [log level]" );
            System.exit( 1 );
        }

        // setup Logging.
        Log4jConfigurator.configure();

        String configFile = args[0];
        final MessageLevel level;
        if ( args.length == 2 ) {
            level = MessageLevel.forName( args[1] );
            log.info( "User requested log level: " + level );
        } else {
            level = MessageLevel.WARN; // default
            log.info( "Message Level set to default: " + level );
        }

        FileInputStream configStream = new FileInputStream( configFile );
        Validator validator = null;
        try {
            validator = new Mi25Validator( configStream, null, null );
        } catch ( OntologyLoaderException e ) {
            throw new ValidatorException( "", e );
        }
        finally {
            try {
                configStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Create and set up the window.
        final JFrame frame = new javax.swing.JFrame( "PSI-MI 2.5 Drag & Drop Validator" );

        //Create and set up the content pane.
        final ValidatorTable validatorTable = new ValidatorTable( validator );
        validatorTable.setOpaque( true ); //content panes must be opaque
        frame.setContentPane( validatorTable );

        // Handle the dropped files
        PrintStream out = null; // could be System.out
        new DragAndDropComponent( out, validatorTable, new FilesDroppedListener() {

            public void filesDropped( java.io.File[] files ) {

                for ( int i = 0; i < files.length; i++ ) {

                    ValidatorTableRow row = new ValidatorTableRow( files[i], level, true );

                    validatorTable.addTableRow( row );
                }   // end for: through each dropped file
            }   // end filesDropped
        } ); // end FileDrop.Listener

        // Set up menus
        setupMenus( frame, validator, validatorTable, level );

        // Set up the window.
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setBounds( 100, 100, 50, 50 ); // the size of the frame is defined by its internal component.

        frame.pack();
        frame.setVisible( true );
    }   // end main
}