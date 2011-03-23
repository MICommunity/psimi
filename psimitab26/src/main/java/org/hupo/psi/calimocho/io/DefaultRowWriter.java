package org.hupo.psi.calimocho.io;

import org.hupo.psi.calimocho.model.ColumnDefinition;
import org.hupo.psi.calimocho.model.DocumentDefinition;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * TODO document this !
 *
 * @author Christine Jandrasits (cjandras@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class DefaultRowWriter implements RowWriter {

    private static final String NEW_LINE = System.getProperty( "line.separator" );
    private DocumentDefinition documentDefinition;

    public DefaultRowWriter( DocumentDefinition documentDefinition ) {
        this.documentDefinition = documentDefinition;

    }

    // TODO check that there is a fieldSeparator when more than 1 field to be written.

    public void write( Writer writer, Collection<Row> rows ) throws IOException, IllegalRowException {
        if ( rows == null ) {
            throw new IllegalArgumentException( "You must give a non null rows" );
        }

        for ( Row row : rows ) {
            try {
                writer.write( writeLine( row ) );
            } catch ( Throwable e ) {
                throw new IllegalRowException( "Failed to write row.", e );
            }
        }
    }

    public String writeLine( Row row ) throws IllegalRowException, IllegalColumnException, IllegalFieldException {
        final List<ColumnDefinition> columnDefinitions = documentDefinition.getColumnDefinitions();
        final boolean hasColumnDelimiter = documentDefinition.hasColumnDelimiter();
        StringBuilder sb = new StringBuilder( 512 );

        // TODO is the order an issue ?
        for ( Iterator<ColumnDefinition> iterator = columnDefinitions.iterator(); iterator.hasNext(); ) {
            final ColumnDefinition columnDefinition = iterator.next();

            if ( hasColumnDelimiter ) {
                sb.append( documentDefinition.getColumnDelimiter() );
            }

            final Collection<Field> fields = row.getFields( columnDefinition );
            if ( fields == null ) {
                // TODO do we want to give more context about the row?
                final IllegalRowException illegalRowException = new IllegalRowException( "Could not find column " + columnDefinition.getKey() );
                illegalRowException.setColumnDefinition( columnDefinition );
                throw illegalRowException;
            }

            if( fields.size() > 1 && ! columnDefinition.hasFieldSeparator() ) {
                throw new IllegalColumnException( "The column definition ("+ columnDefinition.getKey() +") doesn't " +
                                                  "have a field separator, yet the column has "+ fields.size() +" fields" );
            }

            final FieldFormatter fieldFormatter = columnDefinition.getFieldFormatter();
            for ( Iterator<Field> fieldIterator = fields.iterator(); fieldIterator.hasNext(); ) {
                Field field = fieldIterator.next();
                final String fieldStr = fieldFormatter.format( field );

                if ( columnDefinition.hasFieldDelimiter() ) {
                    final String fieldDelimiter = columnDefinition.getFieldDelimiter();
                    sb.append( fieldDelimiter ).append( fieldStr ).append( fieldDelimiter );
                } else {
                    sb.append( fieldStr );
                }

                if ( fieldIterator.hasNext() ) {
                    sb.append( columnDefinition.getFieldSeparator() );
                }
            }

            if ( hasColumnDelimiter ) {
                sb.append( documentDefinition.getColumnDelimiter() );
            }

            if ( iterator.hasNext() ) {
                sb.append( documentDefinition.getColumnSeparator() );
            }
        }

        sb.append( NEW_LINE );

        return sb.toString();
    }

    public void write( OutputStream os, Collection<Row> rows ) throws IOException, IllegalRowException {
        write( new OutputStreamWriter( os ), rows );
    }
}
