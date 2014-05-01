package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.io.JaxbUnmarshallerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
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

public class FullXmlModelledParser extends AbstractFullPsiXmlParser<ModelledInteraction> {
    public FullXmlModelledParser(File file) throws JAXBException, FileNotFoundException {
        super(file);
    }

    public FullXmlModelledParser(InputStream inputStream) throws JAXBException {
        super(inputStream);
    }

    public FullXmlModelledParser(Reader reader) throws JAXBException {
        super(reader);
    }

    public FullXmlModelledParser(URL url) throws IOException, JAXBException {
        super(url);
    }

    @Override
    protected Unmarshaller createJAXBUnmarshaller() throws JAXBException {
        return JaxbUnmarshallerFactory.getInstance().createFullUnmarshaller(getVersion(), InteractionCategory.modelled);
    }

}
