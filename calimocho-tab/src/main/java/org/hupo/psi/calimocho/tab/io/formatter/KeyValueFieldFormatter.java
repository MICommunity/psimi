package org.hupo.psi.calimocho.tab.io.formatter;

import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.model.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;
import org.hupo.psi.calimocho.tab.io.FieldFormatter;

/**
 * This formatter creates a String using the pairs with key 'key' and 'value'
 * from the Field. The String will have the format {key}<separator>{value}.
 * The separator can be set and a default key, which would be used
 * if the Field does not have a key-value pair with key 'key'.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class KeyValueFieldFormatter implements FieldFormatter {

    private String separator;
    private String defaultKey;

    public KeyValueFieldFormatter() {
        this.separator = "=";
    }

    public KeyValueFieldFormatter( String separator ) {
        this.separator = separator;
    }

    public KeyValueFieldFormatter( String separator, String defaultKey ) {
        this.separator = separator;
        this.defaultKey = defaultKey;
    }

    public String format( Field field ) throws IllegalFieldException {
        String key = field.get( CalimochoKeys.KEY );
        String value = field.get( CalimochoKeys.VALUE );

        if (key == null) {
            if (defaultKey == null) {
                throw new IllegalFieldException( "Expected key not found: "+ CalimochoKeys.KEY );
            } else {
                key = defaultKey;
            }
        }

        if (value == null) {
            throw new IllegalFieldException( "Expected key not found: "+CalimochoKeys.VALUE );
        }

        return key+separator+value;
    }

    public String format( Field field, Row row ) throws IllegalFieldException {
        return format(field);
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator( String separator ) {
        this.separator = separator;
    }

    public String getDefaultKey() {
        return defaultKey;
    }

    public void setDefaultKey( String defaultKey ) {
        this.defaultKey = defaultKey;
    }
}
