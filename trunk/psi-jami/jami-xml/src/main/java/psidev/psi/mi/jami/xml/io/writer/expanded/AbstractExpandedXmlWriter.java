package psidev.psi.mi.jami.xml.io.writer.expanded;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.cache.InMemoryLightIdentityObjectCache;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.AbstractXmlWriter;

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

public abstract class AbstractExpandedXmlWriter<T extends Interaction> extends AbstractXmlWriter<T> {

    private Class<T> type;

    public AbstractExpandedXmlWriter(Class<T> type) {
        super();
        this.type = type;
    }

    public AbstractExpandedXmlWriter(Class<T> type, File file) throws IOException, XMLStreamException {
        super(file);
        this.type = type;
    }

    public AbstractExpandedXmlWriter(Class<T> type, OutputStream output) throws XMLStreamException {
        super(output);
        this.type = type;
    }

    public AbstractExpandedXmlWriter(Class<T> type, Writer writer) throws XMLStreamException {
        super(writer);
        this.type = type;
    }

    protected AbstractExpandedXmlWriter(Class<T> type, XMLStreamWriter streamWriter, PsiXmlObjectCache elementCache) {
        super(streamWriter, elementCache);
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

    protected void writeInteraction() throws XMLStreamException {
        // write interaction
        super.writeInteraction();
        // remove experiments
        getElementCache().removeObject(getInteractionWriter().extractDefaultExperimentFrom(getCurrentInteraction()));
    }

    protected void writeComplex(ModelledInteraction modelled) {
        super.writeComplex(modelled);
        // remove experiments
        getElementCache().removeObject(getComplexWriter().extractDefaultExperimentFrom(modelled));
    }
}
