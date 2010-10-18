package org.hupo.psi.mitab.definition;

import org.hupo.psi.mitab.model.ColumnMetadata;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public interface DocumentDefinition {

    ColumnMetadata[] getColumns();

    String getColumnSeparator();

    String getFieldSeparator();

    String getColumnDelimiter();

    String getEmptyColumnValue();

    String getCommentedLineStart();
}
