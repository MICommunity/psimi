package psidev.psi.mi.jami.xml.io.writer.expanded;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.xml.io.writer.AbstractXml25Writer;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Abstract class for expanded PSI-xml 2.5 writers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public abstract class AbstractExpandedXml25Writer<T extends Interaction> extends AbstractXml25Writer<T> {

    public AbstractExpandedXml25Writer() {
    }

    public AbstractExpandedXml25Writer(File file) throws IOException, XMLStreamException {
        super(file);
    }

    public AbstractExpandedXml25Writer(OutputStream output) throws XMLStreamException {
        super(output);
    }

    public AbstractExpandedXml25Writer(Writer writer) throws XMLStreamException {
        super(writer);
    }

    @Override
    protected void writeStartEntryContent() throws XMLStreamException {
        // write start entry
        writeStartEntry();
        // write source
        writeSource();
        // write start interactionList
        writeStartInteractionList();
    }
}
