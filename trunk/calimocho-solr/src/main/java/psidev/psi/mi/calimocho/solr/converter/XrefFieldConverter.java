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

public class XrefFieldConverter implements SolrFieldConverter{
    
    public void indexFieldValues(Field field, String formattedField, SolrFieldName name, SolrInputDocument doc, boolean stored, Set<String> uniques) {
        
        String db = field.get(CalimochoKeys.DB);
        String value = field.get(CalimochoKeys.VALUE);
        String nameField = name.toString();
        
        if (!uniques.contains(formattedField) && stored && formattedField != null && !formattedField.isEmpty()) {
            doc.addField(nameField+"_o", formattedField);
            uniques.add(formattedField);
        }

        if (db != null && !uniques.contains(db)){
            doc.addField(nameField, db);
            if (stored){
                doc.addField(nameField+"_s", db);
            }
            uniques.add(db);
        }
        if (value != null && !uniques.contains(value)){
            doc.addField(nameField, value);
            if (stored){
                doc.addField(nameField+"_s", value);
            }
            uniques.add(value);
        }
        if (db != null && value != null && !uniques.contains(db+":"+value)) {
            doc.addField(nameField, db+":"+value);
            if (stored){
                doc.addField(nameField+"_s", db+":"+value);
            }
            uniques.add(db+":"+value);
        }
    }
}
