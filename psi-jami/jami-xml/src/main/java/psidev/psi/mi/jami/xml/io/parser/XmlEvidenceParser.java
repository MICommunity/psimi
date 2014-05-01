package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.xml.io.JaxbUnmarshallerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * Parser generating interaction evidence objects with all experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public class XmlEvidenceParser extends AbstractPsiXmlParser<InteractionEvidence> {

    public XmlEvidenceParser(File file) {
        super(file);
    }

    public XmlEvidenceParser(InputStream inputStream) {
        super(inputStream);
    }

    public XmlEvidenceParser(URL url) {
        super(url);
    }

    public XmlEvidenceParser(Reader reader) {
        super(reader);
    }

    @Override
    protected Unmarshaller createJAXBUnmarshaller() throws JAXBException {
        return JaxbUnmarshallerFactory.getInstance().createUnmarshaller(getVersion(), InteractionCategory.evidence);
    }
}
