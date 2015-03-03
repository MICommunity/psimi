package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionCategory;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URL;

/**
 * Full Parser generating interaction objects that could be a mix or abstract interactions and interaction evidences.
 *
 * It will load the all entrySet so is consuming a lot of memory in case of large files but is very performant for small files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class FullXmlParser extends AbstractFullPsiXmlParser<Interaction> {
    public FullXmlParser(File file) throws JAXBException, FileNotFoundException {
        super(file);
    }

    public FullXmlParser(InputStream inputStream) throws JAXBException {
        super(inputStream);
    }

    public FullXmlParser(URL url) throws IOException, JAXBException {
        super(url);
    }

    public FullXmlParser(Reader reader) throws JAXBException {
        super(reader);
    }

    @Override
    protected Unmarshaller createJAXBUnmarshaller() throws JAXBException {
        return JaxbUnmarshallerFactory.getInstance().createFullUnmarshaller(getVersion(), InteractionCategory.mixed);
    }

}
