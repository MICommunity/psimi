package org.hupo.psi.calimocho.io;

import org.apache.commons.lang.StringUtils;
import org.hupo.psi.calimocho.model.*;
import org.hupo.psi.calimocho.util.ParseUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class DefaultRowReader implements RowReader {

    private DocumentDefinition documentDefinition;

    public DefaultRowReader( DocumentDefinition documentDefinition ) {
        this.documentDefinition = documentDefinition;
    }

    public List<Row> read( Reader reader ) throws IOException, IllegalRowException {


        List<Row> rows = new ArrayList<Row>();

        BufferedReader in = new BufferedReader( reader );
        String str;

        int lineNumber = 0;

        while ( ( str = in.readLine() ) != null ) {
            lineNumber++;

            final String commentPrefix = documentDefinition.getCommentPrefix();

            if ( commentPrefix != null && str.trim().startsWith( commentPrefix ) ) {
                continue;
            }

            try {
                rows.add( readLine( str ) );
            } catch ( Throwable t ) {
                throw new IllegalRowException( "Problem in line: " + lineNumber + "  /  LINE: " + str, str, lineNumber, t );
            }

        }
        in.close();

        return rows;
    }

    public Row readLine( String line ) throws IllegalRowException, IllegalColumnException, IllegalFieldException {
        Row row = new DefaultRow();

        if ( documentDefinition.getColumnSeparator() == null ) {
            throw new NullPointerException( "Document definition does not have column separator" );
        }

        // split the lines using the column separator
        // TODO we may use other characters than quotes - should be defined in columnDefinition
        String[] cols = ParseUtils.quoteAwareSplit( line, new String[]{documentDefinition.getColumnSeparator()}, false );

        // iterate through the columns to parse the fields
        for ( int i = 0; i < cols.length; i++ ) {
            ColumnDefinition columnDefinition = documentDefinition.getColumnByPosition( i );

            if ( columnDefinition == null ) {
                continue;
            }

            String col = cols[i];

            // strip column delimiters
            String colDelimiter = documentDefinition.getColumnDelimiter();
            if ( colDelimiter != null && colDelimiter.length() > 0 ) {
                if ( col.startsWith( colDelimiter ) && col.endsWith( colDelimiter ) ) {
                    col = StringUtils.removeStart( col, colDelimiter );
                    col = StringUtils.removeEnd( col, colDelimiter );
                }
            }

            // check if the column is empty
            final boolean allowEmpty = columnDefinition.isAllowsEmpty();
            final String emptyValue = columnDefinition.getEmptyValue() == null ? "" : columnDefinition.getEmptyValue();
            final boolean isColumnEmpty = col.equals( emptyValue );

            if ( !allowEmpty && isColumnEmpty ) {
                throw new IllegalColumnException( "Empty column not allowed: " + columnDefinition.getKey() + ", pos=" + columnDefinition.getPosition(), col, columnDefinition );
            }

            if ( !isColumnEmpty ) {

                String[] strFields = ParseUtils.columnSplit( col, columnDefinition.getFieldSeparator() );

                for ( String strField : strFields ) {
                    FieldParser fieldParser = columnDefinition.getFieldParser();
                    Field field = fieldParser.parse( strField, columnDefinition );
                    row.addField( columnDefinition.getKey(), field );
                }
            }
        }

        return row;
    }

    public List<Row> read( InputStream is ) throws IOException, IllegalRowException {
        return read( new BufferedReader( new InputStreamReader( is ) ) );
    }
}
