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

public class TextFieldConverter implements SolrFieldConverter{

    public void indexFieldValues(Field field, SolrFieldName name, SolrInputDocument doc, boolean storeOnly, Set<String> uniques) {

        String db = field.get(CalimochoKeys.DB);
        String value = field.get(CalimochoKeys.VALUE);
        String text = field.get(CalimochoKeys.TEXT);
        String nameField = name.toString();

        if (db != null && !uniques.contains(db)){
            doc.addField(nameField, db);
            uniques.add(db);
        }
        if (value != null && !uniques.contains(value)){
            doc.addField(nameField, value);
            uniques.add(value);
        }
        if (db != null && value != null && !uniques.contains(db+":"+value)) {
            doc.addField(nameField, db+":"+value);
            if (!storeOnly){
                doc.addField(nameField+"_s", db+":"+value);
            }
            uniques.add(db+":"+value);
        }
        if (text != null && !uniques.contains(text)){
            doc.addField(nameField, text);
            if (!storeOnly){
                doc.addField(nameField+"_s", text);
            }
            uniques.add(text);
        }
    }
}
