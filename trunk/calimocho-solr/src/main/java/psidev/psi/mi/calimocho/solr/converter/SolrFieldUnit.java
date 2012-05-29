package psidev.psi.mi.calimocho.solr.converter;

import java.util.Collection;

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

    public SolrFieldUnit(Collection<String> rowKeys, SolrFieldConverter converter){
        this.rowKeys = rowKeys;
        this.converter = converter;
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
}
