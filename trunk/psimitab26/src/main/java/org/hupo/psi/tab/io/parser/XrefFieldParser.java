package org.hupo.psi.tab.io.parser;

import org.hupo.psi.calimocho.io.FieldParser;
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.model.CalimochoKeys;
import org.hupo.psi.calimocho.model.ColumnDefinition;
import org.hupo.psi.calimocho.model.DefaultField;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.util.ParseUtils;

import java.util.Arrays;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class XrefFieldParser implements FieldParser {

    public Field parse( String str, ColumnDefinition columnDefinition ) throws IllegalFieldException {
        DefaultField field = new DefaultField();

        str = removeLineReturn(str);
        str = str.trim();

        if (!str.isEmpty()) {
            String[] groups = ParseUtils.quoteAwareSplit( str, new String[]{":", "(", ")"}, true );

            // some exception handling
            if (groups.length < 2 || groups.length > 3) {
                throw new IllegalFieldException("Incorrect number of groups found ("+groups.length+"): "+ Arrays.asList( groups ) + ", in field '"+str+"'");
            }

            field.set( CalimochoKeys.KEY, groups[0]);
            field.set( CalimochoKeys.DB, groups[0]);
            field.set( CalimochoKeys.VALUE, groups[1]);

            if (groups.length == 3) {
                field.set( CalimochoKeys.TEXT, groups[2]);
            }

            // TODO correct MI:0012(blah) to psi-mi:"MI:0012"(blah)
            //field = fixPsimiFieldfNecessary(field);
        }

        return field;
    }

    private String removeLineReturn(String str) {
        // check that the given string doesn't have any line return, and if so, remove them.
        if (str != null && (str.indexOf("\n") != -1)) {
            str = str.replaceAll("\\n", " ");
        }
        return str;
    }

}
