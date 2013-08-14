package org.hupo.psi.calimocho.tab.io.formatter;

import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;
import org.hupo.psi.calimocho.tab.io.FieldFormatter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class DateFieldFormatter implements FieldFormatter {

    private String dateTimeFormat = "yyyy/MM/dd";

    public DateFieldFormatter() {
    }

    public DateFieldFormatter( String dateTimeFormat ) {
        if (dateTimeFormat != null){
            this.dateTimeFormat = dateTimeFormat;
        }
    }

    public String format( Field field ) throws IllegalFieldException {
        int day = field.getInteger( CalimochoKeys.DAY );
        int month = field.getInteger( CalimochoKeys.MONTH );
        int year = field.getInteger( CalimochoKeys.YEAR );

        DateTime dateTime = new DateTime(year, month, day, 0, 0, 0, 0);

        return dateTime.toString( DateTimeFormat.forPattern( dateTimeFormat ) );
    }

    public String format( Field field, Row row ) throws IllegalFieldException {
        return format(field);
    }
}
