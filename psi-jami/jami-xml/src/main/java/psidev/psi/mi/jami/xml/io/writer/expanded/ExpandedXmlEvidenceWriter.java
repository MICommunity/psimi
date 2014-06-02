package psidev.psi.mi.jami.xml.io.writer.expanded;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXmlType;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Expanded PSI-XML writer for interaction evidences (full experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class ExpandedXmlEvidenceWriter extends AbstractExpandedXmlWriter<InteractionEvidence> {

    public ExpandedXmlEvidenceWriter() {
        super(InteractionEvidence.class);
    }

    public ExpandedXmlEvidenceWriter(File file) throws IOException, XMLStreamException {
        super(InteractionEvidence.class, file);
    }

    public ExpandedXmlEvidenceWriter(OutputStream output) throws XMLStreamException {
        super(InteractionEvidence.class, output);
    }

    public ExpandedXmlEvidenceWriter(Writer writer) throws XMLStreamException {
        super(InteractionEvidence.class, writer);
    }

    public ExpandedXmlEvidenceWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(InteractionEvidence.class, streamWriter, cache);
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
        super.initialiseSubWriters(false, false, PsiXmlType.expanded, InteractionCategory.evidence, ComplexType.n_ary);
    }
}

