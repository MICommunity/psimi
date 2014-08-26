package psidev.psi.mi.jami.crosslink.listener;

import psidev.psi.mi.jami.crosslink.extension.CsvProtein;
import psidev.psi.mi.jami.crosslink.extension.CsvRange;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.listener.impl.MIFileParserLogger;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logger for CsvParser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/06/13</pre>
 */

public class CsvParserLogger extends MIFileParserLogger implements CsvParserListener {
    private static final Logger logger = Logger.getLogger("CsvParserLogger");

    public void onMismatchBetweenPeptideAndLinkedPositions(List<CsvRange> peptidePositions, List<CsvRange> linkedPositions) {
        FileSourceLocator locator1 = peptidePositions.isEmpty() ? null : peptidePositions.iterator().next().getSourceLocator();
        FileSourceLocator locator2 = linkedPositions.isEmpty() ? null : linkedPositions.iterator().next().getSourceLocator();

        logger.log(Level.SEVERE, "The number of peptide positions "+peptidePositions.size() +
                " ("+(locator1 != null ? locator1.toString() : "no location details")+
                ") is different from the number of linked positions "+linkedPositions.size()+" ("+
                (locator2 != null ? locator2.toString() : "no location details")+"). " +
                "These positions will be ignored as too ambiguous.");
    }

    public void oneMismatchBetweenRangePositionsAndProteins(List<CsvRange> rangePositions, List<CsvProtein> proteins) {
        FileSourceLocator locator1 = rangePositions.isEmpty() ? null : rangePositions.iterator().next().getSourceLocator();
        FileSourceLocator locator2 = proteins.isEmpty() ? null : proteins.iterator().next().getSourceLocator();

        logger.log(Level.SEVERE, "The number of linked positions "+rangePositions.size() +
                " ("+(locator1 != null ? locator1.toString() : "no location details")+
                ") is different from the number of protein identifiers "+proteins.size()+" ("+
                (locator2 != null ? locator2.toString() : "no location details")+"). " +
                "These positions will be ignored as too ambiguous.");
    }

    public void onInvalidProteinIdentifierSyntax(String[] identifiers, int lineNumber, int columnNumber) {
        logger.log(Level.SEVERE, "We found "+identifiers.length + " elements at line "+lineNumber+", column "+columnNumber+" separated by '|' but we only expected " +
                "three elements separated by '|' (sp|uniprotId|name)");
    }

    public void onMissingProtein1Column(int lineNumber) {
        logger.log(Level.SEVERE, "The Protein1 column is missing or empty at line "+lineNumber+". The column is required and cannot be empty.");
    }
}
