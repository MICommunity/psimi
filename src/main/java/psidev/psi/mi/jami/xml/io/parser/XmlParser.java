package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionCategory;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * Parser generating interaction objects that can be interaction evidences or abstract interactions.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public class XmlParser extends AbstractPsiXmlParser<Interaction> {
    public XmlParser(File file){
        super(file);
    }

    public XmlParser(InputStream inputStream){
        super(inputStream);
    }

    public XmlParser(URL url){
        super(url);
    }

    public XmlParser(Reader reader){
        super(reader);
    }

    @Override
    protected Unmarshaller createJAXBUnmarshaller() throws JAXBException {
        return JaxbUnmarshallerFactory.getInstance().createUnmarshaller(getVersion(), InteractionCategory.mixed);
    }
}
