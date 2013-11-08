package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.ModelledEntrySet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * Full Parser generating modelled interaction objects and ignore experimental details.
 *
 * It will load the all entrySet so is consuming a lot of memory in case of large files but is very performant for small files
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class FullXml25ModelledInteractionParser extends AbstractFullPsiXml25Parser<ModelledInteraction>{
    public FullXml25ModelledInteractionParser(File file) throws XMLStreamException, JAXBException {
        super(file);
    }

    public FullXml25ModelledInteractionParser(InputStream inputStream) throws XMLStreamException, JAXBException {
        super(inputStream);
    }

    public FullXml25ModelledInteractionParser(Reader reader) throws XMLStreamException, JAXBException {
        super(reader);
    }

    public FullXml25ModelledInteractionParser(URL url) throws IOException, XMLStreamException, JAXBException {
        super(url);
    }

    @Override
    protected Unmarshaller createJAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(ModelledEntrySet.class);
        return ctx.createUnmarshaller();
    }
}
