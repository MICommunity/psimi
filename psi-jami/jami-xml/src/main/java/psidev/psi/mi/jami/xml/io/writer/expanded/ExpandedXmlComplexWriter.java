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
 * Expanded PSI-XML writer for biological complexes (no experimental evidences).
 * Participants, features, experiments also have expanded names
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public class ExpandedXmlComplexWriter extends AbstractExpandedXmlWriter<Complex> {

    public ExpandedXmlComplexWriter() {
        super(Complex.class);
    }

    public ExpandedXmlComplexWriter(File file) throws IOException, XMLStreamException {
        super(Complex.class, file);
    }

    public ExpandedXmlComplexWriter(OutputStream output) throws XMLStreamException {
        super(Complex.class, output);
    }

    public ExpandedXmlComplexWriter(Writer writer) throws XMLStreamException {
        super(Complex.class, writer);
    }

    public ExpandedXmlComplexWriter(XMLStreamWriter streamWriter, PsiXmlObjectCache cache) {
        super(Complex.class, streamWriter, cache);
    }


    @Override
    protected Source extractSourceFromInteraction() {
        return getCurrentInteraction().getSource() != null ? getCurrentInteraction().getSource() : super.extractSourceFromInteraction();
    }

    @Override
    protected void initialiseSubWriters() {
        super.initialiseSubWriters(false, true, PsiXmlType.expanded, InteractionCategory.complex, ComplexType.n_ary);
    }
}
