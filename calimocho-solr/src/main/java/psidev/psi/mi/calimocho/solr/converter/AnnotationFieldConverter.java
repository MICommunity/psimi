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

    public void indexFieldValues(Field field, SolrFieldName fName, SolrInputDocument doc, boolean storeOnly, Set<String> uniques) {

        String name = field.get(CalimochoKeys.NAME);
        String value = field.get(CalimochoKeys.VALUE);
        String nameField = fName.toString();

        if (name != null){
            if (!uniques.contains(name)) {
                doc.addField(nameField, name);
                uniques.add(name);
            }
            if (!storeOnly) {
                if (value != null && !value.isEmpty() && !uniques.contains(name+":"+value)) {
                    doc.addField(nameField+"_s", name+":"+value);
                    uniques.add(name+":"+value);
                }
                else if ((value == null || value.isEmpty()) && !uniques.contains(name+"_s")) {
                    doc.addField(nameField+"_s", name);
                    uniques.add(name+"_s");
                }
            }
        }
        if (value != null && !uniques.contains(value)){
            doc.addField(nameField, value);
            uniques.add(value);
        }
    }

}
