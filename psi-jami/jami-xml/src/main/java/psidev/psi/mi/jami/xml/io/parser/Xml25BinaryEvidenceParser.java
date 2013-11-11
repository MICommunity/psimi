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

public class Xml25BinaryEvidenceParser extends AbstractPsixml25BinaryParser<InteractionEvidence, BinaryInteractionEvidence>{
    public Xml25BinaryEvidenceParser(File file) throws FileNotFoundException, XMLStreamException, JAXBException {
        super(new Xml25EvidenceParser(file));
    }

    public Xml25BinaryEvidenceParser(InputStream inputStream) throws XMLStreamException, JAXBException {
        super(new Xml25EvidenceParser(inputStream));
    }

    public Xml25BinaryEvidenceParser(URL url) throws IOException, XMLStreamException, JAXBException {
        super(new Xml25EvidenceParser(url));
    }

    public Xml25BinaryEvidenceParser(Reader reader) throws XMLStreamException, JAXBException {
        super(new Xml25EvidenceParser(reader));
    }

    @Override
    protected ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> initialiseDefaultExpansionMethod() {
        return new InteractionEvidenceSpokeExpansion();
    }


}
