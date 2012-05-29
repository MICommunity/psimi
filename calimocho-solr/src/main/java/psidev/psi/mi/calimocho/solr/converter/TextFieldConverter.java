package psidev.psi.mi.calimocho.solr.converter;

import org.apache.solr.common.SolrInputDocument;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/05/12</pre>
 */

public class TextFieldConverter implements SolrFieldConverter{

    public void indexFieldValues(Field field, SolrFieldName name, SolrInputDocument doc) {

        String db = field.get(CalimochoKeys.DB);
        String value = field.get(CalimochoKeys.VALUE);
        String text = field.get(CalimochoKeys.TEXT);
        String nameField = name.toString();

        if (db != null){
            doc.addField(nameField, db);
            if (value != null){
                doc.addField(nameField, db+":"+value);
            }
        }
        if (value != null){
            doc.addField(nameField, value);
        }
        if (text != null){
            doc.addField(nameField, text);
        }
    }
}
