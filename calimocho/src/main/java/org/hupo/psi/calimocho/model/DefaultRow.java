package org.hupo.psi.calimocho.model;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        return fieldMultimap.put( columnKey, field );
    }

    public boolean addFields( String columnKey, Collection<Field> fields ) {
        return fieldMultimap.putAll( columnKey, fields );
    }

    public Collection<Field> getFields( String columnKey ) {
        return fieldMultimap.get( columnKey );
    }

    public Collection<Field> getFieldsByKey(String columnKey, String fieldKey) {
        List<Field> fields = new ArrayList<Field>();

        for (Field field : getFields(columnKey)) {
            if (fieldKey.equals(field.get(CalimochoKeys.KEY))) {
                fields.add(field);
            }
        }

        return fields;
    }

    public Collection<Field> getAllFields() {
        return fieldMultimap.values();
    }

    public Collection<String> keySet() {
        return fieldMultimap.keySet();
    }
}
