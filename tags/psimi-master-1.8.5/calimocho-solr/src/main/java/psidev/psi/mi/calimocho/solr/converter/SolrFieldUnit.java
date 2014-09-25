package psidev.psi.mi.calimocho.solr.converter;

import java.util.Collection;
import org.hupo.psi.calimocho.tab.io.FieldFormatter;

/**
 * field unit
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/05/12</pre>
 */

public class SolrFieldUnit {
    
    private Collection<String> rowKeys;
    private SolrFieldConverter converter;
    private FieldFormatter formatter;
    private boolean storeOnly; //fn_o VS fn + fn_s + fn_o (fields in MIQL2.7)

    public SolrFieldUnit(Collection<String> rowKeys, SolrFieldConverter converter, FieldFormatter formatter, boolean storeOnly){
        this.rowKeys = rowKeys;
        this.converter = converter;
        this.formatter = formatter;
        this.storeOnly = storeOnly;
    }

    public SolrFieldUnit(Collection<String> rowKeys, FieldFormatter formatter, boolean storeOnly){
        this.rowKeys = rowKeys;
        this.converter = null;
        this.formatter = formatter;
        this.storeOnly = storeOnly;
    }

    public Collection<String> getRowKeys() {
        return rowKeys;
    }

    public void setRowKeys(Collection<String> rowKeys) {
        this.rowKeys = rowKeys;
    }

    public SolrFieldConverter getConverter() {
        return converter;
    }

    public void setConverter(SolrFieldConverter converter) {
        this.converter = converter;
    }

    public FieldFormatter getFormatter() {
        return this.formatter;
    }

    public boolean isStoreOnly() {
        return storeOnly;
    }
}
