package psidev.psi.mi.jami.crosslink.io.iterator;

import com.googlecode.jcsv.reader.CSVReader;
import psidev.psi.mi.jami.crosslink.listener.CsvParserListener;
import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.InteractionEvidence;

import java.util.Iterator;

/**
 * Crosslink CSV interaction evidence iterator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/06/13</pre>
 */

public class CsvInteractionEvidenceIterator<T extends InteractionEvidence> implements Iterator<T> {

    private CSVReader<T> lineParser;
    private T nextInteraction;
    private CsvParserListener parserListener;

    public CsvInteractionEvidenceIterator(CSVReader<T> lineParser) throws MIIOException {
        if (lineParser == null){
            throw new IllegalArgumentException("The Crosslink CSV iterator needs a non null lineParser.");
        }
        this.lineParser = lineParser;
        processNextInteraction();
        this.parserListener = null;
    }

    public CsvInteractionEvidenceIterator(CSVReader<T> lineParser, CsvParserListener listener) throws MIIOException {
        if (lineParser == null){
            throw new IllegalArgumentException("The Crosslink CSV iterator needs a non null lineParser.");
        }
        this.lineParser = lineParser;
        this.parserListener = listener;
        processNextInteraction();
    }

    private void processNextInteraction() throws MIIOException{
        try {
            this.nextInteraction = this.lineParser.readNext();
        } catch (Exception e) {
            if (this.parserListener != null){
                this.parserListener.onInvalidSyntax(new DefaultFileSourceContext(), e);
            }
            else{
                throw new MIIOException("Impossible to read next interaction.", e);
            }
        }
    }

    public boolean hasNext() {
        return this.nextInteraction != null;
    }

    public T next() throws MIIOException{
        T currentBinary = this.nextInteraction;
        processNextInteraction();
        return currentBinary;
    }

    public void remove() {
        throw new UnsupportedOperationException("A Crosslink CSV iterator does not support the remove method");
    }
}
