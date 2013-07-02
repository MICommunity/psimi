package psidev.psi.mi.jami.tab.io.parser;

/**
 * Enum which lists all possible enums
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/06/13</pre>
 */

public enum TokenKind {
    EOF, QUOTED_STRING, DASH, PUB_DATE, NUMBER, TAXID, QUOTED_TAXID, FALSE, QUOTED_FALSE, TRUE, QUOTED_TRUE,
    POSITION1, POSITION2, POSITION3, STOICHIOMETRY, COMMENT, FIELD_SEPARATOR, COLUMN_SEPARATOR, LINE_SEPARATOR, RANGE_SEPARATOR,
    OPEN_PAREN, CLOSE_PAREN, COLON;

    static TokenKind getFromTokenKind(int kind) {
        switch (kind) {
            case MitabLineParserConstants.EOF: return EOF;
            case MitabLineParserConstants.QUOTED_STRING: return QUOTED_STRING;
            case MitabLineParserConstants.DASH: return DASH;
            case MitabLineParserConstants.PUB_DATE: return PUB_DATE;
            case MitabLineParserConstants.TAXID: return TAXID;
            case MitabLineParserConstants.QUOTED_TAXID: return QUOTED_TAXID;
            case MitabLineParserConstants.FALSE: return FALSE;
            case MitabLineParserConstants.TRUE: return TRUE;
            case MitabLineParserConstants.QUOTED_TRUE: return QUOTED_TRUE;
            case MitabLineParserConstants.POSITION1: return POSITION1;
            case MitabLineParserConstants.POSITION2: return POSITION2;
            case MitabLineParserConstants.POSITION3: return POSITION3;
            case MitabLineParserConstants.STOICHIOMETRY: return STOICHIOMETRY;
            case MitabLineParserConstants.FIELD_SEPARATOR: return FIELD_SEPARATOR;
            case MitabLineParserConstants.COLUMN_SEPARATOR: return COLUMN_SEPARATOR;
            case MitabLineParserConstants.LINE_SEPARATOR: return LINE_SEPARATOR;
            case MitabLineParserConstants.RANGE_SEPARATOR: return RANGE_SEPARATOR;
            case MitabLineParserConstants.COMMENT: return COMMENT;
            case MitabLineParserConstants.NUMBER: return NUMBER;
            case MitabLineParserConstants.COLON: return COLON;
            case MitabLineParserConstants.OPEN_PAREN: return OPEN_PAREN;
            case MitabLineParserConstants.CLOSE_PAREN: return CLOSE_PAREN;
            default: return null;
        }
    }

}
