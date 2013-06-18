package psidev.psi.mi.jami.tab.io.parser;

/**
 * Enum which lists all possible enums
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/06/13</pre>
 */

public enum TokenKind {
    EOF, UNRESERVED_STRING, QUOTED_STRING, EMPTY_COLUMN, PUB_DATE, TAXID, NEGATIVE,
    POSITION, STOICHIOMETRY, COMMENT, FIELD_SEPARATOR, COLUMN_SEPARATOR, LINE_SEPARATOR, RANGE_SEPARATOR;

    static TokenKind getFromTokenKind(int kind) {
        switch (kind) {
            case AbstractMitabLineParserConstants.EOF: return EOF;
            case AbstractMitabLineParserConstants.UNRESERVED_STRING: return UNRESERVED_STRING;
            case AbstractMitabLineParserConstants.QUOTED_STRING: return QUOTED_STRING;
            case AbstractMitabLineParserConstants.EMPTY_COLUMN: return EMPTY_COLUMN;
            case AbstractMitabLineParserConstants.PUB_DATE: return PUB_DATE;
            case AbstractMitabLineParserConstants.TAXID: return TAXID;
            case AbstractMitabLineParserConstants.NEGATIVE: return NEGATIVE;
            case AbstractMitabLineParserConstants.POSITION: return POSITION;
            case AbstractMitabLineParserConstants.STOICHIOMETRY: return STOICHIOMETRY;
            case AbstractMitabLineParserConstants.FIELD_SEPARATOR: return FIELD_SEPARATOR;
            case AbstractMitabLineParserConstants.COLUMN_SEPARATOR: return COLUMN_SEPARATOR;
            case AbstractMitabLineParserConstants.LINE_SEPARATOR: return LINE_SEPARATOR;
            case AbstractMitabLineParserConstants.RANGE_SEPARATOR: return RANGE_SEPARATOR;
            case AbstractMitabLineParserConstants.COMMENT: return COMMENT;
            default: return null;
        }
    }

}
