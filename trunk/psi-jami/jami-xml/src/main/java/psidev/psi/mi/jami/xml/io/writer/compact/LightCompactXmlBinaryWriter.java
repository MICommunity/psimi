package psidev.psi.mi.jami.xml.io.writer.compact;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.xml.PsiXmlType;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Compact PSI-XML 2.5 writer for light binary interactions (no experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class LightCompactXmlBinaryWriter extends AbstractCompactXmlWriter<BinaryInteraction> {

    public LightCompactXmlBinaryWriter() {
        super(BinaryInteraction.class);
    }

    public LightCompactXmlBinaryWriter(File file) throws IOException, XMLStreamException {
        super(BinaryInteraction.class, file);
    }

    public LightCompactXmlBinaryWriter(OutputStream output) throws XMLStreamException {
        super(BinaryInteraction.class, output);
    }

    public LightCompactXmlBinaryWriter(Writer writer) throws XMLStreamException {
        super(BinaryInteraction.class, writer);
    }

    public LightCompactXmlBinaryWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(BinaryInteraction.class, streamWriter, cache);
    }

    @Override
    protected void registerAvailabilities(BinaryInteraction interaction) {
        // nothing to do
    }

    @Override
    protected void registerExperiment(BinaryInteraction interaction) {
        getExperiments().add(getInteractionWriter().extractDefaultExperimentFrom(interaction));
    }

    @Override
    protected void initialiseSubWriters() {
        super.initialiseSubWriters(false, false, PsiXmlType.compact, InteractionCategory.basic, ComplexType.binary);
    }
}