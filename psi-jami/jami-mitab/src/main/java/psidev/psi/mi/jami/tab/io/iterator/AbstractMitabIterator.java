package psidev.psi.mi.jami.tab.io.iterator;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.io.parser.MitabLineParser;
import psidev.psi.mi.jami.tab.io.parser.ParseException;

import java.util.Iterator;

/**
 * Abstract class for mitab iterator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/06/13</pre>
 */

public abstract class AbstractMitabIterator<T extends Interaction, B extends BinaryInteraction, P extends Participant> implements Iterator<T>{

    private MitabLineParser<B,P> lineParser;
    private B nextBinary;

    public AbstractMitabIterator(MitabLineParser<B,P> lineParser){
        if (lineParser == null){
            throw new IllegalArgumentException("The Mitab iterator needs a non null lineParser.");
        }
        this.lineParser = lineParser;
        processNextBinary();
    }

    private void processNextBinary(){
        this.nextBinary = null;

        while (!this.lineParser.hasFinished() && this.nextBinary == null){
            try {
                this.nextBinary = this.lineParser.MitabLine();
            } catch (ParseException e) {
                this.lineParser.getParserListener().onInvalidSyntax(lineParser.getToken(0).beginLine, lineParser.getToken(0).beginColumn, 0, e);
            }
        }
    }

    public boolean hasNext() {
        return this.nextBinary != null;
    }

    public T next() {
        B currentBinary = this.nextBinary;
        processNextBinary();
        return (T)currentBinary;
    }

    public void remove() {
        throw new UnsupportedOperationException("A MITAB iterator does not support the remove method");
    }
}
