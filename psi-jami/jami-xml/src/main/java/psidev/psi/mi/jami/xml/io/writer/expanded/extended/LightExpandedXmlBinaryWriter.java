package psidev.psi.mi.jami.xml.io.writer.expanded.extended;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.PsiXmlType;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlExtendedInteractionWriter;
import psidev.psi.mi.jami.xml.io.writer.expanded.AbstractExpandedXmlWriter;
import psidev.psi.mi.jami.xml.model.extension.XmlSource;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Expanded PSI-XML writer for light binary interactions (no experimental evidences)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class LightExpandedXmlBinaryWriter extends AbstractExpandedXmlWriter<BinaryInteraction> {

    public LightExpandedXmlBinaryWriter() {
        super(BinaryInteraction.class);
    }

    public LightExpandedXmlBinaryWriter(File file) throws IOException, XMLStreamException {
        super(BinaryInteraction.class, file);
    }

    public LightExpandedXmlBinaryWriter(OutputStream output) throws XMLStreamException {
        super(BinaryInteraction.class, output);
    }

    public LightExpandedXmlBinaryWriter(Writer writer) throws XMLStreamException {
        super(BinaryInteraction.class, writer);
    }

    public LightExpandedXmlBinaryWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(BinaryInteraction.class, streamWriter, cache);
    }

    @Override
    protected void initialiseSubWriters() {
        super.initialiseSubWriters(true, true, PsiXmlType.expanded, InteractionCategory.basic, ComplexType.binary);
    }

    @Override
    protected void initialiseDefaultSource() {
        setDefaultSource(new XmlSource("Unknown source"));
    }

    @Override
    protected void writeInteraction() throws XMLStreamException {
        // write interaction
        super.writeInteraction();
        // remove experiments
        for (Object exp : ((PsiXmlExtendedInteractionWriter)getInteractionWriter()).extractDefaultExperimentsFrom(getCurrentInteraction())){
            getElementCache().removeObject(exp);
        }
    }

    @Override
    protected void writeComplex(ModelledInteraction modelled) {
        super.writeComplex(modelled);
        // remove experiments
        for (Object exp : ((PsiXmlExtendedInteractionWriter)getComplexWriter()).extractDefaultExperimentsFrom(modelled)){
            getElementCache().removeObject(exp);
        }
    }
}