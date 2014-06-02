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
 * Expanded PSI-XML writer for a mix of named interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class ExpandedXmlNamedWriter extends AbstractExpandedXmlMixWriter<Interaction, ModelledInteraction, InteractionEvidence> {

    public ExpandedXmlNamedWriter() {
        super(Interaction.class);
    }

    public ExpandedXmlNamedWriter(File file) throws IOException, XMLStreamException {
        super(Interaction.class, file);
    }

    public ExpandedXmlNamedWriter(OutputStream output) throws XMLStreamException {
        super(Interaction.class, output);
    }

    public ExpandedXmlNamedWriter(Writer writer) throws XMLStreamException {
        super(Interaction.class, writer);
    }

    @Override
    protected void initialiseDelegateWriters() {
        setModelledWriter(new ExpandedXmlNamedModelledWriter(getStreamWriter(), getElementCache()));
        setEvidenceWriter(new ExpandedXmlNamedEvidenceWriter(getStreamWriter(), getElementCache()));
        setLightWriter(new LightExpandedXmlNamedWriter(getStreamWriter(), getElementCache()));
    }
}