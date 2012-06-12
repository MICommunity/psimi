/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.client.gui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.client.gui.dnd.DragAndDropComponent;
import psidev.psi.mi.tab.client.gui.dnd.FilesDroppedListener;
import psidev.psi.mi.tab.expansion.ExpansionStrategy;
import psidev.psi.mi.tab.expansion.MatrixExpansion;
import psidev.psi.mi.tab.expansion.SpokeExpansion;
import psidev.psi.mi.tab.processor.ClusterInteractorPairProcessor;
import psidev.psi.mi.tab.processor.PostProcessorStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Front end for converting XML files to PSIMITAB.
 *
 * TODO Use psidev.psi.mi.tab.client.batch.ProcessDirectory to implement the dropping of a directory.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17-Jan-2006</pre>
 */
public class DragAndDropConverter {

    public static final String ABOUT_CONTENT = "Version 1.0 beta 3" +
                                               "\nAuthorImpl: Samuel Kerrien (skerrien@ebi.ac.uk)";

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( DragAndDropConverter.class );

    //////////////////
    // Strategies

    private static ExpansionStrategy expansionStrategy = new SpokeExpansion();

    private static PostProcessorStrategy postProcessorStrategy = new ClusterInteractorPairProcessor();

    private static boolean aggregateSelectedFiles = true;

    //////////////////////////////
    // Frame menu

    private static void addMenuBar( JFrame frame ) {
        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

        addFileMenu( frame, menuBar );
        addExpansionMenu( menuBar );
        addProcessorMenu( menuBar );
        addHelpMenu( frame, menuBar );

        // Install the menu bar in the frame
        frame.setJMenuBar( menuBar );
    }

    public static void addFileMenu( final JFrame frame, JMenuBar menuBar ) {

        // Create a menu
        JMenu menu = new JMenu( "File" );
        menuBar.add( menu );

        // Create a file open iten
        JMenuItem openFileItem = new JMenuItem( "Open files..." );
        openFileItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {

                JFileChooser chooser = new JFileChooser();

                chooser.setApproveButtonText( "Convert to MITAB25" );

                // Enable multiple selections
                chooser.setMultiSelectionEnabled( true );

                // Show the dialog; wait until dialog is closed
                chooser.showOpenDialog( frame );

                // Retrieve the selected files. This method returns empty
                // if multiple-selection mode is not enabled.
                File[] files = chooser.getSelectedFiles();

                if ( files != null && files.length > 0 ) {
                    FilesProcessor processor = new FilesProcessor();
                    processor.process( frame, files, expansionStrategy, postProcessorStrategy, aggregateSelectedFiles );
                }
            }
        } );
        menu.add( openFileItem );

        // Create an exit
        final JMenuItem mergeItem = new JCheckBoxMenuItem( "Aggregate all selected files", aggregateSelectedFiles );
        mergeItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                // If selected, all files dropped or selected at once are aggregated into a single MITAB file
                aggregateSelectedFiles = mergeItem.isSelected();
                log.debug( "Aggregate is " + aggregateSelectedFiles );
            }
        } );
        menu.add( mergeItem );

        // Create an exit
        JMenuItem exitItem = new JMenuItem( "Exit" );
        exitItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                System.exit( 0 );
            }
        } );
        menu.add( exitItem );
    }

    public static void addExpansionMenu( JMenuBar menuBar ) {

        ButtonGroup itemGroup = new ButtonGroup();

        // Create a menu
        JMenu menu = new JMenu( "Expansion model" );
        menuBar.add( menu );

        // Create none item
        JMenuItem noneItem = new JRadioButtonMenuItem( "None" );
        noneItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                log.debug( "No expansion strategy selected." );
                expansionStrategy = null;
            }
        } );
        itemGroup.add( noneItem );
        menu.add( noneItem );

        // Create spoke model item
        JMenuItem spokeItem = new JRadioButtonMenuItem( "Spoke" );
        spokeItem.setSelected( true );
        spokeItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                log.debug( "Sets expansion statefy to Spoke." );
                expansionStrategy = new SpokeExpansion();
            }
        } );
        itemGroup.add( spokeItem );
        menu.add( spokeItem );

        // Create an exit
        JMenuItem matrixItem = new JRadioButtonMenuItem( "Matrix" );
        matrixItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                log.debug( "Sets expansion statefy to Matrix." );
                expansionStrategy = new MatrixExpansion();
            }
        } );
        itemGroup.add( matrixItem );
        menu.add( matrixItem );
    }

    public static void addProcessorMenu( JMenuBar menuBar ) {

        // Create a menu
        JMenu menu = new JMenu( "Processing" );
        menuBar.add( menu );

        ButtonGroup itemGroup = new ButtonGroup();

        // Create none item
        JMenuItem noneItem = new JRadioButtonMenuItem( "None" );
        noneItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                log.debug( "Disables post processing." );
                postProcessorStrategy = null;
            }
        } );
        itemGroup.add( noneItem );
        menu.add( noneItem );

        // Create a clustering item
        JMenuItem clusteringInteractorItem = new JRadioButtonMenuItem( "Clustering interactor pairs" );
        clusteringInteractorItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                log.debug( "Enables post processing: ClusterPerInteractorProcessor" );
                postProcessorStrategy = new ClusterInteractorPairProcessor();
            }
        } );
        itemGroup.add( clusteringInteractorItem );
        clusteringInteractorItem.setSelected( true );
        menu.add( clusteringInteractorItem );
    }

    private static void addHelpMenu( final JFrame frame, JMenuBar menuBar ) {

        // Create a menu
        JMenu menu = new JMenu( "Help" );
        menuBar.add( menu );

        // Create a file open iten
        JMenuItem item = new JMenuItem( "About" );
        item.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {

                JOptionPane.showMessageDialog( frame,
                                               ABOUT_CONTENT,
                                               "About PSIMITAB Converter",
                                               JOptionPane.INFORMATION_MESSAGE );
            }
        } );
        menu.add( item );
    }

    /////////////////////////////
    // Actions

    //////////////////
    // T E S T

    /**
     * Drag and Drop MITAB25 Converter.
     */
    public static void main( String[] args ) throws FileNotFoundException {

        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        } catch ( Exception e ) {
            log.error( "Failed to change Look and Feel.", e );
        }

        // Create and set up the window.
        final JFrame frame = new javax.swing.JFrame( "MITAB25 Converter" );
        frame.setPreferredSize( new Dimension( 400, 200 ) );

        JPanel panel = new JPanel();
        panel.setLayout( new GridBagLayout() );
        frame.add( panel );

        addMenuBar( frame );

        Component label = new JLabel( "Drag and drop your XML file(s) here" );
        label.setPreferredSize( new Dimension( 400, 200 ) );
        label.setForeground( Color.red );
        panel.add( label );
        // Handle the dropped files
        PrintStream out = null; // could be System.out
        new DragAndDropComponent( out, panel, new FilesDroppedListener() {
            public void filesDropped( java.io.File[] files ) {

                FilesProcessor processor = new FilesProcessor();
                processor.process( frame, files, expansionStrategy, postProcessorStrategy, aggregateSelectedFiles );
            }
        } ); // end FileDrop.Listener

        // Set up the window.
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setBounds( 100, 100, 50, 50 ); // the size of the frame is defined by its internal component.

        frame.pack();
        frame.setVisible( true );
    }
}