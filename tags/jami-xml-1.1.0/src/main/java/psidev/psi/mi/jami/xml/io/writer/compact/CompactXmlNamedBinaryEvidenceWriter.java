package psidev.psi.mi.jami.xml.io.writer.compact;

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
 * Compact PSI-XML writer for named binary interaction evidences (full experimental evidences)
 * Participants, features and experiments also have expanded names to write
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class CompactXmlNamedBinaryEvidenceWriter extends AbstractCompactXmlWriter<BinaryInteractionEvidence> {

    public CompactXmlNamedBinaryEvidenceWriter() {
        super(BinaryInteractionEvidence.class);
    }

    public CompactXmlNamedBinaryEvidenceWriter(File file) throws IOException, XMLStreamException {
        super(BinaryInteractionEvidence.class, file);
    }

    public CompactXmlNamedBinaryEvidenceWriter(OutputStream output) throws XMLStreamException {
        super(BinaryInteractionEvidence.class, output);
    }

    public CompactXmlNamedBinaryEvidenceWriter(Writer writer) throws XMLStreamException {
        super(BinaryInteractionEvidence.class, writer);
    }

    public CompactXmlNamedBinaryEvidenceWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(BinaryInteractionEvidence.class, streamWriter, cache);
    }

    @Override
    protected void registerAvailabilities(BinaryInteractionEvidence interaction) {
        if (interaction.getAvailability() != null){
            getAvailabilities().add(interaction.getAvailability());
        }
    }

    @Override
    protected void registerExperiment(BinaryInteractionEvidence interaction) {
        getExperiments().add(getInteractionWriter().extractDefaultExperimentFrom(interaction));
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
        super.initialiseSubWriters(false, true, PsiXmlType.compact, InteractionCategory.evidence, ComplexType.binary);
    }
}