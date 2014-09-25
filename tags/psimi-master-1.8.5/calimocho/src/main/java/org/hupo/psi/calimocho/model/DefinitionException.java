package org.hupo.psi.calimocho.model;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class DefinitionException extends RuntimeException {

    public DefinitionException() {
    }

    public DefinitionException( String message ) {
        super( message );
    }

    public DefinitionException( String message, Throwable cause ) {
        super( message, cause );
    }

    public DefinitionException( Throwable cause ) {
        super( cause );
    }
}
