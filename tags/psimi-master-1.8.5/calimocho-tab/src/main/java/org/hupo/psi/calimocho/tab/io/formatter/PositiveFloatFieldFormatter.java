package org.hupo.psi.calimocho.tab.io.formatter;

import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;
import org.hupo.psi.calimocho.tab.io.FieldFormatter;

/**
 * Formatter for float values
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/05/12</pre>
 */

public class PositiveFloatFieldFormatter implements FieldFormatter {
    public String format(Field field) throws IllegalFieldException {
        String value = field.get( CalimochoKeys.VALUE );

        try {
            float num = Float.parseFloat( value );

            if (num < 0){
                throw new IllegalFieldException( "Positive float expected, found: "+value );
            }
        } catch ( Exception e ) {
            throw new IllegalFieldException( "Positive float expected, found: "+value );
        }

        return value;
    }

    public String format(Field field, Row row) throws IllegalFieldException {
        return format(field);
    }
}
