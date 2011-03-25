package org.hupo.psi.tab.model;

import org.hupo.psi.calimocho.model.DocumentDefinition;

import java.util.Collection;
import java.util.List;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public interface ColumnBasedDocumentDefinition extends DocumentDefinition {

    String getColumnSeparator();

    String getColumnDelimiter();

    String getCommentPrefix();

    ColumnDefinition getColumnByPosition( int position );

    Collection<ColumnDefinition> getColumns();

    boolean isPartial();

    List<ColumnDefinition> getColumnDefinitions();

    boolean hasColumnDelimiter();

    int getHighestColumnPosition();
}
