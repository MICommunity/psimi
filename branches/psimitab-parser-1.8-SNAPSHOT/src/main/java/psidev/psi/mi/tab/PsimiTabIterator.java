package psidev.psi.mi.tab;

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
public class PsimiTabIterator implements Iterator<BinaryInteraction> {

    private CSVReader csvReader;


    /**
     * Has the input got a header ?
     */
    protected boolean hasHeader;

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


    public PsimiTabIterator(Reader psiMiTabInteractionsReader, boolean hasHeaderLine) {
        this.hasHeader = hasHeaderLine;

        if (psiMiTabInteractionsReader == null) {
            throw new IllegalArgumentException("You must give a non null input stream.");
        }

        try {
            if (hasHeader) {
                lineIndex++;
            }
            this.csvReader = new CSVReader(psiMiTabInteractionsReader, '\t', '\0',lineIndex);
            nextLine = csvReader.readNext();
            if (nextLine != null) {

                if (nextLine.length < PsimiTabColumns.MITAB_LENGTH.ordinal()) {
                    nextLine = MitabParsingUtils.extendFormat(nextLine, PsimiTabColumns.MITAB_LENGTH.ordinal());
                }
            }

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

        BinaryInteraction interaction;
        String[] temp = nextLine;
        try {
            nextLine = csvReader.readNext();
            if (nextLine != null) {
                if (nextLine.length < PsimiTabColumns.MITAB_LENGTH.ordinal()) {
                    nextLine = MitabParsingUtils.extendFormat(nextLine, PsimiTabColumns.MITAB_LENGTH.ordinal());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Exception upon parsing at line " + lineIndex, e);
        }

        interaction = MitabParsingUtils.buildBinaryInteraction(temp);
        interactionsProcessedCount++;

        return interaction;
    }

    public void remove() {
        throw new UnsupportedOperationException("This is a read only iterator.");
    }

    public int getInteractionsProcessedCount() {
        return interactionsProcessedCount;
    }
}
