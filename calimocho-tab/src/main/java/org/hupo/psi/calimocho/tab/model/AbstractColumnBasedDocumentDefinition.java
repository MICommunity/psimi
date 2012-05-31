package org.hupo.psi.calimocho.tab.model;

import org.hupo.psi.calimocho.io.IllegalRowException;
import org.hupo.psi.calimocho.model.AbstractDefined;
import org.hupo.psi.calimocho.model.CalimochoDocument;
import org.hupo.psi.calimocho.model.DefaultCalimochoDocument;
import org.hupo.psi.calimocho.model.Row;
import org.hupo.psi.calimocho.tab.io.DefaultRowReader;
import org.hupo.psi.calimocho.tab.io.DefaultRowWriter;
import org.hupo.psi.calimocho.tab.io.RowReader;
import org.hupo.psi.calimocho.tab.io.RowWriter;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class AbstractColumnBasedDocumentDefinition extends AbstractDefined implements ColumnBasedDocumentDefinition {

    private List<ColumnDefinition> columns;
    private String name;
    private String definition;
    private String columnSeparator;
    private String columnDelimiter;
    private String commentPrefix;
    private String emptyValue;
    private boolean partial;

    public CalimochoDocument readDocument( Reader reader ) throws IOException, IllegalRowException {
        CalimochoDocument calimochoDocument = new DefaultCalimochoDocument();

        RowReader rowReader = new DefaultRowReader( this );
        final List<Row> rows = rowReader.read( reader );

        calimochoDocument.setRows( rows );

        return calimochoDocument;
    }

    public void writeDocument( Writer writer, CalimochoDocument calimochoDocument ) throws IOException, IllegalRowException {
        RowWriter rowWriter = new DefaultRowWriter( this );
        rowWriter.write( writer, calimochoDocument.getRows() );
    }

    public List<ColumnDefinition> getColumns() {
        if (columns == null){
            columns = new ArrayList<ColumnDefinition>();
        }
        return columns;
    }

    public void setColumns( List<ColumnDefinition> columns ) {
        this.columns = new ArrayList<ColumnDefinition>( columns );
        Collections.sort( this.columns );
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition( String definition ) {
        this.definition = definition;
    }

    public String getColumnSeparator() {
        return columnSeparator;
    }

    public void setColumnSeparator( String columnSeparator ) {
        this.columnSeparator = columnSeparator;
    }

    public String getColumnDelimiter() {
        return columnDelimiter;
    }

    public void setColumnDelimiter( String columnDelimiter ) {
        this.columnDelimiter = columnDelimiter;
    }

    public String getCommentPrefix() {
        return commentPrefix;
    }

    public boolean isPartial() {
        return partial;
    }

    public List<ColumnDefinition> getColumnDefinitions() {
        return Collections.unmodifiableList( columns );
    }

    public boolean hasColumnDelimiter() {
        return columnDelimiter != null;
    }

    public void setPartial( boolean partial ) {
        this.partial = partial;
    }

    public ColumnDefinition getColumnByPosition( int position ) {
        // TODO this can be optimized
        for (ColumnDefinition columnDefinition : columns) {
            if (position == columnDefinition.getPosition()) {
                return columnDefinition;
            }
        }

        return null;
    }

    public ColumnDefinition getColumnByKey( String key ) {
        for (ColumnDefinition columnDefinition : columns) {
            if (columnDefinition.getKey().equals(key)) {
                return columnDefinition;
            }
        }

        return null;
    }

    public void setCommentPrefix( String commentPrefix ) {
        this.commentPrefix = commentPrefix;
    }

    /**
     * precondition: the <code>columns</code> is not null and ordered by increasing position.
     * @return the highest column's position.
     */
    public int getHighestColumnPosition() {
        if( columns == null ) {
            throw new RuntimeException( "No columns defined in this document definition" );
        }
        return columns.get( columns.size() - 1 ).getPosition();
    }

    public String getEmptyValue() {
        return emptyValue;
    }

    public void setEmptyValue(String emptyValue) {
        this.emptyValue = emptyValue;
    }
}
