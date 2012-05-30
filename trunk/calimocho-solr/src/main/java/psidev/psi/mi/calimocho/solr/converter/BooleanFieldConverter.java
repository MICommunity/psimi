package psidev.psi.mi.calimocho.solr.converter;

import org.apache.solr.common.SolrInputDocument;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;


/**
 *
 * @author kbreuer
 */
public class BooleanFieldConverter implements SolrFieldConverter {

    public void indexFieldValues(Field field, String formattedField, SolrFieldName name, SolrInputDocument doc, boolean stored, boolean facet) {

        String db = field.get(CalimochoKeys.DB);
        String value = field.get(CalimochoKeys.VALUE);
        String text = field.get(CalimochoKeys.TEXT);
        String nameField = name.toString();

        if (stored && formattedField != null && !formattedField.isEmpty()) {
            doc.addField(nameField+"_s", formattedField);
        }

        if ((db == null || db.isEmpty()) && (value == null || value.isEmpty()) && (text == null || text.isEmpty())){
            doc.addField(nameField, "false");
            if (stored) {
                doc.addField(nameField+"_s", "false");
            }
            //facet?
        }
        else {
            doc.addField(nameField, "true");
            if (stored) {
                doc.addField(nameField+"_s", "true");
            }
            //facet?
        }

    }

}
