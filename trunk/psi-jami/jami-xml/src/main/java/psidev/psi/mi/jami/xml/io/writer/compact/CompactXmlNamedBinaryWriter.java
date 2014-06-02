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
 * Compact PSI-XML writer for a mix of named binary interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class CompactXmlNamedBinaryWriter extends AbstractCompactXmlMixWriter<BinaryInteraction, ModelledBinaryInteraction, BinaryInteractionEvidence> {

    public CompactXmlNamedBinaryWriter() {
        super(BinaryInteraction.class);
    }

    public CompactXmlNamedBinaryWriter(File file) throws IOException, XMLStreamException {
        super(BinaryInteraction.class, file);
    }

    public CompactXmlNamedBinaryWriter(OutputStream output) throws XMLStreamException {
        super(BinaryInteraction.class, output);
    }

    public CompactXmlNamedBinaryWriter(Writer writer) throws XMLStreamException {
        super(BinaryInteraction.class, writer);
    }

    @Override
    protected void initialiseDelegateWriters() {
        setModelledWriter(new CompactXmlNamedModelledBinaryWriter(getStreamWriter(), getElementCache()));
        setEvidenceWriter(new CompactXmlNamedBinaryEvidenceWriter(getStreamWriter(), getElementCache()));
        setLightWriter(new LightCompactXmlNamedBinaryWriter(getStreamWriter(), getElementCache()));
    }
}
