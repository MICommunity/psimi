package psidev.psi.mi.xml;

/**
 * TODO commenta that class header
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since specify the maven artifact version
 */
public class PsimiXmlReaderException extends Exception {

    private Object currentObject;

    public PsimiXmlReaderException() {
    }

    public PsimiXmlReaderException( String message ) {
        super( message );
    }

    public PsimiXmlReaderException( String message, Throwable cause ) {
        super( message, cause );
    }

    public PsimiXmlReaderException( Throwable cause ) {
        super( cause );
    }

    public Object getCurrentObject() {
        return currentObject;
    }
}
