package psidev.psi.mi.xml.events;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
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

    private PsimiXmlReaderException exception;

    public InvalidXmlEvent(String message){
        this.message = message;
    }

    public InvalidXmlEvent(String message, PsimiXmlReaderException exception){
        this.message = message;
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public PsimiXmlReaderException getException() {
        return exception;
    }
}
