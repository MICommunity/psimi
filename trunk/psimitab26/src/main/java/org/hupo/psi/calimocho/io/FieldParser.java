package org.hupo.psi.calimocho.io;

import org.hupo.psi.calimocho.model.ColumnDefinition;
import org.hupo.psi.calimocho.model.Field;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public interface FieldParser {

    Field parse(String fieldStr, ColumnDefinition columnDefinition) throws IllegalFieldException;

}
