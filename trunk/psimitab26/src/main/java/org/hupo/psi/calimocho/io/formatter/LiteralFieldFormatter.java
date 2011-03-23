package org.hupo.psi.calimocho.io.formatter;

import org.hupo.psi.calimocho.io.FieldFormatter;
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.model.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class LiteralFieldFormatter implements FieldFormatter {

    public LiteralFieldFormatter() {
    }

    public String format( Field field ) throws IllegalFieldException {
        final String value = field.get( CalimochoKeys.VALUE );

        if (value == null) throw new IllegalFieldException( "Field does not contain '"+CalimochoKeys.VALUE+"' key: "+field );

        return value;
    }

    public String format( Field field, Row row ) throws IllegalFieldException {
       return format( field );
    }
}
