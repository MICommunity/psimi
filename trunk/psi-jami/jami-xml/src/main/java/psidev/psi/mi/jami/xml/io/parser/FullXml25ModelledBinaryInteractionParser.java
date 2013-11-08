package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.ModelledInteractionSpokeExpansion;
import psidev.psi.mi.jami.model.ModelledInteraction;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.URL;

/**
 * Full Parser generating binary modelled interaction objects and ignoring all experimental details.
 *
 * It will load the all entrySet so is consuming a lot of memory in case of large files but is very performant for small files
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class FullXml25ModelledBinaryInteractionParser extends AbstractPsixml25BinaryParser<ModelledInteraction,ModelledBinaryInteraction>{
    public FullXml25ModelledBinaryInteractionParser(File file) throws FileNotFoundException, XMLStreamException, JAXBException {
        super(new FullXml25ModelledInteractionParser(file));
    }

    public FullXml25ModelledBinaryInteractionParser(InputStream inputStream) throws XMLStreamException, JAXBException {
        super(new FullXml25ModelledInteractionParser(inputStream));
    }

    public FullXml25ModelledBinaryInteractionParser(URL url) throws IOException, XMLStreamException, JAXBException {
        super(new FullXml25ModelledInteractionParser(url));
    }

    public FullXml25ModelledBinaryInteractionParser(Reader reader) throws XMLStreamException, JAXBException {
        super(new FullXml25ModelledInteractionParser(reader));
    }

    @Override
    protected ComplexExpansionMethod<ModelledInteraction,ModelledBinaryInteraction> initialiseDefaultExpansionMethod() {
        return new ModelledInteractionSpokeExpansion();
    }
}
