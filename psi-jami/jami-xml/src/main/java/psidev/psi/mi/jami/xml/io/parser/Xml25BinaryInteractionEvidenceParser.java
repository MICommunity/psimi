package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.InteractionEvidenceSpokeExpansion;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.xml.extension.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.URL;

/**
 * Xml 2.5 parser creating binary interaction evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public class Xml25BinaryInteractionEvidenceParser extends AbstractPsixml25BinaryParser<InteractionEvidence, BinaryInteractionEvidence>{
    public Xml25BinaryInteractionEvidenceParser(File file, ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) throws FileNotFoundException, XMLStreamException, JAXBException {
        super(file, expansionMethod != null ? expansionMethod : new InteractionEvidenceSpokeExpansion());
    }

    public Xml25BinaryInteractionEvidenceParser(InputStream inputStream, ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) throws XMLStreamException, JAXBException {
        super(inputStream, expansionMethod != null ? expansionMethod : new InteractionEvidenceSpokeExpansion());
    }

    public Xml25BinaryInteractionEvidenceParser(URL url, ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) throws IOException, XMLStreamException, JAXBException {
        super(url, expansionMethod != null ? expansionMethod : new InteractionEvidenceSpokeExpansion());
    }

    public Xml25BinaryInteractionEvidenceParser(Reader reader, ComplexExpansionMethod<InteractionEvidence, BinaryInteractionEvidence> expansionMethod) throws XMLStreamException, JAXBException {
        super(reader, expansionMethod != null ? expansionMethod : new InteractionEvidenceSpokeExpansion());
    }

    @Override
    protected Unmarshaller createJAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(XmlInteractionEvidence.class, XmlExperiment.class, XmlInteractor.class,
                Availability.class, XmlSource.class, XmlAnnotation.class);
        return ctx.createUnmarshaller();
    }
}
