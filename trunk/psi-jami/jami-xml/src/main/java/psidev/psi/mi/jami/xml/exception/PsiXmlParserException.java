package psidev.psi.mi.jami.xml.exception;

/**
 * An exception to be thrown when a XML file is invalid and cannot be parsed properly
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/10/13</pre>
 */

public class PsiXmlParserException extends Exception{

    public PsiXmlParserException() {
    }

    public PsiXmlParserException(String s) {
        super(s);
    }

    public PsiXmlParserException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public PsiXmlParserException(Throwable throwable) {
        super(throwable);
    }
}
