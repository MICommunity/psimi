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
 * Compact PSI-XML 2.5 writer for a mix of interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class CompactXml25Writer extends AbstractCompactXml25MixWriter<Interaction, ModelledInteraction, InteractionEvidence>{

    public CompactXml25Writer() {
        super(Interaction.class);
    }

    public CompactXml25Writer(File file) throws IOException, XMLStreamException {
        super(Interaction.class, file);
    }

    public CompactXml25Writer(OutputStream output) throws XMLStreamException {
        super(Interaction.class, output);
    }

    public CompactXml25Writer(Writer writer) throws XMLStreamException {
        super(Interaction.class, writer);
    }

    @Override
    protected void initialiseDelegateWriters() {
        setModelledWriter(new CompactXml25ModelledWriter(getStreamWriter()));
        setEvidenceWriter(new CompactXml25EvidenceWriter(getStreamWriter()));
        setLightWriter(new LightCompactXml25Writer(getStreamWriter()));
    }
}
