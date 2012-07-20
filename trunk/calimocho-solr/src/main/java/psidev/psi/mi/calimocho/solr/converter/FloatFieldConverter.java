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

    public void indexFieldValues(Field field, SolrFieldName name, SolrInputDocument doc, Set<String> uniques) {

        String value = field.get(CalimochoKeys.VALUE);
        String nameField = name.toString();

        if (value != null && !uniques.contains(value)){
            doc.addField(nameField, value);
            doc.addField(nameField+"_s", value);
            uniques.add(value);
        }
    }
}
