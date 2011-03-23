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

    String COLUMNKEY_KEY = "column_key";

    Map<String,String> getEntries();

    boolean set( String key, String value );

    boolean containsKey(String key);

    String get(String key);

}
