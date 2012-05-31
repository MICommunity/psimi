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

public class FloatFieldConverter implements SolrFieldConverter{

    public void indexFieldValues(Field field, String formattedField, SolrFieldName name, SolrInputDocument doc, boolean stored) {

        String value = field.get(CalimochoKeys.VALUE);
        String nameField = name.toString();

        if (stored && formattedField != null && !formattedField.isEmpty()) {
            doc.addField(nameField+"_s", formattedField);
        }

        if (value != null){
            doc.addField(nameField, value);
            if (stored){
                doc.addField(nameField+"_s", value);
            }
        }
    }
}
