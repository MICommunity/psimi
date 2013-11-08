package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.SpokeExpansion;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.URL;

/**
 * Full Parser generating binary interaction evidence objects and loading all experimental details.
 *
 * It will load the all entrySet so is consuming a lot of memory in case of large files but is very performant for small files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class FullXml25BinaryInteractionParser extends AbstractPsixml25BinaryParser<Interaction<? extends Participant>,BinaryInteraction>{

    public FullXml25BinaryInteractionParser(File file) throws XMLStreamException, JAXBException {
        super(new FullXml25InteractionParser(file));
    }

    public FullXml25BinaryInteractionParser(InputStream inputStream) throws XMLStreamException, JAXBException {
        super(new FullXml25InteractionParser(inputStream));
    }

    public FullXml25BinaryInteractionParser(URL url) throws IOException, XMLStreamException, JAXBException {
        super(new FullXml25InteractionParser(url));
    }

    public FullXml25BinaryInteractionParser(Reader reader) throws XMLStreamException, JAXBException {
        super(new FullXml25InteractionParser(reader));
    }

    @Override
    protected ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> initialiseDefaultExpansionMethod() {
        return new SpokeExpansion();
    }
}
