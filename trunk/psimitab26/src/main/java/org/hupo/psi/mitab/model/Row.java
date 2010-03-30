/**
 * Copyright 2010 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hupo.psi.mitab.model;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class Row {

    private Multimap<String,Field> fieldsByColumnKey;

    public Row(List<Field> fields) {
        fieldsByColumnKey = HashMultimap.create();

        for (Field field : fields) {
            addField(field);
        }
    }

    public void addField(Field field) {
        if (field == null) {
            throw new NullPointerException("null field");
        }

       fieldsByColumnKey.put(field.getColumnKey(), field);
    }

    public Collection<Field> getFieldsByColumnKey(String columnKey) {
        if (fieldsByColumnKey.containsKey(columnKey)) {
            return fieldsByColumnKey.get(columnKey);
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    public Set<String> getColumnKeys() {
        return fieldsByColumnKey.keySet();
    }
}
