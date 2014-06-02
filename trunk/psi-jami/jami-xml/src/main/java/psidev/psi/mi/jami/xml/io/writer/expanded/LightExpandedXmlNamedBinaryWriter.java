package psidev.psi.mi.jami.xml.io.writer.expanded;

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
 * Expanded PSI-XML 2.5 writer for light binary interactions (no experimental evidences) having names.
 * Participants, experiments and features are also assumed to have expanded names
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class LightExpandedXmlNamedBinaryWriter extends AbstractExpandedXmlWriter<BinaryInteraction> {

    public LightExpandedXmlNamedBinaryWriter() {
        super(BinaryInteraction.class);
    }

    public LightExpandedXmlNamedBinaryWriter(File file) throws IOException, XMLStreamException {
        super(BinaryInteraction.class, file);
    }

    public LightExpandedXmlNamedBinaryWriter(OutputStream output) throws XMLStreamException {
        super(BinaryInteraction.class, output);
    }

    public LightExpandedXmlNamedBinaryWriter(Writer writer) throws XMLStreamException {
        super(BinaryInteraction.class, writer);
    }

    public LightExpandedXmlNamedBinaryWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(BinaryInteraction.class, streamWriter, cache);
    }

    @Override
    protected void initialiseSubWriters() {
        super.initialiseSubWriters(false, true, PsiXmlType.expanded, InteractionCategory.basic, ComplexType.binary);
    }
}