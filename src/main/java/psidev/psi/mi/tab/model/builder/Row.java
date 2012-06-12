/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model.builder;

import java.util.*;

/**
 * Simple model for representing data in columns.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Feb-2007</pre>
 */
public class Row {

    public static final char COLUMN_DELIMITER = '\t';

    private List<Column> columns = new ArrayList<Column>();

    public Row() {
    }

    public Row( List<Column> columns ) {
        this.columns = columns;
    }

    /**
     * The column is added at the end of the Collection.
     *
     * @param column
     */
    public void appendColumn( Column column ) {
        columns.add( column );
    }

    /**
     * Count of column currently in the row,
     * @return
     */
    public int getColumnCount() {
        return columns.size();
    }

    /**
     * Retreive a column by index.
     * @param index the index of the object ( range: 0 .. getColumnCount() - 1 ).
     * @return
     */
    public Column getColumnByIndex( int index ) {
        return columns.get( index );
    }

    /**
     * Gives an unmodifiable iterator on the ordered columns in the row.
     * @return
     */
    public Iterator<Column> iterator() {
        return Collections.unmodifiableList( columns ).iterator();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder( 8192 );
        for ( Iterator<Column> columnIterator = columns.iterator(); columnIterator.hasNext(); ) {
            Column column = columnIterator.next();

            if (column != null) {
                sb.append( column.toString() );
            } else {
                sb.append(Column.EMPTY_COLUMN);
            }

            if( columnIterator.hasNext() ) {
                sb.append( COLUMN_DELIMITER );
            }
        }
        return sb.toString();
    }
}