package psidev.psi.mi.calimocho.solr.converter;

import java.util.Set;
import org.apache.solr.common.SolrInputDocument;
import org.hupo.psi.calimocho.model.Field;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/05/12</pre>
 */

public interface SolrFieldConverter {
    
    public void indexFieldValues(Field field, String formattedField, SolrFieldName name, SolrInputDocument doc, boolean stored, Set<String> uniques);
}
