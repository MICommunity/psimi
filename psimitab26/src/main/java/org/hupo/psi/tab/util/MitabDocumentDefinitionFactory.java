package org.hupo.psi.tab.util;

import org.hupo.psi.calimocho.io.formatter.BooleanFieldFormatter;
import org.hupo.psi.calimocho.io.formatter.DateFieldFormatter;
import org.hupo.psi.calimocho.io.formatter.LiteralFieldFormatter;
import org.hupo.psi.calimocho.io.parser.BooleanFieldParser;
import org.hupo.psi.calimocho.io.parser.DateFieldParser;
import org.hupo.psi.calimocho.io.parser.LiteralFieldParser;
import org.hupo.psi.calimocho.model.ColumnBasedDocumentDefinition;
import org.hupo.psi.calimocho.model.ColumnDefinition;
import org.hupo.psi.calimocho.model.ColumnDefinitionBuilder;
import org.hupo.psi.calimocho.model.DocumentDefinitionBuilder;
import org.hupo.psi.tab.io.formatter.XrefFieldFormatter;
import org.hupo.psi.tab.io.parser.XrefFieldParser;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class MitabDocumentDefinitionFactory {

    private MitabDocumentDefinitionFactory() {}

    public static ColumnBasedDocumentDefinition mitab25() {
        XrefFieldParser xrefParser = new XrefFieldParser();
        XrefFieldFormatter xrefFormatter = new XrefFieldFormatter();

        LiteralFieldParser literalParser = new LiteralFieldParser();
        LiteralFieldFormatter literalFieldFormatter = new LiteralFieldFormatter();

        ColumnDefinition idACol = new ColumnDefinitionBuilder()
                .setName("ID(s) interactor A")
                .setKey(Mitab25ColumnKeys.KEY_ID_A)
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
                .setName("ID(s) interactor B")
                .setKey(Mitab25ColumnKeys.KEY_ID_B)
                .setPosition(1)
                .build();

        ColumnDefinition altidA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(idACol)
                .setName("Alt. ID(s) interactor A")
                .setKey(Mitab25ColumnKeys.KEY_ALTID_A)
                .setPosition(2)
                .setIsAllowsEmpty(true)
                .build();

        ColumnDefinition altidB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setName("Alt. ID(s) interactor B")
                .setKey(Mitab25ColumnKeys.KEY_ALTID_B)
                .setPosition(3)
                .build();

        ColumnDefinition aliasA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setName("Alias(es) interactor A")
                .setKey(Mitab25ColumnKeys.KEY_ALIAS_A)
                .setPosition(4)
                .build();

        ColumnDefinition aliasB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setName("Alias(es) interactor B")
                .setKey(Mitab25ColumnKeys.KEY_ALIAS_B)
                .setPosition(5)
                .build();

        ColumnDefinition detmethod_exact = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setName("Interaction detection method(s)")
                .setKey(Mitab25ColumnKeys.KEY_DETMETHOD)
                .setPosition(6)
                .build();

        ColumnDefinition pubauth = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setName("Publication 1st author(s)")
                .setKey(Mitab25ColumnKeys.KEY_PUBAUTH)
                .setPosition(7)
                .setFieldFormatter(literalFieldFormatter)
                .setFieldParser(literalParser)
                .build();

        ColumnDefinition pubid = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setName("Publication Identifier(s)")
                .setKey(Mitab25ColumnKeys.KEY_PUBID)
                .setPosition(8)
                .build();

        ColumnDefinition taxidA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setName("Taxid interactor A")
                .setKey(Mitab25ColumnKeys.KEY_TAXID_A)
                .setPosition(9)
                .build();

        ColumnDefinition taxidB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setName("Taxid interactor B")
                .setKey(Mitab25ColumnKeys.KEY_TAXID_B)
                .setPosition(10)
                .build();

        ColumnDefinition type_exact = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setName("Interaction type(s)")
                .setKey(Mitab25ColumnKeys.KEY_INTERACTION_TYPE)
                .setPosition(11)
                .build();

        ColumnDefinition source = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setName("Source database(s)")
                .setKey(Mitab25ColumnKeys.KEY_SOURCE)
                .setPosition(12)
                .build();

        ColumnDefinition interaction_id = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setName("Interaction identifier(s)")
                .setKey(Mitab25ColumnKeys.KEY_INTERACTION_ID)
                .setPosition(13)
                .build();

        ColumnDefinition confidence = new ColumnDefinitionBuilder()
                .extendColumnDefinition(altidA)
                .setName("Confidence value(s)")
                .setKey(Mitab25ColumnKeys.KEY_CONFIDENCE)
                .setPosition(14)
                .build();

        ColumnBasedDocumentDefinition docDefinition = new DocumentDefinitionBuilder()
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
                .addColumnDefinition( interaction_id )
                .addColumnDefinition(confidence)
                .setColumnSeparator("\t")
                .setCommentPrefix( "#" )
                .build();

        return docDefinition;
    }

    public static ColumnBasedDocumentDefinition mitab26() {
        XrefFieldParser xrefParser = new XrefFieldParser();
        XrefFieldFormatter xrefFormatter = new XrefFieldFormatter();

        LiteralFieldParser literalParser = new LiteralFieldParser();
        LiteralFieldFormatter literalFieldFormatter = new LiteralFieldFormatter();

        ColumnDefinition expansion = new ColumnDefinitionBuilder()
                .setName("Expansion")
                .setKey(Mitab26ColumnKeys.KEY_EXPANSION)
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
                .setName("Biological Role A")
                .setKey(Mitab26ColumnKeys.KEY_BIOROLE_A)
                .setPosition(16)
                .build();

        ColumnDefinition bioRoleB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setName("Biological Role B")
                .setKey(Mitab26ColumnKeys.KEY_BIOROLE_B)
                .setPosition(17)
                .build();

        ColumnDefinition expRoleA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setName("Experimental Role A")
                .setKey(Mitab26ColumnKeys.KEY_EXPROLE_A)
                .setPosition(18)
                .build();

        ColumnDefinition expRoleB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setName("Experimental Role B")
                .setKey(Mitab26ColumnKeys.KEY_EXPROLE_B)
                .setPosition(19)
                .build();

        ColumnDefinition typeA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setName("Interactor type A")
                .setKey(Mitab26ColumnKeys.KEY_INTERACTOR_TYPE_A)
                .setPosition(20)
                .build();

        ColumnDefinition typeB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setName("Interactor type B")
                .setKey(Mitab26ColumnKeys.KEY_INTERACTOR_TYPE_B)
                .setPosition(21)
                .build();

        ColumnDefinition xrefsA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setName("Xrefs A")
                .setKey(Mitab26ColumnKeys.KEY_XREFS_A)
                .setPosition(22)
                .build();

        ColumnDefinition xrefsB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setName("Xrefs B")
                .setKey(Mitab26ColumnKeys.KEY_XREFS_B)
                .setPosition(23)
                .build();


        ColumnDefinition xrefsI = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setName("Xrefs Interaction")
                .setKey(Mitab26ColumnKeys.KEY_XREFS_I)
                .setPosition(24)
                .build();

        ColumnDefinition annotationsA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setName("Annotations A")
                .setKey(Mitab26ColumnKeys.KEY_ANNOTATIONS_A)
                .setPosition(25)
                .build();

        ColumnDefinition annotationsB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setName("Annotations B")
                .setKey(Mitab26ColumnKeys.KEY_ANNOTATIONS_B)
                .setPosition(26)
                .build();

        ColumnDefinition annotationsI = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setName("Annotations Interaction")
                .setKey(Mitab26ColumnKeys.KEY_ANNOTATIONS_I)
                .setPosition(27)
                .build();

        ColumnDefinition hostOrganism = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setName("Host Organism")
                .setKey(Mitab26ColumnKeys.KEY_HOST_ORGANISM)
                .setPosition(28)
                .build();

        ColumnDefinition parametersI = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setName("Parameters Interaction")
                .setKey(Mitab26ColumnKeys.KEY_PARAMETERS_I)
                .setPosition(29)
                .build();

        ColumnDefinition creationDate = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setName( "Creation Date" )
                .setKey( Mitab26ColumnKeys.KEY_CREATION_DATE )
                .setPosition( 30 )
                .setFieldParser( new DateFieldParser( "yyyy/MM/dd" ) )
                .setFieldFormatter( new DateFieldFormatter( "yyyy/MM/dd" ) )
                .build();

        ColumnDefinition updateDate = new ColumnDefinitionBuilder()
                .extendColumnDefinition(creationDate)
                .setName("Update Date")
                .setKey(Mitab26ColumnKeys.KEY_UPDATE_DATE)
                .setPosition(31)
                .build();

        ColumnDefinition checksumA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setName("Checksum A")
                .setKey(Mitab26ColumnKeys.KEY_CHECKSUM_A)
                .setPosition(32)
                .build();

        ColumnDefinition checksumB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setName("Checksum B")
                .setKey(Mitab26ColumnKeys.KEY_CHECKSUM_B)
                .setPosition(33)
                .build();

        ColumnDefinition checksumI = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setName("Checksum Interaction")
                .setKey(Mitab26ColumnKeys.KEY_CHECKSUM_I)
                .setPosition(34)
                .build();

        ColumnDefinition negative = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setName("Negative")
                .setKey(Mitab26ColumnKeys.KEY_NEGATIVE)
                .setPosition(35)
                .setFieldParser( new BooleanFieldParser() )
                .setFieldFormatter( new BooleanFieldFormatter() )
                .build();


        ColumnBasedDocumentDefinition docDefinition = new DocumentDefinitionBuilder()
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
                .addColumnDefinition(parametersI)
                .addColumnDefinition(creationDate)
                .addColumnDefinition(updateDate)
                .addColumnDefinition(checksumA)
                .addColumnDefinition(checksumB)
                .addColumnDefinition(checksumI)
                .addColumnDefinition(negative)
                .build();
        return docDefinition;
    }

}
