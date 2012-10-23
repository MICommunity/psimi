package org.hupo.psi.calimocho.tab.io.parser;

import org.apache.commons.lang.StringUtils;
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.DefaultField;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.tab.model.ColumnDefinition;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/05/12</pre>
 */

public class AnnotationFieldParser extends KeyValueFieldParser {

    public AnnotationFieldParser() {
        super(":");
    }

    public AnnotationFieldParser(String separator) {
        super(separator);
    }

    @Override
    public Field parse( String keyValue, ColumnDefinition columnDefinition ) throws IllegalFieldException {
        String[] tokens = StringUtils.splitPreserveAllTokens(keyValue, getSeparator(), 2);

        String key;
        String value = null;

        if (tokens.length == 1) {

            key = tokens[0];
        } else {
            key = tokens[0];
            value = tokens[1];
        }

        Field field = new DefaultField();
        field.set( CalimochoKeys.NAME, key);
        if (value != null){
            field.set( CalimochoKeys.VALUE, value);
        }

        return field;
    }
}
