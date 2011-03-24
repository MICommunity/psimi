package org.hupo.psi.calimocho.model;

import org.hupo.psi.calimocho.io.IllegalFieldException;

/**
 * TODO document this !
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class FieldBuilder {

    DefaultField field = new DefaultField();

    public FieldBuilder addKey( String key ) {
        field.set( CalimochoKeys.KEY, key );
        return this;
    }

    public FieldBuilder addKeyValue( String key, String value ) {
        field.set( key, value );
        return this;
    }

    public Field build() throws IllegalFieldException {
        validate( field );
        return field;
    }

    private void validate( DefaultField field ) throws IllegalFieldException {
    }
}
