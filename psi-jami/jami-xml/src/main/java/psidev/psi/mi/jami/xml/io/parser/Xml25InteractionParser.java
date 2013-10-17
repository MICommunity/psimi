package psidev.psi.mi.jami.xml.io.parser;

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
 * Parser generating basic interaction objects and ignore experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public class Xml25InteractionParser extends AbstractPsiXml25Parser<Interaction<? extends Participant>>{
    public Xml25InteractionParser(File file) throws FileNotFoundException, XMLStreamException, JAXBException {
        super(file);
    }

    public Xml25InteractionParser(InputStream inputStream) throws XMLStreamException, JAXBException {
        super(inputStream);
    }

    public Xml25InteractionParser(URL url) throws IOException, XMLStreamException, JAXBException {
        super(url);
    }

    public Xml25InteractionParser(Reader reader) throws XMLStreamException, JAXBException {
        super(reader);
    }

    @Override
    protected Unmarshaller createJAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(XmlBasicInteraction.class, XmlParticipant.class, XmlExperiment.class, XmlInteractor.class,
                Availability.class, XmlSource.class, XmlAnnotation.class);
        return ctx.createUnmarshaller();
    }
}
