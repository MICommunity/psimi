package org.hupo.psi.calimocho.tab.io.parser;

import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.DefaultField;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.tab.io.FieldParser;
import org.hupo.psi.calimocho.tab.model.ColumnDefinition;

/**
 * Parses a free text String, creating a field with a 'value' key with the
 * content of the String.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class LiteralFieldParser implements FieldParser {

    private String defaultKey;

    public LiteralFieldParser() {
    }

    public LiteralFieldParser(String defaultKey) {
        this.defaultKey = defaultKey;
    }

    /**
     * {@inheritDoc}
     *
     * @param value a literal, free text
     * @param columnDefinition it is ignored in this FieldParser implementation
     * @return the field generated using the value
     * @throws IllegalFieldException cannot be thrown by this implementation
     */
    public Field parse(String value, ColumnDefinition columnDefinition) throws IllegalFieldException {
        Field field = new DefaultField();
        field.set(CalimochoKeys.VALUE, value);

        if (defaultKey != null) {
            field.set(CalimochoKeys.KEY, defaultKey);
        }

        return field;
    }

}
