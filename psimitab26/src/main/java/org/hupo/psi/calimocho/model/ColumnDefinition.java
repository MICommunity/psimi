package org.hupo.psi.calimocho.model;

import org.hupo.psi.calimocho.io.FieldFormatter;
import org.hupo.psi.calimocho.io.FieldParser;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public interface ColumnDefinition {

    int getPosition();

    String getKey();

    boolean isAllowsEmpty();

    String getEmptyValue();

    FieldParser getFieldParser();

    FieldFormatter getFieldFormatter();

    String getFieldSeparator();

    String getFieldDelimiter();
}
