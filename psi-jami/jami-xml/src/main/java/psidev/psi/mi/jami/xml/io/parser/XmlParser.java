package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.io.JaxbUnmarshallerFactory;
import psidev.psi.mi.jami.xml.model.extension.PsiXmLocator;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * Parser generating interaction objects that can be interaction evidences or modelled interactions.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public class XmlParser extends AbstractPsiXmlParser<Interaction<? extends Participant>> {
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
