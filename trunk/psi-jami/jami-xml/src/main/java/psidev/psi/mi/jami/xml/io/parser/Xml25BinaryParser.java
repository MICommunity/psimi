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
 * Xml 2.5 parser creating binary interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public class Xml25BinaryParser extends AbstractPsixml25BinaryParser<Interaction<? extends Participant>,BinaryInteraction>{
    public Xml25BinaryParser(File file) throws FileNotFoundException, XMLStreamException, JAXBException {
        super(new Xml25Parser(file));
    }

    public Xml25BinaryParser(InputStream inputStream) throws XMLStreamException, JAXBException {
        super(new Xml25Parser(inputStream));
    }

    public Xml25BinaryParser(URL url) throws IOException, XMLStreamException, JAXBException {
        super(new Xml25Parser(url));
    }

    public Xml25BinaryParser(Reader reader) throws XMLStreamException, JAXBException {
        super(new Xml25Parser(reader));
    }

    @Override
    protected ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> initialiseDefaultExpansionMethod() {
        return new SpokeExpansion();
    }
}
