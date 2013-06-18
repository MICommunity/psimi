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
            case MitabLineParserConstants.EOF: return EOF;
            case MitabLineParserConstants.UNRESERVED_STRING: return UNRESERVED_STRING;
            case MitabLineParserConstants.QUOTED_STRING: return QUOTED_STRING;
            case MitabLineParserConstants.EMPTY_COLUMN: return EMPTY_COLUMN;
            case MitabLineParserConstants.PUB_DATE: return PUB_DATE;
            case MitabLineParserConstants.TAXID: return TAXID;
            case MitabLineParserConstants.NEGATIVE: return NEGATIVE;
            case MitabLineParserConstants.POSITION: return POSITION;
            case MitabLineParserConstants.STOICHIOMETRY: return STOICHIOMETRY;
            case MitabLineParserConstants.FIELD_SEPARATOR: return FIELD_SEPARATOR;
            case MitabLineParserConstants.COLUMN_SEPARATOR: return COLUMN_SEPARATOR;
            case MitabLineParserConstants.LINE_SEPARATOR: return LINE_SEPARATOR;
            case MitabLineParserConstants.RANGE_SEPARATOR: return RANGE_SEPARATOR;
            case MitabLineParserConstants.COMMENT: return COMMENT;
            default: return null;
        }
    }

}
