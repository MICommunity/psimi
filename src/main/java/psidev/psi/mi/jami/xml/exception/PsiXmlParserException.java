package psidev.psi.mi.jami.xml.exception;

import psidev.psi.mi.jami.datasource.FileSourceLocator;

/**
 * An exception to be thrown when a XML file is invalid and cannot be parsed properly
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/10/13</pre>
 */

public class PsiXmlParserException extends Exception{

    private FileSourceLocator locator;

    public PsiXmlParserException() {
    }

    public PsiXmlParserException(FileSourceLocator locator) {
        this.locator = locator;
    }

    public PsiXmlParserException(int line, int col) {
        this.locator = new FileSourceLocator(line, col);
    }

    public PsiXmlParserException(FileSourceLocator locator, String message, Throwable throwable) {
        super(message, throwable);
        this.locator = locator;
    }

    public PsiXmlParserException(int line, int col, String message, Throwable throwable) {
        super(message, throwable);
        this.locator = new FileSourceLocator(line, col);
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

    public FileSourceLocator getLocator() {
        return locator;
    }
}
