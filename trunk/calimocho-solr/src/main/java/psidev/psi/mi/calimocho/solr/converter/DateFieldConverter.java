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

    public void indexFieldValues(Field field, SolrFieldName name, SolrInputDocument doc, boolean storeOnly, Set<String> uniques) {

        String year = field.get(CalimochoKeys.YEAR);
        String month = field.get(CalimochoKeys.MONTH);
        String day = field.get(CalimochoKeys.DAY);
        String nameField = name.toString();

        if (year != null && month != null && day != null){
            String formattedDate = "";
            try {
                SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMdd");
                formattedDate = simpleFormat.format(simpleFormat.parse(year+"/"+month+"/"+day));
                if (!formattedDate.isEmpty() && !uniques.contains(formattedDate)) {
                    doc.addField(nameField, Integer.parseInt((simpleFormat.parse(year+month+day)).toString())); //int representation of date
                    if (!storeOnly) {
                        doc.addField(nameField+"_s", formattedDate);
                    }
                    uniques.add(formattedDate);
                }
            } catch (Exception e) {
                //log?
                e.printStackTrace(System.err);
                System.err.print("Error when trying to create date format yyyy/MM/dd from year "+ year+" month "+month+" day "+day+"!");
            }

        }
    }

}
