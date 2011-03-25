package org.hupo.psi.calimocho.model;

import java.util.List;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public interface CalimochoDocument {

    List<Row> getRows();

    void setRows(List<Row> rows);

}
