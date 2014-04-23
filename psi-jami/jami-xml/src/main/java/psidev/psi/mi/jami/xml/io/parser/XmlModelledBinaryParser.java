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
 * Xml 2.5 parser creating modelled binary interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public class XmlModelledBinaryParser extends AbstractPsixmlBinaryParser<ModelledInteraction, ModelledBinaryInteraction> {
    public XmlModelledBinaryParser(File file) throws FileNotFoundException, XMLStreamException, JAXBException {
        super(new XmlModelledParser(file));
    }

    public XmlModelledBinaryParser(InputStream inputStream) throws XMLStreamException, JAXBException {
        super(new XmlModelledParser(inputStream));
    }

    public XmlModelledBinaryParser(URL url) throws IOException, XMLStreamException, JAXBException {
        super(new XmlModelledParser(url));
    }

    public XmlModelledBinaryParser(Reader reader) throws XMLStreamException, JAXBException {
        super(new XmlModelledParser(reader));
    }

    @Override
    protected ComplexExpansionMethod<ModelledInteraction,ModelledBinaryInteraction> initialiseDefaultExpansionMethod() {
        return new ModelledInteractionSpokeExpansion();
    }
}
