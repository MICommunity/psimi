package org.hupo.psi.calimocho.io;

import org.hupo.psi.calimocho.model.ColumnBasedDocumentDefinition;
import org.hupo.psi.calimocho.model.ColumnDefinition;
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
 * Writes the rows in the format defined by the ColumnBasedDocumentDefinition.
 *
 * @author Christine Jandrasits (cjandras@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class DefaultRowWriter implements RowWriter {

    private static final String NEW_LINE = System.getProperty( "line.separator" );
    private ColumnBasedDocumentDefinition documentDefinition;

    public DefaultRowWriter( ColumnBasedDocumentDefinition documentDefinition ) {
        this.documentDefinition = documentDefinition;

    }

    /**
     * {@inheritDoc}
     *  // TODO check that there is a fieldSeparator when more than 1 field to be written.
     */
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

    // TODO write header as a commented line

    /**
     * {@inheritDoc}
     */
    public String writeLine( Row row ) throws IllegalRowException, IllegalColumnException, IllegalFieldException {
        final List<ColumnDefinition> columnDefinitions = documentDefinition.getColumnDefinitions();
        final boolean hasColumnDelimiter = documentDefinition.hasColumnDelimiter();
        StringBuilder sb = new StringBuilder( 512 );

        for ( Iterator<ColumnDefinition> iterator = columnDefinitions.iterator(); iterator.hasNext(); ) {
            final ColumnDefinition columnDefinition = iterator.next();

            // TODO assume the first column is position ZERO, write empty columns when no definition

            if ( hasColumnDelimiter ) {
                sb.append( documentDefinition.getColumnDelimiter() );
            }

            final Collection<Field> fields = row.getFields( columnDefinition );
            // TODO if column definition says can be empty, do not throw exception
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

                // add missing default values before formatting
                field.setIfMissing( columnDefinition.getDefaultValues() );

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

    /**
     * {@inheritDoc}
     */
    public void write( OutputStream os, Collection<Row> rows ) throws IOException, IllegalRowException {
        write( new OutputStreamWriter( os ), rows );
    }
}
