package psidev.psi.mi.validator.client.console;

import psidev.psi.mi.validator.extension.MiValidator;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.util.Log4jConfigurator;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * <b> A console based client </b>.
 *
 * @author Samuel Kerrien & Matthias Oesterheld
 * @version $Id$
 * @since 04.01.2006; 17:37:46
 */
public class MI25ConsoleClient {

    /**
     * Validates a PSI-MI 2.5 XML file against a set of rules defined in the given configuration file.
     * <br/>
     * Only messages having a log level higher
     *
     * @param psiFile
     * @param configFile
     * @param logLevel
     */
    private static void validate( String psiFile, String configFile, String logLevel ) {
        FileInputStream f = null;
        FileInputStream c = null;
        
        try {
            f = new FileInputStream( psiFile );
            c = new FileInputStream( configFile );
            // TODO this will not work  !!! has to be fixed.
            MiValidator val = new MiValidator( c, null, null );
            List messages = ( List ) val.validate( f );

            MessageLevel logThreshold = MessageLevel.forName( logLevel );

            for ( int i = 0; i < messages.size(); i++ ) {
                ValidatorMessage msg = ( ValidatorMessage ) messages.get( i );

                if ( logThreshold.isLower( msg.getLevel() ) || logThreshold.isSame( msg.getLevel() ) ) {
                    System.out.println( "====================================================================================" );
                    System.out.println( msg.toString() );
                }
            }
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        } catch ( ValidatorException e ) {
            e.getCause().printStackTrace();
        } catch ( OntologyLoaderException e ) {
            e.printStackTrace();
        }
        finally {
            if (f != null){
                try {
                    f.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (c != null){
                try {
                    c.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main( String[] args ) {

        if ( args.length < 2 || args.length > 3 ) {
            System.err.println( "Usage: ConsoleClient psiFile configFile logLevel(DEBUG, INFO, WARN, ERROR FATAL)" );
            System.exit( 1 );
        }

        // setup Logging.
        Log4jConfigurator.configure();

        String psiFile = null;
        String configFile = null;
        String logLevel = null;

        if ( args.length >= 2 ) {
            psiFile = args[0];
            configFile = args[1];
        }

        if ( args.length > 2 ) {
            logLevel = args[2];
        }

        validate( psiFile, configFile, logLevel );
    }
}