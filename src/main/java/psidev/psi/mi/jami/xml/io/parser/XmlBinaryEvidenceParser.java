package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.InteractionEvidenceSpokeExpansion;
import psidev.psi.mi.jami.model.InteractionEvidence;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.URL;

/**
 * Xml 2.5 parser creating binary interaction evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public class XmlBinaryEvidenceParser extends AbstractPsixmlBinaryParser<InteractionEvidence, BinaryInteractionEvidence> {
    public XmlBinaryEvidenceParser(File file) throws FileNotFoundException, XMLStreamException, JAXBException {
        super(new XmlEvidenceParser(file));
    }

    public XmlBinaryEvidenceParser(InputStream inputStream) throws XMLStreamException, JAXBException {
        super(new XmlEvidenceParser(inputStream));
    }

    public XmlBinaryEvidenceParser(URL url) throws IOException, XMLStreamException, JAXBException {
        super(new XmlEvidenceParser(url));
    }

    public XmlBinaryEvidenceParser(Reader reader) throws XMLStreamException, JAXBException {
        super(new XmlEvidenceParser(reader));
    }

    @Override
    protected ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> initialiseDefaultExpansionMethod() {
        return new InteractionEvidenceSpokeExpansion();
    }


}
