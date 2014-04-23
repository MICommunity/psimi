package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.factory.InteractionObjectCategory;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.xml.io.JaxbUnmarshallerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URL;

/**
 * Full Parser generating interaction evidence objects and loading all experimental details.
 *
 * It will load the all entrySet so is consuming a lot of memory in case of large files but is very performant for small files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class FullXmlEvidenceParser extends AbstractFullPsiXmlParser<InteractionEvidence> {
    public FullXmlEvidenceParser(File file) throws JAXBException, FileNotFoundException {
        super(file);
    }

    public FullXmlEvidenceParser(InputStream inputStream) throws JAXBException {
        super(inputStream);
    }

    public FullXmlEvidenceParser(URL url) throws IOException, JAXBException {
        super(url);
    }

    public FullXmlEvidenceParser(Reader reader) throws JAXBException {
        super(reader);
    }

    @Override
    protected Unmarshaller createJAXBUnmarshaller() throws JAXBException {
        return JaxbUnmarshallerFactory.getInstance().createFullUnmarshaller(getVersion(), InteractionObjectCategory.evidence);
    }

}
