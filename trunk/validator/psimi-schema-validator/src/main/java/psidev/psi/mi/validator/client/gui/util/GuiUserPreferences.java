/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.validator.client.gui.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import psidev.psi.tools.validator.preferences.UserPreferences;
import psidev.psi.tools.validator.ValidatorException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * User preferences for the GUI client.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05-Jun-2006</pre>
 */
public class GuiUserPreferences {

    public static final Log log = LogFactory.getLog( GuiUserPreferences.class );

    // Properties' key
    public static final String LAST_DIRECTORY_OPENED = "last.directory.opened";

    private UserPreferences preferences;

    /////////////////////////
    // Constructors

    public GuiUserPreferences( UserPreferences preferences ) {
        this.preferences = preferences;
    }

    public GuiUserPreferences() {
        this( new UserPreferences() );
    }

    ////////////////////////
    // Setters and getters

    /**
     * Set the last opened directory.
     *
     * @param lastDir the last opened directory.
     */
    public void setLastOpenedDirectory( File lastDir ) {

        if ( lastDir == null ) {
            throw new IllegalArgumentException( "The given directory must not be null." );
        }

        if ( !lastDir.exists() ) {
            throw new IllegalArgumentException( "The given directory must exist (" + lastDir.getAbsolutePath() + ")." );
        }

        if ( !lastDir.isDirectory() ) {
            throw new IllegalArgumentException( "The given file must be a directory (" + lastDir.getAbsolutePath() + ")." );
        }

        File props = null;
        try {
            props = preferences.getPropertiesFile();
        } catch ( ValidatorException e ) {
            log.warn( "Could not save the last opened directory.", e );
        }

        if ( props == null ) {
            return;
        }

        Properties properties = new Properties();
        try {
            properties.load( new FileInputStream( props ) );
        } catch ( IOException e ) {
            log.error( "Error - loading properties.", e );
        }

        properties.setProperty( GuiUserPreferences.LAST_DIRECTORY_OPENED, lastDir.getAbsolutePath() );

        // Write properties file.
        try {
            properties.store( new FileOutputStream( props ), null );
        } catch ( IOException e ) {
            log.warn( "Error - writting properties.", e );
        }
    }


    /**
     * Get the last opened directory.
     *
     * @return the last opened directory.
     */
    public File getLastOpenedDirectory() {

        File props = null;
        try {
            props = preferences.getPropertiesFile();
        } catch ( ValidatorException e ) {
            e.printStackTrace();
            return preferences.getWorkDirectory();
        }

        // we have the properties file, now read the property
        Properties properties = new Properties();
        try {
            properties.load( new FileInputStream( props ) );
            String lastPath = properties.getProperty( GuiUserPreferences.LAST_DIRECTORY_OPENED );
            if ( lastPath == null ) {
                // not defined yet
                log.warn( "Warning - " + GuiUserPreferences.LAST_DIRECTORY_OPENED + " was not defined." );
                return preferences.getWorkDirectory();
            } else {
                return new File( lastPath );
            }
        } catch ( IOException e ) {
            log.error( "Error - loading properties.", e );
        }

        return null;
    }
}