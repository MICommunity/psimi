package org.hupo.psi.calimocho.model;

import org.hupo.psi.calimocho.io.FieldFormatter;
import org.hupo.psi.calimocho.io.FieldParser;

import java.util.Map;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public interface ColumnDefinition extends Defined, Comparable<ColumnDefinition> {

    Integer getPosition();

    String getKey();

    boolean isAllowsEmpty();

    String getEmptyValue();

    FieldParser getFieldParser();

    FieldFormatter getFieldFormatter();

    String getFieldSeparator();

    String getFieldDelimiter();

    boolean hasFieldDelimiter();

    boolean hasFieldSeparator();

    Map<String, String> getDefaultValues();

    void addDefaultValue(String key, String value);

    String getDefaultValue(String key);

    int compareTo( ColumnDefinition columnDefinition );
}
