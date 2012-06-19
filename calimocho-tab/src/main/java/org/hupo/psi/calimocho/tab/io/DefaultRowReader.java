package org.hupo.psi.calimocho.tab.io;

import org.apache.commons.lang.StringUtils;
import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.io.IllegalRowException;
import org.hupo.psi.calimocho.model.DefaultRow;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;
import org.hupo.psi.calimocho.tab.model.ColumnBasedDocumentDefinition;
import org.hupo.psi.calimocho.tab.model.ColumnDefinition;
import org.hupo.psi.calimocho.tab.util.ParseUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Default implementation for the Row reader, based on the
 * document definition.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class DefaultRowReader implements RowReader {

    private ColumnBasedDocumentDefinition documentDefinition;

    public DefaultRowReader( ColumnBasedDocumentDefinition documentDefinition ) {
        if (documentDefinition == null){
            throw new IllegalArgumentException("The defaultRowWriter needs a valid non null document definition");
        }
        this.documentDefinition = documentDefinition;
    }

    /**
     * {@inheritDoc}
     */
    public List<Row> read( Reader reader ) throws IOException, IllegalRowException {


        List<Row> rows = new ArrayList<Row>();

        BufferedReader in = new BufferedReader( reader );
        
        try{
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
        }
        finally {
            in.close(); 
        }
        
        return rows;
    }

    /**
     * {@inheritDoc}
     */
    public List<Row> read( InputStream is ) throws IOException, IllegalRowException {
        Reader reader = new BufferedReader( new InputStreamReader( is ) );
        List<Row> rows = Collections.EMPTY_LIST;
        try{
            rows = read( reader );
        }
        finally {
            reader.close();
        }
        return rows;
    }

    /**
     * {@inheritDoc}
     */
    public Row readLine( String line ) throws IllegalRowException, IllegalColumnException, IllegalFieldException {
        Row row = new DefaultRow();

        if ( documentDefinition.getColumnSeparator() == null ) {
            throw new NullPointerException( "Document definition does not have column separator" );
        }

        // split the lines using the column separator
        // TODO we may use other characters than quotes - should be defined in columnDefinition
        String[] cols = ParseUtils.quoteAwareSplit( line, new String[]{documentDefinition.getColumnSeparator()}, false );

        final int expectedColumnCount = documentDefinition.getHighestColumnPosition() + 1;

        // iterate through the columns to parse the fields
        int indexColumns = 0;
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

                    // strip field delimiters
                    final String fieldDelimiter = columnDefinition.getFieldDelimiter();

                    if ( fieldDelimiter != null && !fieldDelimiter.isEmpty()) {
                        strField = StringUtils.removeStart( strField, fieldDelimiter );
                        strField = StringUtils.removeEnd( strField, fieldDelimiter );
                    }

                    FieldParser fieldParser = columnDefinition.getFieldParser();

                    if (fieldParser == null){
                        throw new IllegalColumnException(columnDefinition.getName()+" does not have a field parser.");
                    }

                    Field field = fieldParser.parse( strField, columnDefinition );

                    // default values
                    field.setIfMissing(columnDefinition.getDefaultValues());

                    row.addField( columnDefinition.getKey(), field );
                }
            }
            
            indexColumns ++;
        }

        // the missing columns are considered as empty. We just need to check if empty columns are allowed
        if (indexColumns < expectedColumnCount){
            for (int i = indexColumns; i < expectedColumnCount ; i++){
                ColumnDefinition columnDefinition = documentDefinition.getColumnByPosition( i );

                if ( columnDefinition == null ) {
                    continue;
                }
                // check if the column is empty
                final boolean allowEmpty = columnDefinition.isAllowsEmpty();

                if ( !allowEmpty) {
                    throw new IllegalColumnException( "Empty column not allowed: " + columnDefinition.getKey() + ", pos=" + columnDefinition.getPosition(), null, columnDefinition );
                }
            }
        }

        return row;
    }
}
