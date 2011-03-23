package org.hupo.psi.calimocho.io;

import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;

/**
 * Creates a formatted String for a Field object.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public interface FieldFormatter {

    String format(Field field) throws IllegalFieldException;

    String format(Field field, Row row) throws IllegalFieldException;

}
