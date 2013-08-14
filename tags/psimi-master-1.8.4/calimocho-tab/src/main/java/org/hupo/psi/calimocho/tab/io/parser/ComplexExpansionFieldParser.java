package org.hupo.psi.calimocho.tab.io.parser;

import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.calimocho.tab.model.ColumnDefinition;

/**
 * Parser of complex expansion for backward compatibility
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/12</pre>
 */

public class ComplexExpansionFieldParser extends XrefFieldParser{

    private final static String SPOKE = "spoke";
    private final static String MATRIX = "matrix";
    private final static String BIPARTITE = "bipartite";
    private final static String SPOKE_MI = "MI:1060";
    private final static String MATRIX_MI = "MI:1061";
    private final static String BIPARTITE_MI = "MI:1062";
    private final static String SPOKE_NAME = "spoke expansion";
    private final static String MATRIX_NAME = "matrix expansion";
    private final static String BIPARTITE_NAME = "bipartite expansion";
    @Override
    public Field parse( String str, ColumnDefinition columnDefinition ) throws IllegalFieldException {
        Field field = super.parse(str, columnDefinition);

        if ( SPOKE.equalsIgnoreCase(field.get(CalimochoKeys.DB))) {
            field.set(CalimochoKeys.DB, MI_DB);
            field.set(CalimochoKeys.VALUE, SPOKE_MI);
            field.set(CalimochoKeys.TEXT, SPOKE_NAME);
        }
        else if ( MATRIX.equalsIgnoreCase(field.get(CalimochoKeys.DB))) {
            field.set(CalimochoKeys.DB, MI_DB);
            field.set(CalimochoKeys.VALUE, MATRIX_MI);
            field.set(CalimochoKeys.TEXT, MATRIX_NAME);
        }
        else if ( BIPARTITE.equalsIgnoreCase(field.get(CalimochoKeys.DB))) {
            field.set(CalimochoKeys.DB, MI_DB);
            field.set(CalimochoKeys.VALUE, BIPARTITE_MI);
            field.set(CalimochoKeys.TEXT, BIPARTITE_NAME);
        }

        return field;
    }
}
