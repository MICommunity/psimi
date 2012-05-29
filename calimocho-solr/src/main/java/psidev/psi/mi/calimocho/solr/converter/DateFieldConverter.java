package psidev.psi.mi.calimocho.solr.converter;

import org.apache.solr.common.SolrInputDocument;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;

/**
 *
 * @author kbreuer
 */
public class DateFieldConverter implements SolrFieldConverter {

    public void indexFieldValues(Field field, SolrFieldName name, SolrInputDocument doc) {

        String year = field.get(CalimochoKeys.YEAR);
        String month = field.get(CalimochoKeys.MONTH);
        String day = field.get(CalimochoKeys.DAY);
        String nameField = name.toString();

        if (year != null && month != null && day != null){
            //TODO check format: YYYYMMDD
            doc.addField(nameField, year+month+day);
        }
    }

}
