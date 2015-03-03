package psidev.psi.mi.jami.xml.io.writer.compact;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Compact PSI-XML writer for a mix of interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class CompactXmlWriter extends AbstractCompactXmlMixWriter<Interaction, ModelledInteraction, InteractionEvidence> {

    public CompactXmlWriter() {
        super(Interaction.class);
    }

    public CompactXmlWriter(File file) throws IOException, XMLStreamException {
        super(Interaction.class, file);
    }

    public CompactXmlWriter(OutputStream output) throws XMLStreamException {
        super(Interaction.class, output);
    }

    public CompactXmlWriter(Writer writer) throws XMLStreamException {
        super(Interaction.class, writer);
    }

    @Override
    protected void initialiseDelegateWriters() {
        setModelledWriter(new CompactXmlModelledWriter(getStreamWriter(), getElementCache()));
        setEvidenceWriter(new CompactXmlEvidenceWriter(getStreamWriter(), getElementCache()));
        setLightWriter(new LightCompactXmlWriter(getStreamWriter(), getElementCache()));
    }
}
