package org.hupo.psi.tab.parser;

import org.hupo.psi.calimocho.io.FieldParser;
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.model.ColumnDefinition;
import org.hupo.psi.calimocho.model.DefaultField;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.tab.MitabKeys;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class LiteralFieldParser implements FieldParser {

    public Field parse( String value, ColumnDefinition columnDefinition ) throws IllegalFieldException {
       Field field = new DefaultField();
       field.set( MitabKeys.VALUE, value);

        return field;
    }

}
