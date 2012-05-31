package psidev.psi.mi.calimocho.solr.converter;

import java.text.SimpleDateFormat;
import java.util.Set;
import org.apache.solr.common.SolrInputDocument;
import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;

/**
 *
 * @author kbreuer
 */
public class DateFieldConverter implements SolrFieldConverter {

    public void indexFieldValues(Field field, String formattedField, SolrFieldName name, SolrInputDocument doc, boolean stored, Set<String> uniques) {

        String year = field.get(CalimochoKeys.YEAR);
        String month = field.get(CalimochoKeys.MONTH);
        String day = field.get(CalimochoKeys.DAY);
        String nameField = name.toString();

        if (!uniques.contains(formattedField) && stored && formattedField != null && !formattedField.isEmpty()) {
            doc.addField(nameField+"_o", formattedField);
            uniques.add(formattedField);
        }

        if (year != null && month != null && day != null){
            String formattedDate = "";
            try {
                SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMdd");
                formattedDate = simpleFormat.format(simpleFormat.parse(year+"/"+month+"/"+day));
            } catch (Exception e) {}
            if (!formattedDate.isEmpty() && !uniques.contains(formattedDate)) {
                doc.addField(nameField, formattedDate);
                if (stored) {
                    doc.addField(nameField+"_s", formattedDate);
                }
                uniques.add(formattedDate);
            }
        }
    }

}
