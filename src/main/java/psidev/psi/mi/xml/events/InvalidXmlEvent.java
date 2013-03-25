package psidev.psi.mi.xml.events;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.jami.datasource.FileParsingErrorType;
import psidev.psi.mi.xml.PsimiXmlReaderException;

import java.io.Serializable;

/**
 * An event when having invalid xml syntax
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class InvalidXmlEvent extends DefaultFileSourceContext implements Serializable{

    private String message;
    private FileParsingErrorType errorType;

    private PsimiXmlReaderException exception;

    public InvalidXmlEvent(FileParsingErrorType type, String message){
        this.message = message;
        this.errorType = type;
    }

    public InvalidXmlEvent(String message, PsimiXmlReaderException exception){
        this.message = message;
        this.exception = exception;
        this.errorType = FileParsingErrorType.invalid_syntax;
    }

    public String getMessage() {
        return message;
    }

    public PsimiXmlReaderException getException() {
        return exception;
    }

    public FileParsingErrorType getErrorType() {
        return errorType;
    }
}
