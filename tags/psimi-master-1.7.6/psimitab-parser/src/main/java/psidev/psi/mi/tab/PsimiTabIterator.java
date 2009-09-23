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

import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.builder.DocumentDefinition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator implementation to avoid memory errors when reading a large MI Tab file.
 * This class is not Thread safe.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk), Samuel Kerrien (skerrien@ebi.ac.uk).
 * @version $Id$
 */
public class PsimiTabIterator implements Iterator<BinaryInteraction> {

    /**
     * DocumentDefinition 
     */
    private DocumentDefinition documentDefinition;

    /**
     * Reader on the data we are going to iterate.
     */
    private BufferedReader interactionStreamReader;

    /**
     * Has the input got a header ?
     */
    private boolean hasHeader;

    /**
     * Next line to be processed.
     */
    private String nextLine;

    /**
     * Count of interaction already processed.
     */
    private int interactionsProcessedCount = 0;

    /**
     * Line number currently being parsed.
     */
    private int lineIndex = 0;

    /**
     * indicate if the line that has been read was already consummed by the user via the next() nethod.
     */
    private boolean lineConsummed = true;

    ////////////////////////
    // Constructor

    public PsimiTabIterator( DocumentDefinition documentDefinition, Reader psiMiTabInteractionsReader, boolean hasHeaderLine ) {
        if ( documentDefinition == null ) {
            throw new NullPointerException( "You must give a non null document definition." );
        }

        if ( psiMiTabInteractionsReader == null ) {
            throw new IllegalArgumentException( "You must give a non null input stream." );
        }

        this.documentDefinition = documentDefinition;

        this.interactionStreamReader = new BufferedReader( psiMiTabInteractionsReader );
        this.hasHeader = hasHeaderLine;

        try {
            if ( hasHeader ) {
                readNextLine();
                lineIndex++;
            }
        } catch ( IOException e ) {
            closeStreamReader();
            throw new RuntimeException( "Error while reading the header line.", e );
        }
    }

    //////////////////////////
    // Iterator

    public boolean hasNext() {
        try {
            if ( lineConsummed ) {
                nextLine = readNextLine();
                lineIndex++;
                lineConsummed = false;
            }
        }
        catch ( IOException e ) {
            closeStreamReader();
            return false;
        }

        return ( nextLine != null );
    }

    public BinaryInteraction next() {
        if ( nextLine == null && !hasNext() ) {
            throw new NoSuchElementException();
        }

        BinaryInteraction interaction = null;
        try {
            interaction = documentDefinition.interactionFromString(nextLine);
        }
        catch ( Throwable e ) {
            throw new RuntimeException( "Exception upon parsing at line " + lineIndex, e );
        }

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

    /////////////////////
    // Private method(s)

    private String readNextLine() throws IOException {
        String line = null;
        if ( interactionStreamReader != null ) {
            line = interactionStreamReader.readLine();
            if ( line == null ) {
                closeStreamReader();
                interactionStreamReader = null;
            }
        }
        return line;
    }

    private void closeStreamReader() {
        if ( interactionStreamReader != null ) {
            try {
                interactionStreamReader.close();
            } catch ( IOException e ) {
                // keep it quiet ...
            }
        }
    }
}
