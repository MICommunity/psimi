package org.hupo.psi.calimocho.tab.io.formatter;

import org.hupo.psi.calimocho.io.IllegalFieldException;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/05/12</pre>
 */

public class AnnotationFieldFormatter extends KeyValueFieldFormatter {

    public AnnotationFieldFormatter(){
        super();
    }
    
    public AnnotationFieldFormatter(String separator){
        super(separator);
    }
    
    @Override
    public String format(Field field) throws IllegalFieldException {
        String name = field.get( CalimochoKeys.NAME );
        String value = field.get( CalimochoKeys.VALUE );

        if (name == null) {
            if (getDefaultKey() == null) {
                throw new IllegalFieldException( "Expected topic name not found: "+ CalimochoKeys.NAME );
            } else {
                name = getDefaultKey();
            }
        }

        // we allow annotations without values
        if (value == null) {
            return name;
        }

        return name+getSeparator()+value;
    }
}
