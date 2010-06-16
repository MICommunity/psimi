package psidev.psi.mi;

/**
 * A command line client for the PSI-MI Schema Validator.
 */
public class ValidatorClient {

    public static void main( String[] args ) {

        if( args.length != 3 ) {
            System.err.println( "Usage: ValidatorClient <file> <scope> <level.threshold>" );
            System.exit( 1 );
        }
        final String filename = args[0];
        final String validationScope = args[1];
        final String levelThreshold = args[2];

        System.out.println( "filename = " + filename );
        System.out.println( "validationScope = " + validationScope );
        System.out.println( "levelThreshold = " + levelThreshold );

        // Action ...

    }
}
