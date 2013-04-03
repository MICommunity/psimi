/**
 * Copyright 2007 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package psidev.psi.mi.tab;

import org.apache.commons.lang.exception.ExceptionUtils;
import psidev.psi.mi.jami.datasource.FileParsingErrorType;
import psidev.psi.mi.tab.events.InvalidFormatEvent;
import psidev.psi.mi.tab.listeners.MitabParserListener;
import psidev.psi.mi.tab.listeners.MitabParsingLogger;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.MitabSourceLocator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.NoSuchElementException;

/**
 * Iterator implementation to avoid memory errors when reading a large MI Tab file.
 * This class is not Thread safe.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk), Samuel Kerrien (skerrien@ebi.ac.uk).
 * @version $Id$
 */
public class PsimiTabIterator implements psidev.psi.mi.tab.io.PsimiTabIterator {

    /**
     * Reader on the data we are going to iterate.
     */
    private BufferedReader interactionStreamReader;


    /**
     * Next line to be processed.
     */
    private BinaryInteraction nextLine;

    /**
     * Count of interaction already processed.
     */
    private int interactionsProcessedCount = 0;

    /**
     * Line number currently being parsed.
     */
    private int lineIndex = 0;

    /**
     * indicate if the line that has been read was already consumed by the user via the next() nethod.
     */
    private boolean lineConsummed = false;

    private PsimiTabReader mReader;

    ////////////////////////
    // Constructor

    public PsimiTabIterator(Reader psiMiTabInteractionsReader) {

        boolean isHeader = true;

        if (psiMiTabInteractionsReader == null) {
            throw new IllegalArgumentException("You must give a non null input stream.");
        }
        if (psiMiTabInteractionsReader instanceof BufferedReader) {
            this.interactionStreamReader = (BufferedReader) psiMiTabInteractionsReader;
        } else {
            this.interactionStreamReader = new BufferedReader(psiMiTabInteractionsReader);
        }
        this.mReader = new PsimiTabReader();
        mReader.addMitabParserListener(new MitabParsingLogger());

        try {
            do {
                String firstLine = interactionStreamReader.readLine();
                if(firstLine == null){
                    nextLine = null;
                    isHeader = false;
                }
                else if (!firstLine.isEmpty() && !firstLine.startsWith("#")) {
                    //This line is not a comment, we read
                    nextLine = mReader.readLine(firstLine, lineIndex);
                    lineIndex++;

                    while (nextLine == null && firstLine != null){
                        firstLine = interactionStreamReader.readLine();
                        if (firstLine != null){
                            nextLine = mReader.readLine(firstLine, lineIndex);
                        }
                        lineIndex++;
                    }
                    isHeader = false;
                }
                else if(!isHeader){
                    lineIndex++;
                }

            } while (isHeader);

        } catch (Exception e) {
            boolean errorInLine = true;
            do {
                try {
                    InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "Error while reading the header line. " + ExceptionUtils.getFullStackTrace(e));
                    evt.setSourceLocator(new MitabSourceLocator(lineIndex, -1, -1));
                    for (MitabParserListener l : mReader.getListeners(MitabParserListener.class)){
                        l.fireOnInvalidFormat(evt);
                    }

                    String firstLine = interactionStreamReader.readLine();
                    if(firstLine == null){
                        nextLine = null;
                        isHeader = false;
                    }
                    else if (!firstLine.isEmpty() && !firstLine.startsWith("#")) {
                        //This line is not a comment, we read
                        nextLine = mReader.readLine(firstLine, lineIndex);
                        lineIndex++;
                        while (nextLine == null && firstLine != null){
                            firstLine = interactionStreamReader.readLine();
                            if (firstLine != null){
                                nextLine = mReader.readLine(firstLine, lineIndex);
                            }
                            lineIndex++;
                        }
                        isHeader = false;
                    }
                    else if(!isHeader){
                        lineIndex++;
                    }

                    errorInLine = false;
                } catch (IOException e1) {
                    // skip the error
                }

            } while (isHeader && errorInLine);

            if (errorInLine && nextLine == null){
                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "Error while reading the header line. " + ExceptionUtils.getFullStackTrace(e));
                evt.setSourceLocator(new MitabSourceLocator(lineIndex, -1, -1));
                for (MitabParserListener l : mReader.getListeners(MitabParserListener.class)){
                    l.fireOnInvalidFormat(evt);
                }
                closeStreamReader();
            }
        }
    }

    public PsimiTabIterator(Reader psiMiTabInteractionsReader, PsimiTabReader mitabParser) {

        boolean isHeader = true;

        if (psiMiTabInteractionsReader == null) {
            throw new IllegalArgumentException("You must give a non null input stream.");
        }
        if (psiMiTabInteractionsReader instanceof BufferedReader) {
            this.interactionStreamReader = (BufferedReader) psiMiTabInteractionsReader;
        } else {
            this.interactionStreamReader = new BufferedReader(psiMiTabInteractionsReader);
        }
        this.mReader = mitabParser;


        try {
            do {
                String firstLine = interactionStreamReader.readLine();
                if(firstLine == null){
                    nextLine = null;
                    isHeader = false;
                }
                else if (!firstLine.isEmpty() && !firstLine.startsWith("#")) {
                    //This line is not a comment, we read
                    nextLine = mReader.readLine(firstLine, lineIndex);
                    lineIndex++;

                    while (nextLine == null && firstLine != null){
                        firstLine = interactionStreamReader.readLine();
                        if (firstLine != null){
                            nextLine = mReader.readLine(firstLine, lineIndex);
                        }
                        lineIndex++;
                    }
                    isHeader = false;
                }
                else if(!isHeader){
                    lineIndex++;
                }

            } while (isHeader);

        } catch (Exception e) {

            boolean errorInLine = true;
            do {
                try {
                    InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "Error while reading the header line. " + ExceptionUtils.getFullStackTrace(e));
                    evt.setSourceLocator(new MitabSourceLocator(lineIndex, -1, -1));
                    for (MitabParserListener l : mReader.getListeners(MitabParserListener.class)){
                        l.fireOnInvalidFormat(evt);
                    }

                    String firstLine = interactionStreamReader.readLine();
                    if(firstLine == null){
                        nextLine = null;
                        isHeader = false;
                    }
                    else if (!firstLine.isEmpty() && !firstLine.startsWith("#")) {
                        //This line is not a comment, we read
                        nextLine = mReader.readLine(firstLine, lineIndex);
                        lineIndex++;

                        while (nextLine == null && firstLine != null){
                            firstLine = interactionStreamReader.readLine();
                            if (firstLine != null){
                                nextLine = mReader.readLine(firstLine, lineIndex);
                            }
                            lineIndex++;
                        }
                        isHeader = false;
                    }
                    else if(!isHeader){
                        lineIndex++;
                    }

                    errorInLine = false;
                } catch (IOException e1) {
                    // skip the error
                }

            } while (isHeader && errorInLine);

            if (errorInLine && nextLine == null){
                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "Error while reading the header. " + ExceptionUtils.getFullStackTrace(e));
                evt.setSourceLocator(new MitabSourceLocator(lineIndex, -1, -1));
                for (MitabParserListener l : mReader.getListeners(MitabParserListener.class)){
                    l.fireOnInvalidFormat(evt);
                }
                closeStreamReader();
            }
        }
    }

    //////////////////////////
    // Iterator

    public boolean hasNext() {
        try {
            if (lineConsummed) {
                String line = interactionStreamReader.readLine();
                if (line == null){
                    closeStreamReader();
                    interactionStreamReader = null;
                }
                else {
                    nextLine = mReader.readLine(line, lineIndex);
                    lineIndex++;

                    while (nextLine == null && line != null){
                        line = interactionStreamReader.readLine();
                        if (line != null){
                            nextLine = mReader.readLine(line, lineIndex);
                        }
                        lineIndex++;
                    }

                    if (nextLine == null) {
                        closeStreamReader();
                        interactionStreamReader = null;
                    } else {
                        lineConsummed = false;
                    }
                }
            }
        } catch (Exception e) {

            boolean errorInLine = true;
            do {
                try {
                    InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "Error while reading the line. " + ExceptionUtils.getFullStackTrace(e));
                    evt.setSourceLocator(new MitabSourceLocator(lineIndex, -1, -1));
                    for (MitabParserListener l : mReader.getListeners(MitabParserListener.class)){
                        l.fireOnInvalidFormat(evt);
                    }

                    String line = interactionStreamReader.readLine();
                    if (line == null){
                        closeStreamReader();
                        interactionStreamReader = null;
                    }
                    else {
                        nextLine = mReader.readLine(line, lineIndex);
                        lineIndex++;

                        while (nextLine == null && line != null){
                            line = interactionStreamReader.readLine();
                            if (line != null){
                                nextLine = mReader.readLine(line, lineIndex);
                            }
                            lineIndex++;
                        }

                        if (nextLine == null) {
                            closeStreamReader();
                            interactionStreamReader = null;
                        } else {
                            lineConsummed = false;
                        }
                    }

                    errorInLine = false;
                } catch (IOException e1) {
                    // skip the error
                }

            } while (errorInLine);

            if (errorInLine && nextLine == null){
                InvalidFormatEvent evt = new InvalidFormatEvent(FileParsingErrorType.invalid_syntax, "Error while reading the line. " + ExceptionUtils.getFullStackTrace(e));
                evt.setSourceLocator(new MitabSourceLocator(lineIndex, -1, -1));
                for (MitabParserListener l : mReader.getListeners(MitabParserListener.class)){
                    l.fireOnInvalidFormat(evt);
                }
                closeStreamReader();
            }
        }

        return (nextLine != null);
    }

    public BinaryInteraction next() {
        if (nextLine == null && !hasNext()) {
            throw new NoSuchElementException();
        }

        BinaryInteraction interaction = nextLine;

        interactionsProcessedCount++;
        lineConsummed = true;
        nextLine = null;

        return interaction;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    //////////////////////////////////
    // additional public method(s)

    public int getInteractionsProcessedCount() {
        return interactionsProcessedCount;
    }


    public void closeStreamReader() {
        if (interactionStreamReader != null) {
            try {
                interactionStreamReader.close();
            } catch (IOException e) {
                // keep it quiet ...
            }
        }
    }
}
