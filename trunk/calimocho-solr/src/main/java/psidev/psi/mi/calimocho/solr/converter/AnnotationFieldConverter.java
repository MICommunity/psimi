package psidev.psi.mi.calimocho.solr.converter;

import org.apache.solr.common.SolrInputDocument;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;

/**
 *
 * @author kbreuer
 */
public class AnnotationFieldConverter implements SolrFieldConverter {

    public void indexFieldValues(Field field, String formattedField, SolrFieldName fName, SolrInputDocument doc, boolean stored) {

        String name = field.get(CalimochoKeys.NAME);
        String value = field.get(CalimochoKeys.VALUE);
        String nameField = fName.toString();

        if (stored && formattedField != null && !formattedField.isEmpty()) {
            doc.addField(nameField+"_s", formattedField);
        }

        if (name != null){
            doc.addField(nameField, name);
            if (stored) {
                doc.addField(nameField+"_s", name);
            }
        }
        if (value != null){
            doc.addField(nameField, value);
            if (stored) {
                doc.addField(nameField+"_s", value);
            }
        }
    }

}
