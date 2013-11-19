package psidev.psi.mi.jami.xml.io.writer;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Abstract class for Compact XML 2.5 writers.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractCompactXml25Writer<T extends Interaction> extends AbstractXml25Writer<T> {

    private PsiXml25ElementWriter<String> availabilityWriter;
    private PsiXml25ElementWriter<Experiment> experimentWriter;
    private PsiXml25ElementWriter<Interactor> interactorWriter;

    public AbstractCompactXml25Writer() {
        super();
    }

    public AbstractCompactXml25Writer(File file) throws IOException, XMLStreamException {
        super(file);
    }

    public AbstractCompactXml25Writer(OutputStream output) throws XMLStreamException {
        super(output);
    }

    public AbstractCompactXml25Writer(Writer writer) throws XMLStreamException {
        super(writer);
    }

    @Override
    protected void initialiseSubWriters() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
