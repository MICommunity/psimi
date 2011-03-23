package org.hupo.psi.calimocho.util;

import org.apache.commons.beanutils.BeanUtils;
import org.hupo.psi.calimocho.model.Field;

import java.lang.reflect.InvocationTargetException;

/**
 * Utilities for fields.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class FieldUtils {

    /**
     * Populates the bean properties by using the key-value pairs of the Field.
     * This method will try to set a value to the bean properties with
     * a name that matches the key name. If a property is not present
     * on the bean it will just be ignored
     *
     * @param field The field with the key-value pairs
     * @param bean The bean to be populated.
     * @throws IllegalAccessException thrown if there is a problem accessing the fields of the bean
     */
    public static void populateBean(Field field, Object bean) throws InvocationTargetException, IllegalAccessException {
        BeanUtils.populate( bean, field.getEntries() );
    }

}
