package org.hupo.psi.calimocho.util;

import org.apache.commons.beanutils.BeanUtils;
import org.hupo.psi.calimocho.model.Field;

/**
 * Utilities for fields.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class FieldUtils {

    public static void populateBean(Field field, Object bean) throws Exception {
        BeanUtils.populate( bean, field.getEntries() );
    }

}
