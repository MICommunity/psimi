package psidev.psi.mi.jami.xml.io.writer.compact;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Compact PSI-XML 2.5 writer for a mix of named binary interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class CompactXml25NamedBinaryWriter extends AbstractCompactXml25MixWriter<BinaryInteraction, ModelledBinaryInteraction, BinaryInteractionEvidence>{

    public CompactXml25NamedBinaryWriter() {
        super(BinaryInteraction.class);
    }

    public CompactXml25NamedBinaryWriter(File file) throws IOException, XMLStreamException {
        super(BinaryInteraction.class, file);
    }

    public CompactXml25NamedBinaryWriter(OutputStream output) throws XMLStreamException {
        super(BinaryInteraction.class, output);
    }

    public CompactXml25NamedBinaryWriter(Writer writer) throws XMLStreamException {
        super(BinaryInteraction.class, writer);
    }

    @Override
    protected void initialiseDelegateWriters() {
        setModelledWriter(new CompactXml25NamedModelledBinaryWriter(getStreamWriter()));
        setEvidenceWriter(new CompactXml25NamedBinaryEvidenceWriter(getStreamWriter()));
        setLightWriter(new LightCompactXml25NamedBinaryWriter(getStreamWriter()));
    }
}
