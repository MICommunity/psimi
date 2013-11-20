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
 * Expanded PSI-XML 2.5 writer for a mix of binary interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class ExpandedXml25BinaryWriter extends AbstractExpandedXml25MixWriter<BinaryInteraction, ModelledBinaryInteraction, BinaryInteractionEvidence>{

    public ExpandedXml25BinaryWriter() {
        super(BinaryInteraction.class);
    }

    public ExpandedXml25BinaryWriter(File file) throws IOException, XMLStreamException {
        super(BinaryInteraction.class, file);
    }

    public ExpandedXml25BinaryWriter(OutputStream output) throws XMLStreamException {
        super(BinaryInteraction.class, output);
    }

    public ExpandedXml25BinaryWriter(Writer writer) throws XMLStreamException {
        super(BinaryInteraction.class, writer);
    }

    @Override
    protected void initialiseDelegateWriters() {
        setModelledWriter(new ExpandedXml25ModelledBinaryWriter(getStreamWriter()));
        setEvidenceWriter(new ExpandedXml25BinaryEvidenceWriter(getStreamWriter()));
        setLightWriter(new LightExpandedXml25BinaryWriter(getStreamWriter()));
    }
}
