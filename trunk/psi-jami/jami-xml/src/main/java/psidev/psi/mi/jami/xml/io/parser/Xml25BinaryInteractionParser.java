package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.SpokeExpansion;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.extension.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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

public class Xml25BinaryInteractionParser extends AbstractPsixml25BinaryParser<Interaction<? extends Participant>,BinaryInteraction>{
    public Xml25BinaryInteractionParser(File file, ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> expansionMethod) throws FileNotFoundException, XMLStreamException, JAXBException {
        super(file, expansionMethod != null ? expansionMethod : new SpokeExpansion());
    }

    public Xml25BinaryInteractionParser(InputStream inputStream, ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> expansionMethod) throws XMLStreamException, JAXBException {
        super(inputStream, expansionMethod != null ? expansionMethod : new SpokeExpansion());
    }

    public Xml25BinaryInteractionParser(URL url, ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> expansionMethod) throws IOException, XMLStreamException, JAXBException {
        super(url, expansionMethod != null ? expansionMethod : new SpokeExpansion());
    }

    public Xml25BinaryInteractionParser(Reader reader, ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> expansionMethod) throws XMLStreamException, JAXBException {
        super(reader, expansionMethod != null ? expansionMethod : new SpokeExpansion());
    }

    @Override
    protected Unmarshaller createJAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(XmlBasicInteraction.class, XmlExperiment.class, XmlInteractor.class,
                Availability.class, XmlSource.class, XmlAnnotation.class);
        return ctx.createUnmarshaller();
    }
}
