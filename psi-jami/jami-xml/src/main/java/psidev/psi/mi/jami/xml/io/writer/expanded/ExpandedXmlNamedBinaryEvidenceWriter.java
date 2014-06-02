package psidev.psi.mi.jami.xml.io.writer.expanded;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.xml.PsiXmlType;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Expanded PSI-XML writer for named binary interaction evidences (full experimental evidences)
 * Participants, features and experiments also have expanded names to write
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class ExpandedXmlNamedBinaryEvidenceWriter extends AbstractExpandedXmlWriter<BinaryInteractionEvidence> {

    public ExpandedXmlNamedBinaryEvidenceWriter() {
        super(BinaryInteractionEvidence.class);
    }

    public ExpandedXmlNamedBinaryEvidenceWriter(File file) throws IOException, XMLStreamException {
        super(BinaryInteractionEvidence.class, file);
    }

    public ExpandedXmlNamedBinaryEvidenceWriter(OutputStream output) throws XMLStreamException {
        super(BinaryInteractionEvidence.class, output);
    }

    public ExpandedXmlNamedBinaryEvidenceWriter(Writer writer) throws XMLStreamException {
        super(BinaryInteractionEvidence.class, writer);
    }

    public ExpandedXmlNamedBinaryEvidenceWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(BinaryInteractionEvidence.class, streamWriter, cache);
    }

    @Override
    protected Source extractSourceFromInteraction() {
        Experiment exp = getCurrentInteraction().getExperiment();
        if (exp != null && exp.getPublication() != null && exp.getPublication().getSource() != null){
            return exp.getPublication().getSource();
        }
        return super.extractSourceFromInteraction();
    }

    @Override
    protected void initialiseSubWriters() {
        super.initialiseSubWriters(false, true, PsiXmlType.expanded, InteractionCategory.evidence, ComplexType.binary);
    }
}