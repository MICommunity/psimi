package psidev.psi.mi.jami.utils;

import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Utility class for Molecular interaction datasources
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/03/13</pre>
 */

public class MIFileDataSourceUtils {

    /**
     * Store the content of the given input stream into a temporary file and return its descriptor.
     * It will not close the given input stream.
     *
     * @param is the input stream to store.
     * @return a File descriptor describing a temporary file storing the content of the given input stream.
     * @throws java.io.IOException if an IO error occur.
     */
    public static File storeAsTemporaryFile( InputStream is, String name, String extension ) throws IOException {

        if ( is == null ) {
            throw new IllegalArgumentException( "You must give a non null InputStream" );
        }

        // Create a temp file and write URL content in it.
        File tempDirectory = new File( System.getProperty( "java.io.tmpdir", "tmp" ) );
        if ( !tempDirectory.exists() ) {
            if ( !tempDirectory.mkdirs() ) {
                throw new IOException( "Cannot create temp directory: " + tempDirectory.getAbsolutePath() );
            }
        }

        File tempFile = File.createTempFile( name, extension , tempDirectory );
        BufferedReader in = new BufferedReader( new InputStreamReader( is ) );
        Writer out = null;
        try{
            out = new BufferedWriter( new FileWriter( tempFile ) );
            IOUtils.copyLarge(in, out);
        }
        finally {
            in.close();
            if (out != null){
                out.close();
            }
        }

        return tempFile;
    }

    /**
     * Store the content of the given reader into a temporary file and return its descriptor.
     * It will not close the given reader.
     *
     * @param reader the reader to use.
     * @return a File descriptor describing a temporary file storing the content of the given input stream.
     * @throws java.io.IOException if an IO error occur.
     */
    public static File storeAsTemporaryFile( Reader reader, String name, String extension ) throws IOException {

        if ( reader == null ) {
            throw new IllegalArgumentException( "You must give a non null reader" );
        }

        // Create a temp file and write URL content in it.
        File tempDirectory = new File( System.getProperty( "java.io.tmpdir", "tmp" ) );
        if ( !tempDirectory.exists() ) {
            if ( !tempDirectory.mkdirs() ) {
                throw new IOException( "Cannot create temp directory: " + tempDirectory.getAbsolutePath() );
            }
        }

        File tempFile = File.createTempFile( name, extension , tempDirectory );

        Writer out = null;
        try{
            out = new BufferedWriter( new FileWriter( tempFile ) );
            IOUtils.copyLarge(reader, out);
        }
        finally {
            if (out != null){
                out.close();
            }
        }

        return tempFile;
    }
}
