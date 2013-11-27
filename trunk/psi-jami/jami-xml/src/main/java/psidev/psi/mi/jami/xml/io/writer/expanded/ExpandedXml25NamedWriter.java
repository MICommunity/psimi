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
 * Expanded PSI-XML 2.5 writer for a mix of named interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class ExpandedXml25NamedWriter extends AbstractExpandedXml25MixWriter<Interaction, ModelledInteraction, InteractionEvidence>{

    public ExpandedXml25NamedWriter() {
        super(Interaction.class);
    }

    public ExpandedXml25NamedWriter(File file) throws IOException, XMLStreamException {
        super(Interaction.class, file);
    }

    public ExpandedXml25NamedWriter(OutputStream output) throws XMLStreamException {
        super(Interaction.class, output);
    }

    public ExpandedXml25NamedWriter(Writer writer) throws XMLStreamException {
        super(Interaction.class, writer);
    }

    @Override
    protected void initialiseDelegateWriters() {
        setModelledWriter(new ExpandedXml25NamedModelledWriter(getStreamWriter(), getElementCache()));
        setEvidenceWriter(new ExpandedXml25NamedEvidenceWriter(getStreamWriter(), getElementCache()));
        setLightWriter(new LightExpandedXml25NamedWriter(getStreamWriter(), getElementCache()));
    }
}