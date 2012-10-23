package org.hupo.psi.calimocho.model;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class DefaultField implements Field {

    private Map<String,String> entries;

    public DefaultField() {
        this.entries = Maps.newHashMap();
    }

    public Map<String, String> getEntries() {
        return entries;
    }

    public boolean set( String key, String value ) {
        final String putValue = entries.put( key, value );

        return (putValue != null);
    }

    public boolean containsKey( String key ) {
        return entries.containsKey( key );
    }

    public String get( String key ) {
        return entries.get( key );
    }

    public Integer getInteger( String key ) {
        String value = entries.get( key );

        if (value == null) {
            return null;
        }

        return Integer.parseInt( value );
    }

    public void set( Map<String,String> keyValuePairs ) {
        entries.putAll( keyValuePairs );
    }

    public void setIfMissing( Map<String, String> keyValuePairs ) {
        Map<String,String> missingPairs = new HashMap<String, String>(keyValuePairs);

        for (String key : entries.keySet()) {
            missingPairs.remove( key );
        }

        entries.putAll( missingPairs );
    }

    @Override
    public String toString() {
        return "DefaultField{" +
               "entries=" + entries +
               '}';
    }
}
