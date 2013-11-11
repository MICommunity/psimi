package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.xml.ExperimentalEntrySet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
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

public class FullXml25EvidenceParser extends AbstractFullPsiXml25Parser<InteractionEvidence>{
    public FullXml25EvidenceParser(File file) throws JAXBException {
        super(file);
    }

    public FullXml25EvidenceParser(InputStream inputStream) throws JAXBException {
        super(inputStream);
    }

    public FullXml25EvidenceParser(URL url) throws IOException, JAXBException {
        super(url);
    }

    public FullXml25EvidenceParser(Reader reader) throws JAXBException {
        super(reader);
    }

    @Override
    protected Unmarshaller createJAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(ExperimentalEntrySet.class);
        return ctx.createUnmarshaller();
    }
}
