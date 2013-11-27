package psidev.psi.mi.jami.xml.io.writer.expanded;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.xml.InMemoryLightIdentityObjectCache;
import psidev.psi.mi.jami.xml.io.writer.AbstractXml25Writer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
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

    private Class<T> type;

    public AbstractExpandedXml25Writer(Class<T> type) {
        super();
        this.type = type;
    }

    public AbstractExpandedXml25Writer(Class<T> type, File file) throws IOException, XMLStreamException {
        super(file);
        this.type = type;
    }

    public AbstractExpandedXml25Writer(Class<T> type, OutputStream output) throws XMLStreamException {
        super(output);
        this.type = type;
    }

    public AbstractExpandedXml25Writer(Class<T> type, Writer writer) throws XMLStreamException {
        super(writer);
        this.type = type;
    }

    protected AbstractExpandedXml25Writer(Class<T> type, XMLStreamWriter streamWriter) {
        super(streamWriter);
        this.type = type;
    }

    @Override
    protected void initialiseDefaultElementCache() {
        setElementCache(new InMemoryLightIdentityObjectCache());
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

    protected Class<T> getInteractionType() {
        return type;
    }
}
