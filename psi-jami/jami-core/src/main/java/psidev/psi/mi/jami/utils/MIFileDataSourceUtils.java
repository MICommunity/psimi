package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.datasource.FileSourceError;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Utility class for Molecular interaction datasources
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/03/13</pre>
 */

public class MIFileDataSourceUtils {

    /**
     * Collect all data source errors having same error type
     * @param errors
     * @param errorType
     * @return
     */
    public static Collection<FileSourceError> collectAllDataSourceErrorsHavingErrorType(Collection<? extends FileSourceError> errors, String errorType){

        if (errors == null || errors.isEmpty() || errorType == null){
            return Collections.EMPTY_LIST;
        }
        Collection<FileSourceError> filteredErrors = new ArrayList<FileSourceError>(errors.size());

        for (FileSourceError err : errors){
            if (err.getLabel() != null && err.getLabel().equalsIgnoreCase(errorType)){
                filteredErrors.add(err);
            }
        }

        return filteredErrors;
    }

    /**
     * Collect all data source errors having same error type
     * @param errors
     * @param errorTypes
     * @return
     */
    public static Collection<FileSourceError> collectAllDataSourceErrorsHavingErrorTypes(Collection<? extends FileSourceError> errors, String[] errorTypes){

        if (errors == null || errors.isEmpty() || errorTypes == null || errorTypes.length == 0){
            return Collections.EMPTY_LIST;
        }
        Collection<FileSourceError> filteredErrors = new ArrayList<FileSourceError>(errors.size());

        for (FileSourceError err : errors){
            if (err.getLabel() != null){
                for (String errorType : errorTypes){
                    if (err.getLabel().equalsIgnoreCase(errorType)){
                        filteredErrors.add(err);
                        break;
                    }
                }
            }
        }

        return filteredErrors;
    }

    /**
     * Store the content of the given input stream into a temporary file and return its descriptor.
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
        try{

            BufferedWriter out = new BufferedWriter( new FileWriter( tempFile ) );

            try{
                String line;
                while ( ( line = in.readLine() ) != null ) {
                    out.write( line );
                    out.write( System.getProperty("line.separator"));
                }


                out.flush();
            }
            finally {
                out.close();
            }
        }
        finally {
            in.close();
        }

        return tempFile;
    }
}
