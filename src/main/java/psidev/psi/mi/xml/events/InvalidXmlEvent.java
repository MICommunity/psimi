package psidev.psi.mi.xml.events;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;

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

    public InvalidXmlEvent(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
