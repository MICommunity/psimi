package org.hupo.psi.calimocho.model;

import java.util.Collection;
import java.util.List;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public interface DocumentDefinition extends Defined {

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
