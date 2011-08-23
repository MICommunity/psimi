package org.hupo.psi.calimocho.tab.io.parser;

import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.model.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.FieldBuilder;
import org.hupo.psi.calimocho.tab.io.FieldParser;
import org.hupo.psi.calimocho.tab.model.ColumnDefinition;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class DateFieldParser implements FieldParser {

    private String dateTimeFormat;

    public DateFieldParser() {
        this.dateTimeFormat = DateTimeFormat.longDate().toString();
    }

    public DateFieldParser( String dateTimeFormat ) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public Field parse( String fieldStr, ColumnDefinition columnDefinition ) throws IllegalFieldException {
        DateTime dateTime = DateTimeFormat.forPattern( dateTimeFormat ).parseDateTime( fieldStr );

        Field field = new FieldBuilder()
                .addKeyValue( CalimochoKeys.VALUE, fieldStr )
                .addKeyValue( CalimochoKeys.DAY, dateTime.dayOfMonth().getAsString() )
                .addKeyValue( CalimochoKeys.MONTH, dateTime.monthOfYear().getAsString() )
                .addKeyValue( CalimochoKeys.YEAR, dateTime.year().getAsString() )
                .addKeyValue( CalimochoKeys.WEEK, dateTime.weekOfWeekyear().getAsString() )
                .build();

        return field;

    }

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    public void setDateTimeFormat( String dateTimeFormat ) {
        this.dateTimeFormat = dateTimeFormat;
    }
}
