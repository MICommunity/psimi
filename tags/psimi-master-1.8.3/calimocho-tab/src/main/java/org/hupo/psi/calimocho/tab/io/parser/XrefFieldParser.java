package org.hupo.psi.calimocho.tab.io.parser;

import org.hupo.psi.calimocho.tab.io.FieldParser;
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.DefaultField;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.tab.model.ColumnDefinition;
import org.hupo.psi.calimocho.tab.util.ParseUtils;

import java.util.Arrays;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class XrefFieldParser implements FieldParser {
    protected static final String MI_PREFIX = "MI";
    protected static final String MI_DB = "psi-mi";

    public Field parse( String str, ColumnDefinition columnDefinition ) throws IllegalFieldException {
        DefaultField field = new DefaultField();

        str = removeLineReturn(str);
        str = str.trim();

        if (!str.isEmpty()) {
            String[] groups = ParseUtils.quoteAwareSplit( str, new String[]{":", "(", ")"}, true );


            // some exception handling
            if (groups.length > 3) {
                throw new IllegalFieldException("Incorrect number of groups found ("+groups.length+"): "+ Arrays.asList( groups ) + ", in field '"+str+"' / Is this a xref field?");
            }
            
            String key;
            String value;
            
            if (groups.length >= 2) {
                key = groups[0];
                value = groups[1];
            } else {
                // if ony one group, assume it is unknown key
                key = groups[0];
                value = groups[0];
            }

            field.set( CalimochoKeys.KEY, key);
            field.set( CalimochoKeys.DB, key);
            field.set( CalimochoKeys.VALUE, value);

            if (groups.length == 3) {
                field.set( CalimochoKeys.TEXT, groups[2]);
            }

            // correct MI:0012(blah) to psi-mi:"MI:0012"(blah) for backward compatibility
            fixPsimiFieldfNecessary(field);
        }

        return field;
    }

    private String removeLineReturn(String str) {
        // check that the given string doesn't have any line return, and if so, remove them.
        if (str != null && (str.contains("\n"))) {
            str = str.replaceAll("\\n", " ");
        }
        return str;
    }

    protected void fixPsimiFieldfNecessary(Field field) {
        if ( MI_PREFIX.equalsIgnoreCase(field.get(CalimochoKeys.DB))) {
            String identifier = field.get(CalimochoKeys.VALUE);

            field.set(CalimochoKeys.DB, MI_DB);
            field.set(CalimochoKeys.VALUE, MI_PREFIX+":"+identifier);
        }
    }
}
