package org.hupo.psi.calimocho.tab.io.parser;

import org.apache.commons.lang.StringUtils;
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.DefaultField;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.tab.io.FieldParser;
import org.hupo.psi.calimocho.tab.model.ColumnDefinition;

/**
 * <p>Parses Strings with structures like {key}<separator>{value}. For instance:
 *
 * <ul>
 *     <li>key:value</li>
 *     <li>uniprotkb=P12345</li>
 * </ul>
 *
 * <p>The separator between key and value can be specified. If there is no separator,
 * a default key may specified. In this case, the content of the String will be
 * used as the value, and the default key will be the key.</p>
 * <p>The created Field will have two value pairs, with keys 'key' and 'value'.</p>
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class KeyValueFieldParser implements FieldParser {

    private String separator;

    public KeyValueFieldParser() {
        separator = "=";
    }

    public KeyValueFieldParser(String separator) {
        this.separator = separator;
    }

    /**
     * {@inheritDoc}
     */
    public Field parse( String keyValue, ColumnDefinition columnDefinition ) throws IllegalFieldException {
       String[] tokens = StringUtils.splitPreserveAllTokens( keyValue, separator, 2 );

        String key;
        String value;

        if (tokens.length == 1) {
            final String defaultKey = columnDefinition.getDefaultValue( CalimochoKeys.KEY );

            if ( defaultKey == null) {
                throw new IllegalFieldException( "Expecting a field formed by two tokens separated by: '"+separator+
                                                 "', or a default value for key 'key' in the column definition. Field: "+keyValue);
            }

            key = defaultKey;
            value = tokens[0];
        } else {
            key = tokens[0];
            value = tokens[1];
        }

       Field field = new DefaultField();
       field.set( CalimochoKeys.KEY, key);
       field.set( CalimochoKeys.VALUE, value);

        return field;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator( String separator ) {
        this.separator = separator;
    }

}
