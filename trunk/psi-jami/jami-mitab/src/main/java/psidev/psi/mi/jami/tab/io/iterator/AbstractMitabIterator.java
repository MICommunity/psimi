package psidev.psi.mi.jami.tab.io.iterator;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.io.parser.MitabLineParser;

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
    }

    private void processNextBinary(){
        if (this.lineParser.hasFinished()){

        }
    }

    public boolean hasNext() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public T next() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void remove() {
        throw new UnsupportedOperationException("A MITAB iterator does not support the remove method");
    }
}
