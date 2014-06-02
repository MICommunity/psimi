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
 * Expanded PSI-XML 2.5 writer for binary interaction evidences (full experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class ExpandedXmlBinaryEvidenceWriter extends AbstractExpandedXmlWriter<BinaryInteractionEvidence> {

    public ExpandedXmlBinaryEvidenceWriter() {
        super(BinaryInteractionEvidence.class);
    }

    public ExpandedXmlBinaryEvidenceWriter(File file) throws IOException, XMLStreamException {
        super(BinaryInteractionEvidence.class, file);
    }

    public ExpandedXmlBinaryEvidenceWriter(OutputStream output) throws XMLStreamException {
        super(BinaryInteractionEvidence.class, output);
    }

    public ExpandedXmlBinaryEvidenceWriter(Writer writer) throws XMLStreamException {
        super(BinaryInteractionEvidence.class, writer);
    }

    public ExpandedXmlBinaryEvidenceWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache elementCache) {
        super(BinaryInteractionEvidence.class, streamWriter, elementCache);
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
        super.initialiseSubWriters(false, false, PsiXmlType.expanded, InteractionCategory.evidence, ComplexType.binary);
    }
}
