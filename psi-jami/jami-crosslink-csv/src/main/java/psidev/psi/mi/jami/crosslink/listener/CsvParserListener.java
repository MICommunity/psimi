package psidev.psi.mi.jami.crosslink.listener;

import psidev.psi.mi.jami.crosslink.extension.CsvProtein;
import psidev.psi.mi.jami.crosslink.extension.CsvRange;
import psidev.psi.mi.jami.listener.MIFileParserListener;

import java.util.List;

/**
 * A listener listening to events when parsing a crosslink CSV file
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/06/13</pre>
 */

public interface CsvParserListener extends MIFileParserListener{

    /**
     * Event fired when the number of peptide positions is greater than one and is not the same as the number of linked positions
     * @param peptidePositions : peptide positions in protein sequence
     * @param linkedPositions : linked feature positions relative to peptide positions
     */
    public void onMismatchBetweenPeptideAndLinkedPositions(List<CsvRange> peptidePositions, List<CsvRange> linkedPositions);

    /**
     * Event fired when the number of range positions is superior to 1 and the number of proteins is also superior to 1 but does not match
     * the number of ranges
     * @param rangePositions : feature ranges
     * @param proteins : proteins
     */
    public void onMismatchBetweenRangePositionsAndProteins(List<CsvRange> rangePositions, List<CsvProtein> proteins);

    /**
     * Event fired when we don't find protein xrefs with db|uniprotId|name
     * @param identifiers : the identifiers
     */
    public void onInvalidProteinIdentifierSyntax(String[] identifiers, int lineNumber, int columnNumber);

    /**
     * Event fired when the column Protein1 is missing or empty
     * @param lineNumber : line number
     */
    public void onMissingProtein1Column(int lineNumber);
}
