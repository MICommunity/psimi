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
    private boolean stored;
    private boolean facet;

    public SolrFieldUnit(Collection<String> rowKeys, SolrFieldConverter converter, FieldFormatter formatter, boolean stored, boolean facet){
        this.rowKeys = rowKeys;
        this.converter = converter;
        this.formatter = formatter;
        this.stored = stored;
        this.facet = facet;
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

    public boolean isStored() {
        return stored;
    }

    public boolean isFacet() {
        return facet;
    }
}
