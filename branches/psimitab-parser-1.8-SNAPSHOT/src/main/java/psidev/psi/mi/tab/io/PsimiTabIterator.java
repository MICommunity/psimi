package psidev.psi.mi.tab.io;

import au.com.bytecode.opencsv.CSVReader;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.builder.MitabParsingUtils;
import psidev.psi.mi.tab.model.builder.PsimiTabColumns;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: noedelta
 * Date: 18/06/2012
 * Time: 15:51
 */
public class PsimiTabIterator implements psidev.psi.mi.tab.PsimiTabIterator {

    private CSVReader csvReader;

    /**
     * Next line to be processed.
     */
    private String[] nextLine;

    /**
     * Count of interaction already processed.
     */
    private int interactionsProcessedCount = 0;

    /**
     * Line number currently being parsed.
     */
    protected int lineIndex = 0;

    ////////////////////////
    // Constructor


    public PsimiTabIterator(Reader psiMiTabInteractionsReader) {

        if (psiMiTabInteractionsReader == null) {
            throw new IllegalArgumentException("You must give a non null input stream.");
        }

        try {
            this.csvReader = new CSVReader(psiMiTabInteractionsReader, '\t', '\0', lineIndex);

            nextLine = csvReader.readNext();
            if (nextLine != null && nextLine.length > 0 && nextLine[0].startsWith("#")) {
                //This line is the header, we skip the line
                nextLine = csvReader.readNext();
                lineIndex++;
            }
            // line[] is an array of values from the line
            // Avoid the problem of the size with the different formats

            if (nextLine.length < PsimiTabColumns.MITAB_LENGTH.ordinal()) {
                nextLine = MitabParsingUtils.extendFormat(nextLine, PsimiTabColumns.MITAB_LENGTH.ordinal());
            }
            lineIndex++;


        } catch (IOException e) {
            throw new RuntimeException("Error while reading the header line.", e);
        }
    }

    public boolean hasNext() {
        return (nextLine != null);
    }

    public BinaryInteraction next() {
        if (nextLine == null && !hasNext()) {
            throw new NoSuchElementException();
        }

        BinaryInteraction interaction = null;
        String[] temp = nextLine;
        try {
            nextLine = csvReader.readNext();

            if (nextLine != null) {

                // line[] is an array of values from the line
                // Avoid the problem of the size with the different formats

                if (nextLine.length < PsimiTabColumns.MITAB_LENGTH.ordinal()) {
                    nextLine = MitabParsingUtils.extendFormat(nextLine, PsimiTabColumns.MITAB_LENGTH.ordinal());
                }
            }

            interaction = MitabParsingUtils.buildBinaryInteraction(temp);
            interactionsProcessedCount++;

            lineIndex++;

        } catch (Throwable e) {
            throw new RuntimeException("Exception parsing line " + lineIndex + ": " + Arrays.toString(temp), e);
        }

        return interaction;
    }

    public void remove() {
        throw new UnsupportedOperationException("This is a read only iterator.");
    }

    public int getInteractionsProcessedCount() {
        return interactionsProcessedCount;
    }
}
