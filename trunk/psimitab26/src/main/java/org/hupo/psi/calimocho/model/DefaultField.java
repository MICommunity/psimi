package org.hupo.psi.calimocho.model;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
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
}
