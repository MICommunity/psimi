package org.hupo.psi.calimocho.util;

import junit.framework.Assert;
import org.hupo.psi.calimocho.model.CalimochoKeys;
import org.hupo.psi.calimocho.model.DefaultField;
import org.hupo.psi.calimocho.model.Field;
import org.hupo.psi.tab.io.FieldUtils;
import org.junit.Test;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class FieldUtilsTest {

    @Test
    public void populateBean() throws Exception {
        Field field = new DefaultField();
        field.set( CalimochoKeys.DB, "uniprotkb" );
        field.set( CalimochoKeys.VALUE, "P12345" );

        XrefField xref = new XrefField();

        FieldUtils.populateBean( field, xref );

        Assert.assertEquals( "uniprotkb", xref.getDb() );
        Assert.assertEquals( "P12345", xref.getValue() );
        Assert.assertNull( xref.getText() );
    }

    public class XrefField {
        private String db;
        private String value;
        private String text;

        private XrefField() {
        }

        public String getDb() {
            return db;
        }

        public void setDb( String db ) {
            this.db = db;
        }

        public String getValue() {
            return value;
        }

        public void setValue( String value ) {
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText( String text ) {
            this.text = text;
        }
    }
}
