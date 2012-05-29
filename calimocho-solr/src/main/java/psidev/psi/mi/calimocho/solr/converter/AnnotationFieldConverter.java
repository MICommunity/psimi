package psidev.psi.mi.calimocho.solr.converter;

import org.apache.solr.common.SolrInputDocument;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;

/**
 *
 * @author kbreuer
 */
public class AnnotationFieldConverter implements SolrFieldConverter {

    public void indexFieldValues(Field field, SolrFieldName fName, SolrInputDocument doc) {

        String name = field.get(CalimochoKeys.NAME);
        String value = field.get(CalimochoKeys.VALUE);
        String nameField = fName.toString();

        if (name != null){
            doc.addField(nameField, name);
        }
        if (value != null){
            doc.addField(nameField, value);
        }
    }

}
