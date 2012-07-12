package org.hupo.psi.calimocho.tab.io.parser;

import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.FieldBuilder;
import org.hupo.psi.calimocho.tab.io.FieldParser;
import org.hupo.psi.calimocho.tab.model.ColumnDefinition;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class DateFieldParser implements FieldParser {

    private String dateTimeFormat = "yyyy/MM/dd";
    private DateFormat dateFormat;
    private DateFormat yearFormat;
    private DateFormat dayFormat;
    private DateFormat monthFormat;

    public DateFieldParser() {
        dateFormat = new SimpleDateFormat(dateTimeFormat);
        yearFormat = new SimpleDateFormat("yyyy");
        monthFormat = new SimpleDateFormat("MM");
        dayFormat = new SimpleDateFormat("dd");
    }

    public DateFieldParser( String dateTimeFormat ) {
        if (dateTimeFormat != null){
            this.dateTimeFormat = dateTimeFormat;
        }
        dateFormat = new SimpleDateFormat(dateTimeFormat);
        yearFormat = new SimpleDateFormat("yyyy");
        monthFormat = new SimpleDateFormat("MM");
        dayFormat = new SimpleDateFormat("dd");
    }

    public Field parse( String fieldStr, ColumnDefinition columnDefinition ) throws IllegalFieldException {
        DateTime dateTime = DateTimeFormat.forPattern( dateTimeFormat ).parseDateTime( fieldStr );
        try {
            Date date = dateFormat.parse(fieldStr);
            Field field = new FieldBuilder()
                    .addKeyValue( CalimochoKeys.VALUE, fieldStr )
                    .addKeyValue( CalimochoKeys.DAY, dayFormat.format(date) )
                    .addKeyValue( CalimochoKeys.MONTH, monthFormat.format(date) )
                    .addKeyValue( CalimochoKeys.YEAR, yearFormat.format(date) )
                    .addKeyValue(CalimochoKeys.WEEK, dateTime.weekOfWeekyear().getAsString())
                    .build();

            return field;
        } catch (ParseException e) {
            throw new IllegalFieldException("The date " + fieldStr + " is not valid and should follow this pattern : " + dateTimeFormat);
        }
    }

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    public void setDateTimeFormat( String dateTimeFormat ) {
        this.dateTimeFormat = dateTimeFormat;
    }
}
