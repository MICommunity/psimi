package org.hupo.psi.calimocho.tab.util;

import org.hupo.psi.calimocho.tab.io.formatter.BooleanFieldFormatter;
import org.hupo.psi.calimocho.tab.io.formatter.DateFieldFormatter;
import org.hupo.psi.calimocho.tab.io.formatter.LiteralFieldFormatter;
import org.hupo.psi.calimocho.tab.io.parser.BooleanFieldParser;
import org.hupo.psi.calimocho.tab.io.parser.DateFieldParser;
import org.hupo.psi.calimocho.tab.io.parser.LiteralFieldParser;
import org.hupo.psi.calimocho.tab.io.formatter.XrefFieldFormatter;
import org.hupo.psi.calimocho.tab.io.parser.XrefFieldParser;
import org.hupo.psi.calimocho.tab.model.ColumnBasedDocumentDefinition;
import org.hupo.psi.calimocho.tab.model.ColumnBasedDocumentDefinitionBuilder;
import org.hupo.psi.calimocho.tab.model.ColumnDefinition;
import org.hupo.psi.calimocho.tab.model.ColumnDefinitionBuilder;

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
                .setFieldParser(new LiteralFieldParser("author"))
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

        ColumnBasedDocumentDefinition docDefinition = new ColumnBasedDocumentDefinitionBuilder()
                .setName("mitab25")
                .setDefinition("MITAB25 Specification")
                .addColumnDefinition(idACol)
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
                .setFieldFormatter(literalFieldFormatter)
                .setFieldParser(literalParser)
                .build();

        ColumnDefinition bioRoleA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expansion)
                .setName("Biological Role A")
                .setKey(Mitab26ColumnKeys.KEY_BIOROLE_A)
                .setPosition(16)
                .setFieldFormatter(xrefFormatter)
                .setFieldParser(xrefParser)
                .build();

        ColumnDefinition bioRoleB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(bioRoleA)
                .setName("Biological Role B")
                .setKey(Mitab26ColumnKeys.KEY_BIOROLE_B)
                .setPosition(17)
                .build();

        ColumnDefinition expRoleA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(bioRoleA)
                .setName("Experimental Role A")
                .setKey(Mitab26ColumnKeys.KEY_EXPROLE_A)
                .setPosition(18)
                .build();

        ColumnDefinition expRoleB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(bioRoleA)
                .setName("Experimental Role B")
                .setKey(Mitab26ColumnKeys.KEY_EXPROLE_B)
                .setPosition(19)
                .build();

        ColumnDefinition typeA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(bioRoleA)
                .setName("Interactor type A")
                .setKey(Mitab26ColumnKeys.KEY_INTERACTOR_TYPE_A)
                .setPosition(20)
                .build();

        ColumnDefinition typeB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(bioRoleA)
                .setName("Interactor type B")
                .setKey(Mitab26ColumnKeys.KEY_INTERACTOR_TYPE_B)
                .setPosition(21)
                .build();

        ColumnDefinition xrefsA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(bioRoleA)
                .setName("Xrefs A")
                .setKey(Mitab26ColumnKeys.KEY_XREFS_A)
                .setPosition(22)
                .build();

        ColumnDefinition xrefsB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(bioRoleA)
                .setName("Xrefs B")
                .setKey(Mitab26ColumnKeys.KEY_XREFS_B)
                .setPosition(23)
                .build();


        ColumnDefinition xrefsI = new ColumnDefinitionBuilder()
                .extendColumnDefinition(bioRoleA)
                .setName("Xrefs Interaction")
                .setKey(Mitab26ColumnKeys.KEY_XREFS_I)
                .setPosition(24)
                .build();

        ColumnDefinition annotationsA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(bioRoleA)
                .setName("Annotations A")
                .setKey(Mitab26ColumnKeys.KEY_ANNOTATIONS_A)
                .setPosition(25)
                .build();

        ColumnDefinition annotationsB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(bioRoleA)
                .setName("Annotations B")
                .setKey(Mitab26ColumnKeys.KEY_ANNOTATIONS_B)
                .setPosition(26)
                .build();

        ColumnDefinition annotationsI = new ColumnDefinitionBuilder()
                .extendColumnDefinition(bioRoleA)
                .setName("Annotations Interaction")
                .setKey(Mitab26ColumnKeys.KEY_ANNOTATIONS_I)
                .setPosition(27)
                .build();

        ColumnDefinition hostOrganism = new ColumnDefinitionBuilder()
                .extendColumnDefinition(bioRoleA)
                .setName("Host Organism")
                .setKey(Mitab26ColumnKeys.KEY_HOST_ORGANISM)
                .setPosition(28)
                .build();

        ColumnDefinition parametersI = new ColumnDefinitionBuilder()
                .extendColumnDefinition(bioRoleA)
                .setName("Parameters Interaction")
                .setKey(Mitab26ColumnKeys.KEY_PARAMETERS_I)
                .setPosition(29)
                .build();

        ColumnDefinition creationDate = new ColumnDefinitionBuilder()
                .extendColumnDefinition(bioRoleA)
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
                .extendColumnDefinition(bioRoleA)
                .setName("Checksum A")
                .setKey(Mitab26ColumnKeys.KEY_CHECKSUM_A)
                .setPosition(32)
                .build();

        ColumnDefinition checksumB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(bioRoleA)
                .setName("Checksum B")
                .setKey(Mitab26ColumnKeys.KEY_CHECKSUM_B)
                .setPosition(33)
                .build();

        ColumnDefinition checksumI = new ColumnDefinitionBuilder()
                .extendColumnDefinition(bioRoleA)
                .setName("Checksum Interaction")
                .setKey(Mitab26ColumnKeys.KEY_CHECKSUM_I)
                .setPosition(34)
                .build();

        ColumnDefinition negative = new ColumnDefinitionBuilder()
                .extendColumnDefinition(bioRoleA)
                .setName("Negative")
                .setKey(Mitab26ColumnKeys.KEY_NEGATIVE)
                .setPosition(35)
                .setFieldParser( new BooleanFieldParser() )
                .setFieldFormatter( new BooleanFieldFormatter() )
                .build();


        ColumnBasedDocumentDefinition docDefinition = new ColumnBasedDocumentDefinitionBuilder()
                .extendDocumentDefinition(mitab25())
                .setName("mitab26")
                .setDefinition("MITAB26 Specification")
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

    public static ColumnBasedDocumentDefinition mitab25Intact() {
        XrefFieldParser xrefParser = new XrefFieldParser();
        XrefFieldFormatter xrefFormatter = new XrefFieldFormatter();

        LiteralFieldFormatter literalFieldFormatter = new LiteralFieldFormatter();

        ColumnDefinition expRoleA = new ColumnDefinitionBuilder()
                .setName("Experimental Role A")
                .setKey(Mitab25IntactColumnKeys.KEY_EXPROLE_A)
                .setFieldSeparator("|")
                .setEmptyValue("-")
                .setFieldDelimiter("")
                .setIsAllowsEmpty(true)
                .setPosition(15)
                .setFieldFormatter(xrefFormatter)
                .setFieldParser(xrefParser)
                .build();

        ColumnDefinition expRoleB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expRoleA)
                .setName("Experimental Role B")
                .setKey(Mitab25IntactColumnKeys.KEY_EXPROLE_B)
                .setPosition(16)
                .build();

        ColumnDefinition bioRoleA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expRoleA)
                .setName("Biological Role A")
                .setKey(Mitab25IntactColumnKeys.KEY_BIOROLE_A)
                .setPosition(17)
                .build();

        ColumnDefinition bioRoleB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expRoleA)
                .setName("Biological Role B")
                .setKey(Mitab25IntactColumnKeys.KEY_BIOROLE_B)
                .setPosition(18)
                .build();

        ColumnDefinition xrefsA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expRoleA)
                .setName("Xrefs A")
                .setKey(Mitab25IntactColumnKeys.KEY_XREFS_A)
                .setPosition(19)
                .build();

        ColumnDefinition xrefsB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expRoleA)
                .setName("Xrefs B")
                .setKey(Mitab25IntactColumnKeys.KEY_XREFS_B)
                .setPosition(20)
                .build();

        ColumnDefinition interactorTypeA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expRoleA)
                .setName("Interactor type A")
                .setKey(Mitab25IntactColumnKeys.KEY_INTERACTOR_TYPE_A)
                .setPosition(21)
                .build();

        ColumnDefinition interactorTypeB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expRoleA)
                .setName("Interactor type B")
                .setKey(Mitab25IntactColumnKeys.KEY_INTERACTOR_TYPE_B)
                .setPosition(22)
                .build();

        ColumnDefinition hostOrganism = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expRoleA)
                .setName("Host Organism")
                .setKey(Mitab25IntactColumnKeys.KEY_HOST_ORGANISM)
                .setPosition(23)
                .build();

        ColumnDefinition expansion = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expRoleA)
                .setName("Expansion")
                .setKey(Mitab25IntactColumnKeys.KEY_EXPANSION)
                .setPosition(24)
                .setFieldFormatter(literalFieldFormatter)
                .setFieldParser(new LiteralFieldParser("expansion"))
                .build();

        ColumnDefinition dataset = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expRoleA)
                .setName("Dataset")
                .setKey(Mitab25IntactColumnKeys.KEY_DATASET)
                .setPosition(25)
                .setFieldFormatter(literalFieldFormatter)
                .setFieldParser(new LiteralFieldParser("dataset"))
                .build();

        ColumnDefinition annotA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expRoleA)
                .setName("Annotations A")
                .setKey(Mitab25IntactColumnKeys.KEY_ANNOTATIONS_A)
                .setPosition(26)
                .build();

        ColumnDefinition annotB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expRoleA)
                .setName("Annotations B")
                .setKey(Mitab25IntactColumnKeys.KEY_ANNOTATIONS_B)
                .setPosition(27)
                .build();

        ColumnDefinition paramA = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expRoleA)
                .setName("Parameters A")
                .setKey(Mitab25IntactColumnKeys.KEY_PARAMETERS_A)
                .setPosition(28)
                .build();

        ColumnDefinition paramB = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expRoleA)
                .setName("Parameters B")
                .setKey(Mitab25IntactColumnKeys.KEY_PARAMETERS_B)
                .setPosition(29)
                .build();

        ColumnDefinition paramInteraction = new ColumnDefinitionBuilder()
                .extendColumnDefinition(expRoleA)
                .setName("Parameters Interaction")
                .setKey(Mitab25IntactColumnKeys.KEY_PARAMETERS_I)
                .setPosition(30)
                .build();

        ColumnBasedDocumentDefinition docDefinition = new ColumnBasedDocumentDefinitionBuilder()
                .extendDocumentDefinition( mitab25() )
                .setName("mitab25-intact")
                .setDefinition("Extension of MITAB25 used by IntAct")
                .addColumnDefinition(expRoleA)
                .addColumnDefinition(expRoleB)
                .addColumnDefinition(bioRoleA)
                .addColumnDefinition(bioRoleB)
                .addColumnDefinition(xrefsA)
                .addColumnDefinition(xrefsB)
                .addColumnDefinition(interactorTypeA)
                .addColumnDefinition(interactorTypeB)
                .addColumnDefinition(hostOrganism)
                .addColumnDefinition(expansion)
                .addColumnDefinition(dataset)
                .addColumnDefinition(annotA)
                .addColumnDefinition(annotB)
                .addColumnDefinition(paramA)
                .addColumnDefinition(paramB)
                .addColumnDefinition(paramInteraction)
                .build();
        return docDefinition;
    }

}
