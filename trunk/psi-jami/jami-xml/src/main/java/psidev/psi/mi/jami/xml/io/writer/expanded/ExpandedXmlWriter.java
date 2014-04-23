package psidev.psi.mi.jami.xml.io.writer.expanded;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;

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

public class ExpandedXmlWriter extends AbstractExpandedXmlMixWriter<Interaction, ModelledInteraction, InteractionEvidence> {

    public ExpandedXmlWriter() {
        super(Interaction.class);
    }

    public ExpandedXmlWriter(File file) throws IOException, XMLStreamException {
        super(Interaction.class, file);
    }

    public ExpandedXmlWriter(OutputStream output) throws XMLStreamException {
        super(Interaction.class, output);
    }

    public ExpandedXmlWriter(Writer writer) throws XMLStreamException {
        super(Interaction.class, writer);
    }

    @Override
    protected void initialiseDelegateWriters() {
        setModelledWriter(new ExpandedXmlModelledWriter(getStreamWriter(), getElementCache()));
        setEvidenceWriter(new ExpandedXmlEvidenceWriter(getStreamWriter(), getElementCache()));
        setLightWriter(new LightExpandedXmlWriter(getStreamWriter(), getElementCache()));
    }
}
