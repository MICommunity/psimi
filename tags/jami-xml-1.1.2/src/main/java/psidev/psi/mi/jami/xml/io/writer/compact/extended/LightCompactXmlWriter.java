package psidev.psi.mi.jami.xml.io.writer.compact.extended;

import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.xml.PsiXmlType;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.compact.AbstractCompactXmlWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlExtendedInteractionWriter;
import psidev.psi.mi.jami.xml.model.extension.XmlSource;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Compact PSI-XML writer for light interactions (no experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class LightCompactXmlWriter extends AbstractCompactXmlWriter<Interaction> {

    public LightCompactXmlWriter() {
        super(Interaction.class);
    }

    public LightCompactXmlWriter(File file) throws IOException, XMLStreamException {
        super(Interaction.class, file);
    }

    public LightCompactXmlWriter(OutputStream output) throws XMLStreamException {
        super(Interaction.class, output);
    }

    public LightCompactXmlWriter(Writer writer) throws XMLStreamException {
        super(Interaction.class, writer);
    }

    public LightCompactXmlWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(Interaction.class, streamWriter, cache);
    }

    @Override
    protected void registerAvailabilities(Interaction interaction) {
        // nothing to do
    }

    @Override
    protected void registerExperiment(Interaction interaction) {
        getExperiments().addAll(((PsiXmlExtendedInteractionWriter) getInteractionWriter()).extractDefaultExperimentsFrom(interaction));
    }

    @Override
    protected void initialiseSubWriters() {
        super.initialiseSubWriters(true, true, PsiXmlType.compact, InteractionCategory.basic, ComplexType.n_ary);
    }

    @Override
    protected void initialiseDefaultSource() {
        setDefaultSource(new XmlSource("Unknown source"));
    }
}
