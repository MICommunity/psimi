package psidev.psi.mi.calimocho.solr.converter;

import java.util.Set;
import org.apache.solr.common.SolrInputDocument;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;


/**
 *
 * @author kbreuer
 */
public class BooleanFieldConverter implements SolrFieldConverter {

    public void indexFieldValues(Field field, String formattedField, SolrFieldName name, SolrInputDocument doc, boolean stored, Set<String> uniques) {

        String db = field.get(CalimochoKeys.DB);
        String value = field.get(CalimochoKeys.VALUE);
        String text = field.get(CalimochoKeys.TEXT);
        String nameField = name.toString();

        if (!uniques.contains("_o"+formattedField) && stored && formattedField != null && !formattedField.isEmpty()) {
            doc.addField(nameField+"_o", formattedField);
            uniques.add("_o"+formattedField);
        }

        if ((db == null || db.isEmpty()) && (value == null || value.isEmpty()) && (text == null || text.isEmpty())){
            if (!uniques.contains("false")) {
                doc.addField(nameField, "false");
                if (stored) {
                    doc.addField(nameField+"_s", "false");
                }
                uniques.add("false");
            }
        }
        else {
            if (!uniques.contains("true")) {
                doc.addField(nameField, "true");
                if (stored) {
                    doc.addField(nameField+"_s", "true");
                }
                uniques.add("true");
            }
        }

    }

}
