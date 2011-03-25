package org.hupo.psi.tab.io.parser;

import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.model.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.FieldBuilder;
import org.hupo.psi.tab.io.FieldParser;
import org.hupo.psi.tab.model.ColumnDefinition;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class BooleanFieldParser implements FieldParser {

    public Field parse( String fieldStr, ColumnDefinition columnDefinition ) throws IllegalFieldException {
        try {
            Boolean.parseBoolean( fieldStr );
        } catch ( Exception e ) {
            throw new IllegalFieldException( "Boolean expected, found: "+fieldStr );
        }

        return new FieldBuilder()
                .addKeyValue( CalimochoKeys.VALUE, fieldStr )
                .build();
    }
}
