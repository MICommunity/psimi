package org.hupo.psi.tab.util;

import org.hupo.psi.calimocho.model.ColumnDefinition;
import org.hupo.psi.calimocho.model.DocumentDefinition;
import org.hupo.psi.calimocho.model.DocumentDefinitionBuilder;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class MitabDocumentDefinitionFactory {

    private MitabDocumentDefinitionFactory() {}

    public static DocumentDefinition mitab25() {
        ColumnDefinition idACol = new ColumnDefinitionBuilder()
                .setKey("idA")
                .setFieldSeparator("|")
                .build();

        ColumnDefinition idBCol = new ColumnDefinitionBuilder()
                .extendColumnDefinition(idA)
                .setKey("idB")
                .build();

        DocumentDefinition docDefinition = new DocumentDefinitionBuilder()
                .addColumnDefinition( idACol )
                .addColumnDefinition( idBCol )
                .setColumnSeparator( "\t" )
                .setCommentPrefix( "#" )
                .build();

        return docDefinition;
    }

    public static DocumentDefinition mitab26() {
        DocumentDefinition docDefinition = new DocumentDefinitionBuilder()
                .extendDocumentDefinition( mitab25() )
                .addColumnDefinition( mitab26Col1 )
                .addColumnDefinition( mitab26Col2 )
                .build();

        return docDefinition;
    }

}
