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

public class Xml25ModelledBinaryParser extends AbstractPsixml25BinaryParser<ModelledInteraction, ModelledBinaryInteraction>{
    public Xml25ModelledBinaryParser(File file) throws FileNotFoundException, XMLStreamException, JAXBException {
        super(new Xml25ModelledParser(file));
    }

    public Xml25ModelledBinaryParser(InputStream inputStream) throws XMLStreamException, JAXBException {
        super(new Xml25ModelledParser(inputStream));
    }

    public Xml25ModelledBinaryParser(URL url) throws IOException, XMLStreamException, JAXBException {
        super(new Xml25ModelledParser(url));
    }

    public Xml25ModelledBinaryParser(Reader reader) throws XMLStreamException, JAXBException {
        super(new Xml25ModelledParser(reader));
    }

    @Override
    protected ComplexExpansionMethod<ModelledInteraction,ModelledBinaryInteraction> initialiseDefaultExpansionMethod() {
        return new ModelledInteractionSpokeExpansion();
    }
}
