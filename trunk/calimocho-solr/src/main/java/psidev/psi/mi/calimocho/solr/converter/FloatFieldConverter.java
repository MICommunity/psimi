package psidev.psi.mi.calimocho.solr.converter;

import java.util.Set;
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

public class FloatFieldConverter implements SolrFieldConverter{

    public void indexFieldValues(Field field, String formattedField, SolrFieldName name, SolrInputDocument doc, boolean stored, Set<String> uniques) {

        String value = field.get(CalimochoKeys.VALUE);
        String nameField = name.toString();

        if (!uniques.contains(formattedField) && stored && formattedField != null && !formattedField.isEmpty()) {
            doc.addField(nameField+"_o", formattedField);
            uniques.add(formattedField);
        }

        if (value != null && !uniques.contains(value)){
            doc.addField(nameField, value);
            if (stored){
                doc.addField(nameField+"_s", value);
            }
            uniques.add(value);
        }
    }
}
