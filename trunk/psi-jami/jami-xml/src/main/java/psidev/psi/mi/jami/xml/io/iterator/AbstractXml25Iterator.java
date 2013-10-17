package psidev.psi.mi.jami.xml.io.iterator;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.xml.io.parser.PsiXml25Parser;

import javax.xml.stream.XMLStreamException;
import java.util.Iterator;

/**
 * Abstract iterator for parsing xml 2.5 interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public class AbstractXml25Iterator<T extends Interaction> implements Iterator<T> {

    private PsiXml25Parser<T> parser;
    private MIFileParserListener parserListener;
    private T nextInteraction;

    public AbstractXml25Iterator(PsiXml25Parser<T> lineParser) throws MIIOException {
        if (lineParser == null){
            throw new IllegalArgumentException("The PsiXml 2.5 iterator needs a non null parser.");
        }
        this.parser = lineParser;
        processNextBinary();
    }

    private void processNextBinary() throws MIIOException{
        this.nextInteraction = null;

        while (!this.parser.hasFinished() && this.nextInteraction == null){
            try {
                this.nextInteraction = this.parser.parseNextInteraction();
            } catch (XMLStreamException e) {
                if (this.parserListener != null){
                    FileSourceLocator locator = null;
                    if (e.getLocation() != null){
                        locator = new FileSourceLocator(e.getLocation().getLineNumber(), e.getLocation().getColumnNumber());
                    }
                    this.parserListener.onInvalidSyntax(new DefaultFileSourceContext(locator), e);
                }
                else{
                    throw new MIIOException("Impossible to read next interaction.", e);
                }
            } catch (Exception e) {
                if (this.parserListener != null){
                    this.parserListener.onInvalidSyntax(new DefaultFileSourceContext(), e);
                }
                else{
                    throw new MIIOException("Impossible to read next interaction.", e);
                }
            }
        }
    }

    public boolean hasNext() {
        return this.nextInteraction != null;
    }

    public T next() throws MIIOException{
        T currentBinary = this.nextInteraction;
        processNextBinary();
        return currentBinary;
    }

    public void remove() {
        throw new UnsupportedOperationException("A MITAB iterator does not support the remove method");
    }

    public void setParserListener(MIFileParserListener defaultParserListener) {
        this.parserListener = defaultParserListener;
    }
}
