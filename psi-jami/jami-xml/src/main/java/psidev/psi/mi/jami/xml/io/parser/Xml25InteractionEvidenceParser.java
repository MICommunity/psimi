package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.xml.extension.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.URL;

/**
 * Parser generating interaction evidence objects with all experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public class Xml25InteractionEvidenceParser extends AbstractPsiXml25Parser<InteractionEvidence>{

    public Xml25InteractionEvidenceParser(File file) throws XMLStreamException, JAXBException {
        super(file);
    }

    public Xml25InteractionEvidenceParser(InputStream inputStream) throws XMLStreamException, JAXBException {
        super(inputStream);
    }

    public Xml25InteractionEvidenceParser(URL url) throws IOException, XMLStreamException, JAXBException {
        super(url);
    }

    public Xml25InteractionEvidenceParser(Reader reader) throws XMLStreamException, JAXBException {
        super(reader);
    }

    @Override
    protected Unmarshaller createJAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(XmlAnnotation.class, XmlInteractionEvidence.class, XmlExperiment.class, XmlInteractor.class,
                Availability.class, XmlSource.class);
        return ctx.createUnmarshaller();
    }

    @Override
    protected InteractionEvidence unmarshallInteraction() throws JAXBException {
        return super.unmarshallInteraction(XmlInteractionEvidence.class);
    }
}
