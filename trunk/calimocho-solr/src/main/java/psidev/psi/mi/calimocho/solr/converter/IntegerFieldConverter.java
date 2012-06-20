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

public class IntegerFieldConverter implements SolrFieldConverter {

    public void indexFieldValues(Field field, SolrFieldName name, SolrInputDocument doc, boolean storeOnly, Set<String> uniques) {

        String value = field.get(CalimochoKeys.VALUE);
        String nameField = name.toString();

        if (value != null && !value.isEmpty() && !uniques.contains("true")) {
            doc.addField(nameField, true);
            if (!storeOnly) {
                doc.addField(nameField + "_s", "true");
            }
            uniques.add("true");
        } else if ((value == null || value.isEmpty()) && !uniques.contains("false")) {
            doc.addField(nameField, false);
            if (!storeOnly) {
                doc.addField(nameField + "_s", "false");
            }
            uniques.add("false");
        }
    }
}
