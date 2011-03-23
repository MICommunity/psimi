package org.hupo.psi.calimocho.io;

import org.hupo.psi.calimocho.model.ColumnDefinition;
import org.hupo.psi.calimocho.model.Field;

/**
 * Parses a String with the objective of generating a Field object from it.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public interface FieldParser {

    /**
     * Parses a string, generating a Field object.
     * @param fieldStr the field in text format
     * @param columnDefinition a column definition may help to build the field in some cases
     * @return the Field built using the string
     * @throws IllegalFieldException thrown if there is a problem parsing the String
     */
    Field parse(String fieldStr, ColumnDefinition columnDefinition) throws IllegalFieldException;

}
