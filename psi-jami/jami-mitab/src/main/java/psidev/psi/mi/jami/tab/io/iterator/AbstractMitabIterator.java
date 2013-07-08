package psidev.psi.mi.jami.tab.io.iterator;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.extension.MitabSourceLocator;
import psidev.psi.mi.jami.tab.io.parser.MitabLineParser;

import java.util.Iterator;

/**
 * Abstract class for mitab iterator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/06/13</pre>
 */

public abstract class AbstractMitabIterator<T extends Interaction, P extends Participant> implements Iterator<T>{

    private MitabLineParser<T,P> lineParser;
    private T nextBinary;

    public AbstractMitabIterator(MitabLineParser<T,P> lineParser) throws MIIOException {
        if (lineParser == null){
            throw new IllegalArgumentException("The Mitab iterator needs a non null lineParser.");
        }
        this.lineParser = lineParser;
        processNextBinary();
    }

    private void processNextBinary() throws MIIOException{
        this.nextBinary = null;

        while (!this.lineParser.hasFinished() && this.nextBinary == null){
            try {
                this.nextBinary = this.lineParser.MitabLine();
            } catch (Exception e) {
                if (this.lineParser.getParserListener() != null){
                    this.lineParser.getParserListener().onInvalidSyntax(new DefaultFileSourceContext(new MitabSourceLocator(lineParser.getToken(0).beginLine, lineParser.getToken(0).beginColumn, 0)), e);
                }
                else{
                    throw new MIIOException("Impossible to read next interaction.", e);
                }
            }
        }
    }

    public boolean hasNext() {
        return this.nextBinary != null;
    }

    public T next() throws MIIOException{
        T currentBinary = this.nextBinary;
        processNextBinary();
        return currentBinary;
    }

    public void remove() {
        throw new UnsupportedOperationException("A MITAB iterator does not support the remove method");
    }
}
