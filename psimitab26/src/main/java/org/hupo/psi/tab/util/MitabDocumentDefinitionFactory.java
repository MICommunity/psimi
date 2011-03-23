package org.hupo.psi.tab.util;

import org.hupo.psi.calimocho.model.ColumnDefinition;
import org.hupo.psi.calimocho.model.ColumnDefinitionBuilder;
import org.hupo.psi.calimocho.model.DocumentDefinition;
import org.hupo.psi.calimocho.model.DocumentDefinitionBuilder;
import org.hupo.psi.calimocho.parser.LiteralFieldFormatter;
import org.hupo.psi.calimocho.parser.LiteralFieldParser;
import org.hupo.psi.tab.parser.XrefFieldFormatter;
import org.hupo.psi.tab.parser.XrefFieldParser;

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
        XrefFieldParser xrefParser = new XrefFieldParser();
        XrefFieldFormatter xrefFormatter = new XrefFieldFormatter();

        LiteralFieldParser literalParser = new LiteralFieldParser();
        LiteralFieldFormatter literalFieldFormatter = new LiteralFieldFormatter();

        ColumnDefinition idACol = new ColumnDefinitionBuilder()
                .setKey("idA")
                .setFieldSeparator("|")
                .setEmptyValue("-")
                .setFieldDelimiter("")
                .setIsAllowsEmpty(false)
                .setPosition(0)
                .setFieldFormatter(xrefFormatter)
                .setFieldParser(xrefParser)
                .build();

        ColumnDefinition idBCol = new ColumnDefinitionBuilder()
                .extendColumnDefinition(idACol)
                .setKey("idB")
                .setPosition(1)
                .build();

        ColumnDefinition altidA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(idACol)
                .setKey("altidA")
                .setPosition(2)
                .setIsAllowsEmpty(true)
                .build();

        ColumnDefinition altidB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setKey("altidB")
                .setPosition(3)
                .build();

        ColumnDefinition aliasA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setKey("aliasA")
                .setPosition(4)
                .build();

        ColumnDefinition aliasB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setKey("aliasB")
                .setPosition(5)
                .build();

        ColumnDefinition detmethod_exact = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setKey("detmethod_exact")
                .setPosition(6)
                .build();

        ColumnDefinition pubauth = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setKey("pubauth")
                .setPosition(7)
                .setFieldFormatter(literalFieldFormatter)
                .setFieldParser(literalParser)
                .build();

        ColumnDefinition pubid = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setKey("pubid")
                .setPosition(8)
                .build();

        ColumnDefinition taxidA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setKey("taxidA")
                .setPosition(9)
                .build();

        ColumnDefinition taxidB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setKey("taxidB")
                .setPosition(10)
                .build();

        ColumnDefinition type_exact = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setKey("type_exact")
                .setPosition(11)
                .build();

        ColumnDefinition source = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setKey("source")
                .setPosition(12)
                .build();

        ColumnDefinition interaction_id = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setKey("interaction_id")
                .setPosition(13)
                .build();

        ColumnDefinition confidence = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setKey("confidence")
                .setPosition(14)
                .build();

        DocumentDefinition docDefinition = new DocumentDefinitionBuilder()
                .addColumnDefinition( idACol )
                .addColumnDefinition( idBCol )
                .addColumnDefinition(altidA)
                .addColumnDefinition(altidB)
                .addColumnDefinition(aliasA)
                .addColumnDefinition(aliasB)
                .addColumnDefinition(detmethod_exact)
                .addColumnDefinition(pubauth)
                .addColumnDefinition(pubid)
                .addColumnDefinition(taxidA)
                .addColumnDefinition(taxidB)
                .addColumnDefinition(type_exact)
                .addColumnDefinition(source)
                .addColumnDefinition(interaction_id)
                .addColumnDefinition(confidence)
                .setColumnSeparator( "\t" )
                .setCommentPrefix( "#" )
                .build();

        return docDefinition;
    }

    public static DocumentDefinition mitab26() {
        DocumentDefinition docDefinition = new DocumentDefinitionBuilder()
                .extendDocumentDefinition( mitab25() )
                .build();

        return docDefinition;
    }

}
