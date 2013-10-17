package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.ModelledInteractionSpokeExpansion;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.extension.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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

public class Xml25ModelledBinaryInteractionParser extends AbstractPsixml25BinaryParser<ModelledInteraction, ModelledBinaryInteraction>{
    public Xml25ModelledBinaryInteractionParser(File file, ComplexExpansionMethod<ModelledInteraction, ModelledBinaryInteraction> expansionMethod) throws FileNotFoundException, XMLStreamException, JAXBException {
        super(file, expansionMethod != null ? expansionMethod : new ModelledInteractionSpokeExpansion());
    }

    public Xml25ModelledBinaryInteractionParser(InputStream inputStream, ComplexExpansionMethod<ModelledInteraction, ModelledBinaryInteraction> expansionMethod) throws XMLStreamException, JAXBException {
        super(inputStream, expansionMethod != null ? expansionMethod : new ModelledInteractionSpokeExpansion());
    }

    public Xml25ModelledBinaryInteractionParser(URL url, ComplexExpansionMethod<ModelledInteraction, ModelledBinaryInteraction> expansionMethod) throws IOException, XMLStreamException, JAXBException {
        super(url, expansionMethod != null ? expansionMethod : new ModelledInteractionSpokeExpansion());
    }

    public Xml25ModelledBinaryInteractionParser(Reader reader, ComplexExpansionMethod<ModelledInteraction, ModelledBinaryInteraction> expansionMethod) throws XMLStreamException, JAXBException {
        super(reader, expansionMethod != null ? expansionMethod : new ModelledInteractionSpokeExpansion());
    }

    @Override
    protected Unmarshaller createJAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(XmlModelledInteraction.class, XmlExperiment.class, XmlInteractor.class,
                Availability.class, XmlSource.class, XmlAnnotation.class);
        return ctx.createUnmarshaller();
    }
}
