package org.hupo.psi.tab.io.formatter;

import com.sun.org.apache.bcel.internal.generic.CALOAD;
import org.hupo.psi.calimocho.io.FieldFormatter;
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.model.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;
import org.hupo.psi.tab.util.MitabEscapeUtils;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class XrefFieldFormatter implements FieldFormatter {

    public String format( Field field ) throws IllegalFieldException {
        StringBuilder sb = new StringBuilder();

        final String db = field.get( CalimochoKeys.DB );
        final String value = field.get( CalimochoKeys.VALUE );

        if(db == null){
            throw new IllegalFieldException( "Missing database in field: " + field.toString() );
        }

        if(value == null){
            throw new IllegalFieldException( "Missing value in field: " + field.toString() );
        }

        sb.append( MitabEscapeUtils.escapeFieldElement( db ) ).append( ':' ).append( MitabEscapeUtils.escapeFieldElement( value ) );

        final String text = field.get( CalimochoKeys.TEXT );
        if(text != null){
           sb.append('(').append( MitabEscapeUtils.escapeFieldElement( text )).append( ')' );
        }

        return sb.toString();
    }

    public String format( Field field, Row row ) throws IllegalFieldException {
        return format(field);
    }
}
