package org.hupo.psi.calimocho.io.parser;

import org.apache.commons.lang.StringUtils;
import org.hupo.psi.calimocho.io.FieldParser;
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.model.CalimochoKeys;
import org.hupo.psi.calimocho.model.ColumnDefinition;
import org.hupo.psi.calimocho.model.DefaultField;
import org.hupo.psi.calimocho.model.Field;

/**
 * <p>Parses Strings with structures like {key}{separator}{value}. For instance:
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
    private String defaultKey;

    public KeyValueFieldParser() {
        separator = "=";
    }

    public KeyValueFieldParser(String separator) {
        this.separator = separator;
    }

    public KeyValueFieldParser( String separator, String defaultKey ) {
        this.separator = separator;
        this.defaultKey = defaultKey;
    }

    /**
     * {@inheritDoc}
     */
    public Field parse( String keyValue, ColumnDefinition columnDefinition ) throws IllegalFieldException {
       String[] tokens = StringUtils.splitPreserveAllTokens( keyValue, separator, 2 );

        String key;
        String value;

        if (tokens.length == 1) {
            if (defaultKey == null) {
                throw new IllegalFieldException( "Expecting a field formed by two tokens separated by: '"+separator+"', field: "+keyValue);
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

    public String getDefaultKey() {
        return defaultKey;
    }

    public void setDefaultKey( String defaultKey ) {
        this.defaultKey = defaultKey;
    }
}
