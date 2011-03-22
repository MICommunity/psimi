package org.hupo.psi.calimocho.model;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public interface DocumentDefinition extends Defined {

    String getColumnSeparator();

    String getColumnDelimiter();

    String getCommentPrefix();

    ColumnDefinition getColumnByPosition( int position );

    boolean isPartial();
}
