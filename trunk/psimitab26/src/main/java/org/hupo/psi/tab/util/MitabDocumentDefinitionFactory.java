package org.hupo.psi.tab.util;

import org.hupo.psi.calimocho.model.ColumnDefinition;
import org.hupo.psi.calimocho.model.ColumnDefinitionBuilder;
import org.hupo.psi.calimocho.model.DocumentDefinition;
import org.hupo.psi.calimocho.model.DocumentDefinitionBuilder;
import org.hupo.psi.calimocho.parser.LiteralFieldFormatter;
import org.hupo.psi.calimocho.parser.LiteralFieldParser;
import org.hupo.psi.tab.parser.XrefFieldFormatter;
import org.hupo.psi.tab.parser.XrefFieldParser;

import java.text.Collator;

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
                .setColumnSeparator("\t")
                .setCommentPrefix( "#" )
                .build();

        return docDefinition;
    }

    public static DocumentDefinition mitab26() {
        XrefFieldParser xrefParser = new XrefFieldParser();
        XrefFieldFormatter xrefFormatter = new XrefFieldFormatter();

        LiteralFieldParser literalParser = new LiteralFieldParser();
        LiteralFieldFormatter literalFieldFormatter = new LiteralFieldFormatter();

        ColumnDefinition expansion = new ColumnDefinitionBuilder()
                .setKey("expansion")
                .setFieldSeparator("|")
                .setEmptyValue("-")
                .setFieldDelimiter("")
                .setIsAllowsEmpty(true)
                .setPosition(15)
                .setFieldFormatter(xrefFormatter)
                .setFieldParser(xrefParser)
                .build();

        ColumnDefinition bioRoleA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("bioRoleA")
                .setPosition(16)
                .build();

        ColumnDefinition bioRoleB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("bioRoleB")
                .setPosition(17)
                .build();

        ColumnDefinition expRoleA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("expRoleA")
                .setPosition(18)
                .build();

        ColumnDefinition expRoleB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("expRoleB")
                .setPosition(19)
                .build();

        ColumnDefinition typeA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("typeA")
                .setPosition(20)
                .build();

        ColumnDefinition typeB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("typeB")
                .setPosition(21)
                .build();

        ColumnDefinition xrefsA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("xrefsA")
                .setPosition(22)
                .build();

        ColumnDefinition xrefsB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("xrefsB")
                .setPosition(23)
                .build();


        ColumnDefinition xrefsI = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("xrefsI")
                .setPosition(24)
                .build();

        ColumnDefinition annotationsA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("annotationsA")
                .setPosition(25)
                .build();

        ColumnDefinition annotationsB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("annotationsB")
                .setPosition(26)
                .build();

        ColumnDefinition annotationsI = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("annotationsI")
                .setPosition(27)
                .build();

        ColumnDefinition hostOrganism = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("hostOrganism")
                .setPosition(28)
                .build();

        ColumnDefinition parametersA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("parametersA")
                .setPosition(29)
                .build();

        ColumnDefinition parametersB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("parametersB")
                .setPosition(30)
                .build();

        ColumnDefinition parametersI = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("parametersI")
                .setPosition(31)
                .build();

        ColumnDefinition creationDate = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("creationDate")
                .setPosition(32)
                .build();

        ColumnDefinition updateDate = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("updateDate")
                .setPosition(33)
                .build();

        ColumnDefinition checksumA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("checksumA")
                .setPosition(34)
                .build();

        ColumnDefinition checksumB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("checksumB")
                .setPosition(35)
                .build();

        ColumnDefinition checksumI = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("checksumI")
                .setPosition(36)
                .build();

        ColumnDefinition negative = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setKey("negative")
                .setPosition(37)
                .build();


        DocumentDefinition docDefinition = new DocumentDefinitionBuilder()
                .extendDocumentDefinition( mitab25() )
                .addColumnDefinition(expansion)
                .addColumnDefinition(bioRoleA)
                .addColumnDefinition(bioRoleB)
                .addColumnDefinition(expRoleA)
                .addColumnDefinition(expRoleB)
                .addColumnDefinition(typeA)
                .addColumnDefinition(typeB)
                .addColumnDefinition(xrefsA)
                .addColumnDefinition(xrefsB)
                .addColumnDefinition(xrefsI)
                .addColumnDefinition(annotationsA)
                .addColumnDefinition(annotationsB)
                .addColumnDefinition(annotationsI)
                .addColumnDefinition(hostOrganism)
                .addColumnDefinition(parametersA)
                .addColumnDefinition(parametersB)
                .addColumnDefinition(parametersB)
                .addColumnDefinition(parametersI)
                .addColumnDefinition(creationDate)
                .addColumnDefinition(checksumA)
                .addColumnDefinition(checksumB)
                .addColumnDefinition(checksumI)
                .addColumnDefinition(negative)
                .build();

            public static String KEY_EXPANSION = "expansion";
    public static String KEY_BIOROLE_A = "bioRoleA";
    public static String KEY_BIOROLE_B = "bioRoleB";
    public static String KEY_EXPROLE_A = "expRoleA";
    public static String KEY_EXPROLE_B = "expRoleB";
    public static String KEY_INTERACTOR_TYPE_A = "typeA";
    public static String KEY_INTERACTOR_TYPE_B = "typeB";
    public static String KEY_XREFS_A = "xrefsA";
    public static String KEY_XREFS_B = "xrefsB";
    public static String KEY_XREFS_I = "xrefsI";
    public static String KEY_ANNOTATIONS_A = "annotationsA";
    public static String KEY_ANNOTATIONS_B = "annotationsB";
    public static String KEY_ANNOTATIONS_I = "annotationsI";
    public static String KEY_HOST_ORGANISM = "hostOrganism";
    public static String KEY_PARAMETERS_A = "parametersA";
    public static String KEY_PARAMETERS_B = "parametersB";
    public static String KEY_PARAMETERS_I = "parametersI";
    public static String KEY_CREATION_DATE = "creationDate";
    public static String KEY_UPDATE_DATE = "updateDate";
    public static String KEY_CHECKSUM_A = "checksumA";
    public static String KEY_CHECKSUM_B = "checksumB";
    public static String KEY_CHECKSUM_I = "checksumI";
    public static String KEY_NEGATIVE = "negative";
        return docDefinition;
    }

}
