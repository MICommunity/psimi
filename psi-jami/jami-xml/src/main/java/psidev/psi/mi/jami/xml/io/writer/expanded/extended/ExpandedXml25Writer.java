package psidev.psi.mi.jami.xml.io.writer.expanded.extended;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.extension.XmlSource;
import psidev.psi.mi.jami.xml.io.writer.expanded.AbstractExpandedXml25MixWriter;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Expanded PSI-XML 2.5 writer for a mix of interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class ExpandedXml25Writer extends AbstractExpandedXml25MixWriter<Interaction, ModelledInteraction, InteractionEvidence> {

    public ExpandedXml25Writer() {
        super(Interaction.class);
    }

    public ExpandedXml25Writer(File file) throws IOException, XMLStreamException {
        super(Interaction.class, file);
    }

    public ExpandedXml25Writer(OutputStream output) throws XMLStreamException {
        super(Interaction.class, output);
    }

    public ExpandedXml25Writer(Writer writer) throws XMLStreamException {
        super(Interaction.class, writer);
    }

    @Override
    protected void initialiseDelegateWriters() {
        setModelledWriter(new ExpandedXml25ModelledWriter(getStreamWriter(), getElementCache()));
        setEvidenceWriter(new ExpandedXml25EvidenceWriter(getStreamWriter(), getElementCache()));
        setLightWriter(new LightExpandedXml25Writer(getStreamWriter(), getElementCache()));
    }

    @Override
    protected void initialiseDefaultSource() {
        setDefaultSource(new XmlSource("Unknown source"));
    }
}
