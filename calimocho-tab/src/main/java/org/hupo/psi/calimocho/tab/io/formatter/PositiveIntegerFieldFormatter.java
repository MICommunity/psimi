package org.hupo.psi.calimocho.tab.io.formatter;

import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;
import org.hupo.psi.calimocho.tab.io.FieldFormatter;

/**
 * Formatter for integer values
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/12</pre>
 */

public class PositiveIntegerFieldFormatter implements FieldFormatter {

    public String format(Field field) throws IllegalFieldException {
        String value = field.get( CalimochoKeys.VALUE );
        int num = 0;

        try {
            num = Integer.parseInt( value );

            if (num < 0){
                throw new IllegalFieldException( "Positive integer expected, found: "+value );
            }
        } catch ( Exception e ) {
            throw new IllegalFieldException( "Positive integer expected, found: "+value );
        }

        return Integer.toString(num);
    }

    public String format(Field field, Row row) throws IllegalFieldException {
        return format(field);
    }
}
