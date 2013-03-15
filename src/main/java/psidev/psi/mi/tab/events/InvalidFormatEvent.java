package psidev.psi.mi.tab.events;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;

import java.io.Serializable;

/**
 * An event when having invalid format
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class InvalidFormatEvent extends DefaultFileSourceContext implements Serializable{

    private String message;

    public InvalidFormatEvent(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
