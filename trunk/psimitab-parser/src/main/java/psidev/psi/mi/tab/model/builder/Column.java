/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.model.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * TODO comment this
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30-Jan-2007</pre>
 */
public final class Column {

    public static final char FIELD_DELIMITER = '|';
    public static final char EMPTY_COLUMN = '-';

    private Collection<Field> fields;

    public Column() {
        this.fields = new ArrayList<Field>( );
    }

    public Column( Collection<Field> fields ) {
        if( fields == null ) {
            throw new NullPointerException( "You must give a non null collection of fields" );
        }
        this.fields = fields;
    }

    public Collection<Field> getFields() {
        return fields;
    }

    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Column column = ( Column ) o;

        if ( fields != null ? !fields.equals( column.fields ) : column.fields != null ) return false;

        return true;
    }

    public int hashCode() {
        return ( fields != null ? fields.hashCode() : 0 );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(256);

        for (Iterator<Field> fieldIterator = fields.iterator(); fieldIterator.hasNext();) {
            Field field = fieldIterator.next();

            if (field != null) {
                sb.append(field.toString());
            }

            if (fieldIterator.hasNext()) {
                sb.append(FIELD_DELIMITER);
            }
        }

        if (fields.isEmpty()) {
            sb.append(EMPTY_COLUMN);
        }

        return sb.toString();
    }
}