package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.InteractionEvidenceSpokeExpansion;
import psidev.psi.mi.jami.model.InteractionEvidence;

import javax.xml.bind.JAXBException;
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

public class FullXml25BinaryEvidenceParser extends AbstractPsixml25BinaryParser<InteractionEvidence, BinaryInteractionEvidence>{
    public FullXml25BinaryEvidenceParser(File file) throws JAXBException {
        super(new FullXml25EvidenceParser(file));
    }

    public FullXml25BinaryEvidenceParser(InputStream inputStream) throws JAXBException {
        super(new FullXml25EvidenceParser(inputStream));
    }

    public FullXml25BinaryEvidenceParser(URL url) throws IOException, JAXBException {
        super(new FullXml25EvidenceParser(url));
    }

    public FullXml25BinaryEvidenceParser(Reader reader) throws JAXBException {
        super(new FullXml25EvidenceParser(reader));
    }

    @Override
    protected ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> initialiseDefaultExpansionMethod() {
        return new InteractionEvidenceSpokeExpansion();
    }


}
