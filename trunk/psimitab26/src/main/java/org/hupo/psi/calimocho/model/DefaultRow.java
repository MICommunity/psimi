package org.hupo.psi.calimocho.model;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class DefaultRow implements Row {

    private Multimap<String,Field> fieldMultimap;

    public DefaultRow() {
        // we would like the fields to be sorted in the order they
        // are added to the map
        this.fieldMultimap = LinkedHashMultimap.create();
    }

    public boolean addField(String columnKey, Field field) {
        setFieldColumnKey( columnKey, field );
        return fieldMultimap.put( columnKey, field );
    }

    public boolean addFields( String columnKey, Collection<Field> fields ) {
        for (Field field : fields) {
            setFieldColumnKey( columnKey, field );
        }


        return fieldMultimap.putAll( columnKey, fields );
    }

    private void setFieldColumnKey( String columnKey, Field field ) {
        field.getEntries().put(Field.COLUMNKEY_KEY, columnKey);
    }

    public Collection<Field> getFields( String columnKey ) {
        return fieldMultimap.get( columnKey );
    }

    public Collection<Field> getFields( ColumnDefinition columnDefinition ) {
        return fieldMultimap.get( columnDefinition.getKey() );
    }

    public Collection<Field> getAllFields() {
        return fieldMultimap.values();
    }
}
