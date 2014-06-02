package psidev.psi.mi.jami.xml.io.writer.expanded;

import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.Interaction;
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
 * Expanded PSI-XML 2.5 writer for light interactions (no experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class LightExpandedXmlWriter extends AbstractExpandedXmlWriter<Interaction> {

    public LightExpandedXmlWriter() {
        super(Interaction.class);
    }

    public LightExpandedXmlWriter(File file) throws IOException, XMLStreamException {
        super(Interaction.class, file);
    }

    public LightExpandedXmlWriter(OutputStream output) throws XMLStreamException {
        super(Interaction.class, output);
    }

    public LightExpandedXmlWriter(Writer writer) throws XMLStreamException {
        super(Interaction.class, writer);
    }

    public LightExpandedXmlWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(Interaction.class, streamWriter, cache);
    }

    @Override
    protected void initialiseSubWriters() {
        super.initialiseSubWriters(false, false, PsiXmlType.expanded, InteractionCategory.basic, ComplexType.n_ary);
    }
}
