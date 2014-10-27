/**
 * Copyright 2009 The European Bioinformatics Institute, and others.
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
 * limitations under the License.
 */
package uk.ac.ebi.intact.jami.ontology.iterator;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import psidev.psi.mi.jami.model.OntologyTerm;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * Ontology iterator that gets the terms from lines;
 *
 */
public abstract class LineOntologyIterator implements OntologyIterator{

    private LineIterator lineIterator;
    private String currentLine;
    private Reader reader;

    private OntologyTerm currentTerm;

    public LineOntologyIterator(URL url) throws IOException {
        this(url.openStream());
    }

    public LineOntologyIterator(InputStream is) {
        this(new InputStreamReader(is));
    }

    public LineOntologyIterator(Reader reader) {
        this.reader = reader;
        lineIterator = IOUtils.lineIterator(reader);

        processNextLine();
    }

    protected void processNextLine(){
        currentTerm = null;

        while (lineIterator.hasNext() && currentTerm == null) {
            currentLine = lineIterator.next();

            if (!skipLine(currentLine)) {
                currentTerm = processLine(currentLine);
            }
        }

        if (!lineIterator.hasNext()){
            IOUtils.closeQuietly(reader);
        }
    }

    public boolean hasNext() {
        return currentTerm != null;
    }

    public OntologyTerm next() {
        OntologyTerm current = this.currentTerm;

        processNextLine();

        return current;
    }

    public void remove() {
       throw new UnsupportedOperationException("Cannot be removed");
    }

    protected abstract OntologyTerm processLine(String line);

    protected boolean skipLine(String line) {
        if (line == null){
            return true;
        }

        line = line.trim();

        if (line.length() == 0) {
            return true;
        }

        return false;
    }

    protected OntologyTerm getCurrentTerm() {
        return currentTerm;
    }

    protected void setCurrentTerm(OntologyTerm currentTerm) {
        this.currentTerm = currentTerm;
    }
}
