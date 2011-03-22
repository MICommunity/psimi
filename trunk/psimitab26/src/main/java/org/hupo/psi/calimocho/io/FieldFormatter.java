package org.hupo.psi.calimocho.io;

import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.model.Row;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public interface FieldFormatter {

    String format(Field field);

    String format(Field field, Row row);

}
