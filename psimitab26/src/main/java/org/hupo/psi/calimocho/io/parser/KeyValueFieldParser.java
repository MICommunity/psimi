package org.hupo.psi.calimocho.io.parser;

import org.apache.commons.lang.StringUtils;
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
public class KeyValueFieldParser implements FieldParser {

    private String separator;

    public KeyValueFieldParser() {
        separator = "=";
    }

    public KeyValueFieldParser(String separator) {
        this.separator = separator;
    }

    public Field parse( String keyValue, ColumnDefinition columnDefinition ) throws IllegalFieldException {
       String[] tokens = StringUtils.splitPreserveAllTokens( keyValue, separator );

        if (tokens.length != 2) {
            throw new IllegalFieldException( "Expecting a field formed by two tokens separated by: '"+separator+"', field: "+keyValue);
        }

       Field field = new DefaultField();
       field.set( CalimochoKeys.KEY, tokens[0]);
       field.set( CalimochoKeys.VALUE, tokens[1]);

        return field;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator( String separator ) {
        this.separator = separator;
    }
}
