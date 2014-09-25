package org.hupo.psi.calimocho.tab.io.parser;

import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.FieldBuilder;
import org.hupo.psi.calimocho.tab.io.FieldParser;
import org.hupo.psi.calimocho.tab.model.ColumnDefinition;

/**
 * Positive float parser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/05/12</pre>
 */

public class PositiveFloatrFieldParser implements FieldParser {

    public Field parse( String fieldStr, ColumnDefinition columnDefinition ) throws IllegalFieldException {
        try {
            float num = Float.parseFloat( fieldStr );

            if (num < 0){
                throw new IllegalFieldException( "Positive float expected, found: "+fieldStr );
            }
        } catch ( Exception e ) {
            throw new IllegalFieldException( "Positive float expected, found: "+fieldStr );
        }

        return new FieldBuilder()
                .addKeyValue( CalimochoKeys.VALUE, fieldStr )
                .build();
    }
}
