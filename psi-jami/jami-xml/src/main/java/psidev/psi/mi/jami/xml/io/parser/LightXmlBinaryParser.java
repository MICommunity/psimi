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
 * Xml 2.5 parser creating light binary interactions and ignoring experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public class LightXmlBinaryParser extends AbstractPsixmlBinaryParser<Interaction<? extends Participant>,BinaryInteraction> {
    public LightXmlBinaryParser(File file) throws FileNotFoundException, XMLStreamException, JAXBException {
        super(new LightXmlParser(file));
    }

    public LightXmlBinaryParser(InputStream inputStream) throws XMLStreamException, JAXBException {
        super(new LightXmlParser(inputStream));
    }

    public LightXmlBinaryParser(URL url) throws IOException, XMLStreamException, JAXBException {
        super(new LightXmlParser(url));
    }

    public LightXmlBinaryParser(Reader reader) throws XMLStreamException, JAXBException {
        super(new LightXmlParser(reader));
    }

    @Override
    protected ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> initialiseDefaultExpansionMethod() {
        return new SpokeExpansion();
    }
}
