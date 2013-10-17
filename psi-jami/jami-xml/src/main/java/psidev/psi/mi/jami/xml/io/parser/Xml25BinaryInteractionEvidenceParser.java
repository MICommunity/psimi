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

public class Xml25BinaryInteractionEvidenceParser extends AbstractPsixml25BinaryParser<InteractionEvidence, BinaryInteractionEvidence>{
    public Xml25BinaryInteractionEvidenceParser(File file) throws FileNotFoundException, XMLStreamException, JAXBException {
        super(new Xml25InteractionEvidenceParser(file));
    }

    public Xml25BinaryInteractionEvidenceParser(InputStream inputStream) throws XMLStreamException, JAXBException, FileNotFoundException {
        super(new Xml25InteractionEvidenceParser(inputStream));
    }

    public Xml25BinaryInteractionEvidenceParser(URL url) throws IOException, XMLStreamException, JAXBException {
        super(new Xml25InteractionEvidenceParser(url));
    }

    public Xml25BinaryInteractionEvidenceParser(Reader reader) throws XMLStreamException, JAXBException, FileNotFoundException {
        super(new Xml25InteractionEvidenceParser(reader));
    }

    @Override
    protected ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> initialiseDefaultExpansionMethod() {
        return new InteractionEvidenceSpokeExpansion();
    }


}
