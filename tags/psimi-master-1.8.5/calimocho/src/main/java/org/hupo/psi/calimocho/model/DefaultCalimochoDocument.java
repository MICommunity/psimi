package org.hupo.psi.calimocho.model;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class DefaultCalimochoDocument implements CalimochoDocument {

    private List<Row> rows;

    public DefaultCalimochoDocument() {
        rows = new ArrayList<Row>( );
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows( List<Row> rows ) {
        this.rows = rows;
    }
}
