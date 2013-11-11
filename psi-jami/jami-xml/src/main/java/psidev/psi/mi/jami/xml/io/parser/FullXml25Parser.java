package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.BasicEntrySet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * Full Parser generating basic interaction objects and ignore experimental details.
 *
 * It will load the all entrySet so is consuming a lot of memory in case of large files but is very performant for small files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class FullXml25Parser extends AbstractFullPsiXml25Parser<Interaction<? extends Participant>>{
    public FullXml25Parser(File file) throws JAXBException {
        super(file);
    }

    public FullXml25Parser(InputStream inputStream) throws JAXBException {
        super(inputStream);
    }

    public FullXml25Parser(URL url) throws IOException, JAXBException {
        super(url);
    }

    public FullXml25Parser(Reader reader) throws JAXBException {
        super(reader);
    }

    @Override
    protected Unmarshaller createJAXBUnmarshaller() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(BasicEntrySet.class);
        return ctx.createUnmarshaller();
    }
}
