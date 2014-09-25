package psidev.psi.mi.xml;

/**
 * module specific runtime exception.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.0
 */
public class PsimiXmlReaderRuntimeException extends RuntimeException {

    public PsimiXmlReaderRuntimeException() {
        super();
    }

    public PsimiXmlReaderRuntimeException( String message ) {
        super( message );
    }

    public PsimiXmlReaderRuntimeException( String message, Throwable cause ) {
        super( message, cause );
    }

    public PsimiXmlReaderRuntimeException( Throwable cause ) {
        super( cause );
    }
}
