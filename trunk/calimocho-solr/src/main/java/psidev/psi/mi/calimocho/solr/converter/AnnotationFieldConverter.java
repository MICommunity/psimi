package psidev.psi.mi.calimocho.solr.converter;

import java.util.Set;
import org.apache.solr.common.SolrInputDocument;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;

/**
 *
 * @author kbreuer
 */
public class AnnotationFieldConverter implements SolrFieldConverter {

    public void indexFieldValues(Field field, String formattedField, SolrFieldName fName, SolrInputDocument doc, boolean stored, Set<String> uniques) {

        String name = field.get(CalimochoKeys.NAME);
        String value = field.get(CalimochoKeys.VALUE);
        String nameField = fName.toString();

        if (!uniques.contains("_o"+formattedField) && stored && formattedField != null && !formattedField.isEmpty()) {
            doc.addField(nameField+"_o", formattedField);
            uniques.add("_o"+formattedField);
        }

        if (name != null){
            if (!uniques.contains(name)) {
                doc.addField(nameField, name);
                uniques.add(name);
            }
            if (stored && value != null && !uniques.contains(name+":"+value)) {
                doc.addField(nameField+"_s", name+":"+value);
                uniques.add(name+":"+value);
            }
        }
        if (value != null && !uniques.contains(value)){
            doc.addField(nameField, value);
            uniques.add(value);
        }
    }

}
