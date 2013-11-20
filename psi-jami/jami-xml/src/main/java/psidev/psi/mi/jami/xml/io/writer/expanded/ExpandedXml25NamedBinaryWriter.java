package psidev.psi.mi.jami.xml.io.writer.expanded;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Expanded PSI-XML 2.5 writer for a mix of named binary interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class ExpandedXml25NamedBinaryWriter extends AbstractExpandedXml25MixWriter<BinaryInteraction, ModelledBinaryInteraction, BinaryInteractionEvidence>{

    public ExpandedXml25NamedBinaryWriter() {
        super(BinaryInteraction.class);
    }

    public ExpandedXml25NamedBinaryWriter(File file) throws IOException, XMLStreamException {
        super(BinaryInteraction.class, file);
    }

    public ExpandedXml25NamedBinaryWriter(OutputStream output) throws XMLStreamException {
        super(BinaryInteraction.class, output);
    }

    public ExpandedXml25NamedBinaryWriter(Writer writer) throws XMLStreamException {
        super(BinaryInteraction.class, writer);
    }

    @Override
    protected void initialiseDelegateWriters() {
        setModelledWriter(new ExpandedXml25NamedModelledBinaryWriter(getStreamWriter()));
        setEvidenceWriter(new ExpandedXml25NamedBinaryEvidenceWriter(getStreamWriter()));
        setLightWriter(new LightExpandedXml25NamedBinaryWriter(getStreamWriter()));
    }
}
