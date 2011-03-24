package org.hupo.psi.calimocho.model;

import java.util.Map;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public interface Field {

    Map<String,String> getEntries();

    boolean set( String key, String value );

    boolean containsKey(String key);

    String get(String key);

    Integer getInteger(String key);

    void set( Map<String,String> keyValuePairs );

    void setIfMissing( Map<String,String> keyValuePairs );
}
