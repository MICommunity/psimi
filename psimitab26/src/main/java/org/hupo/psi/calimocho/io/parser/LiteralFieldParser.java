package org.hupo.psi.calimocho.io.parser;

import org.hupo.psi.calimocho.io.FieldParser;
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.model.CalimochoKeys;
import org.hupo.psi.calimocho.model.ColumnDefinition;
import org.hupo.psi.calimocho.model.DefaultField;
import org.hupo.psi.calimocho.model.Field;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class LiteralFieldParser implements FieldParser {

    public Field parse( String value, ColumnDefinition columnDefinition ) throws IllegalFieldException {
       Field field = new DefaultField();
       field.set( CalimochoKeys.VALUE, value);

        return field;
    }

}
