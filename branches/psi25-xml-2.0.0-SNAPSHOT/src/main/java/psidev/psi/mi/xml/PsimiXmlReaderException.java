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

    public PsimiXmlReaderException(Object currentObject) {
        this.currentObject = currentObject;
    }

    public PsimiXmlReaderException( String message, Object currentObject ) {
        super( message );
        this.currentObject = currentObject;
    }

    public PsimiXmlReaderException( String message, Throwable cause, Object currentObject ) {
        super( message, cause );
        this.currentObject = currentObject;
    }

    public PsimiXmlReaderException( Throwable cause, Object currentObject ) {
        super( cause );
        this.currentObject = currentObject;
    }

    public Object getCurrentObject() {
        return currentObject;
    }
}
