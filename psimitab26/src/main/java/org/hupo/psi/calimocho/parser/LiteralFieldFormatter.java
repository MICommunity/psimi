package org.hupo.psi.calimocho.parser;

import org.hupo.psi.calimocho.io.FieldFormatter;
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class LiteralFieldFormatter implements FieldFormatter {

    public LiteralFieldFormatter() {
    }

    public String format( Field field ) throws IllegalFieldException {
        return field.get( CalimochoKeys.VALUE );
    }

    public String format( Field field, Row row ) throws IllegalFieldException {
       return format( field );
    }
}
