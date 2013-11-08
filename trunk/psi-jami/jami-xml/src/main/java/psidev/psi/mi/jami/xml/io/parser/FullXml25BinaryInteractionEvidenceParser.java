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
 * Full Parser generating basic binary interaction objects and ignoring all experimental details.
 *
 * It will load the all entrySet so is consuming a lot of memory in case of large files but is very performant for small files
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class FullXml25BinaryInteractionEvidenceParser extends AbstractPsixml25BinaryParser<InteractionEvidence, BinaryInteractionEvidence>{
    public FullXml25BinaryInteractionEvidenceParser(File file) throws FileNotFoundException, XMLStreamException, JAXBException {
        super(new FullXml25InteractionEvidenceParser(file));
    }

    public FullXml25BinaryInteractionEvidenceParser(InputStream inputStream) throws XMLStreamException, JAXBException, FileNotFoundException {
        super(new FullXml25InteractionEvidenceParser(inputStream));
    }

    public FullXml25BinaryInteractionEvidenceParser(URL url) throws IOException, XMLStreamException, JAXBException {
        super(new Xml25InteractionEvidenceParser(url));
    }

    public FullXml25BinaryInteractionEvidenceParser(Reader reader) throws XMLStreamException, JAXBException, FileNotFoundException {
        super(new FullXml25InteractionEvidenceParser(reader));
    }

    @Override
    protected ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> initialiseDefaultExpansionMethod() {
        return new InteractionEvidenceSpokeExpansion();
    }


}
